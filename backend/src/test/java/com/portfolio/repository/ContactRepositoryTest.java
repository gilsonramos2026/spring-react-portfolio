package com.portfolio.repository;

import com.portfolio.entity.Contact;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ContactRepositoryTest {

    @Autowired
    private ContactRepository contactRepository;

    @Test
    @DisplayName("Deve retornar todos os contatos ordenados pela data de criação decrescente")
    void shouldFindAllContactsOrderedByCreatedAtDesc() {
        // Arrange - Preenchimento completo para respeitar as constraints do banco de dados
        Contact c1 = Contact.builder()
                .name("Alice")
                .email("alice@mail.com")
                .message("Mensagem de teste 1")
                .status("new")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();

        Contact c2 = Contact.builder()
                .name("Bob")
                .email("bob@mail.com")
                .message("Mensagem de teste 2")
                .status("read")
                .createdAt(LocalDateTime.now())
                .build();

        contactRepository.saveAll(List.of(c1, c2));

        // Act
        List<Contact> result = contactRepository.findAllByOrderByCreatedAtDesc();

        // Assert
        assertEquals(2, result.size());
        assertEquals("Bob", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());
    }

    @Test
    @DisplayName("Deve filtrar os contatos por status e manter a ordenação decrescente")
    void shouldFindContactsByStatusOrderedByCreatedAtDesc() {
        // Arrange - Preenchimento completo para respeitar as constraints do banco de dados
        Contact c1 = Contact.builder()
                .name("Alice")
                .email("alice@mail.com")
                .message("Mensagem de teste 1")
                .status("new")
                .createdAt(LocalDateTime.now().minusDays(2))
                .build();

        Contact c2 = Contact.builder()
                .name("Bob")
                .email("bob@mail.com")
                .message("Mensagem de teste 2")
                .status("read")
                .createdAt(LocalDateTime.now())
                .build();

        Contact c3 = Contact.builder()
                .name("Charlie")
                .email("charlie@mail.com")
                .message("Mensagem de teste 3")
                .status("new")
                .createdAt(LocalDateTime.now().minusDays(1))
                .build();

        contactRepository.saveAll(List.of(c1, c2, c3));

        // Act
        List<Contact> result = contactRepository.findByStatusOrderByCreatedAtDesc("new");

        // Assert
        assertEquals(2, result.size());
        assertEquals("Charlie", result.get(0).getName());
        assertEquals("Alice", result.get(1).getName());
    }

    @Test
    @DisplayName("Deve contar a quantidade exata de contatos com base em um status específico")
    void shouldCountContactsByStatusCorrectly() {
        // Arrange - CORRIGIDO: Inclusão de name, email e message em todas as instâncias para satisfazer as restrições NOT NULL do banco
        Contact c1 = Contact.builder().name("Alice").email("alice@mail.com").message("Mensagem 1").status("new").build();
        Contact c2 = Contact.builder().name("Bob").email("bob@mail.com").message("Mensagem 2").status("read").build();
        Contact c3 = Contact.builder().name("Charlie").email("charlie@mail.com").message("Mensagem 3").status("new").build();

        contactRepository.saveAll(List.of(c1, c2, c3));

        // Act
        long countNew = contactRepository.countByStatus("new");
        long countRead = contactRepository.countByStatus("read");

        // Assert
        assertEquals(2L, countNew);
        assertEquals(1L, countRead);
    }
}
