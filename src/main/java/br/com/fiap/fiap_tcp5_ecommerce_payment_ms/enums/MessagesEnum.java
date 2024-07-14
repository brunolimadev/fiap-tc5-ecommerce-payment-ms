package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MessagesEnum {

    PROCESSING_BY_ID_SHOPP_CART_NOT_FOUND("Não foram encontrados processamentos para o ID do carrinho informado!"),
    PROCESSING_BY_ID_NOT_FOUND("Não foram encontrados processamentos para o id: "),
    ERROR("Ocorreu um erro inesperado!");

    private String message;

    }
