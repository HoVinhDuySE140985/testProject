package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.models.Role;
import com.example.demo.models.User;
import com.example.demo.repository.RoleRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.requestDTO.PasswordDTO;
import com.example.demo.requestDTO.UserDTO;
import com.example.demo.requestDTO.UserUpdateDTO;
import com.example.demo.responeDTO.UserResponseDTO;

@Service
public class UserServiceImpl implements UserServiceInterface {

    private UserRepo userRepo;
    private RoleRepo roleRepo;
    private PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepo userRepo, RoleRepo roleRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.roleRepo = roleRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void saveUser(UserDTO userDTO) {
        User user = new User();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        // user.setPassword(userDTO.getPassword());
        user.setPassword(this.passwordEncoder.encode(userDTO.getPassword()));

        Role role = new Role();
        role.setName("ROLE_ADMIN");
        this.roleRepo.save(role);

        user.setRoles(Arrays.asList(role));
        this.userRepo.save(user);
    }

    @Override
    public boolean checkExistEmail(String email) {
        User user = this.userRepo.findUserByEmail(email);
        if (user != null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public User findUserByEmail(String Email) {
        return this.userRepo.findUserByEmail(Email);
    }

    @Override
    public boolean updatePassword(String email, PasswordDTO passwordDTO) {
        User user = this.userRepo.findUserByEmail(email);
        if (passwordDTO.getPassword().equals(passwordDTO.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDTO.getPassword()));
            this.userRepo.save(user);
            return true;
        }
        return false;
    }

    @Override
    public Page<UserResponseDTO> getUserData(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;

        List<User> users = this.userRepo.findAll();
        List<UserResponseDTO> userData = new ArrayList<>();
        for (User user : users) {
            UserResponseDTO data = UserResponseDTO.builder()
                    .id(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
            userData.add(data);
        }
        Collections.reverse(userData);
        List<UserResponseDTO> userInPage;

        if (userData.size() < startItem) {
            userInPage = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, userData.size());
            userInPage = userData.subList(startItem, toIndex);
        }
        Page<UserResponseDTO> userPage = new PageImpl<UserResponseDTO>(userInPage,
                PageRequest.of(currentPage, pageSize), userData.size());
        return userPage;
    }

    @Override
    public boolean deleteUser(Long userId) {
        User user = this.userRepo.getById(userId);
        if (user != null) {
            this.userRepo.delete(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(UserUpdateDTO userUpdateDTO) {
        User user = this.userRepo.getById(userUpdateDTO.getId());
        if (user != null) {
            user.setFirstName(userUpdateDTO.getNewFirstName());
            user.setLastName(userUpdateDTO.getNewLastName());
            this.userRepo.save(user);
            return true;

        }
        return false;
    }

    @Override
    public Page<UserResponseDTO> getDataSearch(String keyWord, Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<User> users = new ArrayList<>();
        if (keyWord == null || keyWord.isEmpty() || keyWord.isBlank()) {
            users = this.userRepo.findAll();
        } else {
            users = this.userRepo.findUserByKeyWord(keyWord);
        }
        List<UserResponseDTO> dataSearch = new ArrayList<>();
        for (User user : users) {
            UserResponseDTO data = UserResponseDTO.builder()
                    .id(user.getUserId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .build();
            dataSearch.add(data);
        }
        Collections.reverse(dataSearch);
        List<UserResponseDTO> userInPage;

        if (dataSearch.size() < startItem) {
            userInPage = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, dataSearch.size());
            userInPage = dataSearch.subList(startItem, toIndex);
        }
        Page<UserResponseDTO> userSearchPage = new PageImpl<UserResponseDTO>(userInPage,
                PageRequest.of(currentPage, pageSize), dataSearch.size());
        return userSearchPage;
    }
}
