package com.example.demo.models;

// import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
// import jakarta.persistence.ManyToOne;
// import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long userId;
    @Column(length = 30, nullable = false)
    private String firstName;
    @Column(length = 30, nullable = false)
    private String lastName;
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    // @Column(length = 10, nullable = false)
    // private String phoneNumber;
    // @Column(length = 100, nullable = false)
    // private String address;
    // private String avatar;
    // @Column(nullable = true)
    // private LocalDate dob;
    // @Column(length = 20, nullable = false)
    private String password;

    // @JsonIgnore
    // @ManyToOne(fetch = FetchType.EAGER)
    // @JoinColumn(name = "role_id", nullable = false)
    // private Role role;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    // @JsonIgnore
    // @OneToMany(mappedBy = "user")
    // private List<News> news;

    // @JsonIgnore
    // @OneToMany(mappedBy = "user")
    // private List<Orders> ordersList;

    // @JsonIgnore
    // @OneToMany(mappedBy = "user")
    // private List<Reviews> reviewsList;
}
