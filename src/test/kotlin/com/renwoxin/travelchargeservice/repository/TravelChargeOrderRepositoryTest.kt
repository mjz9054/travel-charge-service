package com.renwoxin.travelchargeservice.repository

import com.renwoxin.travelchargeservice.model.entity.TravelChargeOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@DataJpaTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TravelChargeOrderRepositoryTest {
    @Autowired
    private lateinit var travelChargeOrderRepository: TravelChargeOrderRepository
    private val travelChargeOrder1 = TravelChargeOrder(
        id = 55L,
        companyId = "1001",
        amount = "236.5",
        chargeStatus = "UNPAID"
    )
    private val travelChargeOrder2 = TravelChargeOrder(
        id = 66L,
        companyId = "1001",
        amount = "366.5",
        chargeStatus = "UNPAID"
    )

    @BeforeEach
    fun setup() {
        travelChargeOrderRepository.save(travelChargeOrder1)
        travelChargeOrderRepository.save(travelChargeOrder2)
    }

    @AfterAll
    fun teardown() {
        travelChargeOrderRepository.delete(travelChargeOrder1)
        travelChargeOrderRepository.delete(travelChargeOrder2)
    }


    @Test
    fun `should return travel charge order when query order given order id and order exist`() {
        // given
        val orderId = 55L

        // when
        val actual = travelChargeOrderRepository.findById(orderId).get()

        // then
        val expected = travelChargeOrder1
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `should return updated travel charge order when update order given order`() {
        // given
        val travelChargeOrder = travelChargeOrder1

        // when
        val actual = travelChargeOrderRepository.save(travelChargeOrder.copy(chargeStatus = "COMPLETED"))

        // then
        val expected = travelChargeOrder1.copy(chargeStatus = "COMPLETED")
        assertThat(actual).isEqualTo(expected)
    }
}
