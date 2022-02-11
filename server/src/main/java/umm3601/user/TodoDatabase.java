package umm3601.user;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.lang.model.SourceVersion;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.javalin.http.BadRequestResponse;

/**
 * A fake "database" of user info
 * <p>
 * Since we don't want to complicate this lab with a real database, we're going
 * to instead just read a bunch of user data from a specified JSON file, and
 * then provide various database-like methods that allow the `UserController` to
 * "query" the "database".
 */
public class TodoDatabase {

  private Todo[] allTodos;

  public TodoDatabase(String todoDataFile) throws IOException {
    InputStreamReader reader = new InputStreamReader(getClass().getResourceAsStream(todoDataFile));
    ObjectMapper objectMapper = new ObjectMapper();
    allTodos = objectMapper.readValue(reader, Todo[].class);
  }

  public int size() {
    return allTodos.length;
  }

  /**
   * Get the single user specified by the given ID. Return `null` if there is no
   * user with that ID.
   *
   * @param id the ID of the desired user
   * @return the user with the given ID, or null if there is no user with that ID
   */
  public Todo getTodo(String id) {
    return Arrays.stream(allTodos).filter(x -> x._id.equals(id)).findFirst().orElse(null);
  }

  /**
   * Get an array of all the users satisfying the queries in the params.
   *
   * @param queryParams map of key-value pairs for the query
   * @return an array of all the users matching the given criteria
   */
  public Todo[] listTodos(Map<String, List<String>> queryParams) {
    Todo[] filteredTodos = allTodos;


    if (queryParams.containsKey("owner")) {
      String targetOwner = queryParams.get("owner").get(0);
      filteredTodos = filterTodosByOwner(filteredTodos, targetOwner);
    }

    if (queryParams.containsKey("contains")) {
      String contains = queryParams.get("contains").get(0);
      filteredTodos = filterTodosByBody(filteredTodos, contains);
    }

    if (queryParams.containsKey("limit")) {
      String limitParam = queryParams.get("limit").get(0);
      try {
        int targetLimit = Integer.parseInt(limitParam);
        filteredTodos = limitTodos(filteredTodos, targetLimit);
      } catch (NumberFormatException e) {
        throw new BadRequestResponse("Specified limit '" + limitParam + "' can't be parsed to an integer");
      }
    }

    if (queryParams.containsKey("status")) {
      String complete = queryParams.get("status").get(0);
      filteredTodos = filterTodosByStatus(filteredTodos, complete);
    }

    if (queryParams.containsKey("category")) {
      String groceries  = queryParams.get("category").get(0);
      filteredTodos = filterTodosByCategory(filteredTodos, groceries );
    }

    if (queryParams.containsKey("orderBy")) {
      String attribute  = queryParams.get("orderBy").get(0);
      filteredTodos = filterByAttribute(filteredTodos, attribute);
    }

    return filteredTodos;
  }


  public Todo[] filterTodosByOwner(Todo[] todos, String targetOwner) {
    return Arrays.stream(todos).filter(x -> x.owner.equals(targetOwner)).toArray(Todo[]::new);
  }

  public Todo[] limitTodos(Todo[] todos, int limit) {
    return Arrays.stream(todos).limit(limit).toArray(Todo[]::new);
  }

  public Todo[] filterTodosByStatus(Todo[] todos, String completionParam) {
    if (completionParam.equals("complete")){
      return Arrays.stream(todos).filter(x -> x.status == true).toArray(Todo[]::new);
    } else if (completionParam.equals("incomplete")){
      return Arrays.stream(todos).filter(x -> x.status == false).toArray(Todo[]::new);
    } else {
      return null;
    }
  }

  public Todo[] filterTodosByBody(Todo[] todos, String contains) {
    return Arrays.stream(todos).filter(x -> x.body.toLowerCase().contains(contains.toLowerCase())).toArray(Todo[]::new);
  }

  public Todo[] filterTodosByCategory(Todo[] todos, String category) {
    return Arrays.stream(todos).filter(x -> x.category.toLowerCase().equals(category.toLowerCase())).toArray(Todo[]::new);
  }

  public Todo[] filterByAttribute(Todo[] todos, String attribute){


    if(attribute.equals("owner")) {
      for (int i = 1; i < todos.length; i++){
        Todo curr = todos[i];
        int j = i - 1;
        while(j >= 0 && todos[j].owner.compareTo(curr.owner) >= 0) {
          todos[j + 1] = todos[j];
          j = j - 1;
        }
        todos[j + 1] = curr;
      }
    }

    if(attribute.equals("category")) {
      for (int i = 1; i < todos.length; i++){
        Todo curr = todos[i];
        int j = i - 1;
        while(j >= 0 && todos[j].category.compareTo(curr.category) >= 0) {
          todos[j + 1] = todos[j];
          j = j - 1;
        }
        todos[j + 1] = curr;
      }
    }

    if(attribute.equals("body")) {
      for (int i = 1; i < todos.length; i++){
        Todo curr = todos[i];
        int j = i - 1;
        while(j >= 0 && todos[j].body.compareTo(curr.body) >= 0) {
          todos[j + 1] = todos[j];
          j = j - 1;
        }
        todos[j + 1] = curr;
      }
    }

    if(attribute.equals("status")) {
      for (int i = 1; i < todos.length; i++){
        Todo curr = todos[i];
        int j = i - 1;
        while(j >= 0 && todos[j].status == true) {
          todos[j + 1] = todos[j];
          j = j - 1;
        }
        todos[j + 1] = curr;
      }
    }
    return todos;
  }

}
