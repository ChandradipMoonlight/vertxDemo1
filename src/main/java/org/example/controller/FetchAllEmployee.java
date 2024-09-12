package org.example.controller;

import io.ebean.ExpressionList;
import io.vertx.ext.web.RoutingContext;
import lombok.Data;
import org.example.entity.Employee;
import org.example.repository.DbConnection;
import org.example.utils.ResponseHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FetchAllEmployee {

    public static void handle(RoutingContext context) {

        try {
            Response response = new Response();
            ExpressionList<Employee> expressionList = DbConnection.sqlDb.find(Employee.class).where();
            List<Employee> employees = expressionList.findList();
            List<EmployeeData> dataList = employees.stream().map(EmployeeData::new).collect(Collectors.toList());
            response.setEmployees(dataList);
            ResponseHelper.writeJsonResponse(context, response);

        } catch (Exception e) {
            e.printStackTrace();
            ResponseHelper.handleError(context, e.getMessage());
        }
    }

    @Data
    private static class Response {
        List<EmployeeData> employees = new ArrayList<>();
    }

    @Data
    private static class EmployeeData {
        private Integer employeeId;
        private String name;
        private Integer age;
        private String email;
        private String mobileNumber;
        EmployeeData(Employee employee) {
            this.employeeId = employee.getId();
            this.name = employee.getName();
            this.age = employee.getAge();
            this.mobileNumber = employee.getMobileNumber();
        }
    }
}
