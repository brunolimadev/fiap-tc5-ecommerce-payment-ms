package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services;

import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.MessagesEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums.StatusHistoricoEnum;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.exceptions.PaymentException;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.dtos.PaymentResponseDTO;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.HistoryPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.model.entities.ProcessPaymentEntity;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.HistoricoPagamentoRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.repositories.ProcessPaymentRepository;
import br.com.fiap.fiap_tcp5_ecommerce_payment_ms.services.interfaces.HistoryProcessService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class HistoricoProcessamentoServiceImpl implements HistoryProcessService {

    private HistoricoPagamentoRepository repository;

    private ProcessPaymentRepository processamentoPagamentoRepository;

    public HistoricoProcessamentoServiceImpl(HistoricoPagamentoRepository repository, ProcessPaymentRepository processamentoPagamentoRepository) {
        this.repository = repository;
        this.processamentoPagamentoRepository = processamentoPagamentoRepository;
    }


    @Override
    @Transactional
    public void criarHistorico(HistoryPaymentEntity historicoStatusPagamento) {
        try {
            repository.save(historicoStatusPagamento);
        } catch (Exception e) {
            throw new PaymentException(MessagesEnum.ERROR.getMessage());
        }
    }

    @Override
    public List<String> listarNovasSolicitacoes() {
        return repository.findNewHistoryPayments();
    }

    @Override
    public void process() {
        var listaProcessamento =  repository.findNewHistoryPayments();

        if(!listaProcessamento.isEmpty()){
            processarPendentes(listaProcessamento);
        }

    }
    @Override
    public List<PaymentResponseDTO> findProcessByIdShoppingCart(String idCarrinho) {
        var result = repository.findProcessByIdShoppingCart(idCarrinho);
        if(result.isEmpty()){
            throw new PaymentException(MessagesEnum.PROCESSING_BY_ID_SHOPP_CART_NOT_FOUND.getMessage());
        }
        return result;
    }
    @Override
    public List<PaymentResponseDTO> findResultProcessByIdShoppingCart(String idCarrinho) {
        List<PaymentResponseDTO> history = repository.findProcessByIdShoppingCart(idCarrinho);
        return filtrarResultado(history);
    }

    private void processarPendentes(List<String> listaIdsStatusProcessamentosPendentes) {

        listaIdsStatusProcessamentosPendentes
                .forEach(idProcessamentoPendente -> {atualizaHistoricoPendente(idProcessamentoPendente);

            Optional<HistoryPaymentEntity> result = repository.findById(idProcessamentoPendente);
            String idProcessamentoPagamento = "";
            if (result.isPresent()) {
                var historicoPagamento = result.get();
                idProcessamentoPagamento = historicoPagamento.getIdHistoryPayment();
            }

            processarEtapa(idProcessamentoPagamento, StatusHistoricoEnum.PROCESSANDO);
            processarEtapa(idProcessamentoPagamento, finalizarProcessamento());
        });
    }

    private StatusHistoricoEnum finalizarProcessamento() {
        var isApproved = new Random().nextBoolean();
        return isApproved ? StatusHistoricoEnum.APROVADO : StatusHistoricoEnum.RECUSADO;
    }

    private void processarEtapa(String idSolicitacaoPagamento, StatusHistoricoEnum historicoEnum) {

        HistoryPaymentEntity historicoEmProcessamento = new HistoryPaymentEntity();
        historicoEmProcessamento.setStatusHistoryEnum(historicoEnum);
        historicoEmProcessamento.setDateTimeEndStage(LocalDateTime.now());

        var idProcessamentoInicial = repository.findIdProcessPaymentByIdStatusPayment(idSolicitacaoPagamento);
        Optional<ProcessPaymentEntity> processamentoPagamento = processamentoPagamentoRepository.findById(idProcessamentoInicial);

        if (processamentoPagamento.isPresent()) {
            historicoEmProcessamento.setProcessPayment(processamentoPagamento.get());
        }
        repository.save(historicoEmProcessamento);
    }

    private void atualizaHistoricoPendente(String idProcessamentoPendente) {
        Optional<HistoryPaymentEntity> historicoInicial = repository.findById(idProcessamentoPendente);

        if (historicoInicial.isPresent()) {
            HistoryPaymentEntity historicoPendente = historicoInicial.get();
            historicoPendente.setDateTimeEndStage(LocalDateTime.now());
            repository.save(historicoPendente);
        }
    }

    private List<PaymentResponseDTO> filtrarResultado(List<PaymentResponseDTO> history) {

        Optional<PaymentResponseDTO> filtro = history.stream()
                        .filter(p -> p.status().equals(StatusHistoricoEnum.APROVADO)
                                  || p.status().equals(StatusHistoricoEnum.RECUSADO))
                        .max(Comparator.comparing(PaymentResponseDTO::dateTimeEndStage));

        return filtro.map(Collections::singletonList)
                .orElseGet(Collections::emptyList);
    }
}
