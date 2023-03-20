package myapp.cli.FacultyCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import myapp.db.FacultyGateway;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;

@Command(name = "updateGrades", description = "Update Grades")
public class UpdateGrades implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-c", "--course" }, description = "Course ID", interactive = true)
    String course_id;

    @Option(names = { "-f", "--file" }, description = "File containing grades", interactive = true)
    String fileloc;

    public Integer call() throws Exception {
        FacultyGateway fg = new FacultyGateway(username);
        if (!fg.checkIfOffered(course_id)) {
            System.out.println("Course not offered");
            return 0;
        } else if (!fg.checkEvent("grading")) {
            System.out.println("Cannot update grades now");
            return 0;
        }
        Scanner sc;
        File file = new File(fileloc);
        sc = new Scanner(file);
        sc.useDelimiter("[,\n\\s]+");
        ArrayList<String> student_id = new ArrayList<String>();
        ArrayList<Integer> grades = new ArrayList<Integer>();
        while (sc.hasNext()) {
            student_id.add(sc.next());
            grades.add(Integer.parseInt(sc.next()));
        }
        fg.updateGrades(student_id, grades, course_id);
        file = null;
        sc.close();
        return 0;
    }
}
