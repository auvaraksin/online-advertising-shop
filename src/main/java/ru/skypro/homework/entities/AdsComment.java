package ru.skypro.homework.entities;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "AdsComment")
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Table(name = "ads_comments")
public class AdsComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ads_id")
    private Ads ads;

    private LocalDateTime createdAt;
    private String text;

}