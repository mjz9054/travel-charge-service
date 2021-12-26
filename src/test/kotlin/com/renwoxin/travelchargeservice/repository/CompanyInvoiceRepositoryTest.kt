package com.renwoxin.travelchargeservice.repository

import com.renwoxin.travelchargeservice.model.entity.CompanyInvoice
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CompanyInvoiceRepositoryTest {
    @Autowired
    private lateinit var companyInvoiceRepository: CompanyInvoiceRepository
    private val invoice1 = CompanyInvoice(
        id = 23L,
        orderId = 55L,
        companyId = "1001",
        payerName = "思特沃克软件技术（武汉）有限公司",
        payerRegisterNo = "111222220MA4K2A3C8N",
        payerAddress = "武汉市东湖新技术开发区关山大道332号保利时代A栋保利国际中心20层",
        payerTelephone = "027-87050527",
        payerBankName = "招商银行股份有限公司武汉创业街支行",
        payerBankAccount = "1334312164512231",
        userEmail = "zhangsan@thoughtworks.com",
        userMobileNo = "12346677890",
        amount = "5221.8",
        merchantName = "任我行差旅服务有限公司",
        status = "PROCESSING"
    )
    private val invoice2 = CompanyInvoice(
        id = 24L,
        orderId = 55L,
        companyId = "1002",
        payerName = "思特沃克软件技术（西安）有限公司",
        payerRegisterNo = "111222220MA4K2A3C8N",
        payerAddress = "西安",
        payerTelephone = "027-87050527",
        payerBankName = "招商银行股份有限公司武汉创业街支行",
        payerBankAccount = "1334312164512231",
        userEmail = "zhangsan@thoughtworks.com",
        userMobileNo = "12346677890",
        amount = "6660.8",
        merchantName = "任我行差旅服务有限公司",
        status = "PROCESSING"
    )

    @BeforeEach
    fun setup() {
        companyInvoiceRepository.save(invoice1)
        companyInvoiceRepository.save(invoice2)
    }

    @AfterAll
    fun teardown() {
        companyInvoiceRepository.delete(invoice1)
        companyInvoiceRepository.delete(invoice2)
    }

    @Test
    fun `should return all processing invoice when query invoice given invoice status is processing`() {
        // given
        val invoiceStatus = "PROCESSING"

        // when
        val actual = companyInvoiceRepository.findByStatus(invoiceStatus)

        // then
        val expected = listOf(invoice1, invoice2)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return updated invoice when update invoice given invoice status is COMPLETED`() {
        // given
        val invoice = invoice1

        // when
        val actual = companyInvoiceRepository.save(invoice.copy(status = "COMPLETED"))

        // then
        val expected = invoice.copy(status = "COMPLETED")
        assertThat(actual).isEqualTo(expected)
    }
}
