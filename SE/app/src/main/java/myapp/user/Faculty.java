package myapp.user;

import java.sql.SQLException;

import myapp.cli.FacultyCommands.FacultyCommand;
import myapp.db.FacultyGateway;
import picocli.CommandLine;

public class Faculty extends User{

    private String name;

    public Faculty(String username,String password) throws SQLException{
        //On creation of faculty, it will be prompted to give a command
        this.username = username;
        this.password = password;
        FacultyGateway fg = new FacultyGateway(username);
        this.name = fg.getName();
        giveCommand();
    }

    private void giveCommand(){
        //Infinite loop runs untill faculty logs out
        do {
            System.out.println("Welcome " + name);
            if(new CommandLine(new FacultyCommand()).execute("-u", username, "-fc")==0){
                break;
            }
        } while (true);
    }

}
