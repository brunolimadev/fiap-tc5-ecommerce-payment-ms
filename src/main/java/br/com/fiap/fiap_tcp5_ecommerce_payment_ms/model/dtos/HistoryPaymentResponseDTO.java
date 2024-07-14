package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cglib.core.Local;

import java.time.Instant;
import java.time.LocalDateTime;

public record HistoryPaymentResponseDTO(

        String idStatus,
        @JsonProperty("idProcessPayment")
        String idProcessPayment,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss.SSS")
        LocalDateTime dateTimeStartStage,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss.SSS")
        LocalDateTime dateTimeEndStage,
        StatusHistoricoEnum status,

        String idShoppingCart

) { }