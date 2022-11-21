package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "User")
@Getter
@Setter
@ToString(exclude = {"password"})
@RequiredArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    @Column(
            name = "user_id",
            updatable = false
    )
    private Integer id;

    @Column(
            name = "user_first_name",
            nullable = false
    )
    private String firstName;

    @Column(
            name = "user_last_name",
            nullable = false
    )
    private String lastName;

    @Column(
            name = "user_email",
            nullable = false
    )
    private String email;

    @Column(
            name = "user_phone",
            nullable = true)
    private String phone;

    @Column(
            name = "user_password",
            nullable = false
    )
    private String password;

    @Column(
            name = "user_role",
            nullable = false
    )
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Column(
            name = "ads_ids",
            nullable = true
    )
    List<Ads> adsList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Column(
            name = "ads_comment_ids",
            nullable = true
    )
    List<AdsComment> adsCommentList;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id.equals(user.id)
                && firstName.equals(user.firstName)
                && lastName.equals(user.lastName)
                && email.equals(user.email)
                && Objects.equals(phone, user.phone)
                && password.equals(user.password)
                && role == user.role
                && Objects.equals(adsList, user.adsList)
                && Objects.equals(adsCommentList, user.adsCommentList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, phone, password, role, adsList, adsCommentList);
    }
}