package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.carteira;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class AssociaCarteiraRequest {

    @Email
    @NotBlank
    private String email;
    @Enumerated(EnumType.STRING)
    private SistemaCarteira carteira;

    public AssociaCarteiraRequest() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AssociaCarteiraRequest(String email, SistemaCarteira carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public Carteira toModel(Cartao cartao) {
        return new Carteira(cartao, email, carteira);
    }

    public String getEmail() {
        return email;
    }

    public SistemaCarteira getCarteira() {
        return carteira;
    }
}
