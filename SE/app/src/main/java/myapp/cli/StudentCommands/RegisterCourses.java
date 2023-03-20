package myapp.cli.StudentCommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import myapp.db.StudentGateway;

import java.util.concurrent.Callable;

@Command(name="registerCourses")
public class RegisterCourses implements Callable<Integer>{
    
    @Option(names = {"-u", "--user"}, description = "Username")
    String username;

    @Option(names = {"-c", "--course"}, description = "Course ID",interactive = true)
    String course_id;

    public Integer call() throws Exception{
        StudentGateway sg = new StudentGateway(username);
        if(!sg.checkCourseOfferThisSem(course_id)){
            System.out.println("Course not offered this semester");
            return 0;
        }
        if(sg.checkCourseRegistrationThisSem(course_id)){
            System.out.println("Course already registered");
            return 0;
        }
        if(!sg.checkEvent("registration")){
            System.out.println("Registration not allowed");
            return 0;
        }
        if(sg.checkIfCourseCompleted(course_id)){
            System.out.println("Course already completed");
            return 0;
        }
        if(!sg.checkPrereq(course_id)){
            System.out.println("Prerequisites not met");
            return 0;
        }
        if(sg.getMinCGPAForCourse(course_id) > sg.getCGPA()){
            System.out.println("Minimum CGPA not met");
            return 0;
        }
        if(sg.getStudentSem()>2){
            float creditsThisSem = sg.getCreditsRegisteredSemX(sg.getCurrentYear(),sg.getCurrentSem());
            float avgCreditForPrevTwoSem;
            if(sg.getCurrentSem() == 2){
                avgCreditForPrevTwoSem = (sg.getCreditsEarnedSemX(sg.getCurrentYear(),1) + sg.getCreditsEarnedSemX(sg.getCurrentYear()-1,2))/2;
            }else{
                avgCreditForPrevTwoSem = (sg.getCreditsRegisteredSemX(sg.getCurrentYear()-1,1) + sg.getCreditsRegisteredSemX(sg.getCurrentYear()-1,2))/2;
            }
            if(creditsThisSem + sg.getCreditsForCourse(course_id) > 1.25 * avgCreditForPrevTwoSem){
                System.out.println("Maximum credits for this semester exceeded");
                return 0;
            }
        }else{
            if(sg.getCreditsRegisteredSemX(sg.getCurrentYear(),sg.getCurrentSem()) + sg.getCreditsForCourse(course_id) > 20){
                System.out.println("Maximum credits for this semester exceeded");
                return 0;
            }
        }
        sg.registerCourse(course_id);
        return 0;
    }
    
}
