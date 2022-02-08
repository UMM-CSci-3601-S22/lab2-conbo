package umm3601.user;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FilterUsersByIDFromDB {
  @Test
  public void getBlanche() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo todo = db.getTodo("5889598585bda42fb8388ba1");
    assertEquals("Blanche", todo.owner, "Incorrect name");
  }

  @Test
  public void getFry() throws IOException {
    TodoDatabase db = new TodoDatabase("/todos.json");
    Todo todo = db.getTodo("58895985ee4964bdc668bd9e");
    assertEquals("Fry", todo.owner, "Incorrect name");
  }
}
