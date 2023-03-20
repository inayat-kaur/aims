package myapp.cli.StudentCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@Command(name = "StudentCommand")
public class StudentCommand implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-sc",
            "--studentCommand" }, description = "\nStudent Commands:\n1 to register for courses\n2 to deregister from course\n3 to view registered courses\n4 to view grades\n5 to getCGPA\n6 to get graduation status\n7 to update phone no.\n8 to update address\n9 to logout\n", interactive = true)
    int studentCommand;

    public Integer call() throws Exception {
        if (studentCommand == 1) {
            if (new CommandLine(new RegisterCourses()).execute("-u", username, "-c") != 0) {
                System.out.println("Error registering courses");
            }
        } else if (studentCommand == 2) {
            if (new CommandLine(new DeregisterCourse()).execute("-u", username, "-c") != 0) {
                System.out.println("Error deregistering courses");
            }
        } else if (studentCommand == 3) {
            if (new CommandLine(new ViewRegisteredCourses()).execute("-u", username) != 0) {
                System.out.println("Error viewing registered courses");
            }
        } else if (studentCommand == 4) {
            if (new CommandLine(new ViewGrades()).execute("-u", username) != 0) {
                System.out.println("Error viewing grades");
            }
        } else if (studentCommand == 5) {
            if (new CommandLine(new GetCGPA()).execute("-u", username) != 0) {
                System.out.println("Error getting CGPA");
            }
        } else if (studentCommand == 6) {
            if (new CommandLine(new GetGraduationStatus()).execute("-u", username) != 0) {
                System.out.println("Error getting graduation status");
            }
        } else if (studentCommand == 7) {
            if (new CommandLine(new UpdatePhone()).execute("-u", username, "-p") != 0) {
                System.out.println("Error updating phone no.");
            }
        } else if (studentCommand == 8) {
            if (new CommandLine(new UpdateAddress()).execute("-u", username, "-a") != 0) {
                System.out.println("Error updating address");
            }
        } else if (studentCommand == 9) {
            return 0;
        } else {
            System.out.println("Invalid studentCommand");
        }
        return 1;
    }
}
