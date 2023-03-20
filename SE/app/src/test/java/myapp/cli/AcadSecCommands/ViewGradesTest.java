package myapp.cli.AcadSecCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ViewGradesTest {
    
    @Test
    public void testViewGrades() throws Exception{
        ViewGrades vg = new ViewGrades();
        vg.username = "acad@email";
        vg.student_id = "s1cs@email";
        assertTrue(vg.call()==0);
    }
}
