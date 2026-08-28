package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.response.SkillResponse;
import com.portfolio.entity.Skill;
import com.portfolio.mapper.SkillMapper;
import com.portfolio.repository.SkillRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito para gerenciar os mocks nesta classe de teste
class PublicSkillServiceImplTest {

    @Mock
    private SkillRepository skillRepo; // Cria um mock do repositório de habilidades para simular consultas ao banco

    @Mock
    private SkillMapper mapper; // Cria um mock do conversor de entidade Skill para o DTO SkillResponse

    @InjectMocks
    private PublicSkillServiceImpl skillService; // Instancia o serviço real injetando os mocks de repositório e mapper nele

    @Test // Indica que este método é um teste unitário executável pelo JUnit
    @DisplayName("Deve retornar um mapa de habilidades ativas agrupadas por categoria") // Descrição amigável do que o teste valida
    void shouldReturnGroupedSkillsMapWhenActiveSkillsExist() {
        // Arrange (Configuração do Cenário)
        // Cria duas entidades falsas de Skill com categorias diferentes (Frontend e Backend) usando o padrão Builder
        Skill s1 = Skill.builder().id(1L).name("React").category("Frontend").sortOrder(1).build();
        Skill s2 = Skill.builder().id(2L).name("Java").category("Backend").sortOrder(2).build();

        // Cria os DTOs de resposta correspondentes que o Mapper deve retornar após a conversão
        SkillResponse r1 = SkillResponse.builder().id(1L).name("React").category("Frontend").build();
        SkillResponse r2 = SkillResponse.builder().id(2L).name("Java").category("Backend").build();

        // Configura o comportamento simulado do repositório: ao buscar as skills ativas, retorna a lista com s1 e s2
        when(skillRepo.findByActiveTrueOrderBySortOrderAscAndNameAsc()).thenReturn(List.of(s1, s2));

        // Configura o comportamento do mapper: converte s1 em r1 e s2 em r2 quando acionados
        when(mapper.toResponse(s1)).thenReturn(r1);
        when(mapper.toResponse(s2)).thenReturn(r2);

        // Act (Execução da Ação)
        // Chama o método real de agrupamento que estamos testando no serviço
        Map<String, List<SkillResponse>> result = skillService.getSkillsGrouped();

        // Assert (Validação das Expectativas)
        assertNotNull(result); // Garante que o mapa retornado não é nulo
        assertEquals(2, result.size()); // Valida se existem exatamente 2 chaves (categorias) no mapa
        assertTrue(result.containsKey("Frontend")); // Confirma se a categoria "Frontend" está presente
        assertTrue(result.containsKey("Backend")); // Confirma se a categoria "Backend" está presente

        // Valida se a lista da categoria "Frontend" possui 1 elemento e se o nome é "React"
        assertEquals(1, result.get("Frontend").size());
        assertEquals("React", result.get("Frontend").get(0).getName());

        // Valida se a lista da categoria "Backend" possui 1 elemento e se o nome é "Java"
        assertEquals(1, result.get("Backend").size());
        assertEquals("Java", result.get("Backend").get(0).getName());

        // Garante que o repositório foi consultado exatamente 1 vez
        verify(skillRepo, times(1)).findByActiveTrueOrderBySortOrderAscAndNameAsc();
        // Garante que o mapper converteu cada uma das duas entidades exatamente 1 vez
        verify(mapper, times(1)).toResponse(s1);
        verify(mapper, times(1)).toResponse(s2);
    }
}