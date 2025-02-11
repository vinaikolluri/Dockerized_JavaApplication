package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.User;
import com.example.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    // Show registration form
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    // Handle registration form submission
    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // Check if the username or email is already taken
        if (userService.isUsernameTaken(user.getUsername())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username is already taken.");
            return "redirect:/register";
        }

        if (userService.isEmailTaken(user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email is already taken.");
            return "redirect:/register";
        }

        // Save the user
        userService.saveUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "Registration successful! Please log in.");
        return "redirect:/login"; // Redirect to the login page after registration
    }

    // Show login form
    @GetMapping("/login")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Handle login form submission
    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = userService.authenticateUser(user.getEmail(), user.getPassword());

        if (isAuthenticated) {
            redirectAttributes.addFlashAttribute("successMessage", "Logged in successfully!");
            return "redirect:/loginSuccess"; // Redirect to login success page after login
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials, please try again.");
            return "redirect:/login"; // Redirect to the login page
        }
    }

    // Show home page
    @GetMapping("/index")
    public String showHomePage(Model model) {
        return "index"; // Show the index page
    }

    // Show login success page
    @GetMapping("/loginSuccess")
    public String showLoginSuccessPage(Model model) {
        return "loginSuccess"; // Show the login success page
    }
}
