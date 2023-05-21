package budgetManagement.servlet;

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
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/add-expense"})
public class AddAndEditExpenseServlet extends HttpServlet {
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
        try {
            List<Category> categoriesList = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categoriesList);
            req.setAttribute("action", Action.ADD);
            showExpensePage(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = ServletUtils.getActionFromRequest(req, resp);
        switch (action) {
            case ADD:
                addExpense(req, resp);
                redirectToExpensesPage(req, resp);
                break;
            case EDIT:
                editExpense(req, resp);
                redirectToExpensesPage(req, resp);
                break;
            default:
                redirectToExpensesPage(req, resp);
        }
    }

    private void editExpense(HttpServletRequest req, HttpServletResponse resp) {
        UUID expenseId = UUID.fromString(req.getParameter("id"));
        Expense expense = new Expense(UUID.fromString(req.getParameter("id")),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("description"),
                UUID.fromString(req.getParameter("categoryId")));
        try {
            expensesStore.updateExpense(expenseId, expense);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("action", Action.EDIT);
        }
    }

    private void addExpense(HttpServletRequest req, HttpServletResponse resp) {
        Expense expense = new Expense(UUID.randomUUID(),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("description"),
                UUID.fromString(req.getParameter("categoryId")));
        try {
            expensesStore.addExpense(expense);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void showExpensePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/add-edit-expense.jsp").forward(req, resp);
    }

    private void redirectToExpensesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/manage-expenses");
    }
}
