package com.example.demo.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.models.User;
import com.example.demo.requestDTO.PasswordDTO;
import com.example.demo.requestDTO.UserDTO;
import com.example.demo.requestDTO.UserUpdateDTO;
import com.example.demo.responeDTO.UserResponseDTO;

public interface UserServiceInterface {
    void saveUser(UserDTO userDTO);

    boolean checkExistEmail(String email);

    // User findUserById(Long id);

    User findUserByEmail(String Email);

    boolean updatePassword(String email, PasswordDTO passwordDTO);

    Page<UserResponseDTO> getUserData(Pageable pageable);

    boolean updateUser(UserUpdateDTO userUpdateDTO);

    boolean deleteUser(Long userId);

    Page<UserResponseDTO> getDataSearch(String keyWord, Pageable pageable);

}
