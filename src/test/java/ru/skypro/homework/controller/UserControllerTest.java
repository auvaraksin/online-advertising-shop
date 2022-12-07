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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dtos.NewPasswordDto;
import ru.skypro.homework.entities.Avatar;
import ru.skypro.homework.entities.UserEntity;
import ru.skypro.homework.repositories.AvatarRepository;
import ru.skypro.homework.repositories.UserRepository;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.skypro.homework.constants.SecurityConstantsTest.*;
import static ru.skypro.homework.constants.DtoConstantsTest.*;
import static ru.skypro.homework.constants.EntityConstantsTest.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    UserRepository userRepository;

    @MockBean
    AvatarRepository avatarRepository;

    @MockBean
    UserDetailsManager manager;

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenGetMe() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));

        mockMvc.perform(get("http://localhost:3000/users/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(USER_DTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(USER_DTO.getLastName()))
                .andExpect(jsonPath("$.id").value(USER_DTO.getId()))
                .andExpect(jsonPath("$.email").value(USER_DTO.getEmail()))
                .andExpect(jsonPath("$.phone").value(USER_DTO.getPhone()));
    }

    @Test
    void returnUnauthorizedWhenGetMe() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        mockMvc.perform(get("http://localhost:3000/users/me"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenUpdateUserData() throws Exception {
        final Long id = 1L;

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(userRepository.save(any(UserEntity.class))).thenReturn(USER);

        mockMvc.perform(patch("http://localhost:3000/users/me", id)
                        .content(objectMapper.writeValueAsString(USER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(USER_DTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(USER_DTO.getLastName()))
                .andExpect(jsonPath("$.id").value(USER_DTO.getId()))
                .andExpect(jsonPath("$.email").value(USER_DTO.getEmail()))
                .andExpect(jsonPath("$.phone").value(USER_DTO.getPhone()));
    }

    @Test
    void returnUnauthorizedWhenUpdateUserData() throws Exception {
        final Long id = 1L;

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(userRepository.save(any(UserEntity.class))).thenReturn(USER);

        mockMvc.perform(patch("http://localhost:3000/users/me", id)
                        .content(objectMapper.writeValueAsString(USER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnNotFoundWhenUpdateUserData() throws Exception {
        final Long id = 1L;

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(userRepository.save(any(UserEntity.class))).thenReturn(USER);

        mockMvc.perform(patch("http://localhost:3000/users/me", id)
                        .content(objectMapper.writeValueAsString(USER_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenUpdateUserImage() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(avatarRepository.save(any(Avatar.class))).thenReturn(AVATAR_IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/users/me/image");
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isOk());
    }

    @Test
    void returnUnauthorizedWhenUpdateUserImage() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(avatarRepository.save(any(Avatar.class))).thenReturn(AVATAR_IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/users/me/image");
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnNotFoundWhenUpdateUserImage() throws Exception {
        final MockMultipartFile file = new MockMultipartFile("image", "image.jpeg", "image/jpeg", new byte[] { 0x00 });

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(avatarRepository.save(any(Avatar.class))).thenReturn(AVATAR_IMAGE);

        MockMultipartHttpServletRequestBuilder builder = MockMvcRequestBuilders.multipart("http://localhost:3000/users/me/image");
        builder.with(request -> { request.setMethod("PATCH"); return request; });

        mockMvc.perform(builder.file(file))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenGetUserImage() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(avatarRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(AVATAR_IMAGE));

        mockMvc.perform(get("http://localhost:3000/users/me/image"))
                .andExpect(status().isOk());
    }

    @Test
    void returnUnauthorizedWhenGetUserImage() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(avatarRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(AVATAR_IMAGE));

        mockMvc.perform(get("http://localhost:3000/users/me/image"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnNotFoundWhenGetUserImage() throws Exception {
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(USER));
        when(avatarRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        mockMvc.perform(get("http://localhost:3000/users/me/image"))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnOkWhenUpdatePassword() throws Exception {
        when(manager.loadUserByUsername(any(String.class))).thenReturn(SECURITY_USER_DETAILS);
        doNothing().when(manager).updateUser(any(UserDetails.class));

        mockMvc.perform(post("http://localhost:3000/users/set_password")
                        .content(objectMapper.writeValueAsString(NEW_PASSWORD_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.currentPassword").value(NEW_PASSWORD_DTO.getCurrentPassword()))
                .andExpect(jsonPath("$.newPassword").value(NEW_PASSWORD_DTO.getNewPassword()));
    }

    @Test
    void returnUnauthorizedWhenUpdatePassword() throws Exception {
        when(manager.loadUserByUsername(any(String.class))).thenReturn(SECURITY_USER_DETAILS);
        doNothing().when(manager).updateUser(any(UserDetails.class));

        mockMvc.perform(post("http://localhost:3000/users/set_password")
                        .content(objectMapper.writeValueAsString(NEW_PASSWORD_DTO))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnForbiddenWhenUpdatePasswordWithIncorrectPassword() throws Exception {
        final NewPasswordDto incorrectNewPasswordDto = new NewPasswordDto();
        incorrectNewPasswordDto.setCurrentPassword(SECURITY_USER_PASSWORD + "-incorrect");
        incorrectNewPasswordDto.setNewPassword(NEW_PASSWORD_DTO.getNewPassword());

        when(manager.loadUserByUsername(any(String.class))).thenReturn(SECURITY_USER_DETAILS);
        doNothing().when(manager).updateUser(any(UserDetails.class));

        mockMvc.perform(post("http://localhost:3000/users/set_password")
                        .content(objectMapper.writeValueAsString(incorrectNewPasswordDto))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void sreturnOkWhenGetUserById() throws Exception {
        final Long id = 1L;

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(USER));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:3000/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value(USER_DTO.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(USER_DTO.getLastName()))
                .andExpect(jsonPath("$.id").value(USER_DTO.getId()))
                .andExpect(jsonPath("$.email").value(USER_DTO.getEmail()))
                .andExpect(jsonPath("$.phone").value(USER_DTO.getPhone()));
    }

    @Test
    void returnUnauthorizedWhenGetUserById() throws Exception {
        final Long id = 1L;

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(USER));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:3000/users/{id}", id))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(username = SECURITY_USER_NAME, password = SECURITY_USER_PASSWORD, roles = SECURITY_USER_ROLE)
    void returnNotFoundWhenGetUserById() throws Exception {
        final Long id = 1L;

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:3000/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = SECURITY_ADMIN_NAME, password = SECURITY_ADMIN_PASSWORD, roles = SECURITY_ADMIN_ROLE)
    void returnForbiddenWhenGetUserById() throws Exception {
        final Long id = 1L;

        when(userRepository.findById(any(Long.class))).thenReturn(Optional.of(USER));

        mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:3000/users/{id}", id))
                .andExpect(status().isForbidden());
    }
}