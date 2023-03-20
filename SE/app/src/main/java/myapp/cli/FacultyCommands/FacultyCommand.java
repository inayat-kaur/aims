package myapp.cli.FacultyCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@Command(name = "FacultyCommand")
public class FacultyCommand implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-fc",
            "--facultyCommand" }, description = "\nFaculty Commands:\n1 to register course\n2 to deregister from course\n3 to view grades of students in a course\n4 to update grades\n5 to update phone no.\n6 to update address\n7 to logout", interactive = true)
    int facultyCommand;

    public Integer call() throws Exception {
        if (facultyCommand == 1) {
            if (new CommandLine(new RegisterCourse()).execute("-u", username, "-c", "-cg") != 0) {
                System.out.println("Error in registering course");
            }
        } else if (facultyCommand == 2) {
            if (new CommandLine(new DeregisterCourse()).execute("-u", username, "-c") != 0) {
                System.out.println("Error in deregistering course");
            }
        } else if (facultyCommand == 3) {
            if (new CommandLine(new ViewGrades()).execute("-u", username, "-c", "-s", "-y") != 0) {
                System.out.println("Error in viewing grades");
            }
        } else if (facultyCommand == 4) {
            if (new CommandLine(new UpdateGrades()).execute("-u", username, "-c", "-f") != 0) {
                System.out.println("Error in updating grades");
            }
        } else if (facultyCommand == 5) {
            if (new CommandLine(new UpdatePhone()).execute("-u", username, "-p") != 0) {
                System.out.println("Error in updating phone no.");
            }
        } else if (facultyCommand == 6) {
            if (new CommandLine(new UpdateAddress()).execute("-u", username, "-a") != 0) {
                System.out.println("Error in updating address");
            }
        } else if (facultyCommand == 7) {
            return 0;
        } else {
            System.out.println("Invalid facultyCommand");
        }
        return 1;
    }
}