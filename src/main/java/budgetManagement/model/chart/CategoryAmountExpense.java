package budgetManagement.model.chart;

public class CategoryAmountExpense {
    private String categoryName;
    private Double amount;

    public CategoryAmountExpense(String categoryName) {
        this.categoryName = categoryName;
        this.amount = 0.0;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CategoryAmountExpense{" +
                "categoryName='" + categoryName + '\'' +
                ", amount=" + amount +
                '}';
    }
}
