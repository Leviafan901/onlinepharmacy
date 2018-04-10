package com.epam.pharmacy.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

/**
 * Action class, responsible for changing languages
 *
 * @author Sosenkov Alexei
 */
public class SelectLanguageCommand implements Command {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(SelectLanguageCommand.class);

    private static final String LANGUAGE = "lang";
    private static final String REFERER = "referer";
    private static final String CHARACTER_ENCODING = "UTF-8";
    private static final int HOUR = 24;
    private static final int MINUTE = 60;
    private static final int SEC = 60;
    
    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String language = request.getParameter(LANGUAGE);
        Config.set(request.getSession(), Config.FMT_LOCALE, new Locale(language));
        Cookie cookie = new Cookie(LANGUAGE, language);
        cookie.setMaxAge(HOUR * MINUTE * SEC);
        response.addCookie(cookie);
        try {
            request.setCharacterEncoding(CHARACTER_ENCODING);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("Can't set character encoding", e);
        }
        return new CommandResult(request.getHeader(REFERER), true);
    }
}
