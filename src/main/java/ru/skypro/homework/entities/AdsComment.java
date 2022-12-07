package ru.skypro.homework.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ads_comments")
public class AdsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @ManyToOne
    @JoinColumn(name = "id_ads")
    private Ads ads;

    @Column(name = "comment_time")
    private LocalDateTime createdAt;

    @Column(name = "comment_text")
    @NotNull
    private String text;
}