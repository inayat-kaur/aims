package myapp.cli.StudentCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.StudentGateway;

@Command(name="updateAddress")
public class UpdateAddress implements Callable<Integer>{
    
    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Option(names = {"-a", "--address"}, description = "New address",interactive = true)
    String address;

    public Integer call() throws Exception{
        StudentGateway sg = new StudentGateway(username);
        sg.updateAddress(address);
        return 0;
    }
    
}


