package com.pureperfect.webln;

/**
 * Thrown when a link definition is invalid.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class BadLinkException extends RuntimeException
{
	private static final long serialVersionUID = -7606685253070994979L;

	private Link link;

	/**
	 * Create a new exception.
	 * 
	 * @param link
	 *            the bad link
	 */
	BadLinkException(Link link)
	{
		super();
		this.link = link;
	}

	/**
	 * Get the link that caused the problem.
	 * 
	 * @return the link that caused the problem.
	 */
	public Link getLink()
	{
		return this.link;
	}
}
