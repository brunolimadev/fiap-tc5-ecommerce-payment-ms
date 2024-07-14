package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.util;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryPaymentUtil {

    public static List<HistoryPaymentEntity> gerarHistoricoInicial() {
        var initialHistory = new HistoryPaymentEntity();
        initialHistory.setStatusHistoryEnum(StatusHistoricoEnum.PENDENTE);
        initialHistory.setDateTimeStatus(LocalDateTime.now());
        List<HistoryPaymentEntity> listaHistorico = new ArrayList<>();
        listaHistorico.add(initialHistory);
        return  listaHistorico;
    }
}
