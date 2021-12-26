package com.renwoxin.travelchargeservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.web.client.RestTemplate

@Configuration
class RestTemplateConfig {

    @Bean
    fun restTemplate(): RestTemplate {
        val restTemplate = RestTemplate()

        val jsonMessageConverter = MappingJackson2HttpMessageConverter()
        restTemplate.messageConverters.add(jsonMessageConverter)
        return restTemplate
    }

}
