package se.challenge.unittests;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.challenge.csv.CSVParser;
import se.challenge.objectmodel.PayRollEntry;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CSVParserTest {
    @Test
    @DisplayName("Creating list of payroll entries from csv test")
    public void CSVParserTest() throws SQLException, ClassNotFoundException, IOException, ParseException {
        List<PayRollEntry> expectedEntries = new ArrayList();
        expectedEntries.add(new PayRollEntry(1, 10.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 4).getTime(), 'A'));
        expectedEntries.add(new PayRollEntry(1, 5.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 14).getTime(), 'A'));
        expectedEntries.add(new PayRollEntry(2, 3.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 20).getTime(), 'B'));
        expectedEntries.add(new PayRollEntry(1, 4.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 20).getTime(), 'A'));

        InputStream stream = new ByteArrayInputStream("date,hours worked,employee id, job group \n 4/01/2020,10,1,A \n 14/01/2020,5,1,A \n 20/01/2020,3,2,B \n 20/01/2020,4,1,A".getBytes());
        List<PayRollEntry> payRollEntriesFromCSV = CSVParser.GetPayRollEntries(stream);

        for (int i = 0; i < expectedEntries.size(); i++) {
            PayRollEntry currentEntry = expectedEntries.get(i);
            PayRollEntry currentEntryFromDB = payRollEntriesFromCSV.get(i);
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
    }
}
