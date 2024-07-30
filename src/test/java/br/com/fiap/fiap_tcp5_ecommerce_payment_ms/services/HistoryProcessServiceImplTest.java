package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.MessagesEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.exceptions.PaymentException;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.HistoryPaymentRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.ProcessPaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class HistoryProcessServiceImplTest {

    @Mock
    private HistoryPaymentRepository historyPaymentRepository;

    @Mock
    private ProcessPaymentRepository processPaymentRepository;

    @InjectMocks
    private HistoryProcessServiceImpl historyProcessService;

    private HistoryPaymentEntity historyPaymentEntity;

    private PaymentResponseDTO dto;

    private List<String> newHistoryPayments;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        historyPaymentEntity = new HistoryPaymentEntity();
        newHistoryPayments = Arrays.asList("id1","id2");
        dto = new PaymentResponseDTO("153",
                "6516",
                LocalDateTime.now().minusSeconds(10),
                LocalDateTime.now(),
                StatusHistoricoEnum.APROVADO,
                "2121");
    }

    @Test
    public void shouldCreateHistoryWithSuccess() {
        HistoryPaymentEntity entity = new HistoryPaymentEntity();
        historyProcessService.criarHistorico(entity);
        verify(historyPaymentRepository, times(1)).save(entity);
    }

    @Test
    public void shouldCreateHistoryException() {
        doThrow(new RuntimeException()).when(historyPaymentRepository).save(any(HistoryPaymentEntity.class));
        PaymentException exception =
                assertThrows(PaymentException.class, () -> historyProcessService.criarHistorico(historyPaymentEntity));
        assertEquals(MessagesEnum.ERROR.getMessage(), exception.getMessage());
    }

    @Test
    public void shouldListNewPayments() {
        when(historyPaymentRepository.findNewHistoryPayments()).thenReturn(newHistoryPayments);
        List<String> result = historyProcessService.listarNovasSolicitacoes();
        assertEquals(newHistoryPayments, result);
    }

    @Test
    public void shouldProcessPaymentWithSuccess() {
        when(historyPaymentRepository.findNewHistoryPayments()).thenReturn(newHistoryPayments);
        historyProcessService.process();
        verify(historyPaymentRepository, times(1)).findNewHistoryPayments();
        verify(historyPaymentRepository, times(4)).findById(any(String.class));
        verify(historyPaymentRepository, atLeastOnce()).save(any(HistoryPaymentEntity.class));
    }

    @Test
    public void shouldFindProcessByIdShoppingCart_Found() {
        String idShoppingCart = "cart123";
        List<PaymentResponseDTO> responses = Arrays.asList(dto, dto);
        when(historyPaymentRepository.findProcessByIdShoppingCart(idShoppingCart)).thenReturn(responses);
        List<PaymentResponseDTO> result = historyProcessService.findProcessByIdShoppingCart(idShoppingCart);
        assertEquals(responses, result);
    }

    @Test
    public void shouldFindProcessByIdShoppingCart_NotFound() {
        when(historyPaymentRepository.findProcessByIdShoppingCart(anyString())).thenReturn(Collections.emptyList());
        PaymentException exception =
                assertThrows(PaymentException.class, () -> historyProcessService.findProcessByIdShoppingCart(anyString()));
        assertEquals(MessagesEnum.PROCESSING_BY_ID_SHOPP_CART_NOT_FOUND.getMessage(), exception.getMessage());
    }

    @Test
    public void shouldFindResultProcessByIdShoppingCartWithSuccess() {
        List<PaymentResponseDTO> history = Arrays.asList(dto,dto);
        when(historyPaymentRepository.findProcessByIdShoppingCart(anyString())).thenReturn(history);
        List<PaymentResponseDTO> result = historyProcessService.findResultProcessByIdShoppingCart(anyString());
        assertFalse(result.isEmpty());
        assertEquals(StatusHistoricoEnum.APROVADO, result.get(0).status());
    }

    @Test
    public void shouldFilterResultWithSuccess() {
        List<PaymentResponseDTO> history = Arrays.asList(dto,dto);
        List<PaymentResponseDTO> result = historyProcessService.filtrarResultado(history);
        assertFalse(result.isEmpty());
        assertEquals(StatusHistoricoEnum.APROVADO, result.get(0).status());
    }

}