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
import budgetManagement.util.ExpensesCalculator;
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
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/manage-expenses"})
public class ManageExpenseServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(ManageExpenseServlet.class);
    private Connection connection;
    private ExpensesStore expensesStore;
    private CategoriesStore categoriesStore;
    private ExpensesCalculator calculator;


    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        expensesStore = new ExpensesStoreImpl(connection);
        categoriesStore = new CategoriesStoreImpl(connection);
        calculator = new ExpensesCalculator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        Action action = ServletUtils.getActionFromRequest(req, resp);
        switch (action) {
            case DELETE:
                deleteExpense(req, resp);
                listExpenses(req, resp);
                break;
            case EDIT:
                loadExpense(req, resp);
                req.setAttribute("action", Action.EDIT);
                showAddExpensesPage(req, resp);
            default:
                listExpenses(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Action action = ServletUtils.getActionFromRequest(req, resp);
        ExpenseFilter filter = (action != Action.EDIT && action != Action.ADD)
                ? new ExpenseFilter(req.getParameter("date1"), req.getParameter("date2"), req.getParameter("categoryId"))
                : new ExpenseFilter();
        try {
            List<Expense> expenses = expensesStore.getFilteredExpenses(filter);
            req.setAttribute("expensesList", calculator.addTotalLine(expenses));
            List<Category> categories = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categories);
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showExpensesPage(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "An unexpected error occurred. Please try again later!");
            LOGGER.error("SQLException in doPost");
            showExpensesPage(req, resp);
        }
    }

    protected void listExpenses(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Expense> expenses = expensesStore.getExpenses();
            req.setAttribute("expensesList", calculator.addTotalLine(expenses));
            List<Category> categories = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categories);
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showExpensesPage(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "An unexpected error occurred. Please try again later!");
            showExpensesPage(req, resp);
            LOGGER.error("SQLException when the user wants to list the expenses");
        }
    }

    private void deleteExpense(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String expenseId = req.getParameter("expenseId");
            UUID expenseUUID = UUID.fromString(expenseId);
            expensesStore.deleteExpense(expenseUUID);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "The expense could not be deleted.");
            LOGGER.error("SQLException when the user wants to delete an expense");

        }
    }

    private void showExpensesPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/manage-expenses.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            System.out.println("getRequestDispatcher not working");
            LOGGER.error("getRequestDispatcher(manage-expenses.jsp) not working");
        }
    }

    private void showAddExpensesPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Category> categoriesList = categoriesStore.getCategories();
            req.setAttribute("categoriesList", categoriesList);
            req.getRequestDispatcher("/jsps/add-edit-expense.jsp").forward(req, resp);
        } catch (ServletException | IOException | SQLException e) {
            e.printStackTrace();
            System.out.println("getRequestDispatcher not working");
            LOGGER.error("getRequestDispatcher(add-edit-expense.jsp) not working");
        }
    }


    private void loadExpense(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String expenseId = req.getParameter("expenseId");
            UUID expenseID = UUID.fromString(expenseId);
            Expense expense = expensesStore.findExpenseById(expenseID);
            req.setAttribute("expense", expense);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Could not load expense.");
            LOGGER.error("Could not load expense.");
        }
    }

}
