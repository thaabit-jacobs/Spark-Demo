package spark.user;

public class User 
{
    private String id;
    private String firstName;
    private String lastName;
    private String email;

    public User(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = this.firstName + this.lastName + "@user.com";
        this.id = "#" + this.firstName.substring(0, 1) + this.lastName.substring(0, 1); 
    }
    
    public String getId()
    {
        return id;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public String getEmail()
    {
        return email;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }
}