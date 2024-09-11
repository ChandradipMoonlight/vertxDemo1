package org.example;

import io.vertx.core.json.JsonObject;
import lombok.Data;

@Data
public class ConfigManager {
    public static JsonObject mainConfig;

    public static JsonObject getSqlConfig() {
        return mainConfig.getJsonObject("sql");
    }
}
