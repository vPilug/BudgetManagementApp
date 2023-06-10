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
                showAddIncomePage(req, resp);
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
        } catch (SQLException e) {
            req.setAttribute("error", "An unexpected error occurred. Please try again later!");
            showIncomePage(req, resp);
        }
    }

    protected void listIncomes(HttpServletRequest req, HttpServletResponse resp) {
        try {
            List<Income> incomes = incomesStore.getIncomes();
            req.setAttribute("incomesList", calculator.addTotalLine(incomes));
            req.setAttribute("action_edit", Action.EDIT);
            req.setAttribute("action_delete", Action.DELETE);
            showIncomePage(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "An unexpected error occurred. Please try again later!");
            showIncomePage(req, resp);
        }
    }

    private void deleteIncome(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String incomeId = req.getParameter("incomeId");
            UUID incomeUUID = UUID.fromString(incomeId);
            incomesStore.deleteIncome(incomeUUID);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "The expense could not be deleted.");
        }
    }

    private void showIncomePage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/manage-incomes.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            System.out.println("getRequestDispatcher not working");
        }
    }

    private void showAddIncomePage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/add-edit-income.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            System.out.println("getRequestDispatcher not working");
        }
    }

    private void loadIncome(HttpServletRequest req, HttpServletResponse resp) {
        try {
            String incomeId = req.getParameter("incomeId");
            UUID incomeID = UUID.fromString(incomeId);
            Income income = incomesStore.findIncomeById(incomeID);
            req.setAttribute("income", income);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "Could not load income.");
        }
    }

}
