package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.AcadSecGateway;

@Command(name = "RemoveCourse")
public class RemoveCourse implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-c", "--course" }, description = "Course ID", interactive = true)
    String course_id;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        if (ag.checkAuthority()) {
            if (ag.checkCourse(course_id))
                ag.removeCourse(course_id);
            else
                System.out.println("Course does not exist");
        } else
            System.out.println("You do not have the authority to perform this action");
        return 0;
    }
}
