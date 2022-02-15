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

    Map<String, List<String>> queryParams = new HashMap<>();

    queryParams.put("limit", Arrays.asList(new String[] {"3"}));
    Todo[] limitOfThree = db.listTodos(queryParams);
    assertEquals(3, limitOfThree.length, "Incorrect number of todos with limit of 3");

    }

    @Test
    public void filterTodosByCategory() throws IOException {
      TodoDatabase db = new TodoDatabase("/todos.json");
      Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] incompleteStatus = db.filterTodosByStatus(allTodos, "incomplete");
    assertEquals(157, incompleteStatus.length, "Incorrect number of todos with completed status");
  }

  @Test
  public void filterTodosByOwner() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

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

  @Test
  public void filterCategory() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Map<String, List<String>> queryParams = new HashMap<>();


  }
    @Test
    public void filterCompletion() throws IOException {
      TodoDatabase db = new TodoDatabase("/todos.json");
      Map<String, List<String>> queryParams = new HashMap<>();


      queryParams.put("status", Arrays.asList(new String[] {"complete"}));
      Todo[] completed = db.listTodos(queryParams);
      assertEquals(143, completed.length);
    }

    @Test
    public void orderByAttribute() throws IOException {
      TodoDatabase db = new TodoDatabase("/todos.json");
      Map<String, List<String>> queryParams = new HashMap<>();

/* Just checks that it actually returns everything like it is suppose to*/
      queryParams.put("orderBy", Arrays.asList(new String[] {"owner"}));
      Todo[] completed = db.listTodos(queryParams);
      assertEquals(300, completed.length);
    }

}
