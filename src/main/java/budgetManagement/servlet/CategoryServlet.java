package budgetManagement.servlet;

import budgetManagement.model.Category;
import budgetManagement.store.CategoriesStore;
import budgetManagement.store.CategoriesStoreImpl;
import budgetManagement.util.ConnectionManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
    private static final Logger LOGGER = LogManager.getLogger(CategoryServlet.class);
    private Connection connection;
    private CategoriesStore categoriesStore;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        categoriesStore = new CategoriesStoreImpl(connection);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/add-category.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            System.out.println("getRequestDispatcher not working");
            LOGGER.error("Exception in doGet");
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        addCategory(req, resp);
        redirectToExpensesPage(req, resp);
    }

    private void addCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Category category = new Category(UUID.randomUUID(),
                    req.getParameter("name"));
            categoriesStore.addCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "The category could not be added.");
            LOGGER.error("The category could not be added.");
        }
    }

    private void redirectToExpensesPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("add-expense");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("getRequestDispatcher not working");
            LOGGER.error("getRequestDispatcher(add-expense) not working");
        }
    }
}
