package ru.skypro.homework.entities;

import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@DynamicInsert
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class UserEntity {

    public enum UserRole {
        USER,
        ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    @NotNull
    private String firstName;

    @Column(name = "last_name")
    @NotNull
    private String lastName;

    @NotNull
    private String phone;

    private String username;

    private String password;

    private Boolean enabled;

    @OneToOne
    @PrimaryKeyJoinColumn
    private Avatar avatar;
}