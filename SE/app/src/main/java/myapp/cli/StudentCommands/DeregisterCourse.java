package myapp.cli.StudentCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.StudentGateway;

@Command(name = "deregisterCourse")
public class DeregisterCourse implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-c", "--course" }, description = "Course ID", interactive = true)
    String course_id;

    public Integer call() throws Exception {
        StudentGateway sg = new StudentGateway(username);
        if (!sg.checkCourseRegistrationThisSem(course_id)) {
            System.out.println("You are not registered for this course in this semester");
            return 0;
        } else if (!sg.checkEvent("deregister")) {
            System.out.println("You cannot deregister from this course now");
            return 0;
        }
        sg.deregisterCourse(course_id);
        return 0;
    }
}
