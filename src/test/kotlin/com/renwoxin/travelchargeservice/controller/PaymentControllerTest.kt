package com.renwoxin.travelchargeservice.controller

import com.nhaarman.mockitokotlin2.whenever
import com.renwoxin.travelchargeservice.dto.TravelChargePaymentDto
import com.renwoxin.travelchargeservice.exception.BusinessException
import com.renwoxin.travelchargeservice.exception.ErrorCode
import com.renwoxin.travelchargeservice.service.TravelChargeService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PaymentControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    protected lateinit var travelChargeService: TravelChargeService

    @Test
    fun `should return accept status when create payment given create payment request`() {
        // given
        val orderId = 93L
        val createPaymentRequest = """
            {
                "orderId": 93
            }
        """.trimIndent()

        whenever(travelChargeService.createPayment(orderId)).thenReturn(TravelChargePaymentDto(status = "ACCEPT"))

        // when
        val result = mvc.perform(
            post("/travel-charge-orders/$orderId/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPaymentRequest)

        )
        // then
        val actual = result.andReturn().response.contentAsString
        val expected = """
            {"data":{"status":"ACCEPT"},"success":true,"error":null}
        """.trimIndent()
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return order has been paid when create payment given order already paid exception`() {
        // given
        val orderId = 93L
        val createPaymentRequest = """
            {
                "orderId": 93
            }
        """.trimIndent()

        whenever(travelChargeService.createPayment(orderId)).thenThrow(BusinessException(ErrorCode.TRAVEL_ORDER_ALREADY_PAID))

        // when
        val result = mvc.perform(
            post("/travel-charge-orders/$orderId/payment")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createPaymentRequest)

        )
        // then
        val actual = result.andReturn().response.contentAsString
        val expected = """
            {"data":null,"success":false,"error":"Travel order has been paid already"}
        """.trimIndent()
        assertThat(actual).isEqualTo(expected)
    }
}
