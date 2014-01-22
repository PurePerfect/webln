package com.pureperfect.webln;

import java.util.Collection;

/**
 * A link manager manages links.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public interface LinkManager
{
	/**
	 * Get the symbolic link for the given path or null if none exists.
	 * 
	 * @param uri
	 *            the uri to get the link for.
	 * @return the symbolic link for the given path or null if none exists.
	 */
	public Link getLink(String uri);

	/**
	 * Get the links that this manager manages.
	 * 
	 * @return the links that this manager manages.
	 */
	public Collection<Link> getLinks();

	/**
	 * Add a link.
	 * 
	 * @param link
	 *            the link to add.
	 */
	public void add(Link link);
}
