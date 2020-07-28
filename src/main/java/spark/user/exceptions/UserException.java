package spark.user.exceptions;

public class UserException extends Exception
{
    public UserException()
    {
        super();
    }

    public UserException(String errMsg)
    {
        super(errMsg);
    }
}