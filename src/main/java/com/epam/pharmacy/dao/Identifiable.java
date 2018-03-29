package com.epam.pharmacy.dao;

import java.io.Serializable;

/**
 * 
 * @author Alexei Sosenkov
 * Interface that identify objects by Long value.
 */
public interface Identifiable extends Serializable  {

	/** Return identificator of object */
    public Long getId();

	public void setId(Long key);
}
