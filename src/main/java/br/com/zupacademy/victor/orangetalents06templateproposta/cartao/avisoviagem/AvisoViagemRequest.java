package br.com.zupacademy.victor.orangetalents06templateproposta.cartao.avisoviagem;

import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.Cartao;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class AvisoViagemRequest {

    @NotBlank
    private String destino;
    @NotNull
    @Future
    private LocalDate terminoViagem;

    public AvisoViagemRequest(String destino, LocalDate terminoViagem) {
        this.destino = destino;
        this.terminoViagem = terminoViagem;
    }

    public AvisoViagem toModel(Cartao cartao, String ip, String userAgent) {
        return new AvisoViagem(cartao, ip, userAgent, destino, terminoViagem);
    }
}
