package test.ss.cvtracker.web;
import org.ss.cvtracker.web.HomeController;

import junit.framework.TestCase;

public class HomeControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{		
        HomeController controller = new HomeController();
        String returned = controller.home();		
        assertEquals("redirect:/inbox", returned);
    }
}