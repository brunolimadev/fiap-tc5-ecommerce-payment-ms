package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.CardRequestDTO;

public interface CardDataService {

    void save(CardRequestDTO dadosCartao, String idRequestPayment, String idRequisicaoPagamento);
}
