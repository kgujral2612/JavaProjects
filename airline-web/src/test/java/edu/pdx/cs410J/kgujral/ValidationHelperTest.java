package edu.pdx.cs410J.kgujral;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A unit test for the {@link ValidationHelper}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class ValidationHelperTest {
    /**Airline name must not be null or empty*/
    @Test
    void shouldValidateAirlineName(){
        assertFalse(ValidationHelper.isValidAirlineName(null));
        assertFalse(ValidationHelper.isValidAirlineName(""));
        assertFalse(ValidationHelper.isValidAirlineName(" "));
        assertFalse(ValidationHelper.isValidAirlineName("      "));
        assertTrue(ValidationHelper.isValidAirlineName("valid1name"));
        assertTrue(ValidationHelper.isValidAirlineName("^^^^^^^"));
    }
    /**Flight number must be a valid whole number*/
    @Test
    void shouldValidateFlightNumber(){
        boolean result = ValidationHelper.isValidFlightNumber(null);
        assertThat(result, is(false));
        result = ValidationHelper.isValidFlightNumber("1234a");
        assertThat(result, is(false));
        result = ValidationHelper.isValidFlightNumber("1234.345");
        assertThat(result, is(false));
        result = ValidationHelper.isValidFlightNumber("-1234");
        assertThat(result, is(false));
        result = ValidationHelper.isValidFlightNumber("1234");
        assertThat(result, is(true));
    }

    /**Airport code must be a valid 3-letter word*/
    @Test
    void shouldValidateAirportCode(){
        assertFalse(ValidationHelper.isValidAirportCode(null));
        assertFalse(ValidationHelper.isValidAirportCode(""));
        assertFalse(ValidationHelper.isValidAirportCode("123"));
        assertTrue(ValidationHelper.isValidAirportCode("ABC"));
    }
    /**Airport code must correspond to a valid name*/
    @Test
    void shouldValidateAirportName(){
        assertFalse(ValidationHelper.isValidAirportName(null));
        assertFalse(ValidationHelper.isValidAirportName(""));
        assertFalse(ValidationHelper.isValidAirportName("ABC"));
        assertTrue(ValidationHelper.isValidAirportName("BOM"));
    }

    /**Date must be in mm/dd/yyyy format*/
    @Test
    void shouldValidateDate(){
        assertFalse(ValidationHelper.isValidDate(null));
        assertFalse(ValidationHelper.isValidDate(""));
        assertFalse(ValidationHelper.isValidDate("12-22-2022"));
        assertFalse(ValidationHelper.isValidDate("12,22 2022"));
        assertTrue(ValidationHelper.isValidDate("12/22/2022"));
    }
    /**Time must be in hh:mm format*/
    @Test
    void shouldValidateTime(){
        assertTrue(ValidationHelper.isValidTime("12:00"));
        assertFalse(ValidationHelper.isValidTime("22:00"));
        assertFalse(ValidationHelper.isValidTime(""));
        assertFalse(ValidationHelper.isValidTime(null));
        assertFalse(ValidationHelper.isValidTime("22:600"));
        assertFalse(ValidationHelper.isValidTime("25:00"));
        assertFalse(ValidationHelper.isValidTime("16.00"));
    }

    /** Time must have the 12-hour indicator: am/pm */
    @Test
    void shouldValidateTimeMarker(){
        assertFalse(ValidationHelper.isValidTimeMarker("ao"));
        assertTrue(ValidationHelper.isValidTimeMarker("am"));
        assertTrue(ValidationHelper.isValidTimeMarker("AM"));
        assertTrue(ValidationHelper.isValidTimeMarker("pm"));
    }

    @Test
    void shouldValidateFlightDuration(){
        Date oldDate = new Date(100010001000L);
        Date newDate = new Date();
        assertFalse(ValidationHelper.isValidFlightDuration(newDate, oldDate));
        assertFalse(ValidationHelper.isValidFlightDuration(newDate, newDate));
        assertTrue(ValidationHelper.isValidFlightDuration(oldDate, newDate));
    }
}
