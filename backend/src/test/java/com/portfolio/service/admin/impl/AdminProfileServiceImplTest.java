package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.ProfileRequest;
import com.portfolio.dto.response.ProfileResponse;
import com.portfolio.entity.Profile;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProfileMapper;
import com.portfolio.repository.ProfileRepository;
import com.portfolio.service.FileStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita a extensão do Mockito no JUnit 5 para inicializar e gerenciar os mocks automaticamente
class AdminProfileServiceImplTest {

    @Mock
    private ProfileRepository profileRepository; // Cria um mock do repositório para simular a tabela de perfil no banco de dados

    @Mock
    private ProfileMapper mapper; // Cria um mock do conversor responsável por aplicar dados da requisição e mapear entidades para DTOs

    @Mock
    private FileStorageService storage; // Cria um mock do serviço de armazenamento para simular upload e exclusão física de arquivos (como avatares)

    @InjectMocks
    private AdminProfileServiceImpl profileService; // Instancia o serviço real administrativo injetando todos os três mocks acima nele

    @Test
    @DisplayName("Deve atualizar o perfil existente com sucesso quando houver um registro ativo (Upsert - Update)")
    void shouldUpdateProfileWhenAlreadyExists() {
        // Arrange (Configuração do Cenário)
        ProfileRequest request = ProfileRequest.builder().name("João Alterado").build();
        Profile existingProfile = Profile.builder().id(1L).name("João Silva").available(true).build();
        ProfileResponse response = ProfileResponse.builder().id(1L).name("João Alterado").build();

        // Configura o repositório para retornar um perfil existente quando buscar o primeiro perfil disponível
        when(profileRepository.findFirstByAvailableTrue()).thenReturn(Optional.of(existingProfile));
        when(profileRepository.save(existingProfile)).thenReturn(existingProfile);
        when(mapper.toResponse(existingProfile)).thenReturn(response);

        // Act (Execução da Ação)
        // Chama o upsert informando novos dados
        ProfileResponse result = profileService.upsertProfile(request);

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        assertEquals("João Alterado", result.getName());
        verify(mapper, times(1)).applyRequest(existingProfile, request); // Garante que as alterações foram aplicadas sobre o registro que já existia
        verify(profileRepository, times(1)).save(existingProfile); // Confirma que o registro existente foi atualizado e salvo
    }

    @Test
    @DisplayName("Deve instanciar e salvar um novo perfil quando não houver registro no banco (Upsert - Create)")
    void shouldCreateNewProfileWhenNoneExists() {
        // Arrange
        ProfileRequest request = ProfileRequest.builder().name("Novo Usuário").build();
        ProfileResponse response = ProfileResponse.builder().id(1L).name("Novo Usuário").build();

        // Simula que a tabela de perfil está vazia (retorna Optional vazio)
        when(profileRepository.findFirstByAvailableTrue()).thenReturn(Optional.empty());

        // Utiliza o ArgumentCaptor para interceptar o novo objeto Profile instanciado internamente pelo serviço
        ArgumentCaptor<Profile> captor = ArgumentCaptor.forClass(Profile.class);
        when(profileRepository.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Profile.class))).thenReturn(response);

        // Act
        ProfileResponse result = profileService.upsertProfile(request);

        // Assert
        assertNotNull(result);
        Profile identityCreated = captor.getValue();
        assertNotNull(identityCreated); // Prova que o serviço instanciou um novo objeto Profile em memória por conta própria
        verify(mapper, times(1)).applyRequest(identityCreated, request); // Garante que o mapper preencheu o novo perfil com os dados do request
        verify(profileRepository, times(1)).save(identityCreated); // Confirma a inserção do novo registro no banco
    }

    @Test
    @DisplayName("Deve realizar upload do avatar, deletar o arquivo antigo local e salvar a nova URL")
    void shouldUploadAvatarAndCleanOldFileLocally() {
        // Arrange
        MultipartFile fileMock = mock(MultipartFile.class); // Cria um mock do arquivo enviado via requisição HTTP multipart
        Profile p = Profile.builder().id(1L).avatarUrl("/uploads/avatars/antigo.jpg").available(true).build();
        ProfileResponse r = ProfileResponse.builder().id(1L).avatarUrl("/uploads/avatars/novo.jpg").build();

        when(profileRepository.findFirstByAvailableTrue()).thenReturn(Optional.of(p));
        // Simula o salvamento do arquivo novo no disco, retornando o caminho gerado
        when(storage.store(fileMock, "avatars")).thenReturn("/uploads/avatars/novo.jpg");
        when(profileRepository.save(p)).thenReturn(p);
        when(mapper.toResponse(p)).thenReturn(r);

        // Act
        // Executa o upload do avatar
        ProfileResponse result = profileService.uploadAvatar(fileMock);

        // Assert
        assertNotNull(result);
        assertEquals("/uploads/avatars/novo.jpg", result.getAvatarUrl());
        verify(storage, times(1)).delete("/uploads/avatars/antigo.jpg"); // Valida uma regra de ouro de limpeza: o arquivo órfão antigo é apagado do disco para não ocupar espaço lixo
        verify(storage, times(1)).store(fileMock, "avatars"); // Confirma que o novo arquivo foi gravado na pasta "avatars"
        verify(profileRepository, times(1)).save(p); // Confirma que o perfil foi atualizado com a nova URL
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar enviar avatar sem um perfil cadastrado no banco")
    void shouldThrowExceptionOnAvatarUploadWhenProfileDoesNotExist() {
        // Arrange
        MultipartFile fileMock = mock(MultipartFile.class);
        // Simula que não existe nenhum perfil cadastrado no sistema
        when(profileRepository.findFirstByAvailableTrue()).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que o sistema impede o upload e lança a exceção caso o usuário tente trocar o avatar sem ter um perfil base
        assertThrows(ResourceNotFoundException.class, () -> profileService.uploadAvatar(fileMock));
        verifyNoInteractions(storage); // Garante estritamente que nenhuma operação de arquivo foi acionada se o perfil não existe
        verify(profileRepository, never()).save(any()); // Garante que nada foi salvo no banco
    }
}