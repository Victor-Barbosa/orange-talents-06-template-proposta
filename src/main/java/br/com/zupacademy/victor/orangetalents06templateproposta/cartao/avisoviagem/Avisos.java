package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.Cartao;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Avisos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String ip;
    @Column(nullable = false)
    private String userAgent;
    @Column(nullable = false)
    private String destino;
    @Column(nullable = false)
    private LocalDate validoAte;
    @Column(nullable = false)
    private LocalDateTime instanteDoAviso = LocalDateTime.now();
    @ManyToOne
    private Cartao cartao;

    public Avisos(Cartao cartao, String ip, String userAgent, String destino, LocalDate validoAte) {

        this.cartao = cartao;
        this.ip = ip;
        this.userAgent = userAgent;
        this.destino = destino;
        this.validoAte = validoAte;
    }

    @Deprecated
    public Avisos() {

    }
}
