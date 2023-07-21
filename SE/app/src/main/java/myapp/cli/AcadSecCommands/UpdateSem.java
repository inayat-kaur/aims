package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.sql.Date;
import java.util.concurrent.Callable;

import myapp.db.AcadSecGateway;

@Command(name = "UpdateSem")
public class UpdateSem implements Callable<Integer> {

    @Option(names = { "-u", "--user" }, description = "Username")
    String username;

    @Option(names = {"-sd", "--startDate"}, description = "Start Date", required = true, interactive = true)
    String startDate;

    @Option(names = {"-ed", "--endDate"}, description = "End Date", required = true, interactive = true)
    String endDate;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        ag.updateSem(java.sql.Date.valueOf(startDate), java.sql.Date.valueOf(endDate));
        return 0;
    }
}
