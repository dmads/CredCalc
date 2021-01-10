package home.dma.pages;

import home.dma.dto.PaymentsDTO;
import home.dma.services.PaymentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.tapestry5.Block;
import org.apache.tapestry5.annotations.*;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;
import java.util.List;

@Import(stylesheet="context:Index.css")
public class Index {
    private static final Logger logger = LogManager.getLogger(Index.class);

    @Inject
    private AjaxResponseRenderer ajaxResponseRenderer;

    @Property
    @ActivationRequestParameter("sum")
    private long creditSum;

    @Property
    @ActivationRequestParameter("duration")
    private int  creditDuration;

    @Property
    @ActivationRequestParameter("rate")
    private double creditInterestRate;

    @SetupRender
    private void setup() {
        if(creditSum == 0) {
            creditSum = PaymentService.INITIAL_CREDIT_SUM;
        }

        if(creditDuration == 0) {
            creditDuration = PaymentService.INITIAL_CREDIT_DURATION;
        }

        if(creditInterestRate == 0) {
            creditInterestRate = PaymentService.INITIAL_CREDIT_INTEREST_RATE;
        }
    }

    @InjectComponent
    private Zone paymentsListzone;

    @Inject
    private Request request;

    Object onSuccess() {
        ajaxResponseRenderer.addRender(paymentsListzone);
        return null;
    }

    @Inject
    PaymentService paymentService;

    public List<PaymentsDTO> getPaymentsList() {
        return paymentService.calculate(creditSum, creditDuration, creditInterestRate);
    }
}
