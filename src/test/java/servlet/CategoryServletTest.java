package servlet;

import budgetManagement.model.Category;
import budgetManagement.servlet.CategoryServlet;
import budgetManagement.store.CategoriesStore;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class CategoryServletTest {
    private CategoryServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private CategoriesStore categoriesStore;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new CategoryServlet();
        servlet.categoriesStore = categoriesStore;
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/jsps/add-category.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/jsps/add-category.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost() throws ServletException, IOException, SQLException {
        when(request.getParameter("name")).thenReturn("New Category");
        doNothing().when(categoriesStore).addCategory(any(Category.class));
        doNothing().when(response).sendRedirect("add-expense");

        servlet.doPost(request, response);

        verify(request).getParameter("name");
        verify(categoriesStore).addCategory(any(Category.class));
        verify(response).sendRedirect("add-expense");
    }

    @Test
    public void testAddCategory() throws SQLException {
        CategoryServlet servlet = this.servlet;

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn("New Category");
        doNothing().when(categoriesStore).addCategory(any(Category.class));

        servlet.addCategory(request, response);

        verify(request).getParameter("name");
        verify(categoriesStore).addCategory(any(Category.class));
    }
}
