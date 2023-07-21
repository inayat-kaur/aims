package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.sql.Date;
import java.util.concurrent.Callable;

import myapp.db.AcadSecGateway;

@Command(name = "AddEvent")
public class AddEvent implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = { "-e", "--event" }, description = "Event to be added", interactive = true)
    String event;

    @Option(names = { "-sd", "--startDate" }, description = "Start date of event (yyyy-mm-dd)", interactive = true)
    String startDate;

    @Option(names = { "-ed", "--endDate" }, description = "End date of event (yyyy-mm-dd)", interactive = true)
    String endDate;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        if (!ag.checkEvent(event)) {
            ag.addEvent(event, java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        } else {
            System.out.println("Event already exists");
        }
        return 0;
    }
}
