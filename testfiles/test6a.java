package test6;

import foo.bar.*;
import java.util.*;

public class test6
{
   String s = "foo";
   int i = 0;

   int foo()
   {
	return i;
   }
   
   protected final String bar(String a)
   {
	s = a;
	return s;
   }
}
