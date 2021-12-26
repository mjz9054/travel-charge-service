package com.renwoxin.travelchargeservice.gateway

import com.renwoxin.travelchargeservice.dto.UnionPayPaymentDto
import com.renwoxin.travelchargeservice.model.response.UnionPayPaymentResponse
import org.springframework.stereotype.Component

@Component
class UnionPayClient {

    fun createPayment(orderPaymentDto: UnionPayPaymentDto): UnionPayPaymentResponse {
        TODO()
    }
}
