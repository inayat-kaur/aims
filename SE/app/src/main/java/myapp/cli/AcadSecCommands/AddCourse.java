package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.AcadSecGateway;

@Command(name = "AddCourse")
public class AddCourse implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-c", "--course" }, description = "Course to be added", interactive = true)
    String course_id;

    @Option(names = { "-d", "--dept" }, description = "Department of the course", interactive = true)
    String dept;

    @Option(names = { "-ltpc", "--ltpc" }, description = "L-T-P-C", interactive = true)
    String ltpc;

    @Option(names = { "-pre",
            "--prerequisite" }, description = "NaN or p1,p2,p3...,pk|p1,p2,p4,...,pk|...", required = false, interactive = true)
    String pre;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        if (!ag.checkAuthority()) {
            System.out.println("You are not authorized to add a course");
        } else {
            if (ag.checkCourse(course_id)) {
                System.out.println("Course already exists");
            } else {
                ag.addCourse(course_id, dept, ltpc, pre);
            }
        }
        return 0;
    }
}
