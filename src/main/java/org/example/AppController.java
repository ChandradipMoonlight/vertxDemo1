package org.example;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;

public class AppController {

    public void checkSta(RoutingContext context) {
        JsonArray array = new JsonArray();
        array.add("Java");
        array.add("MySqL");

        JsonObject json = new JsonObject();
        json.put("message", "Hello Vertx");
        json.put("name", "Chandradip");
        json.put("programmingLang", array);
        context.response()
                .putHeader("content-type", "application/json")
                .end(json.encodePrettily());
    }
}
