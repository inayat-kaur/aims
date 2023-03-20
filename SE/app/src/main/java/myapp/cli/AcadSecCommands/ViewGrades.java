package myapp.cli.AcadSecCommands;

import myapp.db.AcadSecGateway;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.sql.*;
import java.util.concurrent.Callable;

@Command(name = "viewGrades")
public class ViewGrades implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-s", "--student" }, description = "Student ID", interactive = true)
    String student_id;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        ResultSet res = ag.getGrades(student_id);
        while (res.next()) {
            System.out.println(res.getString("course_id") + " " + res.getString("grade"));
        }
        return 0;
    }
}
