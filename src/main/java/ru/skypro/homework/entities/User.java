package ru.skypro.homework.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "User")
@Getter
@Setter
@EqualsAndHashCode
@ToString(exclude = {"password"})
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String password;
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "authority_id", referencedColumnName = "id")
    private Authorities authorities;

    @Column(name = "username")
    private String email;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Column(name = "ads_id")
    List<Ads> adsList;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    @Column(name = "ads_comment_id")
    List<AdsComment> adsCommentList;

}