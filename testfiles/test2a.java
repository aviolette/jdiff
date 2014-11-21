package bar;

public class Foo
{
	public Foo()
	{
		this(b);
	}
	public static final int A_FIELD = 0;
	private static final int ANOTHER_FIELD = 1;
	public transient int aField = 2;
	public abstract void anAbstractMethod();
	public Object bar()
	{
		synchronized (aField)
		{
			bar();
			aSynchronizedMethod(null, null);
		}
		return null;
	}
	public synchronized int aSynchronizedMethod(String foo,
			final String bar)
	{
		ATRANSIENT_FIELD = 3;
		int foo[][] = new int[3][4];
		int foobar[] = {1, 2, 3};
		int j = 0;
		for (int i = 0; i < foobar.length; i++, j++)
		{
			aField = foobar[i][j];
		}
		return null;
	}
	public Foo(Bar b)
	{
		super(b);
	}
}
