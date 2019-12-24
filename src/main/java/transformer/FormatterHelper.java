package transformer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Null;
import java.util.Calendar;
import java.util.Date;

public class FormatterHelper {

    public String calendarToTimestamp(Calendar cal) {
        return String.valueOf(cal.getTimeInMillis());
    }

    public Calendar timestampToCalendar(String timestamp) {
        if (StringUtils.isEmpty(timestamp)) {
            throw new NullPointerException();
        }
        Calendar c = Calendar.getInstance();
        Date d = new Date(Long.parseLong(timestamp));
        c.setTime(d);
        return c;
    }
}
