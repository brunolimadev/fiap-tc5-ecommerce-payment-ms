package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_data")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardDataEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_card_data")
    private String idCardData;

    @Column(name = "type_card")
    private String typeCard;

    @Column(name = "number")
    private String number;

    @Column(name = "expiring_date")
    private String expiringDate;

    @Column(name = "verification_code")
    private String verificationCode;

    @ManyToOne
    @JoinColumn(name = "id_process_payment")
    private ProcessPaymentEntity processPayment;

}
