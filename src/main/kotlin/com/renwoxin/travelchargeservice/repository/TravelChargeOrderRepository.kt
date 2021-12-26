package com.renwoxin.travelchargeservice.repository

import com.renwoxin.travelchargeservice.model.entity.TravelChargeOrder
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TravelChargeOrderRepository : CrudRepository<TravelChargeOrder, Long> {
}
