package myapp.user;

import myapp.cli.LoginCommand;
import picocli.*;

public class User {
    protected String username = "";
    protected String password = "";

    public boolean login() {
        //The method prompts login using login commandline
        new CommandLine(new LoginCommand()).execute("-u", "-p");
        return true;
    }
}
