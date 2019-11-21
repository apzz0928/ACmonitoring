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

public class Purchaser {
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
		hubUrl = "http://10.0.75.1:5555/wd/hub";
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
  	public void purchaseAnalysis() {
  		System.out.println(" ! ----- purchaseAnalysis Start ----- ! ");
  		$("#m_stat > ul > li > a", 15).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("���źм�")) {
			System.out.println(" *** purchaseAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** purchaseAnalysis pageLoad Fail ... !@#$%^&*() *** ");
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
  		$(".statTableHeaderText > select").selectOption("�ϰ����κ���");
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�����", "���湮��", "���ŰǼ�"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** purchaseAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "36", "300.00%\n���", "0", "-", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** purchaseAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "9", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** purchaseAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�����", "���湮��"
  				, "���ŰǼ�", "���ź���", "������", "�ֱ�1������� ���ŰǼ�\n        \n�ֱ�3������� ���ŰǼ�\n        \n�ֱ�1����� ���ŰǼ�"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** purchaseAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "0", "8", "0", "0.00%", "0.00%", "0", "2019.08.09 (��)", "0", "28", "0", "0.00%", "0.00%", "0"};
  		for(int i=0;i<=13;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** purchaseAnalysis statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "0(0.00)", "36(18.00)", "0(0.00)", "0.00%", "0.00%", "0(0.00)"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** purchaseAnalysis statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
				System.out.println(" *** purchaseAnalysis cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** purchaseAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("���")) {
				System.out.println(" *** purchaseAnalysis cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** purchaseAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
  		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("���źм� > ���� ��")) {
			System.out.println(" *** purchaseAnalysis pageLoad Detail Success !! *** ");
		} else {
			System.out.println(" *** purchaseAnalysis pageLoad Detail Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] DetailSummaryTableData = {"0", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(DetailSummaryTableData[i])) {
  				System.out.println(" *** purchaseAnalysis Detail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis Detail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** purchaseAnalysis Detail statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** purchaseAnalysis Detail statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("���źм�")) {
  			System.out.println(" *** purchaseAnalysis window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** purchaseAnalysis window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- purchaseAnalysis End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void newReVisitPurchaser() {
  		System.out.println(" ! ----- newReVisitPurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("�űԹ湮�� ��湮����")) {
			System.out.println(" *** newReVisitPurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newReVisitPurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�űԹ湮", "���ŰǼ�", "�����", "���ŰǼ�"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�űԹ湮", "��湮"
  				, "�湮��", "���ŰǼ�", "������", "�����", "�湮��", "���ŰǼ�", "������"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "0", "0.00%", "0", "0", "0", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+10)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+10)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "36(18.00)", "0(0.00)", "0.00%", "0(0.00)", "2(1.00)", "0(0.00)", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- newReVisitPurchaser End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void visitVSpurchaser() {
  		System.out.println(" ! ----- visitVSpurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�湮��vs���ż�")) {
			System.out.println(" *** visitVSpurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitVSpurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮��", "���ŰǼ�"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** visitVSpurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n���", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitVSpurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitVSpurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�湮��", "���ŰǼ�"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitVSpurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "0", "2019.08.09 (��)", "30", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitVSpurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38(19.00)", "0(0.00)"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitVSpurchaser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("���ŰǼ�")) {
  				System.out.println(" *** visitVSpurchaser cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("������")) {
  				System.out.println(" *** visitVSpurchaser cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] BarChartData = {"2019.08.08 (��)", "�湮��: 8", "2019.08.09 (��)", "�湮��: 30"};
  		for(int i=0,y=0;i<=1;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  	  				System.out.println(" *** visitVSpurchaser BarChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** visitVSpurchaser BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		System.out.println(" ! ----- visitVSpurchaser End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void newRePurchaser() {
  		System.out.println(" ! ----- newRePurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("�űԱ��ſ� �籸��")) {
			System.out.println(" *** newRePurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newRePurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�űԱ���", "���ŰǼ�", "�����", "���ŰǼ�"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newRePurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newRePurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newRePurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�űԱ���", "�籸��"
  				, "���ż���", "���ŰǼ�", "�����", "���ż���", "���ŰǼ�"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newRePurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "0", "0", "0", "0", "0", "2019.08.09 (��)", "0", "0", "0", "0", "0"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+8)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newRePurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+8)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "0(0.00)", "0(0.00)", "0(0.00)", "0(0.00)", "0(0.00)"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newRePurchaser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- newRePurchaser End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void purchaserTime() {
  		System.out.println(" ! ----- purchaserTime Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("���ű��� �ɸ��ð�")) {
			System.out.println(" *** purchaserTime pageLoad Success !! *** ");
		} else {
			System.out.println(" *** purchaserTime pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���ŰǼ�"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** purchaserTime summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** purchaserTime summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** purchaserTime summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ҿ�ð�", "���ŰǼ�", "���ź���", "�����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** purchaserTime statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"�ҿ�ð�", "���ŰǼ�", "���ź���", "�����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** purchaserTime statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** purchaserTime statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** purchaserTime statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
  				System.out.println(" *** purchaserTime cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("����׺���")) {
  				System.out.println(" *** purchaserTime cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- purchaserTime End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void rePurchaserTerm() {
  		System.out.println(" ! ----- rePurchaserTerm Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("�籸���ֱ�")) {
			System.out.println(" *** rePurchaserTerm pageLoad Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���ŰǼ�"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** rePurchaserTerm summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** rePurchaserTerm summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** rePurchaserTerm summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ҿ�Ⱓ", "���ŰǼ�", "���ź���", "�����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** rePurchaserTerm statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
			System.out.println(" *** rePurchaserTerm cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("����׺���")) {
			System.out.println(" *** rePurchaserTerm cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- rePurchaserTerm End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void memberNoMemberPurchaser() {
  		System.out.println(" ! ----- memberNoMemberPurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("ȸ��vs��ȸ�� ����")) {
			System.out.println(" *** memberNoMemberPurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberNoMemberPurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "ȸ��", "���ŰǼ�", "�����", "���ŰǼ�"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�Ͻ�", "ȸ��", "��ȸ��", "���ŰǼ�", "�����", "���ŰǼ�"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "0", "0", "0", "2019.08.09 (��)", "0", "0", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("ȸ��vs��ȸ�� ���� > ���� �� > ȸ��")) {
  			System.out.println(" *** memberNoMemberPurchaserProgress window(1) Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaserProgress window(1) Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		String[] summaryTopProgressTableData = {"���ŰǼ�", "���ż���"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryProgressTableData = {"0", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopProgressTableData = {"����", "IP", "������ó", "���ż���", "�����", "���� ����", "ȸ��ID", "�湮�Ͻ�"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress statTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress statTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("ȸ��vs��ȸ�� ����")) {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 1).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("ȸ��vs��ȸ�� ���� > ���� �� > ��ȸ��")) {
  			System.out.println(" *** memberNoMemberPurchaserMemberProgress window(3) Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaserProgress window(3) Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress statTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress statTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("ȸ��vs��ȸ�� ����")) {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- memberNoMemberPurchaser End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void memberAttributePurchaser() {
  		System.out.println(" ! ----- memberAttributePurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("ȸ��Ư���� ����")) {
			System.out.println(" *** memberAttributePurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberAttributePurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���ŰǼ�", "���ż���"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"���ɴ�vs����\nȸ�� �׷��vs����", "�Ұ�", "����", "����", "�˼�����", "���ŰǼ�", "���ż���", "�����", "���ŰǼ�", "���ż���", "�����", "���ŰǼ�", "���ż���"
  				, "�����", "���ŰǼ�", "���ż���"};
  		for(int i=0;i<=15;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** memberAttributePurchaser statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** memberAttributePurchaser statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- memberAttributePurchaser End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void faverPurchasePrice() {
  		System.out.println(" ! ----- faverPurchasePrice Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("���ż�ȣ ���ݴ�")) {
			System.out.println(" *** faverPurchasePrice pageLoad Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���ż���"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** faverPurchasePrice summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** faverPurchasePrice summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** faverPurchasePrice summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"���ݴ�", "�����", "��ٱ��� ����", "���ŰǼ�", "���ż���", "���ż�������", "�����"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** faverPurchasePrice statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** faverPurchasePrice statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
			System.out.println(" *** faverPurchasePrice cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("����׺���")) {
			System.out.println(" *** faverPurchasePrice cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- faverPurchasePrice End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void paymentAnalysis() {
  		System.out.println(" ! ----- paymentAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("���� ���� �м�")) {
			System.out.println(" *** paymentAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "���ŰǼ�", "�����", "���ŰǼ��� ���� ���� ����"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** paymentAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** paymentAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** paymentAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"���� ����", "���ŰǼ�", "���ŰǼ� ����", "�����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** paymentAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** paymentAnalysis statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
			System.out.println(" *** paymentAnalysis cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("����� ����")) {
			System.out.println(" *** paymentAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- paymentAnalysis End ----- ! ");
  	}
  	@Test(priority = 11)
  	public void timeAvgBuyCount() {
  		System.out.println(" ! ----- timeAvgBuyCount Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("�ð��뺰��ձ��ż�")) {
			System.out.println(" *** timeAvgBuyCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** timeAvgBuyCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�ð���� �����", "�ð���� ���ŰǼ�"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0.00", "-", "0.00", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.00", "0.00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð���", "��� �����", "��� ���湮��", "��� ���ŰǼ�"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.50", "1.00", "1.00", "1.00", "1.00", "2.00", "2.50", "5.00", "3.00"};
  		for(int i=0,x=0;i<=8;i=i+4) {
  			if($(".statDataCenter", (i+42)).text().trim().equals(statTableData[x])) {
  				System.out.println(" *** timeAvgBuyCount statTableData(" + x + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount statTableData(" + x + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+42)).text().trim());
  				close();
  			}
  			x++;
  		}
  		String[] statFootTableData = {"�ð����", "0.00", "0.75", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("��� ���ŰǼ�")) {
			System.out.println(" *** timeAvgBuyCount cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** timeAvgBuyCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("������")) {
			System.out.println(" *** timeAvgBuyCount cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** timeAvgBuyCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > a > font", 1).click();
  		$(".highcharts-series.highcharts-series-1").waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		String[] BarChartData = {"09��", "���湮��: 1.5", "10��", "���湮��: 1", "11��", "���湮��: 1", "12��", "���湮��: 1", "13��", "���湮��: 1", 
  				"14��", "���湮��: 2", "15��", "���湮��: 2.5", "16��", "���湮��: 5", "17��", "���湮��: 3"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).waitUntil(visible, 10000);
  		for(int i=9,y=0;i<=17;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  	  				System.out.println(" *** timeAvgBuyCount BarChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** timeAvgBuyCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		System.out.println(" ! ----- timeAvgBuyCount End ----- ! ");
  	}
  	@Test(priority = 12)
  	public void dayAvgBuyCount() {
  		System.out.println(" ! ----- dayAvgBuyCount Start ----- ! ");
  		$(".active > ul > li > a > span", 11).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 11).click();
  		if($("h3", 0).text().trim().equals("���Ϻ���ձ��ż�")) {
			System.out.println(" *** dayAvgBuyCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** dayAvgBuyCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "����� �����", "��� ���ŰǼ�"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.03 ~ 2019.08.09", "0.00", "-", "0.00", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.27 ~ 2019.08.02", "0.00", "0.00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "��� �����", "��� ���湮��", "��ձ��ŰǼ�"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"(��)", "0.00", "0.00", "0.00", "(ȭ)", "0.00", "0.00", "0.00", "(��)", "0.00", "9.00", "0.00", "(��)", "0.00", "8.00", "0.00"
  				, "(��)", "0.00", "28.00", "0.00", "(��)", "0.00", "0.00", "0.00", "(��)", "0.00", "0.00", "0.00"};
  		for(int i=0;i<=27;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�����", "0.00", "6.43", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("��ձ��ŰǼ�")) {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("������")) {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > a > font", 1).click();
  		$(".highcharts-series.highcharts-series-1").waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		String[] BarChartData = {"(��)", "���湮��: 9", "(��)", "���湮��: 8", "(��)", "���湮��: 28"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 2).waitUntil(visible, 10000);
  		for(int i=2,y=0;i<=4;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  	  				System.out.println(" *** dayAvgBuyCount BarChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** dayAvgBuyCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		System.out.println(" ! ----- dayAvgBuyCount End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}