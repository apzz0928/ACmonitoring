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

public class systemEnvironment {
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // 보안설정 변경
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability("requireWindowFocus", true); // ie text 입력 속도 향상을 위한 부분
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie 캐시 삭제를 위한 부분
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
	// 입력된 URL 정상 여부 확인
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
		if(pageLoadCheck.equals("원래이름")) {
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
  	public void webBrowser() {
  		System.out.println(" ! ----- webBrowser Start ----- ! ");
  		$("#m_stat > ul > li > a", 14).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("웹브라우저")) {
			System.out.println(" *** webBrowser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** webBrowser pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 웹브라우저", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** webBrowser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "Chrome", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** webBrowser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "Chrome", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** webBrowser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"웹브라우저", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** webBrowser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  Chrome", "29", "76.32%", "8.03", "2.  Firefox", "8", "21.05%", "25.00", "3.  Whale", "1", "2.63%", "30.00"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** webBrowser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** webBrowser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** webBrowser cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** webBrowser no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** webBrowser search check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** webBrowser cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"Chrome29", "76.32%", "Firefox8", "21.05%", "Whale1", "2.63%"};
  		for(int i=0,y=0;i<=2;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
				if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(PieChartData[y])) {
  	  				System.out.println(" *** webBrowser PieChartData(" + i + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** webBrowser PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  	  				close();
  	  			}
				y++;
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "Chrome의 방문수 추이: 21", "2019.08.08 (목)", "Chrome의 방문수 추이: 8", 
  				"2019.08.09 (금)", "Firefox의 방문수 추이: 8", "2019.08.08 (목)", "Firefox의 방문수 추이: 0", 
  				"2019.08.09 (금)", "Whale의 방문수 추이: 1", "2019.08.08 (목)", "Whale의 방문수 추이: 0"};
		for(int i=0,y=0;i<=2;i++) {
	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int z=0;z<=5;z++) {
  		  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int a=0;a<=1;a++) {
  	  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[a].equals(LineChartData[y])) {
  	  					System.out.println(" *** webBrowser LineChartData(" + y + ") check Success !! *** ");
  	  				} else {
  	  					System.out.println(" *** webBrowser LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  					System.out.println("fail : " + $(".highcharts-tooltip", 1).text().trim().split("● ")[a]);
  	  					close();
  	  	  			}
  	  	  			y++;
  				}
  			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("웹브라우저 추이")) {
			System.out.println(" *** webBrowser pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** webBrowser pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "29"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** webBrowser Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** webBrowser Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** webBrowser Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** webBrowser Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** webBrowser Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** webBrowser Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "Chrome의 방문수 추이: 21", "2019.08.08 (목)", "Chrome의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** webBrowser Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** webBrowser Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("웹브라우저")) {
  			System.out.println(" *** webBrowser window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** webBrowser window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- webBrowser End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void OS() {
  		System.out.println(" ! ----- OS Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("운영체제")) {
			System.out.println(" *** OS pageLoad Success !! *** ");
		} else {
			System.out.println(" *** OS pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 운영체제", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** OS summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "Windows", "38", "322.22%\n상승", "12.18", "1118.00%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** OS summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "Windows", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** OS summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"운영체제", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** OS statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  Windows", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** OS statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** OS statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** OS no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** OS no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** OS search check Success !! *** ");
  			} else {
  				System.out.println(" *** OS search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** OS cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** OS cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** OS cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** OS cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"Windows38", "100.00%"};
	    for(int x=0;x<=5;x++) {
	        $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
	    }
	    for(int x=0;x<=1;x++) {
	        if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(PieChartData[x])) {
	            System.out.println(" *** OS PieChartData(" + x + ") check Success !! *** ");
	        } else {
	            System.out.println(" *** OS PieChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
	            System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
	            close();
	        }
	    }
  		String[] LineChartData = {"2019.08.09 (금)", "Windows의 방문수 추이: 30", "2019.08.08 (목)", "Windows의 방문수 추이: 8"};
  		for(int i=0,y=0;i<=1;i++) {
  		    for(int x=0;x<=5;x++) {
  		        $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();	
  		    }
  		    for(int x=0;x<=1;x++) {
  		        if($(".highcharts-tooltip", 1).text().trim().split("● ")[x].equals(LineChartData[y])) {
  		            System.out.println(" *** OS visitCountChartData(" + y + ") check Success !! *** ");
  		        } else {
  		            System.out.println(" *** OS visitCountChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  		            System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
  		            close();
  		        }
  		        y++;
  		    }
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("운영체제 추이")) {
			System.out.println(" *** OS pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** OS pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** OS Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** OS Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** OS Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** OS Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** OS Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** OS Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "Windows의 방문수 추이: 30", "2019.08.08 (목)", "Windows의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** OS Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** OS Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("운영체제")) {
  			System.out.println(" *** OS window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** OS window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- OS End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void monitorResolution() {
  		System.out.println(" ! ----- monitorResolution Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("모니터해상도")) {
			System.out.println(" *** monitorResolution pageLoad Success !! *** ");
		} else {
			System.out.println(" *** monitorResolution pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 모니터해상도", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** monitorResolution summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "1920*1080", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** monitorResolution summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "1920*1080", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** monitorResolution summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"모니터해상도", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** monitorResolution statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  1920*1080", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** monitorResolution statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** monitorResolution statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** monitorResolution no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** monitorResolution search check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** monitorResolution cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** monitorResolution cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"1920*108038", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** monitorResolution PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "1920*1080의 방문수 추이: 30", "2019.08.08 (목)", "1920*1080의 방문수 추이: 8"};
  		for(int i=0,y=0;i<=1;i++) {
  		    for(int x=0;x<=5;x++) {
  		        $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();	
  		    }
  		    for(int x=0;x<=1;x++) {
  		        if($(".highcharts-tooltip", 1).text().trim().split("● ")[x].equals(LineChartData[y])) {
  		            System.out.println(" *** monitorResolution visitCountChartData(" + y + ") check Success !! *** ");
  		        } else {
  		            System.out.println(" *** monitorResolution visitCountChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  		            System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
  		            close();
  		        }
  		        y++;
  		    }
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("모니터해상도 추이")) {
			System.out.println(" *** monitorResolution pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** monitorResolution pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** monitorResolution Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "8"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** monitorResolution Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** monitorResolution Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** monitorResolution Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** monitorResolution Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** monitorResolution Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "1920*1080의 방문수 추이: 30", "2019.08.08 (목)", "1920*1080의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** monitorResolution Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** monitorResolution Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("모니터해상도")) {
  			System.out.println(" *** monitorResolution window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** monitorResolution window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- monitorResolution End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void useColor() {
  		System.out.println(" ! ----- useColor Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("사용 색상수")) {
			System.out.println(" *** useColor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** useColor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 색상수", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** useColor summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "24 비트 (16,700,000 colors)", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** useColor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "24 비트 (16,700,000 colors)", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** useColor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"사용 색상수", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** useColor statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  24 비트 (16,700,000 colors)", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** useColor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** useColor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** useColor cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** useColor cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** useColor cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** useColor cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"24 비트 (16,700,000 colors)38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** useColor PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "24 비트 (16,700,000 colors)의 방문수 추이: 30", "2019.08.08 (목)", "24 비트 (16,700,000 colors)의 방문수 추이: 8"};
  		for(int i=0,y=0;i<=1;i++) {
  		    for(int x=0;x<=5;x++) {
  		        $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();	
  		    }
  		    for(int x=0;x<=1;x++) {
  		        if($(".highcharts-tooltip", 1).text().trim().split("● ")[x].equals(LineChartData[y])) {
  		            System.out.println(" *** useColor LineChartData(" + y + ") check Success !! *** ");
  		        } else {
  		            System.out.println(" *** useColor LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  		            System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
  		            close();
  		        }
  		        y++;
  		    }
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("사용 색상수 추이")) {
			System.out.println(" *** useColor pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** useColor pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** useColor Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** useColor Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** useColor Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** useColor Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** useColor Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** useColor Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "24 비트 (16,700,000 colors)의 방문수 추이: 30", "2019.08.08 (목)", "24 비트 (16,700,000 colors)의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** useColor Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** useColor Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("사용 색상수")) {
  			System.out.println(" *** useColor window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** useColor window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- useColor End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void cookie() {
  		System.out.println(" ! ----- cookie Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("쿠키가능여부")) {
			System.out.println(" *** cookie pageLoad Success !! *** ");
		} else {
			System.out.println(" *** cookie pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 쿠키가능여부", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** cookie summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "사용가능", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** cookie summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "사용가능", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** cookie summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"쿠키가능여부", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** cookie statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  사용가능", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** cookie statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** cookie statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** cookie cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** cookie cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** cookie cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** cookie cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"사용가능38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** cookie PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "사용가능의 방문수 추이: 30", "2019.08.08 (목)", "사용가능의 방문수 추이: 8"};
  		for(int i=0,y=0;i<=1;i++) {
  		    for(int x=0;x<=5;x++) {
  		        $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();	
  		    }
  		    for(int x=0;x<=1;x++) {
  		        if($(".highcharts-tooltip", 1).text().trim().split("● ")[x].equals(LineChartData[y])) {
  		            System.out.println(" *** cookie LineChartData(" + y + ") check Success !! *** ");
  		        } else {
  		            System.out.println(" *** cookie LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  		            System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
  		            close();
  		        }
  		        y++;
  		    }
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(5);
  		if($("h3", 0).text().trim().equals("쿠키가능여부 추이")) {
			System.out.println(" *** cookie pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** cookie pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** cookie Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** cookie Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** cookie Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** cookie Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** cookie Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** cookie Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "사용가능의 방문수 추이: 30", "2019.08.08 (목)", "사용가능의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** cookie Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** cookie Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("쿠키가능여부")) {
  			System.out.println(" *** cookie window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** cookie window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- cookie End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void javaScriptUse() {
  		System.out.println(" ! ----- javaScriptUse Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("자바스크립트 가능여부")) {
			System.out.println(" *** javaScriptUse pageLoad Success !! *** ");
		} else {
			System.out.println(" *** javaScriptUse pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 자바스크립트 가능여부", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** javaScriptUse summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "사용불가능", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** javaScriptUse summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "사용불가능", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** javaScriptUse summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"자바스크립트 가능여부", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** javaScriptUse statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  사용불가능", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** javaScriptUse statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** javaScriptUse statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** javaScriptUse cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** javaScriptUse cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"사용불가능38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[i])) {
  				System.out.println(" *** javaScriptUse PieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse PieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "사용불가능의 방문수 추이: 30", "2019.08.08 (목)", "사용불가능의 방문수 추이: 8"};
  		for(int i=0,y=0;i<=1;i++) {
  		    for(int x=0;x<=5;x++) {
  		        $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();	
  		    }
  		    for(int x=0;x<=1;x++) {
  		        if($(".highcharts-tooltip", 1).text().trim().split("● ")[x].equals(LineChartData[y])) {
  		            System.out.println(" *** javaScriptUse LineChartData(" + y + ") check Success !! *** ");
  		        } else {
  		            System.out.println(" *** javaScriptUse LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  		            System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
  		            close();
  		        }
  		        y++;
  		    }
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(6);
  		if($("h3", 0).text().trim().equals("자바스크립트 가능여부 추이")) {
			System.out.println(" *** javaScriptUse pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** javaScriptUse pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** javaScriptUse Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** javaScriptUse Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** javaScriptUse Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptUse Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** javaScriptUse Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** javaScriptUse Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "사용불가능의 방문수 추이: 30", "2019.08.08 (목)", "사용불가능의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** javaScriptUse Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** javaScriptUse Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("자바스크립트 가능여부")) {
  			System.out.println(" *** javaScriptUse window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** javaScriptUse window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- javaScriptUse End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void javaScriptVersion() {
  		System.out.println(" ! ----- javaScriptVersion Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("자바스크립트 버전")) {
			System.out.println(" *** javaScriptVersion pageLoad Success !! *** ");
		} else {
			System.out.println(" *** javaScriptVersion pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석기간", "방문이 높은 자바스크립트 버전", "방문수", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** javaScriptVersion summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "자바스크립트1.2버전", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** javaScriptVersion summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "자바스크립트1.2버전", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** javaScriptVersion summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"자바스크립트 버전", "방문수", "방문비율", "방문당페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** javaScriptVersion statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  자바스크립트1.2버전", "30", "78.95%", "8.77", "2.  자바스크립트1.3버전", "8", "21.05%", "25.00"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** javaScriptVersion statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** javaScriptVersion statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당페이지뷰")) {
  				System.out.println(" *** javaScriptVersion cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당체류시간")) {
  				System.out.println(" *** javaScriptVersion cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] PieChartData = {"자바스크립트1.2버전30", "78.95%", "자바스크립트1.3버전8", "21.05%"};
  		for(int x=0,y=0;x<=1;x++) {
  	  		for(int i=0;i<=5;i++) {
  	  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).hover();	
  	  		}
  			for(int i=0;i<=1;i++) {//툴팁1 확인
  				if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(PieChartData[y])) {
  	  				System.out.println(" *** javaScriptVersion PieChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** javaScriptVersion PieChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "자바스크립트1.2버전의 방문수 추이: 22", "2019.08.08 (목)", "자바스크립트1.2버전의 방문수 추이: 8",
  				"2019.08.09 (금)", "자바스크립트1.3버전의 방문수 추이: 8", "2019.08.08 (목)", "자바스크립트1.3버전의 방문수 추이: 0"};
  		for(int i=0,y=0;i<=1;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			for(int x=0;x<=1;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
  				for(int z=0;z<=5;z++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
  				}
  				for(int a=0;a<=1;a++) {
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[a].equals(LineChartData[y])) {
  						System.out.println(" *** javaScriptVersion LineChartData(" + y + ") check Success !! *** ");
  					} else {
  						System.out.println(" *** javaScriptVersion LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  						System.out.println("fail : " + $(".highcharts-tooltip", 1).text().trim().split("● ")[a]);
  						close();
  					}
  					y++;
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(7);
  		if($("h3", 0).text().trim().equals("자바스크립트 버전 추이")) {
			System.out.println(" *** javaScriptVersion pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** javaScriptVersion pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "30"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** javaScriptVersion Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** javaScriptVersion Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** javaScriptVersion Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** javaScriptVersion Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** javaScriptVersion Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** javaScriptVersion Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "자바스크립트1.2버전의 방문수 추이: 22", "2019.08.08 (목)", "자바스크립트1.2버전의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** javaScriptVersion Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** javaScriptVersion Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("자바스크립트 버전")) {
  			System.out.println(" *** javaScriptVersion window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** javaScriptVersion window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- javaScriptVersion End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}