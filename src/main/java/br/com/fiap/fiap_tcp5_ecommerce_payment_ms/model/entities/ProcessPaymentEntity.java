package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "process_payment")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_process_payment")
    private String idProcessPayment;

    @Column(name = "id_shopping_cart")
    private String idShoppingCart;

    @Column(name = "valor_compra")
    private Double amount;

    @OneToMany(mappedBy = "processPayment")
    private List<CardDataEntity> cardData;

    @OneToMany(mappedBy = "processPayment")
    private List<HistoryPaymentEntity> paymentHistory;

    public ProcessPaymentEntity(String idShoppingCart) {
        this.idShoppingCart = idShoppingCart;
    }

}
