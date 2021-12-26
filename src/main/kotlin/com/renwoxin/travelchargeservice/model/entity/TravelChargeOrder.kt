package com.renwoxin.travelchargeservice.model.entity

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "travel_charge_order")
data class TravelChargeOrder(
    @Id
    val id: Long? = 0,
    val companyId: String,
    val amount: String,
    val chargeStatus: String
)
