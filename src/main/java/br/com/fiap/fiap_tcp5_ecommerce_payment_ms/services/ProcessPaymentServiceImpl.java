package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.converters.ProcessPaymentConverter;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentRequestDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.ProcessPaymentRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.CardDataService;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.HistoryProcessService;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.ProcessPaymentService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessPaymentServiceImpl implements ProcessPaymentService {

    private ProcessPaymentRepository processPaymentRepository;
    private HistoryProcessService historyProcessService;
    private CardDataService cardDataService;
    private ProcessPaymentRepository processPaymentService;

    public ProcessPaymentServiceImpl(ProcessPaymentRepository processPaymentRepository,
                                     HistoryProcessService historicoProcessamentoService,
                                     CardDataService dadosCartaoService,
                                     ProcessPaymentRepository processamentoPagamentoRepository) {
        this.processPaymentRepository = processPaymentRepository;
        this.historyProcessService = historicoProcessamentoService;
        this.cardDataService = dadosCartaoService;
        this.processPaymentService = processamentoPagamentoRepository;
    }
    @Override
    public List<PaymentResponseDTO> processPayment(PaymentRequestDTO dto)  {
        var entity = new ProcessPaymentConverter().toEntity(dto);
        entity = saveProcessPayment(entity);
        createNewHistoryPayment(entity.getIdProcessPayment());
        registerCardDetails(dto, entity);
        processPaymentRequest();
        return historyProcessService.findResultProcessByIdShoppingCart(dto.idShoppingCart());
    }


    private void registerCardDetails(PaymentRequestDTO dto, ProcessPaymentEntity entity) {
        cardDataService.save(dto.card(), dto.idShoppingCart(), entity.getIdProcessPayment());
    }

    private void createNewHistoryPayment(String idProcessPayment) {

        var historyPaymentEntity = new HistoryPaymentEntity();
        historyPaymentEntity.setStatusHistoryEnum(StatusHistoricoEnum.PENDENTE);

        Optional<ProcessPaymentEntity> result = processPaymentService.findById(idProcessPayment);

        if (result.isPresent()){
            historyPaymentEntity.setProcessPayment(result.get());
        }
        historyProcessService.criarHistorico(historyPaymentEntity);
    }

    private void processPaymentRequest() {
        historyProcessService.process();
    }

    @Transactional
    private ProcessPaymentEntity saveProcessPayment(ProcessPaymentEntity entity) {
        return processPaymentRepository.save(entity);
    }
}
