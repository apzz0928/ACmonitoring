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

public class viralMarketing {
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
		//hubUrl = "http://10.77.129.79:5555/wd/hub";
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
  	public void viralMarketingSummary() {
  		System.out.println(" ! ----- viralMarketingSummary Start ----- ! ");
  		$("#m_stat > ul > li > a", 19).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("바이럴마케팅 요약")) {
			System.out.println(" *** viralMarketingSummary pageLoad Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingSummary pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3").text().trim());
			close();
  		}
  		$(".myValue", 1).click();
  		$("#stat_calendar").waitUntil(visible, 10000);
  		js("$j('#calendar_data1').val('2019-08-08');");
  		js("$j('#calendar_data2').val('2019-08-09');");
  		$("#stat_calendar > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		$(".day1").waitUntil(visible, 10000);
  		if($(".day1").text().trim().equals("2019.08.08부터 2019.08.09까지")) {
			System.out.println(" *** viralMarketingSummary calenderSet Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingSummary calenderSet Fail ... !@#$%^&*() *** ");
			System.out.println($(".day1").text().trim());
			close();
  		}
  		String[] statTopTableData = {"제목", "노출수", "게시물수", "유입수", "유입률", "전환수", "전환율", "구매건수"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** viralMarketingSummary statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingSummary statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  바이럴마케팅", "17", "3", "0", "0.00%", "0", "0.00%", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+8)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** viralMarketingSummary statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingSummary statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+8)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "17", "3", "0", "100.00%", "0", "0.00%", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** viralMarketingSummary statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingSummary statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** viralMarketingSummary no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingSummary no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootRight", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("0")) {
  				System.out.println(" *** viralMarketingSummary search check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingSummary search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- viralMarketingSummary End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void viralMarketingExposureAnalytics() {
  		System.out.println(" ! ----- viralMarketingExposureAnalytics Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("바이럴마케팅 노출분석")) {
			System.out.println(" *** viralMarketingExposureAnalytics pageLoad Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingExposureAnalytics pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "노출수"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "17", "88.89%\n상승", "3"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "1"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"마케팅 구분(전체)\n 블로그\n 카페\n 지식인\n 오픈마켓", "노출수", "게시물수",  "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  바이럴마케팅", "17", "3", "0", "0.00%", "0", "0.00%"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "17", "3", "0", "0.00%", "0", "0.00%"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		$("#data_subtr1").waitUntil(visible, 10000);
  		if($("#data_subdiv1 > table > tbody > tr > td", 0).text().trim().equals("\"바이럴마케팅\" 의 노출URL 상세")) {
			System.out.println(" *** viralMarketingExposureAnalytics subTableData Title check Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingExposureAnalytics subTableData Title check Fail ... !@#$%^&*() *** ");
			System.out.println($("#data_subdiv1 > table > tbody > tr > td", 0).text().trim());
			close();
		}
  		String[] subTopTableData = {"노출URL", "노출수", "비율", "유입수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i+14).text().trim().equals(subTopTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics subTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics subTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i+14).text().trim());
  				close();
  			}
  		}
  		String[] subTableData = {"1.   apzz092888.blogspot.com/", "14", "82.35%", "0", "2.   apzz092888.blogspot.com/search/label/fileDownload", "2", "11.76%", "0"
  				, "3.   apzz092888.blogspot.com/2019/08/1.html", "1", "5.88%", "0"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+18)).text().trim().equals(subTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics subTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics subTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+18)).text().trim());
  				close();
  			}
  		}
  		String[] subFootTableData = {"합계", "17", "100.00%", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(subFootTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalytics subFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics subFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** viralMarketingExposureAnalytics no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("0")) {
  				System.out.println(" *** viralMarketingExposureAnalytics search check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalytics search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
		$("#cell_check_8").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("현재구성이 기본구성과 같습니다!");
		$("#cell_check_8").click();
		$("#cell_8").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("구매율")) {
				System.out.println(" *** viralMarketingExposureAnalytics cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** viralMarketingExposureAnalytics cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 0).click();
		$("#cell_check_8").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
		$("#cell_8").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("매출액")) {
				System.out.println(" *** viralMarketingExposureAnalytics cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** viralMarketingExposureAnalytics cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		String[] PieChartData = {"바이럴마케팅17", "100.00%", "바이럴마케팅3", "100.00%"};
		for(int i=0,y=0;i<=1;i++) {
			for(int x=0;x<=5;x++) {
				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
			}
			for(int x=0;x<=1;x++) {
				if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(PieChartData[y])) {
					System.out.println(" *** viralMarketingExposureAnalytics PieChartData(" + y + ") check Success !! *** ");
				} else {
					System.out.println(" *** viralMarketingExposureAnalytics PieChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
					close();
				}
				y++;
			}
			$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > font").click();
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
			sleep(1000);
		}
		String[] LineChartData = {"2019.08.09 (금)", "바이럴마케팅의 노출수 추이: 9", "2019.08.08 (목)", "바이럴마케팅의 노출수 추이: 8"
				, "2019.08.09 (금)", "바이럴마케팅의 추이: 3", "2019.08.08 (목)", "바이럴마케팅의 추이: 1"};
		for(int i=0,z=0;i<=1;i++) {
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).click();
			for(int x=0;x<=1;x++) {
				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
				for(int y=0;y<=5;y++) {
					$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
				}
				for(int y=0;y<=1;y++) {
					if($(".highcharts-tooltip", 1).text().trim().split("● ")[y].equals(LineChartData[z])) {
						System.out.println(" *** viralMarketingExposureAnalytics LineChartData(" + z + ") check Success !! *** ");
					} else {
						System.out.println(" *** viralMarketingExposureAnalytics LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
						System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
						close();
					}
					z++;
				}
			}
			$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > font").click();
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
			sleep(1000);
		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 1).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("바이럴마케팅 노출분석 추이")) {
  			System.out.println(" *** viralMarketingExposureAnalyticsProgress pageLoad Success !! *** ");
  		} else {
  			System.out.println(" *** viralMarketingExposureAnalyticsProgress pageLoad Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		String[] summaryProgressTableData = {"2019.08.08 ~ 2019.08.09", "17"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalyticsProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalyticsProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootProgressTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootProgressTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalyticsProgress summaryFootProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalyticsProgress summaryFootProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statProgressTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(statProgressTableData[i])) {
  				System.out.println(" *** viralMarketingExposureAnalyticsProgress statProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingExposureAnalyticsProgress statProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
  		String[] progressLineChartData = {"2019.08.09 (금)", "바이럴마케팅의 노출수 추이: 9", "2019.08.08 (목)", "바이럴마케팅의 노출수 추이: 8"};
  		for(int i=0,y=0;i<=1;i++) {
  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).waitUntil(visible, 10000);
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();				
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("● ")[x].equals(progressLineChartData[y])) {
  					System.out.println(" *** viralMarketingExposureAnalyticsProgress LineChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** viralMarketingExposureAnalyticsProgress LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[x]);
  					close();
  				}
  				y++;
  			}
  		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("바이럴마케팅 노출분석")) {
  			System.out.println(" *** viralMarketingExposureAnalyticsProgress window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** viralMarketingExposureAnalyticsProgress window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- viralMarketingExposureAnalytics End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void viralMarketingInflowAnalytics() {
  		System.out.println(" ! ----- viralMarketingInflowAnalytics Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("바이럴마케팅 유입분석")) {
			System.out.println(" *** viralMarketingInflowAnalytics pageLoad Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingInflowAnalytics pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "유입수", "구매건수", "구매율"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** viralMarketingInflowAnalytics summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingInflowAnalytics summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** viralMarketingInflowAnalytics summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingInflowAnalytics summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** viralMarketingInflowAnalytics summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingInflowAnalytics summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"마케팅 구분(전체)\n 블로그\n 카페\n 지식인\n 오픈마켓", "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** viralMarketingInflowAnalytics statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketingInflowAnalytics statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** viralMarketingInflowAnalytics statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingInflowAnalytics statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_6").click();
  		$("#cell_6").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
			System.out.println(" *** viralMarketingInflowAnalytics cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingInflowAnalytics cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** viralMarketingInflowAnalytics cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** viralMarketingInflowAnalytics cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- viralMarketingInflowAnalytics End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void viralMarketinglandingAnalytics() {
  		System.out.println(" ! ----- viralMarketinglandingAnalytics Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("바이럴마케팅 랜딩분석")) {
			System.out.println(" *** viralMarketinglandingAnalytics pageLoad Success !! *** ");
		} else {
			System.out.println(" *** viralMarketinglandingAnalytics pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "유입수", "구매건수", "구매율"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** viralMarketinglandingAnalytics summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketinglandingAnalytics summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** viralMarketinglandingAnalytics summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketinglandingAnalytics summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** viralMarketinglandingAnalytics summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketinglandingAnalytics summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"마케팅 구분(전체)\n 블로그\n 카페\n 지식인\n 오픈마켓", "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** viralMarketinglandingAnalytics statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** viralMarketinglandingAnalytics statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** viralMarketinglandingAnalytics statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** viralMarketinglandingAnalytics statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_6").click();
  		$("#cell_6").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
			System.out.println(" *** viralMarketinglandingAnalytics cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** viralMarketinglandingAnalytics cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** viralMarketinglandingAnalytics cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** viralMarketinglandingAnalytics cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- viralMarketinglandingAnalytics End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void contentExposureDetail() {
  		System.out.println(" ! ----- contentExposureDetail Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("콘텐츠 노출상세")) {
			System.out.println(" *** contentExposureDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** contentExposureDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "노출수", "순노출"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** contentExposureDetail summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "17", "88.89%\n상승", "11", "22.22%\n상승", "6"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** contentExposureDetail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** contentExposureDetail summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"노출된 URL", "노출수", "순노출"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** contentExposureDetail statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  apzz092888.blogspot.com/", "14", "11", "2.  apzz092888.blogspot.com/search/label/fileDownload", "2", "0"
  				, "3.  apzz092888.blogspot.com/2019/08/1.html", "1", "0"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** contentExposureDetail statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** contentExposureDetail no-data search check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail no-data search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("6")) {
  				System.out.println(" *** contentExposureDetail search check Success !! *** ");
  			} else {
  				System.out.println(" *** contentExposureDetail search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_3").click();
  		$("#cell_3").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 1).text().trim().equals("순노출")) {
			System.out.println(" *** contentExposureDetail cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** contentExposureDetail cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 1).text().trim().equals("중복노출")) {
			System.out.println(" *** contentExposureDetail cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** contentExposureDetail cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		String[] PieChartData = {"apzz092888.blogspot.com/14", "82.35%", "apzz092888.blogspot.com/search/label/fileDownload2", "11.76%"
  				, "apzz092888.blogspot.com/2019/08/1.html1", "5.88%", "apzz092888.blogspot.com/11", "100.00%"
  				, "apzz092888.blogspot.com/3", "50.00%", "apzz092888.blogspot.com/search/label/fileDownload2", "33.33%", "apzz092888.blogspot.com/2019/08/1.html1", "16.67%"};
		for(int i=0,q=0;i<=2;i++) {
			for(int x=0;x<=2;x++) {
				for(int y=0;y<=5;y++) {
					$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).hover();
				}
				for(int y=0;y<=1;y++) {
					if($(".highcharts-tooltip", 0).text().trim().split(": ")[y].equals(PieChartData[q])) {
						System.out.println(" *** contentExposureDetail PieChartData(" + q + ") check Success !! *** ");
					} else {
						System.out.println(" *** contentExposureDetail PieChartData(" + q + ") check Fail ... !@#$%^&*() *** ");
						System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
						close();
					}
					q++;
				}
				if(i==1) {
					break;
				}
			}
			if(i==2) {
				i = 0;
			}			
			$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > font", i).click();
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
			sleep(1000);
			if(i==0 && q==14) {
				break;
			}
		}
		String[] LineChartData = {"2019.08.09 (금)", "apzz092888.blogspot.com/의 노출수 추이: 6", "2019.08.08 (목)", "apzz092888.blogspot.com/의 노출수 추이: 8"
				,"2019.08.09 (금)", "apzz092888.blogspot.com/search/label/fileDownload의 노출수 추이: 2", "2019.08.08 (목)", "apzz092888.blogspot.com/search/label/fileDownload의 노출수 추이: 0"
				,"2019.08.09 (금)", "apzz092888.blogspot.com/2019/08/1.html의 노출수 추이: 1", "2019.08.08 (목)", "apzz092888.blogspot.com/2019/08/1.html의 노출수 추이: 0"
				,"2019.08.09 (금)", "apzz092888.blogspot.com/의 순노출 추이: 3", "2019.08.08 (목)", "apzz092888.blogspot.com/의 순노출 추이: 8"
				,"2019.08.09 (금)", "apzz092888.blogspot.com/의 중복노출 추이: 3", "2019.08.08 (목)", "apzz092888.blogspot.com/의 중복노출 추이: 0"
				,"2019.08.09 (금)", "apzz092888.blogspot.com/search/label/fileDownload의 중복노출 추이: 2", "2019.08.08 (목)", "apzz092888.blogspot.com/search/label/fileDownload의 중복노출 추이: 0"
				,"2019.08.09 (금)", "apzz092888.blogspot.com/2019/08/1.html의 중복노출 추이: 1", "2019.08.08 (목)", "apzz092888.blogspot.com/2019/08/1.html의 중복노출 추이: 0"};
		for(int i=0,q=0;i<=2;i++) {
			for(int x=0;x<=2;x++) {
				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).click();
				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 1).waitUntil(visible, 10000);
				for(int y=0;y<=1;y++) {
					for(int z=0;z<=5;z++) {
						$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
					}
					for(int z=0;z<=1;z++) {
						if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[q])) {
							System.out.println(" *** contentExposureDetail LineChartData(" + q + ") check Success !! *** ");
						} else {
							System.out.println(" *** contentExposureDetail LineChartData(" + q + ") check Fail ... !@#$%^&*() *** ");
							System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
							close();
						}
						q++;
					}
				}
				if(i==1) {
					break;
				}
			}
			if(i<=1) {
				$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > font", i).click();
				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
				sleep(1000);	
			}
		}
  		System.out.println(" ! ----- contentExposureDetail End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}