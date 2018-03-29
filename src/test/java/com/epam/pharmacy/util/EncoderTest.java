package com.epam.pharmacy.util;

import org.junit.Assert;
import org.junit.Test;

public class EncoderTest {

	private final static String INSERTED_PASSWORD = "223223";
	private final static String EXPECTED_PASSWORD = "ad0a5640be863c30d45d07f704fc9a7f";
	
	@Test
	public void shouldReturnEncriptedPasswordInMD5WhenGetInsertedPassword() {
		//when
		String actualPassword = DigestUtils.encode(INSERTED_PASSWORD);
		//then
		Assert.assertEquals(EXPECTED_PASSWORD, actualPassword);
	}
}
