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

public class Convert {
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
  	public void convertPageInflow() {
  		System.out.println(" ! ----- convertPageInflow Start ----- ! ");
  		$("#m_stat > ul > li > a", 7).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("��ȯ ������ ������ó")) {
			System.out.println(" *** convertPageInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertPageInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3").text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".myValue", 1).click();
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('2019-08-08')");
  		js("$j('#calendar_data2').val('2019-08-09')");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n���", "119"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** convertPageInflow summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".convertPageInflowDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** convertPageInflow summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".convertPageInflowFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"������ó", "���Լ�", "��ü��ȯ��", "�ݼۼ�", "��ȯ-����", "��ȯ-�ֹ�", "��ȯ-����", "��ȯ-��û", "��ȯ-��Ÿ1", "��ȯ-��Ÿ2"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertPageInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  ��������", "36", "119", "18", "17", "17", "17", "17", "17", "17", "2.  ��������", "2", "0", "0", "0", "0", "0", "0", "0", "0"};
  		for(int i=0;i<=19;i++) {
  			if($(".statDataCenter", (i+10)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertPageInflow statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+10)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "38", "119", "18", "17", "17", "17", "17", "17", "17"};
  		for(int i=0;i<=9;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** convertPageInflow statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_10").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_10").click();
  		$("#cell_10").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("��ȯ-��Ÿ2")) {
  				System.out.println(" *** visitInflow cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_10").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_10").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("��ȯ-��Ÿ3")) {
  				System.out.println(" *** visitInflow cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] convertPageInflow = {"�������� : 119", "�������� : 18", "�������� : 36", "�������� : 2"};
  		for(int x=0;x<=3;x++) {
  			if(x==0 || x==1) {
				for(int i=0;i<=5;i++) {
	  		  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 0).hover();	
				}
				if($(".highcharts-tooltip", 0).text().trim().equals(convertPageInflow[x])) {
  	  				System.out.println(" *** convertPageInflow barChartData(" + x + ") check Success !! *** ");
  	  				$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > a", x).click();
  	  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 0).waitUntil(visible, 10000);  	  				
  	  			} else {
  	  				System.out.println(" *** convertPageInflow barChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim());
  	  				close();
  	  			}
  			} else if(x==2) {
  				for(int i=0;i<=1;i++) {
  					for(int y=0;y<=5;y++) {
  	  	  		  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  					}
	  	  		  	if($(".highcharts-tooltip", 0).text().trim().equals(convertPageInflow[x])) {
	  	  				System.out.println(" *** convertPageInflow barChartData(" + x + ") check Success !! *** ");
	  	  				$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 0).waitUntil(visible, 10000);
	  	  				x++;
	  	  			} else {
	  	  				System.out.println(" *** convertPageInflow barChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim());
	  	  				close();
	  	  			}	
  	  		  	}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("��ȯ ������ ������ó ����")) {
			System.out.println(" *** convertPageInflow Progress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertPageInflow Progress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** convertPageInflow progressSummaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow progressSummaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** convertPageInflow progressSummaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow progressSummaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** convertPageInflow progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** convertPageInflow progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertPageInflow progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "���������� ��ü��ȯ�� ����: 119", "2019.08.08 (��)", "���������� ��ü��ȯ�� ����: 0"};
		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** convertPageInflow Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** convertPageInflow Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- convertPageInflow End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void convertPagePath() {
  		System.out.println(" ! ----- convertPagePath Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("��ȯ ������ ���")) {
			System.out.println(" *** convertPagePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertPagePath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"*��ȯ-����", "*��ȯ-�ֹ�", "*��ȯ-��Ÿ1", "*��ȯ-��Ÿ2", "*��ȯ-��Ÿ3", "*��ȯ-��û", "*��ȯ-����"};
  		for(int i=0;i<=6;i++) {
  			if($("#data_summary > table > tbody > tr > td > table > tbody > tr > td > a", (i+1)).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** convertPagePath summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($("#data_summary > table > tbody > tr > td > table > tbody > tr > td > a", (i+1)).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(hidden, 10000);
  		$(".statFootRight", 0).waitUntil(visible, 10000);
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** convertPagePath no-data check Success !! *** ");
		} else {
			System.out.println(" *** convertPagePath no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(visible, 10000);
  		$(".statDataCenter", 4).waitUntil(visible, 10000);
  		if($(".statDataCenter", 4).text().trim().equals("25")) {
			System.out.println(" *** convertPagePath statTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertPagePath statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 4).text().trim());
			close();
  		}
  		String[] statTopTableData = {"������ �̵����(������)\n         \n������ �̵����(URL)", "��������", "�湮��"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertPagePath statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  (8)", "25", "16", "2.  (2) (9) (2)", "30", "1"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertPagePath statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "-", "17"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** convertPagePath statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮��")) {
  				System.out.println(" *** convertPagePath cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("�湮����")) {
  				System.out.println(" *** convertPagePath cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- convertPagePath End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void convertVisitDetail() {
  		System.out.println(" ! ----- convertVisitDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("��ȯ �湮�� ��")) {
			System.out.println(" *** convertVisitDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertVisitDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"����", "IP", "������ó", "�湮����", "�̵����", "��ȯ����", "ȸ��ID", "�湮�Ͻ�"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertVisitDetail statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertVisitDetail statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"10.77.129.79", "[��������]", "�űԹ湮", "25", "��ȯ-��Ÿ3", "", "2019-08-09 17:28:04"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", (i+9)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertVisitDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertVisitDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", (i+9)).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(hidden, 10000);
  		$(".statFootRight", 0).waitUntil(visible, 10000);
		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** convertVisitDetail no-data check Success !! *** ");
		} else {
			System.out.println(" *** convertVisitDetail no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(visible, 10000);
  		$(".statDataCenter", 12).waitUntil(visible, 10000);
  		if($(".statDataCenter", 12).text().trim().equals("25")) {
			System.out.println(" *** convertVisitDetail search check Success !! *** ");
		} else {
			System.out.println(" *** convertVisitDetail search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 12).text().trim());
			close();
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 1).click();
  		switchTo().window(2);
  		if($("span", 9).text().trim().split(" : ")[1].equals(statTableData[0])) {
			System.out.println(" *** convertVisitDetail WhoisIP check Success !! *** ");
		} else {
			System.out.println(" *** convertVisitDetail WhoisIP check Fail ... !@#$%^&*() *** ");
			System.out.println($("span", 9).text().trim().split(" : ")[1]);
			close();
  		}
  		switchTo().window(0);
  		System.out.println(" ! ----- convertVisitDetail End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void convertRequiredPeriod() {
  		System.out.println(" ! ----- convertRequiredPeriod Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("��ȯ���� �ҿ�Ⱓ")) {
			System.out.println(" *** convertRequiredPeriod pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertRequiredPeriod pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"�ҿ�Ⱓ", "��ȯ��", "��ȯ����", "��ȯ-����", "��ȯ-�ֹ�", "��ȯ-����", "��ȯ-��û", "��ȯ-��Ÿ1", "��ȯ-��Ÿ2"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  ����", "119", "100.00%", "17", "17", "17", "17", "17", "17"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+9)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+9)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "119", "100.00%", "17", "17", "17", "17", "17", "17"};
  		for(int i=0;i<=8;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_9").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("���籸���� �⺻������ �����ϴ�!");
  		$("#cell_check_9").click();
  		$("#cell_9").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("��ȯ-��Ÿ2")) {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_9").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("�⺻�������� �ǵ����ðڽ��ϱ�?\n���籸���� �ٽ� �������� �ʽ��ϴ�.");
  		$("#cell_9").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("��ȯ-��Ÿ3")) {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("��ȯ���� �ҿ�Ⱓ ����")) {
			System.out.println(" *** convertRequiredPeriod progress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertRequiredPeriod progress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** convertRequiredPeriod progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertRequiredPeriod progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "������ ��ü ��ȯ�� ����: 119", "2019.08.08 (��)", "������ ��ü ��ȯ�� ����: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** convertRequiredPeriod Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** convertRequiredPeriod Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- convertRequiredPeriod End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void convertInflowCount() {
  		System.out.println(" ! ----- convertInflowCount Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("��ȯ���� ����Ƚ��")) {
			System.out.println(" *** convertInflowCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertInflowCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"����Ƚ��", "��ȯ��"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertInflowCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1ȸ", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertInflowCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** convertInflowCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		$(".statDataCenter", 7).waitUntil(visible, 10000);
  		String[] subStatTopTableData = {"������ó ���", "��ȯ��"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(subStatTopTableData[i])) {
  				System.out.println(" *** convertInflowCount subStatTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount subStatTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] subStatTableData = {"1.   ��������", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(subStatTableData[i])) {
  				System.out.println(" *** convertInflowCount subStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount subStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] subStatFootTableData = {"�հ�", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(subStatFootTableData[i])) {
  				System.out.println(" *** convertInflowCount subStatFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount subStatFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("��ȯ���� ����Ƚ�� ����")) {
			System.out.println(" *** convertInflowCount progress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertInflowCount progress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressStatTableData = {"2019.08.08 (��)", "2019.08.09 (��)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** convertInflowCount progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("�հ�")) {
			System.out.println(" *** convertInflowCount progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertInflowCount progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (��)", "1ȸ�� ��ü ��ȯ�� ����: 119", "2019.08.08 (��)", "1ȸ�� ��ü ��ȯ�� ����: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("�� ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** convertInflowCount Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** convertInflowCount Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("�� ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- convertInflowCount End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void convertInflow() {
  		System.out.println(" ! ----- convertInflow Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("��ȯ���� ���԰��")) {
			System.out.println(" *** convertInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"������ó", "��ȯ��"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1. \n��������", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertInflow statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflow statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"�հ�", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** convertInflow statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflow statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- convertInflow End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void inflowIndirectConvert() {
  		System.out.println(" ! ----- inflowIndirectConvert Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("#cString_3", 0).text().trim().equals("���̹�(����Ʈ�˻�����) - ���Ժ� ������ȯ")) {
			System.out.println(" *** inflowIndirectConvert pageLoad Success !! *** ");
		} else {
			System.out.println(" *** inflowIndirectConvert pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** inflowIndirectConvert summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowIndirectConvert summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** inflowIndirectConvert summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowIndirectConvert summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"�˻���", "���� ��ȯ��", "���� ��ȯ��", "���� ��ȯ��+���� ��ȯ��"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** inflowIndirectConvert statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowIndirectConvert statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("�ڷᰡ �����ϴ�.")) {
			System.out.println(" *** inflowIndirectConvert statTableData check Success !! *** ");
		} else {
			System.out.println(" *** inflowIndirectConvert statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- inflowIndirectConvert End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}