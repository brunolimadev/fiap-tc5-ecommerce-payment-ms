package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.exceptions;

public class PaymentException extends RuntimeException {

    public PaymentException(String message) {
        super(message);
    }
}
