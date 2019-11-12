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
  	public void pageView() {
  		System.out.println(" ! ----- pageView Start ----- ! ");
  		$("#m_stat > ul > li > a", 3).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("��������")) {
			System.out.println(" *** pageView pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pageView pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$("select[name=vtype]", 0).selectOption("�ϰ����κ���");
  		alertCheck("pageView dateViewType check msg", "�Ϸ缱�� �ÿ� �ð������� ��ȸ�ϽǼ� �ֽ��ϴ�.");
  		$(".myValue", 1).click();
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('2019-08-08')");
  		js("$j('#calendar_data2').val('2019-08-09')");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();  		
  		$("select[name=vtype]", 0).waitUntil(visible, 10000);
  		$("select[name=vtype]", 0).selectOption("�ϰ����κ���");  		
  		$("td.statDataRight", 3).waitUntil(hidden, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "��������", "�湮��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** pageView summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "463", "5044.44%\n���", "38", "322.22%\n���", "12.18"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pageView summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "1.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pageView summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "��������"
  				,"�ֱ�1�������\n        \n�ֱ�3�������\n        \n�ֱ�1�����", "����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** pageView statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "8", "0", "2019.08.09 (��)", "455", "8", "+447"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pageView statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "463(232)", "16(8)", "+ 447"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pageView statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageView statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** pageView cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** pageView cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� %")) {
  				System.out.println(" *** pageView cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** pageView cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] BarChartData = {"2019.08.08 (��)", "��������: 8", "2019.08.09 (��)", "��������: 455"};
  		for(int i=0,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  					System.out.println(" *** pageView BarChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** pageView BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		System.out.println(" ! ----- pageView End ----- ! ");
  	}
  	//@Test(priority = 2)
  	public void visitCount() {
  		System.out.println(" ! ----- visitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("�湮��")) {
			System.out.println(" *** visitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮��", "�űԹ湮��", "��湮��"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** visitCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "3", "50.00%\n���", "3", "50.00%\n���", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "2", "2", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�湮��", "�űԹ湮��"
  				,"��湮��" ,"�ֱ�1�������\n        \n�ֱ�3�������\n        \n�ֱ�1�����", "����"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "8", "0", "6", "+2", "2019.08.09 (��)", "30", "28", "2", "6", "+24"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "38(19)", "36(18)", "2(1)", "12(6)", "+26"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** visitCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� %")) {
  				System.out.println(" *** visitCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] BarChartData = {"2019.08.08 (��)", "�湮��: 8", "2019.08.09 (��)", "�湮��: 30"};
  		for(int i=0,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  					System.out.println(" *** visitCount BarChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** visitCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		System.out.println(" ! ----- visitCount End ----- ! ");
  	}
  	//@Test(priority = 3)
  	public void newVisitCount() {
  		System.out.println(" ! ----- newVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("�űԹ湮��")) {
			System.out.println(" *** newVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮��", "�űԹ湮��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newVisitCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n���", "36", "300.00%\n���", "94.74%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "100.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�湮��", "�űԹ湮��"
  				, "�űԹ湮��", "�ֱ�1�������\n        \n�ֱ�3�������\n        \n�ֱ�1�����", "����"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newVisitCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "8", "100.00%", "6", "+2", "2019.08.09 (��)", "30", "28", "93.33%", "6", "+22"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "38(19)", "36(18)", "94.74%", "12(6)", "+24"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** newVisitCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� %")) {
  				System.out.println(" *** newVisitCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] BarChartData = {"2019.08.08 (��)", "�űԹ湮��: 8", "2019.08.09 (��)", "�űԹ湮��: 28"};
  		for(int i=0,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  					System.out.println(" *** newVisitCount BarChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** newVisitCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		System.out.println(" ! ----- newVisitCount End ----- ! ");
  	}
  	//@Test(priority = 4)
  	public void reVisitCount() {
  		System.out.println(" ! ----- reVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("��湮��")) {
			System.out.println(" *** reVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** reVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�湮��", "��湮��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** reVisitCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n���", "2", "���", "5.26%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** reVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** reVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�湮��", "��湮��"
  				, "��湮��", "�ֱ�1�������\n        \n�ֱ�3�������\n        \n�ֱ�1�����", "����"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** reVisitCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "0", "0.00%", "0", "0", "2019.08.09 (��)", "30", "2", "6.67%", "0", "2"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** reVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "38(19)", "2(1)", "5.26%", "0(0)", "+2"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** reVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** reVisitCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� %")) {
  				System.out.println(" *** reVisitCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** reVisitCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] BarChartData = {"2019.08.09 (��)", "��湮��: 2"};
  		for(int i=1,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  					System.out.println(" *** reVisitCount BarChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** reVisitCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		System.out.println(" ! ----- reVisitCount End ----- ! ");
  	}
  	//@Test(priority = 5)
  	public void newAndReVisitCount() {
  		System.out.println(" ! ----- newAndReVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("�űԹ湮�� ��湮")) {
			System.out.println(" *** newAndReVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newAndReVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�űԹ湮��", "��湮��", "�űԹ湮����"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newAndReVisitCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "36", "300.00%\n���", "2", "���", "94.74%", "5.26% p\n�϶�", "5.26%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newAndReVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "100.00%", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newAndReVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���", "�űԹ湮��"
  				, "��湮��", "�űԹ湮����"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newAndReVisitCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "0", "100.00%", "2019.08.09 (��)", "28", "2", "93.33%"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newAndReVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "36(18)", "2(1)", "94.74%"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newAndReVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�űԹ湮����")) {
  				System.out.println(" *** newAndReVisitCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��湮����")) {
  				System.out.println(" *** newAndReVisitCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** newAndReVisitCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] PieChartData = {"�űԹ湮36", "94.74%", "��湮2", "5.26%"};
  		for(int i=0,y=0;i<=1;i++) {
  		    for(int x=0;x<=5;x++) {
  		        $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  		    }
  		    for(int x=0;x<=1;x++) {
  		        if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(PieChartData[y])) {
  		            System.out.println(" *** newAndReVisitCount visitCountChartData(" + y + ") check Success !! *** ");
  		        } else {
  		            System.out.println(" *** newAndReVisitCount visitCountChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  		            System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  		            close();
  		        }
  		        y++;
  		    }
  		}
  		String[] LineChartData = {"2019.08.09 (��)", "�űԹ湮: 28", "2019.08.08 (��)", "�űԹ湮: 8"
  				, "2019.08.09 (��)", "��湮: 2", "2019.08.08 (��)", "��湮: 0"};
  		for(int i=0,z=0;i<=1;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("�� ")[y].equals(LineChartData[z])) {
  						System.out.println(" *** newAndReVisitCount LineChartData(" + z + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** newAndReVisitCount LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println($(".highcharts-tooltip", 1).text().trim().split("�� ")[y]);
  						close();
  					}
  					z++;
  				}
  			}
  		}
  		System.out.println(" ! ----- newAndReVisitCount End ----- ! ");
  	}
  	//@Test(priority = 6)
  	public void pureVisitCount() {
  		System.out.println(" ! ----- pureVisitCount Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("���湮��")) {
			System.out.println(" *** pureVisitCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pureVisitCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�ð����� ���湮��", "�ϰ����� ���湮��", "�ְ����� ���湮��"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** popularDirectory summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "36", "300.00%\n���", "36", "300.00%\n���", "36", "300.00%\n���", "36"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pureVisitCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "9", "9"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pureVisitCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���"
  				, "�ð����� ���湮��\n        \n�ϰ����� ���湮��\n        \n�ְ����� ���湮��\n        \n�������� ���湮��"
  				, "�ֱ�1�������\n        \n�ֱ�3�������\n        \n�ֱ�1�����", "����", "��� %"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** pureVisitCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "6", "+2", "2019.08.09 (��)", "28", "6", "+22"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pureVisitCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "36(18)", "12(6)", "+24"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pureVisitCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** pureVisitCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� %")) {
  				System.out.println(" *** pureVisitCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** pureVisitCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] BarChartData = {"2019.08.08 (��)", "�ϰ����� ���湮��: 8", "2019.08.09 (��)", "�ϰ����� ���湮��: 28"};
  		for(int i=0,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  					System.out.println(" *** pureVisitCount BarChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** pureVisitCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		System.out.println(" ! ----- pureVisitCount End ----- ! ");
  	}
  	//@Test(priority = 7)
  	public void visitPageview() {
  		System.out.println(" ! ----- visitPageview Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("�湮�� ��������")) {
			System.out.println(" *** visitPageview pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitPageview pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "��������", "�湮��"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** visitPageview summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "463", "5044.44%\n���", "38", "322.22%\n���", "12.18"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitPageview summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "1.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitPageview summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð����κ���\n        \n�ϰ����κ���\n        \n�ְ����κ���\n        \n�������κ���\n        \n�б�κ���"
  				, "��������", "�湮��", "�湮�� ��������" , "�ֱ�1�������\n        \n�ֱ�3�������\n        \n�ֱ�1�����", "����"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitPageview statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (��)", "8", "8", "1.00", "1.32", "-0.32", "2019.08.09 (��)", "455", "30", "15.17", "1.28", "+13.89"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitPageview statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�(���)", "463(232)", "38(19)", "12.18", "1.30", "+10.88"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitPageview statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("����")) {
  				System.out.println(" *** visitPageview cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� %")) {
  				System.out.println(" *** visitPageview cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitPageview cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-series.highcharts-series-2').css('display', 'none')");
  		String[] BarChartData = {"2019.08.08 (��)", "�湮����������: 1", "2019.08.09 (��)", "�湮����������: 15.17"};
  		for(int i=0,y=0;i<=1;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("�� ")[x].equals(BarChartData[y])) {
  					System.out.println(" *** visitInflow BarChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** visitInflow BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		System.out.println(" ! ----- visitPageview End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void timezoneCount() {
  		System.out.println(" ! ----- timezoneCount Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("�ð��뺰 ���Ӽ� ����")) {
			System.out.println(" *** timezoneCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** timezoneCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"�м� �Ⱓ", "�ð���� ��������", "�ð���� �湮��", "�ð���� �űԹ湮��", "�ð���� ��湮��"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** timezoneCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "9.65", "4978.95%\n���", "0.79", "315.79%\n���", "0.75", "294.74%\n���", "0.04", "���", "0.75"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** timezoneCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.19", "0.19", "0.19", "0.00", "0.19"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** timezoneCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�ð���", "��� ��������", "��� �湮��", "��� �űԹ湮��", "��� ��湮��"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�ð� ���", "9.65", "0.79", "0.75", "0.04"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** timezoneCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� ��湮��")) {
  				System.out.println(" *** timezoneCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� ���湮��")) {
  				System.out.println(" *** timezoneCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] BarChartData = {"09��", "��������: 16", "10��", "��������: 1", "11��", "��������: 1", "12��", "��������: 3", "13��", "��������: 1", "14��", "��������: 19", "15��"
  				, "��������: 38.5", "16��", "��������: 101", "17��", "��������: 51", "09��", "�湮��: 1.5", "10��", "�湮��: 1", "11��", "�湮��: 1", "12��", "�湮��: 1.5", "13��"
  				, "�湮��: 1", "14��", "�湮��: 2.5", "15��", "�湮��: 2.5", "16��", "�湮��: 5", "17��", "�湮��: 3", "09��", "�űԹ湮��: 1.5", "10��", "�űԹ湮��: 1", "11��"
  				, "�űԹ湮��: 1", "12��", "�űԹ湮��: 1", "13��", "�űԹ湮��: 1", "14��", "�űԹ湮��: 2", "15��", "�űԹ湮��: 2.5", "16��", "�űԹ湮��: 5", "17��", "�űԹ湮��: 3"
  				, "12��", "��湮��: 0.5", "14��", "��湮��: 0.5" , "09��", "���湮��: 1.5", "10��", "���湮��: 1", "11��", "���湮��: 1", "12��", "���湮��: 1", "13��", "���湮��: 1"
  				, "14��", "���湮��: 2", "15��", "���湮��: 2.5", "16��", "���湮��: 5", "17��", "���湮��: 3"};
  		int[] BarChartLegend = {1, 3, 5, 7};
  		for(int i=0,q=0;i<=4;i++) {
  			for(int x=0;x<=8;x++) {
  				js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  				if(i==3 && x==0) {
  					x = 3;
  				} else if (i==3 && x==6) {
  					break;
  				}
  				for(int y=0;y<=5;y++) {
  			        $(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", (x+9)).hover();
  				}
  				if(i==3 && x==3) {
  					x = 5;
  				}
  				for(int y=0;y<=1;y++) {
  					if($(".highcharts-tooltip", 0).text().trim().split("�� ")[y].equals(BarChartData[q])) {
  				        System.out.println(" *** timezoneCount BarChartData(" + q + ") check Success !! *** ");
  				        System.out.println("�迭 �� : " + BarChartData[q]);
  				        System.out.println("���� �� : " + $(".highcharts-tooltip", 0).text().trim().split("�� ")[y]);
  				    } else {
  				        System.out.println(" *** timezoneCount BarChartData(" + q + ") check Fail ... !@#$%^&*() *** ");
  				        System.out.println("�迭 �� : " + BarChartData[q]);
  				        System.out.println("���� �� : " + $(".highcharts-tooltip", 0).text().trim().split("�� ")[y]);
  				        //close();
  					}
  					q++;
  				}
  			}
			if(i==4) {
				i = 0;
			}
  			$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > a > font", BarChartLegend[i]).click();
  			sleep(1000);
			if(i==0 && q<=70) {
				break;
			}
  		}
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		
  		/*
  		int[] num = {9, 10, 11, 12, 13, 14, 15, 16, 17};
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=8;i++) {
  			for(int x=0;x<=5;x++) {
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();	
  			}
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
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=8;i++) {
  			for(int x=0;x<=5;x++) {
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();	
  			}
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
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=8;i++) {
  			for(int x=0;x<=5;x++) {
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();	
  			}
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
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 12).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
		for(int i=0;i<=5;i++) {
			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 12).hover();	
		}
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
		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		for(int i=0;i<=8;i++) {
  			for(int x=0;x<=5;x++) {
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", num[i]).hover();	
  			}
			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(pureVisitwChartData[i])) {
  				System.out.println(" *** timezoneCount pureVisitwChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timezoneCount pureVisitwChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  		}*/
  		System.out.println(" ! ----- timezoneCount End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void dayCount() {
  		System.out.println(" ! ----- dayCount Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("���Ϻ� ���Ӽ� ����")) {
			System.out.println(" *** dayCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** dayCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.03 ~ 2019.08.09", "67.43", "23151.72%\n���", "6.71", "4692.86%\n���", "6.43", "4492.86%\n���", "0.29", "���", "6.43"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** dayCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.27 ~ 2019.08.02", "0.29", "0.14", "0.14", "0.00", "0.14"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** dayCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"����", "��� ��������", "��� �湮��", "��� �űԹ湮��", "��� ��湮��"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** dayCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�����", "67.43", "6.71", "6.43", "0.29"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** dayCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� ��湮��")) {
  				System.out.println(" *** dayCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("��� ���湮��")) {
  				System.out.println(" *** dayCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] dayChartData = {"��������: 455", "", "�湮��: 30", "", "�űԹ湮��: 28", "", "��湮��: 2", "", "���湮��: 28"};
  		for(int i=0;i<=8;i=i+2) {
  	  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  			for(int x=0;x<=5;x++) {
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 4).hover();	
  			}
  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[1].equals(dayChartData[i])) {
  				System.out.println(" *** dayCount dayChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayCount dayChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[1]);
  				close();
  			}
  			if(i >= 0 && i <= 6) {
  	  	  		$("tbody > tr > td > a > font", i).click();
  	  	  		sleep(1000);
  	  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 4).waitUntil(visible, 10000);
  			}
  		}
  		System.out.println(" ! ----- dayCount End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}