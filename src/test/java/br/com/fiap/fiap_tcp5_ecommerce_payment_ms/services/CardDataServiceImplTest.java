package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services;


import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.converters.CardDataConverter;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.exceptions.PaymentException;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.CardRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.CardDataEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.DataCardRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.ProcessPaymentRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.CardDataService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardDataServiceImplTest {

    @Mock
    private DataCardRepository repository;

    @Mock
    private ProcessPaymentRepository processPaymentRepository;

    @Mock
    private CardDataConverter converter;

    @Mock
    private CardDataService cardDataService;

    @Mock
    private CardDataEntity entity;

    @InjectMocks
    private CardDataServiceImpl service;

    @Mock
    private ProcessPaymentEntity processPaymentEntity;

    private CardRequestDTO dto;
    private String idRequestPayment;
    private String idProcessPayment;

    @BeforeEach
    void setUp() {
        dto = new CardRequestDTO("Credit","123456789456","10/24","325");
        idRequestPayment = UUID.randomUUID().toString();
        idProcessPayment = UUID.randomUUID().toString();
    }

    @Test
    void shouldSaveCardDataWithSucess() throws Exception {
        when(converter.toEntity(dto, idRequestPayment)).thenReturn(entity);
        when(processPaymentRepository.findById(idProcessPayment)).thenReturn(Optional.of(processPaymentEntity));

        service.save(dto, idRequestPayment, idProcessPayment);

        verify(converter).toEntity(dto, idRequestPayment);
        verify(processPaymentRepository).findById(idProcessPayment);
        verify(entity).setProcessPayment(processPaymentEntity);
        verify(repository).save(entity);
    }

    @Test
    void shouldSaveThrowsPaymentExceptionWhenProcessPaymentNotFound() {
        when(converter.toEntity(dto, idRequestPayment)).thenReturn(entity);
        when(processPaymentRepository.findById(idProcessPayment)).thenReturn(Optional.empty());

        assertThrows(PaymentException.class, () -> service.save(dto, idRequestPayment, idProcessPayment));

        verify(converter).toEntity(dto, idRequestPayment);
        verify(processPaymentRepository).findById(idProcessPayment);
        verify(repository, never()).save(any());
    }

}