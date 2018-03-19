package com.epam.pharmacy.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Class , provide to show jsp page without any dependieses form action classes.
 *
 * @author Sosenkov Alexei
 */
public class ShowPageCommand implements Command {

	private CommandResult result;

	public ShowPageCommand(String page) {
		result = new CommandResult(page);
	}

	@Override
	public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
		return result;
	}
}
