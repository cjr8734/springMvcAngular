package app1.controllers;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import app1.model.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import app1.utilities.SpringAppContextUtils;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Properties;

@Controller
public class WelcomeController
{
    @Resource(name = "myProps")
    private Properties appProperties;

    private final static Logger logger = LoggerFactory.getLogger(WelcomeController.class);


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

        // Create a userInfo object
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("Chris");
        userInfo.setIsAdministrator(true);

        String sDatabaseTime = getDatabaseTime();
        logger.debug("Current database time is {}", sDatabaseTime);
        mav.addObject("currentTime", sDatabaseTime);

        // Add the userInfo information to the view
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

        // Create a userInfo object
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("Chris");
        userInfo.setIsAdministrator(true);

        // Add the userInfo information to the view
        mav.addObject("userInfo", userInfo);

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
     *  1) Run SQL to get a List of UserInfo objects
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
            ArrayList<UserInfo> users = getUserListOrderedBy("name");

            // Convert the list of UserInfo into a JSON string
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
     *  1) Run SQL to get a List of UserInfo objects
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
            ArrayList<UserInfo> users = getUserListOrderedBy("name");

            // Return the array of Users and a status code of 200 (OK)
            // NOTE:  Spring will convert this to JSON for us automatically
            return new ResponseEntity<ArrayList<UserInfo>>(users, HttpStatus.OK);
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
     *  2) Loop through the results, creating UserInfo objects
     *
     *  Returns a list of UserInfo objects
     ****************************************************************************/
    private ArrayList<UserInfo> getUserListOrderedBy(String aOrderBy) throws Exception
    {
        ArrayList<UserInfo> users = new ArrayList<UserInfo>();

        // Construct the SQL call
        final String sSql = "Select name from users order by " + aOrderBy;

        JdbcTemplate jt = new JdbcTemplate(this.postgresDataSource);

        // Get a connection from the JDBC pool, run the query, return the connection to the pool
        SqlRowSet rs = jt.queryForRowSet(sSql);

        while (rs.next())
        {
            // Get the name from the read-only recordset
            String sUserName = rs.getString(1);

            // Construct a new userInfo object and popuplate it with data from the database
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(sUserName);
            userInfo.setIsAdministrator(false);

            users.add(userInfo);
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
}