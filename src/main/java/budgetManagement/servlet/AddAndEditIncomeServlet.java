package budgetManagement.servlet;

import budgetManagement.model.Income;
import budgetManagement.store.IncomesStore;
import budgetManagement.store.IncomesStoreImpl;
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
import java.time.LocalDate;
import java.util.UUID;

@WebServlet(urlPatterns = {"/add-income"})
public class AddAndEditIncomeServlet extends HttpServlet {
    private Connection connection;
    private IncomesStore incomesStore;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        incomesStore = new IncomesStoreImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("action", Action.ADD);
        showIncomePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        }
    }

    private void showIncomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/add-edit-income.jsp").forward(req, resp);
    }

    private void redirectToIncomesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/manage-incomes");
    }
}
