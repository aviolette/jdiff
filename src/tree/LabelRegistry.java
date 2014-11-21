package tree;

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

public final class LabelRegistry
{
    private static LabelRegistry s_registry;
    private java.util.Map m_registry;

    {
	m_registry = new java.util.HashMap();
    }
    public void register(String label, Class c)
    {
	m_registry.put(label, c);
    }

    public java.util.Iterator allLabels()
    {
	return (java.util.Iterator)m_registry.keySet().iterator();
    }
    
    public Class getClassFromLabel(String label)
    {
	return (Class)m_registry.get(label);
    }
}
