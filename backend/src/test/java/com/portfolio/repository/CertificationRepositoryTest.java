package com.portfolio.repository;

import com.portfolio.entity.Certification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
// RESOLUÇÃO DO BUG: Desativa o Flyway no teste e proíbe o Spring de buscar um banco H2 embutido fictício
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test") // Usa o banco configurado no seu profile de teste, efetuando rollback automático de dados
class CertificationRepositoryTest {

    @Autowired
    private CertificationRepository certificationRepository;

    @Test
    @DisplayName("Deve retornar apenas certificações ativas ordenadas por sortOrder ascendente e data de emissão decrescente")
    void shouldFindOnlyActiveCertificationsWithProperSorting() {
        // Arrange
        Certification cert1 = Certification.builder()
                .name("Spring Expert")
                .issuer("Pivotal")
                .issuedAt(LocalDate.of(2026, 1, 1))
                .sortOrder(2)
                .active(true)
                .build();

        Certification cert2 = Certification.builder()
                .name("AWS Architect")
                .issuer("Amazon")
                .issuedAt(LocalDate.of(2026, 5, 1))
                .sortOrder(1)
                .active(true)
                .build();

        Certification cert3 = Certification.builder()
                .name("Java Cloud")
                .issuer("Oracle")
                .issuedAt(LocalDate.of(2025, 1, 1))
                .sortOrder(1)
                .active(true)
                .build();

        Certification certInactive = Certification.builder()
                .name("Certificação Inativa")
                .issuer("Qualquer")
                .issuedAt(LocalDate.of(2024, 1, 1))
                .sortOrder(0)
                .active(false)
                .build();

        certificationRepository.saveAll(List.of(cert1, cert2, cert3, certInactive));

        // Act
        List<Certification> result = certificationRepository.findByActiveTrueOrderBySortOrderAscAndIssuedAtDesc();

        // Assert
        assertNotNull(result);
        assertEquals(3, result.size());

        assertEquals("AWS Architect", result.get(0).getName());
        assertEquals("Java Cloud", result.get(1).getName());
        assertEquals("Spring Expert", result.get(2).getName());
    }
}
