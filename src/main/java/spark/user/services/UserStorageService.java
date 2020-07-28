package spark.user.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import spark.user.User;
import spark.user.exceptions.UserException;

public class UserStorageService implements UserService {
    private final String jdbcURL = "jdbc:h2:file:./target/users";

    private Connection con;

    private ResultSet rs;

    private final String addUserSql = "INSERT INTO USERS(USER_ID, FIRSTNAME, LASTNAME, EMAIL) VALUES(?, ?, ?, ?)";
    private final String findUserSql = "SELECT * FROM USERS WHERE USER_ID=?";
    private final String deleteAllUserSql = "DELETE FROM USERS";
    private final String deleteUserSql = "DELETE FROM USERS WHERE USER_ID=?";
    private final String getAllUserSql = "SELECT * FROM USERS";
    private final String userExistsSql = "SELECT * FROM USERS WHERE EXISTS(SELECT * FROM USERS WHERE USER_ID=?)";


    private PreparedStatement addUserPstmt;
    private PreparedStatement getUserPstmt;
    private PreparedStatement deleteUserPstmt;
    private PreparedStatement userExistPstmt;

    private Statement deleteAllUserStmt;
    private Statement getAllUserStmt;

    private HashMap<String, User> userMap = new HashMap<>();

    public UserStorageService() throws SQLException, ClassNotFoundException
    {
            Connection con = DriverManager.getConnection(jdbcURL, "admin", "");

            Class.forName("org.h2.Driver");

            this.con = con;

            addUserPstmt = con.prepareStatement(addUserSql);
            getUserPstmt = con.prepareStatement(findUserSql);
            deleteUserPstmt = con.prepareStatement(deleteUserSql);
            userExistPstmt = con.prepareStatement(userExistsSql);

            deleteAllUserStmt = con.createStatement();
            getAllUserStmt = con.createStatement();

            userMap.put("1018", new User("1018", "John", "Miller", "your-email@your-domain.com"));            

    }

    public Connection getConnection()
    {
        return con;
    }

    @Override
    public User getUser(String id) {
        try
        {
            getUserPstmt.setString(1, id);
            rs = getUserPstmt.executeQuery();

            if(rs.next())
            {
                User u = new User(rs.getString("USER_ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("EMAIL"));

                return u;
            }
        } catch (SQLException sqle) 
        {
            System.out.println(sqle + " : Sql query issues or database");
            sqle.printStackTrace();
        }
        
        return null;
    }

    @Override
    public void addUser(User user) {
        try {

            addUserPstmt.setString(1, user.getId());
            addUserPstmt.setString(2, user.getFirstName());
            addUserPstmt.setString(3, user.getLastName());
            addUserPstmt.setString(4, user.getEmail());

            addUserPstmt.executeUpdate();
        } catch (SQLException sqle) 
        {
            System.out.println(sqle + " : Sql query issues or database");
            sqle.printStackTrace();
        }
    }

    @Override
    public Collection<User> getUsers()
    {
        try
        {
            rs = getAllUserStmt.executeQuery(getAllUserSql);

            if(rs.next())
            {
                User u = new User(rs.getString("USER_ID"), rs.getString("FIRSTNAME"), rs.getString("LASTNAME"), rs.getString("EMAIL"));

                userMap.put(u.getId(), u);
            }
        } catch(SQLException sqle)
        {
            System.out.println(sqle + " : Sql query issues or database");
            sqle.printStackTrace();
        }
        
        return userMap.values();
    }

    @Override
    public User editUser(User user) throws UserException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void deleteUser(String id) {
        try
        {
            deleteUserPstmt.setString(1, id);
            deleteUserPstmt.executeUpdate();
        } catch (SQLException sqle) 
        {
            System.out.println(sqle + " : Sql query issues or database");
            sqle.printStackTrace();
        }
    }

    @Override
    public boolean userExist(String id) {
        boolean result = false;
        try 
        {
            userExistPstmt.setString(1, id);

            rs = userExistPstmt.executeQuery();
            rs.next();

           result = Boolean.valueOf(rs.getBoolean(1));
        } catch (SQLException sqle) {
            System.out.println(sqle + " : Sql query issues or database");
            sqle.printStackTrace();
        }

        return result;
    }

    public void clearUsers(){
        try
        {
            deleteAllUserStmt.executeUpdate(deleteAllUserSql);
        } catch (SQLException sqle) 
        {
            System.out.println(sqle + " : Sql query issues or database");
            sqle.printStackTrace();
        }
    }
}