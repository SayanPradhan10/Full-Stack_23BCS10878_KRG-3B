package com.resumebuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller for handling error pages
 */
@Controller
public class ErrorController implements org.springframework.boot.web.servlet.error.ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            switch (statusCode) {
                case 404:
                    model.addAttribute("errorTitle", "Page Not Found");
                    model.addAttribute("errorMessage", "The page you're looking for doesn't exist.");
                    return "error/404";
                case 500:
                    model.addAttribute("errorTitle", "Internal Server Error");
                    model.addAttribute("errorMessage", "Something went wrong on our end. Please try again later.");
                    return "error/500";
                default:
                    model.addAttribute("errorTitle", "Error");
                    model.addAttribute("errorMessage", "An unexpected error occurred.");
                    return "error/general-error";
            }
        }
        
        return "error/general-error";
    }
}