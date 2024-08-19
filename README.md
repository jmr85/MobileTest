# MobileTest

## Run test
```cmd
mvn test
```
## Steps to run appium-inspector

### start emulator
```cmd
cd %ANDROID_HOME%\emulator && emulator -avd Pixel_Tablet_API_35
```
### start appium
```cmd
appium server
```
### open appium-inspector app, configure capabilities and start session

capability builder
```json
{ 
	"platformName": "Android", 
	"appium:options": { 
		"automationName": "UiAutomator2", 
		"platformVersion": "15.0",
		"app": "C:\\Users\\{user_name}\\eclipse-workspace\\MobileTest\\src\\ApiDemos-debug.apk", 
        "deviceName": "Pixel_Tablet_API_35", 
		"noReset": true 
	} 
}
```