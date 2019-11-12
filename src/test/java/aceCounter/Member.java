package aceCounter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.codeborne.selenide.WebDriverRunner;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.WebDriverRunner.*;

public class Member {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, temp_pw, A, B, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;
	
	Date date = new Date();
    SimpleDateFormat date_format = new SimpleDateFormat("YYYYMMddHHmmss");
    String id_date = date_format.format(date);
	
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://www.acecounter.com/www2/main.amz";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "ap";
		pw = "qordlf";
		pw1 = "qordlf";
		A = "!@34";
		B = "12";
		domain = "apzz";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
			TestBrowser = "chrome";
			/*ChromeOptions options = new ChromeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			//cap = DesiredCapabilities.firefox();
			//RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*EdgeOptions options = new EdgeOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*InternetExplorerOptions options = new InternetExplorerOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);*/
			cap = DesiredCapabilities.internetExplorer();
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			cap.setCapability(InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			cap.setCapability("nativeEvents", false);    
	        cap.setCapability("unexpectedAlertBehaviour", "accept");
	        cap.setCapability("ignoreProtectedModeSettings", true);
	        cap.setCapability("requireWindowFocus", true);
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().maximize();
		}
	}
	// �Էµ� URL ���� ���� Ȯ��
	public static boolean linkCheck(String urlName, String urlLink) {
		try {
			huc = (HttpURLConnection) (new URL(urlLink).openConnection());
			huc.setRequestMethod("HEAD");
			huc.connect();
			respCode = huc.getResponseCode();
			if (respCode >= 400) {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " - check Fail ... !@#$%^&*() *** ");
				close();
			} else {
				System.out.println("***** " + urlName + " : Link Status HTTP : " + respCode + " - check Success !! *** ");
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	@SuppressWarnings("unused")
	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}
  	@Test(priority = 0)
  	public void login() {
  		System.out.println(" ! ----- login Start ----- ! ");
		open(baseUrl);
		$(".text").waitUntil(visible, 10000);
		$(".text").setValue("apzz092888");
		$(".password").setValue(pw+A);
		$(".login_btn").click();
		pageLoadCheck = $(".id").text().trim();
		if(pageLoadCheck.equals("�����̸�")) {
			System.out.println(" *** login check Success !! *** ");
		} else {
			System.out.println(" *** login check Fail ... !@#$%^&*() *** ");
			System.out.println($(".id").text().trim());
			close();
		}
  		$(".btn_blue", 0).click();
  		$("#contents_top").waitUntil(visible, 10000);
  		System.out.println(" ! ----- login End ----- ! ");
  	}
  	@Test(priority = 1)
  	public void signupTermination() {
  		System.out.println(" ! ----- signupTermination Start ----- ! ");
  		$("#m_stat > ul > li > a", 12).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("ȸ������/����")) {
			System.out.println(" *** signupTermination pageLoad Success !! *** ");
		} else {
			System.out.println(" *** signupTermination pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".myValue", 1).click();
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('2019-08-08')");
  		js("$j('#calendar_data2').val('2019-08-09')");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮��", "���Լ�", "���Է�", "������"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** signupTermination summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n���", "0", "-", "0.00%", "-", "0", "-", "0.00%"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** signupTermination summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%", "0", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** signupTermination summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�湮��", "���Լ�", "���Է�", "������"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** signupTermination statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 00��", "0", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** signupTermination statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** signupTermination statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_5").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_5").click();
  		$("#cell_5").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("������")) {
  				System.out.println(" *** signupTermination cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_5").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_5").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("������")) {
  				System.out.println(" *** signupTermination cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("ȸ������/���� > ȸ������ ��")) {
			System.out.println(" *** signupTermination pageLoad Detail Success !! *** ");
		} else {
			System.out.println(" *** signupTermination pageLoad Detail Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] summaryTopDetailTableData = {"ȸ�� ���Լ�"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopDetailTableData[i])) {
  				System.out.println(" *** signupTermination summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryDetailTableData = {"0"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryDetailTableData[i])) {
  				System.out.println(" *** signupTermination summaryDetailTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination summaryDetailTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statDetailTopTableData = {"����", "ȸ��ID", "�湮�Ͻ�", "��ȸ �湮�� ����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statDetailTopTableData[i])) {
  				System.out.println(" *** signupTermination statDetailTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statDetailTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** signupTermination statDetailFootRight Detail Success !! *** ");
		} else {
			System.out.println(" *** signupTermination statDetailFootRight Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("ȸ������/����")) {
  			System.out.println(" *** signupTermination window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** signupTermination window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- signupTermination End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void newVisitVSreVisitSignup() {
  		System.out.println(" ! ----- newVisitVSreVisitSignup Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("�űԹ湮vs��湮����")) {
			System.out.println(" *** newVisitVSreVisitSignup pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newVisitVSreVisitSignup pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�űԹ湮", "�湮��", "���Լ�", "���Է�", "�湮��", "���Լ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "36", "300.00%\n���", "0", "-", "0.00%", "-", "2", "���", "0", "-", "0.00%"};
  		for(int i=0;i<=11;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%", "0", "0", "0.00%"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�űԹ湮", "��湮", "�湮��"
  				, "���Լ�", "���Է�", "�湮��", "���Լ�"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "0", "0.00%", "0", "0", "2019.08.09 (��)", "28", "0", "0.00%", "2", "0"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+8)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+8)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "36", "0", "0.00%", "2", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- newVisitVSreVisitSignup End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void loginCount() {
  		System.out.println(" ! ----- loginCount Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�α��� Ƚ��")) {
			System.out.println(" *** loginCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** loginCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮��", "�α��� Ƚ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** loginCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n���", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** loginCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** loginCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�湮��", "�α��� Ƚ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** loginCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "0", "2019.08.09 (��)", "30", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** loginCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** loginCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_3").click();
  		$("#cell_3").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�α��� Ƚ��")) {
  				System.out.println(" *** loginCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�α�����")) {
  				System.out.println(" *** loginCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
		switchTo().window(2); 
		if($("h3", 0).text().trim().equals("�α��� Ƚ�� > ȸ�� ��")) {
			System.out.println(" *** loginCount pageLoad Progress Success !! *** "); }
		else { 
			System.out.println(" *** loginCount pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim()); close(); 
		} 
		String[] summaryTopDetailTableData = {"�α��� Ƚ��"};
		for(int i=0;i<=0;i++) {
		    if($(".summaryTopCenter", i).text().trim().equals(summaryTopDetailTableData[i])) {
		        System.out.println(" *** loginCount summaryTopTableData(" + i + ") check Success !! *** ");
		    } else {
		        System.out.println(" *** loginCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
		        System.out.println($(".summaryTopCenter", i).text().trim());
		        close();
		    }
		}
		String[] summaryDetailTableData = {"0"};
		for(int i=0;i<=0;i++) {
		    if($(".summaryDataCenter", i).text().trim().equals(summaryDetailTableData[i])) {
		        System.out.println(" *** loginCount summaryDetailTableData(" + i + ") check Success !! *** ");
		    } else {
		        System.out.println(" *** loginCount summaryDetailTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
		        System.out.println($(".summaryDataCenter", i).text().trim());
		        close();
		    }
		}
		String[] statDetailTopTableData = {"����", "ȸ��ID", "�α����Ͻ�", "���ɴ�", "����"};
		for(int i=0;i<=4;i++) {
		    if($(".statDataCenter", i).text().trim().equals(statDetailTopTableData[i])) {
		        System.out.println(" *** loginCount statDetailTopTableData(" + i + ") check Success !! *** ");
		    } else {
		        System.out.println(" *** loginCount statDetailTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
		        System.out.println($(".statDataCenter", i).text().trim());
		        close();
		    }
		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** loginCount statFootRight Detail Success !! *** ");
		} else {
			System.out.println(" *** loginCount statFootRight Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		switchTo().window(0);
		if($("h3", 0).text().trim().equals("�α��� Ƚ��")) {
			System.out.println(" *** loginCount window(0) check Success !! *** ");
		} else {
			System.out.println(" *** loginCount window(0) check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- loginCount End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void loginVisitDetail() {
  		System.out.println(" ! ----- loginVisitDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("�α��� �湮 ��")) {
			System.out.println(" *** loginVisitDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** loginVisitDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�α��� Ƚ��", "�ֿ� �α��νð���", "�ֿ� �α��ο��ɴ�"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** loginVisitDetail summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginVisitDetail summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "-", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** loginVisitDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginVisitDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "-", "-"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** loginVisitDetail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginVisitDetail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "ȸ��ID", "�α��� �Ͻ�", "���ɴ�", "����"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** loginVisitDetail statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginVisitDetail statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}	
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** loginVisitDetail statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** loginVisitDetail statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_5").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("���籸���� �⺻������ �����ϴ�!");
		$("#cell_check_5").click();
		$("#cell_5").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("����")) {
				System.out.println(" *** loginVisitDetail cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** loginVisitDetail cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_5").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
		$("#cell_5").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("������ó")) {
				System.out.println(" *** loginVisitDetail cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** loginVisitDetail cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- loginVisitDetail End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void memberAttribute() {
  		System.out.println(" ! ----- memberAttribute Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("ȸ�� Ư��")) {
			System.out.println(" *** memberAttribute pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberAttribute pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�α���Ƚ��", "�ִ� �α��� ����", "����", "�α���Ƚ��", "����", "���ɴ�", "�α���Ƚ��"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** memberAttribute summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttribute summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "-", "0", "0.00%", "", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** memberAttribute summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttribute summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "-", "0", "0.00%", "", "0"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** memberAttribute summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttribute summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"���ɴ� vs ����\n ȸ�� �׷�� vs ����", "�Ұ�", "�α��� Ƚ��", "�α��κ���", "�α���Ƚ��", "�α��κ���", "����", "����"
  				, "�˼�����", "����", "����"};
  		for(int i=0;i<=10;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** memberAttribute statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttribute statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}	
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** memberAttribute statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** memberAttribute statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- memberAttribute End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void memberLogin() {
  		System.out.println(" ! ----- memberLogin Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("ȸ���� �α�����Ȳ")) {
			System.out.println(" *** memberLogin pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberLogin pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�α��� Ƚ��", "���ü���ð�"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** memberLogin summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberLogin summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "00:00:00", "-", "0.00"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** memberLogin summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberLogin summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "00:00:00", "0.00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** memberLogin summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberLogin summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"ȸ��ID", "�α��� Ƚ��", "���ü���ð�", "�湮�� ��������", "����", "���ɴ�"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** memberLogin statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberLogin statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}	
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** memberLogin statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** memberLogin statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_7").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("���籸���� �⺻������ �����ϴ�!");
		$("#cell_check_7").click();
		$("#cell_7").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("ȸ�� �׷��")) {
				System.out.println(" *** memberLogin cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** memberLogin cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_7").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
		$("#cell_7").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("ȥ�ο���")) {
				System.out.println(" *** memberLogin cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** memberLogin cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- memberLogin End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void loginTerm() {
  		System.out.println(" ! ----- loginTerm Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("�α��� ����")) {
			System.out.println(" *** loginTerm pageLoad Success !! *** ");
		} else {
			System.out.println(" *** loginTerm pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�ֿ� �α��� ����"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** loginTerm summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginTerm summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** loginTerm summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginTerm summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** loginTerm summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginTerm summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�α��� ����", "�α��� Ƚ��"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** loginTerm statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginTerm statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}	
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** loginTerm statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** loginTerm statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_2").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("���籸���� �⺻������ �����ϴ�!");
		$("#cell_check_2").click();
		$("#cell_2").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("�α��� Ƚ��")) {
				System.out.println(" *** loginTerm cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** loginTerm cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_2").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
		$("#cell_2").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("�α��� ����")) {
				System.out.println(" *** loginTerm cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** loginTerm cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- loginTerm End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}