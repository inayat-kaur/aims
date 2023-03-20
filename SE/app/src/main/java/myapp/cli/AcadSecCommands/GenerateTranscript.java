package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.concurrent.Callable;

import myapp.db.AcadSecGateway;

@Command(name = "GenerateTranscript")
public class GenerateTranscript implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-s", "--student" }, description = "Student ID", interactive = true)
    String student_id;

    @Option(names = { "-t",
            "--type" }, description = "Transcript Type - cumulative or semester no.", interactive = true)
    String type;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        if (type.equals("cumulative")) {
            ResultSet res = ag.getGrades(student_id);
            String filename = student_id + "_transcript.txt";
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            out.println("Student ID: " + student_id);
            while (res.next()) {
                out.println("Course ID: " + res.getString("course_id") + " Year: " + res.getInt("year")
                        + " Semester: " + res.getInt("sem") + " Grade: " + res.getString("grade") + " Credits: "
                        + res.getFloat("credits"));
            }
            out.close();
        } else {
            ResultSet res = ag.getSemGrades(student_id, Integer.parseInt(type));
            String filename = student_id + "_semester" + type + "_transcript.txt";
            PrintWriter out = new PrintWriter(new FileWriter(filename));
            out.println("Student ID: " + student_id);
            while (res.next()) {
                out.println("Course ID: " + res.getString("course_id") + " Year: " + res.getInt("year")
                        + " Semester: " + res.getInt("sem") + " Grade: " + res.getString("grade") + " Credits: "
                        + res.getFloat("credits"));
            }
            out.close();
        }
        return 0;
    }

}
