package ug.ite.controller;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import ug.ite.model.DataStore;
import ug.ite.model.ListItem;
import ug.ite.model.TodoList;

import java.util.stream.Collectors;

public class DataStoreController {

    private DataStore data = new DataStore();

    public void createList(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        var list = new TodoList();
        list.setId(body.getInteger("id"));
        list.setTitle(body.getString("title"));
        list.setDescription(body.getString("description"));
        var newList = data.add(list);
        ctx.response().putHeader("content-type", "application/json").end(listToJson(newList).encode());
    }

    public void getLists(RoutingContext ctx) {
        var lists = data.getTodoLists().stream().map(this::listToJson).collect(Collectors.toList());
        ctx.response().putHeader("content-type", "application/json").end(new JsonArray(lists).encode());
    }

    public void getList(RoutingContext ctx) {
        int id = Integer.valueOf(ctx.pathParam("id"));
        var list = data.findById(id);
        ctx.response().putHeader("content-type", "application/json").end(listToJson(list).encode());
    }

    public void deleteList(RoutingContext ctx) {
        int id = Integer.valueOf(ctx.pathParam("id"));
        var removed = data.remove(id);
        ctx.response()
                .putHeader("content-type", "application/json")
                .end(new JsonObject().put("deleted", removed).encode());
    }

    public void createListItem(RoutingContext ctx) {
        var body = ctx.body().asJsonObject();
        var id = Integer.valueOf(ctx.pathParam("id"));
        var item = body.mapTo(ListItem.class);
        var newItem = data.addToList(id, item);
        ctx.response().putHeader("content-type", "application/json").end(listToJson(newItem).encode());
    }
    private JsonObject listToJson(ListItem list) {
        if(list == null) return new JsonObject();
        else return new JsonObject()
            .put("id", list.getId())
            .put("title", list.getTitle())
            .put("description", list.getDescription());
    }

}
