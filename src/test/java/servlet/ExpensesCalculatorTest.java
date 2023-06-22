package servlet;

import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.model.chart.CategoryAmountExpense;
import budgetManagement.model.chart.MonthAmountExpense;
import budgetManagement.util.ExpensesCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpensesCalculatorTest {

    private ExpensesCalculator expensesCalculator;

    @BeforeEach
    public void setUp() {
        expensesCalculator = new ExpensesCalculator();
    }

    @Test
    public void testAddTotalLine() {
        List<Expense> expensesList = new ArrayList<>();
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 10), 100.0, null, null));
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 15), 50.0, null, null));

        List<Expense> expensesListWithTotal = expensesCalculator.addTotalLine(expensesList);

        Assertions.assertEquals(3, expensesListWithTotal.size());
        Expense totalLine = expensesListWithTotal.get(expensesListWithTotal.size() - 1);
        Assertions.assertEquals(150.0, totalLine.getAmount());
    }


    @Test
    public void testGetChartData() {
        List<Expense> expensesList = new ArrayList<>();
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 10), 100.0, "Food", null));
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 15), 50.0, "Transportation", null));
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.FEBRUARY, 20), 200.0, "Food", null));

        List<MonthAmountExpense> monthAmountExpensesList = expensesCalculator.getChartData(expensesList);

        Assertions.assertEquals(2, monthAmountExpensesList.size());
        Assertions.assertEquals(Month.JANUARY, monthAmountExpensesList.get(0).getMonth());
        Assertions.assertEquals(150.0, monthAmountExpensesList.get(0).getAmount());
        Assertions.assertEquals(Month.FEBRUARY, monthAmountExpensesList.get(1).getMonth());
        Assertions.assertEquals(200.0, monthAmountExpensesList.get(1).getAmount());
    }

    @Test
    public void testGetChartData2() {
        List<Expense> expensesList = new ArrayList<>();
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 10), 100.0, "Food", UUID.fromString("0eda0b64-7181-443c-aa98-7e723fd52ac3")));
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 15), 50.0, "Transportation", UUID.fromString("72c70f3b-2141-4171-b86c-8cde7ca4c8f9")));
        expensesList.add(new Expense(UUID.randomUUID(), LocalDate.of(2023, Month.FEBRUARY, 20), 200.0, "Food", UUID.fromString("0eda0b64-7181-443c-aa98-7e723fd52ac3")));

        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(new Category(UUID.fromString("0eda0b64-7181-443c-aa98-7e723fd52ac3"), "Food"));
        categoriesList.add(new Category(UUID.fromString("72c70f3b-2141-4171-b86c-8cde7ca4c8f9"), "Transportation"));
        categoriesList.add(new Category(UUID.fromString("e95e2a19-df58-42c7-b904-4cffe8969169"), "Entertainment"));

        List<CategoryAmountExpense> categoryAmountExpensesList = expensesCalculator.getChartData2(expensesList, categoriesList);

        Assertions.assertEquals(2, categoryAmountExpensesList.size());
    }
}

