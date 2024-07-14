package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProcessPaymentService {

    List<PaymentResponseDTO> processPayment(PaymentRequestDTO dto) throws InterruptedException;

}
