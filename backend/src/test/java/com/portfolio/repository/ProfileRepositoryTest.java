package com.portfolio.repository;

import com.portfolio.entity.Profile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class ProfileRepositoryTest {

    @Autowired
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("Deve retornar o primeiro perfil disponível com sucesso quando houver registros válidos")
    void shouldFindFirstAvailableProfileSuccessfully() {
        // CORRIGIDO: Limpa qualquer dado residual inserido por migrations ou execuções anteriores na sua máquina
        profileRepository.deleteAll();

        // Arrange
        Profile profile1 = Profile.builder()
                .name("João Inativo")
                .title("Desenvolvedor")
                .email("inativo@dev.com")
                .available(false)
                .build();

        Profile profile2 = Profile.builder()
                .name("João Silva")
                .title("Tech Lead")
                .email("joao@dev.com")
                .available(true)
                .build();

        Profile profile3 = Profile.builder()
                .name("Alice Souza")
                .title("Senior Dev")
                .email("alice@dev.com")
                .available(true)
                .build();

        profileRepository.saveAll(List.of(profile1, profile2, profile3));

        // Act
        Optional<Profile> result = profileRepository.findFirstByAvailableTrue();

        // Assert
        assertTrue(result.isPresent());
        assertEquals("João Silva", result.get().getName());
        assertEquals("Tech Lead", result.get().getTitle());
    }

    @Test
    @DisplayName("Deve retornar um Optional vazio quando nenhum perfil estiver marcado como disponível")
    void shouldReturnEmptyOptionalWhenNoProfileIsAvailable() {
        // CORRIGIDO: Limpa dados residuais para garantir o isolamento total do cenário de retorno vazio
        profileRepository.deleteAll();

        // Arrange
        Profile profile = Profile.builder()
                .name("João Silva")
                .title("Tech Lead")
                .email("joao@dev.com")
                .available(false)
                .build();

        profileRepository.save(profile);

        // Act
        Optional<Profile> result = profileRepository.findFirstByAvailableTrue();

        // Assert
        assertTrue(result.isEmpty());
    }
}
