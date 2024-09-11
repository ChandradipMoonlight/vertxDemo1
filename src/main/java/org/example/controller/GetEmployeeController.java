package org.example.controller;

import io.ebean.DB;
import io.ebean.ExpressionList;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import org.example.entity.Employee;
import org.example.exception.CustomException;
import org.example.repository.DbConnection;
import org.example.utils.ResponseHelper;

public class GetEmployeeController {

    public static void handle(RoutingContext context) {
        try {
            String employeeId = context.request().getParam("employeeId");
            System.out.println("Request Id :  "+employeeId);
            Integer id = Integer.valueOf(employeeId);
            // parsing string to int
            Employee employee = DbConnection.sqlDb.find(Employee.class, id);
            if (employee==null) {
                throw new CustomException("Employee not found");
            }
            Response response = generateResponse(employee);
            ResponseHelper.writeJsonResponse(context, response);
        } catch (Exception e) {
            e.printStackTrace();
            ResponseHelper.handleError(context, e.getMessage());
        }
    }

    private static Response generateResponse(Employee employee) {
        Response response = new Response();
        response.setEmployeeId(employee.getId());
        response.setName(employee.getName());
        response.setEmail(employee.getEmail());
        if (employee.getAge()!=null && employee.getAge()>0) {
            response.setAge(employee.getAge());
        }
        if (employee.getMobileNumber()!=null) {
            response.setMobileNumber(employee.getMobileNumber());
        }
        return response;
    }

    @Data
    private static class Response {
        private Integer employeeId;
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;
    }

}
