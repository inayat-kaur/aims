package myapp.cli.StudentCommands;

import java.util.concurrent.Callable;

import myapp.db.StudentGateway;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name="getGraduationStatus")
public class GetGraduationStatus implements Callable<Integer>{

    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Override
    public Integer call() throws Exception {
        StudentGateway sg = new StudentGateway(username);
        String[] resPC = sg.getIncompleteProgramCore();
        float pendingCreditsPE = sg.getRemainingPE();
        float pendingCreditsHS = sg.getRemainingHS();
        float pendingCreditsSC = sg.getRemainingSC();
        float pendingCreditsOE = sg.getRemainingOE();
        int pending =0;
        if(!sg.checkIfBTPCompleted()){
            System.out.println("BTP not completed");
            pending++;
        }
        if(resPC != null){
            System.out.println("Program core courses not completed");
            for(String s: resPC){
                System.out.println(s);
            }
            pending++;
        }
        if(pendingCreditsPE > 0){
            System.out.println("Program elective credits not completed");
            System.out.println("Remaining credits: " + pendingCreditsPE);
            pending++;
        }
        if(pendingCreditsHS > 0){
            System.out.println("Humanities and social science credits not completed");
            System.out.println("Remaining credits: " + pendingCreditsHS);
            pending++;
        }
        if(pendingCreditsSC > 0){
            System.out.println("Science credits not completed");
            System.out.println("Remaining credits: " + pendingCreditsSC);
            pending++;
        }
        if(pendingCreditsOE > 0){
            System.out.println("Open elective credits not completed");
            System.out.println("Remaining credits: " + pendingCreditsOE);
            pending++;
        }
        if(pending==0)System.out.println("Graduation requirements completed");   
        return 0;
    }
    
}

