package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoricoPagamentoRepository extends JpaRepository<HistoryPaymentEntity, String> {

    @Query(value = "SELECT h.idHistoryPayment FROM HistoryPaymentEntity h " +
            "WHERE h.statusHistoryEnum = PENDENTE AND h.dateTimeEndStage IS NULL ")
    List<String> findNewHistoryPayments();

    @Query(value = "SELECT h.id_process_payment FROM history_payment h" +
            " WHERE h.id_history_payment = :param", nativeQuery = true)
    String findIdProcessPaymentByIdStatusPayment(@Param("param") String idStatusPayment);

    @Query(value = "SELECT new br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO" +
            " (h.idHistoryPayment, h.processPayment.idProcessPayment, h.dateTimeStatus," +
            " h.dateTimeEndStage, h.statusHistoryEnum, h.processPayment.idShoppingCart) " +
            " FROM HistoryPaymentEntity h JOIN h.processPayment rp WHERE rp.idShoppingCart = :idShoppingCart" +
            " ORDER BY h.dateTimeStatus")
    List<PaymentResponseDTO> findProcessByIdShoppingCart(@Param("idShoppingCart") String idShoppingCart);

}
