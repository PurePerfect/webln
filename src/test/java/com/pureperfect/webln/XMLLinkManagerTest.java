package com.pureperfect.webln;

import java.io.IOException;
import java.io.InputStream;

import junit.framework.TestCase;

public class XMLLinkManagerTest extends TestCase
{
	public void testMissingRedirect() throws Exception
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/missing-redirect.webln");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load(in);

		assertEquals(1, lm.getLinks().size());

		Link link = lm.getLink("thesource");

		assertEquals("thesource", link.getName());
		assertEquals("thetarget", link.getTarget());
		assertFalse(link.redirect());
	}

	public void testRedirectTrue() throws Exception
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/redirect-true.webln");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load(in);

		assertEquals(1, lm.getLinks().size());

		Link link = lm.getLink("thesource");

		assertEquals("thesource", link.getName());
		assertEquals("thetarget", link.getTarget());
		assertTrue(link.redirect());
	}

	public void testRedirectFalse() throws Exception
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/redirect-false.webln");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load(in);

		assertEquals(1, lm.getLinks().size());

		Link link = lm.getLink("thesource");

		assertEquals("thesource", link.getName());
		assertEquals("thetarget", link.getTarget());
		assertFalse(link.redirect());
	}

	public void testMultipleInFile() throws IOException
	{
		InputStream in = this.loadFile("com/pureperfect/webln/multiple.webln");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load(in);

		assertEquals(2, lm.getLinks().size());

		Link link1 = lm.getLink("link1");

		assertEquals("link1", link1.getName());
		assertEquals("target1", link1.getTarget());
		assertFalse(link1.redirect());

		Link link2 = lm.getLink("link2");

		assertEquals("link2", link2.getName());
		assertEquals("target2", link2.getTarget());
		assertTrue(link2.redirect());
	}

	public void testMissingName() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/missing-name.webln");

		XMLLinkManager lm = new XMLLinkManager();

		try
		{
			lm.load(in);
			fail();
		}
		catch (BadLinkException wtf)
		{
			BadLinkException e = (BadLinkException) wtf;

			Link link = e.getLink();

			assertNull(link.getName());
			assertEquals("thetarget", link.getTarget());
			assertFalse(link.redirect());
		}
	}

	public void testMissingTarget() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/missing-target.webln");

		XMLLinkManager lm = new XMLLinkManager();

		try
		{
			lm.load(in);
			fail();
		}
		catch (BadLinkException wtf)
		{
			BadLinkException e = (BadLinkException) wtf;

			Link link = e.getLink();

			assertEquals("linkname", link.getName());
			assertNull(link.getTarget());
			assertTrue(link.redirect());
		}
	}

	public void testInlineMissingRedirect() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/missing-redirect.weblnk");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load("link", in);

		Link link = lm.getLink("link");

		assertEquals("link", link.getName());
		assertEquals("thetarget", link.getTarget());
		assertFalse(link.redirect());
	}

	public void testInlineMissingName() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/missing-name.weblnk");

		XMLLinkManager lm = new XMLLinkManager();

		try
		{
			lm.load(null, in);
			fail();
		}
		catch (BadLinkException e)
		{
			Link link = e.getLink();

			assertNull(link.getName());
			assertEquals("thetarget", link.getTarget());
			assertFalse(link.redirect());
		}
	}

	public void testInlineMissingTarget() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/missing-target.weblnk");

		XMLLinkManager lm = new XMLLinkManager();

		try
		{
			lm.load("itsalink", in);
			fail();
		}
		catch (BadLinkException e)
		{
			Link link = e.getLink();

			assertEquals("itsalink", link.getName());
			assertNull(link.getTarget());
			assertFalse(link.redirect());
		}
	}

	public void testInlineRedirectFalse() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/redirect-false.weblnk");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load("itsalink", in);

		Link link = lm.getLink("itsalink");

		assertEquals("itsalink", link.getName());
		assertEquals("thedestination", link.getTarget());
		assertFalse(link.redirect());
	}

	public void testInlineRedirectTrue() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/redirect-true.weblnk");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load("itsalink", in);

		Link link = lm.getLink("itsalink");

		assertEquals("itsalink", link.getName());
		assertEquals("thedestination", link.getTarget());
		assertTrue(link.redirect());
	}

	public void testToString() throws IOException
	{
		InputStream in = this
				.loadFile("com/pureperfect/webln/redirect-true.webln");

		XMLLinkManager lm = new XMLLinkManager();

		lm.load(in);

		assertEquals(1, lm.getLinks().size());

		Link link = lm.getLink("thesource");
		
		assertEquals("[name=thesource, target=thetarget, redirect=true]", link.toString());

		
		in = this
				.loadFile("com/pureperfect/webln/redirect-false.webln");

		lm.load(in);

		assertEquals(1, lm.getLinks().size());

		link = lm.getLink("thesource");
		
		assertEquals("[name=thesource, target=thetarget, redirect=false]", link.toString());
	}

	private InputStream loadFile(String path)
	{
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		return cl.getResourceAsStream(path);
	}
}
