package ru.skypro.homework.entities;

import lombok.*;

import javax.persistence.*;

@Entity(name = "Authorities")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Table(name = "authorities")

public class Authorities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String username;
    private String authority;

    @OneToOne(mappedBy = "authorities")
    private User user;
}
