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

public class Visitor {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, temp_pw, A, B, pageLoadCheck;
	private static HttpURLConnection huc;
	private static int respCode;
	
	Date date = new Date();
	Date date1 = new Date();
    SimpleDateFormat date_format = new SimpleDateFormat("YYYYMMddHHmmss");
    SimpleDateFormat date_format1 = new SimpleDateFormat("YYYY-MM-dd");
    String id_date = date_format.format(date);
    String today = date_format1.format(date1);
	
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
  	public void recentlyVisitor() {
  		System.out.println(" ! ----- recentlyVisitor Start ----- ! ");
  		$("#m_stat > ul > li > a", 13).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("�ֱ� �湮��")) {
			System.out.println(" *** recentlyVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** recentlyVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".myValue", 1).click();
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('" + today + "')");
  		js("$j('#calendar_data2').val('" + today + "')");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"����", "�湮�Ͻ�", "IP", "������ó", "����������", "��������", "��ȯ", "ȸ��ID", "��������"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** recentlyVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** recentlyVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("�ڷᰡ �����ϴ�.")) {
				System.out.println(" *** recentlyVisitor no-data search check Success !! *** ");
			} else {
				System.out.println(" *** recentlyVisitor no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(1);
  		$("#tblpop > tbody > tr > td > table > tbody > tr > td > b > span").waitUntil(visible, 10000);
  		if($("#tblpop > tbody > tr > td > table > tbody > tr > td > b > span").text().trim().equals("IP : 10.77.129.79")) {
			System.out.println(" *** recentlyVisitor Whois IP check Success !! *** ");
		} else {
			System.out.println(" *** recentlyVisitor Whois IP check Fail ... !@#$%^&*() *** ");
			System.out.println($("#tblpop > tbody > tr > td > table > tbody > tr > td > b > span").text().trim());
			close();
  		}
  		switchTo().window(0);
  		System.out.println(" ! ----- recentlyVisitor End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void accessVisitor() {
  		System.out.println(" ! ----- accessVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("������������ �湮��")) {
			System.out.println(" *** accessVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** accessVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"�ֱټ���", "�湮�ð�", "IP", "������ó", "����������", "��������", "����", "����", "��������"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** accessVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** accessVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
				System.out.println(" *** accessVisitor statFootTableData check Success !! *** ");
			} else {
				System.out.println(" *** accessVisitor statFootTableData check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- accessVisitor End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void visitAnalysis() {
  		System.out.println(" ! ----- visitAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�湮 �� �м�")) {
			System.out.println(" *** visitAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitAnalysis pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTopTableData = {"�м��Ⱓ", "���� �湮Ƚ��", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** visitAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "1ȸ", "38", "322.22%\n���", "12.18", "1118.42%\n���", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "1ȸ", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�湮Ƚ��", "�湮��", "�湮����", "���ŰǼ�", "������", "�����", "�湮����������"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1ȸ", "36", "94.74%", "0", "0.00%", "0", "12.67", "2ȸ", "2", "5.26%", "0", "0.00%", "0", "3.50"};
  		for(int i=0;i<=13;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitAnalysis statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "0", "0.00%", "0", "12.18"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitAnalysis statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮����������")) {
  				System.out.println(" *** visitAnalysis cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** visitAnalysis cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"1ȸ36", "94.74%", "2ȸ2", "5.26%"};
  		for(int i=0,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(PieChartData[y])) {
  					System.out.println(" *** visitAnalysis PieChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** visitAnalysis PieChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "1ȸ�� �湮�� ����: 28", "2019.08.08 (��)", "1ȸ�� �湮�� ����: 8"
  				, "2019.08.09 (��)", "2ȸ�� �湮�� ����: 2", "2019.08.08 (��)", "2ȸ�� �湮�� ����: 0"};
  		for(int i=0,z=0;i<=1;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** visitAnalysis LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** visitAnalysis LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("�湮 �� �м� ����")) {
			System.out.println(" *** visitAnalysis pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** visitAnalysis pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "36"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** visitAnalysis Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** visitAnalysis Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** visitAnalysis Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** visitAnalysis Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** visitAnalysis Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "1ȸ�� �湮�� ����: 28", "2019.08.08 (��)", "1ȸ�� �湮�� ����: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** visitAnalysis Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** visitAnalysis Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("�湮 �� �м�")) {
  			System.out.println(" *** visitAnalysis window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** visitAnalysis window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- visitAnalysis End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void revisitTerm() {
  		System.out.println(" ! ----- revisitTerm Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("��湮����")) {
			System.out.println(" *** revisitTerm pageLoad Success !! *** ");
		} else {
			System.out.println(" *** revisitTerm pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м��Ⱓ", "���� ��湮����", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** revisitTerm summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "����", "2", "���", "3.50", "���", "00:07:28"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** revisitTerm summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "-", "0", "0.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** revisitTerm summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"��湮����", "�湮��", "�湮����", "���ŰǼ�", "������", "�����", "�湮����������"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** revisitTerm statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  ����", "2", "100.00%", "0", "0.00%", "0", "3.50"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** revisitTerm statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "2", "100.00%", "0", "0.00%", "0", "3.50"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** revisitTerm statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮����������")) {
  				System.out.println(" *** revisitTerm cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** revisitTerm cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"����2", "100.00%"};
  		for(int i=0,y=0;i<=0;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(PieChartData[y])) {
  					System.out.println(" *** revisitTerm PieChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** revisitTerm PieChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "������ �湮�� ����: 2", "2019.08.08 (��)", "������ �湮�� ����: 0"};
  		for(int i=0,z=0;i<=0;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** revisitTerm LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** revisitTerm LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("��湮���� ����")) {
			System.out.println(" *** revisitTerm pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** revisitTerm pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "2"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** revisitTerm Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** revisitTerm Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** revisitTerm Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** revisitTerm Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** revisitTerm Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "������ �湮�� ����: 2", "2019.08.08 (��)", "������ �湮�� ����: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** revisitTerm Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** revisitTerm Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("��湮����")) {
  			System.out.println(" *** revisitTerm window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** revisitTerm window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- revisitTerm End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void regionVisitor() {
  		System.out.println(" ! ----- regionVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("������ �湮��")) {
			System.out.println(" *** regionVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** regionVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮�� ���� ����", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** regionVisitor summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "�˼�����", "38", "322.22%\n���", "12.18", "1118.00%\n���", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** regionVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "�˼�����", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** regionVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"������", "�湮��", "�湮����", "���ŰǼ�", "������"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** regionVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  �˼�����", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** regionVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** regionVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** regionVisitor no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** regionVisitor search check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** regionVisitor cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
  				System.out.println(" *** regionVisitor cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"�˼�����38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** regionVisitor PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "�˼������� �湮�� ����: 30", "2019.08.08 (��)", "�˼������� �湮�� ����: 8"};
  		for(int i=0,z=0;i<=0;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** regionVisitor LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** regionVisitor LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("������ �湮�� ����")) {
			System.out.println(" *** regionVisitor pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** regionVisitor pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** regionVisitor Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** regionVisitor Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** regionVisitor Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** regionVisitor Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** regionVisitor Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "�˼������� �湮�� ����: 30", "2019.08.08 (��)", "�˼������� �湮�� ����: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** regionVisitor Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** regionVisitor Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("������ �湮��")) {
  			System.out.println(" *** regionVisitor window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** regionVisitor window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- regionVisitor End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void nationVisitor() {
  		System.out.println(" ! ----- nationVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("������ �湮��")) {
			System.out.println(" *** nationVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** nationVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮�� ���� ����", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** nationVisitor summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "�˼�����", "38", "322.22%\n���", "12.18", "1118.00%\n���", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** nationVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "�˼�����", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** nationVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"������", "�湮��", "�湮����", "���ŰǼ�", "������"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** nationVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"�˼�����", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** nationVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** nationVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** nationVisitor no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** nationVisitor search check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** nationVisitor cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
  				System.out.println(" *** nationVisitor cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"��Ÿ38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//����1 Ȯ��
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** nationVisitor PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "�˼������� �湮�� ����: 30", "2019.08.08 (��)", "�˼������� �湮�� ����: 8"};
  		for(int i=0,z=0;i<=0;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** nationVisitor LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** nationVisitor LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		System.out.println(" ! ----- nationVisitor End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void companyVisitor() {
  		System.out.println(" ! ----- companyVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("ȸ��/����� �湮��")) {
			System.out.println(" *** companyVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** companyVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮�� ���� ȸ��/���", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** companyVisitor summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "-", "0", "�϶�", "0.00", "�϶�", "00:00:00"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** companyVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "-", "1", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** companyVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"ȸ��/���", "�湮��", "�湮����", "���ŰǼ�", "������"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** companyVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"�˼�����", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** companyVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** companyVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** companyVisitor no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** companyVisitor search check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", 14).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** companyVisitor cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("�����")) {
  				System.out.println(" *** companyVisitor cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"�˼�����38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//����1 Ȯ��
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** companyVisitor PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "�˼������� �湮�� ����: 30", "2019.08.08 (��)", "�˼������� �湮�� ����: 8"};
  		for(int i=0,z=0;i<=0;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** companyVisitor LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** companyVisitor LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		System.out.println(" ! ----- companyVisitor End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void languageVisitor() {
  		System.out.println(" ! ----- languageVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("��� �� �湮��")) {
			System.out.println(" *** languageVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** languageVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м��Ⱓ", "�湮�� ���� �����", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** languageVisitor summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "�ѱ���", "38", "322.22%\n���", "12.18", "1118.00%\n���", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** languageVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "�ѱ���", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** languageVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�����", "�湮��", "�湮����", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** languageVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  �ѱ���", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** languageVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** languageVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** languageVisitor no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** languageVisitor search check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor search check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮����������")) {
  				System.out.println(" *** languageVisitor cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** languageVisitor cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"�ѱ���38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//����1 Ȯ��
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** languageVisitor PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "�ѱ����� �湮�� ����: 30", "2019.08.08 (��)", "�ѱ����� �湮�� ����: 8"};
  		for(int i=0,z=0;i<=0;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** languageVisitor LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** languageVisitor LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
  		switchTo().window(5);
  		if($("h3", 0).text().trim().equals("��� �� �湮�� ����")) {
			System.out.println(" *** languageVisitor pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** languageVisitor pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** languageVisitor Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** languageVisitor Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** languageVisitor Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** languageVisitor Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** languageVisitor Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "�ѱ����� �湮�� ����: 30", "2019.08.08 (��)", "�ѱ����� �湮�� ����: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** languageVisitor Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** languageVisitor Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("��� �� �湮��")) {
  			System.out.println(" *** languageVisitor window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** languageVisitor window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- languageVisitor End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void TimeZone() {
  		System.out.println(" ! ----- TimeZone Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("TimeZone")) {
			System.out.println(" *** TimeZone pageLoad Success !! *** ");
		} else {
			System.out.println(" *** TimeZone pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м��Ⱓ", "�湮�� ���� TimeZone", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** TimeZone summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "[GMT+9] ����, ����, ����ī, ������, ������ũ", "38", "322.22%\n���", "12.18", "1118.42%\n���", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** TimeZone summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "[GMT+9] ����, ����, ����ī, ������, ������ũ", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** TimeZone summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"TimeZone", "�湮��", "�湮����", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** TimeZone statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"[GMT+9] ����, ����, ����ī, ������, ������ũ", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+88)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** TimeZone statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+88)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** TimeZone statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮����������")) {
  				System.out.println(" *** TimeZone cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** TimeZone cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"[GMT+9] ����, ����, ����ī, ������, ������ũ38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** TimeZone PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "[GMT+9] ����, ����, ����ī, ������, ������ũ �� �湮�� ����: 30", "2019.08.08 (��)", "[GMT+9] ����, ����, ����ī, ������, ������ũ �� �湮�� ����: 8"};
  		for(int i=0,z=0;i<=0;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** TimeZone LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** TimeZone LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 21).click();
  		switchTo().window(6);
  		if($("h3", 0).text().trim().equals("TimeZone ����")) {
			System.out.println(" *** TimeZone pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** TimeZone pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** TimeZone Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** TimeZone Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** TimeZone Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** TimeZone Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** TimeZone Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "[GMT+9] ����, ����, ����ī, ������, ������ũ �� �湮�� ����: 30", "2019.08.08 (��)", "[GMT+9] ����, ����, ����ī, ������, ������ũ �� �湮�� ����: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** TimeZone Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** TimeZone Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("TimeZone")) {
  			System.out.println(" *** TimeZone window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** TimeZone window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- TimeZone End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void ISPVisitor() {
  		System.out.println(" ! ----- ISPVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("ISP�� �湮��")) {
			System.out.println(" *** ISPVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** ISPVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м��Ⱓ", "�湮�� ���� ISP", "�湮��", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** ISPVisitor summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "UNKNOWN", "0", "�϶�", "0.00", "�϶�", "00:00:00"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** ISPVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "not convert", "1", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** ISPVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"ISP", "�湮��", "�湮����", "�湮����������"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** ISPVisitor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"��Ÿ", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** ISPVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** ISPVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** ISPVisitor no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** ISPVisitor search check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor search check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮����������")) {
  				System.out.println(" *** ISPVisitor cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮��ü���ð�")) {
  				System.out.println(" *** ISPVisitor cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"��Ÿ38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** ISPVisitor PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		System.out.println(" ! ----- ISPVisitor End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}