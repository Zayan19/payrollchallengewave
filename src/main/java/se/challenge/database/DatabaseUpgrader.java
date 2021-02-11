package se.challenge.database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DatabaseUpgrader {
    String databaseName;
    String user;
    String password;


    /**
     *  Creates database and adds required tables if they do not exist
     * @param databaseName The name of the postgres database
     * @param user The default user used to connect to the database with
     * @param password The password for the postgres user, used to connect to the database
     */
    public DatabaseUpgrader(String databaseName, String user, String password) throws ClassNotFoundException, SQLException {
        this.user = user;
        this.password = password;
        Class.forName("org.postgresql.Driver");
        this.databaseName = databaseName;
    }

    public void CreateAndUpgradeDatabase() throws SQLException, ClassNotFoundException {
        Connection initialConnection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", user, password);
        Statement statement = initialConnection.createStatement();
        ResultSet databaseCheck = statement.executeQuery(String.format("SELECT datname FROM pg_database where datname='%s'", databaseName));
        if (!databaseCheck.next()) {
            statement.executeUpdate(String.format("CREATE DATABASE %s", databaseName));
        }
        initialConnection.close();

        String url = String.format("jdbc:postgresql://localhost:5432/%s", databaseName);
        Connection connection = DriverManager.getConnection(url, user, password);
        CreatePayrollEntryTable(connection);
        CreateTimeReportEntryTable(connection);

        connection.close();
    }

    // Creates a table for each payroll entry in the .csv file, note it was not specified whether an employee could change job groups so that is also being stored for each entry
    private void CreatePayrollEntryTable(Connection connection) throws SQLException {
        String createPayRollEntryTable = "CREATE TABLE IF NOT EXISTS payrollentry ( id SERIAL, employeeid INT NOT NULL, hoursworked FLOAT NOT NULL, date TIMESTAMP NOT NULL, jobgroup CHARACTER(1) )";
        Statement statement = connection.createStatement();
        statement.executeUpdate(createPayRollEntryTable);
    }

    // Creates table
    private void CreateTimeReportEntryTable(Connection connection) throws SQLException {
        String timeReportEntryTable = "CREATE TABLE IF NOT EXISTS timereportentry ( id INTEGER UNIQUE NOT NULL)";
        Statement statement = connection.createStatement();
        statement.executeUpdate(timeReportEntryTable);
    }

    // For testing purposes, not part of the core required functionality for this assignment
    public void DropDatabase() throws SQLException {
        Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", user, password);
        Statement statement = connection.createStatement();
        ResultSet databaseCheck = statement.executeQuery(String.format("SELECT datname FROM pg_database where datname='%s'", databaseName));
        statement.executeUpdate(String.format("DROP DATABASE %s", databaseName));
        connection.close();
    }
}