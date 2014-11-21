package language.java.diff;

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

import java.io.*;

/**
 * Extends the behavior of <code>PrintWriter</code> by maintaining a
 * left, tabbed margin.  The <code>IndentationWriter</code> class will
 * preceed each new line with <i>n</i> number of tabs, where <i>n</i>
 * is the level set by the various methods provided by this class.
 * 
 */

public class IndentationWriter extends PrintWriter
{

    private int m_level = 0;
    private String m_tab;
    private String m_space;
    private String m_endln;


    /**
     * Constructs an <code>IndentationWriter</code> object.
     */

    public IndentationWriter(Writer writer, String tab, 
			     String space, String endln)
    {
	super(writer);
	m_tab = tab;
	m_space = space;
	m_endln = endln;
    }

    
    

    /**
     * Sets the current indentation level.
     */

    public void setLevel(int level)
    {
	m_level = level;
    }

    /**
     * Returns the current indentation level.
     */

    public int getLevel()
    {
	return m_level;
    }

    /**
     * Increments the current indentation level.
     */

    public void incrementLevel()
    {
	m_level++;
    }
    
    /**
     * Decrements the current indentation level.
     */

    public void decrementLevel()
    {
	if(m_level > 0)
	{
	    m_level--;
	}
    }

    /**
     * Prefixes <code>n</code> tabs to the front of the String
     * <code>s</code>.  
     */

    public String indent(String s, int n)
    {
	StringBuffer buffer = new StringBuffer(n + 1 + s.length());
	for(int i=0; i < n; i++)
	{
	    buffer.append(m_tab);
	}
	buffer.append(s);
	return buffer.toString();
	
    }

    private String indent(String s)
    {
	return indent(s, m_level);
    }

    /**
     * Prints out the string so that the string is preceeded by the
     * number of tabs specified by the current level.
     */

    public void print(String s)
    {
	super.print(indent(s));
    }

    /**
     * Converts the object to a string, and prints out the string so
     * that the string is preceeded by the number of tabs specified by
     * the current level.  
     */

    public void print(Object o)
    {
	super.print(indent(o.toString()));
    }

    /**
     * Prints the string, preceeded by the number of tabs specified by
     * the current level.  
     */

    public void println(String s)
    {
	print(s);
	write(m_endln);
    }
    
    /**
     * Converts the object to a string, prefixes the string with the
     * number of tabs specified by the current level, and prints out
     * the line.  
     */

    public void println(Object o)
    {
	//	println(o.toString());
	print(o.toString());
	write(m_endln);
    }

}
