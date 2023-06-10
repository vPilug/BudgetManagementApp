package budgetManagement.util;

import budgetManagement.model.Income;

import java.util.ArrayList;
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
}
