package com.renwoxin.travelchargeservice.exception

import com.renwoxin.travelchargeservice.dto.ResultDto
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

internal class GlobalExceptionHandlerTest {

    private val globalExceptionHandler = GlobalExceptionHandler()

    @Test
    fun `should return responseEntity when handling errorException`() {
        // given
        val exception = BusinessException(ErrorCode.TRAVEL_ORDER_ALREADY_PAID)

        // when
        val responseEntity = globalExceptionHandler.handleErrorException(exception)

        // then
        assertThat(responseEntity.statusCode).isEqualTo(HttpStatus.OK)
        assertThat(responseEntity.body).isEqualTo(
            ResultDto(
                null,
                false,
                "Travel order has been paid already"
            )
        )
    }
}
