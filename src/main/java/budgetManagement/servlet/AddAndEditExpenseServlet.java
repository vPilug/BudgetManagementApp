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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger(AddAndEditExpenseServlet.class);
    public CategoriesStore categoriesStore;
    public ExpensesStore expensesStore;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        expensesStore = new ExpensesStoreImpl(connection);
        categoriesStore = new CategoriesStoreImpl(connection);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Category> categoriesList = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categoriesList);
            req.setAttribute("action", Action.ADD);
            showExpensePage(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "An unexpected error occurred when retrieving categories from database. Please try again later!");
            LOGGER.error("SQLException in doGet");
            showExpensePage(req, resp);
        }
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
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
        try {
            if (req.getParameter("date").isEmpty() || req.getParameter("amount").isEmpty() || req.getParameter("categoryId").isEmpty()) {
                throw new IllegalArgumentException("Date, amount and category fields are mandatory!");
            }
            UUID expenseId = UUID.fromString(req.getParameter("id"));
            Expense expense = new Expense(UUID.fromString(req.getParameter("id")),
                    LocalDate.parse(req.getParameter("date")),
                    Double.parseDouble(req.getParameter("amount")),
                    req.getParameter("description"),
                    UUID.fromString(req.getParameter("categoryId")));

            expensesStore.updateExpense(expenseId, expense);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("action", Action.EDIT);
            req.setAttribute("error", "The expense could not be edited.");
            LOGGER.error("SQLException when the user wants to edit an expense");
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            req.setAttribute("error", illegalArgumentException.getMessage());
            LOGGER.error(illegalArgumentException.getMessage());
        }
    }

    public void addExpense(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getParameter("date").isEmpty() || req.getParameter("amount").isEmpty() || req.getParameter("categoryId").isEmpty()) {
                throw new IllegalArgumentException("Date, amount and category fields are mandatory!");
            }
            Expense expense = new Expense(UUID.randomUUID(),
                    LocalDate.parse(req.getParameter("date")),
                    Double.parseDouble(req.getParameter("amount")),
                    req.getParameter("description"),
                    UUID.fromString(req.getParameter("categoryId")));
            expensesStore.addExpense(expense);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "The expense could not be added.");
            LOGGER.error("SQLException when the user wants to add an expense");
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            req.setAttribute("error", illegalArgumentException.getMessage());
            LOGGER.error(illegalArgumentException.getMessage());
        }
    }

    private void showExpensePage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/add-edit-expense.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("getRequestDispatcher(add-edit-expense.jsp) not working");
        }
    }

    private void redirectToExpensesPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("manage-expenses").forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.error("getRequestDispatcher(manage-expenses) not working");
        }
    }
}
