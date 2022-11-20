package ru.skypro.homework.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity(name = "AdsComment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "ads_comments")
public class AdsComment {
    @Id
    @SequenceGenerator(
            name = "ads_comment_sequence",
            sequenceName = "ads_comment_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ads_comment_sequence"
    )
    @Column(
            name = "ads_comment_id",
            updatable = false
    )
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User author;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "ads_id",
            nullable = false
    )
    private Ads ads;

    @Column(
            name = "ads_comment_created_time",
            nullable = false
    )
    private LocalDateTime createdAt;

    @Column(
            name = "ads_comment_text",
            nullable = false
    )
    private String text;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdsComment that = (AdsComment) o;
        return id.equals(that.id)
                && author.equals(that.author)
                && ads.equals(that.ads)
                && createdAt.equals(that.createdAt)
                && text.equals(that.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, ads, createdAt, text);
    }
}