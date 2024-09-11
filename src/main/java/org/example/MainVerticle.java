package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.controller.AppController;
import org.example.repository.DbConnection;

import java.util.concurrent.CompletableFuture;

public class MainVerticle extends AbstractVerticle {

    private static AsyncResult<String> handler;

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        System.out.println("hello");
        vertx.deployVerticle(new MainVerticle(), handler -> {
            if (handler.failed()) {
                handler.cause().printStackTrace();
            }
        });
        CompletableFuture.runAsync(()-> {
            DbConnection.initSqlConnection();
        });
    }

    @Override
    public void start(Promise<Void> promise) throws Exception {


        ConfigManager.mainConfig=config();

        System.out.println("sqlCredential : "+ ConfigManager.getSqlConfig().encodePrettily());

        int port = 8081;

        AppController controller = new AppController();

        Router router = Router.router(vertx);

        router.route().handler(BodyHandler.create());

//        router.get("/check").handler(context ->controller.checkSta(context));
        router.get("/check").handler(controller::checkSta);



//        router.get("/check").handler(context -> {
//            JsonObject json = new JsonObject();
//            json.put("name", "chandradip");
//            json.put("message", "success");
//            context.response().putHeader("content-type", "application/json")
//                    .end(json.encode());
//        });

        vertx.createHttpServer().requestHandler(router)
                .listen(port, handle -> {
                    if (handle.succeeded()) {
                        System.out.println("Vertical is started on port : "+port);
                        promise.complete();
                    } else {
                        System.out.println("Error in deployed vertical");
                        promise.fail(handle.cause());
                    }
                });
        DbConnection.initSqlConnection();

    }

}