package com.portfolio.service.admin.impl;

import com.portfolio.dto.request.SkillRequest;
import com.portfolio.dto.response.SkillResponse;
import com.portfolio.entity.Skill;
import com.portfolio.exception.ResourceNotFoundException;
import com.portfolio.mapper.SkillMapper;
import com.portfolio.repository.SkillRepository;
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

@ExtendWith(MockitoExtension.class) // Habilita o Mockito para gerenciar os mocks do JUnit 5 nesta classe de teste
class AdminSkillServiceImplTest {

    @Mock private SkillRepository skillRepo; // Cria um mock do repositório de skills para isolar o acesso ao banco
    @Mock private SkillMapper mapper; // Cria um mock do conversor de entidades e requisições de habilidades
    @InjectMocks private AdminSkillServiceImpl skillService; // Instancia o serviço real injetando os mocks acima nele

    @Test
    @DisplayName("Deve retornar uma lista com todas as competências técnicas cadastradas")
    void shouldReturnAllSkillsSuccessfully() {
        // Arrange (Configuração do Cenário)
        Skill s = Skill.builder().id(1L).name("Java").build();
        SkillResponse r = SkillResponse.builder().id(1L).name("Java").build();

        // Configura o repositório para retornar a listagem total de competências
        when(skillRepo.findAll()).thenReturn(List.of(s));
        when(mapper.toResponse(s)).thenReturn(r);

        // Act (Execução da Ação)
        List<SkillResponse> result = skillService.getAllSkills();

        // Assert (Validação das Expectativas)
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(skillRepo, times(1)).findAll(); // Confirma que a busca geral foi acionada exatamente 1 vez
    }

    @Test
    @DisplayName("Deve salvar uma nova habilidade forçando a flag active como TRUE")
    void shouldCreateSkillWithActiveTrue() {
        // Arrange
        SkillRequest req = SkillRequest.builder().name("React").build();
        SkillResponse resp = SkillResponse.builder().id(1L).name("React").build();

        // Usa o ArgumentCaptor para interceptar a entidade Skill enviada ao repositório no momento do save
        ArgumentCaptor<Skill> captor = ArgumentCaptor.forClass(Skill.class);
        when(skillRepo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));
        when(mapper.toResponse(any(Skill.class))).thenReturn(resp);

        // Act
        SkillResponse result = skillService.createSkill(req);

        // Assert
        assertNotNull(result);
        assertTrue(captor.getValue().getActive()); // Valida a regra de negócio essencial: toda nova skill nasce ativa por padrão (active=true)
        verify(skillRepo, times(1)).save(any(Skill.class)); // Confirma a persistência do novo registro
    }

    @Test
    @DisplayName("Deve aplicar soft delete mudando a flag active para FALSE em vez de remover do banco")
    void shouldApplySoftDeleteSuccessfully() {
        // Arrange
        Long id = 1L;
        Skill s = Skill.builder().id(id).name("Docker").active(true).build();

        // Configura o repositório para encontrar o registro ativo pelo ID e salvá-lo atualizado
        when(skillRepo.findById(id)).thenReturn(Optional.of(s));

        // CORRIGIDO: Força o Mockito a retornar o próprio objeto recebido com o estado atualizado em tempo de execução
        when(skillRepo.save(any(Skill.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        // Aciona a deleção administrativa da habilidade
        skillService.deleteSkill(id);

        // Assert
        assertFalse(s.getActive()); // Valida o Soft Delete: a flag active se torna false, desativando logicamente a competência
        verify(skillRepo, times(1)).save(s); // Confirma que a entidade alterada foi salva no banco
        verify(skillRepo, never()).deleteById(any()); // Garante com rigor que a remoção física (permanente) na tabela nunca ocorreu
    }
}
