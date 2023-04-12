package ug.ite.model;

import java.util.ArrayList;
import java.util.List;

public class TodoList extends ListItem {
    private List<ListItem> items = new ArrayList<>();

    public ListItem add(ListItem item) {
        return items.add(item) ? item : null;
    }

    public boolean remove(int id) {
        var item = items.stream().filter(i -> i.getId() == id).findFirst();
        return item.isPresent() ? items.remove(item.get()) : false;
    }

}
