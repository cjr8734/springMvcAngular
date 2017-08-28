package app1.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import app1.model.UserInfo;
import org.springframework.web.bind.annotation.PathVariable;
import app1.utilities.SpringAppContextUtils;
import javax.annotation.Resource;
import javax.sql.DataSource;

@Controller
public class WelcomeController
{
    @Resource
    private DataSource postgresDataSource;
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
    public ModelAndView mainPage( Model aModel ) {
        logger.debug("mainPage() started");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the welcome.jsp page
        mav.setViewName("welcome.jsp");

        // Create a userInfo object
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName("Chris");
        userInfo.setIsAdministrator(true);

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
}