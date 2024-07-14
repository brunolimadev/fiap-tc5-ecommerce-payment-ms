package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessPaymentRepository extends JpaRepository<ProcessPaymentEntity, String> {

}
