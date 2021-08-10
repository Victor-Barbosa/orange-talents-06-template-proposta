package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @Future
    private LocalDate validoAte;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public AvisoViagemRequest(String destino, LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    public Avisos toModel(Cartao cartao, String ip, String userAgent) {
        return new Avisos(cartao, ip, userAgent, this.destino, this.validoAte);
    }
}
