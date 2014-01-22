package com.pureperfect.webln;

import com.pureperfect.ferret.ScanFilter;
import com.pureperfect.ferret.vfs.PathElement;

/**
 * A visitor that returns .weblnk files.
 * 
 * @author J. Chris Folsom
 * @version 1.0
 * @since 1.0
 */
class WebLNKScanner implements ScanFilter
{
	@Override
	public boolean accept(PathElement resource)
	{
		//FIXME
		return resource instanceof Object
				&& resource.getName().endsWith(".weblnk");
	}
}