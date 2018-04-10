package com.epam.pharmacy.util;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {

	private static final String DATE_FORMAT = "yyyy-MM-dd";

	public static LocalDate getFormattedDate(String stringDate) throws ParseException {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
	    LocalDate localExpirationDate = LocalDate.parse(stringDate, dateFormatter);
		return localExpirationDate;
	}
}
