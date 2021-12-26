package com.renwoxin.travelchargeservice.schedule

import com.renwoxin.travelchargeservice.service.TravelInvoiceService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
class ScheduleServiceTest {
    @Mock
    private lateinit var travelInvoiceService: TravelInvoiceService

    @Test
    fun `should invoke schedule create invoice when enable Scheduling`() {
        // TODO
    }
}
