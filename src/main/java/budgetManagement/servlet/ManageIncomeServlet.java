package budgetManagement.servlet;

import budgetManagement.model.Income;
import budgetManagement.store.IncomesStore;
import budgetManagement.store.IncomesStoreImpl;
import budgetManagement.util.Action;
import budgetManagement.util.ConnectionManager;

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

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        incomesStore = new IncomesStoreImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Action action = getActionFromRequest(req, resp);
        switch (action) {
            case DELETE:
                deleteIncome(req, resp);
                listIncomes(req, resp);
                break;
            default:
                listIncomes(req, resp);
        }
    }

    protected void listIncomes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Income> incomes = incomesStore.getIncomes();
            req.setAttribute("incomesList", incomes);
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

    private Action getActionFromRequest(HttpServletRequest req, HttpServletResponse resp) {
        String actionAsString = req.getParameter("action");
        actionAsString = (actionAsString != null) ? actionAsString : "list";
        Action action = Action.LIST;
        try {
            action = Action.valueOf(actionAsString);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return action;
    }

    private void showIncomePage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/manage-incomes.jsp").forward(req, resp);
    }

}
