<html>
    <body>
        <H1>Wave SE-Payroll-Challenge</H1>
        <form action="AddPayrollEntries" method="post" enctype="multipart/form-data">
            <input type="file" name="file"/><br/>
            <input type="submit" value="upload"/>
        </form>
        <form action="GetReport" method="get">
            <input type="submit" value="Get Report"/>
        </form>
</body>
</html>