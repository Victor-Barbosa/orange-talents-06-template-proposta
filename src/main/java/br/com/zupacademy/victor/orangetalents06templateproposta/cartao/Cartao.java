package br.com.zupacademy.victor.orangetalents06templateproposta.cartao;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem.Avisos;
import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio.Bloqueio;
import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.bloqueio.StatusCartao;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Cartao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCartao;
    private String id;
    private LocalDateTime emitidoEm = LocalDateTime.now();
    private String titular;
    @Enumerated(EnumType.STRING)
    private StatusCartao statusCartao = StatusCartao.ATIVO;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Bloqueio> bloqueios;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Avisos> avisos;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Carteira> carteiras;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Parcela> parcelas;
    private int limite;
    private String renegociacao;
    @OneToOne(cascade = CascadeType.ALL)
    private Vencimento vencimento;
    private Integer idProposta;

    public Cartao(String id, LocalDateTime emitidoEm, String titular, List<Bloqueio> bloqueios, List<Avisos> avisos,
                  List<Carteira> carteiras, List<Parcela> parcelas, int limite, String renegociacao, Vencimento vencimento, Integer idProposta) {
        this.id = id;
        this.emitidoEm = emitidoEm;
        this.titular = titular;
        this.bloqueios = bloqueios;
        this.avisos = avisos;
        this.carteiras = carteiras;
        this.parcelas = parcelas;
        this.limite = limite;
        this.renegociacao = renegociacao;
        this.vencimento = vencimento;
        this.idProposta = idProposta;
    }

    @Deprecated
    public Cartao() {

    }

    public Long getIdCartao() {
        return idCartao;
    }

    public String getId() {
        return id;
    }

    public LocalDateTime getEmitidoEm() {
        return emitidoEm;
    }

    public String getTitular() {
        return titular;
    }

    public List<Bloqueio> getBloqueios() {
        return bloqueios;
    }

    public List<Avisos> getAvisos() {
        return avisos;
    }

    public List<Carteira> getCarteiras() {
        return carteiras;
    }

    public List<Parcela> getParcelas() {
        return parcelas;
    }

    public int getLimite() {
        return limite;
    }

    public String getRenegociacao() {
        return renegociacao;
    }

    public Vencimento getVencimento() {
        return vencimento;
    }

    public Integer getIdProposta() {
        return idProposta;
    }

    public StatusCartao getStatusCartao() {
        return statusCartao;
    }

    public Cartao setStatusCartao(StatusCartao statusCartao) {
        this.statusCartao = statusCartao;
        return null;
    }
}
