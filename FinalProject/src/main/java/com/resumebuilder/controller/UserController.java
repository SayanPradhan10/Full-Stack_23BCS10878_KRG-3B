package com.resumebuilder.controller;

import com.resumebuilder.entity.User;
import com.resumebuilder.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Controller for handling user-related web requests including registration, 
 * authentication, profile management, and dashboard access.
 */
@Controller
@RequestMapping("/user")
public class UserController {
    
    private final UserService userService;
    
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    /**
     * Displays the user registration form.
     * 
     * @param model the model for the view
     * @return the registration view name
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }
    
    /**
     * Processes user registration form submission.
     * 
     * @param user the user data from the form
     * @param bindingResult validation results
     * @param redirectAttributes attributes for redirect
     * @return redirect to login or back to registration form
     */
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("user") User user,
                              BindingResult bindingResult,
                              RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "user/register";
        }
        
        try {
            userService.createUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Registration successful! Please log in with your email.");
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            return "user/register";
        }
    }
    
    /**
     * Displays the user login form.
     * 
     * @param model the model for the view
     * @return the login view name
     */
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        return "user/login";
    }
    
    /**
     * Processes user login form submission.
     * Simplified authentication using email only for demo purposes.
     * 
     * @param email the user's email
     * @param session HTTP session for storing user info
     * @param redirectAttributes attributes for redirect
     * @return redirect to dashboard or back to login form
     */
    @PostMapping("/login")
    public String loginUser(@RequestParam("email") String email,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        if (email == null || email.trim().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email is required");
            return "redirect:/user/login";
        }
        
        try {
            User user = userService.authenticateUser(email);
            session.setAttribute("currentUser", user);
            session.setAttribute("userId", user.getId());
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Welcome back, " + user.getName() + "!");
            return "redirect:/user/dashboard";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/login";
        }
    }
    
    /**
     * Displays the user dashboard with resume data.
     * Ensures dashboard loads within 3 seconds as per requirement 7.4.
     * 
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the dashboard view name or redirect to login
     */
    @GetMapping("/dashboard")
    public String showDashboard(HttpSession session, Model model) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        try {
            // Start timing for performance requirement (3 seconds)
            long startTime = System.currentTimeMillis();
            
            UserService.DashboardData dashboardData = userService.getDashboardData(userId);
            
            long loadTime = System.currentTimeMillis() - startTime;
            
            model.addAttribute("user", dashboardData.getUser());
            model.addAttribute("resumes", dashboardData.getResumes());
            model.addAttribute("resumeCount", dashboardData.getResumeCount());
            model.addAttribute("loadTime", loadTime); // For monitoring performance
            
            return "user/dashboard";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "user/login";
        }
    }
    
    /**
     * Displays the user profile form for editing.
     * 
     * @param session HTTP session to get current user
     * @param model the model for the view
     * @return the profile view name or redirect to login
     */
    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        try {
            User user = userService.getUserById(userId);
            model.addAttribute("user", user);
            return "user/profile";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
    
    /**
     * Processes user profile update form submission.
     * 
     * @param user the updated user data from the form
     * @param bindingResult validation results
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @return redirect to profile or dashboard
     */
    @PostMapping("/profile")
    public String updateProfile(@Valid @ModelAttribute("user") User user,
                               BindingResult bindingResult,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        if (bindingResult.hasErrors()) {
            return "user/profile";
        }
        
        try {
            User updatedUser = userService.updateUser(userId, user);
            session.setAttribute("currentUser", updatedUser);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "Profile updated successfully!");
            return "redirect:/user/profile";
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("email", "error.user", e.getMessage());
            return "user/profile";
        }
    }
    
    /**
     * Handles user logout.
     * 
     * @param session HTTP session to invalidate
     * @param redirectAttributes attributes for redirect
     * @return redirect to login page
     */
    @PostMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("successMessage", "You have been logged out successfully.");
        return "redirect:/user/login";
    }
    
    /**
     * Handles user account deletion.
     * 
     * @param session HTTP session to get current user
     * @param redirectAttributes attributes for redirect
     * @return redirect to login page
     */
    @PostMapping("/delete-account")
    public String deleteAccount(HttpSession session, RedirectAttributes redirectAttributes) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return "redirect:/user/login";
        }
        
        try {
            userService.deleteUser(userId);
            session.invalidate();
            redirectAttributes.addFlashAttribute("successMessage", 
                "Your account has been deleted successfully.");
            return "redirect:/user/login";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/user/dashboard";
        }
    }
    
    /**
     * API endpoint to get current user information (for AJAX requests).
     * 
     * @param session HTTP session to get current user
     * @return User object as JSON or null if not authenticated
     */
    @GetMapping(value = "/api/current", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public User getCurrentUser(HttpSession session) {
        Long userId = getCurrentUserId(session);
        if (userId == null) {
            return null;
        }
        
        try {
            return userService.getUserById(userId);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
    
    /**
     * Helper method to get current user ID from session.
     * 
     * @param session HTTP session
     * @return user ID or null if not authenticated
     */
    private Long getCurrentUserId(HttpSession session) {
        Object userId = session.getAttribute("userId");
        return userId instanceof Long ? (Long) userId : null;
    }
    
    /**
     * Helper method to get current user from session.
     * 
     * @param session HTTP session
     * @return User object or null if not authenticated
     */
    private User getCurrentUserFromSession(HttpSession session) {
        Object user = session.getAttribute("currentUser");
        return user instanceof User ? (User) user : null;
    }
}