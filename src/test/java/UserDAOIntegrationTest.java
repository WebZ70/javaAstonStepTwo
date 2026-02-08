import hw2.dao.UserDAO;
import hw2.models.User;
import hw2.utils.HibernateSessionFactory;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.lang.reflect.Field;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class UserDAOIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static SessionFactory originalSessionFactory;
    private UserDAO userDAO;

    @BeforeAll
    static void setUpClass() throws Exception {
        Field field = HibernateSessionFactory.class.getDeclaredField("sessionFactory");
        field.setAccessible(true);
        originalSessionFactory = (SessionFactory) field.get(null);

        Configuration config = new Configuration();
        config.setProperty("hibernate.connection.driver_class", "org.postgresql.Driver");
        config.setProperty("hibernate.connection.url", postgres.getJdbcUrl());
        config.setProperty("hibernate.connection.username", postgres.getUsername());
        config.setProperty("hibernate.connection.password", postgres.getPassword());
        config.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        config.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        config.setProperty("hibernate.show_sql", "true");
        config.addAnnotatedClass(User.class);
        SessionFactory testSessionFactory = config.buildSessionFactory();

        field.set(null, testSessionFactory);
    }

    @AfterAll
    static void tearDownClass() throws Exception {
        Field field = HibernateSessionFactory.class.getDeclaredField("sessionFactory");
        field.setAccessible(true);
        field.set(null, originalSessionFactory);
    }

    @BeforeEach
    void setUp() {
        userDAO = new UserDAO();
    }

    @AfterEach
    void tearDown() {
        try (var session = HibernateSessionFactory.getSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Test
    void testFindById_UserExists() {
        User user = new User("John Doe", "john@example.com", 30);
        userDAO.create(user);

        User found = userDAO.findById(user.getId());
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        assertEquals("john@example.com", found.getEmail());
        assertEquals(30, found.getAge());
        assertNotNull(found.getCreated_at());
    }

    @Test
    void testFindById_UserNotExists() {
        User found = userDAO.findById(999);
        assertNull(found);
    }

    @Test
    void testCreate_ValidUser() {
        User user = new User("Jane Doe", "jane@example.com", 25);

        userDAO.create(user);
        assertNotNull(user.getId());

        User found = userDAO.findById(user.getId());
        assertNotNull(found);
        assertEquals("Jane Doe", found.getName());
        assertEquals("jane@example.com", found.getEmail());
        assertEquals(25, found.getAge());
        assertNotNull(found.getCreated_at());
    }

    @Test
    void testCreate_NullUser_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.create(null));
    }

    @Test
    void testUpdate_ValidUser() {
        User user = new User("Old Name", "old@example.com", 20);
        userDAO.create(user);

        user.setName("New Name");
        user.setAge(25);
        userDAO.update(user);

        User updated = userDAO.findById(user.getId());
        assertEquals("New Name", updated.getName());
        assertEquals(25, updated.getAge());
        assertEquals("old@example.com", updated.getEmail());
        assertNotNull(updated.getCreated_at());
    }

    @Test
    void testUpdate_NullUser_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.update(null));
    }

    @Test
    void testDelete_ValidUser() {
        User user = new User("To Delete", "delete@example.com", 40);
        userDAO.create(user);

        userDAO.delete(user);
        User found = userDAO.findById(user.getId());
        assertNull(found);
    }

    @Test
    void testDelete_NullUser_ThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> userDAO.delete(null));
    }

    @Test
    void testFindAll() {
        User user1 = new User("User1", "user1@example.com", 20);
        userDAO.create(user1);

        User user2 = new User("User2", "user2@example.com", 25);
        userDAO.create(user2);

        List<User> users = userDAO.findAll();
        assertEquals(2, users.size());
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("User1") && u.getAge() == 20));
        assertTrue(users.stream().anyMatch(u -> u.getName().equals("User2") && u.getAge() == 25));
        users.forEach(u -> assertNotNull(u.getCreated_at()));
    }
}