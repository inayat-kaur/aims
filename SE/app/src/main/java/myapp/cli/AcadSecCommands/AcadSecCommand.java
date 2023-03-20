package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine;
import java.util.concurrent.Callable;

@Command(name = "AcadSecCommand")
public class AcadSecCommand implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-ac",
            "--acadSecCommand" }, description = "\nAcademic Section Commands:\n1 to add an event\n2 to remove an event\n3 to update the semester\n4 to add a course to catalog (By Dean SA only)\n5 to remove a course from catalog (By Dean SA only)\n6 to view grades of a student\n7 to generate transcript of a student\n8 to add degree criteria\n9 to logout", interactive = true)
    int acadSecCommand;

    public Integer call() throws Exception {
        if (acadSecCommand == 1) {
            if (new CommandLine(new AddEvent()).execute("-u", username, "-e","-sd","-ed") != 0) {
                System.out.println("Error in adding event");
            }
        } else if (acadSecCommand == 2) {
            if (new CommandLine(new RemoveEvent()).execute("-u", username, "-e") != 0) {
                System.out.println("Error in removing event");
            }
        } else if (acadSecCommand == 3) {
            if (new CommandLine(new UpdateSem()).execute("-u", username,"-sd","-ed") != 0) {
                System.out.println("Error in updating semester");
            }
        } else if (acadSecCommand == 4) {
            if (new CommandLine(new AddCourse()).execute("-u", username, "-c", "-d", "-ltpc", "-pre") != 0) {
                System.out.println("Error in adding course");
            }
        } else if (acadSecCommand == 5) {
            if (new CommandLine(new RemoveCourse()).execute("-u", username, "-c") != 0) {
                System.out.println("Error in removing course");
            }
        } else if (acadSecCommand == 6) {
            if (new CommandLine(new ViewGrades()).execute("-u", username, "-s") != 0) {
                System.out.println("Error in viewing grades");
            }
        } else if (acadSecCommand == 7) {
            if (new CommandLine(new GenerateTranscript()).execute("-u", username, "-s", "-t") != 0) {
                System.out.println("Error in generating transcript");
            }
        } else if (acadSecCommand == 8) {
            if (new CommandLine(new AddDegreeCriteria()).execute("-u", username, "-d","-p", "-pc", "-pe", "-hs","-sc","-oe","-btp") != 0) {
                System.out.println("Error in adding degree criteria");
            }
        } else if (acadSecCommand == 9) {
            return 0;
        } else {
            System.out.println("Invalid Academic Section Command");
        }
        return 1;
    }
}
