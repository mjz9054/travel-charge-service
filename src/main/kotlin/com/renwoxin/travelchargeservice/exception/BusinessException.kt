package com.renwoxin.travelchargeservice.exception

import java.lang.RuntimeException

open class BusinessException constructor(val errorCode: ErrorCode) :
    RuntimeException(errorCode.errorMessage)
