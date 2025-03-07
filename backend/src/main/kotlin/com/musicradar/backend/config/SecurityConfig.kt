package com.musicradar.backend.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    // Criar um usuário temporário para testes
    @Bean
    fun userDetailsService(): UserDetailsService {
        val userDetails = User.withDefaultPasswordEncoder()
            .username("user")
            .password("password")
            .roles("USER")
            .build()

        return InMemoryUserDetailsManager(userDetails)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeRequests { authz ->
                authz
                    .requestMatchers("/api/**").permitAll() // API endpoints são públicos
                    .anyRequest().authenticated() // Outras requisições requerem autenticação
            }
            .formLogin { form ->
                form
                    .loginPage("/login") // Usar nossa página de login personalizada
                    .permitAll()
            }
            .csrf { csrf -> 
                csrf.disable() 
            }

        return http.build()
    }
}
