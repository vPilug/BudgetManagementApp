package budgetManagement.model;

import java.time.LocalDate;
import java.util.UUID;

public class Income {
    private UUID id;
    private LocalDate date;
    private double amount;
    private String source;

    public Income(UUID id, LocalDate date, double amount, String source) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.source = source;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public double getAmount() {
        return amount;
    }

    public String getSource() {
        return source;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return
                id.toString() + "  |  "
                        + date + "  |  "
                + amount + "  |  "
                + source + "  |  ";
    }

}
