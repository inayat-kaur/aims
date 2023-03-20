package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import java.util.concurrent.Callable;
import myapp.db.AcadSecGateway;

@Command(name = "RemoveEvent")
public class RemoveEvent implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-e", "--event" }, description = "Event to be removed", interactive = true)
    String event;

    public Integer call() throws Exception {
        AcadSecGateway acadSecGateway = new AcadSecGateway(username);
        if (acadSecGateway.checkEvent(event)) {
            acadSecGateway.removeEvent(event);
        } else {
            System.out.println("Event does not exist");
        }
        return 0;
    }
}
