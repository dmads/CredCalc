package home.dma.services;

import home.dma.dto.PaymentsDTO;


import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;



public class PaymentServiceImpl implements PaymentService{

    private static final Logger logger = LogManager.getLogger(PaymentServiceImpl.class);

    public List<PaymentsDTO> calculate(long creditSum, int  creditDuration, double creditInterestRate) {

        logger.info("->" + creditSum + "|" + creditDuration + "|" + creditInterestRate);
        List<PaymentsDTO> result = new ArrayList<>();

        /*
         * Платеж по основному долгу paymentPrincipal;
         * Платеж по процентам       paymentInterest;
         * Остаток основного долга   remainingPrincipalDebt;
         * Общая сумма платежа       totalPaymentAmount;
         *
         * next                      totalPaymentAmount - remainingPrincipalDebt;
        */

        LocalDate startDate = LocalDate.now().plusMonths(1);
        int year = startDate.getYear();
        Month month = startDate.getMonth();

        double monthRate = creditInterestRate / 100. / 12.;
        long paymentPrincipal = Math.round(creditSum * monthRate / (1. - Math.pow(1. + monthRate, -creditDuration)));
        long remainingPrincipalDebt = creditSum;

        for (int i = 0; i < creditDuration; i++) {
            long paymentInterest = Math.round(remainingPrincipalDebt * monthRate);

            long totalPaymentAmount = paymentPrincipal - paymentInterest;

            PaymentsDTO payment = PaymentsDTO.builder()
                    .year(year)
                    .month(month.getValue())
                    .paymentPrincipal(paymentPrincipal)
                    .paymentInterest(paymentInterest)
                    .totalPaymentAmount(totalPaymentAmount)
                    .remainingPrincipalDebt(remainingPrincipalDebt)
                    .build();
            result.add(payment);

            remainingPrincipalDebt = remainingPrincipalDebt - totalPaymentAmount;

            month = month.plus(1);
            if (month.getValue() == 1) {
                year++;
            }
        }
        return result;
    }
}
