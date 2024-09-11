package org.example;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import org.example.controller.AddEmployeeController;
import org.example.controller.AppController;
import org.example.controller.GetEmployeeController;
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


        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());

        httpRouting(router);

        vertx.createHttpServer().requestHandler(router)
                .listen(ConfigManager.mainConfig.getInteger("port"), handle -> {
                    if (handle.succeeded()) {
                        System.out.println("Vertical is started on port : "+ConfigManager.mainConfig.getInteger("port"));
                        promise.complete();
                    } else {
                        System.out.println("Error in deployed vertical");
                        promise.fail(handle.cause());
                    }
                });
        //db connection initialization
        DbConnection.initSqlConnection();

    }

    private void httpRouting(Router router) {
        router.get("/check").handler(AppController::checkSta);
        router.post("/employee/add").handler(AddEmployeeController::handle);
        router.get("/employee/details").handler(GetEmployeeController::handle);
    }

}