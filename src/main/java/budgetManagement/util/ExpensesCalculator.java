package budgetManagement.util;

import budgetManagement.model.Expense;

import java.util.ArrayList;
import java.util.List;

public class ExpensesCalculator {


    public List<Expense> addTotalLine(List<Expense> expensesList) {
        double total = 0.0;
        for (Expense expense : expensesList) {
            total += expense.getAmount();
        }
        total = Math.floor(total * 100) / 100;
        Expense totalLine = new Expense(null, null, total, "TOTAL", null);
        List<Expense> expensesListWithTotal = new ArrayList<>(expensesList);
        expensesListWithTotal.add(totalLine);
        return expensesListWithTotal;
    }
}



