package strategy;

import models.Payment;

public interface PaymentStrategy {

    Payment pay(Double amount);
}
