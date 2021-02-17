package generic;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ReadProperties 
{
	public Properties pro;

	public ReadProperties() 
	{
		try {
			
			File src = new File(System.getProperty("user.dir")+"/src/test/resources/config.properties");
			
			FileInputStream fis = new FileInputStream(src);

			pro = new Properties();
			
			pro.load(fis);
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public String getUrl()
	{
		return pro.getProperty("url");	
	}
	
	public String getMailPassword()
	{
		return pro.getProperty("reportmailPassword");
	}
	
	public String getMailId()
	{
		return pro.getProperty("reportmailId");
	}
	
	public String getLogInMailId()
	{
		return pro.getProperty("loginmailId");
	}
	
	public String getLogInPassword()
	{
		return pro.getProperty("loginpassword");
	}
	
	public String getAddToMail()
	{
		return pro.getProperty("addtomail");
	}
	
	public String getAddFromMail()
	{
		return pro.getProperty("addfrommail");
	}
	
	public String getAddToName()
	{
		return pro.getProperty("addToName");
	}
	
	public String getAddFromName()
	{
		return pro.getProperty("addFromName");
	}
	
	public String getMailSubject()
	{
		return pro.getProperty("reportSub");
	}
	
	public String getMailMsg()
	{
		return pro.getProperty("reportMsg");
	}
	
	public String getMailHostName()
	{
		return pro.getProperty("mailhostname");
	}
	
	public String getMailPort()
	{
		return pro.getProperty("mailPort");
	}
}

