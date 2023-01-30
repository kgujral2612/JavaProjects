package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class Project2Test {

    String[] invalidArgs = {"My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] lessArgs = {"1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] moreArgs = {"Scam Airways", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] validArgsAllOp = {"-print", "-textFile", "filepath", "-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] validArgsPrintOp = {"-print", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] validArgsReadmeOp = {"-README", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] validArgsFileOp = {"-textFile", "filepath", "My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};
    String[] validArgsNoOp = {"My Awesome Airways", "1234", "PDX", "3/15/2023", "1:03", "SFO", "3/15/2023", "3:33"};

    /**
     * the readMe() method should read README.txt
     * as a project resource and return the content
     * in form of a string
     * */
    @Test
    void readMeShouldReturnContentOfReadMeFile(){
        try{
            var content = Project2.readMe();
            assertThat(content, containsString("CS501 Project2: : Storing An Airline in a Text File"));
            assertThat(content, containsString("- Kaushambi Gujral"));
        }
        catch(IOException e){
            var expected = "";
            assertThat(expected, containsString("Readme could not be read!"));
        }
    }

    @Test
    void getArgumentIndexWithOps(){
        int result = Project2.getArgumentIndex(validArgsAllOp);
        assertThat(result, is(not(0)));
        assertThat(result, is(4));
    }

    @Test
    void getArgumentIndexWithoutOps(){
        int result = Project2.getArgumentIndex(validArgsNoOp);
        assertThat(result, is(0));
    }
    @Test
    void parseArgumentsWithLessArgs(){

    }

    @Test
    void parseArgumentsWithMoreArgs(){

    }

    @Test
    void parseArgumentsWithInvalidArgs(){

    }

    @Test
    void parseArgumentsWithValidArgs(){}

    @Test
    void parseArgumentsWithPrint(){}

    @Test
    void parseArgumentsWithFile(){}

    @Test
    void parseArgumentsWithReadme(){}

    @Test
    void parseArgumentsWithReadmeAndFileOptions(){}


}
