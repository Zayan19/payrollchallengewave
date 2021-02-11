package se.challenge.objectmodel;
import java.util.Date;

public class PayRollEntry {

    private int employeeId;
    private float hoursWorked;
    private Date date;
    private Character jobGroup;

    /**
     *  An entity representing each row of data in the given parsed csv
     *  Note: The specifications did mention whether or not an employee could be in multiple job groups for different entries so this
        was also stored
     * @param employeeId The id representing a unique employee
     * @param hoursWorked How many hours the employee worked on that particular date
     * @param jobGroup The job group the employee belongs which corresponds to their pay rate
     */
    public PayRollEntry(int employeeId, float hoursWorked, Date date, Character jobGroup) {
        this.employeeId = employeeId;
        this.hoursWorked = hoursWorked;
        this.date = date;
        this.jobGroup = jobGroup;
    }

    // Note, getters are provided here but no setters as the assignment did not indicate these values could or should be changed after the fact
    public int getEmployeeId(){
        return employeeId;
    }

    public float getHoursWorked(){
        return hoursWorked;
    }

    public Date getDate(){
        return date;
    }

    public Character getJobGroup(){
        return jobGroup;
    }
}
