package com.epam.pharmacy.util;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class, work with SQL date.
 *
 * @author Sosenkov Alexei
 */
public class SqlDate {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SqlDate.class);
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	/**
     * Method , Sets the current time
     */
    public static Date getCurrentDateAndTime() {
        return new Date(Calendar.getInstance()
        		.getTime()
        		.getTime());
    }

    /**
     * Method, formats the string in the date format
     *
     * @param date - string field
     * @return Return SQL date
     */
    public static Date getDate(String date) {
        Date sqlDate = null;
        boolean isEmptyDate = !date.isEmpty();
        if(!isEmptyDate) {
            try {
                DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
                sqlDate = new Date(dateFormat.parse(date)
                		.getTime());
                return sqlDate;
            } catch (ParseException e) {
                LOGGER.warn("Incorrect date!", e);
            }
        }
        return sqlDate;
    }
}
