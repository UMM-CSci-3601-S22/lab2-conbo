package umm3601.user;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

@SuppressWarnings({ "MagicNumber" })
public class FilterTodos {
    @Test
  public void limitTodos() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] ownedByFry = db.limitTodos(allTodos, 3);
    assertEquals(3, ownedByFry.length, "Incorrect number of todos with owner Fry");

    }

    @Test
    public void filterTodosByOwner() throws IOException {
      TodoDatabase db = new TodoDatabase("/todos.json");
      Todo[] allTodos = db.listTodos(new HashMap<>());

      Todo[] ownedByFry = db.filterTodosByOwner(allTodos, "Fry");
      assertEquals(61, ownedByFry.length, "Incorrect number of todos with owner Fry");

      Todo[] ownedByWorkman = db.filterTodosByOwner(allTodos, "Workman");
      assertEquals(49, ownedByWorkman.length, "Incorrect number of todos with owner Workman");
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

    @Test
    public void filterBodyContent() throws IOException {
      TodoDatabase db = new TodoDatabase("/todos.json");
      Map<String, List<String>> queryParams = new HashMap<>();

      queryParams.put("contains", Arrays.asList(new String[] {"Veniam"}));
      Todo[] ownedByFry = db.listTodos(queryParams);
      assertEquals(78, ownedByFry.length);
    }
}
