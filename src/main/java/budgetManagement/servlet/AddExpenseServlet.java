package budgetManagement.servlet;

import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.model.Income;
import budgetManagement.store.*;
import budgetManagement.util.ConnectionManager;

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
public class AddExpenseServlet extends HttpServlet {
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
            showExpensePage(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            addExpense(req, resp);
            redirectToExpensesPage(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void addExpense(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Expense expense = new Expense(UUID.randomUUID(),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("description"),
                UUID.fromString(req.getParameter("categoryId")));
                expensesStore.addExpense(expense);
    }

    private void showExpensePage (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/add-expense.jsp").forward(req, resp);
    }

    private void redirectToExpensesPage (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/manage-expenses");
    }
}
