package com.portfolio.repository;

import com.portfolio.entity.Testimonial;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class TestimonialRepositoryTest {

    @Autowired
    private TestimonialRepository testimonialRepository;

    @BeforeEach
    void setUp() {
        // Garante o isolamento completo limpando qualquer dado residual antes de cada teste
        testimonialRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve retornar apenas depoimentos ativos ordenados pelo sortOrder de forma ascendente")
    void shouldFindOnlyActiveTestimonialsOrderedBySortOrderAsc() {
        // Arrange
        Testimonial t1 = Testimonial.builder().name("Cliente A").role("Manager").content("Excelente").sortOrder(2).active(true).build();
        Testimonial t2 = Testimonial.builder().name("Cliente B").role("CEO").content("Incrível").sortOrder(1).active(true).build();
        Testimonial tInactive = Testimonial.builder().name("Cliente Inativo").role("Dev").content("Ok").sortOrder(0).active(false).build();

        testimonialRepository.saveAll(List.of(t1, t2, tInactive));

        // Act
        List<Testimonial> result = testimonialRepository.findByActiveTrueOrderBySortOrderAsc();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Despreza o registro inativo
        assertEquals("Cliente B", result.get(0).getName()); // sortOrder = 1 vem antes do sortOrder = 2
        assertEquals("Cliente A", result.get(1).getName());
    }

    @Test
    @DisplayName("Deve retornar apenas depoimentos ativas e destacados ordenados por sortOrder ascendente")
    void shouldFindOnlyActiveAndFeaturedTestimonials() {
        // Arrange
        Testimonial tFeatured1 = Testimonial.builder().name("Cliente Top 1").role("CTO").content("Show").sortOrder(2).active(true).featured(true).build();
        Testimonial tFeatured2 = Testimonial.builder().name("Cliente Top 2").role("VPE").content("Top").sortOrder(1).active(true).featured(true).build();
        Testimonial tCommon = Testimonial.builder().name("Cliente Comum").role("PO").content("Bom").sortOrder(0).active(true).featured(false).build();

        testimonialRepository.saveAll(List.of(tFeatured1, tFeatured2, tCommon));

        // Act
        List<Testimonial> result = testimonialRepository.findByActiveTrueAndFeaturedTrueOrderBySortOrderAsc();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size()); // Filtra e remove o depoimento comum (featured = false)
        assertEquals("Cliente Top 2", result.get(0).getName()); // sortOrder = 1
        assertEquals("Cliente Top 1", result.get(1).getName()); // sortOrder = 2
    }
}
