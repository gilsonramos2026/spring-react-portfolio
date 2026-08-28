package com.portfolio.service.publicapi.impl;

import com.portfolio.dto.request.ContactRequest;
import com.portfolio.entity.Contact;
import com.portfolio.repository.ContactRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor; // Importa a classe do Mockito usada para capturar argumentos passados em chamadas de métodos mockados
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // Inicializa o Mockito e o JUnit 5
class PublicContactServiceImplTest {

    @Mock
    private ContactRepository contactRepo; // Cria um mock do repositório de contatos

    @InjectMocks
    private PublicContactServiceImpl contactService; // Instancia o serviço de contato injetando o mock do repositório nele

    @Test
    @DisplayName("Deve extrair os dados do request e salvar o contato com status 'new' e o IP correto")
    void shouldMapAndSaveContactSuccessfully() {
        // Arrange (Configuração do Cenário)
        // Cria um DTO de requisição preenchido via Builder simulando os dados enviados pelo cliente no formulário
        ContactRequest request = ContactRequest.builder()
                .name("Alice Silva")
                .email("alice@example.com")
                .subject("Oportunidade Comercial")
                .message("Olá João, gostaria de conversar sobre um projeto.")
                .phone("11999999999")
                .build();

        String clientIp = "192.168.0.1"; // Define um IP de cliente simulado para teste

        // Cria um capturador de argumentos do tipo Contact para conseguirmos inspecionar a entidade montada pelo serviço
        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);

        // Act (Execução da Ação)
        // Chama o método de envio de contato do serviço passando o DTO e o IP
        contactService.sendContact(request, clientIp);

        // Assert (Validação das Expectativas)
        // Verifica se o método save do repositório foi invocado exatamente 1 vez, e nesse momento captura o objeto Contact que foi passado como argumento
        verify(contactRepo, times(1)).save(contactCaptor.capture());

        // Extrai o objeto Contact capturado de dentro do ArgumentCaptor para análise
        Contact savedContact = contactCaptor.getValue();

        // Valida se o mapeamento (feito manualmente no serviço ou via builder interno) transferiu os dados do DTO para a Entidade com total integridade
        assertNotNull(savedContact); // Garante que o contato gerado não é nulo
        assertEquals("Alice Silva", savedContact.getName()); // Valida o nome
        assertEquals("alice@example.com", savedContact.getEmail()); // Valida o e-mail
        assertEquals("Oportunidade Comercial", savedContact.getSubject()); // Valida o assunto
        assertEquals("Olá João, gostaria de conversar sobre um projeto.", savedContact.getMessage()); // Valida a mensagem
        assertEquals("11999999999", savedContact.getPhone()); // Valida o telefone
        assertEquals("192.168.0.1", savedContact.getIpAddress()); // Valida se o IP do cliente foi atribuído corretamente
        assertEquals("new", savedContact.getStatus()); // Valida a regra de negócio crucial: todo novo contato nasce com o status "new"
    }
}
