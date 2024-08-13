package com.MobileTest;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.service.local.AppiumDriverLocalService;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.time.Duration;
import java.util.Properties;

public class base {
    public static AppiumDriverLocalService service;
	public static AndroidDriver driver;

	public AppiumDriverLocalService startServer() {
		//
		boolean flag = checkIfServerIsRunnning(4723);// port number
		if (!flag) {

			service = AppiumDriverLocalService.buildDefaultService();
			// AppiumServiceBuilder builder = new AppiumServiceBuilder();
            // builder.withIPAddress("127.0.0.1");
            // builder.usingPort(4723);
            // service = AppiumDriverLocalService.buildService(builder);
			service.start();
		}
		return service;

	}

	public static boolean checkIfServerIsRunnning(int port) {

		boolean isServerRunning = false;
		ServerSocket serverSocket;
		try {
			serverSocket = new ServerSocket(port);

			serverSocket.close();
		} catch (IOException e) {
			// If control comes here, then it means that the port is in use
			isServerRunning = true;
		} finally {
			serverSocket = null;
		}
		return isServerRunning;
	}

	public static void startEmulator() throws IOException, InterruptedException {

		Runtime.getRuntime().exec(System.getProperty("user.dir") + "\\src\\main\\resources\\startEmulator.bat");
		Thread.sleep(10000);
	}

	public static AndroidDriver capabilities(String appName) throws IOException, InterruptedException {

		FileInputStream fis = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\global.properties");
		Properties prop = new Properties();
		prop.load(fis);

		File appDir = new File("src");
		File app = new File(appDir, (String) prop.get(appName));

		// DesiredCapabilities capabilities = new DesiredCapabilities();
		String device = (String) prop.get("device");
		// String device = System.getProperty("device");

		if (device.contains("emulator")) {
			startEmulator();
		}

		// capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, device);

		// capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "uiautomator2");
		// capabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 14);

		// capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		// driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
		// driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		UiAutomator2Options options = new UiAutomator2Options()
		.setDeviceName(device)
		.setPlatformName("Android")
		.setAutomationName(AutomationName.ANDROID_UIAUTOMATOR2)
		.setAutoGrantPermissions(true) 
		.setNewCommandTimeout(Duration.ofSeconds(50))
		.setAdbExecTimeout(Duration.ofSeconds(50))
		.setApp(app.getAbsolutePath());

		driver = new AndroidDriver(service.getUrl(), options);
		//driver = new AndroidDriver(new URL("http://127.0.0.1:4723"), options);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

		return driver;
	}

	public static void getScreenshot(String s) throws IOException {
		File scrfile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrfile, new File(System.getProperty("user.dir") + "\\" + s + ".png"));
	}
}