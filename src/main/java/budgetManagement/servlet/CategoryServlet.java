package budgetManagement.servlet;

import budgetManagement.store.CategoriesStore;
import budgetManagement.store.CategoriesStoreImpl;
import com.sun.net.httpserver.HttpServer;

import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/category"})
public class CategoryServlet extends HttpServlet {
    private Connection connection;
    private CategoriesStore categoriesStore;

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

        categoriesStore = new CategoriesStoreImpl(connection);
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
