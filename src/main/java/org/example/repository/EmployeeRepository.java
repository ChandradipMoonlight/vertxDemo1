package org.example.repository;

import io.ebean.Database;

public class EmployeeRepository {
    public Database sqlConnection = DbConnection.connect();

}
