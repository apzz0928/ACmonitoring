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

public class inflowPath {
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
  	public void inflowPathSummary() {
  		System.out.println(" ! ----- inflowPathSummary Start ----- ! ");
  		$("#m_stat > ul > li > a", 4).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("���԰�� ���")) {
			System.out.println(" *** inflowPathSummary pageLoad Success !! *** ");
		} else {
			System.out.println(" *** inflowPathSummary pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3").text().trim());
			close();
  		}
  		//��¥ ���� 2019-07-10~2019-07-11 
  		$("#date_range1 > a > img", 0).click();
  		$(".tabcontent.defaultOpen").waitUntil(visible, 10000);
  		$("#user_srt_date", 0).click();
  		js("$j('#user_srt_date').val('2019-07-10')");
  		js("$j('#user_end_date').val('2019-07-11')");
  		$(".btn_srh").click();
  		$(".black2", 0).waitUntil(visible, 10000);
  		if($(".black2", 0).text().trim().equals("(2019-07-10~2019-07-11)")) {
			System.out.println(" *** inflowPathSummary calenderSet Success !! *** ");
		} else {
			System.out.println(" *** inflowPathSummary calenderSet Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		String[] widgetTitle = {"�湮������ó", "���� ������", "���� �����λ�", "���� URL��"};
  		for(int i=0;i<=3;i++) {
  			if($(".w_handle > b", i).text().trim().equals(widgetTitle[i])) {
  				System.out.println(" *** inflowPathSummary widgetTitle (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowPathSummary widgetTitle (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".w_handle > b", i).text().trim());
  				close();
  			}
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 4).waitUntil(visible, 10000);
  		String[] widgetChart = {"��������:8", "��������:1", "��������:8", "��������:1", "��������:8", "��������:1", "��������:8", "��������:1"};
  		for(int i=0;i<=7;i++) {
  			if($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g > text ", i).text().trim().equals(widgetChart[i])) {
  				System.out.println(" *** inflowPathSummary widgetChart (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowPathSummary widgetChart (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).text().trim());
  				close();
  			}
  		}
  		$(".close_wiget", 3).click();
  		$("#widget4").waitUntil(hidden, 10000);
  		js("openWidgetRemoconSub('C', '3')");
  		sleep(500);
  		$(".set_bottom").waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 11).waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 8).click();
  		sleep(1500);
  		$(".btn_close").click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g > text", 7).waitUntil(visible, 10000);
  		$("#widget5 > .w_title > table > tbody > tr > .w_handle > b").waitUntil(visible, 10000);
  		if($("#widget5 > .w_title > table > tbody > tr > .w_handle > b").text().trim().equals("���� URL��")) {
				System.out.println(" *** inflowPathSummary widget del&add check Success !! *** ");
			} else {
				System.out.println(" *** inflowPathSummary widget del&add check Fail ... !@#$%^&*() *** ");
				System.out.println($("#widget5 > .w_title > table > tbody > tr > .w_handle > b").text().trim());
				close();
  		}  		
  		$("#date_range1 > a > img", 1).scrollIntoView(false);
  		$("#date_range1 > a > img", 1).click();
  		$("#date_range1 > a > img", 1).waitUntil(hidden, 10000);
  		if(!$(".black2", 0).text().trim().equals("(2019-07-10~2019-07-11)")) {
			System.out.println(" *** inflowPathSummary defalut set restore Success !! *** ");
		} else {
			System.out.println(" *** inflowPathSummary defalut set restore Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		System.out.println(" ! ----- inflowPathSummary End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void visitInflow() {
  		System.out.println(" ! ----- visitInflow Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("�湮������ó")) {
			System.out.println(" *** visitInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".myValue", 1).click();
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('2019-07-10')");
  		js("$j('#calendar_data2').val('2019-07-11')");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "0", "-", "0.00%", "-", "0", "-", "0.00%", "-", "00:02:06"};
  		for(int i=0;i<=11;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitInflow summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitInflow summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  ��������", "8", "88.89%", "0", "0.00%", "2.  ��������", "1", "11.11%", "0", "0.00%"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitInflow statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "9", "100.00%", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitInflow statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("�ڷᰡ �����ϴ�.")) {
				System.out.println(" *** visitInflow no-data search check Success !! *** ");
			} else {
				System.out.println(" *** visitInflow no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if($(".statDataCenter", 14).text().trim().equals("0.00%")) {
				System.out.println(" *** visitInflow search check Success !! *** ");
			} else {
				System.out.println(" *** visitInflow search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataCenter", 14).text().trim());
				close();
  		}
  		String[] inflowPieChartData = {"��������8", "88.89%", "��������1", "11.11%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[i])) {
  	  	  				System.out.println(" *** visitInflow inflowPieChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow inflowPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();	
  				}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitInflow inflowPieChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow inflowPieChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] directLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[i])) {
  	  	  				System.out.println(" *** visitInflow directLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow directLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitInflow directLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow directLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] innerLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 1", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).click();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[i])) {
  	  	  				System.out.println(" *** visitInflow innerLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow innerLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitInflow innerLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow innerLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("�湮������ó ����")) {
			System.out.println(" *** visitInflowProgress window(1) Success !! *** ");
		} else {
			System.out.println(" *** visitInflowProgress window(1) Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] summaryProgressTableData = {"2019.07.10 ~ 2019.07.11", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** visitInflowProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflowProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootProgressTableData = {"2019.07.08 ~ 2019.07.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootProgressTableData[i])) {
  				System.out.println(" *** visitInflowProgress summaryFootProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflowProgress summaryFootProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statProgressTableData = {"2019.07.10 (��)", "2019.07.11 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(statProgressTableData[i])) {
  				System.out.println(" *** visitInflowProgress statProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflowProgress statProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] progressLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[i])) {
  	  	  				System.out.println(" *** visitInflow progressLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow progressLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitInflow progressLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitInflow progressLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("�湮������ó")) {
			System.out.println(" *** visitInflow window(0) check Success !! *** ");
		} else {
			System.out.println(" *** visitInflow window(0) check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- visitInflow End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void searchEngineWord() {
  		System.out.println(" ! ----- searchEngineWord Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�˻�����/�˻���")) {
			System.out.println(" *** searchEngineWord pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchEngineWord pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchEngineWord summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchEngineWord summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0", "0", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchEngineWord summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchEngineWord summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- searchEngineWord End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void searchEngineDetail() {
  		System.out.println(" ! ----- searchEngineDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("�˻����� ��")) {
			System.out.println(" *** searchEngineDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchEngineDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchEngineDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchEngineDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchEngineDetail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchEngineDetail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- searchEngineDetail End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void searchWordDetail() {
  		System.out.println(" ! ----- searchWordDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("�˻��� ��")) {
			System.out.println(" *** searchWordDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** searchWordDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** searchWordDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchWordDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** searchWordDetail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** searchWordDetail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- searchWordDetail End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void changeSearchWord() {
  		System.out.println(" ! ----- changeSearchWord Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("��ȯ ���� �˻���")) {
			System.out.println(" *** changeSearchWord pageLoad Success !! *** ");
		} else {
			System.out.println(" *** changeSearchWord pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=7;i++) {
  			if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
  				System.out.println(" *** changeSearchWord statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** changeSearchWord statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- changeSearchWord End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void portalInflow() {
  		System.out.println(" ! ----- portalInflow Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("�ֿ����� ���Ǻ� ����")) {
			System.out.println(" *** portalInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** portalInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** portalInflow summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** portalInflow summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** portalInflow summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** portalInflow summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- portalInflow End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void inflowDomain() {
  		System.out.println(" ! ----- inflowDomain Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("���� ������")) {
			System.out.println(" *** inflowDomain pageLoad Success !! *** ");
		} else {
			System.out.println(" *** inflowDomain pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".summaryDataCenter", 7).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "0", "-", "0.00%", "-", "00:02:06"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** inflowDomain summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomain summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** inflowDomain summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomain summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   ��������", "8", "88.89%", "0", "0.00%", "2.   ��������", "1", "11.11%", "0", "0.00%"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** inflowDomain statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomain statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "9", "100.00%", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** inflowDomain statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomain statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("�ڷᰡ �����ϴ�.")) {
				System.out.println(" *** inflowDomain no-data search check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomain no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if($(".statDataCenter", 14).text().trim().equals("0.00%")) {
				System.out.println(" *** inflowDomain search check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomain search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataCenter", 14).text().trim());
				close();
  		}
  		String[] inflowPieChartData = {"��������8", "88.89%", "��������1", "11.11%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[i])) {
  	  	  				System.out.println(" *** inflowDomain inflowPieChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain inflowPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomain inflowPieChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain inflowPieChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] directLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[i])) {
  	  	  				System.out.println(" *** inflowDomain directLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain directLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomain directLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain directLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] innerLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 1", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).click();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[i])) {
  	  	  				System.out.println(" *** inflowDomain innerLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain innerLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomain innerLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain innerLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("���� ������ ����")) {
			System.out.println(" *** inflowDomainProgress window(1) Success !! *** ");
		} else {
			System.out.println(" *** inflowDomainProgress window(1) Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] summaryProgressTableData = {"2019.07.10 ~ 2019.07.11", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** inflowDomainProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootProgressTableData = {"2019.07.08 ~ 2019.07.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootProgressTableData[i])) {
  				System.out.println(" *** inflowDomainProgress summaryFootProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainProgress summaryFootProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statProgressTableData = {"2019.07.10 (��)", "2019.07.11 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(statProgressTableData[i])) {
  				System.out.println(" *** inflowDomainProgress statProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainProgress statProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] progressLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[i])) {
  	  	  				System.out.println(" *** inflowDomain progressLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain progressLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomain progressLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomain progressLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("���� ������")) {
			System.out.println(" *** inflowDomain window(0) check Success !! *** ");
		} else {
			System.out.println(" *** inflowDomain window(0) check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- inflowDomain End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void inflowDomainDetail() {
  		System.out.println(" ! ----- inflowDomainDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("���� �����λ�")) {
			System.out.println(" *** inflowDomainDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** inflowDomainDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".summaryDataCenter", 7).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "0", "-", "0.00%", "-", "00:02:06"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** inflowDomainDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** inflowDomainDetail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   ��������", "8", "88.89%", "0", "0.00%", "2.   ��������", "1", "11.11%", "0", "0.00%"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** inflowDomainDetail statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetail statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "9", "100.00%", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** inflowDomainDetail statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetail statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("�ڷᰡ �����ϴ�.")) {
				System.out.println(" *** inflowDomainDetail no-data search check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomainDetail no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if($(".statDataCenter", 14).text().trim().equals("0.00%")) {
				System.out.println(" *** inflowDomainDetail search check Success !! *** ");
			} else {
				System.out.println(" *** inflowDomainDetail search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataCenter", 14).text().trim());
				close();
  		}
  		String[] inflowPieChartData = {"��������8", "88.89%", "��������1", "11.11%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[i])) {
  	  	  				System.out.println(" *** inflowDomainDetail inflowPieChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail inflowPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomainDetail inflowPieChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail inflowPieChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] directLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[i])) {
  	  	  				System.out.println(" *** inflowDomainDetail directLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail directLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomainDetail directLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail directLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] innerLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 1", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).click();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[i])) {
  	  	  				System.out.println(" *** inflowDomainDetail innerLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail innerLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
				for(int i=0;i<=5;i++) {
	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
				}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomainDetail innerLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail innerLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("���� �����λ� ����")) {
			System.out.println(" *** inflowDomainDetailProgress window(1) Success !! *** ");
		} else {
			System.out.println(" *** inflowDomainDetailProgress window(1) Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] summaryProgressTableData = {"2019.07.10 ~ 2019.07.11", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** inflowDomainDetailProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetailProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootProgressTableData = {"2019.07.08 ~ 2019.07.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootProgressTableData[i])) {
  				System.out.println(" *** inflowDomainDetailProgress summaryFootProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetailProgress summaryFootProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statProgressTableData = {"2019.07.10 (��)", "2019.07.11 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(statProgressTableData[i])) {
  				System.out.println(" *** inflowDomainDetailProgress statProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowDomainDetailProgress statProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] progressLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[i])) {
  	  	  				System.out.println(" *** inflowDomainDetail progressLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail progressLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowDomainDetail progressLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowDomainDetail progressLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("���� �����λ�")) {
			System.out.println(" *** inflowDomainDetail window(0) check Success !! *** ");
		} else {
			System.out.println(" *** inflowDomainDetail window(0) check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- inflowDomainDetail End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void inflowURLDetail() {
  		System.out.println(" ! ----- inflowURLDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("���� URL��")) {
			System.out.println(" *** inflowURLDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** inflowURLDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".summaryDataCenter", 7).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "0", "-", "0.00%", "-", "00:02:06"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** inflowURLDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** inflowURLDetail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   ��������", "8", "88.89%", "0", "0.00%", "2.   ��������", "1", "11.11%", "0", "0.00%"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** inflowURLDetail statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetail statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "9", "100.00%", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** inflowURLDetail statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetail statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("�ڷᰡ �����ϴ�.")) {
				System.out.println(" *** inflowURLDetail no-data search check Success !! *** ");
			} else {
				System.out.println(" *** inflowURLDetail no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if($(".statDataCenter", 14).text().trim().equals("0.00%")) {
				System.out.println(" *** inflowURLDetail search check Success !! *** ");
			} else {
				System.out.println(" *** inflowURLDetail search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataCenter", 14).text().trim());
				close();
  		}
  		String[] inflowPieChartData = {"��������8", "88.89%", "��������1", "11.11%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[i])) {
  	  	  				System.out.println(" *** inflowURLDetail inflowPieChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail inflowPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(inflowPieChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowURLDetail inflowPieChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail inflowPieChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] directLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[i])) {
  	  	  				System.out.println(" *** inflowURLDetail directLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail directLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(directLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowURLDetail directLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail directLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] innerLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 1", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).click();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[i])) {
  	  	  				System.out.println(" *** inflowURLDetail innerLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail innerLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[i].equals(innerLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowURLDetail innerLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail innerLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("���� URL�� ����")) {
			System.out.println(" *** inflowURLDetailProgress window(4) Success !! *** ");
		} else {
			System.out.println(" *** inflowURLDetailProgress window(4) Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] summaryProgressTableData = {"2019.07.10 ~ 2019.07.11", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** inflowURLDetailProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetailProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootProgressTableData = {"2019.07.08 ~ 2019.07.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootProgressTableData[i])) {
  				System.out.println(" *** inflowURLDetailProgress summaryFootProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetailProgress summaryFootProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statProgressTableData = {"2019.07.10 (��)", "2019.07.11 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(statProgressTableData[i])) {
  				System.out.println(" *** inflowURLDetailProgress statProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowURLDetailProgress statProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] progressLineChartData = {"2019.07.11 (��)", "���������� ���Լ� ����: 8", "2019.07.10 (��)", "���������� ���Լ� ����: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[i])) {
  	  	  				System.out.println(" *** inflowURLDetail progressLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail progressLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(progressLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** inflowURLDetail progressLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** inflowURLDetail progressLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("���� URL��")) {
			System.out.println(" *** inflowURLDetail window(0) check Success !! *** ");
		} else {
			System.out.println(" *** inflowURLDetail window(0) check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- inflowURLDetail End ----- ! ");
  	}
  	@Test(priority = 11)
  	public void webMail() {
  		System.out.println(" ! ----- webMail Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("������")) {
			System.out.println(" *** webMail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** webMail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0.00", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** webMail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webMail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** webMail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webMail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- webMail End ----- ! ");
  	}
  	@Test(priority = 12)
  	public void SNS() {
  		System.out.println(" ! ----- SNS Start ----- ! ");
  		$(".active > ul > li > a > span", 11).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 11).click();
  		if($("h3", 0).text().trim().equals("��α�/ī��/SNS")) {
			System.out.println(" *** SNS pageLoad Success !! *** ");
		} else {
			System.out.println(" *** SNS pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** SNS summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** SNS summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** SNS summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** SNS summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- SNS End ----- ! ");
  	}
  	@Test(priority = 13)
  	public void shoppingSearch() {
  		System.out.println(" ! ----- shoppingSearch Start ----- ! ");
  		$(".active > ul > li > a > span", 12).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 12).click();
  		if($("h3", 0).text().trim().equals("���ΰ˻�")) {
			System.out.println(" *** shoppingSearch pageLoad Success !! *** ");
		} else {
			System.out.println(" *** shoppingSearch pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** shoppingSearch summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** shoppingSearch summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** shoppingSearch summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** shoppingSearch summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- shoppingSearch End ----- ! ");
  	}
  	@Test(priority = 14)
  	public void domain() {
  		System.out.println(" ! ----- domain Start ----- ! ");
  		$(".active > ul > li > a > span", 13).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 13).click();
  		if($("h3", 0).text().trim().equals("������ �뵵��")) {
			System.out.println(" *** domain pageLoad Success !! *** ");
		} else {
			System.out.println(" *** domain pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** domain summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** domain summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** domain summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** domain summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- domain End ----- ! ");
  	}
  	@Test(priority = 15)
  	public void nationDomain() {
  		System.out.println(" ! ----- nationDomain Start ----- ! ");
  		$(".active > ul > li > a > span", 14).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 14).click();
  		if($("h3", 0).text().trim().equals("������ ������")) {
			System.out.println(" *** nationDomain pageLoad Success !! *** ");
		} else {
			System.out.println(" *** nationDomain pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** nationDomain summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationDomain summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** nationDomain summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationDomain summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- nationDomain End ----- ! ");
  	}
  	//@Test(priority = 99)
  	public void increaseVisit() {
  		System.out.println(" ! ----- increaseVisit Start ----- ! ");
		open("http://apzz092888.blogspot.com/");
		$(".sub24").waitUntil(visible, 10000);
		for(int i=1;i<=24;i++) {
			$(".sub" + i).scrollIntoView(false);
			$(".sub" + i).click();
	  		System.out.println("sub" + i + " Ŭ��");
			sleep(500);
		}
		open("http://apzz98288.egloos.com");
  		System.out.println(" ! ----- increaseVisit End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}