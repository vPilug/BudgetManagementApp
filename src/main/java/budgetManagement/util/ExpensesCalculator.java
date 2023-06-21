package budgetManagement.util;

import budgetManagement.model.Expense;
import budgetManagement.model.chart.MonthAmountExpense;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

    public List<MonthAmountExpense> getChartData(List<Expense> expensesList) {
        Month endDate = expensesList.get(0).getDate().getMonth();
        List<Month> monthsList = new ArrayList<>();
        monthsList.add(expensesList.get(expensesList.size() - 1).getDate().getMonth());
        for (Expense expense : expensesList) {
            Month currentMonth = expense.getDate().getMonth();
            if (!monthsList.contains(currentMonth)) {
                monthsList.add(currentMonth);
            }
        }
        List<MonthAmountExpense> monthAmountExpensesList = new ArrayList<>();
        for (Month month : monthsList) {
            MonthAmountExpense monthAmountExpense = new MonthAmountExpense(month);
            for (Expense expense : expensesList) {
                if (month.equals(expense.getDate().getMonth())) {
                    Double currentAmount = monthAmountExpense.getAmount();
                    Double expenseAmount = expense.getAmount();
                    monthAmountExpense.setAmount(currentAmount + expenseAmount);
                }
            }
            monthAmountExpensesList.add(monthAmountExpense);
            Collections.sort(monthAmountExpensesList, Comparator.comparing(MonthAmountExpense::getMonth));
        }
        return monthAmountExpensesList;
    }
}



