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
    public CategoriesStore categoriesStore;
    private Connection connection;

    @Override
    public void init() throws ServletException {
        super.init();
        connection = ConnectionManager.getConnection();
        categoriesStore = new CategoriesStoreImpl(connection);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/add-category.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            LOGGER.error("Exception in doGet");
        }
    }

    public void doPost(HttpServletRequest req, HttpServletResponse resp) {
        addCategory(req, resp);
        redirectToExpensesPage(req, resp);
    }

    public void addCategory(HttpServletRequest req, HttpServletResponse resp) {
        try {
            if (req.getParameter("name").isEmpty()) {
                throw new IllegalArgumentException("Name is empty!");
            }
            Category category = new Category(UUID.randomUUID(),
                    req.getParameter("name"));
            categoriesStore.addCategory(category);
        } catch (SQLException e) {
            e.printStackTrace();
            req.setAttribute("error", "The category could not be added.");
            LOGGER.error("The category could not be added.");
        } catch (IllegalArgumentException illegalArgumentException) {
            illegalArgumentException.printStackTrace();
            req.setAttribute("error", illegalArgumentException.getMessage());
        }
    }

    private void redirectToExpensesPage(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect("add-expense");
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("getRequestDispatcher(add-expense) not working");
        }
    }
}
