package myapp.cli.StudentCommands;

import java.sql.ResultSet;

import myapp.db.StudentGateway;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

@Command(name="viewRegisteredCourses")
public class ViewRegisteredCourses implements Callable<Integer>{

    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    public Integer call() throws Exception {
        StudentGateway sg = new StudentGateway(username);
        ResultSet res = sg.getRegisteredCourses();
            while(res.next()){
                System.out.println(res.getString("course_id"));
            }
        return 0;
    }
    
}
