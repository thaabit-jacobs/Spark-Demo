package spark.main;

import static spark.Spark.*;

import java.sql.SQLException;

import com.google.gson.Gson;

import spark.json.data.StandardResponse;
import spark.json.data.StatusResponse;
import spark.user.User;
import spark.user.services.*;

/*
GET /users — get list of all users
GET /users/:id — get user with given id
POST /users/:id — add a user
PUT /users/:id — edit a particular user
OPTIONS /users/:id — check whether a user exists with given id
DELETE /users/:id — delete a particular user
public class App 
*/

public class App {
    public static void main(String[] args) throws ClassNotFoundException, SQLException
    {
        
        UserStorageService userService = new UserStorageService();

        userService.clearUsers();

        post("/users", (request, response) -> {
            response.type("application/json");
            User user = new Gson().fromJson(request.body(), User.class);
            userService.addUser(user);
         
            return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
        });

        get("/users", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS,new Gson()
                .toJsonTree(userService.getUsers())));
        });

        get("/users/:id", (request, response) -> {
            response.type("application/json");
            return new Gson().toJson(
              new StandardResponse(StatusResponse.SUCCESS,new Gson()
                .toJsonTree(userService.getUser(request.params(":id")))));
        });

        delete("/users/:id", (request, response) -> {
          response.type("application/json");
          userService.deleteUser(request.params(":id"));
          return new Gson().toJson(
            new StandardResponse(StatusResponse.SUCCESS, "user deleted"));
      });

      options("/users/:id", (request, response) -> {
        response.type("application/json");
        return new Gson().toJson(
          new StandardResponse(StatusResponse.SUCCESS, 
            (userService.userExist(
              request.params(":id"))) ? "User exists" : "User does not exists" ));
    });

       // userService.getConnection().close();
    }
}
