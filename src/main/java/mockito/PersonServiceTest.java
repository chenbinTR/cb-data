package mockito;

import org.junit.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class PersonServiceTest {
    // 被测类
    private PersonService personService;
    // 模拟对象
    @Mock
    private PersonDao personDAO;

    @BeforeClass
    public static void setUpClass() {
        System.out.println("最先执行");
    }

    @AfterClass
    public static void tearDownClass() {
        System.out.println("最后执行");
    }

    // 在@Test标注的测试方法之前运行  
    @Before
    public void setUp() throws Exception {
        System.out.println("预处理");
        // 初始化测试用例类中由Mockito的注解标注的所有模拟对象  
        MockitoAnnotations.initMocks(this);
        // 用模拟对象创建被测类对象  
        personService = new PersonService(personDAO);
    }

    @After
    public void tearDown() {
        System.out.println("after处理");
    }

    @Test
    public void shouldUpdatePersonName() {
        Person person = new Person(1, "Phillip");
        // 设置模拟对象的返回预期值  
        when(personDAO.fetchPerson(1)).thenReturn(person);
        // 执行测试  
        boolean updated = personService.update(1, "David");
        // 验证更新是否成功  
        assertTrue(updated);
        // 验证模拟对象的fetchPerson(1)方法是否被调用了一次  
        verify(personDAO).fetchPerson(1);
        // 得到一个抓取器  
        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        // 验证模拟对象的update()是否被调用一次，并抓取调用时传入的参数值  
        verify(personDAO).update(personCaptor.capture());
        // 获取抓取到的参数值  
        Person updatePerson = personCaptor.getValue();
        // 验证调用时的参数值  
        assertEquals("David", updatePerson.getPersonName());
        // asserts that during the test, there are no other calls to the mock object.  
        // 检查模拟对象上是否还有未验证的交互  
        verifyNoMoreInteractions(personDAO);
    }

    @Test
    public void shouldNotUpdateIfPersonNotFound() {
        // 设置模拟对象的返回预期值  
        when(personDAO.fetchPerson(1)).thenReturn(null);
        // 执行测试  
        boolean updated = personService.update(1, "David");
        // 验证更新是否失败  
        assertFalse(updated);
        // 验证模拟对象的fetchPerson(1)方法是否被调用了一次  
        verify(personDAO).fetchPerson(1);
        // 验证模拟对象是否没有发生任何交互  
        verifyZeroInteractions(personDAO);
        // 检查模拟对象上是否还有未验证的交互  
        verifyNoMoreInteractions(personDAO);
    }

    /**
     * Test of update method, of class PersonService.
     */
    @Test
    public void testUpdate() {
        System.out.println("update");
        Integer personId = null;
        String name = "Phillip";
        PersonService instance = new PersonService(new PersonDao() {

            @Override
            public Person fetchPerson(Integer personID) {
                System.out.println("Not supported yet.");
                return null;
            }

            @Override
            public void update(Person person) {
                System.out.println("Not supported yet.");
            }
        });
        boolean expResult = false;
        boolean result = instance.update(personId, name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.  
        fail("The test case is a prototype.");
    }
}