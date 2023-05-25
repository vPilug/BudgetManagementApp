package budgetManagement.servlet;

import budgetManagement.filers.IncomeFilter;
import budgetManagement.model.Income;
import budgetManagement.store.IncomesStore;
import budgetManagement.store.IncomesStoreImpl;
import budgetManagement.util.Action;
import budgetManagement.util.ConnectionManager;
import budgetManagement.util.IncomesCalculator;
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

@WebServlet(urlPatterns = {"/manage-incomes"})
public class ManageIncomeServlet extends HttpServlet {
    private Connection connection;
    private IncomesStore incomesStore;
    private IncomesCalculator calculator;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        incomesStore = new IncomesStoreImpl(connection);
        calculator = new IncomesCalculator();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = ServletUtils.getActionFromRequest(req, resp);
        switch (action) {
            case DELETE:
                deleteIncome(req, resp);
                listIncomes(req, resp);
                break;
            case EDIT:
                loadIncome(req, resp);
                req.setAttribute("action", Action.EDIT);
                try {
                    showAddIncomePage(req, resp);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            default:
                listIncomes(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        IncomeFilter filter = new IncomeFilter(req.getParameter("date1"), req.getParameter("date2"));
        try {
            List<Income> incomes = incomesStore.getFilteredIncomes(filter);
            req.setAttribute("incomesList", calculator.addTotalLine(incomes));
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showIncomePage(req, resp);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected void listIncomes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Income> incomes = incomesStore.getIncomes();
            req.setAttribute("incomesList", calculator.addTotalLine(incomes));
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showIncomePage(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteIncome(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String incomeId = req.getParameter("incomeId");
            UUID incomeUUID = UUID.fromString(incomeId);
            incomesStore.deleteIncome(incomeUUID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showIncomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/manage-incomes.jsp").forward(req, resp);
    }

    private void showAddIncomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        req.getRequestDispatcher("/jsps/add-edit-income.jsp").forward(req, resp);
    }

    private void loadIncome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String incomeId = req.getParameter("incomeId");
            UUID incomeID = UUID.fromString(incomeId);
            Income income = incomesStore.findIncomeById(incomeID);
            req.setAttribute("income", income);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
