package com.pureperfect.webln;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pureperfect.ferret.WebScanner;
import com.pureperfect.ferret.vfs.PathElement;

/**
 * Intercepting filter for handling requests and processing symbolic links.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
public class Filter implements javax.servlet.Filter
{
	private static final Log log = LogFactory.getLog("WebLN");

	private XMLLinkManager linkManager = new XMLLinkManager();

	private ServletContext context;

	@Override
	public void destroy()
	{
		log.info("WebLN shutting down...");
	}

	@Override
	public void doFilter(ServletRequest pointless, ServletResponse lyabstract,
			FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) pointless;

		Link link = this.linkManager.getLink(request.getServletPath());

		/*
		 * No link defined. Just continue the chain.
		 */
		if (link == null)
		{
			chain.doFilter(pointless, lyabstract);
		}

		else
		{
			if (link.redirect())
			{
				HttpServletResponse response = (HttpServletResponse) lyabstract;

				response.sendRedirect(link.getTarget());
			}
			else
			{
				RequestDispatcher dispatch = request.getRequestDispatcher(link
						.getTarget());

				dispatch.forward(request, lyabstract);
			}
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		log.info("Starting WebLN filter...");
		log.info("Initializing class path scanner...");

		/*
		 * First we try to scan the class path to find any global .webln files.
		 */
		WebScanner ferret = new WebScanner();

		this.context = config.getServletContext();

		try
		{
			ferret.add(context, "/WEB-INF/classes");
			ferret.add(context, "/WEB-INF/lib");
		}
		catch (Throwable e)
		{
			log.error("Error initializing class path scanner: ", e);
		}

		log.info("Scanning for .webln files...");

		Set<PathElement> weblnFiles = ferret.scan(new WebLNScanner());

		log.info(weblnFiles.size() + " found.");

		/*
		 * Load any .webln files that we found.
		 */
		for (PathElement weblnFile : weblnFiles)
		{
			log.info("Found: " + weblnFile);

			try
			{
				this.linkManager.load(weblnFile.openStream());
			}
			catch (BadLinkException ble)
			{
				StringBuilder msg = new StringBuilder();

				msg.append("Bad link ");
				msg.append(ble.getLink());
				msg.append(" in ");
				msg.append(weblnFile);
				msg.append(": ");

				log.error(msg, ble);
			}
			catch (Throwable t)
			{
				StringBuilder msg = new StringBuilder();

				msg.append("Error parsing resource ");
				msg.append(weblnFile);
				msg.append(": ");

				log.error(msg, t);
			}
		}

		/*
		 * Now scan for .weblnk files.
		 */
		log.info("Scanning for .weblnk files...");

		ferret = new WebScanner();

		ferret.addPublicResources(this.context);

		Set<PathElement> inlineLinks = ferret.scan(new WebLNKScanner());

		log.info(inlineLinks.size() + " found.");

		for (PathElement linkFile : inlineLinks)
		{
			log.info("Found in-line link file: " + linkFile);

			try
			{
				String name = linkFile.getFullPath();

				/*
				 * Trim .weblnk suffix.
				 */
				name = name.substring(0, name.length() - 7);

				this.linkManager.load(name, linkFile.openStream());
			}
			catch (BadLinkException ble)
			{
				StringBuilder msg = new StringBuilder();

				msg.append("Bad link ");
				msg.append(ble.getLink());
				msg.append(" in ");
				msg.append(linkFile);
				msg.append(": ");

				log.error(msg, ble);
			}
			catch (Throwable t)
			{
				StringBuilder msg = new StringBuilder();

				msg.append("Error parsing resource ");
				msg.append(linkFile);
				msg.append(": ");

				log.error(msg, t);
			}
		}
	}
}