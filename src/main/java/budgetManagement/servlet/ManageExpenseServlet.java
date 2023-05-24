package budgetManagement.servlet;

import budgetManagement.filers.ExpenseFilter;
import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.store.CategoriesStore;
import budgetManagement.store.CategoriesStoreImpl;
import budgetManagement.store.ExpensesStore;
import budgetManagement.store.ExpensesStoreImpl;
import budgetManagement.util.Action;
import budgetManagement.util.ConnectionManager;
import budgetManagement.util.ServletUtils;

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
    private CategoriesStore categoriesStore;


    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        expensesStore = new ExpensesStoreImpl(connection);
        categoriesStore = new CategoriesStoreImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = ServletUtils.getActionFromRequest(req, resp);
        switch (action) {
            case DELETE:
                deleteExpense(req, resp);
                listExpenses(req, resp);
                break;
            case EDIT:
                loadExpense(req, resp);
                req.setAttribute("action", Action.EDIT);
                try {
                    showAddExpensesPage(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            default:
                listExpenses(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        ExpenseFilter filter = new ExpenseFilter(req.getParameter("date1"), req.getParameter("date2"), req.getParameter("categoryId"));
        try {
            List<Expense> expenses = expensesStore.getFilteredExpenses(filter);
            req.setAttribute("expensesList", expenses);
            List<Category> categories = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categories);
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showExpensesPage(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    protected void listExpenses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Expense> expenses = expensesStore.getExpenses();
            req.setAttribute("expensesList", expenses);
            List<Category> categories = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categories);
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showExpensesPage(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteExpense(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String expenseId = req.getParameter("expenseId");
            UUID expenseUUID = UUID.fromString(expenseId);
            expensesStore.deleteExpense(expenseUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showExpensesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/manage-expenses.jsp").forward(req, resp);
    }

    private void showAddExpensesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        List<Category> categoriesList = categoriesStore.getCategories();
        req.setAttribute("categoriesList", categoriesList);
        req.getRequestDispatcher("/jsps/add-edit-expense.jsp").forward(req, resp);
    }


    private void loadExpense(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String expenseId = req.getParameter("expenseId");
            UUID expenseID = UUID.fromString(expenseId);
            Expense expense = expensesStore.findExpenseById(expenseID);
            req.setAttribute("expense", expense);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
