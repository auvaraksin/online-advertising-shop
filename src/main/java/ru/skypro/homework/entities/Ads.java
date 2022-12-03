package ru.skypro.homework.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Ads")
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL)
    @Column(name = "ads_comment_id")
    List<AdsComment> adsCommentList;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL)
    @Column(name = "imege_id")
    List<Image> images;

    private String image;
    private Integer price;
    private String title;
    private String description;

}