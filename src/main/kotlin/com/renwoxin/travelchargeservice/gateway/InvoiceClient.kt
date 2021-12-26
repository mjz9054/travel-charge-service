package com.renwoxin.travelchargeservice.gateway

import com.renwoxin.travelchargeservice.config.AppProperties
import com.renwoxin.travelchargeservice.dto.CompanyInvoiceDto
import com.renwoxin.travelchargeservice.model.entity.CompanyInvoice
import com.renwoxin.travelchargeservice.model.response.InvoiceCreateResponse
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
class InvoiceClient {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var appProperties: AppProperties

    fun createInvoice(companyInvoice: CompanyInvoiceDto): InvoiceCreateResponse {
        val url = "${appProperties.invoiceHost}/invoices"
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        val requestEntity = HttpEntity(companyInvoice, headers)
        val responseTypeReference =
            object : ParameterizedTypeReference<InvoiceCreateResponse>() {}

        val response = restTemplate.exchange(
            url, HttpMethod.POST, requestEntity, responseTypeReference
        )
        return response.body!!
    }
}
