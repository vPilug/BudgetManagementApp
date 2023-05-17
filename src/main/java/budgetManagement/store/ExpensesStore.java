package budgetManagement.store;

import budgetManagement.model.Expense;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface ExpensesStore {
    void addExpense(Expense expense) throws SQLException;
    List<Expense> getExpenses() throws SQLException;
    void updateExpense(UUID id, Expense expense) throws SQLException;
    void deleteExpense(UUID id) throws SQLException;
}
