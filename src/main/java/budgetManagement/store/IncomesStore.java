package budgetManagement.store;

import budgetManagement.filers.IncomeFilter;
import budgetManagement.model.Income;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface IncomesStore {
    void addIncome(Income income) throws SQLException;

    List<Income> getIncomes() throws SQLException;

    void updateIncome(UUID id, Income income) throws SQLException;

    void deleteIncome(UUID id) throws SQLException;

    Income findIncomeById(UUID id) throws SQLException;

    List<Income> getFilteredIncomes(IncomeFilter filter) throws SQLException;
}

