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
    /** should build a date using date elements*/
    @Test
    void shouldConvertDateElementsToDate(){
        var result = DateHelper.dateElementsToDate("3", "3", "2023", "15", "00");
        assertThat(result, is(not(nullValue())));
        result = DateHelper.dateElementsToDate("3", "3", "2023", "5", "00");
        assertThat(result, is(not(nullValue())));
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
        Date date = DateHelper.stringToDate("3/3/2023 5:00 am");
        var res = DateHelper.datetoShortString(date);
        assertThat(res, is(not(nullValue())));
        assertThat(res, equalTo("3/3/23, 5:00 AM"));
    }
    @Test
    void shouldConvertDateToMediumString(){
        Date date = DateHelper.stringToDate("3/3/2023 5:00 am");
        assertThat(DateHelper.datetoMediumString(date), is(not(nullValue())));
        assertThat(DateHelper.datetoMediumString(date), equalTo("Mar 3, 2023, 5:00:00 AM"));
    }

    @Test
    void shouldBreakdownDate(){
        Date date = DateHelper.stringToDate("3/3/2023 5:00 pm");
        var res = DateHelper.breakdownDate(date);
        assertThat(res, is(not(nullValue())));

        date = DateHelper.stringToDate("3/3/2023 5:00 am");
        res = DateHelper.breakdownDate(date);
        assertThat(res, is(not(nullValue())));

    }
}
