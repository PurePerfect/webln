package com.pureperfect.webln;

import com.pureperfect.ferret.ScanFilter;
import com.pureperfect.ferret.vfs.PathElement;

/**
 * TODO put both of these in a single file...actually just consolidate file
 * formats
 * 
 * TODO make sure that .webln with no prefix before the . works for directories.
 * 
 * A visitor that returns .webln files.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class WebLNScanner implements ScanFilter
{
	@Override
	public boolean accept(PathElement resource)
	{
		return resource instanceof Object
				&& resource.getName().endsWith(".webln");
	}
}
