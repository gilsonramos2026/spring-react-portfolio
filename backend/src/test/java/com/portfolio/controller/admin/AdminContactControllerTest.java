package com.portfolio.controller.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.portfolio.dto.request.ContactStatusRequest;
import com.portfolio.dto.response.ContactResponse;
import com.portfolio.security.AdminAuthFilter;
import com.portfolio.security.RateLimitFilter;
import com.portfolio.security.SecurityErrorResolver;
import com.portfolio.service.admin.AdminContactService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminContactController.class)
@AutoConfigureMockMvc(addFilters = false) // Desabilita os filtros de segurança para isolar o comportamento das rotas
class AdminContactControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AdminContactService contactService;

    // Beans de infraestrutura obrigatórios para subir o ApplicationContext do WebMvcTest
    @MockBean
    private SecurityErrorResolver securityErrorResolver;

    @MockBean
    private AdminAuthFilter adminAuthFilter;

    @MockBean
    private RateLimitFilter rateLimitFilter;

    @Test
    @DisplayName("Deve retornar status 200 e filtrar contatos por status quando o parâmetro for enviado")
    void shouldListContactsFilteredByStatusSuccessfully() throws Exception {
        // Arrange
        ContactResponse resp = ContactResponse.builder()
                .id(1L)
                .name("Alice")
                .email("alice@mail.com")
                .status("new")
                .build();
        when(contactService.getContacts("new")).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/admin/contacts")
                        .param("status", "new"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].status").value("new"))
                .andExpect(jsonPath("$[0].name").value("Alice"));

        verify(contactService, times(1)).getContacts("new");
    }

    @Test
    @DisplayName("Deve retornar status 200 e listar todos os contatos quando o parâmetro status for omitido")
    void shouldListAllContactsWhenStatusParamIsMissing() throws Exception {
        // Arrange
        ContactResponse resp = ContactResponse.builder().id(2L).name("Bob").status("read").build();
        when(contactService.getContacts(null)).thenReturn(List.of(resp));

        // Act & Assert
        mockMvc.perform(get("/admin/contacts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(2L))
                .andExpect(jsonPath("$[0].status").value("read"));

        verify(contactService, times(1)).getContacts(null);
    }

    @Test
    @DisplayName("Deve retornar status 200 e atualizar o status da mensagem com sucesso (PATCH)")
    void shouldUpdateContactStatusSuccessfully() throws Exception {
        // Arrange
        Long targetId = 1L;
        ContactStatusRequest req = new ContactStatusRequest();
        // Nota: Certifique-se de que o campo 'status' do seu ContactStatusRequest aceita este valor ou use um válido
        req.setStatus("read");

        ContactResponse resp = ContactResponse.builder()
                .id(targetId)
                .name("Alice")
                .status("read")
                .build();

        when(contactService.updateContactStatus(eq(targetId), anyString())).thenReturn(resp);

        // Act & Assert
        mockMvc.perform(patch("/admin/contacts/{id}/status", targetId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(targetId))
                .andExpect(jsonPath("$.status").value("read"));

        verify(contactService, times(1)).updateContactStatus(eq(targetId), eq("read"));
    }

    @Test
    @DisplayName("Deve retornar status 200 e a contagem exata de novos contatos não lidos")
    void shouldCountNewContactsSuccessfully() throws Exception {
        // Arrange
        when(contactService.countNewContacts()).thenReturn(15L);

        // Act & Assert
        mockMvc.perform(get("/admin/contacts/count-new"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(15L));

        verify(contactService, times(1)).countNewContacts();
    }
}
