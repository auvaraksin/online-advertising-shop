package ru.skypro.homework.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "avatars")
@Getter
@Setter
@NoArgsConstructor
public class Avatar {

    @Id
    @Column(name = "id_user")
    private Long id;

    @MapsId
    @OneToOne(mappedBy = "avatar", fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private UserEntity user;

    private byte[] image;
}