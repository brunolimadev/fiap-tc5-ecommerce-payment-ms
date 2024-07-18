package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record PaymentRequestDTO(

                @NotBlank(message = "Informe o id do carrinho de compras") String idShoppingCart,

                @Valid CardRequestDTO card,

                double amount) {
}