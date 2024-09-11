package org.example.repository;


import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.dbplatform.mysql.MySqlPlatform;
import io.ebean.datasource.DataSourceConfig;
import io.vertx.core.json.JsonObject;
import org.example.ConfigManager;

public class DbConnection {

    private static Database sqlDb;

    private static DataSourceConfig dataSourceConfig() {
        // getting sql credential from config.json file
        JsonObject sqlConfig = ConfigManager.getSqlConfig();
        System.out.println("sql :" +sqlConfig);
        DataSourceConfig config = new DataSourceConfig();
        config.setUrl(sqlConfig.getString("url"));
        config.setDriver(sqlConfig.getString("driver"));
        config.setPassword(sqlConfig.getString("password"));
        config.setUsername(sqlConfig.getString("username"));
        return config;
    }

    public static Database connect(){
        DatabaseConfig dbConfig = new DatabaseConfig();
        dbConfig.addPackage("org.example.entity");
        dbConfig.setDataSourceConfig(dataSourceConfig());

        dbConfig.setDdlGenerate(true);
        dbConfig.setDdlRun(true);
        dbConfig.setDefaultServer(true);
        dbConfig.setDdlCreateOnly(true);
        dbConfig.setRegister(true);
        dbConfig.setDatabasePlatform(new MySqlPlatform());

        return DatabaseFactory.create(dbConfig);
    }

    public static void initSqlConnection() {
            if (sqlDb==null) {
                sqlDb = connect();
            }
            if (sqlDb!=null) {
                System.out.println("Sql Db connection ready");
            }
    }

}
