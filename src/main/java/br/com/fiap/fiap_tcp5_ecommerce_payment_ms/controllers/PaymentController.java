package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.controllers;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.HistoryProcessService;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.ProcessPaymentService;
import jakarta.validation.Valid;
import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payments")
public class PaymentController {

    ProcessPaymentService processPaymentService;

    HistoryProcessService historyProcessService;

    public PaymentController(ProcessPaymentService processPaymentService,
            HistoryProcessService historyProcessService) {
        this.processPaymentService = processPaymentService;
        this.historyProcessService = historyProcessService;
    }

    @SneakyThrows
    @PostMapping
    public ResponseEntity<?> processPayment(@Valid @RequestBody PaymentRequestDTO dto) {
        var listPaymentResponseDto = processPaymentService.processPayment(dto);
        return ResponseEntity.ok(listPaymentResponseDto);
    }

    @GetMapping("/{idShoppingCart}")
    public ResponseEntity<?> findProcessamentoByIdCarrinho(@PathVariable String idShoppingCart) {
        var listPaymentResponseDto = historyProcessService.findProcessByIdShoppingCart(idShoppingCart);
        return ResponseEntity.ok(listPaymentResponseDto);
    }

}
