package budgetManagement.servlet;

import budgetManagement.model.Income;
import budgetManagement.store.IncomesStore;
import budgetManagement.store.IncomesStoreImpl;
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
import java.time.LocalDate;
import java.util.UUID;

@WebServlet(urlPatterns = {"/add-income"})
public class AddAndEditIncomeServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(AddAndEditIncomeServlet.class);
    private Connection connection;
    private IncomesStore incomesStore;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        incomesStore = new IncomesStoreImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("action", Action.ADD);
        showIncomePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        Action action = ServletUtils.getActionFromRequest(req, resp);
        switch (action) {
            case ADD:
                addIncome(req, resp);
                redirectToIncomesPage(req, resp);
                break;
            case EDIT:
                editIncome(req, resp);
                redirectToIncomesPage(req, resp);
                break;
            default:
                redirectToIncomesPage(req, resp);
        }
    }

    private void editIncome(HttpServletRequest req, HttpServletResponse resp) {
        UUID incomeId = UUID.fromString(req.getParameter("id"));
        Income income = new Income(UUID.fromString(req.getParameter("id")),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("source"));
        try {
            incomesStore.updateIncome(incomeId, income);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("action", Action.EDIT);
            req.setAttribute("error", "The income could not be edited.");
            LOGGER.error("SQLException when the user wants to edit an income");
        }
    }

    private void addIncome(HttpServletRequest req, HttpServletResponse resp) {
        Income income = new Income(UUID.randomUUID(),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("source"));
        try {
            incomesStore.addIncome(income);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "The income could not be added.");
            LOGGER.error("SQLException when the user wants to add an income");
        }
    }

    private void showIncomePage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/add-edit-income.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            System.out.println("getRequestDispatcher not working");
            LOGGER.error("getRequestDispatcher(add-edit-income.jsp) not working");
        }
    }

    private void redirectToIncomesPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("manage-incomes").forward(req, resp);
        } catch (ServletException | IOException e) {
            System.out.println("getRequestDispatcher not working");
            LOGGER.error("getRequestDispatcher(manage-incomes) not working");
        }
    }
}
