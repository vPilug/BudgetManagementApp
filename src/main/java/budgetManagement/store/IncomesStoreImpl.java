package budgetManagement.store;

import budgetManagement.model.Income;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class IncomesStoreImpl implements IncomesStore {
    private Connection dbConnection;

    public IncomesStoreImpl(Connection dbConnection) {
        this.dbConnection = dbConnection;
    }

    @Override
    public void addIncome(Income income) throws SQLException {
        String insertStatement = "INSERT INTO incomes VALUES (?, ?, ?, ?)";
        PreparedStatement createIncomeStatement = dbConnection.prepareStatement(insertStatement);
        createIncomeStatement.setObject(1, income.getId());
        createIncomeStatement.setObject(2, income.getDate());
        createIncomeStatement.setDouble(3, income.getAmount());
        createIncomeStatement.setString(4, income.getSource());
        createIncomeStatement.executeUpdate();
    }

    @Override
    public List<Income> getIncomes() throws SQLException {
        Statement getIncomesStatement;
        getIncomesStatement = dbConnection.createStatement();
        ResultSet incomesResultSet;
        incomesResultSet = getIncomesStatement.executeQuery("SELECT * FROM incomes");
        List<Income> incomesList = new ArrayList<>();
        while (true) {
            if (!incomesResultSet.next()) break;
            UUID incomeId;
            incomeId = UUID.fromString(incomesResultSet.getObject("id").toString());
            LocalDate incomeDate;
            incomeDate = incomesResultSet.getDate(2).toLocalDate();
            double incomeAmount;
            incomeAmount = incomesResultSet.getDouble(3);
            String incomeSource;
            incomeSource = incomesResultSet.getString(4);
            incomesList.add(new Income(incomeId, incomeDate, incomeAmount, incomeSource));
        }
        return incomesList;
    }

    @Override
    public void updateIncome(UUID id, Income income) throws SQLException {
        String updateStatement = "UPDATE incomes SET date = ?, amount = ?, source = ? WHERE id = ?";
        PreparedStatement updateIncomeStatement = dbConnection.prepareStatement(updateStatement);
        updateIncomeStatement.setObject(1, income.getDate());
        updateIncomeStatement.setDouble(2, income.getAmount());
        updateIncomeStatement.setString(3, income.getSource());
        updateIncomeStatement.setObject(4, id);
        updateIncomeStatement.executeUpdate();
    }

    @Override
    public void deleteIncome(UUID id) throws SQLException {
        PreparedStatement deleteIncomeStatement = dbConnection.prepareStatement("DELETE FROM incomes WHERE id = ?");
        deleteIncomeStatement.setObject(1, id);
        deleteIncomeStatement.executeUpdate();
    }

    @Override
    public Income findIncomeById(UUID id) throws SQLException {
        PreparedStatement findIncomeByIdStatement = dbConnection.prepareStatement("SELECT * FROM incomes WHERE id = ?");
        findIncomeByIdStatement.setObject(1, id);
        ResultSet incomeResultSet = findIncomeByIdStatement.executeQuery();
        incomeResultSet.next();
        UUID incomeId = (UUID) incomeResultSet.getObject("id");
        LocalDate incomeDate = incomeResultSet.getDate(2).toLocalDate();
        Double incomeAmount = incomeResultSet.getDouble(3);
        String incomeSource = incomeResultSet.getString(4);


        return new Income(incomeId, incomeDate, incomeAmount, incomeSource);
    }
}
