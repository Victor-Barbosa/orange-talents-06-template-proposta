package br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.bloqueio;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Bloqueio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ip;

    private String userAgent;

    private LocalDateTime instanteBloqueio = LocalDateTime.now();

    @ManyToOne
    private Cartao cartao;

    public Bloqueio(String ip, String userAgent, Cartao cartao) {

        this.ip = ip;
        this.userAgent = userAgent;
        this.cartao = cartao;
    }

    @Deprecated
    public Bloqueio() {

    }
}
