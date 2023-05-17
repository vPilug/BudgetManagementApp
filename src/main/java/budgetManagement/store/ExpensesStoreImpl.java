package budgetManagement.store;

import budgetManagement.model.Expense;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ExpensesStoreImpl implements ExpensesStore{

    private Connection dbConnection;

    public ExpensesStoreImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void addExpense(Expense expense) throws SQLException {
        String insertStatement = "INSERT INTO expenses VALUES (?, ?, ?, ?, ?)";
        PreparedStatement createExpenseStatement = dbConnection.prepareStatement(insertStatement);
            createExpenseStatement.setObject(1, expense.getId());
            createExpenseStatement.setObject(2, expense.getDate());
            createExpenseStatement.setDouble(3, expense.getAmount());
            createExpenseStatement.setString(4, expense.getDescription());
            createExpenseStatement.setObject(5, expense.getCategoryId());
            createExpenseStatement.executeUpdate();
    }

    @Override
    public List<Expense> getExpenses() throws SQLException {
        Statement getExpensesStatement;
            getExpensesStatement = dbConnection.createStatement();
        ResultSet expensesResultSet;
            expensesResultSet = getExpensesStatement.executeQuery("SELECT * FROM expenses");
        List<Expense> expensesList = new ArrayList<>();
        while (true) {
                if (!expensesResultSet.next()) break;
                UUID expenseId;
                expenseId = UUID.fromString(expensesResultSet.getObject(1).toString());
                LocalDate expenseDate;
                expenseDate = expensesResultSet.getDate(2).toLocalDate();
                double expenseAmount;
                expenseAmount = expensesResultSet.getDouble(3);
                String expenseDescription;
                expenseDescription = expensesResultSet.getString(4);
                UUID expenseExpenseId;
                expenseExpenseId = UUID.fromString(expensesResultSet.getObject(5).toString());
                expensesList.add(new Expense(expenseId, expenseDate, expenseAmount, expenseDescription, expenseExpenseId));
        }
        return expensesList;
    }

    @Override
    public void updateExpense(UUID id, Expense expense) throws SQLException {
        String updateStatement = "UPDATE expenses SET date = ?, amount = ?, description = ? WHERE id = ?";
            PreparedStatement updateExpenseStatement = dbConnection.prepareStatement(updateStatement);
            updateExpenseStatement.setObject(1, expense.getDate());
            updateExpenseStatement.setDouble(2, expense.getAmount());
            updateExpenseStatement.setString(3, expense.getDescription());
            updateExpenseStatement.setObject(4, id);
            updateExpenseStatement.executeUpdate();
    }

    @Override
    public void deleteExpense(UUID id) throws SQLException {
            PreparedStatement deleteExpenseStatement = dbConnection.prepareStatement("DELETE FROM expenses WHERE id = ?");
            deleteExpenseStatement.setObject(1, id);
            deleteExpenseStatement.executeUpdate();

    }
}
