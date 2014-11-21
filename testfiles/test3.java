public class test3
{

    public abstract interface Foo
    {

    }

    private final interface Bar extends Foo
    {
	public int foo(Foo b)
	{
	    if(b instanceof Bar)
	    {
		return true;
	    }
	    return false;
	}
    }
    
    int foo()
    {
	Integer i = new Integer(1);
	int i[] = new int[3];
	switch(i.intValue())
	{
	case 1:
	    foobar();
	    break;
	case 2:
	    j = t();
	    {
		try
		{
		}
		catch(bar e)
		{
		    e.printStackTrace();
		}
		finally
		{
		    f();
		}
	    }
	    break;
	default:
	    break;
	}
	System.out.println("here");
    }
}
