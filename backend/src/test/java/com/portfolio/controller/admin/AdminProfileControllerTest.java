package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminProfileService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminProfileController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita filtros para isolar o comportamento do controlador
class AdminProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminProfileService profileService;

    // Beans de infraestrutura de segurança obrigatórios para o contexto subir de forma limpa
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e o perfil atualizado ao efetuar o Upsert com dados válidos")
    void shouldUpsertProfileSuccessfully() throws Exception {
        // Arrange
        ProfileRequest req = ProfileRequest.builder()
                .name("João Silva")
                .title("Desenvolvedor Java Senior")
                .bio("Especialista em arquitetura de microsserviços.")
                .email("joao@dev.com")
                .build();

        ProfileResponse resp = ProfileResponse.builder()
                .id(1L)
                .name("João Silva")
                .title("Desenvolvedor Java Senior")
                .build();

        when(profileService.upsertProfile(any(ProfileRequest.class))).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(put("/admin/profile")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.title").value("Desenvolvedor Java Senior"));

        verify(profileService, times(1)).upsertProfile(any(ProfileRequest.class));
    }

    @Test
    @DisplayName("Deve aceitar o arquivo binário via multipart/form-data e retornar status 200 com a nova URL do avatar")
    void shouldUploadAvatarSuccessfully() throws Exception {
        // Arrange
        // Simula o arquivo enviado pelo formulário do painel administrativo
        MockMultipartFile fakeFile = new MockMultipartFile(
                "file",
                "foto-perfil.png",
                MediaType.IMAGE_PNG_VALUE,
                "binario-imagem-falsa".getBytes()
        );

        ProfileResponse resp = ProfileResponse.builder()
                .id(1L)
                .name("João Silva")
                .avatarUrl("/uploads/avatars/nova-foto-gerada.png")
                .build();

        when(profileService.uploadAvatar(any())).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(multipart("/admin/profile/avatar")
                        .file(fakeFile))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.avatarUrl").value("/uploads/avatars/nova-foto-gerada.png"));

        verify(profileService, times(1)).uploadAvatar(any());
    }
}
