package ru.skypro.homework.constants;

import ru.skypro.homework.entities.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collections;
import java.util.List;

public class EntityConstantsTest {
    public static final UserEntity USER;

    public static final Ads ADS;

    public static final AdsComment ADS_COMMENT;

    public static final Image IMAGE;

    public static final Avatar AVATAR_IMAGE;

    static {
        USER = new UserEntity();
        USER.setId(1L);
        USER.setFirstName("firstName");
        USER.setLastName("lastName");
        USER.setPhone("phone");
        USER.setUsername("user@mail.com");
        USER.setPassword("password");
        USER.setEnabled(true);

        ADS = new Ads();
        ADS.setId(1L);
        ADS.setAuthor(USER);
        ADS.setTitle("title");
        ADS.setDescription("description");
        ADS.setPrice(new BigDecimal(1000));
        List<Image> images = Collections.singletonList(new Image());
        images.get(0).setId(1L);
        byte[] imageBytes = new byte[] { 0x01, 0x02, 0x03 };
        images.get(0).setImage(imageBytes);
        ADS.setImages(images);

        ADS_COMMENT = new AdsComment();
        ADS_COMMENT.setId(1L);
        ADS_COMMENT.setAuthor(USER);
        ADS_COMMENT.setAds(ADS);
        ADS_COMMENT.setCreatedAt(LocalDateTime.of(2022, Month.JANUARY, 1, 12, 0, 0));
        ADS_COMMENT.setText("text");

        IMAGE = new Image();
        IMAGE.setId(1L);
        IMAGE.setImage(imageBytes);
        IMAGE.setAds(ADS);

        AVATAR_IMAGE = new Avatar();
        AVATAR_IMAGE.setUser(USER);
        AVATAR_IMAGE.setImage(imageBytes);
    }
}