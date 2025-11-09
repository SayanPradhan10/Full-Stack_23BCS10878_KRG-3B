package com.resumebuilder.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller for handling home page and root path requests.
 */
@Controller
public class HomeController {
    
    /**
     * Displays the home page.
     * 
     * @return redirect to user registration page
     */
    @GetMapping("/")
    public String home() {
        return "redirect:/user/register";
    }
    
    /**
     * Displays the home page explicitly.
     * 
     * @return redirect to user registration page
     */
    @GetMapping("/home")
    public String showHome() {
        return "redirect:/user/register";
    }
}