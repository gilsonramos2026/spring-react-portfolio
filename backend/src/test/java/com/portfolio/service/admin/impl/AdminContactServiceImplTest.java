package com.portfolio.service.admin.impl;

import com.portfolio.dto.response.ContactResponse;
import com.portfolio.entity.Contact;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ContactMapper;
import com.portfolio.repository.ContactRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito para gerenciar os mocks desta classe de teste
class AdminContactServiceImplTest {

    @Mock
    private ContactRepository contactRepo; // Cria um mock do repositório de contatos para simular as operações de banco de dados

    @Mock
    private ContactMapper mapper; // Cria um mock do conversor de entidades Contact para o DTO ContactResponse

    @InjectMocks
    private AdminContactServiceImpl contactService; // Instancia a classe de serviço real injetando os mocks acima nela

    @Test
    @DisplayName("Deve retornar mensagens filtradas por status quando o parâmetro status for preenchido")
    void shouldReturnContactsFilteredByStatus() {
        // Arrange (Configuração do Cenário)
        Contact c = Contact.builder().id(1L).status("new").build();
        ContactResponse r = ContactResponse.builder().id(1L).status("new").build();

        // Configura o repositório para retornar a lista filtrada quando o status específico "new" for solicitado
        when(contactRepo.findByStatusOrderByCreatedAtDesc("new")).thenReturn(List.of(c));
        when(mapper.toResponse(c)).thenReturn(r);

        // Act (Execução da Ação)
        List<ContactResponse> result = contactService.getContacts("new");

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("new", result.get(0).getStatus());
        verify(contactRepo, times(1)).findByStatusOrderByCreatedAtDesc("new"); // Garante que o método de busca por status foi chamado
        verify(contactRepo, never()).findAllByOrderByCreatedAtDesc(); // Garante que a listagem geral NÃO foi acionada
    }

    @Test
    @DisplayName("Deve retornar todas as mensagens da caixa de entrada quando o status for nulo ou vazio")
    void shouldReturnAllContactsWhenStatusIsNullOrEmpty() {
        // Arrange
        Contact c = Contact.builder().id(2L).status("read").build();
        ContactResponse r = ContactResponse.builder().id(2L).status("read").build();

        // Configura o repositório para retornar a listagem geral ordenada por data de criação
        when(contactRepo.findAllByOrderByCreatedAtDesc()).thenReturn(List.of(c));
        when(mapper.toResponse(c)).thenReturn(r);

        // Act
        // Testa o comportamento passando um valor nulo e também uma string preenchida apenas com espaços em branco (blank)
        List<ContactResponse> resultNull = contactService.getContacts(null);
        List<ContactResponse> resultBlank = contactService.getContacts("   ");

        // Assert
        assertNotNull(resultNull);
        assertNotNull(resultBlank);
        verify(contactRepo, times(2)).findAllByOrderByCreatedAtDesc(); // Confirma que a listagem geral foi chamada duas vezes (uma para o null e outra para o blank)
        verify(contactRepo, never()).findByStatusOrderByCreatedAtDesc(anyString()); // Garante que o filtro por status nunca foi chamado por engano
    }

    @Test
    @DisplayName("Deve atualizar o status da mensagem com sucesso")
    void shouldUpdateContactStatusSuccessfully() {
        // Arrange
        Long id = 1L;
        Contact c = Contact.builder().id(id).status("new").build();
        ContactResponse r = ContactResponse.builder().id(id).status("read").build();

        // Configura o repositório para encontrar o contato existente e persistir as alterações
        when(contactRepo.findById(id)).thenReturn(Optional.of(c));
        when(contactRepo.save(c)).thenReturn(c);
        when(mapper.toResponse(c)).thenReturn(r);

        // Act
        // Atualiza o status do contato para "read" (lido)
        ContactResponse result = contactService.updateContactStatus(id, "read");

        // Assert
        assertNotNull(result);
        assertEquals("read", result.getStatus());
        assertEquals("read", c.getStatus()); // Valida a mutação direta de estado na entidade recuperada antes de salvar
        verify(contactRepo, times(1)).save(c); // Confirma que o método de salvamento foi acionado
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar status de uma mensagem inexistente")
    void shouldThrowExceptionWhenMessageNotFound() {
        // Arrange
        Long id = 99L;
        // Simula que a mensagem não foi encontrada no banco
        when(contactRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que a exceção customizada é lançada ao tentar atualizar uma mensagem fantasma
        assertThrows(ResourceNotFoundException.class, () -> contactService.updateContactStatus(id, "read"));
        verify(contactRepo, never()).save(any()); // Garante que o save nunca foi chamado
    }

    @Test
    @DisplayName("Deve retornar a quantidade exata de novas mensagens não lidas")
    void shouldCountNewContactsSuccessfully() {
        // Arrange
        // Configura o repositório para retornar um valor numérico simulando o total de mensagens novas
        when(contactRepo.countByStatus("new")).thenReturn(5L);

        // Act
        long count = contactService.countNewContacts();

        // Assert
        assertEquals(5L, count); // Valida se o contador exato foi retornado para o dashboard
        verify(contactRepo, times(1)).countByStatus("new"); // Confirma a chamada da query de contagem
    }
}