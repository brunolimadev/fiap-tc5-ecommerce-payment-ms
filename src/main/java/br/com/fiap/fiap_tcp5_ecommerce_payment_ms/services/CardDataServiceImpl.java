package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.converters.CardDataConverter;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.MessagesEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.exceptions.PaymentException;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.CardRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.DataCardRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.ProcessPaymentRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.CardDataService;
import org.springframework.stereotype.Service;

@Service
public class CardDataServiceImpl implements CardDataService {

    private DataCardRepository repository;

    private CardDataConverter converter;

    private ProcessPaymentRepository processPaymentRepository;

    public CardDataServiceImpl(DataCardRepository repository, CardDataConverter converter,
                               ProcessPaymentRepository processamentoPagamentoRepository) {
        this.repository = repository;
        this.converter = converter;
        this.processPaymentRepository = processamentoPagamentoRepository;
    }

    @Override
    public void save(CardRequestDTO dto, String idRequestPayment, String idProcessPayment) {
        var entity = converter.toEntity(dto, idRequestPayment);
        ProcessPaymentEntity processPaymentEntity = processPaymentRepository.findById(idProcessPayment)
                .orElseThrow(() ->
                        new PaymentException(MessagesEnum.PROCESSING_BY_ID_NOT_FOUND.getMessage() + idProcessPayment));

        entity.setProcessPayment(processPaymentEntity);
        repository.save(entity);
    }
}
