package myapp.cli.AcadSecCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class AddRemoveCourseTest {
    
    private static ByteArrayOutputStream outContent;

    @BeforeAll
    public static void getConn(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void testAddRemovecourse() throws Exception{
        AddCourse addCourse = new AddCourse();
        addCourse.username = "acad@email";
        addCourse.course_id = "cs101";
        addCourse.dept = "cs";
        addCourse.ltpc = "3-1-0-4";
        addCourse.pre = "NaN";
        addCourse.call();
        assertTrue(outContent.toString().contains("You are not authorized to add a course"));
        addCourse.username = "dean@email";
        addCourse.call();
        assertTrue(outContent.toString().contains("Course already exists"));
        addCourse.course_id = "cs302";
        addCourse.call();
        assertTrue(outContent.toString().contains("Course added"));
        RemoveCourse removeCourse = new RemoveCourse();
        removeCourse.username = "acad@email";
        removeCourse.course_id = "cs302";
        removeCourse.call();
        assertTrue(outContent.toString().contains("You do not have the authority to perform this action"));
        removeCourse.username = "dean@email";
        removeCourse.call();
        assertTrue(outContent.toString().contains("Course removed"));
        removeCourse.course_id = "cs501";
        removeCourse.call();
        assertTrue(outContent.toString().contains("Course does not exist"));
    }

}
