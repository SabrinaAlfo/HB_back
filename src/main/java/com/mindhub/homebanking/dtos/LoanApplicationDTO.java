package com.mindhub.homebanking.dtos;

public class LoanApplicationDTO {
    //tener id del préstamo, monto, cuotas y número de cuenta de destino
    private long loanId;
    private double amount;
    private int payments;
    private String accountNumber;


    public LoanApplicationDTO() {}

    public LoanApplicationDTO(long loanId, double amount, int payments, String accountNumber) {
        this.loanId = loanId;
        this.amount = amount;
        this.payments = payments;
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public long getLoanId() {
        return loanId;
    }

    public void setLoanId(long loanId) {
        this.loanId = loanId;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getPayments() {
        return payments;
    }

    public void setPayments(int payments) {
        this.payments = payments;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}