package org.example.controller;

import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import org.example.entity.Employee;
import org.example.exception.CustomException;
import org.example.repository.DbConnection;
import org.example.utils.ResponseHelper;

public class UpdateEmployeeController {

    public static void handle(RoutingContext context) {
        try {
            Integer id = Integer.valueOf(context.request().getParam("id"));
            Request request = context.getBodyAsJson().mapTo(Request.class);
            update(request, id);
            ResponseHelper.writeJsonResponse(context);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHelper.handleError(context, e.getMessage());
        }
    }

    private static void update(Request request, Integer id) {
        Employee employee = DbConnection.sqlDb.find(Employee.class).where().eq("id", id)
                .findOneOrEmpty()
                .orElseThrow(() -> new CustomException("Employee Details not found with given Id : " + id));
        if (request.getName()!=null) {
            employee.setName(request.getName());
        }
        if (request.getEmail()!=null) {
            employee.setEmail(request.getEmail());
        }
        if (request.getAge()!=null) {
            employee.setAge(request.getAge());
        }
        if (request.getMobileNumber()!=null) {
            employee.setMobileNumber(request.getMobileNumber());
        }

        DbConnection.sqlDb.update(employee); // update operation
    }

    @Data
    private static class Request {
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;
    }
}
