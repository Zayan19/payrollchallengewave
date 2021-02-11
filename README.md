# Wave Software Development Challenge

Applicants for the Full-stack Developer role at Wave must
complete the following challenge, and submit a solution prior to the onsite
interview.

The purpose of this exercise is to create something that we can work on
together during the onsite. We do this so that you get a chance to collaborate
with Wavers during the interview in a situation where you know something better
than us (it's your code, after all!)

There isn't a hard deadline for this exercise; take as long as you need to
complete it. However, in terms of total time spent actively working on the
challenge, we ask that you not spend more than a few hours, as we value your
time and are happy to leave things open to discussion in the on-site interview.

Please use whatever programming language and framework you feel the most
comfortable with.

Feel free to email [dev.careers@waveapps.com](dev.careers@waveapps.com) if you
have any questions.

## Project Description

Imagine that this is the early days of Wave's history, and that we are prototyping a new payroll system API. A front end (that hasn't been developed yet, but will likely be a single page application) is going to use our API to achieve two goals:

1. Upload a CSV file containing data on the number of hours worked per day per employee
1. Retrieve a report detailing how much each employee should be paid in each _pay period_

All employees are paid by the hour (there are no salaried employees.) Employees belong to one of two _job groups_ which determine their wages; job group A is paid $20/hr, and job group B is paid $30/hr. Each employee is identified by a string called an "employee id" that is globally unique in our system.

Hours are tracked per employee, per day in comma-separated value files (CSV).
Each individual CSV file is known as a "time report", and will contain:

1. A header, denoting the columns in the sheet (`date`, `hours worked`,
   `employee id`, `job group`)
1. 0 or more data rows

In addition, the file name should be of the format `time-report-x.csv`,
where `x` is the ID of the time report represented as an integer. For example, `time-report-42.csv` would represent a report with an ID of `42`.

You can assume that:

1. Columns will always be in that order.
1. There will always be data in each column and the number of hours worked will always be greater than 0.
1. There will always be a well-formed header line.
1. There will always be a well-formed file name.

A sample input file named `time-report-42.csv` is included in this repo.

### What your API must do:

We've agreed to build an API with the following endpoints to serve HTTP requests:

1. An endpoint for uploading a file.

   - This file will conform to the CSV specifications outlined in the previous section.
   - Upon upload, the timekeeping information within the file must be stored to a database for archival purposes.
   - If an attempt is made to upload a file with the same report ID as a previously uploaded file, this upload should fail with an error message indicating that this is not allowed.

1. An endpoint for retrieving a payroll report structured in the following way:

   _NOTE:_ It is not the responsibility of the API to return HTML, as we will delegate the visual layout and redering to the front end. The expectation is that this API will only return JSON data.

   - Return a JSON object `payrollReport`.
   - `payrollReport` will have a single field, `employeeReports`, containing a list of objects with fields `employeeId`, `payPeriod`, and `amountPaid`.
   - The `payPeriod` field is an object containing a date interval that is roughly biweekly. Each month has two pay periods; the _first half_ is from the 1st to the 15th inclusive, and the _second half_ is from the 16th to the end of the month, inclusive. `payPeriod` will have two fields to represent this interval: `startDate` and `endDate`.
   - Each employee should have a single object in `employeeReports` for each pay period that they have recorded hours worked. The `amountPaid` field should contain the sum of the hours worked in that pay period multiplied by the hourly rate for their job group.
   - If an employee was not paid in a specific pay period, there should not be an object in `employeeReports` for that employee + pay period combination.
   - The report should be sorted in some sensical order (e.g. sorted by employee id and then pay period start.)
   - The report should be based on all _of the data_ across _all of the uploaded time reports_, for all time.

   As an example, given the upload of a sample file with the following data:

    <table>
    <tr>
      <th>
        date
      </th>
      <th>
        hours worked
      </th>
      <th>
        employee id
      </th>
      <th>
        job group
      </th>
    </tr>
    <tr>
      <td>
        2020-01-04
      </td>
      <td>
        10
      </td>
      <td>
        1
      </td>
      <td>
        A
      </td>
    </tr>
    <tr>
      <td>
        2020-01-14
      </td>
      <td>
        5
      </td>
      <td>
        1
      </td>
      <td>
        A
      </td>
    </tr>
    <tr>
      <td>
        2020-01-20
      </td>
      <td>
        3
      </td>
      <td>
        2
      </td>
      <td>
        B
      </td>
    </tr>
    <tr>
      <td>
        2020-01-20
      </td>
      <td>
        4
      </td>
      <td>
        1
      </td>
      <td>
        A
      </td>
    </tr>
    </table>

   A request to the report endpoint should return the following JSON response:

   ```javascript
   {
     payrollReport: {
       employeeReports: [
         {
           employeeId: 1,
           payPeriod: {
             startDate: "2020-01-01",
             endDate: "2020-01-15"
           },
           amountPaid: "$300.00"
         },
         {
           employeeId: 1,
           payPeriod: {
             startDate: "2020-01-16",
             endDate: "2020-01-31"
           },
           amountPaid: "$80.00"
         },
         {
           employeeId: 2,
           payPeriod: {
             startDate: "2020-01-16",
             endDate: "2020-01-31"
           },
           amountPaid: "$90.00"
         }
       ];
     }
   }
   ```

We consider ourselves to be language agnostic here at Wave, so feel free to use any combination of technologies you see fit to both meet the requirements and showcase your skills. We only ask that your submission:

- Is easy to set up
- Can run on either a Linux or Mac OS X developer machine
- Does not require any non open-source software

### Documentation:

Please commit the following to this `README.md`:

1. Instructions on how to build/run your application
1. Answers to the following questions:
   - How did you test that your implementation was correct?
   - If this application was destined for a production environment, what would you add or change?
   - What compromises did you have to make as a result of the time constraints of this challenge?

## Submission Instructions

1. Clone the repository.
1. Complete your project as described above within your local repository.
1. Ensure everything you want to commit is committed.
1. Create a git bundle: `git bundle create your_name.bundle --all`
1. Email the bundle file to [dev.careers@waveapps.com](dev.careers@waveapps.com) and CC the recruiter you have been in contact with.

## Evaluation

Evaluation of your submission will be based on the following criteria.

1. Did you follow the instructions for submission?
1. Did you complete the steps outlined in the _Documentation_ section?
1. Were models/entities and other components easily identifiable to the
   reviewer?
1. What design decisions did you make when designing your models/entities? Are
   they explained?
1. Did you separate any concerns in your application? Why or why not?
1. Does your solution use appropriate data types for the problem as described?

# How to build and run the web app (Ubuntu)

1. Install Maven - This is a build automation tool used for Java projects
   
    ```
    Sudo apt-get install maven
    ```
2. Install Postgresql - This is an open source database used to archive data for the application as requested 
    ```
    sudo apt-get install postgresql postgresql-contrib
    ```
3. Setup a password for Postgresql for the default user - This is a requirement before the application can use it
     ```
    sudo -u postgres psql
    ```
    ```
    ALTER USER postgres with encrypted password 'bA%&Y66HkT';
    ```
    *Please enter the password specified here as that is what the application expects to be able to to connect to the database. No other password will work. 
    
4. Install Tomcat and start it - this is a web server the application will be deployed to
    ```
    sudo apt-get install tomcat8
    ```
     ```
    systemctl start tomcat8
    ```
5. Install the Java Development Kit Package if not already present
    ```
    sudo apt-get install default-jdk
    ```

6. Go to the location where the git bundle was extracted and make sure you are in the root directory. Then enter the following build maven command in the terminal:

    ```
    mvn clean package
    ```

7. If the above command was successful, you should see a new "Target" folder in the directory. In there will be a file named payrollchallenge.war. This needs to be deployed to the web server. The easiest way is to move payrollchallenge.war to inside /var/lib/tomcat8/webapps. 

8. Start up Tomcat and go to http://localhost:8080/payrollchallenge-1.0
    This is a simple web page that allows you to test out the applications functionality easily. You can upload new valid .csv files from here as well as get the expected report. Note you can also just go to http://localhost:8080/payrollchallenge-1.0/GetReport? directly to retrieve it. 

# Followup Questions

Q. How did you test that your implementation was correct?
 
I added multiple unit tests to test the different components seperately. As part of the output test data, I used the sample expected JSON output provided in this README. Then I confirmed my returned JSON was the exact same given the same input data. 

Additionally, I did some manual testing for the entire process. This included adding several kinds of sample CSVs and ensuring the expected report could be retrieved. I did some manual calculations to ensure the report data was what it should have been. 

Q. If this application was destined for a production environment, what would you add or change?

In terms of scalability and performance which is very important in a production environment, this application could be improved. 

When generating the report, currently it gets all the pay roll entry data it needs from the database and then generates the report from scratch each time. Assuming countless users were invoking the web server at once and there was a large amount of report data coming in, this would quickly become a performance issue. To remedy this ideally some kind of caching mechanism for the report should exist. A simple solution would be to keep the object used to generate reports in memory and update it dynamically as new report entry data came in which should scale a lot better. This way on a request for the report, the application would have to do far less work on consecutive requests. 

Additionally, some kind of rate limiter for api requests could be implemented. Right now there is no restriction so users could request the report countless times if they wanted to in a short time span. To prevent api abuse and protect the application from bad potentially automated requests, it could restrict how many times per minute for instance, the same user can request or add data on a web server level. 

Error handling could also be improved. Right now the application expects completely correct data to come into the system. It expects a valid csv file with data in a particular format. But there is no guarantee all users will comply to these expectations. At the very least in a production environment, the APIs should return well explained errors if any of the data comes in a format it cannot handle or ideally, it should try to handle input data in more formats for flexibility and ease of use. 

In terms of features the application could be certainly expanded on as well. Right now it does not provide an api to modify or delete existing pay roll entries. Users might input incorrect data by mistake that later needs to be corrected. Additionally, the application returns a report for all time. But users might want only data returned for specific time periods, employees or job groups. Providing an api that takes in search parameters with these options would be ideal. Once it was better understood how users chose to typically use the application, database level indexes could be added as well for more efficient lookups. 

Q.What compromises did you have to make as a result of the time constraints of this challenge?

The performance and scalability concerns I mentioned in the previous section were some of the compromises I made due to time constraints.  

Additionally, I added some unit tests for the core functionality of the various components. But I could have added more unit tests and/or expanded the existing ones with more input data so potentially more corner cases were tested. A full integration test would be nice. Ideally it would be something like a selenium web test that spins up a browser instance, goes to the sample web page I provided and makes the api calls from there. This way every component of the application would be tested together just like how it would be used in a production environment.
