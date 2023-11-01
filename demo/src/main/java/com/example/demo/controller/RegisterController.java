package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.requestDTO.UserDTO;
import com.example.demo.service.UserServiceInterface;

import jakarta.validation.Valid;

@Controller
public class RegisterController {
    private UserServiceInterface userServiceInterface;

    public RegisterController(UserServiceInterface userService) {
        this.userServiceInterface = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);
        return "authenticates/register";
    }

    @PostMapping("/register/save")
    public String createAccount(@Valid @ModelAttribute("user") UserDTO userDTO,
            BindingResult bindingResult,
            Model model) {

        boolean checkExistedEmail = this.userServiceInterface.checkExistEmail(userDTO.getEmail());
        if (checkExistedEmail) {
            bindingResult.rejectValue("email", "409", "This Email is already registed");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userDTO);
            return "authenticates/register";
        }

        this.userServiceInterface.saveUser(userDTO);
        return "redirect:/register?success";
    }
}
