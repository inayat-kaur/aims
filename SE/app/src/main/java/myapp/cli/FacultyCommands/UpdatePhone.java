package myapp.cli.FacultyCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.FacultyGateway;

@Command(name="UpdatePhone")
public class UpdatePhone implements Callable <Integer>{
    
    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Option(names = {"-p", "--phone"}, description = "New phone number", interactive = true)
    String phone;

    public Integer call() throws Exception{
        FacultyGateway fg = new FacultyGateway(username);
        fg.updatePhone(phone);
        return 0;
    }
}
