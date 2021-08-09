package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.Proposta;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.PropostaRepository;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.bloqueio.StatusCartao;
import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise;
import feign.RetryableException;
import org.hibernate.annotations.common.util.impl.LoggerFactory;
import org.jboss.logging.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

@Component
@EnableScheduling
@Transactional
public class CriaCartaoPropostaElegivel {

    Logger logger = LoggerFactory.logger(CriaCartaoPropostaElegivel.class);

    private CartaoClient cartaoClient;

    private PropostaRepository propostaRepository;

    public CriaCartaoPropostaElegivel(CartaoClient cartaoClient, PropostaRepository propostaRepository) {
        this.cartaoClient = cartaoClient;
        this.propostaRepository = propostaRepository;
    }

    @Scheduled(fixedDelay = 20000)
    public ResponseEntity<String> CriaCartaoPropostaElegivel() {
        logger.info("Verifica proposta, se for elegivel salva cartao e vincula a proposta!");

        List<Proposta> propostasElegiveis = propostaRepository.findByResultadoAnaliseAndCartaoIsNull(ResultadoAnalise.ELEGIVEL);

        if (!propostasElegiveis.isEmpty()) {
            for (Proposta proposta : propostasElegiveis) {
                try {
                    CriaCartaoRequest criaCartaoRequest =
                            new CriaCartaoRequest(proposta.getDocumento(), proposta.getNome(), proposta.getId().toString());
                    Cartao novoCartao = cartaoClient.criaCartaoPropostaElegivel(criaCartaoRequest);
                    proposta.setCartao(novoCartao);
                    novoCartao.setStatusCartao(StatusCartao.ATIVO);
                    propostaRepository.save(proposta);

                    logger.info("Cartao criado e salvo " + proposta);

                } catch (RetryableException ex){
                    logger.info(ex.getMessage());

                    return ResponseEntity.internalServerError().body("Erro inesperado, tente novamente!");

                }

            }
        }
        return ResponseEntity.status(201).body("Cartão criado e vinculado a proposta!");
    }
}
