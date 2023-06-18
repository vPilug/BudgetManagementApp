package servlet;

import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.servlet.AddAndEditExpenseServlet;
import budgetManagement.store.CategoriesStore;
import budgetManagement.store.ExpensesStore;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class AddAndEditExpenseServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;
    private ExpensesStore expensesStore;
    private CategoriesStore categoriesStore;
    private AddAndEditExpenseServlet servlet;


    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new AddAndEditExpenseServlet();
        categoriesStore = mock(CategoriesStore.class);
        servlet.categoriesStore = categoriesStore;
        expensesStore = mock(ExpensesStore.class);
        servlet.expensesStore = expensesStore;
        requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/jsps/add-edit-expense.jsp")).thenReturn(requestDispatcher);
        when(categoriesStore.getCategories()).thenReturn(List.of(new Category(UUID.randomUUID(), "new category")));
        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/jsps/add-edit-expense.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_AddAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("date")).thenReturn("2023-05-20");
        when(request.getParameter("amount")).thenReturn("100.0");
        when(request.getParameter("description")).thenReturn("Test expense");
        when(request.getParameter("categoryId")).thenReturn(String.valueOf(UUID.randomUUID()));

        servlet.doPost(request, response);

        verify(request, times(1)).getRequestDispatcher("manage-expenses");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPost_EditAction() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("EDIT");
        when(request.getParameter("id")).thenReturn(String.valueOf(UUID.randomUUID()));
        when(request.getParameter("date")).thenReturn("2023-05-20");
        when(request.getParameter("amount")).thenReturn("100.0");
        when(request.getParameter("description")).thenReturn("Updated expense");
        when(request.getParameter("categoryId")).thenReturn(String.valueOf(UUID.randomUUID()));

        servlet.doPost(request, response);

        verify(expensesStore).updateExpense(any(UUID.class), any(Expense.class));

        verify(request, times(1)).getRequestDispatcher("manage-expenses");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPost_DefaultAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).getRequestDispatcher("manage-expenses");
        verify(requestDispatcher).forward(request, response);
    }
}


