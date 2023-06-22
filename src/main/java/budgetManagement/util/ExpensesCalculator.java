package budgetManagement.util;

import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.model.chart.CategoryAmountExpense;
import budgetManagement.model.chart.MonthAmountExpense;

import java.time.Month;
import java.util.*;

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
        List<MonthAmountExpense> monthAmountExpensesList = new ArrayList<>();
        List<Month> monthsList = new ArrayList<>();
        if (!expensesList.isEmpty()) {
            monthsList.add(expensesList.get(expensesList.size() - 1).getDate().getMonth());
            for (Expense expense : expensesList) {
                Month currentMonth = expense.getDate().getMonth();
                if (!monthsList.contains(currentMonth)) {
                    monthsList.add(currentMonth);
                }
            }
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
            }
            Collections.sort(monthAmountExpensesList, Comparator.comparing(MonthAmountExpense::getMonth));
        }
        return monthAmountExpensesList;
    }

    public List<CategoryAmountExpense> getChartData2(List<Expense> expensesList, List<Category> categoriesList) {
        List<String> categoriesNamesList = new ArrayList<>();
        Map<UUID, String> categoriesMap = new HashMap<>();
        for (Category category : categoriesList) {
            categoriesMap.put(category.getId(), category.getName());
        }

        for (Expense expense : expensesList) {
            UUID categoryId = expense.getCategoryId();
            String categoryName = categoriesMap.get(categoryId);
            if (!categoriesNamesList.contains(categoryName)) {
                categoriesNamesList.add(categoryName);
            }
        }
        List<CategoryAmountExpense> categoryAmountExpensesList = new ArrayList<>();
        for (String name : categoriesNamesList) {
            CategoryAmountExpense categoryAmountExpense = new CategoryAmountExpense(name);
            Double amount = 0.0;
            for (Expense expense : expensesList) {
                UUID categoryId = expense.getCategoryId();
                String categoryName = categoriesMap.get(categoryId);
                if (name.equals(categoryName)) {
                    amount = amount + expense.getAmount();
                }
            }
            categoryAmountExpense.setAmount(amount);
            categoryAmountExpensesList.add(categoryAmountExpense);
        }
        return categoryAmountExpensesList;
    }
}



