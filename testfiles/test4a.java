package foo.bar;

import foo.*;
import bar.*;

public class Foo
extends Bar
implements Foobar, BarFoo
{

    int bar = 1;

    public int b()
    {
	return bar;
    }
}
