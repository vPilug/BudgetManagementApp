package budgetManagement.util;

import budgetManagement.model.Income;
import budgetManagement.model.chart.MonthAmountIncome;

import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class IncomesCalculator {

    public List<Income> addTotalLine(List<Income> incomesList) {
        double total = 0.0;
        for (Income income : incomesList) {
            total += income.getAmount();
        }
        total = Math.floor(total * 100) / 100;
        Income totalLine = new Income(null, null, total, "TOTAL");
        List<Income> incomesListWithTotal = new ArrayList<>(incomesList);
        incomesListWithTotal.add(totalLine);
        return incomesListWithTotal;
    }

    public List<MonthAmountIncome> getChartData(List<Income> incomeList) {
        List<Month> monthsList = new ArrayList<>();
        monthsList.add(incomeList.get(incomeList.size() - 1).getDate().getMonth());
        for (Income income : incomeList) {
            Month currentMonth = income.getDate().getMonth();
            if (!monthsList.contains(currentMonth)) {
                monthsList.add(currentMonth);
            }
        }
        List<MonthAmountIncome> monthAmountIncomesList = new ArrayList<>();
        for (Month month : monthsList) {
            MonthAmountIncome monthAmountIncome = new MonthAmountIncome(month);
            for (Income income : incomeList) {
                if (month.equals(income.getDate().getMonth())) {
                    Double currentAmount = monthAmountIncome.getAmount();
                    Double incomeAmount = income.getAmount();
                    monthAmountIncome.setAmount(currentAmount + incomeAmount);
                }
            }
            monthAmountIncomesList.add(monthAmountIncome);
            Collections.sort(monthAmountIncomesList, Comparator.comparing(MonthAmountIncome::getMonth));
        }
        return monthAmountIncomesList;
    }
}
