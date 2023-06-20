package budgetManagement.model.chart;

import java.time.Month;

public class MonthAmountIncome {
    private Month month;
    private Double amount;

    public MonthAmountIncome(Month month) {
        this.month = month;
        this.amount = 0.0;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Month getMonth() {
        return month;
    }

   
}
