package edu.pdx.cs410J.kgujral;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/** Tests for PrettyHelper */
public class PrettyHelperTest {
    @Test
    void shouldPrettify(){
        Airline airline = new Airline("Sample Airways");
        Flight f1 = new Flight(1, "CHA", "SFO", DateHelper.stringToDate("3/3/2020 05:40 pm"), DateHelper.stringToDate("3/3/2020 06:40 pm"));
        Flight f2 = new Flight(2, "BNA", "SFO", DateHelper.stringToDate("3/3/2020 05:40 am"), DateHelper.stringToDate("3/3/2020 09:40 am"));
        airline.addFlight(f1);
        airline.addFlight(f2);
        var prettified = PrettyHelper.prettify(airline);
        assertThat(prettified, containsString("Sample Airways"));
        assertThat(prettified, containsString("Flight # 2\n" +
                "From Nashville, TN To San Francisco, CA\n" +
                "Departs at: Mar 3, 2020, 5:40:00 AM\n" +
                "Arrives at: Mar 3, 2020, 9:40:00 AM\n" +
                "Total Duration of Flight: 240 minutes."));
        assertThat(prettified, containsString("Flight # 1\n" +
                "From Chattanooga, TN To San Francisco, CA\n" +
                "Departs at: Mar 3, 2020, 5:40:00 PM\n" +
                "Arrives at: Mar 3, 2020, 6:40:00 PM\n" +
                "Total Duration of Flight: 60 minutes."));
    }
}
