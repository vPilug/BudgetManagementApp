package servlet;

import budgetManagement.model.Income;
import budgetManagement.model.chart.MonthAmountIncome;
import budgetManagement.util.IncomesCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IncomesCalculatorTest {

    private IncomesCalculator incomesCalculator;

    @BeforeEach
    public void setUp() {
        incomesCalculator = new IncomesCalculator();
    }

    @Test
    public void testAddTotalLine() {
        List<Income> incomesList = new ArrayList<>();
        incomesList.add(new Income(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 10), 100.0, null));
        incomesList.add(new Income(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 15), 50.0, null));

        List<Income> incomesListWithTotal = incomesCalculator.addTotalLine(incomesList);

        Assertions.assertEquals(3, incomesListWithTotal.size());
        Income totalLine = incomesListWithTotal.get(incomesListWithTotal.size() - 1);
        Assertions.assertEquals(150.0, totalLine.getAmount());
    }

    @Test
    public void testGetChartData() {
        List<Income> incomeList = new ArrayList<>();
        incomeList.add(new Income(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 10), 100.0, null));
        incomeList.add(new Income(UUID.randomUUID(), LocalDate.of(2023, Month.JANUARY, 15), 50.0, null));
        incomeList.add(new Income(UUID.randomUUID(), LocalDate.of(2023, Month.FEBRUARY, 20), 200.0, null));

        List<MonthAmountIncome> monthAmountIncomesList = incomesCalculator.getChartData(incomeList);

        Assertions.assertEquals(2, monthAmountIncomesList.size());
        Assertions.assertEquals(Month.JANUARY, monthAmountIncomesList.get(0).getMonth());
        Assertions.assertEquals(150.0, monthAmountIncomesList.get(0).getAmount());
        Assertions.assertEquals(Month.FEBRUARY, monthAmountIncomesList.get(1).getMonth());
        Assertions.assertEquals(200.0, monthAmountIncomesList.get(1).getAmount());
    }
}
