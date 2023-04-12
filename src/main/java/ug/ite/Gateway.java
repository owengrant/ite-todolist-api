package ug.ite;

import io.vertx.core.AbstractVerticle;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import ug.ite.controller.DataStoreController;

public class Gateway extends AbstractVerticle {

    @Override
    public void start() {
        System.out.println(config().encodePrettily());
        vertx.createHttpServer()
                .requestHandler(createRouter())
                .listen(
                        8000,
                        "localhost",
                        handler -> {
                            if(handler.succeeded())
                                System.out.println("Todo List Server Running");
                            else handler.cause().printStackTrace();
                        }
                );
    }
    public Router createRouter() {
        var controller = new DataStoreController();
        var router = Router.router(vertx);
        router.route("/*").handler(BodyHandler.create());
        router.post("/lists").consumes("application/json").handler(controller::createList);
        router.get("/lists").handler(controller::getLists);
        router.get("/lists/:id").handler(controller::getList);
        router.delete("/lists/:id").handler(controller::deleteList);
        return router;
    }
}
