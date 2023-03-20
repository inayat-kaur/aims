package myapp.user;

import myapp.cli.AcadSecCommands.AcadSecCommand;
import picocli.CommandLine;


public class AcadSec extends User{

    public AcadSec(String username,String password) {
        //On creation of acadSec, it will be prompted to give a command
        this.username = username;
        this.password = password;
        giveCommand();
    }

    private void giveCommand(){
        //Infinite loop runs untill acad sec logs out
        do {
            System.out.println("Welcome " + username);
            if(new CommandLine(new AcadSecCommand()).execute("-u", username, "-ac")==0){
                break;
            }
        } while (true);
    }

}
