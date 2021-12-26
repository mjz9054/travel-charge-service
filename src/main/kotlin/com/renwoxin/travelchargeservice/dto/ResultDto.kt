package com.renwoxin.travelchargeservice.dto

data class ResultDto<T>(
    val data: T? = null, val success: Boolean, val error: String? = null
)

