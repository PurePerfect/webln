package com.pureperfect.webln;

import org.jdom.Element;

/**
 * XMLLink.
 *  
 * <pre>
 * 	<link>
 * 		<name>name</name>
 * 		<target>target</target>
 * 		<redirect>true</redirect>
 * 	</link>
 * </pre>
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class XMLLink implements Link
{
	private boolean redirect;

	private String name;

	private String target;

	/**
	 * Create a new instance from the specified xml link element.
	 * 
	 * @param linkElement
	 *            the xml
	 */
	public XMLLink(Element linkElement)
	{
		this.redirect = Boolean.valueOf(linkElement
				.getChildTextTrim("redirect"));

		this.name = linkElement.getChildTextTrim("name");
		this.target = linkElement.getChildTextTrim("target");

		this.validate();
	}

	/**
	 * Create a new instance with the given name. Only target and redirect will
	 * be loaded from the xml.
	 * 
	 * @param name
	 *            The name of the element.
	 * 
	 * @param linkElement
	 *            the xml defining target and redirect.
	 */
	public XMLLink(String name, Element linkElement)
	{
		this.redirect = Boolean.valueOf(linkElement
				.getChildTextTrim("redirect"));

		this.name = name;
		this.target = linkElement.getChildTextTrim("target");

		this.validate();
	}

	/**
	 * Make sure that the link is valid to be used.
	 */
	private void validate()
	{
		if (this.name == null || this.target == null)
			throw new BadLinkException(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean redirect()
	{
		return this.redirect;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName()
	{
		return this.name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTarget()
	{
		return this.target;
	}

	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder(64);
		
		builder.append("[");
		builder.append("name=");
		builder.append(this.name);
		builder.append(", target=");
		builder.append(this.target);
		builder.append(", redirect=");
		builder.append(this.redirect);
		builder.append("]");
		
		return builder.toString();
	}
}