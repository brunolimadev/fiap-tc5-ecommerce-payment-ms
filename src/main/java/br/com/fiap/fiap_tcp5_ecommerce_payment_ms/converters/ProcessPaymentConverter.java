package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.converters;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.util.HistoryPaymentUtil;
import org.springframework.stereotype.Component;

@Component
public class ProcessPaymentConverter {

    public ProcessPaymentEntity toEntity(PaymentRequestDTO dto){
        return ProcessPaymentEntity.builder()
                .idShoppingCart(dto.idShoppingCart())
                .cardData(new CardDataConverter().toListEntity(dto.card()))
                .paymentHistory(HistoryPaymentUtil.gerarHistoricoInicial())
                .amount(dto.amount())
                .build();
    }

}
