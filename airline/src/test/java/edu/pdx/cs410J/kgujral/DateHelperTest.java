package edu.pdx.cs410J.kgujral;
import org.junit.jupiter.api.Test;
import java.util.Date;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/** Unit Tests for {@link DateHelper}*/
public class DateHelperTest {
    @Test
    void shouldConvertStringToDateIfStringContainsValidDate(){
        String date = "3/3/2023 5:00 am";
        assertThat(DateHelper.stringToDate(date), is(not(nullValue())));
    }
    @Test
    void shouldConvertShortStringToDateIfStringContainsValidDate(){
        String date = "3/3/2023, 5:00 am";
        assertThat(DateHelper.shortStringToDate(date), is(not(nullValue())));
    }
    @Test
    void shouldNotConvertStringToDateIfStringContainsInvalidDate(){
        String date = "3/3/2023 5:00";
        assertThat(DateHelper.stringToDate(date), is(nullValue()));
    }
    @Test
    void shouldConvertDateToShortString(){
        Date date = new Date();
        assertThat(DateHelper.datetoShortString(date), is(not(nullValue())));
    }
    @Test
    void shouldConvertDateToMediumString(){
        Date date = new Date();
        assertThat(DateHelper.datetoMediumString(date), is(not(nullValue())));
    }
}
