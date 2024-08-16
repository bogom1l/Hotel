//package com.tinqinacademy.hotel.persistence.model;
//
//import jakarta.persistence.*;
//import lombok.*;
//import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.annotations.UpdateTimestamp;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.UUID;
//
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@NoArgsConstructor
//@ToString
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @Column(name = "email", nullable = false)
//    private String email;
//
//    @Column(name = "password", nullable = false)
//    private String password;
//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;
//
//    @Column(name = "phone_number", nullable = false)
//    private String phoneNumber;
//
//    @Column(name = "birthdate", nullable = false)
//    private LocalDate birthdate;
//
//    @CreationTimestamp
//    @Column(name = "created_at")
//    private LocalDateTime createdAt;
//
//    @UpdateTimestamp
//    @Column(name = "updated_at")
//    private LocalDateTime updatedAt;
//}
