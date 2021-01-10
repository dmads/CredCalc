package home.dma.services;


import home.dma.dto.PaymentsDTO;

import java.util.List;

public interface PaymentService {

    public static final long INITIAL_CREDIT_SUM = 100_000;

    public static final int  INITIAL_CREDIT_DURATION = 12;

    public static final double INITIAL_CREDIT_INTEREST_RATE = 14.9;


    List<PaymentsDTO> calculate(long creditSum, int  creditDuration, double creditInterestRate);

}
