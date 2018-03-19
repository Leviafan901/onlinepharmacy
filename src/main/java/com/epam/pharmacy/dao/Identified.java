package com.epam.pharmacy.dao;

import java.io.Serializable;

/**
 * 
 * @author Alexei Sosenkov
 * Interface that identify objects by Long value.
 */
public interface Identified<PK extends Serializable> {

	/** Return identificator of object */
    public PK getId();

	public void setId(PK key);
}
