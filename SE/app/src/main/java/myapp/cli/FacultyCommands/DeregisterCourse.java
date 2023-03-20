package myapp.cli.FacultyCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.FacultyGateway;

@Command(name = "deregisterCourse", 
         description = "Deregister Course")
public class DeregisterCourse implements Callable<Integer>{
    
    @Option (names = {"-u", "--username"}, required = true, description = "Username", interactive = false)
    String username;

    @Option(names = {"-c", "--course_id"}, required = true, description = "Course ID", interactive = true)
    String course_id;

    public Integer call() throws Exception {
        FacultyGateway fg = new FacultyGateway(username);
        if(!fg.checkIfOffered(course_id)){
            System.out.println("Course not offered");
        }
        else if(!fg.checkEvent("takeback_course")){
            System.out.println("Cannot deregister course now");
        }
        fg.deregisterStudents(course_id);
        fg.deregisterCourse(course_id);
        return 0;
    }
}
