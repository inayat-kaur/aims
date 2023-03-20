package myapp.cli;

import picocli.CommandLine.Option;
import picocli.CommandLine.Command;
import java.util.concurrent.Callable;

import myapp.db.LoginGateway;
import myapp.user.Student;
import myapp.user.Faculty;
import myapp.user.AcadSec;

@Command(name = "login", 
         description = "Login to the system")
public class LoginCommand implements Callable<Integer> {
    @Option(names = {"-u", "--user"}, description = "Username",interactive = true)
    String username;

    @Option(names = {"-p", "--password"}, description = "Passphrase",interactive = true)
    String password;

    //This command authorizes user as a student, faculty or acadSection
    public Integer call() throws Exception {
        LoginGateway lg = new LoginGateway();
        boolean isloggedin = lg.login(username, password);
        if(isloggedin==false){
            System.out.println("Invalid username or password");
            return 0;
        }
        if(lg.getRole()==0||lg.getRole()==1){
            AcadSec acadSec = new AcadSec(username,password);
            acadSec = null;
            lg.logout();
        }else if(lg.getRole()==2){
            Faculty faculty = new Faculty(username,password);
            faculty = null;
            lg.logout();
        }else if(lg.getRole()==3){
            Student student = new Student(username,password);
            student = null;
            lg.logout();
        }
        return 0;
    }

}
