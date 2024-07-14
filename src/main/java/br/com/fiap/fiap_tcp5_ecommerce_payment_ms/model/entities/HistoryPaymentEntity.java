package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "history_payment")
@Getter
@Setter
@NoArgsConstructor
public class HistoryPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_history_payment")
    private String idHistoryPayment;

    @Column(name = "status_history")
    @Enumerated(EnumType.STRING)
    private StatusHistoricoEnum statusHistoryEnum;

    @Column(name = "date_time_status")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeStatus;

    @Column(name = "date_time_end_stage")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime dateTimeEndStage;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_process_payment")
    private ProcessPaymentEntity processPayment;

    public HistoryPaymentEntity(StatusHistoricoEnum statusHistoricoEnum) {
        this.statusHistoryEnum = statusHistoricoEnum;
    }
    @PrePersist
    private void addDateTimeStatus(){
        this.dateTimeStatus = LocalDateTime.now();
    }

}
