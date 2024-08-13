package com.MobileTest;

import java.io.IOException;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
// import io.appium.java_client.android.AndroidElement;

import com.MobileTest.pages.HomePage;
import com.MobileTest.pages.Preferences;
import com.MobileTest.utils.TestData;

public class ApiDemoTest extends base {

    @Test(dataProvider="InputData", dataProviderClass=TestData.class)
	public void apiDemo(String input) throws IOException, InterruptedException {
		service=startServer();
	

		AndroidDriver driver = capabilities("apiDemo");
		//driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		//driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// xpath id className, androidUIautomator
		/*
		 * xpath Syntax //tagName[@attribute='value']
		 * 
		 * 
		 */
		HomePage h = new HomePage(driver);

		h.Preferences.click();

		Preferences p = new Preferences(driver);
		// driver.findElementByXPath("//android.widget.TextView[@text='3. Preference
		// dependencies']").click();
		p.dependencies.click();
		// driver.findElementById("android:id/checkbox").click();
		//driver.findElement(AppiumBy.accessibilityId("android:id/checkbox")).click();
		p.checkBox.click();
		// driver.findElementByXPath("(//android.widget.RelativeLayout)[2]").click();
		driver.findElement(AppiumBy.xpath("(//android.widget.RelativeLayout)[2]")).click();;
		// driver.findElementByClassName("android.widget.EditText").sendKeys(input);
		driver.findElement(AppiumBy.className("android.widget.EditText")).sendKeys(input);;
		// driver.findElementsByClassName("android.widget.Button").get(1).click();
		p.buttons.get(1).click();

		service.stop();
	}

	@BeforeTest
	public void killAllNodes() throws IOException, InterruptedException {
		// taskkill /F /IM node.exe
		Runtime.getRuntime().exec("taskkill /F /IM node.exe");
		Thread.sleep(3000);

	}
}
