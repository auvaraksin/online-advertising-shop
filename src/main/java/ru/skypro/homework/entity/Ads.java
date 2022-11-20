package ru.skypro.homework.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity(name = "Ads")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @SequenceGenerator(
            name = "ads_sequence",
            sequenceName = "ads_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ads_sequence"
    )
    @Column(
            name = "ads_id",
            updatable = false
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User author;

    @OneToMany(mappedBy = "ads", cascade = CascadeType.ALL)
    @Column(
            name = "ads_comment_ids",
            nullable = true
    )
    List<AdsComment> adsCommentList;

    @Column(
            name = "ads_image_url",
            nullable = false
    )
    private String image;

    @Column(
            name = "ads_price",
            nullable = false
    )
    private Integer price;

    @Column(
            name = "ads_title",
            nullable = false
    )
    private String title;

    @Column(
            name = "ads_description"
    )
    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ads ads = (Ads) o;
        return id.equals(ads.id)
                && author.equals(ads.author)
                && Objects.equals(adsCommentList, ads.adsCommentList)
                && image.equals(ads.image) && price.equals(ads.price)
                && title.equals(ads.title) && description.equals(ads.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, adsCommentList, image, price, title, description);
    }

}