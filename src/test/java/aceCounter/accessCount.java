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

public class accessCount {
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
	private static void alertCheck(String msg, String alert) {
		if(switchTo().alert().getText().equals(alert)) {
			System.out.println(" *** " + msg + " check Success !! *** ");
			confirm(alert);
		} else {
			System.out.println(" *** " + msg + " check Fail ... !@#$%^&*() *** ");
			System.out.println(switchTo().alert().getText());
			close();
		}
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
  	public void accessCountSummary() {
  		System.out.println(" ! ----- accessCountSummary Start ----- ! ");
  		$("#m_stat > ul > li > a", 3).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("���Ӽ� ���")) {
			System.out.println(" *** accessCountSummary pageLoad Success !! *** ");
		} else {
			System.out.println(" *** accessCountSummary pageLoad Fail ... !@#$%^&*() *** ");
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
			System.out.println(" *** accessCountSummary calenderSet Success !! *** ");
		} else {
			System.out.println(" *** accessCountSummary calenderSet Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		String[] widgetTitle = {"��������", "�űԹ湮�� ��湮", "�湮��", "�湮�� ��������"};
  		for(int i=0;i<=3;i++) {
  			if($(".w_handle > b", i).text().trim().equals(widgetTitle[i])) {
  				System.out.println(" *** accessCountSummary widgetTitle (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** accessCountSummary widgetTitle (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".w_handle > b", i).text().trim());
  				close();
  			}
  		}
  		widgetTitle = null;
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 4).waitUntil(visible, 10000);
  		String[] widgetChart = {"21", "�űԹ湮:8", "��湮:1", "9", "2.33"};
  		for(int i=0;i<=4;i++) {
  			if($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).text().trim().equals(widgetChart[i])) {
  				System.out.println(" *** accessCountSummary widgetChart (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** accessCountSummary widgetChart (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).text().trim());
  				close();
  			}
  		}
  		widgetChart = null;
  		$(".close_wiget", 3).click();
  		$("#widget4").waitUntil(hidden, 10000);
  		js("openWidgetRemoconSub('C', '2')");
  		sleep(500);
  		$(".set_bottom").waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 12).waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 6).click();
  		sleep(1500);
  		$(".btn_close").click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 4).waitUntil(visible, 10000);
  		$("#widget5 > .w_title > table > tbody > tr > .w_handle > b").waitUntil(visible, 10000);
  		if($("#widget5 > .w_title > table > tbody > tr > .w_handle > b").text().trim().equals("�湮�� ��������")) {
				System.out.println(" *** accessCountSummary widget del&add check Success !! *** ");
			} else {
				System.out.println(" *** accessCountSummary widget del&add check Fail ... !@#$%^&*() *** ");
				System.out.println($("#widget5 > .w_title > table > tbody > tr > .w_handle > b").text().trim());
				close();
  		}
  		$("#date_range1 > a > img", 1).scrollIntoView(false);
  		$("#date_range1 > a > img", 1).click();
  		$("#date_range1 > a > img", 1).waitUntil(hidden, 10000);
  		if(!$(".black2", 0).text().trim().equals("(2019-07-10~2019-07-11)")) {
			System.out.println(" *** accessCountSummary defalut set restore Success !! *** ");
		} else {
			System.out.println(" *** accessCountSummary defalut set restore Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		System.out.println(" ! ----- accessCountSummary End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void pageView() {
  		System.out.println(" ! ----- pageView Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("��������")) {
			System.out.println(" *** pageView pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pageView pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$("select[name=vtype]", 0).selectOption("�ϰ����κ���");
  		alertCheck("pageView dateViewType check msg", "�Ϸ缱�� �ÿ� �ð������� ��ȸ�ϽǼ� �ֽ��ϴ�.");
  		//��¥ ���� 2019-07-10~2019-07-11 
  		$(".myValue", 1).click();
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('2019-07-10')");
  		js("$j('#calendar_data2').val('2019-07-11')");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();  		
  		$("select[name=vtype]", 0).waitUntil(visible, 10000);
  		$("select[name=vtype]", 0).selectOption("�ϰ����κ���");  		
  		$("td.statDataRight", 3).waitUntil(hidden, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "21", "���", "9", "���", "2.33"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pageView summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pageView summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0", "2019.07.11 (��)", "21", "0", "21"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pageView statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "21(11)", "0(0)", "+ 21"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pageView statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] avgChartData = {"2019.07.11 (��)", "�ֱ�1�������: 0", "2019.07.10 (��)", "�ֱ�1�������: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(avgChartData[i])) {
  	  	  				System.out.println(" *** pageView avgChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** pageView avgChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				for(int i=0;i<=5;i++) {
  		  	  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(avgChartData[(i+2)])) {
  	  	  				System.out.println(" *** pageView avgChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** pageView avgChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] barChartData = {"2019.07.11 (��)", "��������: 21"};
		for(int i=0;i<=5;i++) {
				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();	
		}
  		for(int i=0;i<=1;i++) {
  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[i].equals(barChartData[i])) {
  				System.out.println(" *** pageView barChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView barChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[i]);
  				close();
  			}
  		}
  		System.out.println(" ! ----- pageView End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void visitCount() {
  		System.out.println(" ! ----- visitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�湮��")) {
			System.out.println(" *** visitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "8", "���", "1"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0", "0", "0", "2019.07.11 (��)", "9", "8", "1", "0", "9"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "9(5)", "8(4)", "1(1)", "0(0)", "+9"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] avgChartData = {"2019.07.11 (��)", "�ֱ�1�������: 0", "2019.07.10 (��)", "�ֱ�1�������: 0"};
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		String[] dataCheck = pageLoadCheck.split("�� ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(avgChartData[i])) {
  	  	  				System.out.println(" *** visitCount avgChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitCount avgChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 0).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(avgChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitCount avgChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitCount avgChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] barChartData = {"2019.07.11 (��)", "�湮��: 9"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		dataCheck = pageLoadCheck.split("�� ");
  		for(int i=0;i<=1;i++) {
  			if(dataCheck[i].equals(barChartData[i])) {
  				System.out.println(" *** visitCount barChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount barChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(dataCheck[i]).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- visitCount End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void newVisitCount() {
  		System.out.println(" ! ----- newVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("�űԹ湮��")) {
			System.out.println(" *** newVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "8", "���", "88.89%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0.00%", "0", "0", "2019.07.11 (��)", "9", "8", "88.89%", "0", "8"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "9(5)", "8(4)", "88.89%", "0(0)", "+8"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] avgChartData = {"2019.07.11 (��)", "�ֱ�1�������: 0", "2019.07.10 (��)", "�ֱ�1�������: 0"};
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		String[] dataCheck = pageLoadCheck.split("�� ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(avgChartData[i])) {
  	  	  				System.out.println(" *** newVisitCount avgChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newVisitCount avgChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 0).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(avgChartData[(i+2)])) {
  	  	  				System.out.println(" *** newVisitCount avgChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newVisitCount avgChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] barChartData = {"2019.07.11 (��)", "�űԹ湮��: 8"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		dataCheck = pageLoadCheck.split("�� ");
  		for(int i=0;i<=1;i++) {
  			if(dataCheck[i].equals(barChartData[i])) {
  				System.out.println(" *** newVisitCount barChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount barChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(dataCheck[i]).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- newVisitCount End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void reVisitCount() {
  		System.out.println(" ! ----- reVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("��湮��")) {
			System.out.println(" *** reVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** reVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "9", "���", "1", "���", "11.11%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** reVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** reVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0.00%", "0", "0", "2019.07.11 (��)", "9", "1", "11.11%", "0", "1"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** reVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "9(5)", "1(1)", "11.11%", "0(0)", "+1"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** reVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] avgChartData = {"2019.07.11 (��)", "�ֱ�1�������: 0", "2019.07.10 (��)", "�ֱ�1�������: 0"};
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		String[] dataCheck = pageLoadCheck.split("�� ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(avgChartData[i])) {
  	  	  				System.out.println(" *** reVisitCount avgChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** reVisitCount avgChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 0).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(avgChartData[(i+2)])) {
  	  	  				System.out.println(" *** reVisitCount avgChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** reVisitCount avgChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] barChartData = {"2019.07.11 (��)", "��湮��: 1"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		dataCheck = pageLoadCheck.split("�� ");
  		for(int i=0;i<=1;i++) {
  			if(dataCheck[i].equals(barChartData[i])) {
  				System.out.println(" *** reVisitCount barChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount barChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(dataCheck[i]).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- reVisitCount End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void newAndReVisitCount() {
  		System.out.println(" ! ----- newAndReVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("�űԹ湮�� ��湮")) {
			System.out.println(" *** newAndReVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newAndReVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "8", "���", "1", "���", "88.89%", "88.89% p\n���", "11.11%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newAndReVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00%", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newAndReVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0.00%", "2019.07.11 (��)", "8", "1", "88.89%"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newAndReVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "8(4)", "1(1)", "88.89%"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newAndReVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] visitPieChartData = {"�űԹ湮8", "88.89%", "��湮1", "11.11%"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		String[] dataCheck = pageLoadCheck.split(": ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(visitPieChartData[i])) {
  	  	  				System.out.println(" *** newAndReVisitCount visitPieChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newAndReVisitCount visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();
  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();
  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 0).text();
  				dataCheck = pageLoadCheck.split(": ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(visitPieChartData[(i+2)])) {
  	  	  				System.out.println(" *** newAndReVisitCount visitPieChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newAndReVisitCount visitPieChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		dataCheck = null;
  		String[] newVisitLineChartData = {"2019.07.11 (��)", "�űԹ湮: 8", "2019.07.10 (��)", "�űԹ湮: 0"};
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 1).text();
		dataCheck = pageLoadCheck.split("�� ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(newVisitLineChartData[i])) {
  	  	  				System.out.println(" *** newAndReVisitCount newVisitChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newAndReVisitCount newVisitChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 1).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(newVisitLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** newAndReVisitCount newVisitChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newAndReVisitCount newVisitChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		dataCheck = null;
  		String[] reVisitLineChartData = {"2019.07.11 (��)", "��湮: 1", "2019.07.10 (��)", "��湮: 0"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).click();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();  			
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ").equals(reVisitLineChartData[i])) {
  	  	  				System.out.println(" *** newAndReVisitCount reVisitChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newAndReVisitCount reVisitChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				pageLoadCheck = $(".highcharts-tooltip", 1).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(reVisitLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** newAndReVisitCount newVisitChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** newAndReVisitCount newVisitChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		dataCheck = null;
  		System.out.println(" ! ----- newAndReVisitCount End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void pureVisitCount() {
  		System.out.println(" ! ----- pureVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("���湮��")) {
			System.out.println(" *** pureVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pureVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "8", "���", "8", "���", "8", "���", "8"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pureVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pureVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0", "2019.07.11 (��)", "8", "0", "8"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pureVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "8(4)", "0(0)", "+8"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pureVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] avgChartData = {"2019.07.11 (��)", "�ֱ�1�������: 0", "2019.07.10 (��)", "�ֱ�1�������: 0"};
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		String[] dataCheck = pageLoadCheck.split("�� ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(avgChartData[i])) {
  	  	  				System.out.println(" *** pureVisitCount avgChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** pureVisitCount avgChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 0).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(avgChartData[(i+2)])) {
  	  	  				System.out.println(" *** pureVisitCount avgChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** pureVisitCount avgChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] barChartData = {"2019.07.11 (��)", "�ϰ����� ���湮��: 8"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		dataCheck = pageLoadCheck.split("�� ");
  		for(int i=0;i<=1;i++) {
  			if(dataCheck[i].equals(barChartData[i])) {
  				System.out.println(" *** pureVisitCount barChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount barChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(dataCheck[i]).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- pureVisitCount End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void visitPageview() {
  		System.out.println(" ! ----- visitPageview Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("�湮�� ��������")) {
			System.out.println(" *** visitPageview pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitPageview pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "21", "���", "9", "���", "2.33"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitPageview summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0", "0", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitPageview summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.07.10 (��)", "0", "0", "0.00", "0", "0", "2019.07.11 (��)", "21", "9", "2.33", "0", "2.33"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitPageview statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "21(11)", "9(5)", "2.33", "0.00", "+2.33"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitPageview statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] avgChartData = {"2019.07.11 (��)", "�ֱ�1�������: 0", "2019.07.10 (��)", "�ֱ�1�������: 0"};
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 0).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		String[] dataCheck = pageLoadCheck.split("�� ");
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//���� 1, 2�� Ȯ�ο� �б�
  				for(int i=0;i<=1;i++) {//����1 Ȯ��
  					if(dataCheck[i].equals(avgChartData[i])) {
  	  	  				System.out.println(" *** visitPageview avgChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitPageview avgChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				dataCheck = null;
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  		  		$(".highcharts-markers.highcharts-series-1.highcharts-tracker > path", 1).hover();
  				pageLoadCheck = $(".highcharts-tooltip", 0).text();
  				dataCheck = pageLoadCheck.split("�� ");
  				for(int i=0;i<=1;i++) {//����2 Ȯ��
  					if(dataCheck[i].equals(avgChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitPageview avgChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitPageview avgChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(dataCheck[i]).text().trim());
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] barChartData = {"2019.07.11 (��)", "�湮����������: 2.33"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 1).hover();
		pageLoadCheck = $(".highcharts-tooltip", 0).text();
		dataCheck = pageLoadCheck.split("�� ");
  		for(int i=0;i<=1;i++) {
  			if(dataCheck[i].equals(barChartData[i])) {
  				System.out.println(" *** visitPageview barChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview barChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(dataCheck[i]).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- visitPageview End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void timezoneCount() {
  		System.out.println(" ! ----- timezoneCount Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("�ð��뺰 ���Ӽ� ����")) {
			System.out.println(" *** timezoneCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** timezoneCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.10 ~ 2019.07.11", "0.44", "���", "0.19", "���", "0.17", "���", "0.02", "���", "0.17"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** timezoneCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.08 ~ 2019.07.09", "0.00", "0.00", "0.00", "0.00", "0.00"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** timezoneCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�ð� ���", "0.44", "0.19", "0.17", "0.02"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** timezoneCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] pageViewChartData = {"��������: 3.5", "��������: 1", "��������: 0.5", "��������: 0.5", "��������: 1", "��������: 3.5", "��������: 0.5"};
  		String[] visitCountChartData = {"�湮��: 0.5", "�湮��: 0.5", "�湮��: 0.5", "�湮��: 0.5", "�湮��: 1", "�湮��: 1", "�湮��: 0.5"};
  		String[] newVisitChartData = {"�űԹ湮��: 0.5", "�űԹ湮��: 0.5", "�űԹ湮��: 0.5", "�űԹ湮��: 1", "�űԹ湮��: 1", "�űԹ湮��: 0.5"};
  		String[] pureVisitwChartData = {"���湮��: 0.5", "���湮��: 0.5", "���湮��: 0.5", "���湮��: 1", "���湮��: 1", "���湮��: 0.5"};
  		int[] num = {8, 9, 12, 13, 14, 15, 17};
  		int[] num1 = {8, 12, 13, 14, 15, 17};
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=6;i++) {
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();
			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(pageViewChartData[i])) {
  				System.out.println(" *** timezoneCount pageViewChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount pageViewChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  		}
  		$("tbody > tr > td > a > font", 0).click();
  		sleep(1000);
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 8).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=6;i++) {
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();
			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(visitCountChartData[i])) {
  				System.out.println(" *** timezoneCount visitCountChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount visitCountChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  		}
  		$("tbody > tr > td > a > font", 2).click();
  		sleep(1000);
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 8).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=5;i++) {
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num1[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num1[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num1[i]).hover();
			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(newVisitChartData[i])) {
  				System.out.println(" *** timezoneCount newVisitChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount newVisitChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  		}
  		$("tbody > tr > td > a > font", 4).click();
  		sleep(1000);
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).hover();
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).hover();
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).hover();
		pageLoadCheck = $(".highcharts-tooltip").text().trim().split("�� ")[1];
		if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals("��湮��: 0.5")) {
			System.out.println(" *** timezoneCount reVisitChartData check Success !! *** ");
		} else {
			System.out.println(" *** timezoneCount reVisitChartData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
			close();
		}
  		$("tbody > tr > td > a > font", 6).click();
  		sleep(1000);
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 8).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=5;i++) {
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num1[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num1[i]).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num1[i]).hover();
			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(pureVisitwChartData[i])) {
  				System.out.println(" *** timezoneCount pureVisitwChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount pureVisitwChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  		}
  		System.out.println(" ! ----- timezoneCount End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void dayCount() {
  		System.out.println(" ! ----- dayCount Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("���Ϻ� ���Ӽ� ����")) {
			System.out.println(" *** dayCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** dayCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.07.05 ~ 2019.07.11", "3.00", "���", "1.29", "���", "1.14", "���", "0.14", "���", "1.14"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** dayCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.06.28 ~ 2019.07.04", "0.00", "0.00", "0.00", "0.00", "0.00"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** dayCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�����", "3.00", "1.29", "1.14", "0.14"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** dayCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		//��Ʈ
  		String[] dayChartData = {"��������: 21", "", "�湮��: 9", "", "�űԹ湮��: 8", "", "��湮��: 1", "", "���湮��: 8"};
  		for(int i=0;i<=8;i=i+2) {
  	  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 3).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 3).hover();
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 3).hover();
  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(dayChartData[i])) {
  				System.out.println(" *** timezoneCount dayChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount dayChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  			if(i >= 0 && i <= 6) {
  	  	  		$("tbody > tr > td > a > font", i).click();
  	  	  		sleep(1000);
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 3).waitUntil(visible, 10000);
  			}
  		}
  		System.out.println(" ! ----- dayCount End ----- ! ");
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