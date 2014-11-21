package language.java.ast;

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

/**
 * The <code>Visibility</code> class represents a method, field, or
 * type declaration's visibility to itself, its descendents, and
 * outside classifiers.  
 */

public final class Visibility
{
    /** 
     * Default (package level) visibility.
     */

    public static final Visibility DEFAULT = new Visibility("", 0);

    /**
     * Public visibility
     */

    public static final Visibility PUBLIC = new Visibility("public", 1);

    /**
     * Protected visibility
     */

    public static final Visibility PROTECTED = new Visibility("protected", 3);

    /**
     * Private visibility
     */

    public static final Visibility PRIVATE = new Visibility("private", 2);

    private String m_keyword;
    private int m_id;

    public Visibility(String keyword, int id)
    {
	m_keyword = keyword;
	m_id = id;
    }

    /**
     * Returns the visibility object represented by the string.
     */

    public static Visibility fromString(String visibility)
    {
	if(visibility.equals("public"))
	{
	    return PUBLIC;
	}
	else if(visibility.equals("protected"))
	{
	    return PROTECTED;
	}
	else if(visibility.equals("private"))
	{
	    return PRIVATE;
	}
	return DEFAULT;
    }

    /**
     * Returns true if the visibility level is the same as the level specified.
     */

    public boolean equals(Object o)
    {
	if(o == this) return true;

	else if(o instanceof Visibility)
	{
	    return ((Visibility)o).m_id == m_id;
	}
	else if(o instanceof String)
	{
	    o.equals(m_keyword);
	}
	return false;
    }



    public String toString()
    {
	return m_keyword;
    }
}
