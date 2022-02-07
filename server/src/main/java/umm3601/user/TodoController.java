package umm3601.user;

import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;

public class TodoController {

  private TodoDatabase database;

  public TodoController(TodoDatabase database) {
    this.database = database;
  }

  public void getTodo(Context ctx) {
    String id = ctx.pathParam("id");
    Todo todo = database.getTodo(id);
    if (todo != null) {
      ctx.json(todo);
      ctx.status(HttpCode.OK);
    } else {
      throw new NotFoundResponse("No todo with id " + id + " was found.");
    }
  }

  public void getTodos(Context ctx) {
    Todo[] todos = database.listTodos(ctx.queryParamMap());
    ctx.json(todos);
  }

}
