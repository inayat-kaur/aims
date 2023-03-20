package myapp.cli.FacultyCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.sql.*;
import java.util.concurrent.Callable;

import myapp.db.FacultyGateway;

@Command(name = "viewGrades", 
         description = "View Grades")
public class ViewGrades implements Callable<Integer>{
    
    @Option (names = {"-u", "--user"}, description = "Username")
    String username;

    @Option (names = {"-c", "--course_id"}, description = "Course ID", interactive = true)
    String course_id;

    @Option(names = {"-s", "--sem"}, description = "Semester", interactive = true)
    int sem;

    @Option(names = {"-y", "--year"}, description = "Year", interactive = true)
    int year;

    public Integer call() throws Exception {
        FacultyGateway fg = new FacultyGateway(username);
        ResultSet res = fg.getGrades(course_id,year,sem);
            while(res.next()){
                System.out.println(res.getString("student_id") + " " + res.getString("grade"));
            }
        return 0;
    }
}