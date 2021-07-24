package br.com.zupacademy.victor.orangetalents06templateproposta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrangeTalents06TemplatePropostaApplication {

    public static void main(String[] args) {
        SpringApplication.run(OrangeTalents06TemplatePropostaApplication.class, args);
    }

}
