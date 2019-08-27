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
  	public void visitorSummary() {
  		System.out.println(" ! ----- visitorSummary Start ----- ! ");
  		$("#m_stat > ul > li > a", 14).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("방문자 요약")) {
			System.out.println(" *** visitorSummary pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitorSummary pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3").text().trim());
			close();
  		}
  		$("#date_range1 > a > img", 0).click();
  		$(".tabcontent.defaultOpen").waitUntil(visible, 10000);
  		$("#user_srt_date", 0).click();
  		js("$j('#user_srt_date').val('2019-08-08')");
  		js("$j('#user_end_date').val('2019-08-08')");
  		$(".btn_srh").click();
  		$(".black2", 0).waitUntil(visible, 10000);
  		if($(".black2", 0).text().trim().equals("(2019-08-08~2019-08-08)")) {
			System.out.println(" *** visitorSummary calenderSet Success !! *** ");
		} else {
			System.out.println(" *** visitorSummary calenderSet Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		String[] widgetTitle = {"방문 빈도 분석", "지역별 방문자", "국가별 방문자", "회사/기관별 방문자", "사용 언어별 방문자", "ISP별 방문자"};
  		for(int i=0;i<=5;i++) {
  			if($(".w_handle > b", i).text().trim().equals(widgetTitle[i])) {
  				System.out.println(" *** visitorSummary widgetTitle (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitorSummary widgetTitle (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".w_handle > b", i).text().trim());
  				close();
  			}
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 5).waitUntil(visible, 10000);
  		String[] widgetChart = {"1회8", "100.00%", "알수없음8", "100.00%", "기타8", "100.00%", "알수없음8", "100.00%", "한국어8", "100.00%", "기타8", "100.00%"};
  		for(int i=0,z=0;i<=5;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();
  			}
			for(int y=0;y<=1;y++) {
				if($(".highcharts-tooltip", i).text().trim().split(": ")[y].equals(widgetChart[z])) {
	  				System.out.println(" *** visitorSummary widgetChart (" + z + ") check Success !! *** ");
	  			} else {
	  				System.out.println(" *** visitorSummary widgetChart (" + z + ") check Fail ... !@#$%^&*() *** ");
	  				System.out.println($(".highcharts-tooltip", i).text().trim().split(": ")[y]);
	  				close();
				}
				z++;
			}
  		}
  		$(".close_wiget", 5).click();
  		$("#widget6").waitUntil(hidden, 10000);
  		js("openWidgetRemoconSub('C', '12')");
  		sleep(500);
  		$(".set_bottom").waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 7).waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 6).click();
  		sleep(1500);
  		$(".btn_close").click();
  		$("#widget7 > .w_title > table > tbody > tr > .w_handle > b").waitUntil(visible, 10000);
  		if($("#widget7 > .w_title > table > tbody > tr > .w_handle > b").text().trim().equals("ISP별 방문자")) {
				System.out.println(" *** visitorSummary widget del&add check Success !! *** ");
			} else {
				System.out.println(" *** visitorSummary widget del&add check Fail ... !@#$%^&*() *** ");
				System.out.println($("#widget7 > .w_title > table > tbody > tr > .w_handle > b").text().trim());
				close();
  		}
  		$("#date_range1 > a > img", 1).scrollIntoView(false);
  		$("#date_range1 > a > img", 1).click();
  		$("#date_range1 > a > img", 1).waitUntil(hidden, 10000);
  		if(!$(".black2", 0).text().trim().equals("(2019-08-08~2019-08-08)")) {
			System.out.println(" *** visitorSummary defalut set restore Success !! *** ");
		} else {
			System.out.println(" *** visitorSummary defalut set restore Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		System.out.println(" ! ----- visitorSummary End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void recentlyVisitor() {
  		System.out.println(" ! ----- recentlyVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("최근 방문자")) {
			System.out.println(" *** recentlyVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** recentlyVisitor pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] statTableData = {"38", "2019-08-09 17:28:04", "10.77.129.79", "[직접유입]", "/index.html", "25", "전환-주문\n전환-예약\n전환-신청\n전환-기타3\n전환-기타2\n전환-기타1\n전환-가입"
  				,"", "Chrome 75.0"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+9)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** recentlyVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** recentlyVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+9)).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** recentlyVisitor no-data search check Success !! *** ");
			} else {
				System.out.println(" *** recentlyVisitor no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
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
  	@Test(priority = 3)
  	public void visitAnalysis() {
  		System.out.println(" ! ----- visitAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("방문 빈도 분석")) {
			System.out.println(" *** visitAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "1회", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "1회", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1회", "36", "94.74%", "0", "0.00%", "0", "12.67", "2회", "2", "5.26%", "0", "0.00%", "0", "3.50"};
  		for(int i=0;i<=13;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitAnalysis statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "0", "0.00%", "0", "12.18"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitAnalysis statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"1회36", "94.74%", "2회2", "5.26%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  	  	  				System.out.println(" *** visitAnalysis visitPieChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitAnalysis visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  				for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).hover();  					
  				}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitAnalysis visitPieChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitAnalysis visitPieChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] oneLineChartData = {"2019.08.09 (금)", "1회의 방문수 추이: 28", "2019.08.08 (목)", "1회의 방문수 추이: 8"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(oneLineChartData[i])) {
  	  	  				System.out.println(" *** visitAnalysis oneLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitAnalysis oneLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(oneLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitAnalysis oneLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitAnalysis oneLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		String[] twoLineChartData = {"2019.08.09 (금)", "2회의 방문수 추이: 2", "2019.08.08 (목)", "2회의 방문수 추이: 0"};
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 1).click();
  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();  			
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(twoLineChartData[i])) {
  	  	  				System.out.println(" *** visitAnalysis twoLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitAnalysis twoLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(twoLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** visitAnalysis twoLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** visitAnalysis twoLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("방문 빈도 분석 추이")) {
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
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** visitAnalysis Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitAnalysis Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** visitAnalysis Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** visitAnalysis Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "1회의 방문수 추이: 28", "2019.08.08 (목)", "1회의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** visitAnalysis Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** visitAnalysis Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- visitAnalysis End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void revisitTerm() {
  		System.out.println(" ! ----- revisitTerm Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("재방문간격")) {
			System.out.println(" *** revisitTerm pageLoad Success !! *** ");
		} else {
			System.out.println(" *** revisitTerm pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "당일", "2", "상승", "3.50", "상승", "00:07:28"};
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
  		String[] statTableData = {"1.  당일", "2", "100.00%", "0", "0.00%", "0", "3.50"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** revisitTerm statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "2", "100.00%", "0", "0.00%", "0", "3.50"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** revisitTerm statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitCountPieChartData = {"당일2", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitCountPieChartData[i])) {
  				System.out.println(" *** revisitTerm visitCountPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm visitCountPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] todayLineChartData = {"2019.08.09 (금)", "당일의 방문수 추이: 2", "2019.08.08 (목)", "당일의 방문수 추이: 0"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(todayLineChartData[i])) {
  	  	  				System.out.println(" *** revisitTerm todayLineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** revisitTerm todayLineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(todayLineChartData[(i+2)])) {
  	  	  				System.out.println(" *** revisitTerm todayLineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** revisitTerm todayLineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("재방문간격 추이")) {
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
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** revisitTerm Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** revisitTerm Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** revisitTerm Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** revisitTerm Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "당일의 방문수 추이: 2", "2019.08.08 (목)", "당일의 방문수 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** revisitTerm Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** revisitTerm Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- revisitTerm End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void regionVisitor() {
  		System.out.println(" ! ----- regionVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("지역별 방문자")) {
			System.out.println(" *** regionVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** regionVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "알수없음", "38", "322.22%\n상승", "12.18", "1118.00%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** regionVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "알수없음", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** regionVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  알수없음", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** regionVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** regionVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"알수없음38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  				System.out.println(" *** regionVisitor visitPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "알수없음의 방문수 추이: 30", "2019.08.08 (목)", "알수없음의 방문수 추이: 8"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[i])) {
  	  	  				System.out.println(" *** regionVisitor LineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** regionVisitor LineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[(i+2)])) {
  	  	  				System.out.println(" *** regionVisitor LineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** regionVisitor LineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("지역별 방문자 추이")) {
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
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** regionVisitor Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** regionVisitor Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** regionVisitor Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** regionVisitor Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "알수없음의 방문수 추이: 30", "2019.08.08 (목)", "알수없음의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** regionVisitor Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** regionVisitor Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- regionVisitor End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void nationVisitor() {
  		System.out.println(" ! ----- nationVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("국가별 방문자")) {
			System.out.println(" *** nationVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** nationVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "알수없음", "38", "322.22%\n상승", "12.18", "1118.00%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** nationVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "알수없음", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** nationVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"알수없음", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** nationVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** nationVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"기타38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  				System.out.println(" *** nationVisitor visitPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nationVisitor visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "알수없음의 방문수 추이: 30", "2019.08.08 (목)", "알수없음의 방문수 추이: 8"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[i])) {
  	  	  				System.out.println(" *** nationVisitor LineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** nationVisitor LineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[(i+2)])) {
  	  	  				System.out.println(" *** nationVisitor LineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** nationVisitor LineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		System.out.println(" ! ----- nationVisitor End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void companyVisitor() {
  		System.out.println(" ! ----- companyVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("회사/기관별 방문자")) {
			System.out.println(" *** companyVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** companyVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "-", "0", "하락", "0.00", "하락", "00:00:00"};
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
  		String[] statTableData = {"알수없음", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** companyVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** companyVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"알수없음38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  				System.out.println(" *** companyVisitor visitPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** companyVisitor visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "알수없음의 방문수 추이: 30", "2019.08.08 (목)", "알수없음의 방문수 추이: 8"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[i])) {
  	  	  				System.out.println(" *** companyVisitor LineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** companyVisitor LineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[(i+2)])) {
  	  	  				System.out.println(" *** companyVisitor LineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** companyVisitor LineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		System.out.println(" ! ----- companyVisitor End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void languageVisitor() {
  		System.out.println(" ! ----- languageVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("사용 언어별 방문자")) {
			System.out.println(" *** languageVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** languageVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "한국어", "38", "322.22%\n상승", "12.18", "1118.00%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** languageVisitor summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "한국어", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** languageVisitor summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  한국어", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** languageVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** languageVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"한국어38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  				System.out.println(" *** languageVisitor visitPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "한국어의 방문수 추이: 30", "2019.08.08 (목)", "한국어의 방문수 추이: 8"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[i])) {
  	  	  				System.out.println(" *** languageVisitor LineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** languageVisitor LineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[(i+2)])) {
  	  	  				System.out.println(" *** languageVisitor LineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** languageVisitor LineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
  		switchTo().window(5);
  		if($("h3", 0).text().trim().equals("사용 언어별 방문자 추이")) {
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
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** languageVisitor Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** languageVisitor Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** languageVisitor Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** languageVisitor Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "한국어의 방문수 추이: 30", "2019.08.08 (목)", "한국어의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** languageVisitor Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** languageVisitor Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- languageVisitor End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void TimeZone() {
  		System.out.println(" ! ----- TimeZone Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("TimeZone")) {
			System.out.println(" *** TimeZone pageLoad Success !! *** ");
		} else {
			System.out.println(" *** TimeZone pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크", "38", "322.22%\n상승", "12.18", "1118.42%\n상승", "00:00:57"};
  		for(int i=0;i<=6;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** TimeZone summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** TimeZone summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+88)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** TimeZone statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+88)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** TimeZone statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  				System.out.println(" *** TimeZone visitPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크 의 방문수 추이: 30", "2019.08.08 (목)", "[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크 의 방문수 추이: 8"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();	
  		}
  		for(int x=0;x<=1;x++) {
  			if(x==0) {//툴팁 1, 2를 확인용 분기
  				for(int i=0;i<=1;i++) {//툴팁1 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[i])) {
  	  	  				System.out.println(" *** TimeZone LineChartData(" + i + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** TimeZone LineChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			} else {
  		  		for(int i=0;i<=5;i++) {
  	  		  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).hover();	
  		  		}
  				for(int i=0;i<=1;i++) {//툴팁2 확인
  					if($(".highcharts-tooltip", 1).text().trim().split("● ")[i].equals(LineChartData[(i+2)])) {
  	  	  				System.out.println(" *** TimeZone LineChartData(" + (i+2) + ") check Success !! *** ");
  	  	  			} else {
  	  	  				System.out.println(" *** TimeZone LineChartData(" + (i+2) + ") check Fail ... !@#$%^&*() *** ");
  	  	  				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[i]);
  	  	  				close();
  	  	  			}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 21).click();
  		switchTo().window(6);
  		if($("h3", 0).text().trim().equals("TimeZone 추이")) {
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
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** TimeZone Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** TimeZone Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** TimeZone Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** TimeZone Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크 의 방문수 추이: 30", "2019.08.08 (목)", "[GMT+9] 서울, 도쿄, 오사카, 삿포로, 야쿠츠크 의 방문수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** TimeZone Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** TimeZone Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- TimeZone End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void ISPVisitor() {
  		System.out.println(" ! ----- ISPVisitor Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("ISP별 방문자")) {
			System.out.println(" *** ISPVisitor pageLoad Success !! *** ");
		} else {
			System.out.println(" *** ISPVisitor pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "UNKNOWN", "0", "하락", "0.00", "하락", "00:00:00"};
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
  		String[] statTableData = {"기타", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** ISPVisitor statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "12.18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** ISPVisitor statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] visitPieChartData = {"기타38", "100.00%"};
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		for(int i=0;i<=1;i++) {//툴팁1 확인
			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(visitPieChartData[i])) {
  				System.out.println(" *** ISPVisitor visitPieChartData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** ISPVisitor visitPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
  				close();
  			}
  		}
  		System.out.println(" ! ----- ISPVisitor End ----- ! ");
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