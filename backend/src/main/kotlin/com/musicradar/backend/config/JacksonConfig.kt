package com.musicradar.backend.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class JacksonConfig {

    @Bean
    fun objectMapper(): ObjectMapper {
        val mapper = Jackson2ObjectMapperBuilder.json()
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .build<ObjectMapper>()
        
        // Registrar módulo Hibernate5JakartaModule para lidar com proxies do Hibernate
        val hibernate5Module = Hibernate5JakartaModule()
        // Não falhar em proxies não inicializados
        hibernate5Module.disable(Hibernate5JakartaModule.Feature.FORCE_LAZY_LOADING)
        mapper.registerModule(hibernate5Module)
        
        return mapper
    }
}
