package ru.skypro.homework.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.entities.Ads;
import ru.skypro.homework.entities.AdsComment;
import ru.skypro.homework.entities.Image;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.repositories.AdsCommentRepository;
import ru.skypro.homework.repositories.AdsRepository;
import ru.skypro.homework.repositories.ImageRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.constants.SecurityConstantsTest.*;
import static ru.skypro.homework.constants.DtoConstantsTest.*;
import static ru.skypro.homework.constants.EntityConstantsTest.*;

@SpringBootTest
@AutoConfigureMockMvc
class AdsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AdsRepository adsRepository;

    @MockBean
    AdsCommentRepository adsCommentRepository;

    @MockBean
    ImageRepository imageRepository;

    AdsControllerTest() {
    }

    /**
     * Тесты по работе контроллера с объявлениями.
     */

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenAddAds() throws Exception {
        byte[] fileContent = new byte[] { 0x00 };
        MockPart filePart = new MockPart("image", "image.jpeg", fileContent);

        byte[] adsContent = objectMapper.writeValueAsString(CREATE_ADS_DTO).getBytes(UTF_8);
        MockPart adsPart = new MockPart("properties", "createAdsDto", adsContent);
        adsPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(adsRepository.save(any(Ads.class))).thenReturn(ADS);
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        mockMvc.perform(multipart("http://localhost:3000/ads")
                        .part(adsPart)
                        .part(filePart)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.image").value(ADS_DTO.getImage()))
                .andExpect(jsonPath("$.pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.price").value(ADS_DTO.getPrice()))
                .andExpect(jsonPath("$.title").value(ADS_DTO.getTitle()));
    }

    @Test
    void returnUnauthorizedWhenAddAds() throws Exception {
        byte[] fileContent = new byte[] { 0x00 };
        MockPart filePart = new MockPart("image", "image.jpeg", fileContent);

        byte[] adsContent = objectMapper.writeValueAsString(CREATE_ADS_DTO).getBytes(UTF_8);
        MockPart adsPart = new MockPart("properties", "createAdsDto", adsContent);
        adsPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(adsRepository.save(any(Ads.class))).thenReturn(ADS);
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        mockMvc.perform(multipart("http://localhost:3000/ads")
                        .part(adsPart)
                        .part(filePart)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnNotFoundWhenAddAdsForMissingUser() throws Exception {
        byte[] fileContent = new byte[] { 0x00 };
        MockPart filePart = new MockPart("image", "image.jpeg", fileContent);

        byte[] adsContent = objectMapper.writeValueAsString(CREATE_ADS_DTO).getBytes(UTF_8);
        MockPart adsPart = new MockPart("properties", "createAdsDto", adsContent);
        adsPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(adsRepository.save(any(Ads.class))).thenReturn(ADS);
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        mockMvc.perform(multipart("http://localhost:3000/ads")
                        .part(adsPart)
                        .part(filePart)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenUserUpdateAds() throws Exception {
        final Long id = 1L;

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(adsRepository.save(any(Ads.class))).thenReturn(ADS);

        mockMvc.perform(patch("http://localhost:3000/ads/{id}", id)
                        .content(objectMapper.writeValueAsString(ADS_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.price").value(ADS_DTO.getPrice()));
    }

    @Test
    @WithMockUser(username = "undefined", password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnForbiddenWhenUndefinedUpdateAds() throws Exception {
        final Long id = 1L;

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(adsRepository.save(any(Ads.class))).thenReturn(ADS);

        mockMvc.perform(patch("http://localhost:3000/ads/{id}", id)
                        .content(objectMapper.writeValueAsString(ADS_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void returnUnauthorizedWhenUpdateAds() throws Exception {
        final Long id = 1L;

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(adsRepository.save(any(Ads.class))).thenReturn(ADS);

        mockMvc.perform(patch("http://localhost:3000/ads/{id}", id)
                        .content(objectMapper.writeValueAsString(ADS_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    void returnOkWhenGetAllAds() throws Exception {
        final List<Ads> adsList = new ArrayList<>();
        adsList.add(ADS);

        when(adsRepository.findAll()).thenReturn(adsList);

        mockMvc.perform(get("http://localhost:3000/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(adsList.size()))
                .andExpect(jsonPath("$.results[0].author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.results[0].pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.results[0].price").value(ADS_DTO.getPrice()));
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenGetAllAdsForMe() throws Exception {
        final List<Ads> adsList = new ArrayList<>();
        adsList.add(ADS);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(adsRepository.findAllByAuthor(any(UserEntity.class))).thenReturn(adsList);

        mockMvc.perform(get("http://localhost:3000/ads/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(adsList.size()))
                .andExpect(jsonPath("$.results[0].author").value(ADS_DTO.getAuthor()))
                .andExpect(jsonPath("$.results[0].pk").value(ADS_DTO.getPk()))
                .andExpect(jsonPath("$.results[0].price").value(ADS_DTO.getPrice()));
    }

    @Test
    void returnUnauthorizedWhenGetAllAdsForMe() throws Exception {
        final List<Ads> adsList = new ArrayList<>();
        adsList.add(ADS);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(adsRepository.findAllByAuthor(any(UserEntity.class))).thenReturn(adsList);

        mockMvc.perform(get("http://localhost:3000/ads/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnNotFoundWhenGetAllAdsForNotMe() throws Exception {
        final List<Ads> adsList = new ArrayList<>();
        adsList.add(ADS);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(adsRepository.findAllByAuthor(any(UserEntity.class))).thenReturn(adsList);

        mockMvc.perform(get("http://localhost:3000/ads/me"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenAuthorDeleteAds() throws Exception {
        final Long id = 1L;

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        doNothing().when(adsRepository).delete(any(Ads.class));

        mockMvc.perform(delete("http://localhost:3000/ads/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "undefined", password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnForbiddenWhenUndefinedDeleteAds() throws Exception {
        final Long id = 1L;

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        doNothing().when(adsRepository).delete(any(Ads.class));

        mockMvc.perform(delete("http://localhost:3000/ads/{id}", id))
                .andExpect(status().isForbidden());
    }

    @Test
    void returnUnauthorizedWhenDeleteAds() throws Exception {
        final Long id = 1L;

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        doNothing().when(adsRepository).delete(any(Ads.class));

        mockMvc.perform(delete("http://localhost:3000/ads/{id}", id))
                .andExpect(status().isUnauthorized());
    }

    /**
     * Тесты по работе контроллера с изображениями.
     */

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenUpdateImage() throws Exception {
        final Long ad = 1L;
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/ads/{ad}/image", ad);
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenAuthorUpdateImage() throws Exception {
        final Long id = 1L;
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/ads/{id}/image", id);
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "undefined", password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnForbiddenWhenUndefinedUpdateImage() throws Exception {
        final Long id = 1L;
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/ads/{id}/image", id);
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnUnauthorizedWhenUpdateImage() throws Exception {
        final Long id = 1L;
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/ads/{id}/image", id);
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void shouldReturnNotFoundWhenUpdateImageForMissingAds() throws Exception {
        final Long id = 1L;
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
        when(imageRepository.save(any(Image.class))).thenReturn(IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/ads/{id}/image", id);
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isNotFound());
    }

    /**
     * Тесты по работе контроллера с отзывами.
     */

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenAddComment() throws Exception {
        final Long adPk = 1L;

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(ADS_COMMENT);

        mockMvc.perform(post("http://localhost:3000/ads/{ad_pk}/comments", adPk)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text").value(ADS_COMMENT_DTO.getText()));
    }

    @Test
    void returnUnauthorizedWhenAddComment() throws Exception {
        final Long adPk = 1L;

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(adsRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(ADS));
        when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(ADS_COMMENT);

        mockMvc.perform(post("http://localhost:3000/ads/{ad_pk}/comments", adPk)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenAuthorUpdateComment() throws Exception {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        final Long adPk = 1L;
        final Long id = 1L;

        when(adsCommentRepository.findFirstByIdAndAdsId(any(Long.class), any(Long.class))).thenReturn(Optional.of(ADS_COMMENT));
        when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(ADS_COMMENT);

        mockMvc.perform(patch("http://localhost:3000/ads/{ad_pk}/comments/{id}", adPk, id)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pk").value(ADS_COMMENT_DTO.getPk()))
                .andExpect(jsonPath("$.text").value(ADS_COMMENT_DTO.getText()))
                .andExpect(jsonPath("$.author").value(ADS_COMMENT_DTO.getAuthor()))
                .andExpect(jsonPath("$.createdAt").value(ADS_COMMENT_DTO.getCreatedAt().format(dateTimeFormatter)));
    }

    @Test
    @WithMockUser(username = "undefined", password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnForbiddenWhenUndefinedUpdateComment() throws Exception {
        final Long adPk = 1L;
        final Long id = 1L;

        when(adsCommentRepository.findFirstByIdAndAdsId(any(Long.class), any(Long.class))).thenReturn(Optional.of(ADS_COMMENT));
        when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(ADS_COMMENT);

        mockMvc.perform(patch("http://localhost:3000/ads/{ad_pk}/comments/{id}", adPk, id)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    void returnUnauthorizedWhenUpdateComment() throws Exception {
        final Long adPk = 1L;
        final Long id = 1L;

        when(adsCommentRepository.findFirstByIdAndAdsId(any(Long.class), any(Long.class))).thenReturn(Optional.of(ADS_COMMENT));
        when(adsCommentRepository.save(any(AdsComment.class))).thenReturn(ADS_COMMENT);

        mockMvc.perform(patch("http://localhost:3000/ads/{ad_pk}/comments/{id}", adPk, id)
                        .content(objectMapper.writeValueAsString(ADS_COMMENT_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenAuthorDeleteComment() throws Exception {
        final Long adPk = 1L;
        final Long id = 1L;

        when(adsCommentRepository.findFirstByIdAndAdsId(any(Long.class), any(Long.class))).thenReturn(Optional.of(ADS_COMMENT));
        doNothing().when(adsCommentRepository).delete(any(AdsComment.class));

        mockMvc.perform(delete("http://localhost:3000/ads/{ad_pk}/comments/{id}", adPk, id))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "undefined", password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnForbiddenWhenUndefinedDeleteComment() throws Exception {
        final Long adPk = 1L;
        final Long id = 1L;

        when(adsCommentRepository.findFirstByIdAndAdsId(any(Long.class), any(Long.class))).thenReturn(Optional.of(ADS_COMMENT));
        doNothing().when(adsCommentRepository).delete(any(AdsComment.class));

        mockMvc.perform(delete("http://localhost:3000/ads/{ad_pk}/comments/{id}", adPk, id))
                .andExpect(status().isForbidden());
    }

    @Test
    void returnUnauthorizedWhenDeleteComment() throws Exception {
        final Long adPk = 1L;
        final Long id = 1L;

        when(adsCommentRepository.findFirstByIdAndAdsId(any(Long.class), any(Long.class))).thenReturn(Optional.of(ADS_COMMENT));
        doNothing().when(adsCommentRepository).delete(any(AdsComment.class));

        mockMvc.perform(delete("http://localhost:3000/ads/{ad_pk}/comments/{id}", adPk, id))
                .andExpect(status().isUnauthorized());
    }
}