package com.renwoxin.travelchargeservice.gateway

import com.renwoxin.travelchargeservice.config.AppProperties
import com.renwoxin.travelchargeservice.dto.UnionPayPaymentDto
import com.renwoxin.travelchargeservice.model.response.UnionPayPaymentResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class UnionPayClient {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var appProperties: AppProperties

    fun createPayment(orderPaymentDto: UnionPayPaymentDto): UnionPayPaymentResponse {
        val url = "${appProperties.unionPayHost}/unionpay/payment"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(orderPaymentDto, headers)
        val responseTypeReference =
            object : ParameterizedTypeReference<UnionPayPaymentResponse>() {}

        val response = restTemplate.exchange(
            url, HttpMethod.POST, requestEntity, responseTypeReference
        )
        return response.body!!
    }
}
