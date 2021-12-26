package com.renwoxin.travelchargeservice.controller

import com.renwoxin.travelchargeservice.dto.ResultDto
import com.renwoxin.travelchargeservice.model.request.CreatePaymentRequest
import com.renwoxin.travelchargeservice.model.response.CreatePaymentResponse
import com.renwoxin.travelchargeservice.service.TravelChargeService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/travel-charge-orders")
class PaymentController {

    @Autowired
    private lateinit var travelChargeService: TravelChargeService

    @PostMapping("/{orderId}/payment", produces = ["application/json"])
    fun createPayment(request: CreatePaymentRequest): ResultDto<CreatePaymentResponse> {
        val travelChargePaymentDto = travelChargeService.createPayment(request.orderId)
        return ResultDto(
            CreatePaymentResponse(status = travelChargePaymentDto.status),
            success = true
        )
    }
}
