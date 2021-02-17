package scripts;

import org.testng.annotations.Test;


import generic.BaseClass;
import pages.LogInPage;

public class TestLogin extends BaseClass
{
	@Test
	public void validLogin() 
	{
		test=extent.createTest("Valid LogIn");
		LogInPage lp=new LogInPage();
			
	}	
}
