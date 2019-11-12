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
  	public void CPCADchannel() {
  		System.out.println(" ! ----- CPCADchannel Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("CPC �����ü")) {
			System.out.println(" *** CPCADchannel pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPCADchannel pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPCADchannel summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCADchannel summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPCADchannel summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCADchannel summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�����ü", "���Լ�", "���Է�", "���ŰǼ�", "������", "�����", "�ݼۼ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** CPCADchannel statTableData check Success !! *** ");
		} else {
			System.out.println(" *** CPCADchannel statTableData check Fail ... !@#$%^&*() *** ");
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
  				System.out.println(" *** CPCADchannel cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** CPCADchannel cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  				System.out.println(" *** CPCADchannel cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** CPCADchannel cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- CPCADchannel End ----- ! ");
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
  	public void CPCTimeAvg() {
  		System.out.println(" ! ----- CPCTimeAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("CPC �ð��뺰 ���")) {
			System.out.println(" *** CPCTimeAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPCTimeAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPCTimeAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCTimeAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.00", "0.00", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPCTimeAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCTimeAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð���", "��ü�����ǰ\n      \n����(Ads)\n      \n���̹�(����Ʈ�˻�����)\n      \n���̹�(���ΰ˻�����)\n" + 
  				"      \nīī��(Ű���層��)\n      \n�׿�Ŭ��\n      \n�����߾�", "�˻��� : �˻������", "������Լ�", "���Է�", "�����ȯ��"
  				, "�����ȯ��", "������Լ�", "���Է�", "�����ȯ��"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** CPCTimeAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCTimeAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�ð� ���", "0.00", "0.00%", "0.00", "0.00%", "0.00", "0.00%", "0.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** CPCTimeAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCTimeAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- CPCTimeAvg End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void CPCDailyAvg() {
  		System.out.println(" ! ----- CPCDailyAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("CPC ���Ϻ� ���")) {
			System.out.println(" *** CPCDailyAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPCDailyAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.03 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPCDailyAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCDailyAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.27 ~ 2019.08.02", "0.00", "0.00", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPCDailyAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCDailyAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "��ü�����ǰ\n      ����(Ads)\n      ���̹�(����Ʈ�˻�����)\n      ���̹�(���ΰ˻�����)\n      īī��(Ű���層��)\n" + 
  				"      �׿�Ŭ��\n      �����߾�", "�˻��� : �˻������", "������Լ�", "���Է�", "�����ȯ��", "�����ȯ��", "������Լ�", "���Է�", "�����ȯ��"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** CPCDailyAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCDailyAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�����", "0.00", "0.00%", "0.00", "0.00%", "0.00", "0.00%", "0.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** CPCDailyAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPCDailyAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- CPCDailyAvg End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void CPMTimgAvg() {
  		System.out.println(" ! ----- CPMTimgAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("CPM �ð��뺰 ���")) {
			System.out.println(" *** CPMTimgAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPMTimgAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPMTimgAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMTimgAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.00", "0.00", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPMTimgAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMTimgAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð���", "��ü�����ǰ\n      \n���̹�(���ø�ũ)\n���̹�(�귣��˻�)\n���̹�(����ǰ�˻�)\n����Ʈ(�ٷΰ���)\n����Ʈ(����ȸ�ũ)\n" + 
  				"����Ʈ(�������ڽ�)\nīī��(���ø���Ʈ)\nīī��(�귣��˻�)\n�ڸ��ƿ������(��帵ũ)\n�ڸ��ƿ������(�����ũ)\n�ڸ��ƿ������(����ũ)\n" + 
  				"�ڸ��ƿ������(�����̾���ũ)", "�˻��� : �˻������", "������Լ�", "���Է�", "�����ȯ��", "�����ȯ��", "������Լ�", "���Է�", "�����ȯ��"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** CPMTimgAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMTimgAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�ð� ���", "0.00", "0.00%", "0.00", "0.00%", "0.00", "0.00%", "0.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** CPMTimgAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMTimgAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- CPMTimgAvg End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void CPMDailyAvg() {
  		System.out.println(" ! ----- CPMDailyAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("CPM ���Ϻ� ���")) {
			System.out.println(" *** CPMDailyAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPMDailyAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.03 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPMDailyAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMDailyAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.27 ~ 2019.08.02", "0.00", "0.00", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPMDailyAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMDailyAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "��ü�����ǰ\n      \n���̹�(���ø�ũ)\n���̹�(�귣��˻�)\n���̹�(����ǰ�˻�)\n����Ʈ(�ٷΰ���)\n����Ʈ(����ȸ�ũ)\n" + 
  				"����Ʈ(�������ڽ�)\nīī��(���ø���Ʈ)\nīī��(�귣��˻�)\n�ڸ��ƿ������(��帵ũ)\n�ڸ��ƿ������(�����ũ)\n�ڸ��ƿ������(����ũ)\n" + 
  				"�ڸ��ƿ������(�����̾���ũ)", "�˻��� : �˻������", "������Լ�", "���Է�", "�����ȯ��", "�����ȯ��", "������Լ�", "���Է�", "�����ȯ��"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** CPMDailyAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMDailyAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�����", "0.00", "100.00%", "0.00", "0.00%", "0.00", "0.00%", "0.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** CPMDailyAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMDailyAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- CPMDailyAvg End ----- ! ");
  	}
  	@Test(priority = 11)
  	public void CPMInflowIP() {
  		System.out.println(" ! ----- CPMInflowIP Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("CPM ���� IP")) {
			System.out.println(" *** CPMInflowIP pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPMInflowIP pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPMInflowIP summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMInflowIP summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0", "0.00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPMInflowIP summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMInflowIP summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "��ü IP \n        \n[ A ] Ŭ����\n        \n[ B ] Ŭ����\n        \n[ C ] Ŭ����\n        \n[ D ] Ŭ����"
  				, "�����ǰ", "�����ü", "�˻���", "����", "�����Ͻ�", "��������", "����"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** CPMInflowIP statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMInflowIP statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** CPMInflowIP statTableData check Success !! *** ");
		} else {
			System.out.println(" *** CPMInflowIP statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_7").click();
  		$("#cell_7").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** CPMInflowIP cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** CPMInflowIP cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("ȸ��/���")) {
  				System.out.println(" *** CPMInflowIP cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** CPMInflowIP cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- CPMInflowIP End ----- ! ");
  	}
  	@Test(priority = 12)
  	public void CPMlandingPage() {
  		System.out.println(" ! ----- CPMlandingPage Start ----- ! ");
  		$(".active > ul > li > a > span", 11).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 11).click();
  		if($("h3", 0).text().trim().equals("CPM ���������� ��Ż")) {
			System.out.println(" *** CPMlandingPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** CPMlandingPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** CPMlandingPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMlandingPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** CPMlandingPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMlandingPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "Unique ID", "IP", "�����ǰ", "�����ü", "�˻���", "����������"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** CPMlandingPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** CPMlandingPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** CPMlandingPage statTableData check Success !! *** ");
		} else {
			System.out.println(" *** CPMlandingPage statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- CPMlandingPage End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}