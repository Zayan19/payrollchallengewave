package se.challenge.web;
import se.challenge.database.DatabaseUpgrader;

import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.sql.SQLException;

@WebServlet(name = "OnStartup")
public class OnStartup extends HttpServlet {
    private String message;

    public void init() {
        try {
            new DatabaseUpgrader("testdb", "postgres", "bA%&Y66HkT").CreateAndUpgradeDatabase();
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}