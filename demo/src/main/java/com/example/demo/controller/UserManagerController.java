package com.example.demo.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.models.User;
import com.example.demo.requestDTO.UserDTO;
import com.example.demo.requestDTO.UserUpdateDTO;
import com.example.demo.responeDTO.UserResponseDTO;
import com.example.demo.service.UserServiceInterface;

import jakarta.validation.Valid;

@Controller
public class UserManagerController {

    private UserServiceInterface userServiceInterface;

    public UserManagerController(UserServiceInterface userServiceInterface) {
        this.userServiceInterface = userServiceInterface;
    }

    @GetMapping("/user")
    public String index(Model model) {
        UserDTO user = new UserDTO();
        model.addAttribute("user", user);

        return "pages/usermanager";
    }

    @GetMapping("/user/load-data")
    public String getUsersDataList(Model model, @RequestParam(name = "pageNumber", defaultValue = "1") Integer page,
            @RequestParam(name = "pageSize", defaultValue = "5") Integer size) {
        // int currentPage = page.orElse(1);
        // int pageSize = size.orElse(5);
        Page<UserResponseDTO> userPage = this.userServiceInterface
                .getUserData(PageRequest.of(page - 1, size));

        model.addAttribute("users", userPage);

        int totalPages = userPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        return "pages/modal/user_table";
    }

    @PostMapping("/user/save")
    public ResponseEntity<String> save(
            @Valid @ModelAttribute("user") UserDTO userDTO,
            BindingResult result,
            Model model) {
        User checkExistedEmail = this.userServiceInterface.findUserByEmail(userDTO.getEmail());

        if (checkExistedEmail != null) {
            result.rejectValue("email", "409", "This Email is already registed");
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDTO);
            return ResponseEntity.ok("error data");
        }

        this.userServiceInterface.saveUser(userDTO);

        return ResponseEntity.ok("create successfully");
    }

    @PostMapping("user/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO,
            Model model,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", userUpdateDTO);
            return ResponseEntity.ok("error data");
        }
        this.userServiceInterface.updateUser(userUpdateDTO);
        System.out.println(this.userServiceInterface.updateUser(userUpdateDTO));
        return ResponseEntity.ok("create successfully");
    }

    @PostMapping("user/delete")
    public ResponseEntity<String> updateUser(@RequestParam("userId") Long userId) {

        this.userServiceInterface.deleteUser(userId);

        return ResponseEntity.ok("create successfully");
    }

    @PostMapping("user/search")
    public String search(@RequestParam("keyword") String keyWord, Model model) {
        List<UserResponseDTO> dataSearch = this.userServiceInterface.getDataSearch(keyWord);
        if (dataSearch.isEmpty()) {
            model.addAttribute("errorMessage", "DATA NOT FOUND !");
            return "pages/modal/user_table";
        }
        model.addAttribute("users", dataSearch);
        return "pages/modal/user_table";
    }
}
