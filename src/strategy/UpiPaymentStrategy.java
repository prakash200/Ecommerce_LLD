package strategy;

import models.Payment;

public class UpiPaymentStrategy implements PaymentStrategy{
    @Override
    public Payment pay(Double amount) {
        System.out.printf("\n\nPayment of %f paid by UPI ", amount);
        return new Payment(amount);
    }
}
