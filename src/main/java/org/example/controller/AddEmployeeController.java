package org.example.controller;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import org.example.entity.Employee;
import org.example.repository.DbConnection;
import org.example.utils.ResponseHelper;

public class AddEmployeeController {

    public static void handle(RoutingContext context) {
        try {
            JsonObject requestBody = context.getBodyAsJson();
            Request request = requestBody.mapTo(Request.class);
            crateEmployeeAndSave(request);
            ResponseHelper.writeJsonResponse(context);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHelper.handleError(context, e.getMessage());
        }

    }

    private static void crateEmployeeAndSave(Request request) {
        Employee employee = new Employee();
        employee.setAge(request.age);
        employee.setName(request.name);
        employee.setEmail(request.email);
        employee.setMobileNumber(request.mobileNumber);
        DbConnection.sqlDb.save(employee);
    }

    @Data
    private static class Request {
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;
    }
}
