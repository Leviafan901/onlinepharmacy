package com.epam.pharmacy.command;

public class CommandResult {

	private final String view;
    private final boolean redirect;
	
    public CommandResult(String view, boolean redirect) {
		this.view = view;
		this.redirect = redirect;
	}
    
    public CommandResult(String view) {
		this.view = view;
		this.redirect = false;
	}

	public String getView() {
		return view;
	}

	public boolean isRedirect() {
		return redirect;
	}
    
    
}
