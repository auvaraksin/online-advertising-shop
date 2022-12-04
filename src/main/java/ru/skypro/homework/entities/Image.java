package ru.skypro.homework.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Objects;

@Entity(name = "Image")
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name = "images")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String filePath;
    private long fileSize;
    private String mediaType;
    @Lob
    private byte[] preview;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ads_id")
    private Ads ads;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return fileSize == image.fileSize
                && Objects.equals(id, image.id)
                && Objects.equals(filePath, image.filePath)
                && Objects.equals(mediaType, image.mediaType)
                && Arrays.equals(preview, image.preview)
                && Objects.equals(ads, image.ads);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, filePath, fileSize, mediaType, ads);
        result = 31 * result + Arrays.hashCode(preview);
        return result;
    }
}
