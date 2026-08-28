package com.portfolio.service.publicapi.impl; // Define o pacote do teste, espelhando a estrutura do código de produção

import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.entity.Profile;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProfileMapper;
import com.portfolio.repository.ProfileRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith; // Importa a extensão para habilitar o Mockito no JUnit 5
import org.mockito.InjectMocks; // Importa a anotação para injetar os mocks automaticamente na classe testada
import org.mockito.Mock; // Importa a anotação para criar objetos simulados (falsos)
import org.mockito.junit.jupiter.MockitoExtension; // Habilita o uso do Mockito com JUnit 5

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito antes de rodar os testes desta classe
public class PublicProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepo; // Cria um mock (simulação) do repositório para não acessar o banco real

    @Mock
    private ProfileMapper mapper; // Cria um mock do conversor de entidades para DTO

    @InjectMocks
    private PublicProfileServiceImpl profileService; // Instancia a classe real de serviço injetando os dois mocks acima nela

    @Test // Informa ao JUnit que este método é um caso de teste unitário
    @DisplayName("Deve retornar ProfileResponse com sucesso quando houver um perfil ativo cadastrado") // Dá um nome descritivo amigável para o relatório de testes
    void shouldReturnProfileResponseWhenActiveProfileExists() {
        // Arrange (Configuração do Cenário)
        // Cria um objeto Profile falso preenchido via Builder para simular o retorno do banco
        Profile profileMock = Profile.builder()
                .id(1L)
                .name("João Silva")
                .title("Desenvolvedor Full Stack")
                .available(true)
                .build();

        // Cria o DTO de resposta esperado que o Mapper deve devolver
        ProfileResponse responseMock = ProfileResponse.builder()
                .id(1L)
                .name("João Silva")
                .title("Desenvolvedor Full Stack")
                .build();

        // Define o comportamento do mock: quando chamar findFirstByAvailableTrue(), retorne o profileMock embrulhado em Optional
        when(profileRepo.findFirstByAvailableTrue()).thenReturn(Optional.of(profileMock));
        // Define o comportamento do mapper: quando converter o profileMock, retorne o responseMock
        when(mapper.toResponse(profileMock)).thenReturn(responseMock);

        // Act (Execução da Ação)
        // Executa o método real que estamos testando no serviço
        ProfileResponse result = profileService.getProfile();

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que o resultado não é nulo
        assertEquals("João Silva",  result.getName()); // Valida se o nome está correto
        assertEquals("Desenvolvedor Full Stack", result.getTitle()); // Valida se o cargo está correto

        // Verifica se o método do repositório foi chamado exatamente 1 vez
        verify(profileRepo, times(1)).findFirstByAvailableTrue();
        // Verifica se o método de mapeamento foi chamado exatamente 1 vez com o objeto correto
        verify(mapper, times(1)).toResponse(profileMock);
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando nenhum perfil ativo for encontrado")
    void shouldThrowExceptionWhenNoActiveProfileFound() {
        // Arrange
        // Configura o repositório para retornar Optional vazio (simulando que não há perfil ativo)
        when(profileRepo.findFirstByAvailableTrue()).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que ao chamar o método, a exceção específica ResourceNotFoundException é lançada
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,  () -> {
            profileService.getProfile();
        });

        // Valida se a mensagem de erro da exceção é exatamente a esperada
        assertEquals("Perfil profissional ativo não configurado no sistema", exception.getMessage());

        // Garante que o repositório foi consultado 1 vez
        verify(profileRepo, times(1)).findFirstByAvailableTrue();

        // Garante que o mapper nem chegou a ser acionado, já que a exceção foi lançada antes
        verifyNoInteractions(mapper);
    }
}