package com.renwoxin.travelchargeservice.gateway

import com.renwoxin.travelchargeservice.config.AppProperties
import com.renwoxin.travelchargeservice.dto.CompanyInvoiceDto
import com.renwoxin.travelchargeservice.model.entity.CompanyInvoice
import com.renwoxin.travelchargeservice.model.response.InvoiceCreateResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate

@Component
class InvoiceClient {

    @Autowired
    private lateinit var restTemplate: RestTemplate

    @Autowired
    private lateinit var appProperties: AppProperties

    fun createInvoice(companyInvoice: CompanyInvoiceDto): InvoiceCreateResponse {
        TODO()
    }
}
