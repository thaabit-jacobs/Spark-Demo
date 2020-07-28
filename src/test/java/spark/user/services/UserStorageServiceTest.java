package spark.user.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import spark.user.User;

public class UserStorageServiceTest 
{
    final User u = new User("10101", "Thaabit", "Jacobs", "thaabit@gmail.com");
    final UserStorageService dbService = new UserStorageService();

    public UserStorageServiceTest() throws SQLException, ClassNotFoundException 
    {

    }


    @Test
    void shouldAddUserToDataBase() throws SQLException
    {
        dbService.addUser(u);
        assertEquals("Thaabit", dbService.getUser("10101").getFirstName());
        dbService.getConnection().close();
    }

    @Test
    void shouldDeleteUser() throws SQLException
    {
        dbService.addUser(u);
        dbService.deleteUser("10101");
        assertEquals(null, dbService.getUser("10101"));
        dbService.getConnection().close();
    }

    @Test
    void shouldReturnForUserExists() throws SQLException
    {
        dbService.addUser(u);
        assertEquals(true, dbService.userExist("10101"));
        dbService.getConnection().close();
    }
}