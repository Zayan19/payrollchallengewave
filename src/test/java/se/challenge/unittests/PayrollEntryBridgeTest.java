package se.challenge.unittests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.challenge.database.DatabaseUpgrader;
import se.challenge.objectmodel.PayRollEntry;
import se.challenge.database.PayrollEntryBridge;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PayrollEntryBridgeTest {

    private String user = "postgres";
    private String password = "bA%&Y66HkT";

    @BeforeEach
    public void setUp() throws Exception {
        DatabaseUpgrader databaseUpgrader = new DatabaseUpgrader("bridgetestdb", user, password);
        databaseUpgrader.CreateAndUpgradeDatabase();
    }

    @Test
    @DisplayName("Adding and retrieving payroll entries from database test")
    public void PayRollEntryAddAndRetrieveTest() throws SQLException, ClassNotFoundException {
        List<PayRollEntry> entries = new ArrayList();
        entries.add(new PayRollEntry(1, 10.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 4).getTime(), 'A'));
        entries.add(new PayRollEntry(1, 5.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 14).getTime(), 'A'));
        entries.add(new PayRollEntry(2, 3.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 20).getTime(), 'B'));
        entries.add(new PayRollEntry(1, 4.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 20).getTime(), 'A'));
        entries.add(new PayRollEntry(3, 4.0F,  new GregorianCalendar(2020, Calendar.FEBRUARY, 20).getTime(), 'A'));
        entries.add(new PayRollEntry(4, 4.0F,  new GregorianCalendar(2020, Calendar.MARCH, 20).getTime(), 'A'));
        entries.add(new PayRollEntry(5, 4.0F,  new GregorianCalendar(2020, Calendar.APRIL, 20).getTime(), 'A'));

        PayrollEntryBridge payrollEntryBridge = new PayrollEntryBridge("jdbc:postgresql://localhost:5432/bridgetestdb", user, password);
        payrollEntryBridge.AddMultipleEntries(entries);
        List<PayRollEntry> entriesFromDatabase = payrollEntryBridge.GetAllEntries();

        for (int i = 0; i < entries.size(); i++) {
            PayRollEntry currentEntry = entries.get(i);
            PayRollEntry currentEntryFromDB = entriesFromDatabase.get(i);
            if (currentEntry.getEmployeeId() != currentEntryFromDB.getEmployeeId()) {
                assertTrue(false, "Entry employeeids are not equal");
            }
            if (currentEntry.getHoursWorked() != currentEntryFromDB.getHoursWorked()) {
                assertTrue(false, "Entry hours worked are not equal");
            }
            if (currentEntry.getJobGroup() != currentEntryFromDB.getJobGroup()) {
                assertTrue(false, "Entry job groups are not equal");
            }
            if (currentEntry.getDate().getTime() != currentEntryFromDB.getDate().getTime()) {
                assertTrue(false, "Entry dates are not equal");
            }
        }

        new DatabaseUpgrader("bridgetestdb", user, password).DropDatabase();
    }
}
