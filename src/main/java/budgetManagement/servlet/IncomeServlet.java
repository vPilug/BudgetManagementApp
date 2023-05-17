package budgetManagement.servlet;

import budgetManagement.model.Income;
import budgetManagement.store.IncomesStore;
import budgetManagement.store.IncomesStoreImpl;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@WebServlet(urlPatterns = {"/incomes"})
public class IncomeServlet extends HttpServlet {
    private Connection connection;
    private IncomesStore incomesStore;
    @Override
    public void init() throws ServletException {
        super.init();
        try {
            InitialContext cxt = new InitialContext();
            if ( cxt == null ) {
                throw new Exception("Uh oh -- no context!");
            }

            DataSource ds = (DataSource) cxt.lookup( "java:/comp/env/jdbc/postgres" );

            if ( ds == null ) {
                throw new Exception("Data source not found!");
            }

            connection = ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception ex){
            ex.printStackTrace();
        }

        incomesStore = new IncomesStoreImpl(connection);
    }
@Override
protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            listIncomes(req, resp);

}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                addIncome(req, resp);
    }

    private void addIncome(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Income income = new Income(UUID.randomUUID(),
                LocalDate.parse(req.getParameter("date")),
                Double.parseDouble(req.getParameter("amount")),
                req.getParameter("source"));
        try {
            incomesStore.addIncome(income);
            listIncomes(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", e.getMessage());
            showForm(req, resp);
        }
    }
    protected void listIncomes(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<Income> incomes = incomesStore.getIncomes();
            req.setAttribute("incomesList", incomes);

            req.getRequestDispatcher("/jsps/incomes-history.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void showForm (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/add-income.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
