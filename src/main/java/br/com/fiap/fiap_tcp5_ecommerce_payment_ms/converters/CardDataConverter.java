package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.converters;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.CardRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.CardDataEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CardDataConverter {

    public CardDataEntity toEntity(CardRequestDTO dto, String idRequestPayment){
        return CardDataEntity.builder()
                .typeCard(dto.type())
                .number(dto.number())
                .expiringDate(dto.expiringDate())
                .verificationCode(dto.verificationCode())
                .processPayment(new ProcessPaymentEntity(idRequestPayment))
                .build();
    }

    public List<CardDataEntity> toListEntity(CardRequestDTO card){

        List<CardDataEntity> listaDadosCartao = new ArrayList<>();
        var dadosCartaoEntity = new CardDataEntity();
        dadosCartaoEntity.setTypeCard(card.type());
        dadosCartaoEntity.setExpiringDate(card.expiringDate());
        dadosCartaoEntity.setVerificationCode(card.verificationCode());
        listaDadosCartao.add(dadosCartaoEntity);

        return listaDadosCartao;
    }

}
