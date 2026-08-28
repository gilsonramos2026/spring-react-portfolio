package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.CertificationRequest;
import com.portfolio.dto.response.CertificationResponse;
import com.portfolio.entity.Certification;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.CertificationMapper;
import com.portfolio.repository.CertificationRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para inicializar os mocks nesta classe de teste
class AdminCertificationServiceImplTest {

    @Mock
    private CertificationRepository certRepo; // Cria um mock do repositório para isolar as operações de banco de dados administrativo

    @Mock
    private CertificationMapper mapper; // Cria um mock do mapper responsável por aplicar dados de requisições e converter entidades em DTOs

    @InjectMocks
    private AdminCertificationServiceImpl certService; // Instancia o serviço real administrativo injetando os mocks acima nele

    @Test
    @DisplayName("Deve retornar uma lista com todas las certificações")
    void shouldReturnAllCertifications() {
        // Arrange
        Certification c = Certification.builder().id(1L).name("Java").build();
        CertificationResponse r = CertificationResponse.builder().id(1L).name("Java").build();

        // Configura o repositório para retornar a listagem geral independente da flag active (comum em painéis admin)
        when(certRepo.findAll()).thenReturn(List.of(c));
        when(mapper.toResponse(c)).thenReturn(r);

        // Act
        List<CertificationResponse> result = certService.getAllCertifications();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(certRepo, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve salvar uma nova certificação com flag active como TRUE")
    void shouldCreateCertificationSuccessfully() {
        // Arrange
        CertificationRequest request = CertificationRequest.builder().name("AWS").build();
        CertificationResponse response = CertificationResponse.builder().id(1L).name("AWS").build();

        // Usa o ArgumentCaptor para interceptar o objeto Certification enviado ao repositório no momento do save
        ArgumentCaptor<Certification> captor = ArgumentCaptor.forClass(Certification.class);
        when(certRepo.save(captor.capture())).thenAnswer(invocation -> invocation.getArgument(0));
        when(mapper.toResponse(any(Certification.class))).thenReturn(response);

        // Act
        CertificationResponse result = certService.createCertification(request);

        // Assert
        assertNotNull(result);
        Certification saved = captor.getValue();
        assertTrue(saved.getActive()); // Valida a regra de negócio essencial: toda nova certificação nasce ativa por padrão (active=true)
        verify(mapper, times(1)).applyRequest(any(Certification.class), eq(request)); // Garante que o mapper preencheu a entidade com os dados do request
        verify(certRepo, times(1)).save(any(Certification.class));
    }

    @Test
    @DisplayName("Deve atualizar uma certificação existente com sucesso")
    void shouldUpdateCertificationSuccessfully() {
        // Arrange
        Long id = 1L;
        CertificationRequest request = CertificationRequest.builder().name("AWS v2").build();
        Certification c = Certification.builder().id(id).name("AWS").build();
        CertificationResponse response = CertificationResponse.builder().id(id).name("AWS v2").build();

        // Configura o repositório para encontrar o registro existente pelo ID
        when(certRepo.findById(id)).thenReturn(Optional.of(c));
        when(certRepo.save(c)).thenReturn(c);
        when(mapper.toResponse(c)).thenReturn(response);

        // Act
        CertificationResponse result = certService.updateCertification(id, request);

        // Assert
        assertNotNull(result);
        assertEquals("AWS v2", result.getName());
        verify(mapper, times(1)).applyRequest(c, request); // Garante que os novos dados do request foram aplicados na entidade encontrada
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException ao tentar atualizar um ID inexistente")
    void shouldThrowExceptionWhenUpdatingInexistentId() {
        // Arrange
        Long id = 99L;
        CertificationRequest request = CertificationRequest.builder().name("AWS").build();
        // Simula que o registro não foi encontrado no banco (retorna Optional vazio)
        when(certRepo.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        // Valida que a exceção de recurso não encontrado é lançada
        assertThrows(ResourceNotFoundException.class, () -> certService.updateCertification(id, request));
        // Garante que o método save nunca foi chamado, já que o ID não existe
        verify(certRepo, never()).save(any());
    }

    @Test
    @DisplayName("Deve aplicar soft delete alterando a flag active para FALSE")
    void shouldApplySoftDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        Certification c = Certification.builder().id(id).name("Java").active(true).build();

        // Configura o repositório para encontrar o registro ativo
        when(certRepo.findById(id)).thenReturn(Optional.of(c));
        when(certRepo.save(c)).thenReturn(c);

        // Act
        // Aciona o método de exclusão administrativa
        certService.deleteCertification(id);

        // Assert
        assertFalse(c.getActive()); // Valida o conceito de Soft Delete: o registro não é apagado fisicamente, apenas desativado (active vira false)
        verify(certRepo, times(1)).save(c); // Confirma que a entidade atualizada foi salva no banco
        verify(certRepo, never()).delete(any()); // Garante estritamente que o delete físico do banco NUNCA foi acionado
    }
}