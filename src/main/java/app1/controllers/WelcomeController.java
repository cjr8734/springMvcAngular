package app1.controllers;

import app1.model.User;
import app1.security.UserInfo;
import app1.services.ElasticSearchDao;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.PathVariable;
import app1.utilities.SpringAppContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

@Controller
public class WelcomeController
{
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
    public ModelAndView handleDefaultPage( Model aModel )
    {
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
    public ModelAndView mainPage( Model aModel ) throws Exception {
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
    @RequestMapping(value="/example/{aViewName}")
    public ModelAndView showExamplePage(@PathVariable String aViewName, Model aModel )
    {
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
    private String getDatabaseTime() throws Exception
    {
        JdbcTemplate jt = new JdbcTemplate( this.postgresDataSource );

        final String sSql = "Select now()";

        // Run a query -- which initialized the connection pool
        String sDatabaseTime = jt.queryForObject(sSql, String.class);

        return sDatabaseTime;
    }


    /***************************************************************************
     * getUsers()   *REST EndPoint*
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
    @RequestMapping(value="/rest/users", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUsers()
    {
        logger.debug("getUsers() started");

        try
        {
            // Run a SQL call to get the list of users
            ArrayList<User> users = getUserListOrderedBy("log_id");

            // Convert the list of User into a JSON string
            Gson gson = new Gson();
            String sJson = gson.toJson(users);

            // Return respnose code of 200 and the JSON string
            return new ResponseEntity<String>(sJson, HttpStatus.OK);
        }
        catch (Exception e)
        {
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
     * getUsers2()   *REST EndPoint*
     *  1) Run SQL to get a List of User objects
     *  2) Have Spring Convert the list into a JSON string
     *
     * ASSUMPTION:  You have a dependency that will convert an object to JSON
     *              such as
     *
     *  	    	 <dependency>
     *  	            <groupId>com.google.code.gson</groupId>
     *  	            <artifactId>gson</artifactId>
     *  	            <version>2.6.2</version>
     *  	    	 </dependency>
     *
     *  	        *OR*
     *
     *  	     	<dependency>
     *  	           <groupId>com.fasterxml.jackson.core</groupId>
     *  	           <artifactId>jackson-databind</artifactId>
     *  	           <version>2.8.1</version>
     *  	      	</dependency>
     *  	      	<dependency>
     *  	           <groupId>javax.xml.bind</groupId>
     *  	           <artifactId>jaxb-api</artifactId>
     *  	           <version>2.2.12</version>
     *  	      	</dependency>
     ****************************************************************************/
    @RequestMapping(value="/rest/users2", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> getUsers2()
    {
        logger.debug("getUsers() started");

        try
        {
            // Run a SQL call to get the list of users
            ArrayList<User> users = getUserListOrderedBy("name");

            // Return the array of Users and a status code of 200 (OK)
            // NOTE:  Spring will convert this to JSON for us automatically
            return new ResponseEntity<ArrayList<User>>(users, HttpStatus.OK);
        }
        catch (Exception e)
        {
            // Tell the AJAX call that this call failed
            logger.error("Error occurred making rest call to /rest/users2", e);

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
     * getUserListOrderedBy()
     *  1) Run a sql call to get all userinfo from the database
     *  2) Loop through the results, creating User objects
     *
     *  Returns a list of User objects
     ****************************************************************************/
    private ArrayList<User> getUserListOrderedBy(String aOrderBy) throws Exception
    {
        ArrayList<User> users = new ArrayList<User>();

        // Construct the SQL call
        final String sSql = "Select log_id from jrss_data.data_pa order by " + aOrderBy;

        JdbcTemplate jt = new JdbcTemplate(this.postgresDataSource);

        // Get a connection from the JDBC pool, run the query, return the connection to the pool
        SqlRowSet rs = jt.queryForRowSet(sSql);

        while (rs.next())
        {
            // Get the name from the read-only recordset
            String sUserName = rs.getString(1);

            // Construct a new user object and popuplate it with data from the database
            User user = new User();
            user.setUserName(sUserName);
            user.setIsAdministrator(false);

            users.add(user);
        }

        return users;
    }


    /***************************************************************************
     * getFormattedMessageFromException()
     *
     * Take the passed-in exception object and return a formatted string
     ****************************************************************************/
    private String getFormattedMessageFromException(Exception aException)
    {
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
    public String getStackTraceAsString(Exception aException)
    {
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
    @RequestMapping(value="/search/{rawQuery}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<?> runSearch(@PathVariable(value="rawQuery") String aRawQuery)
    {
        logger.debug("runSearch() started.  aRawQuery={}", aRawQuery);

        final String ES_INEX_NAME = "docs";
        final int    ES_PAGE_SIZE = 5;
        final int    ES_STARTING_RECORD_NUMBER = 0;

        try
        {
            // Run a *synchronous* simple-query-string search against ElasticSearch
            String sJsonResults = elasticSearchDao.runSimpleQueryString(aRawQuery, ES_INEX_NAME, ES_STARTING_RECORD_NUMBER, ES_PAGE_SIZE);

            // Return respnose code of 200 and the JSON string
            return new ResponseEntity<String>(sJsonResults, HttpStatus.OK);
        }
        catch (Exception e)
        {
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
    public ModelAndView showGrid1( Model aModel ) throws Exception
    {
        logger.debug("showGrid1() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the grid1.jsp page
        mav.setViewName("grid1.jsp");

        // Part 1:  Get list of UserInfo objects from the the database
        ArrayList<User> users = getUserListOrderedBy("name");

        // Part 2:  Convert the list of UserInfo objects into a JSON string
        String sListOfUsersAsJson = this.gson.toJson(users);

        // Part 3:  Add the JSON string to the model-and-view  (so the page can access it)
        mav.addObject("listOfUsersAsJson", sListOfUsersAsJson);

        logger.debug("showGrid1() finished");
        return mav;
    }

    /**********************************************************************
     * download1()
     ***********************************************************************/
    @RequestMapping(value = "/download/{fileId}", method = RequestMethod.GET)
    public  ResponseEntity<?> download(@PathVariable("fileId") String aFileId, HttpServletResponse response) throws Exception
    {
        logger.debug("download() started.  aFileId={}", aFileId);

        // Use the passed-in fileId to generate a full-path
        //    String sFullPath = stuffService.figureOutFileNameFor(stuffId);
        String sFullPath = "C:\\vault\\CentOS-6.6-x86_64-bin-DVD1.iso";

        try
        {
            File file = new File(sFullPath);

            HttpHeaders headers = new HttpHeaders();

            // Set a header with the length of the file (so the browser will know what the total file size is)
            headers.setContentLength(file.length() );

            // Set a header with the default name to save this file as
            headers.setContentDispositionFormData("attachment", file.getName());

            FileInputStream fis = new FileInputStream(file);
            InputStreamResource isr = new InputStreamResource(fis);
            return new ResponseEntity<InputStreamResource>(isr, headers, HttpStatus.OK);
        }
        catch (Exception e)
        {
            logger.error("Error occurred making call to /download/{}", aFileId, e);

            // Get a formatted error message from the exception object
            String sMessage = getFormattedMessageFromException(e);

            // Tell the AJAX caller that this will be plain text being returned (and not JSON)
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);

            // Return the error back to the caller
            return new ResponseEntity<String>(sMessage, headers, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        finally
        {
            logger.debug("download() finished.");
        }
    }

    /**********************************************************************
     * exportCsv()
     ***********************************************************************/
    @RequestMapping(value="/exportCsv", method=RequestMethod.GET)
    public ModelAndView exportCsv()
    {
        ModelAndView mav = new ModelAndView();

        // Create an array list of stuff to be exported out
        ArrayList<String[]> csvOutput = new ArrayList<String[]>();

        // Add the header to the csvOutput
        csvOutput.add(new String[] {"id", "name", "position" } );

        // Add the data
        csvOutput.add(new String[] {"1", "Adam", "Developer" } );
        csvOutput.add(new String[] {"2", "Ben", "Developer" } );

        ViewCSV mavView = new ViewCSV("exportOutput.csv", csvOutput);
        mav.setView(mavView);

        return(mav);
    }

}