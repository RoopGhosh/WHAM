package edu.neu.cs5500.jerks.TS1;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import edu.neu.cs5500.jerks.dbProviders.test.*;

@RunWith(Suite.class)
@SuiteClasses({TestJUnit1.class,AddressProviderTest.class,UserProviderTest.class})
public class AllTests {
	public static void main(String arg[])
	{
		@SuppressWarnings("unused")
		AllTests test = new AllTests();
	}
}
	