package com.renwoxin.travelchargeservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AppProperties {

    @Value("\${gateway.unionpay.host}")
    var unionPayHost: String = ""

    @Value("\${gateway.invoice.host}")
    var invoiceHost: String = ""
}
