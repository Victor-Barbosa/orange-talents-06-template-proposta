package br.com.zupacademy.victor.orangetalents06templateproposta.compartilhado.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/api/proposta/**").hasAuthority("SCOPE_proposta:read")
                                .antMatchers(HttpMethod.GET, "/actuator/prometheus").permitAll()
                                .antMatchers(HttpMethod.GET, "/actuator/**").hasAuthority("SCOPE_actuator:read")
                                .antMatchers(HttpMethod.POST, "/api/proposta*").hasAuthority("SCOPE_proposta:write")
                                .antMatchers(HttpMethod.POST, "/api/biometria/**").hasAuthority("SCOPE_biometria:write")
                                .antMatchers(HttpMethod.POST, "/api/bloqueio/**").hasAuthority("SCOPE_bloqueio:write")
                                .antMatchers(HttpMethod.POST, "/api/viagem**").hasAuthority("SCOPE_viagem:write")
                                .antMatchers(HttpMethod.POST, "/api/carteira**").hasAuthority("SCOPE_carteira:write")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }

}
