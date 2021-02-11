package se.challenge.unittests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.challenge.database.DatabaseUpgrader;
import se.challenge.database.TimeReportEntryBridge;

import javax.management.InvalidAttributeValueException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class TimeReportEntryBridgeTest {
    private String user = "postgres";
    private String password = "bA%&Y66HkT";

    @BeforeEach
    public void setUp() throws Exception {
        DatabaseUpgrader databaseUpgrader = new DatabaseUpgrader("bridgetestdb", user, password);
        databaseUpgrader.CreateAndUpgradeDatabase();
    }

    @Test
    @DisplayName("Adding time report entries from database test")
    public void TimeReportEntryAddTest() throws SQLException, ClassNotFoundException, InvalidAttributeValueException {

        TimeReportEntryBridge timeReportEntryBridge = new TimeReportEntryBridge("jdbc:postgresql://localhost:5432/bridgetestdb", user, password);
        timeReportEntryBridge.Add("time-report-42.csv");
        timeReportEntryBridge.Add("time-report-4355.csv");
        timeReportEntryBridge.Add("time-report-03.csv");

        Throwable exception = assertThrows(InvalidAttributeValueException.class, () -> timeReportEntryBridge.Add("time-report-42.csv"));
        assertEquals("A time report entry with id 42 already exists. This is not allowed.", exception.getMessage());

        exception = assertThrows(InvalidAttributeValueException.class, () -> timeReportEntryBridge.Add("time-report-4355.csv"));
        assertEquals("A time report entry with id 4355 already exists. This is not allowed.", exception.getMessage());

        exception = assertThrows(InvalidAttributeValueException.class, () -> timeReportEntryBridge.Add("time-report-03.csv"));
        assertEquals("A time report entry with id 3 already exists. This is not allowed.", exception.getMessage());

        new DatabaseUpgrader("bridgetestdb", user, password).DropDatabase();
    }
}

