package servlet;

import budgetManagement.model.Income;
import budgetManagement.servlet.ManageIncomeServlet;
import budgetManagement.store.IncomesStore;
import budgetManagement.util.Action;
import budgetManagement.util.IncomesCalculator;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;

public class ManageIncomeServletTest {
    private ManageIncomeServlet servlet;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private IncomesStore incomesStore;

    @Mock
    private IncomesCalculator calculator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new ManageIncomeServlet();
        servlet.incomesStore = incomesStore;
        servlet.calculator = calculator;
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet_DeleteAction() throws SQLException, ServletException, IOException {
        when(request.getParameter("action")).thenReturn(Action.DELETE.name());
        String myUUID = UUID.randomUUID().toString();
        when(request.getParameter("incomeId")).thenReturn(myUUID);
        servlet.doGet(request, response);

        verify(incomesStore).deleteIncome(UUID.fromString(myUUID));
    }

    @Test
    public void testDoGet_EditAction() throws ServletException, IOException {
        when(request.getParameter("action")).thenReturn(Action.EDIT.name());
        String myUUID = UUID.randomUUID().toString();
        when(request.getParameter("incomeId")).thenReturn(myUUID);
        when(request.getRequestDispatcher("/jsps/add-edit-income.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("action", Action.EDIT);

    }


    @Test
    public void testDoPost() throws SQLException, ServletException, IOException {
        List<Income> incomes = new ArrayList<>();
        when(incomesStore.getFilteredIncomes(any())).thenReturn(incomes);
        when(calculator.addTotalLine(incomes)).thenReturn(incomes);

        when(request.getRequestDispatcher("/jsps/manage-incomes.jsp")).thenReturn(requestDispatcher);

        servlet.doPost(request, response);

        verify(request).setAttribute("incomesList", incomes);
        verify(request).setAttribute("action_edit", Action.EDIT);
        verify(request).setAttribute("action_delete", Action.DELETE);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testListIncomes() throws SQLException, ServletException, IOException {
        List<Income> incomes = new ArrayList<>();
        when(incomesStore.getIncomes()).thenReturn(incomes);
        when(calculator.addTotalLine(incomes)).thenReturn(incomes);

        when(request.getRequestDispatcher("/jsps/manage-incomes.jsp")).thenReturn(requestDispatcher);

        servlet.listIncomes(request, response);

        verify(request).setAttribute("incomesList", incomes);
        verify(request).setAttribute("action_edit", Action.EDIT);
        verify(request).setAttribute("action_delete", Action.DELETE);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDeleteIncome() throws SQLException {
        String uuid = UUID.randomUUID().toString();
        when(request.getParameter("incomeId")).thenReturn(uuid);
        servlet.deleteIncome(request, response);

        verify(incomesStore).deleteIncome(UUID.fromString(uuid));
    }

    @Test
    public void testShowIncomePage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/jsps/manage-incomes.jsp")).thenReturn(requestDispatcher);

        servlet.showIncomePage(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testShowAddIncomePage() throws ServletException, IOException, SQLException {
        when(request.getRequestDispatcher("/jsps/add-edit-income.jsp")).thenReturn(requestDispatcher);

        servlet.showAddIncomePage(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testLoadIncome() throws ServletException, IOException, SQLException {
        String uuid = UUID.randomUUID().toString();
        when(request.getParameter("incomeId")).thenReturn(uuid);

        UUID incomeId = UUID.fromString(uuid);
        Income income = new Income(incomeId, LocalDate.now(), 100.0, "test");
        when(incomesStore.findIncomeById(incomeId)).thenReturn(income);

        servlet.loadIncome(request, response);

        verify(request).setAttribute("income", income);
    }
}


