package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CarteiraRepository extends JpaRepository<Carteira, Long> {

    boolean existsByCartao_IdCartaoAndCarteira(Long idCartao, SistemaCarteira carteira);

}
