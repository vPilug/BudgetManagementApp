package budgetManagement.servlet;

import budgetManagement.model.Income;
import budgetManagement.store.IncomesStore;
import budgetManagement.store.IncomesStoreImpl;
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
import java.util.UUID;

@WebServlet(urlPatterns = {"/add-income"})
public class AddIncomeServlet extends HttpServlet {
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
        showIncomePage(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            addIncome(req, resp);
            redirectToIncomesPage(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addIncome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Income income = new Income(UUID.randomUUID(),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("source"));
        incomesStore.addIncome(income);
    }

    private void showIncomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/add-income.jsp").forward(req, resp);
    }

    private void redirectToIncomesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/manage-incomes");
    }
}
