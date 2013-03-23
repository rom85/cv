package test.ss.cvtracker.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.ss.cvtracker.dao.ConstantDAO;
import org.ss.cvtracker.domain.Constant;

@SuppressWarnings("deprecation")
public class JdbcConstantDaoTests extends AbstractTransactionalDataSourceSpringContextTests {

    private ConstantDAO constantDao;
	    
    public void setConstantDao(ConstantDAO constantDao) {
        this.constantDao = constantDao;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:test-context.xml"};
    }

    public void testGetConstantData() {
        
        Constant testConstant = constantDao.getConstantById(1);
        assertEquals("184", testConstant.getConstant()); //Ukraine code = 184
        testConstant = constantDao.getConstantById(2); //log folder
        assertNotSame("", testConstant.getConstant());//Not empty
        
    }
    
    public void testGetConstants(){
    	List<Constant> constants = constantDao.findAllConstant();
        assertEquals("wrong number of constants?", 3, constants.size());
    }
    
    public void testConstantUpdate(){
    	Constant testConstant = constantDao.getConstantById(2);
    	testConstant.setConstant("D:\\ARCHIVE");
    	constantDao.update(testConstant);
    	testConstant = constantDao.getConstantById(2);
    	assertEquals("D:\\ARCHIVE", testConstant.getConstant());
    }
}