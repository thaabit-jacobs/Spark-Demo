package spark.main;

import static spark.Spark.*;

/*
GET /users — get list of all users
GET /users/:id — get user with given id
POST /users/:id — add a user
PUT /users/:id — edit a particular user
OPTIONS /users/:id — check whether a user exists with given id
DELETE /users/:id — delete a particular user
public class App 
*/

public class App 
{
    public static void main( String[] args )
    {
        get("/hello", (req, res)->"Hello, world");
        
        get("/hello/:name", (req,res)->{
            return "Hello, "+ req.params(":name");
        });
    }
}
