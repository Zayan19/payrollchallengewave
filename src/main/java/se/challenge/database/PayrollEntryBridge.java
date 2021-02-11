package se.challenge.database;
import se.challenge.objectmodel.PayRollEntry;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class PayrollEntryBridge {

    private String url;
    private String user;
    private String password;

    /**
     *  Used to add and retrieve pay roll entry data to and from the database
     * @param newUrl The url path to the database
     * @param user The default user used to connect to the database with
     * @param password The password for the postgres user, used to connect to the database
     */
    public PayrollEntryBridge(String newUrl, String user, String password) {
        this.url = newUrl;
        this.user = user;
        this.password = password;
    }

    public void AddMultipleEntries(List<PayRollEntry> entries) throws SQLException {
        if (entries.size() == 0) {
            return;
        }

        Connection connection = DriverManager.getConnection(url, user, password);
        String query = "INSERT INTO payrollentry(employeeid, hoursworked, date, jobgroup) VALUES(?, ?, ?, ?)";
        for (PayRollEntry entry : entries) {
            AddDB(entry, connection, query);
        }
        connection.close();
    }

    private static void AddDB(PayRollEntry entry, Connection connection, String query) throws SQLException {
        PreparedStatement st = connection.prepareStatement(query);
        st.setInt(1, entry.getEmployeeId());
        st.setFloat(2, entry.getHoursWorked());
        st.setDate(3, new java.sql.Date(entry.getDate().getTime()));
        st.setString(4, String.valueOf(entry.getJobGroup()));
        st.executeUpdate();
    }

    public List<PayRollEntry> GetAllEntries() throws SQLException {

        List<PayRollEntry> allEntries = new ArrayList();

        Connection connection = DriverManager.getConnection(url, user, password);
        String query = "Select employeeid, hoursworked, date, jobgroup from payrollentry";
        Statement st = connection.createStatement();
        ResultSet results = st.executeQuery(query);

        while (results.next())
        {
            PayRollEntry entryFromDB = new PayRollEntry(results.getInt("employeeid"), results.getFloat("hoursworked"), results.getDate("date"), results.getString("jobgroup").charAt(0));
            allEntries.add(entryFromDB);
        }
        connection.close();
        return allEntries;
    }
}