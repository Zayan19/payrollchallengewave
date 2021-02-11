package se.challenge.unittests;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.challenge.objectmodel.PayRollEntry;
import se.challenge.report.PayRollReport;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PayrollReportGenerationTest {

    @Test
    @DisplayName("Report generation test")
    public void ReportGenerationJSONTest() {

        List<PayRollEntry> entries = new ArrayList();
        String jsonString = "{\r\n  payrollReport: {\r\n    employeeReports: [\r\n      {\r\n        employeeId: 1,\r\n        payPeriod: {\r\n          startDate: \"2020-01-01\",\r\n          endDate: \"2020-01-15\"\r\n        },\r\n        amountPaid: \"$300.00\"\r\n      },\r\n      {\r\n        employeeId: 1,\r\n        payPeriod: {\r\n          startDate: \"2020-01-16\",\r\n          endDate: \"2020-01-31\"\r\n        },\r\n        amountPaid: \"$80.00\"\r\n      },\r\n      {\r\n        employeeId: 2,\r\n        payPeriod: {\r\n          startDate: \"2020-01-16\",\r\n          endDate: \"2020-01-31\"\r\n        },\r\n        amountPaid: \"$90.00\"\r\n      }\r\n    ];\r\n  }\r\n}";
        JSONObject expectedJSON = new JSONObject(jsonString);

        entries.add(new PayRollEntry(1, 10.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 4).getTime(), 'A'));
        entries.add(new PayRollEntry(1, 5.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 14).getTime(), 'A'));
        entries.add(new PayRollEntry(2, 3.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 20).getTime(), 'B'));
        entries.add(new PayRollEntry(1, 4.0F,  new GregorianCalendar(2020, Calendar.JANUARY, 20).getTime(), 'A'));

        assertEquals(new PayRollReport().GetReport(entries).toString(), expectedJSON.toString());

    }
}
