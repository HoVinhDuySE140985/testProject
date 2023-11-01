package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.models.User;
import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {
    User findUserByEmail(String name);

    @Query("select u \n" +
            "From User u\n" +
            "where u.email Like %:keyWord% \n" +
            "order by u.email")
    List<User> findUserByKeyWord(String keyWord);
}
