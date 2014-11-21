package debug;

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

import java.util.*;
import java.io.*;

public class Debug
{
    private Debug() {}

    public static boolean debugMode = true;
    public static PrintWriter out = new PrintWriter(System.out);

    public static void assert(boolean predicate, String s)
    {
	if(!debugMode) return;

	if(!predicate)
	{
	    throw new AssertionFailedException("Assertion failed: "+s);
	}
    }

    public static void mapPrint(Map m)
    {
	if(!debugMode) return;

	Iterator i = m.keySet().iterator();
	out.print("{");
	while(i.hasNext())
	{
	    Object key = i.next();
	    System.out.println("(" + key.toString() + ", " + m.get(key)+")");
	}
	out.println("}");
    }


    public static void collectionPrint(Collection c, boolean ret)
    {
	if(!debugMode) return;
	Iterator i = c.iterator();
	out.print("<");
	while(i.hasNext())
	{
	    out.print(i.next().toString());
	    if(i.hasNext())
	    {
		out.print(", ");
		if(ret)
		{
		    out.println();
		}
	    }
	}
	out.println(">");
    }

    public static void debugPrint(String s)
    {
	if(!debugMode) return;
	out.println(s);
    }

    public static void assert(boolean predicate)
    {
	if(!debugMode) return;
	assert(predicate, "Assertion failed.");
    }
}
