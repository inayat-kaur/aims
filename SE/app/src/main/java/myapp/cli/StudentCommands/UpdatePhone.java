package myapp.cli.StudentCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.StudentGateway;

@Command(name="updatePhone")
public class UpdatePhone implements Callable<Integer>{
    
    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Option(names = {"-p", "--phone"}, description = "New phone number",interactive = true)
    String phone;

    public Integer call() throws Exception{
        StudentGateway sg = new StudentGateway(username);
        sg.updatePhone(phone);
        return 0;
    }
    
}

