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
import com.renwoxin.travelchargeservice.dto.UnionPayPaymentDto
import com.renwoxin.travelchargeservice.model.response.UnionPayPaymentResponse
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
class UnionPayClientTest {
    @Mock
    private lateinit var restTemplate: RestTemplate

    @Mock
    private lateinit var appProperties: AppProperties

    @InjectMocks
    private lateinit var unionPayClient: UnionPayClient

    private val wireMockServer: WireMockServer = WireMockServer(5566)

    @BeforeAll
    fun setup() {
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());
        stubFor(
            post(urlEqualTo("/unionpay/payment")).willReturn(
                okJson(
                    """
                    {
                        "transactionId":"24762644145713599",
                        "status":"ACCEPT"
                    }
                    """.trimIndent()
                )
            )
        )
        ReflectionTestUtils.setField(unionPayClient, "restTemplate", RestTemplate())
    }

    @AfterAll
    fun teardown() {
        wireMockServer.stop();
    }

    @Test
    fun `should return payment status when create union payment given payment request`() {
        // given
        val orderPaymentDto =
            UnionPayPaymentDto(referenceId = "24762644145713501", amount = "366.5")
        whenever(appProperties.unionPayHost).thenReturn("http://localhost:5566")
        whenever(
            restTemplate.exchange(
                eq("http://localhost:5566//unionpay/payment"),
                eq(HttpMethod.POST),
                any(),
                any<ParameterizedTypeReference<UnionPayPaymentResponse>>()
            )
        ).thenCallRealMethod()

        // when
        val actual = unionPayClient.createPayment(orderPaymentDto)

        // then
        val expected =
            UnionPayPaymentResponse(transactionId = "24762644145713599", status = "ACCEPT")
        assertThat(actual).isEqualTo(expected)
    }
}
