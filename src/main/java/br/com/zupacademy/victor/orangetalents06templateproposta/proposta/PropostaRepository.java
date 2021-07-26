package br.com.zupacademy.victor.orangetalents06templateproposta.proposta;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.consultadocumento.ResultadoAnalise;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    boolean existsByDocumento(String documento);

    List<Proposta> findByResultadoAnaliseAndCartaoIsNull(ResultadoAnalise elegivel);
}