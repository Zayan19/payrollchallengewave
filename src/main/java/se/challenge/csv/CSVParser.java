package se.challenge.csv;
import se.challenge.objectmodel.PayRollEntry;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVParser {
    public static List<PayRollEntry> GetPayRollEntries(InputStream inputStream) throws IOException, ParseException {

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        br.readLine().toString(); // The first line just contains the headings so it can be skipped

        List<PayRollEntry> payrollEntryList = new ArrayList();
        String currentLine;
        while ((currentLine = br.readLine()) != null) {

            // date, hours worked, employee id, job group
            String[] lineArray = currentLine.split(",");

            Date date = new SimpleDateFormat("dd/MM/yyyy").parse(lineArray[0]);
            float hoursWorked = Float.valueOf(lineArray[1]);
            int employeeId = Integer.valueOf(lineArray[2]);
            Character jobGroup = lineArray[3].toCharArray()[0];

            PayRollEntry newEntry = new PayRollEntry(employeeId, hoursWorked, date, jobGroup);
            payrollEntryList.add(newEntry);
        }

        return payrollEntryList;
    }
}