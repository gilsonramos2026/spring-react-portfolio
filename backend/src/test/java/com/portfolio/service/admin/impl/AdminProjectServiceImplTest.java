package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.ProjectRequest;
import com.portfolio.dto.response.ProjectImageResponse;
import com.portfolio.dto.response.ProjectResponse;
import com.portfolio.entity.Project;
import com.portfolio.entity.ProjectImage;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.ProjectMapper;
import com.portfolio.repository.ProjectImageRepository;
import com.portfolio.repository.ProjectRepository;
import com.portfolio.service.FileStorageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para inicializar e gerenciar automaticamente os mocks nesta classe de teste
class AdminProjectServiceImplTest {

    @Mock private ProjectRepository projectRepo; // Cria um mock do repositório principal de projetos
    @Mock private ProjectImageRepository imageRepo; // Cria um mock do repositório secundário focado na galeria de imagens dos projetos
    @Mock private ProjectMapper mapper; // Cria um mock do conversor de entidades e requisições de projetos
    @Mock private FileStorageService storage; // Cria um mock do serviço de armazenamento em disco para salvar as imagens dos projetos

    @InjectMocks private AdminProjectServiceImpl projectService; // Instancia o serviço real injetando todos os quatro mocks acima nele

    @Test
    @DisplayName("Deve criar um projeto gerando um slug amigável e único automaticamente")
    void shouldCreateProjectWithGeneratedSlug() {
        // Arrange (Configuração do Cenário)
        ProjectRequest req = ProjectRequest.builder().title("Meu Portfolio Expert").build();
        ProjectResponse resp = ProjectResponse.builder().id(1L).title("Meu Portfolio Expert").slug("meu-portfolio-expert-123").build();

        // Usa o ArgumentCaptor para interceptar o objeto Project gerado e passado ao repositório no momento do save
        ArgumentCaptor<Project> captor = ArgumentCaptor.forClass(Project.class);
        when(projectRepo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toResponse(any(Project.class))).thenReturn(resp);

        // Act (Execução da Ação)
        ProjectResponse result = projectService.createProject(req);

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        Project saved = captor.getValue();
        assertTrue(saved.getActive()); // Garante que o projeto nasce ativo por padrão
        assertNotNull(saved.getSlug()); // Garante que o slug foi gerado dinamicamente
        assertTrue(saved.getSlug().startsWith("meu-portfolio-expert-")); // Valida o comportamento do normalizador de slugs (slugify + sufixo único)
        verify(projectRepo, times(1)).save(any(Project.class));
    }

    @Test
    @DisplayName("Deve atualizar o slug do projeto apenas se o título for modificado no painel")
    void shouldUpdateSlugOnlyIfTitleChanges() {
        // Arrange
        Long id = 1L;
        ProjectRequest req = ProjectRequest.builder().title("Novo Nome Do Projeto").build();
        Project existing = Project.builder().id(id).title("Nome Antigo").slug("nome-antigo-111").build();

        when(projectRepo.findById(id)).thenReturn(Optional.of(existing));
        when(projectRepo.save(existing)).thenReturn(existing);

        // Act
        projectService.updateProject(id, req);

        // Assert
        assertNotEquals("nome-antigo-111", existing.getSlug()); // Confirma que o slug antigo foi descartado
        assertTrue(existing.getSlug().startsWith("novo-nome-do-projeto-")); // Confirma que um novo slug foi gerado com base no novo título
        verify(projectRepo, times(1)).save(existing);
    }

    @Test
    @DisplayName("Deve calcular o sort order incremental e persistir uma nova imagem atrelada ao projeto")
    void shouldAddProjectImageSuccessfully() {
        // Arrange
        Long pid = 1L;
        MultipartFile fileMock = mock(MultipartFile.class);
        Project project = Project.builder().id(pid).title("Project").build();
        ProjectImage savedImage = ProjectImage.builder().id(10L).url("/media/img.jpg").altText("Alt").sortOrder(5).build();

        when(projectRepo.findById(pid)).thenReturn(Optional.of(project));
        // Simula o salvamento do arquivo dentro de uma pasta estruturada por ID do projeto ("projects/1")
        when(storage.store(fileMock, "projects/" + pid)).thenReturn("/media/img.jpg");
        when(imageRepo.nextSortOrder(pid)).thenReturn(5); // Simula a busca inteligente da próxima posição na galeria
        when(imageRepo.save(any(ProjectImage.class))).thenReturn(savedImage);

        // Act
        ProjectImageResponse result = projectService.addProjectImage(pid, fileMock, "Alt");

        // Assert
        assertNotNull(result);
        assertEquals(10L, result.getId());
        assertEquals(5, result.getSortOrder()); // Valida que a ordem calculada foi aplicada corretamente à imagem
        verify(imageRepo, times(1)).save(any(ProjectImage.class));
    }

    @Test
    @DisplayName("Deve barrar e lançar exceção caso tentem apagar uma imagem que pertence a outro ID de projeto")
    void shouldThrowExceptionWhenImageDoesNotBelongToProject() {
        // Arrange
        Long pid = 1L;
        Long iid = 50L;
        ProjectImage img = ProjectImage.builder().id(iid).url("/img.jpg").build();

        when(imageRepo.findById(iid)).thenReturn(Optional.of(img));
        when(imageRepo.existsByIdAndProjectId(iid, pid)).thenReturn(false); // Simula falha de validação de escopo (tentativa de invasão ou ID cruzado)

        // Act & Assert
        // Valida que o sistema protege os dados lançando a exceção de recurso não encontrado
        assertThrows(ResourceNotFoundException.class, () -> projectService.deleteProjectImage(pid, iid));
        verifyNoInteractions(storage); // Garante estritamente que nenhuma mídia física foi apagada do disco se a validação falhou
        verify(imageRepo, never()).delete(any()); // Garante que o banco não apagou registro algum
    }

    @Test
    @DisplayName("Deve reordenar as imagens do projeto com base no mapeamento posicional do array de IDs")
    void shouldReorderProjectImagesBasedOnIdListOrder() {
        // Arrange
        Long pid = 1L;
        // Cria duas imagens com ordens iniciais padrão (0 e 1)
        ProjectImage img1 = ProjectImage.builder().id(10L).sortOrder(0).build();
        ProjectImage img2 = ProjectImage.builder().id(20L).sortOrder(1).build();
        List<ProjectImage> images = new ArrayList<>(List.of(img1, img2));

        when(imageRepo.findByProjectIdOrderBySortOrderAsc(pid)).thenReturn(images);
        List<Long> inputNewOrderIds = List.of(20L, 10L); // Simula o front-end enviando os IDs invertidos (o ID 20 agora vem primeiro)

        // Act
        projectService.reorderProjectImages(pid, inputNewOrderIds);

        // Assert
        assertEquals(1, img1.getSortOrder()); // O ID 10L que estava na posição 0 foi movido para o índice 1
        assertEquals(0, img2.getSortOrder()); // O ID 20L que estava na posição 1 foi movido para o índice 0 (primeiro lugar)
        verify(imageRepo, times(1)).saveAll(images); // Confirma a persistência em lote da nova ordenação no banco
    }
}