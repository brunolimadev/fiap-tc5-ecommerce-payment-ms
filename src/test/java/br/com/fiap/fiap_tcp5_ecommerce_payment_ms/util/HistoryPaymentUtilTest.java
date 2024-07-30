package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.util;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HistoryPaymentUtilTest {

    @Test
    void shouldBuildInitialHistory() {
        // Act
        List<HistoryPaymentEntity> history = HistoryPaymentUtil.gerarHistoricoInicial();

        // Assert
        assertNotNull(history, "A lista de histórico não deve ser nula");
        assertEquals(1, history.size(), "A lista de histórico deve conter um elemento");

        HistoryPaymentEntity entidade = history.get(0);
        assertNotNull(entidade, "A entidade de histórico não deve ser nula");
        assertEquals(StatusHistoricoEnum.PENDENTE, entidade.getStatusHistoryEnum(), "O status do histórico deve ser PENDENTE");
        assertNotNull(entidade.getDateTimeStatus(), "A data e hora do status não devem ser nulas");
        assertTrue(entidade.getDateTimeStatus().isBefore(LocalDateTime.now().plusSeconds(1)), "A data e hora do status deve ser antes do momento atual");
    }

}