
package com.example.stockmedication.domain;

import java.time.LocalDate;
public class Batch implements Comparable<Batch>
{
    private final String batchNo;
    private int quantity;
    private final LocalDate expiry;
    //Konstruktor
    public Batch(String batchNo, int quantity, LocalDate expiry)
    {
        if (batchNo == null || batchNo.isBlank()) throw new IllegalArgumentException("batchNo required");
        if (quantity < 0) throw new IllegalArgumentException("quantity cannot be negative");
        if (expiry == null) throw new IllegalArgumentException("expiry required");
        this.batchNo = batchNo.trim();
        this.quantity = quantity;
        this.expiry = expiry;
    }

    public String getBatchNo() { return batchNo; }
    public int getQuantity() { return quantity; }
    public LocalDate getExpiry() { return expiry; }

    public void take(int n) {
        if (n < 0 || n > quantity) throw new IllegalArgumentException("invalid take amount");
        quantity -= n;
    }

    public boolean isExpired(LocalDate ref) {
        return expiry.isBefore(ref); // abgelaufen, wenn expiry < ref
    }

    @Override
    public int compareTo(Batch o) {
        int cmp = this.expiry.compareTo(o.expiry);
        return (cmp != 0) ? cmp : this.batchNo.compareToIgnoreCase(o.batchNo);
    }
}

