package com.pureperfect.webln;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * Loads and manages links from XML files.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class XMLLinkManager implements LinkManager
{
	private Map<String, Link> managedLinks;

	/**
	 * Create a new link manager.
	 */
	public XMLLinkManager()
	{
		this.managedLinks = new HashMap<String, Link>();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Link getLink(String uri)
	{
		return this.managedLinks.get(uri);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Collection<Link> getLinks()
	{
		return Collections.unmodifiableCollection(this.managedLinks.values());
	}

	/**
	 * Load links from the given input stream. This method is used for
	 * <code>.webln</code> files that contain multiple link definitions.
	 * 
	 * @param in
	 *            the input stream for the xml file.
	 * @throws IOException
	 *             if there is an error parsing the xml from the stream.
	 */
	public void load(InputStream in) throws IOException
	{
		SAXBuilder docBuilder = new SAXBuilder();

		try
		{
			Document document = docBuilder.build(in);

			Element root = document.getRootElement();

			// Its a file that we care about
			if ("webln".equals(root.getName()))
			{
				@SuppressWarnings("unchecked")
				List<Element> linkElements = root.getChildren();

				for (Element linkElement : linkElements)
				{
					Link link = new XMLLink(linkElement);

					this.managedLinks.put(link.getName(), link);
				}
			}
		}
		catch (BadLinkException ble)
		{
			throw ble;
		}
		catch (JDOMException jde)
		{
			throw new IOException(jde);
		}
	}

	/**
	 * Load an inline link.
	 * 
	 * @param name
	 *            the name of the file
	 * @param in
	 *            the stream to the file
	 * @throws IOException
	 *             if an error occurs reading the file
	 */
	public void load(String name, InputStream in) throws IOException
	{
		SAXBuilder docBuilder = new SAXBuilder();

		try
		{
			Document document = docBuilder.build(in);

			Element linkElement = document.getRootElement();

			// Its a file that we care about
			if ("link".equals(linkElement.getName()))
			{
				Link link = new XMLLink(name, linkElement);

				this.add(link);
			}
		}
		catch (BadLinkException ble)
		{
			throw ble;
		}
		catch (JDOMException jde)
		{
			throw new IOException(jde);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void add(Link link)
	{
		this.managedLinks.put(link.getName(), link);
	}
}