package myapp.cli.FacultyCommands;

import java.util.concurrent.Callable;

import myapp.db.FacultyGateway;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "registerCourse", 
         description = "Register Course")
public class RegisterCourse implements Callable<Integer>{
    
    @Option (names = {"-u", "--username"}, required = true, description = "Username", interactive = false)
    String username;

    @Option(names = {"-c", "--course_id"}, required = true, description = "Course ID", interactive = true)
    String course_id;

    @Option(names = {"-cg", "--cg_constraint"}, required = true, description = "CG Constraint", interactive = true)
    float cg_constraint;

    public Integer call() throws Exception {
        FacultyGateway fg = new FacultyGateway(username);
        if(fg.checkIfOffered(course_id)){
            System.out.println("Course already offered");
        }
        else if(!fg.checkEvent("offering")){
            System.out.println("Cannot offer course now");
        }
        else{
            fg.registerCourse(course_id, cg_constraint);
        }
        return 0;
    }
}
