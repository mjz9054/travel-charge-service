package com.renwoxin.travelchargeservice.schedule

import com.renwoxin.travelchargeservice.service.TravelInvoiceService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class ScheduleService {

    @Autowired
    private lateinit var travelInvoiceService: TravelInvoiceService

    @Scheduled(cron = "\${scheduler.invoice.cron}")
    fun scheduleCreateInvoice() {
        travelInvoiceService.scheduleCreateInvoice()
    }
}
