package app1.controllers;

import app1.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * Created by adam on 1/16/2017.
 */
@ControllerAdvice
public class MyExceptionHandler
{
    private static final Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    /***********************************************************************
     * handleNoHandlerFound()
     *
     * Spring MVC calls this method when the user attempts to go to a URL that has no mapping
     *
     * Instead of showing an ugly 404 not found message,
     * we will display a regular web page (404.jsp) and set the status code to Not found (404)
     ************************************************************************/
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoHandlerFound(Exception ex)
    {
        logger.debug("handleNoHandlerFound() finished");

        // Create a modelAndView object
        ModelAndView mav = new ModelAndView();

        // Show the 404.jsp page
        mav.setViewName("404.jsp");

        // Create a userInfo object
        User userInfo = new User();
        userInfo.setUserName("Chris");
        userInfo.setIsAdministrator(true);

        // Add the userInfo information to the view
        mav.addObject("userInfo", userInfo);

        logger.debug("handleNoHandlerFound() finished");
        return mav;
    }

}