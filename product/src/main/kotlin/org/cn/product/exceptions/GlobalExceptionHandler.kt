package org.cn.product.exceptions

import jakarta.validation.ConstraintViolationException
import org.cn.product.utils.ErrorMessageConstant
import org.cn.product.utils.ErrorMessageConstant.INTERNAL_SERVER_ERROR_MSG
import org.cn.product.utils.ErrorMessageConstant.NOT_FOUND_MESSAGE
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import kotlin.Exception

@RestControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any>? {
        val errors = ex.bindingResult.fieldErrors.joinToString(",") { "${it.field} : ${it.defaultMessage}" }
        return exceptionBuilder(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(exception: Exception) =
        exceptionBuilder(exception.message ?: INTERNAL_SERVER_ERROR_MSG, HttpStatus.INTERNAL_SERVER_ERROR)

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintsException(exception: ConstraintViolationException) : ResponseEntity<Any> {
       val errors = exception.constraintViolations.joinToString(",") { it.message  }
       return exceptionBuilder(errors, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(value = [NotSuchProductException::class])
    fun handle404Exception(exception: Exception) =
        exceptionBuilder(exception.message ?: NOT_FOUND_MESSAGE, HttpStatus.NOT_FOUND)

    private fun exceptionBuilder(errors: String, status: HttpStatus): ResponseEntity<Any> {
        val problemDetail = ProblemDetail.forStatus(status).apply {
            this.detail = errors
            this.title = status.reasonPhrase
        }
        return ResponseEntity(problemDetail, status)
    }

}