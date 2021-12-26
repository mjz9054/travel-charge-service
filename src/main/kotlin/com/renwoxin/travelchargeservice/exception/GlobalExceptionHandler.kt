package com.renwoxin.travelchargeservice.exception

import com.renwoxin.travelchargeservice.dto.ResultDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleExceptionInternal(
        ex: Exception, body: Any?, headers: HttpHeaders, status: HttpStatus, request: WebRequest
    ): ResponseEntity<Any> {
        val resultDto = ResultDto(null, false, ex.message)
        return super.handleExceptionInternal(ex, resultDto, headers, status, request)
    }

    @ExceptionHandler(BusinessException::class)
    fun handleErrorException(e: BusinessException): ResponseEntity<ResultDto<Nothing>> {
        val resultDto = ResultDto(null, false, e.message)
        return ResponseEntity.status(HttpStatus.OK).body(resultDto)
    }
}
