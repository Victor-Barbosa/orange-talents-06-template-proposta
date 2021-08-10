package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.Cartao;

import javax.persistence.*;

@Entity
public class Carteira {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @Enumerated
    private SistemaCarteira carteira;
    @ManyToOne
    private Cartao cartao;

    public Carteira(Cartao cartao, String email, SistemaCarteira carteira) {

        this.cartao = cartao;
        this.email = email;
        this.carteira = carteira;
    }

    public Long getId() {
        return id;
    }

    @Deprecated
    public Carteira() {

    }
}
