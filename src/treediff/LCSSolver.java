package treediff;

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
import debug.Debug;

/**
 * The <code>LCSSolver</code> class is used to determine the longest
 * common subsequences (LCS) between two sets of objects.  The
 * collections specified as input to the functions below should be
 * optimized for random access.  The algorithm used to compute the LCS
 * is based a dynamic programming algorithm in <i>Introduction to
 * Algorithms</i> by Cormen, Rivest, and Leiserson.
 *
 * @author Andrew Violette 
 */

final class LCSSolver
{
   private long[][] m_c;
   private int[][] m_b;
   private Comparator m_comparator;

   private Map m_forward;
   private Map m_reverse;

   private static final int NORTH = -1;
   private static final int NORTHWEST = -2;
   private static final int WEST = -3;


   /**
    * Constructs an <code>LCSSolver</code> object.
    * @param comparator the comparator object used to determine
    * equivalence of the nodes.  Cannot be <code>null</code>.  
    */

   public LCSSolver(Comparator comparator)
   {
	m_comparator = comparator;
   }

   /**
    * Generates the tables that define the LCS. After this method is
    * called, <code>traverseLCSMatrix()</code> can be used to
    * retrieve the LCS.  
    */

   private void generateLCS(AbstractList s1, AbstractList s2)
   {
	int m = s1.size();
	int n = s2.size();

	m_c = new long[m+1][n+1];
	m_b = new int[m+1][n+1];

	for(int i=1; i <= m; i++)
	{
	   for(int j=1; j <= n; j++)
	   {
		if(m_comparator.compare(s1.get(i-1), s2.get(j-1)) == 0)
		{
		   m_c[i][j] = m_c[i-1][j-1] + 1;
		   m_b[i][j] = NORTHWEST;
		}
		else if(m_c[i-1][j] >= m_c[i][j-1])
		{
		   m_c[i][j] = m_c[i-1][j];
		   m_b[i][j] = NORTH;
		}
		else
		{
		   m_c[i][j] = m_c[i][j-1];
		   m_b[i][j] = WEST;
		}
	   }
	}
	
   }
    

   /**
    * Given two sequences of nodes, S<sub>1</sub> and S<sub>2</sub>,
    * this function returns a map with matching nodes of
    * S<sub>2</sub> as the keys, and their partners in S<sub>1</sub>
    * as the values. 
    *
    * @param s1 sequence 1
    * @param s2 sequence 2
    * @param unX the unmatched nodes of S<sub>1</sub>
    * @param unY the unmatched nodes of S<sub>2</sub>
    * @param map a map whose key set is the matched nodes from
    * S<sub>2</sub> and whoses mapped values are the matched nodes
    * from S<sub>1</sub>.
    * @param reverse a map whose key set is the matched nodes from
    * S<sub>1</sub> and whose mapped values are the matched nodes
    * from S<sub>2</sub>.
    */

   public void lcs(AbstractList s1, AbstractList s2, AbstractList unX, AbstractList unY, Map map, Map reverse)
   {
	unX.clear();
	unY.clear();

	unX.addAll(s1);
	unY.addAll(s2);

	generateLCS(s1, s2);

	m_forward = map;
	m_reverse = reverse;

	traverseLCSMatrix(unX, unY, s1.size(), s2.size());
	generateUnsolved(map, unY);
	generateUnsolved(reverse, unX);

   }

   private void generateUnsolved(Map map, AbstractList unsolved)
   {
	Iterator i = map.keySet().iterator();
	while(i.hasNext())
	{
	   unsolved.remove(i.next());
	}
   }

   private void traverseLCSMatrix(AbstractList s1, AbstractList s2, int i, int j)
   {
	if(i == 0 || j == 0) return;

	if(m_b[i][j] == NORTHWEST)
	{
	   traverseLCSMatrix(s1, s2, i - 1, j - 1);
	   Object n1 = s1.get(i-1);
	   Object n2 = s2.get(j-1);
	   m_forward.put(n2, n1);
	   m_reverse.put(n1, n2);
	}
	else if(m_b[i][j] == NORTH)
	{
	   traverseLCSMatrix(s1, s2, i - 1, j);
	}
	else
	{
	   traverseLCSMatrix(s1, s2, i, j - 1);
	}
   }

   private static class EquivalenceComparator implements Comparator
   {
	private boolean xorIsNull(Object o1, Object o2)
	{
	   return (((o1 == null) && (o2 != null)) || 
		     (o1 != null) && (o2 == null));
	}

	public int compare(Object o1, Object o2)
	{
	   return equal(o1, o2) ? 0 : 1;
	}
	public boolean equal(Object o1, Object o2)
	{
	   if(xorIsNull(o1, o2)) return false;
	   return o1.equals(o2);
	}
   }

   /**
    * Runs a component test of the <code>LCSSolver</code> class.
    */

   public static void main(String args[])
   {
	Comparator comparator = new EquivalenceComparator();
	
	LCSSolver solver = new LCSSolver(comparator);

	Vector v1 = new Vector();
	Vector v2 = new Vector();
	
	v1.addElement("a");
	v1.addElement("x");
	v1.addElement("d");
	v1.addElement("e");
	v1.addElement("z");
	v1.addElement("f");

	v2.addElement("a");
	v2.addElement("b");
	v2.addElement("c");
	v2.addElement("d");
	v2.addElement("e");
	v2.addElement("f");

	Vector u1 = new Vector();
	Vector u2 = new Vector();

	HashMap forward = new HashMap();
	HashMap reverse = new HashMap();

	solver.lcs(v1, v2, u1, u2, forward, reverse);

   }

}
