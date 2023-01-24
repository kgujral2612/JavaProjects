package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * A unit test for code in the <code>Project1</code> class.  This is different
 * from <code>Project1IT</code> which is an integration test (and can capture data
 * written to {@link System#out} and the like.
 */
class Project1Test {
  String[] validArgs = {"AirLine", "1234", "PDX", "16:00", "SFO", "19:00", "-print", "-README"};
  String[] invalidArgs = {null, null, null, null, null, null, "-someotheroption1", "-someotheroption2" };
  @Test
  void readmeCanBeReadAsResource() throws IOException {
    try (
      InputStream readme = Project1.class.getResourceAsStream("README.txt")
    ) {
      assertThat(readme, not(nullValue()));
      BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
      String line = reader.readLine();
      assertThat(line, containsString("This is a README file!"));
    }
  }

  /*
  * initArgMap() method should initialize
  * a hashmap of arguments called argMap with
  * a default value of "" (empty string)
  */
  @Test
  void initArgMapShouldPopulateMapWithDefaultValues(){
    Project1.initArgMap();
    assertThat(Project1.argMap, is(not(nullValue())));
    assertThat(Project1.argMap.size(), is(8));
    assertThat(Project1.argMap.get("airline"), is(nullValue()));
    assertThat(Project1.argMap.get("flightNumber"), is(nullValue()));
    assertThat(Project1.argMap.get("src"), is(nullValue()));
    assertThat(Project1.argMap.get("depart"), is(nullValue()));
    assertThat(Project1.argMap.get("dest"), is(nullValue()));
    assertThat(Project1.argMap.get("print"), is(nullValue()));
    assertThat(Project1.argMap.get("README"), is(nullValue()));
  }

  /*
  * Given user inputs inform of arguments, parseArguments() method
  * should separate out options and data arguments
  * and should populate argMap with those values
  */
  @Test
  void parseArgumentsShouldCreateADictionaryOfArguments(){
    Project1.initArgMap();
    Project1.parseArguments(validArgs);
    assertThat(Project1.argMap.get("print"), is(not(nullValue())));
    assertThat(Project1.argMap.get("README"), is(not(nullValue())));
    assertThat(Project1.argMap.get("airline"), is(validArgs[0]));
    assertThat(Project1.argMap.get("flightNumber"), is(validArgs[1]));
    assertThat(Project1.argMap.get("src"), is(validArgs[2]));
    assertThat(Project1.argMap.get("depart"), is(validArgs[3]));
    assertThat(Project1.argMap.get("dest"), is(validArgs[4]));
    assertThat(Project1.argMap.get("arrive"), is(validArgs[5]));
  }

  /*
  * When the arguments contain null/invalid values
  * parseArgument() should not populate it in the
  * argMap.
  */
  @Test
  void parseArgumentsShouldWorkIfArgumentsAreInvalid(){
    Project1.initArgMap();
    Project1.parseArguments(invalidArgs);
    assertThat(Project1.argMap.get("print"), is(nullValue()));
    assertThat(Project1.argMap.get("README"), is(nullValue()));
    assertThat(Project1.argMap.get("airline"), is(nullValue()));
    assertThat(Project1.argMap.get("flightNumber"), is(nullValue()));
    assertThat(Project1.argMap.get("src"), is(nullValue()));
    assertThat(Project1.argMap.get("depart"), is(nullValue()));
    assertThat(Project1.argMap.get("dest"), is(nullValue()));
    assertThat(Project1.argMap.get("arrive"), is(nullValue()));
  }

  /*
   * If the airline name is not provided
   * createdAirline() method should return
   * a null object
   */
  @Test
  void createAirlineShouldReturnNullIfAirlineNameIsNull(){
    //Arrange
    Project1.parseArguments(invalidArgs);

    //Act
    var airline = Project1.createAirline();

    //Assert
    assertThat(airline, is(nullValue()));
  }


  /*
   * If the airline name is provided
   * createdAirline() method should return
   * an airline object
   */
  @Test
  void createAirlineShouldReturnAirlineWithValidAirlineName(){
    //Arrange
    Project1.parseArguments(validArgs);

    //Act
    var airline = Project1.createAirline();

    //Assert
    assertThat(airline, is(not(nullValue())));
    assertThat(airline.getName(),equalTo(validArgs[0]) );
  }

  @Test
  void readFileShouldReturnFileContent(){
    //Arrange
    String path = "/Users/kaushambigujral/Desktop/git/PSUWinter23KG/airline/src/main/resources/edu/pdx/cs410J/kgujral/README.txt";

    //Act
    var content = Project1.readFile(path);

    //Assert
    assertThat(content, is(not(nullValue())));
    assertThat(content, is(not(equalTo(""))));
  }

  @Test
  void readFileShouldReturnEmptyStringIfFileIsNotFound(){
    //Arrange
    String path = "/WrongPath.txt";

    //Act
    var content = Project1.readFile(path);

    //Assert
    assertThat(content, is(equalTo("")));
  }
}
