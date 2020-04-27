package ug.ite.model;

import java.util.ArrayList;
import java.util.List;

public class DataStore {
    private List<TodoList> todoLists = new ArrayList<>();

    public TodoList add(TodoList list) {
        return todoLists.add(list) ? list : null;
    }

    public boolean remove(int id) {
        var item = todoLists.stream().filter(i -> i.getId() == id).findFirst();
        return item.isPresent() ? todoLists.remove(item.get()) : false;
    }

    public ListItem addToList(int listId, ListItem item) {
        var todoList = todoLists.stream().filter(list -> list.getId() == listId).findFirst();
        if(todoList.isPresent()) return todoList.get().add(item);
        else return null;
    }

    public TodoList findById(int listId) {
        var first = todoLists.stream().filter(list -> list.getId() == listId).findFirst();
        return first.isPresent() ? first.get() : null;
    }

    public List<TodoList> getTodoLists() {
        return todoLists;
    }

    public void setTodoLists(List<TodoList> todoLists) {
        this.todoLists = todoLists;
    }
}
