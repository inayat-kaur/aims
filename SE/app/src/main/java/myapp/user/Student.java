package myapp.user;

import myapp.cli.StudentCommands.StudentCommand;
import myapp.db.StudentGateway;
import picocli.CommandLine;

public class Student extends User{

    private String name;

    public Student(String username, String password) throws Exception {
        //On creation of student, it will be prompted to give a command
        this.username = username;
        this.password = password;
        StudentGateway sg = new StudentGateway(username);
        this.name = sg.getName();
        giveCommand();
    }

    private void giveCommand(){
        //Infinite loop runs untill student logs out
        do {
            System.out.println("Welcome " + name);
            if(new CommandLine(new StudentCommand()).execute("-u", username, "-sc")==0){
                break;
            }
        } while (true);
    }

}