package br.com.zupacademy.victor.orangetalents06templateproposta.biometria;

import br.com.zupacademy.victor.orangetalents06templateproposta.proposta.cartao.Cartao;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Biometria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private byte[] fingerPrint;
    @Column(nullable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();
    @NotNull
    @ManyToOne
    private Cartao cartao;

    public Biometria(byte[] fingerPrint, Cartao cartao) {
        this.fingerPrint = fingerPrint;
        this.cartao = cartao;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public Biometria() {

    }
}
