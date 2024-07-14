package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;

import java.util.List;

public interface HistoryProcessService {

    void criarHistorico(HistoryPaymentEntity historicoStatusPagamento);

    List<String> listarNovasSolicitacoes();

    void process();

    List<PaymentResponseDTO> findProcessByIdShoppingCart(String idCarrinho);

    List<PaymentResponseDTO> findResultProcessByIdShoppingCart(String idCarrinho);
}
