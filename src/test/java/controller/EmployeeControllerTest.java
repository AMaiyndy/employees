package controller;

import static org.mockito.Mockito.*;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import ru.technoserv.controller.EmployeeController;
import ru.technoserv.dao.Employee;

import javax.resource.spi.RetryableUnavailableException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EmployeeControllerTest {

    @Mock
    private EmployeeController mockController;

    private EmployeeController controller = new EmployeeController();

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testThrowAnException() throws Exception{
        expectedException.expect(Exception.class);
        expectedException.expectMessage("User are");
        when(controller.createEmployee("","",request,  response)).thenThrow(new Exception("User are"));
        controller.createEmployee("","",request, response);
    }

    @Test
    public void readParameters() throws  Exception{

        Assert.assertNotNull(controller.getEmployeeByName("","", response));
    }

    @Test
    public void testHome() throws Exception{

        Assert.assertEquals("Hello, I'm working!", controller.welcomeMessage());
    }
}