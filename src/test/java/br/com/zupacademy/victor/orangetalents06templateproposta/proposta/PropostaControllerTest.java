package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ConsultaRestricao;
import feign.FeignException;
import feign.RetryableException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise.*;
import static br.com.zupacademy.victor.orangetalents06templateproposta.utils.Factory.*;
import static br.com.zupacademy.victor.orangetalents06templateproposta.utils.Utils.toJson;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class PropostaControllerTest {


    @MockBean
    private ConsultaRestricao consultaRestricao;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PropostaRepository propostaRepository;

    @BeforeEach
    void setUp() {
        propostaRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        propostaRepository.deleteAll();
    }

    @Test
    @DisplayName("Não deve cadastrar nova proposta com documento já existente no banco de dados!")
    void naoDeveCadastrarNovaPropostaComDocumentoJaExistente() throws Exception {
        var novaPropostaRequest = buildNovaPropostaRequest(DOCUMENTO_ELEGIVEL);
        propostaRepository.save(novaPropostaRequest.toModel());

        var response = mockMvc.perform(post(URI)
                .contentType(APPLICATION_JSON)
                .content(toJson(novaPropostaRequest)))
                .andExpect(status().isUnprocessableEntity())
                .andReturn().getResponse();

        var propostas = propostaRepository.findAll();

        assertEquals("Já existe proposta para esse documento!", response.getContentAsString());
        assertEquals(1, propostas.size());
    }

    @Test
    @DisplayName("Deve criar proposta elegivel quando documento não começar com o numero 3")
    void deveCriarPropostaElegivel() throws Exception {

        var novaPropostaRequest = buildNovaPropostaRequest(DOCUMENTO_ELEGIVEL);
        var analisaPropostaRequest = buildAnalisaPropostaRequest(DOCUMENTO_ELEGIVEL);
        var analisaPropostaResponse = buildAnalisaPropostaResponse(DOCUMENTO_ELEGIVEL, SEM_RESTRICAO);

        when(consultaRestricao.avaliaProposta(any())).thenReturn(analisaPropostaResponse);

        var response = mockMvc.perform(post(URI)
                .contentType(APPLICATION_JSON)
                .content(toJson(novaPropostaRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var propostas = propostaRepository.findAll();

        assertEquals("http://localhost/api/v1/proposta/" + propostas.get(0).getId(), response.getHeader("Location"));
        assertEquals("Proposta criada: Cliente elegivel!", response.getContentAsString());
        assertEquals(1, propostas.size());
        assertEquals(ELEGIVEL, propostas.get(0).getResultadoAnalise());
        assertNotNull(propostas.get(0).getId());
    }

    @Test
    @DisplayName(" Deve criar proposta não elegivel quando documento começar com o numero 3")
    void deveCriarPropostaNaoElegivel() throws Exception {

        var novaPropostaRequest = buildNovaPropostaRequest(DOCUMENTO_NAO_ELEGIVEL);
        var analisaPropostaRequest = buildAnalisaPropostaRequest(DOCUMENTO_NAO_ELEGIVEL);

        when(consultaRestricao.avaliaProposta(any()))
                .thenThrow(mock(FeignException.FeignClientException.class));

        var response = mockMvc.perform(post(URI)
                .contentType(APPLICATION_JSON)
                .content(toJson(novaPropostaRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        var propostas = propostaRepository.findAll();

        assertEquals("http://localhost/api/v1/proposta/" + propostas.get(0).getId(), response.getHeader("Location"));
        assertEquals("Proposta criada: Cliente não elegivel no momento!", response.getContentAsString());
        assertEquals(1, propostas.size());
        assertEquals(NAO_ELEGIVEL, propostas.get(0).getResultadoAnalise());
        assertNotNull(propostas.get(0).getId());
    }

    @Test
    @DisplayName(" Não Deve criar proposta quando api estiver fora do ar")
    void naoDeveCriarPropostaQuandoApiEstiverForaDoAr() throws Exception {

        var novaPropostaRequest = buildNovaPropostaRequest(DOCUMENTO_NAO_ELEGIVEL);
        var analisaPropostaRequest = buildAnalisaPropostaRequest(DOCUMENTO_NAO_ELEGIVEL);

        when(consultaRestricao.avaliaProposta(analisaPropostaRequest))
                .thenThrow(mock(RetryableException.class));

        var response = mockMvc.perform(post(URI)
                .contentType(APPLICATION_JSON)
                .content(toJson(novaPropostaRequest)))
                .andExpect(status().is5xxServerError())
                .andReturn().getResponse();

        var propostas = propostaRepository.findAll();

        assertEquals("Erro inesperado, tente novamente!", response.getContentAsString());
        assertTrue(propostas.isEmpty());
    }


}