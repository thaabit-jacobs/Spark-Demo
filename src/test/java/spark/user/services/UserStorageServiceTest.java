package spark.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import spark.user.User;

public class UserStorageServiceTest 
{
    final User u = new User("Thaabit", "Jacobs");
    final UserStorageService dbService = new UserStorageService();

    public UserStorageServiceTest() throws SQLException, ClassNotFoundException 
    {

    }


    @Test
    void shouldAddUserToDataBase() throws SQLException
    {
        dbService.addUser(u);
        assertEquals("Thaabit", dbService.getUser("#TJ").getFirstName());
        dbService.getConnection().close();
    }

    @Test
    void shouldReturnArrayListOfAllUsers() throws SQLException
    {
        dbService.addUser(u);
        assertEquals("Thaabit", dbService.getUsers().get(0).getFirstName());
        dbService.getConnection().close();
    }

    @Test
    void shouldDeleteUser() throws SQLException
    {
        dbService.addUser(u);
        dbService.deleteUser("#TJ");
        assertEquals(null, dbService.getUser("#TJ"));
        dbService.getConnection().close();
    }

    @Test
    void shouldReturnForUserExists() throws SQLException
    {
        dbService.addUser(u);
        assertEquals(true, dbService.userExist("#TJ"));
        dbService.getConnection().close();
    }
}