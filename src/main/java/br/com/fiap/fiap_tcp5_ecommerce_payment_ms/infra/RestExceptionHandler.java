package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.infra;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.exceptions.PaymentException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(PaymentException.class)
    private ResponseEntity<String> processPaymentError(PaymentException e){
        return ResponseEntity.ok(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> validationException(MethodArgumentNotValidException ex){

        var builder = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach( error -> builder.append(error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(builder.toString());
    }

}
