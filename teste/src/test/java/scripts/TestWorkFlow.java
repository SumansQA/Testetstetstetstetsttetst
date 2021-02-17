package scripts;

import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import generic.BaseClass;
import generic.Utils;
import pages.WorkFlowPages;

public class TestWorkFlow extends BaseClass
{
	@Test
	public void workFlow()
	{
		test=extent.createTest("Work Flow");
	}
}
