package test.ss.cvtracker.web;

import org.springframework.web.servlet.ModelAndView;
import org.ss.cvtracker.web.ErrorController;

import junit.framework.TestCase;

public class ErrorControllerTests extends TestCase {

    public void testHandleRequestView() throws Exception{		
        ErrorController controller = new ErrorController();
        ModelAndView mav = controller.error();		
        assertEquals("error", mav.getViewName());
        assertNotNull(mav.getModel());
    }
}