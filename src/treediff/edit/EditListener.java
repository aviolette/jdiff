package treediff.edit;

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
 * The <code>EditListener</code> interface defines events that are
 * signaled as edit operations are applied by the edit script.
 *
 * @author Andrew Violette
 */

public interface EditListener
{
   /**
    * Called when a delete operation is applied.
    * @param d the delete operation.
    */

   public void deleteApplied(Delete d);

   /**
    * Called when a move operation is applied.
    * @param m the move operation.
    */

   public void moveApplied(Move m);

   /**
    * Called when an insert operation is applied.
    */

    public void insertApplied(Insert i);

   /**
    * Called when an update operation is applied.
    */

    public void updateApplied(Update u);
}
