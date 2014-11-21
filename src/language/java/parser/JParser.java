package language.java.parser;

/* 
 * Copyright (C) 2000  Andrew Violette
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License as
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA 
 *
 * Andrew Violette 
 * aviolette@acm.org
 *
 */

import tree.*;
import java.io.*;

/**
 * The <code>JParser</code> class is strictly an adapter class that
 * adapts the interface of <code>JavaParser</code> with that of the
 * Parser interface.
 */

public class JParser
implements Parser
{

    public RootNode parse(InputStreamReader reader, boolean index)
	throws tree.ParseException
    {
	try
	{
	    JavaParser parser = new JavaParser(reader);
	    return parser.parseCU(index);
	}
	catch(com.metamata.parse.ParseException e)
	{
	    throw new tree.ParseException(e);
	}
    }


    
}
