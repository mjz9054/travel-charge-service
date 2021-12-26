package com.renwoxin.travelchargeservice.service

import com.renwoxin.travelchargeservice.common.TravelOrderChargeStatus
import com.renwoxin.travelchargeservice.dto.UnionPayPaymentDto
import com.renwoxin.travelchargeservice.dto.TravelChargePaymentDto
import com.renwoxin.travelchargeservice.gateway.UnionPayClient
import com.renwoxin.travelchargeservice.repository.TravelChargeOrderRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TravelChargeService {
    @Autowired
    private lateinit var travelChargeOrderRepository: TravelChargeOrderRepository

    @Autowired
    private lateinit var unionPayClient: UnionPayClient

    fun createPayment(orderId: Long): TravelChargePaymentDto {
        val order = travelChargeOrderRepository.findById(orderId).get()
        if (order.chargeStatus != TravelOrderChargeStatus.UNPAID.name) {
            TODO()
        }
        val payPaymentResponse = unionPayClient.createPayment(
            UnionPayPaymentDto(
                referenceId = order.id.toString(), amount = order.amount
            )
        )
        travelChargeOrderRepository.save(order.copy(chargeStatus = TravelOrderChargeStatus.PROCESSING.name))
        return TravelChargePaymentDto(status = payPaymentResponse.status)
    }
}
