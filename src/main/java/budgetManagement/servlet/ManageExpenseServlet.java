package budgetManagement.servlet;

import budgetManagement.model.Expense;
import budgetManagement.store.ExpensesStore;
import budgetManagement.store.ExpensesStoreImpl;
import budgetManagement.util.Action;
import budgetManagement.util.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/manage-expenses"})
public class ManageExpenseServlet extends HttpServlet {
    private Connection connection;
    private ExpensesStore expensesStore;
    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        expensesStore = new ExpensesStoreImpl(connection);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = getActionFromRequest(req, resp);
        switch (action){
            case DELETE :
                deleteExpense(req, resp);
                listExpenses(req, resp);
                break;
            default:
                listExpenses(req, resp);
        }
    }
    protected void listExpenses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Expense> expenses = expensesStore.getExpenses();
            req.setAttribute("expensesList", expenses);
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            req.getRequestDispatcher("/jsps/manage-expenses.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void deleteExpense(HttpServletRequest req, HttpServletResponse resp) {
        try{
            String expenseId = req.getParameter("expenseId");
            UUID expenseUUID = UUID.fromString(expenseId);
            expensesStore.deleteExpense(expenseUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private Action getActionFromRequest(HttpServletRequest req, HttpServletResponse resp) {
        String actionAsString = req.getParameter("action");
        actionAsString = (actionAsString != null) ? actionAsString : "list";
        Action action = Action.LIST;
        try{
            action = Action.valueOf(actionAsString);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return action;
    }
    private void showIncomePage (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/manage-expenses.jsp").forward(req, resp);
    }

}
