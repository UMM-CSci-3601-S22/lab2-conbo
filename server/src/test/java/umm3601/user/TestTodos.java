package umm3601.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.HashMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import io.javalin.http.Context;
import io.javalin.http.HttpCode;
import io.javalin.http.NotFoundResponse;

import umm3601.Server;

@SuppressWarnings({ "MagicNumber" })
public class TestTodos {

  private Context ctx = mock(Context.class);

  private TodoController todoController;
  private static TodoDatabase db;

  @BeforeEach
  public void setUp() throws IOException {
    ctx.clearCookieStore();

    db = new TodoDatabase(Server.TODOS_DATA_FILE);
    todoController = new TodoController(db);
  }

  @Test
  public void sizeTodosIsCorrect() throws IOException {
    assertEquals(300, db.size());
  }

  @Test
  public void canGetAllTodos() throws IOException {
    todoController.getTodos(ctx);
    // Confirm that `json` was called with all the todos.
    ArgumentCaptor<Todo[]> argument = ArgumentCaptor.forClass(Todo[].class);
    verify(ctx).json(argument.capture());
    assertEquals(db.size(), argument.getValue().length);
  }

  @Test
  public void canGetTodoWithSpecificID() throws IOException {
    String id = "58895985a22c04e761776d54";
    Todo todo = db.getTodo(id);

    when(ctx.pathParam("id")).thenReturn(id);

    todoController.getTodo(ctx);

    verify(ctx).json(todo);
    verify(ctx).status(HttpCode.OK);
  }

  @Test
  public void respondsAppropriatelyToRequestForNonexistentID() throws IOException {
    when(ctx.pathParam("id")).thenReturn(null);
    Assertions.assertThrows(NotFoundResponse.class, () -> {
      todoController.getTodo(ctx);
    });
  }

  @Test
  public void canFilterStatus() throws IOException {
    Todo[] listOfTodos = db.listTodos(new HashMap<>());
    Todo[] completed = db.filterTodosByStatus(listOfTodos, "complete");
    assertEquals(143, completed.length);

    Todo[] incomplete = db.filterTodosByStatus(listOfTodos, "incomplete");
    assertEquals(157, incomplete.length);

    Todo[] unknownString = db.filterTodosByStatus(listOfTodos, "asdfag");
    assertEquals(null, unknownString);

  }

  @Test
  public void limitTodos() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] ownedByFry = db.limitTodos(allTodos, 3);
    assertEquals(3, ownedByFry.length, "Incorrect number of todos with owner Fry");

    }

  @Test
  public void canFilterByCategory() throws IOException {
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] filteredTodos = db.filterTodosByCategory(allTodos, "homework");
    assertEquals(79, filteredTodos.length);
  }

  @Test
  public void filterTodosByStatus() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] completeStatus = db.filterTodosByStatus(allTodos, "complete");
    assertEquals(143, completeStatus.length, "Incorrect number of todos with completed status");
  }


  @Test
  public void canOrderByAttribute() throws IOException {
    Todo[] allTodos = db.listTodos(new HashMap<>());
    Todo[] filteredTodosByOwner = db.filterByAttribute(allTodos, "owner");
    Todo[] filteredTodosByCategory = db.filterByAttribute(allTodos, "category");
    Todo[] filteredTodosByBody = db.filterByAttribute(allTodos, "body");
    Todo[] filteredTodosByStatus = db.filterByAttribute(allTodos, "status");

    /* Just checks that it actually returns everything like it is suppose to */
    assertEquals(300, filteredTodosByOwner.length);
    assertEquals(300, filteredTodosByCategory.length);
    assertEquals(300, filteredTodosByBody.length);
    assertEquals(300, filteredTodosByStatus.length);
  }

  @Test
  public void testSize() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    assertEquals(300, db.size(), "Incorrect total Todos");
  }
}
