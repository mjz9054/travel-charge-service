package com.renwoxin.travelchargeservice.service

import com.renwoxin.travelchargeservice.common.TravelOrderChargeStatus
import com.renwoxin.travelchargeservice.common.TravelOrderInvoiceStatus
import com.renwoxin.travelchargeservice.dto.CompanyInvoiceDto
import com.renwoxin.travelchargeservice.gateway.InvoiceClient
import com.renwoxin.travelchargeservice.repository.CompanyInvoiceRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TravelInvoiceService {
    @Autowired
    private lateinit var companyInvoiceRepository: CompanyInvoiceRepository

    @Autowired
    private lateinit var invoiceClient: InvoiceClient

    fun scheduleCreateInvoice() {
        val companyInvoices =
            companyInvoiceRepository.findByStatus(TravelOrderInvoiceStatus.PROCESSING.name)
        companyInvoices.forEach {
            invoiceClient.createInvoice(CompanyInvoiceDto(it))
            companyInvoiceRepository.save(it.copy(status = TravelOrderChargeStatus.COMPLETED.name))
        }
    }
}
