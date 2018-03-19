package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface ,needs to be defined
 * as an interface type which should do the work based on
 * the passed-in arguments of the abstract method
 *
 * @author Sosenkov Alexei
 */
public interface Command {

	CommandResult execute(HttpServletRequest request, HttpServletResponse response);
}
