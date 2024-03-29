package budgetManagement.model;

import java.time.LocalDate;
import java.util.UUID;

public class Expense {
    private UUID id;
    private LocalDate date;
    private double amount;
    private String description;
    private UUID categoryId;

    public Expense(UUID id, LocalDate date, double amount, String description, UUID categoryId) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.description = description;
        this.categoryId = categoryId;
    }

    public UUID getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    @Override
    public String toString() {
        return
                id.toString() + "  |  "
                        + date + "  |  "
                        + amount + "  |  "
                        + description + "  |  "
                        + categoryId + "  |  ";
    }
}
