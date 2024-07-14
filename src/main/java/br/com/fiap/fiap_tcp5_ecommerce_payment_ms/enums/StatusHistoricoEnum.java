package br.com.fiap.fiap_tcp5_ecommerce_payment_ms.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StatusHistoricoEnum {

    PENDENTE(1,"Pagamento Pendente"),
    PROCESSANDO(2,"Pagamento em processamento"),
    APROVADO(3,"Pagamento Aprovado"),
    RECUSADO(4,"Pagamento Recusado"),
    NOT_FOUND(0,"Histórico Não Encontrado");

    private final int id;
    private final String descricao;

}
