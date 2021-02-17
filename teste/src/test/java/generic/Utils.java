package generic;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Utils extends BaseClass
{
	public static String captureScreenshot(WebDriver driver,String name)
	{
		TakesScreenshot ts=(TakesScreenshot)driver;
		String ds = ts.getScreenshotAs(OutputType.BASE64);
		return ds;
	}
}
