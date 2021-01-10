package home.dma.dto;

import lombok.Getter;
import lombok.Builder;

@Builder
@Getter
public class PaymentsDTO {

    private int month;

    private int year;
    /*
     * Платеж по основному долгу
     * */
    private long paymentPrincipal;
    /*
     * Платеж по процентам
     * */
    private long paymentInterest;
    /*
     * Остаток основного долга
     * */
    private long remainingPrincipalDebt;
    /*
     * Общая сумма платежа
     * */
    private long totalPaymentAmount;

}
