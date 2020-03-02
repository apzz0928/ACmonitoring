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

public class searchADEffect {
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
		//hubUrl = "http://10.0.75.1:5555/wd/hub";
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
  	public void searchADProduct() {
  		System.out.println(" ! ----- searchADProduct Start ----- ! ");
  		$("#m_stat > ul > li > a", 5).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("�˻����� ��ǰ��")) {
			System.out.println(" *** searchADProduct pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchADProduct pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "���ŰǼ�", "������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** searchADProduct summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProduct summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchADProduct summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProduct summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchADProduct summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProduct summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�˻����� ��ǰ��", "���Լ�", "���Է�", "���ŰǼ�", "������", "�����", "�ݼۼ�"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchADProduct statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProduct statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** searchADProduct statTableData check Success !! *** ");
		} else {
			System.out.println(" *** searchADProduct statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 1).click();
		$("#cell_check_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("���籸���� �⺻������ �����ϴ�!");
		$("#cell_check_13").click();
		$("#cell_13").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("�ݼۼ�")) {
				System.out.println(" *** searchADProduct cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** searchADProduct cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 1).click();
		$("#cell_check_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
		$("#cell_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("�ݼ۷�")) {
				System.out.println(" *** searchADProduct cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** searchADProduct cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- searchADProduct End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void searchADsearchKeyword() {
  		System.out.println(" ! ----- searchADsearchKeyword Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("�˻����� �˻��")) {
			System.out.println(" *** searchADsearchKeyword pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchADsearchKeyword pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "���ŰǼ�", "������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** searchADsearchKeyword summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADsearchKeyword summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchADsearchKeyword summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADsearchKeyword summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchADsearchKeyword summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADsearchKeyword summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�˻���", "���Լ�", "���Է�", "���ŰǼ�", "������", "�����", "�ݼۼ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchADsearchKeyword statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADsearchKeyword statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** searchADsearchKeyword statTableData check Success !! *** ");
		} else {
			System.out.println(" *** searchADsearchKeyword statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 1).click();
		$("#cell_check_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("���籸���� �⺻������ �����ϴ�!");
		$("#cell_check_13").click();
		$("#cell_13").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("�ݼۼ�")) {
				System.out.println(" *** searchADsearchKeyword cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** searchADsearchKeyword cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 1).click();
		$("#cell_check_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
		$("#cell_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("�ݼ۷�")) {
				System.out.println(" *** searchADsearchKeyword cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** searchADsearchKeyword cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- searchADsearchKeyword End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void searchADProductKeyword() {
  		System.out.println(" ! ----- searchADProductKeyword Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�˻����� ��ǰ/�˻���")) {
			System.out.println(" *** searchADProductKeyword pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchADProductKeyword pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "���ŰǼ�", "������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** searchADProductKeyword summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProductKeyword summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchADProductKeyword summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProductKeyword summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchADProductKeyword summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProductKeyword summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�����ǰ-�˻���", "���Լ�", "���Է�", "���ŰǼ�", "������", "�����", "�ݼۼ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchADProductKeyword statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProductKeyword statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** searchADProductKeyword statTableData check Success !! *** ");
		} else {
			System.out.println(" *** searchADProductKeyword statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_13").click();
  		$("#cell_13").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼۼ�")) {
  				System.out.println(" *** searchADProductKeyword cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProductKeyword cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼ۷�")) {
  				System.out.println(" *** searchADProductKeyword cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** searchADProductKeyword cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- searchADProductKeyword End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void searchADmedia() {
  		System.out.println(" ! ----- searchADmedia Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("�˻������ü")) {
			System.out.println(" *** searchADmedia pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchADmedia pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "���ŰǼ�", "������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** searchADmedia summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADmedia summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchADmedia summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADmedia summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchADmedia summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADmedia summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�����ü", "���Լ�", "���Է�", "���ŰǼ�", "������", "�����", "�ݼۼ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchADmedia statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADmedia statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** searchADmedia statTableData check Success !! *** ");
		} else {
			System.out.println(" *** searchADmedia statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_13").click();
  		$("#cell_13").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼۼ�")) {
  				System.out.println(" *** searchADmedia cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** searchADmedia cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼ۷�")) {
  				System.out.println(" *** searchADmedia cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** searchADmedia cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- searchADmedia End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void naverSiteSearchAD() {
  		System.out.println(" ! ----- naverSiteSearchAD Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("���̹� ����Ʈ�˻�����")) {
			System.out.println(" *** naverSiteSearchAD pageLoad Success !! *** ");
		} else {
			System.out.println(" *** naverSiteSearchAD pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "���ŰǼ�", "������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** naverSiteSearchAD summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** naverSiteSearchAD summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** naverSiteSearchAD summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** naverSiteSearchAD summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** naverSiteSearchAD summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** naverSiteSearchAD summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"������", "���Լ�", "���Է�", "���ŰǼ�", "������", "�����", "�ݼۼ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** naverSiteSearchAD statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** naverSiteSearchAD statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** naverSiteSearchAD statTableData check Success !! *** ");
		} else {
			System.out.println(" *** naverSiteSearchAD statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_13").click();
  		$("#cell_13").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼۼ�")) {
  				System.out.println(" *** naverSiteSearchAD cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** naverSiteSearchAD cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_13").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼ۷�")) {
  				System.out.println(" *** naverSiteSearchAD cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** naverSiteSearchAD cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- naverSiteSearchAD End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void brandSearch() {
  		System.out.println(" ! ----- brandSearch Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("�귣��˻� ��")) {
			System.out.println(" *** brandSearch pageLoad Success !! *** ");
		} else {
			System.out.println(" *** brandSearch pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$("#no_newmenu").click();
  		$("div > a > img", 0).click();
  		$("div > a > img", 0).waitUntil(hidden, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "��ȯ��", "��ȯ��"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** brandSearch summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** brandSearch summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** brandSearch summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** brandSearch summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** brandSearch summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** brandSearch summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"��ũ����", "���Լ�", "���Է�", "�ݼۼ�", "�ݼ۷�", "��ȯ��", "��ȯ��", "���ü���ð�"
  				, "��ü\n��ȯ-����\n��ȯ-�ֹ�\n��ȯ-����\n��ȯ-��û\n��ȯ-��Ÿ1\n��ȯ-��Ÿ2\n��ȯ-��Ÿ3", "��ü"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** brandSearch statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** brandSearch statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** brandSearch statTableData check Success !! *** ");
		} else {
			System.out.println(" *** brandSearch statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("���ü���ð�")) {
  				System.out.println(" *** brandSearch cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** brandSearch cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("���Դ���������")) {
  				System.out.println(" *** brandSearch cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** brandSearch cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- brandSearch End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void searchADTimeAvg() {
  		System.out.println(" ! ----- searchADTimeAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("�˻����� �ð��뺰 ���")) {
			System.out.println(" *** searchADTimeAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchADTimeAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�ð� ������Լ�", "�ð� �����ȯ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** searchADTimeAvg summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADTimeAvg summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchADTimeAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADTimeAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.00", "0.00", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchADTimeAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADTimeAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð���", "������Լ�", "���Է�", "�����ȯ��"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchADTimeAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADTimeAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�ð� ���", "0.00", "0.00%", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** searchADTimeAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADTimeAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- searchADTimeAvg End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void searchADDailyAvg() {
  		System.out.println(" ! ----- searchADDailyAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("�˻����� ���Ϻ� ���")) {
			System.out.println(" *** searchADDailyAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchADDailyAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�� ������Լ�", "�� �����ȯ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** searchADDailyAvg summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADDailyAvg summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.03 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchADDailyAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADDailyAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.27 ~ 2019.08.02", "0.00", "0.00", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchADDailyAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADDailyAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "������Լ�", "���Է�", "�����ȯ��"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchADDailyAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADDailyAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�����", "0.00", "0.00%", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** searchADDailyAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchADDailyAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- searchADDailyAvg End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void inflowHistory() {
  		System.out.println(" ! ----- inflowHistory Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("���� ����")) {
			System.out.println(" *** inflowHistory pageLoad Success !! *** ");
		} else {
			System.out.println(" *** inflowHistory pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "Unique ID ��", "IP ��"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** inflowHistory summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowHistory summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** inflowHistory summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowHistory summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0", "0.00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** inflowHistory summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowHistory summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "Unique ID", "IP", "�����ǰ", "�����ü", "�˻���", "�����Ͻ�", "��������", "����"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** inflowHistory statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowHistory statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** inflowHistory statTableData check Success !! *** ");
		} else {
			System.out.println(" *** inflowHistory statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- inflowHistory End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void duplicateInflowInterval() {
  		System.out.println(" ! ----- duplicateInflowInterval Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("�ߺ����� ����")) {
			System.out.println(" *** duplicateInflowInterval pageLoad Success !! *** ");
		} else {
			System.out.println(" *** duplicateInflowInterval pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "Unique ID ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** inflowHistory summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowHistory summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** duplicateInflowInterval summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** duplicateInflowInterval summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** duplicateInflowInterval summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** duplicateInflowInterval summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "Unique ID", "IP ��", "���Լ�/\n��������", "�Ⱓ�� ����\n�����Ͻ�", "�Ⱓ�� ���� ���� �� �ߺ����� ����"
  				, "�հ�", "���\n��������", "~10��\n�̳�", "~30��\n�̳�", "~1�ð�\n�̳�", "~3�ð�\n�̳�", "~6�ú�\n�̳�", "6�ð�\n����"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** duplicateInflowInterval statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** duplicateInflowInterval statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** duplicateInflowInterval statTableData check Success !! *** ");
		} else {
			System.out.println(" *** duplicateInflowInterval statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- duplicateInflowInterval End ----- ! ");
  	}
  	@Test(priority = 11)
  	public void IPChangeHistory() {
  		System.out.println(" ! ----- IPChangeHistory Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("IP ���� ����")) {
			System.out.println(" *** IPChangeHistory pageLoad Success !! *** ");
		} else {
			System.out.println(" *** IPChangeHistory pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "Unique ID ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** IPChangeHistory summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** IPChangeHistory summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** IPChangeHistory summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** IPChangeHistory summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** IPChangeHistory summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** IPChangeHistory summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "Unique ID", "���Լ�"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** IPChangeHistory statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** IPChangeHistory statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** IPChangeHistory statTableData check Success !! *** ");
		} else {
			System.out.println(" *** IPChangeHistory statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- IPChangeHistory End ----- ! ");
  	}
  	@Test(priority = 12)
  	public void suspicionInflow() {
  		System.out.println(" ! ----- suspicionInflow Start ----- ! ");
  		$(".active > ul > li > a > span", 11).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 11).click();
  		if($("h3", 0).text().trim().equals("�ǽ����� �м�")) {
			System.out.println(" *** suspicionInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** suspicionInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "Unique ID ��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** suspicionInflow summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** suspicionInflow summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** suspicionInflow summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** suspicionInflow summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** suspicionInflow summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** suspicionInflow summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "Unique ID", "���� �����Ͻ�", "���� ���Լ�", "�Ⱓ�� ���Լ�", "�ǽ� ���Լ�"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** suspicionInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** suspicionInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** suspicionInflow statTableData check Success !! *** ");
		} else {
			System.out.println(" *** suspicionInflow statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- suspicionInflow End ----- ! ");
  	}
  	@Test(priority = 13)
  	public void searchKeyword() {
  		System.out.println(" ! ----- searchKeyword Start ----- ! ");
  		$(".active > ul > li > a > span", 12).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 12).click();
  		if($("h3", 0).text().trim().equals("�˻��� ����͸�")) {
			System.out.println(" *** searchKeyword pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchKeyword pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"����", "�����ǰ", "�˻���", "������", "(�ֱ� 30��)\n����� ���Լ�", "����͸� ����", "�ʰ��Ͻ�", "������ ���Լ�"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** searchKeyword statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchKeyword statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** searchKeyword statTableData check Success !! *** ");
		} else {
			System.out.println(" *** searchKeyword statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- searchKeyword End ----- ! ");
  	}
  	@Test(priority = 14)
  	public void landingPageLeave() {
  		System.out.println(" ! ----- landingPageLeave Start ----- ! ");
  		$(".active > ul > li > a > span", 13).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 13).click();
  		if($("h3", 0).text().trim().equals("���������� ��Ż")) {
			System.out.println(" *** landingPageLeave pageLoad Success !! *** ");
		} else {
			System.out.println(" *** landingPageLeave pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "��Ż��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** landingPageLeave summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageLeave summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** landingPageLeave summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageLeave summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** landingPageLeave summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageLeave summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "Unique ID", "IP", "�����ǰ", "�����ü", "�˻���", "����������"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** landingPageLeave statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageLeave statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** landingPageLeave statTableData check Success !! *** ");
		} else {
			System.out.println(" *** landingPageLeave statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- landingPageLeave End ----- ! ");
  	}
  	@Test(priority = 15)
  	public void landingPageCompare() {
  		System.out.println(" ! ----- landingPageCompare Start ----- ! ");
  		$(".active > ul > li > a > span", 14).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 14).click();
  		if($("h3", 0).text().trim().equals("���������� ��")) {
			System.out.println(" *** landingPageCompare pageLoad Success !! *** ");
		} else {
			System.out.println(" *** landingPageCompare pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���Լ�", "���ŰǼ�", "������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** landingPageCompare summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageCompare summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** landingPageCompare summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageCompare summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** landingPageCompare summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageCompare summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����������", "���Լ�", "���Է�", "���ŰǼ�", "������", "�ݼۼ�"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** landingPageCompare statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageCompare statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** landingPageCompare statTableData check Success !! *** ");
		} else {
			System.out.println(" *** landingPageCompare statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_8").click();
  		$("#cell_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("���Դ���������")) {
  				System.out.println(" *** landingPageCompare cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageCompare cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_8").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�ݼ۷�")) {
  				System.out.println(" *** landingPageCompare cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** landingPageCompare cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- landingPageCompare End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}