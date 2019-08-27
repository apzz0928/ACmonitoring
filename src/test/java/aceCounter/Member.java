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
  	public void memberSummary() {
  		System.out.println(" ! ----- memberSummary Start ----- ! ");
  		$("#m_stat > ul > li > a", 13).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("회원 요약")) {
			System.out.println(" *** memberSummary pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberSummary pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3").text().trim());
			close();
  		}
  		$("#date_range1 > a > img", 0).click();
  		$(".tabcontent.defaultOpen").waitUntil(visible, 10000);
  		$("#user_srt_date", 0).click();
  		js("$j('#user_srt_date').val('2019-08-08')");
  		js("$j('#user_end_date').val('2019-08-09')");
  		$(".btn_srh").click();
  		$(".black2", 0).waitUntil(visible, 10000);
  		if($(".black2", 0).text().trim().equals("(2019-08-08~2019-08-09)")) {
			System.out.println(" *** memberSummary calenderSet Success !! *** ");
		} else {
			System.out.println(" *** memberSummary calenderSet Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		String[] widgetTitle = {"방문 vs 로그인수", "방문 vs 회원가입수"};
  		for(int i=0;i<=1;i++) {
  			if($(".w_handle > b", i).text().trim().equals(widgetTitle[i])) {
  				System.out.println(" *** memberSummary widgetTitle (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberSummary widgetTitle (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".w_handle > b", i).text().trim());
  				close();
  			}
  		}
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 3).waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		js("$j('.highcharts-markers.highcharts-series-1.highcharts-tracker').css('display', 'none')");
  		String[] widgetChart = {"08/08 (목)", "방문수: 8", "08/09 (금)", "방문수: 30", "08/08 (목)", "방문수: 8", "08/09 (금)", "방문수: 30"};
  		for(int i=0,z=0,a=0;i<=3;i++) {
  			for(int x=0;x<=5;x++) {
  		  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();
  			}
  			if(i>=2) {
  				a = 1;
  			}
  			for(int y=0;y<=1;y++) {
  				if($(".highcharts-tooltip", a).text().trim().split("● ")[y].equals(widgetChart[z])) {
  	  				System.out.println(" *** memberSummary widgetChart (" + z + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** memberSummary widgetChart (" + z + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println(widgetChart[z]);
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[y]);
  	  				close();
  				}
  				z++;
  			}
  		}
  		$(".close_wiget", 1).click();
  		$("#widget2").waitUntil(hidden, 10000);
  		js("openWidgetRemoconSub('C', '11')");
  		sleep(500);
  		$(".set_bottom").waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 9).waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		sleep(1500);
  		$(".btn_close").click();
  		$("#widget3 > .w_title > table > tbody > tr > .w_handle > b").waitUntil(visible, 10000);
  		if($("#widget3 > .w_title > table > tbody > tr > .w_handle > b").text().trim().equals("방문 vs 회원가입수")) {
				System.out.println(" *** memberSummary widget del&add check Success !! *** ");
			} else {
				System.out.println(" *** memberSummary widget del&add check Fail ... !@#$%^&*() *** ");
				System.out.println($("#widget3 > .w_title > table > tbody > tr > .w_handle > b").text().trim());
				close();
  		}
  		$("#date_range1 > a > img", 1).scrollIntoView(false);
  		$("#date_range1 > a > img", 1).click();
  		$("#date_range1 > a > img", 1).waitUntil(hidden, 10000);
  		if(!$(".black2", 0).text().trim().equals("(2019-08-08~2019-08-09)")) {
			System.out.println(" *** memberSummary defalut set restore Success !! *** ");
		} else {
			System.out.println(" *** memberSummary defalut set restore Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		System.out.println(" ! ----- memberSummary End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void signupTermination() {
  		System.out.println(" ! ----- signupTermination Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("회원가입/해지")) {
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
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n상승", "0", "-", "0.00%", "-", "0", "-", "0.00%"};
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
  		String[] statTableData = {"2019.08.08 00시", "0", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** signupTermination statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** signupTermination statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("회원가입/해지 > 회원가입 상세")) {
			System.out.println(" *** signupTermination pageLoad Detail Success !! *** ");
		} else {
			System.out.println(" *** signupTermination pageLoad Detail Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		if($(".summaryDataCenter", 0).text().trim().equals("0")) {
			System.out.println(" *** signupTermination summaryDataCenter Detail Success !! *** ");
		} else {
			System.out.println(" *** signupTermination summaryDataCenter Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".summaryDataCenter", 0).text().trim());
			close();
  		}
  		if($(".summaryDataRight", 0).text().trim().equals("없음")) {
			System.out.println(" *** signupTermination summaryDataRight Detail Success !! *** ");
		} else {
			System.out.println(" *** signupTermination summaryDataRight Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".summaryDataRight", 0).text().trim());
			close();
  		}
  		String[] statDataTable = {"순번", "회원ID", "방문일시", "몇회 방문후 가입"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statDataTable[i])) {
  				System.out.println(" *** signupTermination statDataTable Detail (" + i + ") Success !! *** ");
  			} else {
  				System.out.println(" *** signupTermination statDataTable Detail (" + i + ") Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  	  		}	
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** signupTermination statFootRight Detail Success !! *** ");
		} else {
			System.out.println(" *** signupTermination statFootRight Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- signupTermination End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void newVisitVSreVisitSignup() {
  		System.out.println(" ! ----- newVisitVSreVisitSignup Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("신규방문vs재방문가입")) {
			System.out.println(" *** newVisitVSreVisitSignup pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newVisitVSreVisitSignup pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "36", "300.00%\n상승", "0", "-", "0.00%", "-", "2", "상승", "0", "-", "0.00%"};
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
  		String[] statTableData = {"2019.08.08 (목)", "8", "0", "0.00%", "0", "0", "2019.08.09 (금)", "28", "0", "0.00%", "2", "0"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+8)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newVisitVSreVisitSignup statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newVisitVSreVisitSignup statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "36", "0", "0.00%", "2", "0"};
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
  	@Test(priority = 4)
  	public void loginCount() {
  		System.out.println(" ! ----- loginCount Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("로그인 횟수")) {
			System.out.println(" *** loginCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** loginCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n상승", "0", "-", "0.00%"};
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
  		String[] statTableData = {"2019.08.08 (목)", "8", "0", "2019.08.09 (금)", "30", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** loginCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** loginCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
		switchTo().window(2); 
		if($("h3", 0).text().trim().equals("로그인 횟수 > 회원 상세")) {
			System.out.println(" *** loginCount pageLoad Progress Success !! *** "); }
		else { 
			System.out.println(" *** loginCount pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim()); close(); 
		} 
		if($(".summaryDataCenter", 0).text().trim().equals("0")) {
			System.out.println(" *** loginCount summaryDataCenter Detail Success !! *** ");
		} else {
			System.out.println(" *** loginCount summaryDataCenter Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".summaryDataCenter", 0).text().trim());
			close();
  		}
  		if($(".summaryDataRight", 0).text().trim().equals("-")) {
			System.out.println(" *** loginCount summaryDataRight Detail Success !! *** ");
		} else {
			System.out.println(" *** loginCount summaryDataRight Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".summaryDataRight", 0).text().trim());
			close();
  		}
  		String[] statDataTable = {"순번", "회원ID", "로그인일시", "연령대", "성별"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statDataTable[i])) {
  				System.out.println(" *** loginCount statDataTable Detail (" + i + ") Success !! *** ");
  			} else {
  				System.out.println(" *** loginCount statDataTable Detail (" + i + ") Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  	  		}	
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** loginCount statFootRight Detail Success !! *** ");
		} else {
			System.out.println(" *** loginCount statFootRight Detail Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		switchTo().window(0); 
  		System.out.println(" ! ----- loginCount End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void loginVisitDetail() {
  		System.out.println(" ! ----- loginVisitDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("로그인 방문 상세")) {
			System.out.println(" *** loginVisitDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** loginVisitDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
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
  		String[] statDataTableData = {"순번", "회원ID", "로그인 일시", "연령대", "성별"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statDataTableData[i])) {
  				System.out.println(" *** loginVisitDetail statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** loginVisitDetail statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}	
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** loginVisitDetail statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** loginVisitDetail statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- loginVisitDetail End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void memberAttribute() {
  		System.out.println(" ! ----- memberAttribute Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("회원 특성")) {
			System.out.println(" *** memberAttribute pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberAttribute pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
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
  		String[] statDataTableData = {"연령대 vs 성별\n 회원 그룹명 vs 성별", "소계", "로그인 횟수", "로그인비율", "로그인횟수", "로그인비율", "남성", "여성"
  				, "알수없음", "남성", "여성"};
  		for(int i=0;i<=10;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statDataTableData[i])) {
  				System.out.println(" *** memberAttribute statDataTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttribute statDataTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}	
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** memberAttribute statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** memberAttribute statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- memberAttribute End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void memberLogin() {
  		System.out.println(" ! ----- memberLogin Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("회원별 로그인현황")) {
			System.out.println(" *** memberLogin pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberLogin pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
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
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** memberLogin statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** memberLogin statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- memberLogin End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void loginTerm() {
  		System.out.println(" ! ----- loginTerm Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("로그인 간격")) {
			System.out.println(" *** loginTerm pageLoad Success !! *** ");
		} else {
			System.out.println(" *** loginTerm pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
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
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** loginTerm statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** loginTerm statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- loginTerm End ----- ! ");
  	}
  	//@Test(priority = 99)
  	public void increaseVisit() {
  		System.out.println(" ! ----- increaseVisit Start ----- ! ");
		open("http://apzz092888.blogspot.com/");
		$(".sub24").waitUntil(visible, 10000);
		for(int i=1;i<=24;i++) {
			$(".sub" + i).scrollIntoView(false);
			$(".sub" + i).click();
	  		System.out.println("sub" + i + " 클릭");
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