package com.renwoxin.travelchargeservice.gateway

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock.configureFor
import com.github.tomakehurst.wiremock.client.WireMock.okJson
import com.github.tomakehurst.wiremock.client.WireMock.post
import com.github.tomakehurst.wiremock.client.WireMock.stubFor
import com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.whenever
import com.renwoxin.travelchargeservice.config.AppProperties
import com.renwoxin.travelchargeservice.dto.CompanyInvoiceDto
import com.renwoxin.travelchargeservice.model.response.InvoiceCreateResponse
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.util.ReflectionTestUtils
import org.springframework.web.client.RestTemplate


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension::class)
class InvoiceClientTest {
    @Mock
    private lateinit var restTemplate: RestTemplate

    @Mock
    private lateinit var appProperties: AppProperties

    @InjectMocks
    private lateinit var invoiceClient: InvoiceClient

    private val wireMockServer: WireMockServer = WireMockServer(5566)

    @BeforeAll
    fun setup() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(
            post(urlEqualTo("/invoices")).willReturn(
                okJson(
                    """
                    {
                        "referenceId":"24762644145713432",
                        "status":"ACCEPT"
                    }
                    """.trimIndent()
                )
            )
        )
        ReflectionTestUtils.setField(invoiceClient, "restTemplate", RestTemplate())
    }

    @AfterAll
    fun teardown() {
        wireMockServer.stop();
    }

    @Test
    fun `should return payment status when create union payment given payment request`() {
        // given
       val invoiceDto = CompanyInvoiceDto(
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
        whenever(appProperties.invoiceHost).thenReturn("http://localhost:5566")
        whenever(
            restTemplate.exchange(
                eq("http://localhost:5566/invoices"),
                eq(HttpMethod.POST),
                any(),
                any<ParameterizedTypeReference<InvoiceCreateResponse>>()
            )
        ).thenCallRealMethod()

        // when
        val actual = invoiceClient.createInvoice(invoiceDto)

        // then
        val expected = InvoiceCreateResponse(referenceId = "24762644145713432", status = "ACCEPT")
        assertThat(actual).isEqualTo(expected)
    }
}
