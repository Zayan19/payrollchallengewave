package se.challenge.database;
import javax.management.InvalidAttributeValueException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TimeReportEntryBridge {
    private String url;
    private String user;
    private String password;

    /**
     *  Used to add time report entry ids to the database to ensure no duplicate entries are uploaded
     * @param newUrl The url path to the database
     * @param user The default user used to connect to the database with
     * @param password The password for the postgres user, used to connect to the database
     */
    public TimeReportEntryBridge(String newUrl, String user, String password) {
        url = newUrl;
        this.user = user;
        this.password = password;
    }

    public void Add(String timeReportName) throws InvalidAttributeValueException, SQLException {
        int timeReportId = GetTimeReportId(timeReportName);
        Connection connection = DriverManager.getConnection(url, user, password);
        try {

            // Attempt to add the id to the time report entry table, if the id already exists it will throw a duplicate key exception
            String query = "INSERT INTO timereportentry(id) VALUES(?)";
            PreparedStatement st = connection.prepareStatement(query);
            st.setInt(1, timeReportId);
            st.executeUpdate();
            connection.close();
        }
        catch (SQLException e) {
            connection.close();
            throw new InvalidAttributeValueException(String.format("A time report entry with id %s already exists. This is not allowed.", timeReportId));
        }
    }

    private int GetTimeReportId(String timeReportName) {
        StringBuilder sb = new StringBuilder();

        int end = timeReportName.length() - 1;
        Character current = timeReportName.charAt(end);

        // Move the pointer past the .csv extension in the name
        while (current != '.') {
            end--;
            current = timeReportName.charAt(end);
        }
        end--;
        current = timeReportName.charAt(end);

        // Everything in between the '.' and last '-' should be a valid integer
        while (current != '-') {
            sb.append(current);
            end--;
            current = timeReportName.charAt(end);
        }
        return Integer.valueOf(sb.reverse().toString());
    }
}