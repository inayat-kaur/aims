package myapp.cli.FacultyCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.FacultyGateway;

@Command(name="UpdateAddress")
public class UpdateAddress implements Callable<Integer>{
    
    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Option(names = {"-a", "--address"}, description = "New address", interactive = true)
    String address;

    public Integer call() throws Exception {
        FacultyGateway fg = new FacultyGateway(username);
        fg.updateAddress(address);
        return 0;
    }
}
