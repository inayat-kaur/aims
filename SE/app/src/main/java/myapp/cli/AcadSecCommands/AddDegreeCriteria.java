package myapp.cli.AcadSecCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.util.concurrent.Callable;

import myapp.db.AcadSecGateway;

@Command(name = "add-degree-criteria", mixinStandardHelpOptions = true, description = "Add degree criteria")
public class AddDegreeCriteria implements Callable<Integer> {

    @Option(names = { "-u", "--username" }, required = true, description = "Username")
    String username;

    @Option(names = { "-d", "--dept" }, required = true, description = "Department", interactive = true)
    String dept;

    @Option(names = {"-p", "--program"}, required = true, description = "Program", interactive = true)
    String program;

    @Option(names = { "-pc", "--program-core" }, required = true, description = "p1,p2,p3...", interactive = true)
    String programCore;

    @Option(names = { "-pe",
            "--program-elective" }, required = true, description = "Minimum credits for program elective", interactive = true)
    float programElective;

    @Option(names = {"-hs", "--humanities-elective"}, required = true, description = "Minimum credits for humanities and social elective", interactive = true)
    float humanitiesElective;

    @Option(names = {"-sc", "--science-elective"}, required = true, description = "Minimum credits for science elective", interactive = true)
    float scienceElective;

    @Option(names = {"-oe", "--open-elective"}, required = true, description = "Minimum credits for open elective", interactive = true)
    float openElective;


    @Option(names = { "-btp", "--btp" }, required = true, description = "btp1,btp2,...", interactive = true)
    String btp;

    public Integer call() throws Exception {
        AcadSecGateway ag = new AcadSecGateway(username);
        String[] PC = programCore.split(",");
        String[] BTP = btp.split(",");
        ag.addDegreeCriteria(dept,program, PC, programElective, humanitiesElective, scienceElective, openElective, BTP);
        return 0;
    }

}
