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

import java.io.*;

/**
 * The <code>Parser</code> interface defines a service that produces a
 * tree from some serialized form.  
 */

public interface Parser
{

    /**
     * Extracts the tree from the specified reader.
     * @param reader the reader to extract the tree from
     * @param index <code>true</code> if the nodes in the tree should be indexed.
     */

    public RootNode parse(InputStreamReader reader, boolean index)
	throws ParseException;
}
