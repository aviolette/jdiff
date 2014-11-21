import java.util.*;
import foo.bar.*;

public abstract class test6
{
   String s = "foo";
   int i = 0;

   public abstract int foo();
   
   public String bar(String a)
   {
	s = a;
	return s;
   }

   public abstract void foobar();

}
