package budgetManagement.servlet;

import budgetManagement.model.Expense;
import budgetManagement.store.ExpensesStore;
import budgetManagement.store.ExpensesStoreImpl;

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
import java.util.List;

@WebServlet(urlPatterns = {"/expenses"})
public class ExpenseServlet extends HttpServlet {
    private Connection connection;
    private ExpensesStore ExpensesStore;
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

        ExpensesStore = new ExpensesStoreImpl(connection);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        listExpenses(req, resp);

    }
    protected void listExpenses(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            List<Expense> expenses = ExpensesStore.getExpenses();
            req.setAttribute("expensesList", expenses);

            req.getRequestDispatcher("/jsps/expenses-history.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
