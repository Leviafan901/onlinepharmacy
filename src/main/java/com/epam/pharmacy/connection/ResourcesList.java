package com.epam.pharmacy.connection;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epam.pharmacy.exceptions.ResourcesException;

/**
 * Custom class-list, for storing stream objects
 *
 * @author Sosenkov Alexei
 */
public class ResourcesList<T> {

	private static final Logger LOGGER = LoggerFactory.getLogger(ResourcesList.class);
	
	private static final int HEAD_ELEMENT = 0;
	
	/**
	 * Locker for concurrent resources
	 */
	private static Lock locker = new ReentrantLock();
	
	/**
     * Field - restrict access to the resource.
     */
    private Semaphore semaphore;
    
    /**
     * Field - storage of the list of initialized connections
     */
    private List<T> resourceList = new LinkedList<>();
    
    /**
     * Field - time waiting for a special connection
     */
    private final int timeOut;

    /**
     * Creates a new object with Semaphore and assigns a value to timeOut
     *
     * @param count   - semaphore counter
     * @param timeOut - time waiting for a connection
     */
    public ResourcesList(int count, int timeOut) {
        semaphore = new Semaphore(count, true);
        this.timeOut = timeOut;
    }

    /**
     * Method that allows you to take a connection from the list if it is available
     *
     * @return initialized connection
     */
    public T takeResource() throws ResourcesException {
        try {
            if (semaphore.tryAcquire(timeOut, TimeUnit.SECONDS)) {
                LOGGER.debug("The connection was taken");
                return resourceList.remove(HEAD_ELEMENT);
            }
        } catch (InterruptedException e) {
            throw new ResourcesException("Didn't wait for connect", e);
        }
        throw new ResourcesException("Didn't wait for connect");
    }

    /**
     * Returns the connection back to the list of initialized connections
     */
    public void returnResource(T resource) {
        locker.lock();
        try {
	        resourceList.add(resource);
	        semaphore.release();
	        LOGGER.debug("The connection was added back!");
        } finally {
        	locker.unlock();
        }
    }

    /**
     * Adds an initialized connection to the list
     */
    public void addResource(T resource) {
    	locker.lock();
        try {
        	resourceList.add(resource);
        } finally {
        	locker.unlock();
        }
    }

    /**
     * The size of the list
     *
     * @return List size
     */
    public int size() {
        return resourceList.size();
    }
	
	/**
     * List of connections
     *
     * @return Returns a list
     */
    public List<T> getResources() {
    	locker.lock();
        try {
        	return resourceList;
        } finally {
        	locker.unlock();
        }
    }
}
