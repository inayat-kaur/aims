package myapp.cli.AcadSecCommands;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.Test;

public class GenerateTranscriptTest {

    @Test
    public void testGenerateTranscript() throws Exception{
        GenerateTranscript gt = new GenerateTranscript();
        gt.username = "acad@email";
        gt.student_id = "s1cs@email";
        gt.type = "cumulative";
        gt.call();
        File file = new File("s1cs@email_transcript.txt");
        assertTrue(file.exists());
        gt.type = "1";
        gt.call();
        File file2 = new File("s1cs@email_semester1_transcript.txt");
        assertTrue(file2.exists());
    }

}
