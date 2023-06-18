package servlet;

import budgetManagement.model.Category;
import budgetManagement.model.Expense;
import budgetManagement.servlet.ManageExpenseServlet;
import budgetManagement.store.CategoriesStore;
import budgetManagement.store.ExpensesStore;
import budgetManagement.util.Action;
import budgetManagement.util.ExpensesCalculator;
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

public class ManageExpenseServletTest {
    private ManageExpenseServlet servlet;


    private HttpServletRequest request;


    private HttpServletResponse response;

    private RequestDispatcher requestDispatcher;

    @Mock
    private ExpensesStore expensesStore;

    @Mock
    private CategoriesStore categoriesStore;

    @Mock
    private ExpensesCalculator calculator;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        servlet = new ManageExpenseServlet();
        servlet.expensesStore = expensesStore;
        servlet.categoriesStore = categoriesStore;
        servlet.calculator = calculator;
        requestDispatcher = mock(RequestDispatcher.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    public void testDoGet_DeleteAction() throws SQLException {
        when(request.getParameter("action")).thenReturn(Action.DELETE.name());
        String myUUID = UUID.randomUUID().toString();
        when(request.getParameter("expenseId")).thenReturn(myUUID);
        servlet.doGet(request, response);

        verify(expensesStore).deleteExpense(UUID.fromString(myUUID));
    }

    @Test
    public void testDoGet_EditAction() {
        when(request.getParameter("action")).thenReturn(Action.EDIT.name());
        String myUUID = UUID.randomUUID().toString();
        when(request.getParameter("expenseId")).thenReturn(myUUID);
        when(request.getRequestDispatcher("/jsps/add-edit-expense.jsp")).thenReturn(requestDispatcher);

        servlet.doGet(request, response);

        verify(request).setAttribute("action", Action.EDIT);
    }

    @Test
    public void testListExpenses() throws ServletException, IOException, SQLException {
        List<Expense> expenses = new ArrayList<>();
        when(expensesStore.getExpenses()).thenReturn(expenses);
        when(calculator.addTotalLine(any())).thenReturn(expenses);

        List<Category> categories = new ArrayList<>();
        when(categoriesStore.getCategories()).thenReturn(categories);

        when(request.getRequestDispatcher("/jsps/manage-expenses.jsp")).thenReturn(requestDispatcher);

        servlet.listExpenses(request, response);

        verify(request).setAttribute("expensesList", expenses);
        verify(request).setAttribute("categoriesList", categories);
        verify(request).setAttribute("action_edit", Action.EDIT);
        verify(request).setAttribute("action_delete", Action.DELETE);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testDeleteExpense() throws SQLException {
        String uuid = UUID.randomUUID().toString();
        when(request.getParameter("expenseId")).thenReturn(uuid);

        servlet.deleteExpense(request, response);

        verify(expensesStore).deleteExpense(UUID.fromString(uuid));
    }

    @Test
    public void testShowExpensesPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/jsps/manage-expenses.jsp")).thenReturn(requestDispatcher);

        servlet.showExpensesPage(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testShowAddExpensesPage() throws ServletException, IOException, SQLException {
        List<Category> categoriesList = new ArrayList<>();
        when(categoriesStore.getCategories()).thenReturn(categoriesList);

        when(request.getRequestDispatcher("/jsps/add-edit-expense.jsp")).thenReturn(requestDispatcher);

        servlet.showAddExpensesPage(request, response);

        verify(request).setAttribute("categoriesList", categoriesList);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    public void testLoadExpense() throws SQLException {
        String uuid = UUID.randomUUID().toString();
        when(request.getParameter("expenseId")).thenReturn(uuid);

        UUID expenseId = UUID.fromString(uuid);
        Expense expense = new Expense(expenseId, LocalDate.now(), 50.0, "test", expenseId);
        when(expensesStore.findExpenseById(expenseId)).thenReturn(expense);

        servlet.loadExpense(request, response);

        verify(request).setAttribute("expense", expense);
    }
}

