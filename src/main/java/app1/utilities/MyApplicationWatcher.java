package app1.utilities;


import javax.servlet.ServletContextEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

public class MyApplicationWatcher extends ContextLoaderListener
{
    private static final Logger logger = LoggerFactory.getLogger( MyApplicationWatcher.class );


    /***********************************************************************************
     * contextInitialized()
     *  1) Read the property file for this webapp
     *  2) Initialize the Spring Context
     *
     * NOTE:  If a runtime exception is thrown, then the webapp will *not* startup
     ************************************************************************************/
    @Override
    public void contextInitialized( ServletContextEvent aContextEvent )
    {
        // Get the name of the web application
        final String sWebAppName = aContextEvent.getServletContext().getContextPath().substring(1);

        logger.debug("{} contextInitialized() started", sWebAppName);

        try
        {
            // I N I T I A T E      S P R I N G       C O N T E X T
            super.contextInitialized( aContextEvent );

            // Do additional tests -- e.g., verify that the database can be reached
            // At this point, we can use spring-beans

        }
        catch (Exception e)
        {
            logger.error("{} WILL NOT STARTUP", sWebAppName);

            // Throw a runtime exception so that this webapp will *not* startup
            RuntimeException runtimeException = new RuntimeException(e);
            runtimeException.setStackTrace(e.getStackTrace());
            throw runtimeException;
        }

        logger.debug("{} contextInitialized() finished", sWebAppName);
    }


    /***********************************************************************************
     * contextDestroyed()
     * The webapp is about to be shutdown
     *   1) Shutdown any database connection pools manually
     *   2) Shutdown the spring context
     ************************************************************************************/
    @Override
    public void contextDestroyed( ServletContextEvent aContextEvent )
    {
        // Get the name of the web application
        final String sWebAppName = aContextEvent.getServletContext().getContextPath().substring(1);

        logger.debug("{} contextDestroyed() started", sWebAppName);


        // S H U T D O W N       S P R I N G       C O N T E X T
        super.contextDestroyed(aContextEvent);

        // At this point, all spring-beans are destroyed


        logger.debug("{} contextDestroyed() finished", sWebAppName);
    }


}