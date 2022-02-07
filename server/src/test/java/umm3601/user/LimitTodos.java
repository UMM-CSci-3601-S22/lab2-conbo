package umm3601.user;

import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;


public class LimitTodos {


  @Test
  public void limitTodos() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo[] allTodos = db.listTodos(new HashMap<>());

    Todo[] OwnedByFry = db.limitTodos(allTodos, 3);
    assertEquals(3, OwnedByFry.length, "Incorrect number of todos with owner Fry");

    }

  }
