package myapp.cli.StudentCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.StudentGateway;

@Command(name="getCGPA")
public class GetCGPA implements Callable<Integer>{
    
    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    public Integer call() throws Exception {
        StudentGateway sg = new StudentGateway(username);
        System.out.println("CGPA: " + sg.getCGPA());
        return 0;
    }
}
