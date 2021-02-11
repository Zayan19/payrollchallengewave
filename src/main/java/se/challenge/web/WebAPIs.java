package se.challenge.web;
import org.json.JSONObject;
import se.challenge.csv.CSVParser;
import se.challenge.database.PayrollEntryBridge;
import se.challenge.database.TimeReportEntryBridge;
import se.challenge.objectmodel.PayRollEntry;
import se.challenge.report.PayRollReport;

import java.io.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import javax.management.InvalidAttributeValueException;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "WebApis")
@MultipartConfig
public class WebAPIs extends HttpServlet {

    PayrollEntryBridge payrollEntryBridge;
    TimeReportEntryBridge timeReportEntryBridge;
    PayRollReport payRollReport;

    public void init() {
        String user = "postgres";
        String password = "bA%&Y66HkT";
        payrollEntryBridge = new PayrollEntryBridge("jdbc:postgresql://localhost:5432/testdb", user, password);
        timeReportEntryBridge = new TimeReportEntryBridge("jdbc:postgresql://localhost:5432/testdb", user, password);
        payRollReport = new PayRollReport();
    }

    // Servlet method that handles the GetReport request
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            List<PayRollEntry> payRollEntries = payrollEntryBridge.GetAllEntries();
            JSONObject reportJSON = payRollReport.GetReport(payRollEntries);

            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            PrintWriter out = response.getWriter();
            out.println(reportJSON);
            out.flush();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Servlet method that handles the post request for uploading new valid .csv files
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServletException {
        List<Part> fileParts = request.getParts().stream().collect(Collectors.toList());
        Part filePart = fileParts.get(0);
        PrintWriter out = response.getWriter();

        try {
            timeReportEntryBridge.Add(filePart.getSubmittedFileName());
            List<PayRollEntry> payRollEntries = CSVParser.GetPayRollEntries(filePart.getInputStream());
            payrollEntryBridge.AddMultipleEntries(payRollEntries);
            out.println("Data upload was successful.");
            out.flush();
        } catch (ParseException | SQLException | InvalidAttributeValueException e) {
            e.printStackTrace();
            out.println(e.getMessage());
            out.flush();
        }
    }
}