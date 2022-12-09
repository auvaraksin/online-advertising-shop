package ru.skypro.homework.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "ads")
public class Ads {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity author;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @JsonIgnore
    @OneToMany(mappedBy = "ads", fetch = FetchType.EAGER)
    private List<Image> images;

    public Image getLastImage() {
        return ((images == null) || (images.size()) == 0) ? null : images.get(images.size() - 1);
    }
}