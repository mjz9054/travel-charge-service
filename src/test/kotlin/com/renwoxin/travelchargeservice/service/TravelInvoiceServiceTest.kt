package com.renwoxin.travelchargeservice.service

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.renwoxin.travelchargeservice.dto.CompanyInvoiceDto
import com.renwoxin.travelchargeservice.gateway.InvoiceClient
import com.renwoxin.travelchargeservice.model.entity.CompanyInvoice
import com.renwoxin.travelchargeservice.model.response.InvoiceCreateResponse
import com.renwoxin.travelchargeservice.repository.CompanyInvoiceRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class TravelInvoiceServiceTest {
    @Mock
    private lateinit var companyInvoiceRepository: CompanyInvoiceRepository

    @Mock
    private lateinit var invoiceClient: InvoiceClient

    @InjectMocks
    private lateinit var travelInvoiceService: TravelInvoiceService

    @Test
    fun `should return invoice status is PROCESSING when create invoice request given order id and order is paid`() {
        // given
        val invoice = CompanyInvoice(
            id = 23L,
            orderId = 55L,
            companyId = "1001",
            payerName = "思特沃克软件技术（武汉）有限公司",
            payerRegisterNo = "111222220MA4K2A3C8N",
            payerAddress = "武汉市东湖新技术开发区关山大道332号保利时代A栋保利国际中心20层",
            payerTelPhone = "027-87050527",
            payerBankName = "招商银行股份有限公司武汉创业街支行",
            payerBankAccount = "1334312164512231",
            userEmail = "zhangsan@thoughtworks.com",
            userMobileNo = "12346677890",
            amount = "5221.8",
            merchantName = "任我行差旅服务有限公司",
            status = "PROCESSING"
        )
        val invoiceDto = CompanyInvoiceDto(invoice)
        whenever(companyInvoiceRepository.findByStatus("PROCESSING")).thenReturn(listOf(invoice))
        whenever(invoiceClient.createInvoice(invoiceDto)).thenReturn(
            InvoiceCreateResponse(
                status = "ACCEPT",
                referenceId = "24762644145713432"
            )
        )

        // when
        travelInvoiceService.scheduleCreateInvoice()

        // then
        verify(invoiceClient, times(1)).createInvoice(invoiceDto)
        verify(companyInvoiceRepository, times(1)).save(invoice.copy(status = "COMPLETED"))
    }
}


