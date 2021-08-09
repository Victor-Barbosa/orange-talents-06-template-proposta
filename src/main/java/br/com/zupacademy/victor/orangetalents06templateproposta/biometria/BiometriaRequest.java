package br.com.zupacademy.victor.orangetalents06templateproposta.biometria;

import br.com.zupacademy.victor.orangetalents06templateproposta.compartilhado.IsValidBase64;
import br.com.zupacademy.victor.orangetalents06templateproposta.cartao.Cartao;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;
import java.util.Base64;

public class BiometriaRequest {

    @NotBlank
    @IsValidBase64
    private String fingerPrint;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public BiometriaRequest(String fingerPrint) {
        this.fingerPrint = fingerPrint;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }

    public Biometria toModel(Cartao cartao) {
        return new Biometria(Base64.getEncoder().encode(fingerPrint.getBytes()), cartao);
    }
}
