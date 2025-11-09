package com.resumebuilder.controller;

import com.resumebuilder.entity.Resume;
import com.resumebuilder.entity.User;
import com.resumebuilder.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for UserController endpoints.
 * Tests web layer functionality including request/response handling and validation.
 */
@WebMvcTest(UserController.class)
class UserControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private UserService userService;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private User testUser;
    private MockHttpSession session;
    
    @BeforeEach
    void setUp() {
        testUser = new User("test@example.com", "Test User");
        testUser.setId(1L);
        testUser.setPhone("123-456-7890");
        testUser.setAddress("123 Test St");
        
        session = new MockHttpSession();
        session.setAttribute("userId", 1L);
        session.setAttribute("currentUser", testUser);
    }
    
    @Test
    void showRegistrationForm_ShouldReturnRegistrationView() throws Exception {
        mockMvc.perform(get("/user/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().attributeExists("user"));
    }
    
    @Test
    void registerUser_WithValidData_ShouldRedirectToLogin() throws Exception {
        when(userService.createUser(any(User.class))).thenReturn(testUser);
        
        mockMvc.perform(post("/user/register")
                .param("name", "Test User")
                .param("email", "test@example.com")
                .param("phone", "123-456-7890")
                .param("address", "123 Test St"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(userService).createUser(any(User.class));
    }
    
    @Test
    void registerUser_WithInvalidEmail_ShouldReturnRegistrationForm() throws Exception {
        when(userService.createUser(any(User.class)))
                .thenThrow(new IllegalArgumentException("Invalid email format"));
        
        mockMvc.perform(post("/user/register")
                .param("name", "Test User")
                .param("email", "invalid-email")
                .param("phone", "123-456-7890"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/register"))
                .andExpect(model().hasErrors());
    }
    
    @Test
    void showLoginForm_ShouldReturnLoginView() throws Exception {
        mockMvc.perform(get("/user/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"));
    }
    
    @Test
    void loginUser_WithValidEmail_ShouldRedirectToDashboard() throws Exception {
        when(userService.authenticateUser("test@example.com")).thenReturn(testUser);
        
        mockMvc.perform(post("/user/login")
                .param("email", "test@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/dashboard"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(userService).authenticateUser("test@example.com");
    }
    
    @Test
    void loginUser_WithInvalidEmail_ShouldRedirectToLoginWithError() throws Exception {
        when(userService.authenticateUser("invalid@example.com"))
                .thenThrow(new IllegalArgumentException("User not found"));
        
        mockMvc.perform(post("/user/login")
                .param("email", "invalid@example.com"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"))
                .andExpect(flash().attributeExists("errorMessage"));
    }
    
    @Test
    void showDashboard_WithAuthenticatedUser_ShouldReturnDashboardView() throws Exception {
        List<Resume> resumes = Arrays.asList(new Resume(), new Resume());
        UserService.DashboardData dashboardData = new UserService.DashboardData(testUser, resumes);
        
        when(userService.getDashboardData(1L)).thenReturn(dashboardData);
        
        mockMvc.perform(get("/user/dashboard").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("user/dashboard"))
                .andExpect(model().attributeExists("user", "resumes", "resumeCount"))
                .andExpect(model().attribute("resumeCount", 2));
        
        verify(userService).getDashboardData(1L);
    }
    
    @Test
    void showDashboard_WithoutAuthentication_ShouldRedirectToLogin() throws Exception {
        mockMvc.perform(get("/user/dashboard"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"));
        
        verify(userService, never()).getDashboardData(anyLong());
    }
    
    @Test
    void showProfile_WithAuthenticatedUser_ShouldReturnProfileView() throws Exception {
        when(userService.getUserById(1L)).thenReturn(testUser);
        
        mockMvc.perform(get("/user/profile").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("user/profile"))
                .andExpect(model().attributeExists("user"));
        
        verify(userService).getUserById(1L);
    }
    
    @Test
    void updateProfile_WithValidData_ShouldRedirectToProfile() throws Exception {
        User updatedUser = new User("updated@example.com", "Updated User");
        updatedUser.setId(1L);
        
        when(userService.updateUser(eq(1L), any(User.class))).thenReturn(updatedUser);
        
        mockMvc.perform(post("/user/profile").session(session)
                .param("name", "Updated User")
                .param("email", "updated@example.com")
                .param("phone", "987-654-3210"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/profile"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(userService).updateUser(eq(1L), any(User.class));
    }
    
    @Test
    void logout_ShouldInvalidateSessionAndRedirectToLogin() throws Exception {
        mockMvc.perform(post("/user/logout").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"))
                .andExpect(flash().attributeExists("successMessage"));
    }
    
    @Test
    void deleteAccount_WithAuthenticatedUser_ShouldDeleteAndRedirectToLogin() throws Exception {
        doNothing().when(userService).deleteUser(1L);
        
        mockMvc.perform(post("/user/delete-account").session(session))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/login"))
                .andExpect(flash().attributeExists("successMessage"));
        
        verify(userService).deleteUser(1L);
    }
    
    @Test
    void getCurrentUser_WithAuthenticatedUser_ShouldReturnUserJson() throws Exception {
        when(userService.getUserById(1L)).thenReturn(testUser);
        
        mockMvc.perform(get("/user/api/current").session(session))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
        
        verify(userService).getUserById(1L);
    }
    
    @Test
    void getCurrentUser_WithoutAuthentication_ShouldReturnNull() throws Exception {
        mockMvc.perform(get("/user/api/current"))
                .andExpect(status().isOk())
                .andExpect(content().string(""));
        
        verify(userService, never()).getUserById(anyLong());
    }
}