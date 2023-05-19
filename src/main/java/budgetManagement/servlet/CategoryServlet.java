package budgetManagement.servlet;

import budgetManagement.model.Category;
import budgetManagement.store.CategoriesStore;
import budgetManagement.store.CategoriesStoreImpl;
import budgetManagement.util.ConnectionManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

@WebServlet(urlPatterns = {"/add-category"})
public class CategoryServlet extends HttpServlet {
    private Connection connection;
    private CategoriesStore categoriesStore;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        categoriesStore = new CategoriesStoreImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsps/add-category.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            addCategory(req, resp);
            redirectToExpensesPage(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        Category category = new Category(UUID.randomUUID(),
                req.getParameter("name"));
        categoriesStore.addCategory(category);
    }

    private void redirectToExpensesPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/add-expense");
    }

}
