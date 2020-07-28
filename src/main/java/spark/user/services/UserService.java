package spark.user.services;

import java.util.Collection;

import spark.user.User;
import spark.user.exceptions.UserException;

public interface UserService 
{
    public void addUser (User user);
    
    public Collection<User> getUsers ();
    
    public User getUser (String id);
    
    public User editUser (User user) 
      throws UserException;
    
    public void deleteUser (String id);
    
    public boolean userExist (String id);
}