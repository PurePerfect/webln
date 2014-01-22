package com.pureperfect.webln;

/**
 * Represents a symbolic link.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface Link
{ 
	/**
	 * Whether or not to redirect. If this values is false, in-container request
	 * forwarding will be used.
	 * 
	 * @return whether or not to redirect.
	 */
	public boolean redirect();

	/**
	 * Get the name (inbound URI) of the link.
	 * 
	 * @return the name (inbound URI) of the link.
	 */
	public String getName();

	/**
	 * Get the target for the link.
	 * 
	 * @return the target for the link.
	 */
	public String getTarget();
}