package budgetManagement.servlet;

import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.store.CategoriesStore;
import budgetManagement.store.CategoriesStoreImpl;
import budgetManagement.util.Action;
import com.sun.net.httpserver.HttpServer;

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


public class CategoryServlet extends HttpServlet {
    private Connection connection;
    private CategoriesStore categoriesStore;

    @Override
    public void init() throws ServletException {
        super.init();
        categoriesStore = new CategoriesStoreImpl(connection);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    listCategory(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.sendRedirect(req.getContextPath() + "/add-expenses");
    }
    protected void listCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Category> categories = categoriesStore.getCategories();
            req.setAttribute("categories_list", categories);
            req.getRequestDispatcher("/jsps/add-expenses.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
