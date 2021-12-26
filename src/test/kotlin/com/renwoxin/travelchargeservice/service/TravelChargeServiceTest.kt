package com.renwoxin.travelchargeservice.service

import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.renwoxin.travelchargeservice.dto.TravelChargePaymentDto
import com.renwoxin.travelchargeservice.dto.UnionPayPaymentDto
import com.renwoxin.travelchargeservice.exception.BusinessException
import com.renwoxin.travelchargeservice.exception.ErrorCode
import com.renwoxin.travelchargeservice.gateway.UnionPayClient
import com.renwoxin.travelchargeservice.model.entity.TravelChargeOrder
import com.renwoxin.travelchargeservice.model.response.UnionPayPaymentResponse
import com.renwoxin.travelchargeservice.repository.TravelChargeOrderRepository
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import java.util.Optional

@ExtendWith(MockitoExtension::class)
class TravelChargeServiceTest {

    @Mock
    private lateinit var travelChargeOrderRepository: TravelChargeOrderRepository

    @Mock
    private lateinit var unionPayClient: UnionPayClient

    @InjectMocks
    private lateinit var travelChargeService: TravelChargeService

    @Test
    fun `should call union pay for payment when create payment request given order id and order is not paid`() {
        // given
        val orderId = 55L
        val travelChargeOrder = TravelChargeOrder(
            id = 55L,
            companyId = "1001",
            amount = "1000.0",
            chargeStatus = "UNPAID",
            invoiceStatus = "PROCESSING"
        )
        whenever(travelChargeOrderRepository.findById(orderId)).thenReturn(
            Optional.of(
                travelChargeOrder
            )
        )

        val orderPaymentDto = UnionPayPaymentDto(
            referenceId = orderId.toString(),
            amount = "1000.0"
        )
        val unionPayPaymentResponse = UnionPayPaymentResponse(
            status = "ACCEPT",
            transactionId = "24762644145710601"
        )
        whenever(unionPayClient.createPayment(orderPaymentDto)).thenReturn(unionPayPaymentResponse)
        whenever(
            travelChargeOrderRepository.save(travelChargeOrder.copy(chargeStatus = "PROCESSING"))
        ).thenReturn(
            travelChargeOrder.copy(chargeStatus = "PROCESSING")
        )

        // when
        val actual = travelChargeService.createPayment(orderId = orderId)

        // then
        val expected = TravelChargePaymentDto(status = "ACCEPT")
        verify(
            travelChargeOrderRepository, times(1)
        ).save(
            travelChargeOrder.copy(chargeStatus = "PROCESSING")
        )
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should throw order has been paid exception when create payment request given order id and order is paid`() {
        // given
        val orderId = 55L
        val travelChargeOrder = TravelChargeOrder(
            id = 55L,
            companyId = "1001",
            amount = "1000.0",
            chargeStatus = "COMPLETED",
            invoiceStatus = "PROCESSING"
        )
        whenever(travelChargeOrderRepository.findById(orderId)).thenReturn(Optional.of(travelChargeOrder))

        // when
        val actual = catchThrowable { travelChargeService.createPayment(orderId = orderId) }

        // then
        assertThat(actual).isExactlyInstanceOf(BusinessException::class.java)
        assertThat((actual as BusinessException).errorCode).isEqualTo(ErrorCode.TRAVEL_ORDER_ALREADY_PAID)
    }
}


