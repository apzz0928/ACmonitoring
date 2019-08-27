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

public class Content {
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
  	public void contentSummary() {
  		System.out.println(" ! ----- contentSummary Start ----- ! ");
  		$("#m_stat > ul > li > a", 11).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("콘텐츠 요약")) {
			System.out.println(" *** contentSummary pageLoad Success !! *** ");
		} else {
			System.out.println(" *** contentSummary pageLoad Fail ... !@#$%^&*() *** ");
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
			System.out.println(" *** contentSummary calenderSet Success !! *** ");
		} else {
			System.out.println(" *** contentSummary calenderSet Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		String[] widgetTitle = {"성취율이 높은 시나리오", "사이트 체류시간", "페이지별 체류시간(낮은순)", "파일다운로드", "페이지별 체류시간(높은순)", "인기있는 내부검색어"};
  		for(int i=0;i<=5;i++) {
  			if($(".w_handle > b", i).text().trim().equals(widgetTitle[i])) {
  				System.out.println(" *** contentSummary widgetTitle (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentSummary widgetTitle (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".w_handle > b", i).text().trim());
  				close();
  			}
  		}
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 3).waitUntil(visible, 10000);
  		String[] widgetChart = {"100", "35", "2", "1", "0", "0", "0", "3", "10", "15", "16", "16", "17", "19", "12", "12", "10", "10", "10", "10", "10"
  				, "1,776", "154", "54", "38", "38", "24", "19"};
  		for(int i=0;i<=27;i++) {
  			if($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g > text", i).text().trim().equals(widgetChart[i])) {
  				System.out.println(" *** contentSummary widgetChart (" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentSummary widgetChart (" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).text().trim());
  				close();
  			}
  		}
  		$(".close_wiget", 5).click();
  		$("#widget6").waitUntil(hidden, 10000);
  		js("openWidgetRemoconSub('C', '9')");
  		sleep(500);
  		$(".set_bottom").waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 10).waitUntil(visible, 10000);
  		$("tbody > tr > td > table > tbody > tr > td > a > img", 7).click();
  		sleep(1500);
  		$(".btn_close").click();
  		$("#widget7 > .w_title > table > tbody > tr > .w_handle > b").waitUntil(visible, 10000);
  		if($("#widget7 > .w_title > table > tbody > tr > .w_handle > b").text().trim().equals("인기있는 내부검색어")) {
				System.out.println(" *** contentSummary widget del&add check Success !! *** ");
			} else {
				System.out.println(" *** contentSummary widget del&add check Fail ... !@#$%^&*() *** ");
				System.out.println($("#widget7 > .w_title > table > tbody > tr > .w_handle > b").text().trim());
				close();
  		}
  		$("#date_range1 > a > img", 1).scrollIntoView(false);
  		$("#date_range1 > a > img", 1).click();
  		$("#date_range1 > a > img", 1).waitUntil(hidden, 10000);
  		if(!$(".black2", 0).text().trim().equals("(2019-08-08~2019-08-09)")) {
			System.out.println(" *** contentSummary defalut set restore Success !! *** ");
		} else {
			System.out.println(" *** contentSummary defalut set restore Fail ... !@#$%^&*() *** ");
			System.out.println($(".black2", 0).text().trim());
			close();	
  		}
  		System.out.println(" ! ----- contentSummary End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void Scenario() {
  		System.out.println(" ! ----- Scenario Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("시나리오")) {
			System.out.println(" *** Scenario pageLoad Success !! *** ");
		} else {
			System.out.println(" *** Scenario pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] statTableData = {"1.  시나리오명", "7", "17", "17", "100.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", (i+5)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** Scenario statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** Scenario statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+5)).text().trim());
  				close();
  			}
  		}
  		String[] scenarioTableData = {"페이지", "방문수", "/index.html", "/index.html", "17"};
  		for(int i=0;i<=4;i++) {
  			if($(".greyB", (i+1)).text().trim().equals(scenarioTableData[i])) {
  				System.out.println(" *** Scenario scenarioTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** Scenario scenarioTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".greyB", (i+1)).text().trim());
  				close();
  			}
  		}
  		String[] step_t1Table = {"전환-주문", "전환-예약", "전환-신청", "전환-기타3", "전환-기타2", "전환-기타1", "전환-가입"};
  		for(int i=0;i<=6;i++) {
  			if($(".step_t1", (i+2)).text().trim().equals(step_t1Table[i])) {
  				System.out.println(" *** Scenario step_t1Table(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** Scenario step_t1Table(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".step_t1", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] step_t2Table = {"100.00%", "100.00%", "100.00%", "100.00%", "100.00%", "100.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".step_t2", i).text().trim().equals(step_t2Table[i])) {
  				System.out.println(" *** Scenario step_t2Table(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** Scenario step_t2Table(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".step_t2", i).text().trim());
  				close();
  			}
  		}
  		String[] step_t3Table = {"(방문수 : 17)", "(방문수 : 17)", "(방문수 : 17)", "(방문수 : 17)", "(방문수 : 17)", "(방문수 : 17)", "(방문수 : 17)"};
  		for(int i=0;i<=6;i++) {
  			if($(".step_t3", i).text().trim().equals(step_t3Table[i])) {
  				System.out.println(" *** Scenario step_t3Table(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** Scenario step_t3Table(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".step_t3", i).text().trim());
  				close();
  			}
  		}
  		String[] step_t4Table = {"0 이탈", "0.00%", "0 이탈", "0.00%", "0 이탈", "0.00%", "0 이탈", "0.00%", "0 이탈", "0.00%", "0 이탈", "0.00%", "0 이탈", "0.00%"};
  		for(int i=0;i<=11;i++) {
  			if($(".step_t4", i).text().trim().equals(step_t4Table[i])) {
  				System.out.println(" *** Scenario step_t4Table(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** Scenario step_t4Table(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".step_t4", (i+2)).text().trim());
  				close();
  			}
  		}
  		js("openScenarioDetail( '7Iuc64KY66as7Jik66qF', 0)");
  		$(".s_title > tbody > tr > td > a > img").waitUntil(visible, 10000);
  		if($(".s_title > tbody > tr > td > b").text().trim().equals("방문경로 상세")) {
				System.out.println(" *** Scenario layer check Success !! *** ");
			} else {
				System.out.println(" *** Scenario layer check Fail ... !@#$%^&*() *** ");
				System.out.println($(".s_title > tbody > tr > td > b").text().trim());
				close();
  		} 		
  		System.out.println(" ! ----- Scenario End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void innerBannerAdv() {
  		System.out.println(" ! ----- innerBannerAdv Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("내부 배너광고")) {
			System.out.println(" *** innerBannerAdv pageLoad Success !! *** ");
		} else {
			System.out.println(" *** innerBannerAdv pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "17", "88.89%\n상승", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** innerBannerAdv summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** innerBannerAdv summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  내부배너2", "17", "0", "0", "0", "0.00%", "0.00%"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", (i+9)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** innerBannerAdv statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "17", "0", "0", "0", "0.00%", "0.00%"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** innerBannerAdv statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** innerBannerAdv no-data search check Success !! *** ");
			} else {
				System.out.println(" *** innerBannerAdv no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] innerBannerAdvBarChartData = {"내부배너2", "17"};
		for(int i=0;i<=5;i++) {
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
		}
		for(int y=0;y<=1;y++) {
  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(innerBannerAdvBarChartData[y])) {
				System.out.println(" *** innerBannerAdv pageviewBarChartData(" + y + ") check Success !! *** ");
			} else {
				System.out.println(" *** innerBannerAdv pageviewBarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".highcharts-tooltip", 0).text().trim().split(" : ")[y]);
				close();
  			}
		}
  		String[] innerBannerAdvLineChartData = {"2019.08.09 (금)", "내부배너2의 노출수 추이: 9", "2019.08.08 (목)", "내부배너2의 노출수 추이: 8"};
		for(int z=0;z<=5;z++) {
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
		}
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(innerBannerAdvLineChartData[x])) {
  					System.out.println(" *** innerBannerAdv innerBannerAdvLineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** innerBannerAdv innerBannerAdvLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
		switchTo().window(1);
		if($("h3", 0).text().trim().equals("내부 배너광고 추이")) {
			System.out.println(" *** innerBannerAdv pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** innerBannerAdv pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "17"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** innerBannerAdv Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** innerBannerAdv Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** innerBannerAdv Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerBannerAdv Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** innerBannerAdv Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** innerBannerAdv Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "내부배너2의 노출수 추이: 9", "2019.08.08 (목)", "내부배너2의 노출수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** innerBannerAdv Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** innerBannerAdv Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- innerBannerAdv End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void fileDownload() {
  		System.out.println(" ! ----- fileDownload Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("파일다운로드")) {
			System.out.println(" *** fileDownload pageLoad Success !! *** ");
		} else {
			System.out.println(" *** fileDownload pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "78"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** fileDownload summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** fileDownload summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** fileDownload summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** fileDownload summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"", "12", "", "12", "", "10", "", "10", "", "10", "", "10", "", "10", "", "2", "", "1", "", "1"};
  		for(int i=0;i<=19;i++) {
  			if(i % 2 == 1) {
  	  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  	  				System.out.println(" *** fileDownload statTableData(" + i + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** fileDownload statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  	  				close();
  	  			}
  			}
  		}
  		String[] statFootTableData = {"합계", "78"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** fileDownload statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** fileDownload statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** fileDownload no-data search check Success !! *** ");
			} else {
				System.out.println(" *** fileDownload no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] fileNameBarChartData = {"12", "12", "10", "10", "10", "10", "10"};
  		for(int i=0;i<=6;i++) {
  			for(int y=0;y<=5;y++) {
  	  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();
  			}
  			if($(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).text().trim().equals(fileNameBarChartData[i])) {
				System.out.println(" *** fileDownload fileNameBarChartData(" + i + ") check Success !! *** ");
			} else {
				System.out.println(" *** fileDownload fileNameBarChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
  			}

  		}
  		
  		String[] fileNameLineChartData = {"12", "0", "12", "0", "10", "0", "10", "0", "10", "0", "10", "0", "10", "0"};
  		for(int i=0,a=0;i<=6;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  			for(int x=0;x<=1;x++) {
  				for(int y=0;y<=5;y++) {
  					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();
  				}
  				for(int z=0;z<=1;z++) {
  					if(z==1) {
  	  					if($(".highcharts-tooltip", 1).text().trim().split(": ")[z].equals(fileNameLineChartData[a])) {
  	  	  					System.out.println(" *** fileDownload fileNameLineChartData(" + a + ") check Success !! *** ");
  	  	  				} else {
  	  	  					System.out.println(" *** fileDownload fileNameLineChartData(" + a + ") check Fail ... !@#$%^&*() *** ");
  	  	  					System.out.println($(".highcharts-tooltip", 1).text().trim().split(": ")[z]);
  	  	  					close();
  	  					}
  	  					a++;
  					}
  				}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
		switchTo().window(2); 
		if($("h3", 0).text().trim().equals("파일다운로드 추이")) {
			System.out.println(" *** fileDownload pageLoad Progress Success !! *** "); }
		else { 
			System.out.println(" *** fileDownload pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim()); close(); 
		} 
		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "12"}; 
		for(int i=0;i<=1;i++) { 
			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
				System.out.println(" *** fileDownload Progress summaryTableData(" + i + ") check Success !! *** "); 
			} else {
				System.out.println(" *** fileDownload Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".summaryDataCenter", i).text().trim()); close(); 
			} 
		}
		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
		for(int i=0;i<=1;i++) { 
			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
				System.out.println(" *** fileDownload Progress summaryFootTableData(" + i + ") check Success !! *** "); 
			} else {
				System.out.println(" *** fileDownload Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".summaryFootCenter", i).text().trim()); close();
			}
		}
		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
		for(int i=0;i<=1;i++) { 
			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
				System.out.println(" *** fileDownload Progress statTableData(" + i + ") check Success !! *** ");
			} else {
				System.out.println(" *** fileDownload Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** "); 
				System.out.println($(".statDataCenter", (i+1)).text().trim()); close();
			} 
		} 
		if($(".statFootCenter", 0).text().trim().equals("합계")) { 
			System.out.println(" *** fileDownload Progress statFootTableData check Success !! *** "); 
		} else { 
			System.out.println(" *** fileDownload Progress statFootTableData check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".statFootCenter", 0).text().trim()); close(); 
		}
		String[] progressLineChartData = {"12", "0"};
		for(int y=0,x=0;y<=1;y++) { 
			for(int i=0;i<=5;i++) {
				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover(); 
			} 
			for(int z=0;z<=1;z++) {
				if(z==1) {
					if($(".highcharts-tooltip", 0).text().trim().split(": ")[z].equals(progressLineChartData[x])) {
						System.out.println(" *** fileDownload Progress LineChartData(" + x + ") check Success !! *** "); 
					} else {
						System.out.println(" *** fileDownload Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
						System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
						close(); 
					} 
					x++; 	
				}
			} 
		}
		switchTo().window(0); 
  		System.out.println(" ! ----- fileDownload End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void siteStayTime() {
  		System.out.println(" ! ----- siteStayTime Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("사이트 체류시간")) {
			System.out.println(" *** siteStayTime pageLoad Success !! *** ");
		} else {
			System.out.println(" *** siteStayTime pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n상승", "00:00:57", "상승"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** siteStayTime summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** siteStayTime summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "00:00:00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** siteStayTime summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** siteStayTime summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  2019-08-08", "8", "8", "0", "0", "0", "0", "0", "0", "0", "0", "0", "00:00:00", 
  				"2.  2019-08-09", "30", "27", "0", "0", "1", "1", "0", "1", "0", "0", "0", "00:36:20"};
  		for(int i=0;i<=25;i++) {
  			if($(".statDataCenter", (i+13)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** siteStayTime statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** siteStayTime statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+13)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "35", "0", "0", "1", "1", "0", "1", "0", "0", "0", "00:36:20"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** siteStayTime statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** siteStayTime statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		
  		String[] visitCountBarChartData = {"1분미만", "35", "5분~10분 미만", "2", "10분~30분 미만", "1", "1분~5분 미만", "0", "30분~1시간 미만", "0", "1시간 이상", "0"};
		for(int i=0,x=0;i<=5;i++) {
			for(int z=0;z<=5;z++) {
	  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
	  		}
			for(int y=0;y<=1;y++) {
				if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(visitCountBarChartData[x])) {
					System.out.println(" *** siteStayTime visitCountBarChartData(" + x + ") check Success !! *** ");
				} else {
					System.out.println(" *** siteStayTime visitCountBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
					close();
	  			}
	  			x++;	
			}
		}
  		String[] stayTimeLineChartData = {"2019.08.09 (금)", "방문당 총 체류시간(초) 추이: 2,180", "2019.08.08 (목)", "방문당 총 체류시간(초) 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();  				
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(stayTimeLineChartData[x])) {
  					System.out.println(" *** siteStayTime stayTimeLineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** siteStayTime stayTimeLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		System.out.println(" ! ----- siteStayTime End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void pageStayTime() {
  		System.out.println(" ! ----- pageStayTime Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("페이지별 체류시간")) {
			System.out.println(" *** pageStayTime pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pageStayTime pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "463", "5044.44%\n상승", "00:00:04", "상승"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pageStayTime summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "00:00:00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pageStayTime summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  /index.html", "173", "163", "3", "1", "1", "3", "2", "00:29:36"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+9)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pageStayTime statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+9)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "463", "449", "5", "1", "2", "4", "2", "00:36:20"};
  		for(int i=0;i<=8;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pageStayTime statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** pageStayTime no-data search check Success !! *** ");
			} else {
				System.out.println(" *** pageStayTime no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] endCountBarChartData = {"/index.html", "1,776", "/search/label/fileDownload", "154", "/2019/08/1.html", "54", "전환-가입", "38", 
  				"전환-기타2", "38", "전환-신청", "24", "전환-주문", "19"};
  		for(int i=0,x=0;i<=6;i++) {
  			for(int z=0;z<=5;z++) {
  	  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  			}
  			for(int y=0;y<=1;y++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(endCountBarChartData[x])) {
  					System.out.println(" *** pageStayTime endCountBarChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** pageStayTime endCountBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  	  			}
  	  			x++;
  			}
  		}
  		String[] endCountLineChartData = {"2019.08.09 (금)", "/index.html의 총 체류시간 추이: 1,776", "2019.08.08 (목)", "/index.html의 총 체류시간 추이: 0", 
  				"2019.08.09 (금)", "/search/label/fileDownload의 총 체류시간 추이: 154", "2019.08.08 (목)", "/search/label/fileDownload의 총 체류시간 추이: 0", 
  				"2019.08.09 (금)", "/2019/08/1.html의 총 체류시간 추이: 54", "2019.08.08 (목)", "/2019/08/1.html의 총 체류시간 추이: 0",
  				"2019.08.09 (금)", "전환-가입의 총 체류시간 추이: 38", "2019.08.08 (목)", "전환-가입의 총 체류시간 추이: 0",
  				"2019.08.09 (금)", "전환-기타2의 총 체류시간 추이: 38", "2019.08.08 (목)", "전환-기타2의 총 체류시간 추이: 0",
  				"2019.08.09 (금)", "전환-신청의 총 체류시간 추이: 24", "2019.08.08 (목)", "전환-신청의 총 체류시간 추이: 0",
  				"2019.08.09 (금)", "전환-주문의 총 체류시간 추이: 19", "2019.08.08 (목)", "전환-주문의 총 체류시간 추이: 0"};
  		for(int i=0,x=0;i<=6;i++) {
  			for(int a=0;a<=5;a++) {
  	 			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover(); 				
  			}
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  			for(int y=0;y<=1;y++) {
  	  			for(int a=0;a<=5;a++) {
  	  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  	  			}
  	  			for(int z=0;z<=1;z++) {
	  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(endCountLineChartData[x])) {
	  					System.out.println(" *** pageStayTime endCountLineChartData(" + x + ") check Success !! *** ");
	  				} else {
	  					System.out.println(" *** pageStayTime endCountLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
	  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
	  					close();
	  	  			}
	  	  			x++;
  	  			}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("페이지별 체류시간 추이")) {
			System.out.println(" *** pageStayTime pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** pageStayTime pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "00:29:36"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** pageStayTime Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "00:00:00"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** pageStayTime Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** pageStayTime Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageStayTime Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** pageStayTime Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** pageStayTime Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 총 체류시간 추이: 1,776", "2019.08.08 (목)", "/index.html의 총 체류시간 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** pageStayTime Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** pageStayTime Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- pageStayTime End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void menuStayTime() {
  		System.out.println(" ! ----- menuStayTime Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("메뉴별 체류시간")) {
			System.out.println(" *** menuStayTime pageLoad Success !! *** ");
		} else {
			System.out.println(" *** menuStayTime pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "237", "상승", "00:00:00", "상승"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** menuStayTime summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "00:00:00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** menuStayTime summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  전환테스트", "237", "17", "235", "2", "0", "0", "0", "0"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+10)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** menuStayTime statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+10)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "237", "17", "235", "2", "0", "0", "0", "0"};
  		for(int i=0;i<=8;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** menuStayTime statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** menuStayTime no-data search check Success !! *** ");
			} else {
				System.out.println(" *** menuStayTime no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();
	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		String[] menuStayTimeBarChartData = {"전환테스트", "162"};
		for(int y=0,x=0;y<=1;y++) {
  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(menuStayTimeBarChartData[x])) {
				System.out.println(" *** menuStayTime menuStayTimeBarChartData(" + x + ") check Success !! *** ");
			} else {
				System.out.println(" *** menuStayTime menuStayTimeBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
				close();
  			}
  			x++;
		}
  		String[] menuStayTimeLineChartData = {"2019.08.09 (금)", "전환테스트의 총 체류시간 추이: 162", "2019.08.08 (목)", "전환테스트의 총 체류시간 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(menuStayTimeLineChartData[x])) {
  					System.out.println(" *** menuStayTime menuStayTimeLineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** menuStayTime menuStayTimeLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("메뉴별 체류시간 추이")) {
			System.out.println(" *** menuStayTime pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** menuStayTime pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "00:02:42"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** menuStayTime Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "00:00:00"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** menuStayTime Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** menuStayTime Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuStayTime Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** menuStayTime Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** menuStayTime Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "전환테스트의 총 체류시간 추이: 162", "2019.08.08 (목)", "전환테스트의 총 체류시간 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** menuStayTime Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** menuStayTime Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- menuStayTime End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void innerSearchKeyword() {
  		System.out.println(" ! ----- innerSearchKeyword Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("내부검색어")) {
			System.out.println(" *** innerSearchKeyword pageLoad Success !! *** ");
		} else {
			System.out.println(" *** innerSearchKeyword pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** innerSearchKeyword summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** innerSearchKeyword summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** innerSearchKeyword no-data check Success !! *** ");
			} else {
				System.out.println(" *** innerSearchKeyword no-data check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		System.out.println(" ! ----- innerSearchKeyword End ----- ! ");
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