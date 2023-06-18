package budgetManagement.servlet;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class StartPage extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(StartPage.class);

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.getRequestDispatcher("/jsps/start-page.jsp").forward(req, resp);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            LOGGER.error("getRequestDispatcher(start-page.jsp) not working");
        }
    }
}
