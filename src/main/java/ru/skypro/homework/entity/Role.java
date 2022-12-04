package ru.skypro.homework.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Set;

@Entity
@Data
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique = true)
    private String roleName;
    @Transient
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}