package myapp.cli.StudentCommands;

import myapp.db.StudentGateway;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.sql.*;
import java.util.concurrent.Callable;


@Command(name="viewGrades")
public class ViewGrades implements Callable<Integer>{

    @Option(names = {"-u", "--user"}, description = "Username")
    String username;
    
    public Integer call() throws Exception {
        StudentGateway sg = new StudentGateway(username);
        ResultSet res = sg.getGrades();
            while(res.next()){
                System.out.println(res.getString("course_id") + " " + res.getString("grade"));
    }
    return 0;
}

}
