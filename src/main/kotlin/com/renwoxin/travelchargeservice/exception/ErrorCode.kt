package com.renwoxin.travelchargeservice.exception

enum class ErrorCode(val code: Int, val errorMessage: String) {
    TRAVEL_ORDER_ALREADY_PAID(10001, "Travel order has been paid already")
}
