package servlet;

import budgetManagement.model.Income;
import budgetManagement.servlet.AddAndEditIncomeServlet;
import budgetManagement.store.IncomesStore;
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
import java.util.UUID;

import static org.mockito.Mockito.*;

public class AddAndEditIncomeServletTest {
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;

    private RequestDispatcher requestDispatcher;

    private IncomesStore incomesStore;
    private AddAndEditIncomeServlet servlet;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new AddAndEditIncomeServlet();
        incomesStore = mock(IncomesStore.class);
        servlet.incomesStore = incomesStore;
        requestDispatcher = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet() throws ServletException, IOException {
        when(request.getRequestDispatcher("/jsps/add-edit-income.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).getRequestDispatcher("/jsps/add-edit-income.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_AddAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn("ADD");
        when(request.getParameter("date")).thenReturn("2023-05-20");
        when(request.getParameter("amount")).thenReturn("1000.0");
        when(request.getParameter("source")).thenReturn("Salary");

        servlet.doPost(request, response);

        verify(request, times(1)).getRequestDispatcher("manage-incomes");
        verify(requestDispatcher, times(1)).forward(request, response);
    }

    @Test
    public void testDoPost_EditAction() throws ServletException, IOException, SQLException {
        when(request.getParameter("action")).thenReturn("EDIT");
        when(request.getParameter("id")).thenReturn(String.valueOf(UUID.randomUUID()));
        when(request.getParameter("date")).thenReturn("2023-05-20");
        when(request.getParameter("amount")).thenReturn("1500.0");
        when(request.getParameter("source")).thenReturn("Bonus");

        servlet.doPost(request, response);

        verify(incomesStore).updateIncome(any(UUID.class), any(Income.class));

        verify(request).getRequestDispatcher("manage-incomes");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDoPost_DefaultAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn(null);

        servlet.doPost(request, response);

        verify(request).getRequestDispatcher("manage-incomes");
        verify(requestDispatcher).forward(request, response);
    }
}
