package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

// import com.example.demo.models.User;
import com.example.demo.requestDTO.PasswordDTO;
import com.example.demo.service.UserServiceInterface;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class PasswordController {
    private UserServiceInterface userService;

    public PasswordController(UserServiceInterface userServiceInterface) {
        this.userService = userServiceInterface;
    }

    @GetMapping("/forgotpassword")
    public String forgotPassWord() {
        return "pages/forgotpassword";
    }

    @PostMapping("/forgotpassword/ischeck")
    public String processForgotPassWord(@RequestParam("email") String email, RedirectAttributes redirectAttributes,
            Model model) {
        boolean check = this.userService.checkExistEmail(email);
        if (check) {
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/recoverpassword";
        } else {
            // redirectAttributes.addFlashAttribute("email", email);
            model.addAttribute("error", "Email Is Not Exist !");
            return "pages/forgotpassword";
        }
    }

    @GetMapping("/recoverpassword")
    public String recoverpassword(@ModelAttribute("email") String email,
            HttpSession session,
            Model model) {
        session.setAttribute("email", email);
        PasswordDTO dto = new PasswordDTO();
        model.addAttribute("changePassword", dto);
        return "pages/recoverpassword";
    }

    @PostMapping("/recoverpassword/updatepassword")
    public String updatePassword(@Valid @ModelAttribute("changePassword") PasswordDTO passwordDTO,
            HttpSession session,
            Model model,
            BindingResult bindingResult) {
        String email = (String) session.getAttribute("email");
        boolean check = this.userService.updatePassword(email, passwordDTO);
        if (!check) {
            bindingResult.rejectValue("confirmPassword", "400", "Password confirm and Password arenot match !");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("pass", passwordDTO);
            return "pages/recoverpassword";
        }

        return "redirect:/recoverpassword?success";
    }
}
