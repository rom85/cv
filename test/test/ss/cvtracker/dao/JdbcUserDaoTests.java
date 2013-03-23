package test.ss.cvtracker.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.test.AbstractTransactionalDataSourceSpringContextTests;
import org.ss.cvtracker.dao.UserDAO;
import org.ss.cvtracker.dao.UserDAOImpl;
import org.ss.cvtracker.domain.User;

@SuppressWarnings("deprecation")
public class JdbcUserDaoTests extends AbstractTransactionalDataSourceSpringContextTests {

    private UserDAO userDao;
	private DataSource dataSource;

    
    public void setUserDao(UserDAO userDao) {
        this.userDao = userDao;
    }

    @Override
    protected String[] getConfigLocations() {
        return new String[] {"classpath:test-context.xml"};
    }

//    @Override
//    protected void onSetUpInTransaction() throws Exception {
//        super.deleteFromTables(new String[] {"products"});
//        super.executeSqlScript("file:db/load_data.sql", true);
//    }

    public void testGetUserData() {
        
        User testUser = userDao.getUserByName("test");
        
        assertEquals("test", testUser.getUsername());
        assertEquals("098f6bcd4621d373cade4e832627b4f6", testUser.getPassword());//MD5-hash for password "test"
        
    }
    
    public void testGetUsers(){
    	List<User> users = userDao.findUsers();
        assertEquals("wrong number of products?", 5, users.size());
    }
    
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
    
//    public void testAddUser() {
//       //adding a new user
//        User testuser = new User();
//        testuser.setUsername("testUser");
//        testuser.setPassword("33ef37db24f3a27fb520847dcd549e9f");// MD5-hash for password "testUser"
//        userDao.addUser(testuser);
//        //getting new user credentials
//        User testUser = userDao.getUserByName("testUser");
//        assertEquals("testUser", testUser.getUsername());
//        assertEquals("33ef37db24f3a27fb520847dcd549e9f", testUser.getPassword());//MD5-hash for password "test"
//        }

    }