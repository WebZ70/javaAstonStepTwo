import hw2.dao.UserDAO;
import hw2.models.User;
import hw2.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceUnitTest {

    @Mock
    private UserDAO userDAO;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUser() {
        User mockUser = new User("John Doe", "john@example.com", 30);
        when(userDAO.findById(1)).thenReturn(mockUser);

        User result = userService.findUser(1);
        assertEquals(mockUser, result);
        verify(userDAO, times(1)).findById(1);
    }

    @Test
    void testCreateUser() {
        User user = new User("Jane Doe", "jane@example.com", 25);
        userService.createUser(user);
        verify(userDAO, times(1)).create(user);
    }

    @Test
    void testUpdateUser() {
        User user = new User("Updated Name", "updated@example.com", 35);
        userService.updateUser(user);
        verify(userDAO, times(1)).update(user);
    }

    @Test
    void testDeleteUser() {
        User user = new User("To Delete", "delete@example.com", 40);
        userService.deleteUser(user);
        verify(userDAO, times(1)).delete(user);
    }

    @Test
    void testFindAllUsers() {
        List<User> mockUsers = Arrays.asList(
                new User("User1", "user1@example.com", 20),
                new User("User2", "user2@example.com", 25)
        );
        when(userDAO.findAll()).thenReturn(mockUsers);

        List<User> result = userService.findAllUsers();
        assertEquals(2, result.size());
        assertEquals("User1", result.get(0).getName());
        assertEquals(20, result.get(0).getAge());
        assertEquals("User2", result.get(1).getName());
        assertEquals(25, result.get(1).getAge());
        verify(userDAO, times(1)).findAll();
    }
}