package umm3601.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;


public class FilterTodosByOwner {


  @Test
  public void filterTodosByOwner() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] OwnedByFry = db.filterTodosByOwner(allTodos, "Fry");
    assertEquals(61, OwnedByFry.length, "Incorrect number of todos with owner Fry");

    Todo[] OwnedByWorkman = db.filterTodosByOwner(allTodos, "Workman");
    assertEquals(49, OwnedByWorkman.length, "Incorrect number of todos with owner Workman");
  }

  @Test
  public void testSize() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    assertEquals(300, db.size(), "Incorrect total Todos");
  }

  @Test
  public void listTodosWithOwnerFilter() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("owner", Arrays.asList(new String[] {"Fry"}));
    Todo[] ownedByFry = db.listTodos(queryParams);
    assertEquals(61, ownedByFry.length, "Incorrect number of users with age 25");
  }

}
