package models;

import enums.PaymentStatus;

import java.time.LocalDateTime;

public class Payment {


    private Double amount;
    private LocalDateTime paymentTime;
    private PaymentStatus status;

    public Payment( Double amount){
        this.amount = amount;
        this.paymentTime = LocalDateTime.now();
        this.status = PaymentStatus.Success;
    }
    

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(LocalDateTime paymentTime) {
        this.paymentTime = paymentTime;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }
}
