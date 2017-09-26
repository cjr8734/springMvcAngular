package app1.controllers;

import app1.model.DataInstance;
import app1.model.User;
import app1.security.UserInfo;
import app1.services.ElasticSearchDao;
import app1.utilities.SpringAppContextUtils;
import app1.utilities.ViewCSV;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.sql.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

@Controller
public class WelcomeController {
    @Resource(name = "myProps")
    private Properties appProperties;

    @Resource
    private ElasticSearchDao elasticSearchDao;

    private final static Logger logger = LoggerFactory.getLogger(WelcomeController.class);
    private Gson gson = new Gson();


    /**********************************************************************
     * handleDefaultPage()
     *
     * The user browsed to the   http://www.myserver.com/webapp
     * So, forward the user to   http://www.myserver.com/webapp/forward
     ***********************************************************************/
    @RequestMapping("/")
    public ModelAndView handleDefaultPage(Model aModel) {
        String sAppMode = appProperties.getProperty("app.mode");
        logger.debug("app.mode={}", sAppMode);

        // By default, forward users to the /welcome page
        return new ModelAndView("forward:/welcome");
    }

    /**********************************************************************
     * mainPage()
     *
     * The user browsed to the /welcome page
     *  1) Get a userinfo object setup
     *  2) Forward the user to the welcome.jsp page
     ***********************************************************************/
    @RequestMapping("/welcome")
    public ModelAndView mainPage(Model aModel) throws Exception {
        logger.debug("mainPage() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the welcome.jsp page
        mav.setViewName("welcome.jsp");

        // Get the userInfo object from Spring-security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserInfo userInfo = (UserInfo) auth.getPrincipal();

        String sDatabaseTime = getDatabaseTime();
        logger.debug("Current database time is {}", sDatabaseTime);
        mav.addObject("currentTime", sDatabaseTime);

        // Add the user information to the view
        mav.addObject("userInfo", userInfo);

        logger.debug("mainPage() finished");
        return mav;
    }

    /**********************************************************************
     * showExamplePage()
     ***********************************************************************/
    @RequestMapping(value = "/example/{aViewName}")
    public ModelAndView showExamplePage(@PathVariable String aViewName, Model aModel) {
        // Create a modelAndView object
        String sViewName = aViewName + ".jsp";
        ModelAndView mav = new ModelAndView(sViewName);

        // Create a user object
        User user = new User();
        user.setUserName("Chris");
        user.setIsAdministrator(true);

        // Add the user information to the view
        mav.addObject("user", user);

        return mav;
    }

    DataSource postgresDataSource = (DataSource) SpringAppContextUtils.getBean("postgresDataSource");

    /*******************************************************************************
     * getDatabaseTime()
     *******************************************************************************/
    private String getDatabaseTime() throws Exception {
        JdbcTemplate jt = new JdbcTemplate(this.postgresDataSource);

        final String sSql = "Select now()";

        // Run a query -- which initialized the connection pool
        String sDatabaseTime = jt.queryForObject(sSql, String.class);

        return sDatabaseTime;
    }


    /***************************************************************************
     * getPAData()   *REST EndPoint*
     *  1) Run SQL to get a List of User objects
     *  2) Use the GSON library to convert the list into a JSON string
     *  3) Return the JSON string
     *
     * ASSUMPTION:  You have the GSON library dependecy added:
     *  	     <dependency>
     *  	          <groupId>com.google.code.gson</groupId>
     *  	          <artifactId>gson</artifactId>
     *  	          <version>2.6.2</version>
     *  	     </dependency>
     ****************************************************************************/
    @RequestMapping(value = "/rest/PA", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getPAData() {
        logger.debug("getUsers() started");

        try {
            // Run a SQL call to get the list of users
            ArrayList<DataInstance> dataPA = getPADataOrderedBy("log_id");

            // Convert the list of User into a JSON string
            Gson gson = new Gson();
            String sJson = gson.toJson(dataPA);

            // Return respnose code of 200 and the JSON string
            return new ResponseEntity<String>(sJson, HttpStatus.OK);
        } catch (Exception e) {
            // Tell the AJAX call that this call failed
            logger.error("Error occurred making rest call to /rest/users", e);

            // Get a formatted error message from the exception object
            String sMessage = getFormattedMessageFromException(e);

            // Tell the AJAX caller that this will be plain text being returned (and not JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            // Return the error back to the caller
            return new ResponseEntity<String>(sMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /***************************************************************************
     * getPADataOrderedBy()
     *  1) Run a sql call to get all userinfo from the database
     *  2) Loop through the results, creating User objects
     *
     *  Returns a list of User objects
     ****************************************************************************/
    private ArrayList<DataInstance> getPADataOrderedBy(String aOrderBy) throws Exception {
        ArrayList<DataInstance> dataPA = new ArrayList<DataInstance>();

        // Construct the SQL call
        final String sSql = "Select * from data_pa order by " + aOrderBy + " limit 20";

        JdbcTemplate jt = new JdbcTemplate(this.postgresDataSource);

        // Get a connection from the JDBC pool, run the query, return the connection to the pool
        SqlRowSet rs = jt.queryForRowSet(sSql);

        while (rs.next()) {
            // Get the name from the read-only recordset
            int sLogID = rs.getInt(1);
            Timestamp sLogDate = rs.getTimestamp(2);
            Timestamp sBeginTS = rs.getTimestamp(3);
            Timestamp sEndTS = rs.getTimestamp(5);
            String sType = rs.getString(4);
            String sSrcIP = rs.getString(6);
            String sDstIP = rs.getString(7);
            String sRule = rs.getString(8);
            String sApp = rs.getString(9);
            String sSrcNetwork = rs.getString(10);
            String sDstNetwork = rs.getString(11);
            int sBytes = rs.getInt(12);
            int sSrcPort = rs.getInt(13);
            int sDstPort = rs.getInt(14);
            String sProtocol = rs.getString(15);

            // Construct a new user object and popuplate it with data from the database
            DataInstance dataRow = new DataInstance();
            dataRow.setLogID(sLogID);
            dataRow.setLogDate(sLogDate);
            dataRow.setBeginTS(sBeginTS);
            dataRow.setEndTS(sEndTS);
            dataRow.setType(sType);
            dataRow.setSrcIP(sSrcIP);
            dataRow.setDstIP(sDstIP);
            dataRow.setRule(sRule);
            dataRow.setApp(sApp);
            dataRow.setSrcNetwork(sSrcNetwork);
            dataRow.setDstNetwork(sDstNetwork);
            dataRow.setBytes(sBytes);
            dataRow.setSrcPort(sSrcPort);
            dataRow.setDstPort(sDstPort);
            dataRow.setProtocol(sProtocol);

            dataPA.add(dataRow);
        }

        return dataPA;
    }


    /***************************************************************************
     * getFormattedMessageFromException()
     *
     * Take the passed-in exception object and return a formatted string
     ****************************************************************************/
    private String getFormattedMessageFromException(Exception aException) {
        StringBuilder sbMessage = new StringBuilder();

        String sStackTrace = getStackTraceAsString(aException);

        sbMessage.append("Message: ")
                .append(aException.getMessage())
                .append("\n\n")
                .append("Cause: ")
                .append(aException.getCause())
                .append("\n\n")
                .append("StackTrace:\n")
                .append(sStackTrace);

        return sbMessage.toString();
    }


    /***************************************************************************
     * getStackTraceAsString()
     *
     * Take the passed-in exception object and generate a stack trace as
     * a string separated by \n
     ****************************************************************************/
    public String getStackTraceAsString(Exception aException) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : aException.getStackTrace()) {
            sb.append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    /***************************************************************************
     * runSearch()
     *
     * Returns the JSON holding a list of users
     ****************************************************************************/
    @RequestMapping(value = "/search/{rawQuery}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> runSearch(@PathVariable(value = "rawQuery") String aRawQuery) {
        logger.debug("runSearch() started.  aRawQuery={}", aRawQuery);

        final String ES_INEX_NAME = "docs";
        final int ES_PAGE_SIZE = 5;
        final int ES_STARTING_RECORD_NUMBER = 0;

        try {
            // Run a *synchronous* simple-query-string search against ElasticSearch
            String sJsonResults = elasticSearchDao.runSimpleQueryString(aRawQuery, ES_INEX_NAME, ES_STARTING_RECORD_NUMBER, ES_PAGE_SIZE);

            // Return respnose code of 200 and the JSON string
            return new ResponseEntity<String>(sJsonResults, HttpStatus.OK);
        } catch (Exception e) {
            // Tell the AJAX call that this call failed
            logger.error("Error occurred making rest call to /search", e);

            // Get a formatted error message from the exception object
            String sMessage = getFormattedMessageFromException(e);

            // Tell the AJAX caller that this will be plain text being returned (and not JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            // Return the error back to the caller
            return new ResponseEntity<String>(sMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**********************************************************************
     * showGrid1()
     *
     * The user browsed to the /grid1 page
     *  1) Run a SQL query to get some data from the database
     *  2) Add that data to the ModelAndView
     *  3) Forward the user to the grid1.jsp page
     ***********************************************************************/
    @RequestMapping("/grid1")
    public ModelAndView showGrid1(Model aModel) throws Exception {
        logger.debug("showGrid1() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the grid1.jsp page
        mav.setViewName("grid1.jsp");

        // Part 1:  Get list of UserInfo objects from the the database
        ArrayList<DataInstance> PAData = getPADataOrderedBy("log_id");

        // Part 2:  Convert the list of UserInfo objects into a JSON string
        String sPADataAsJson = this.gson.toJson(PAData);

        // Part 3:  Add the JSON string to the model-and-view  (so the page can access it)
        mav.addObject("PADataAsJson", sPADataAsJson);

        logger.debug("showGrid1() finished");
        return mav;
    }

    /**********************************************************************
     * download1()
     ***********************************************************************/
    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    public ResponseEntity<?> download(@PathVariable("fileId") String aFileId, HttpServletResponse response) throws Exception {
        logger.debug("download() started.  aFileId={}", aFileId);

        // Use the passed-in fileId to generate a full-path
        //    String sFullPath = stuffService.figureOutFileNameFor(stuffId);
        String sFullPath = "C:\\vault\\CentOS-6.6-x86_64-bin-DVD1.iso";

        try {
            File file = new File(sFullPath);

            HttpHeaders headers = new HttpHeaders();

            // Set a header with the length of the file (so the browser will know what the total file size is)
            headers.setContentLength(file.length());

            // Set a header with the default name to save this file as
            headers.setContentDispositionFormData("attachment", file.getName());

            FileInputStream fis = new FileInputStream(file);
            InputStreamResource isr = new InputStreamResource(fis);
            return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error occurred making call to /download/{}", aFileId, e);

            // Get a formatted error message from the exception object
            String sMessage = getFormattedMessageFromException(e);

            // Tell the AJAX caller that this will be plain text being returned (and not JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            // Return the error back to the caller
            return new ResponseEntity<String>(sMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            logger.debug("download() finished.");
        }
    }

    /**********************************************************************
     * exportCsv()
     ***********************************************************************/
    @RequestMapping(value = "/exportCsv", method = RequestMethod.GET)
    public ModelAndView exportCsv() {
        ModelAndView mav = new ModelAndView();

        // Create an array list of stuff to be exported out
        ArrayList<String[]> csvOutput = new ArrayList<String[]>();

        // Add the header to the csvOutput
        csvOutput.add(new String[]{"id", "name", "position"});

        // Add the data
        csvOutput.add(new String[]{"1", "Adam", "Developer"});
        csvOutput.add(new String[]{"2", "Ben", "Developer"});

        ViewCSV mavView = new ViewCSV("exportOutput.csv", csvOutput);
        mav.setView(mavView);

        return (mav);
    }

    /**********************************************************************
     * showMap1()
     *
     * The user browsed to the /sample page
     *  1) Forward the user to the sample.jsp page
     ***********************************************************************/
    @RequestMapping("/map1")
    public ModelAndView showMap1( Model aModel )
    {
        logger.debug("showMap1() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the sample.jsp page
        mav.setViewName("map1.jsp");

        return mav;
    }

    /**********************************************************************
     * showPlayground()
     *
     * The user browsed to the /sample page
     *  1) Forward the user to the sample.jsp page
     ***********************************************************************/
    @RequestMapping("/playground")
    public ModelAndView showPlayground( Model aModel )
    {
        logger.debug("showMap1() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the sample.jsp page
        mav.setViewName("playground.jsp");

        return mav;
    }

    /***************************************************************************
     * getProtocolData()
     *  1) Run a sql call to get all userinfo from the database
     *  2) Loop through the results, creating User objects
     *
     ****************************************************************************/
    public Object[] getProtocolData(int topNumber, String srcORdst) throws Exception {

        JdbcTemplate jt = new JdbcTemplate(this.postgresDataSource);
        Connection con = jt.getDataSource().getConnection();

        String query = "";

        if (Objects.equals("Source", srcORdst)) {
            query = "select * from(\n" +
                    "  select\n" +
                    "    src_ip,\n" +
                    "    protocol,\n" +
                    "    count(*) counter,\n" +
                    "    'SOURCE'\n" +
                    "  from\n" +
                    "    data_pa\n" +
                    "  where\n" +
                    "    upper(action) like '%BLOCK%'\n" +
                    "  group by\n" +
                    "    src_ip,\n" +
                    "    protocol\n" +
                    "  having\n" +
                    "    count(src_ip) > 1\n" +
                    "  order by\n" +
                    "    counter desc) total_data\n" +
                    "limit ?;";
        }
        if (Objects.equals("Destination", srcORdst)) {
            query = "select * from(\n" +
                    "  select\n" +
                    "    dst_ip,\n" +
                    "    protocol,\n" +
                    "    count(*) counter,\n" +
                    "    'DESTINATION'\n" +
                    "  from\n" +
                    "    data_pa\n" +
                    "  where\n" +
                    "    upper(action) like '%BLOCK%' \n" +
                    "  group by\n" +
                    "    dst_ip,\n" +
                    "    protocol\n" +
                    "  having\n" +
                    "    count(dst_ip) > 1\n" +
                    "  order by\n" +
                    "    counter desc) total_data\n" +
                    "limit ?;";
        }
        if (Objects.equals("Both", srcORdst)) {
            query = "select\n" +
                    "  src_ip,\n" +
                    "  protocol,\n" +
                    "  count(*) counter,\n" +
                    "  'SOURCE'\n" +
                    "from \n" +
                    "  data_PA\n" +
                    "where\n" +
                    "  upper(action) like '%BLOCK%'\n" +
                    "group by\n" +
                    "  src_ip,\n" +
                    "  protocol\n" +
                    "UNION ALL\n" +
                    "select\n" +
                    "  dst_ip,\n" +
                    "  protocol,\n" +
                    "  count(*) counter,\n" +
                    "  'DESTINATION'\n" +
                    "from \n" +
                    "  data_PA\n" +
                    "where\n" +
                    "  upper(action) like '%BLOCK%'\n" +
                    "group by\n" +
                    "  dst_ip,\n" +
                    "  protocol\n" +
                    "order by\n" +
                    "    counter desc\n" +
                    "limit ?;";
        }

        // Construct the SQL call
        PreparedStatement sSql = con.prepareStatement(query);
        sSql.setInt(1,topNumber);

        // Get a connection from the JDBC pool, run the query, return the connection to the pool
        ResultSet rs = sSql.executeQuery();

        String[] listIPs = new String[topNumber];
        String[] protocols = new String[topNumber];
        int[] count = new int[topNumber];
        String[] direction = new String[topNumber];

        while (rs.next()) {
            listIPs[rs.getRow()-1] = rs.getString(1);
            protocols[rs.getRow()-1] = rs.getString(2);
            count[rs.getRow()-1] = rs.getInt(3);
            direction[rs.getRow()-1] = rs.getString(4);
        }
        System.out.println(listIPs);

        return new Object[]{listIPs, protocols, count, direction};
    }

    /**********************************************************************
     * showGrid2()
     *
     * The user browsed to the /grid1 page
     *  1) Run a SQL query to get some data from the database
     *  2) Add that data to the ModelAndView
     *  3) Forward the user to the grid1.jsp page
     ***********************************************************************/
    @RequestMapping(value = "/grid2")
    public ModelAndView showGrid2(@RequestParam(value="topNumber", required=false, defaultValue="10") int topNumber,
                                  @RequestParam(value="srcORdst", required=false, defaultValue="Both") String srcORdst,
                                  Model aModel) throws Exception {
        logger.debug("showGrid2() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the grid1.jsp page
        mav.setViewName("grid2.jsp");

        // Part 1:  Get list of UserInfo objects from the the database
        Object[] PAData = getProtocolData(topNumber, srcORdst);

        String sPADataAsJson = this.gson.toJson(PAData);

        // Part 3:  Add the JSON string to the model-and-view  (so the page can access it)
        mav.addObject("PADataAsJson", sPADataAsJson);

        logger.debug("showGrid2() finished");
        return mav;
    }

}