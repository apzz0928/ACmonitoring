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

public class Page {
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
  	public void popularPage() {
  		System.out.println(" ! ----- popularPage Start ----- ! ");
  		$("#m_stat > ul > li > a", 9).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("많이 찾는 페이지")) {
			System.out.println(" *** popularPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** popularPage pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTopTableData = {"분석 기간", "페이지뷰", "방문수", "방문당 페이지뷰"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** pupolarPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "463", "5044.44%\n상승", "208", "2211.11%\n상승", "2.23", "123.00%\n상승", "00:00:10"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pupolarPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pupolarPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"페이지명", "페이지뷰", "비율", "방문수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** pupolarPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   \n  /index.html", "173", "37.37%", "38"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pupolarPage statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "463", "100.00%", "208"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pupolarPage statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** pupolarPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** pupolarPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문수")) {
  				System.out.println(" *** pupolarPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당 페이지뷰")) {
  				System.out.println(" *** pupolarPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] BarChartData = {"173", "34", "34", "34", "34", "34", "34", "38", "17", "17", "17", "17", "17", "17"};
  		for(int i=0,z=0;i<=1;i++) {
  	  		for(int x=0;x<=6;x++) {
  	  			for(int y=0;y<=5;y++) {
  	  				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).hover();
  	  			}
  	  			$(".highcharts-tooltip", 0).waitUntil(visible, 10000);
  	  			for(int y=1;y<=1;y++) {
  	  	  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[z])) {
  	  					System.out.println(" *** popularPage BarChartData(" + z + ") check Success !! *** ");
  	  				} else {
  	  					System.out.println(" *** popularPage BarChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  	  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  	  					close();
  	  	  			}
  	    	  		z++;
  	  			}
  	  		}
  	  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a  > font").click();
  	  		sleep(800);
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
  		}
  		String[] LineChartData = {"165", "8", "34", "0", "34", "0", "34", "0", "34", "0", "34", "0", "34", "0"
  				, "30", "8", "17", "0", "17", "0", "17", "0", "17", "0", "17", "0", "17", "0"};
  		for(int i=0,a=0;i<=1;i++) {
  	  		for(int x=0;x<=6;x++) {
  				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).click();
  				for(int y=0;y<=1;y++) {
  					for(int z=0;z<=5;z++) {
  		  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).waitUntil(visible, 10000);
  						$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();
  					}
  					for(int z=1;z<=1;z++) {
  						if($(".highcharts-tooltip", 1).text().trim().split(": ")[z].equals(LineChartData[a])) {
  	  	  					System.out.println(" *** popularPage LineChartData(" + a + ") check Success !! *** ");
  						} else {
  	  	  					System.out.println(" *** popularPage LineChartData(" + a + ") check Fail ... !@#$%^&*() *** ");
  	  	  					System.out.println($(".highcharts-tooltip", 1).text().trim().split(": ")[z]);
  	  	  					close();
  						}
  						a++;
  					}
  				}
  	  		}
  	  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a  > font").click();
  	  		sleep(800);
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  	  		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
	        $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
	        $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("많이 찾는 페이지 추이")) {
			System.out.println(" *** popularPage pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** popularPage pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "173"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** pupolarPage Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** pupolarPage Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** pupolarPage Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pupolarPage Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** pupolarPage Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** pupolarPage Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 페이지뷰 추이: 165", "2019.08.08 (목)", "/index.html의 페이지뷰 추이: 8"};
		for(int i=0,y=0;i<=1;i++) {
			for(int x=0;x<=5;x++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();				
			}
  			for(int x=0;x<=1;x++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[x].equals(progressLineChartData[y])) {
  					System.out.println(" *** pupolarPage Progress LineChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** pupolarPage Progress LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[x]);
  					close();
  	  			}
  	  			y++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("많이 찾는 페이지")) {
  			System.out.println(" *** pupolarPage window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** pupolarPage window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- popularPage End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void popularDirectory() {
  		System.out.println(" ! ----- popularDirectory Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("많이 찾는 디렉토리")) {
			System.out.println(" *** popularDirectory pageLoad Success !! *** ");
		} else {
			System.out.println(" *** popularDirectory pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "페이지뷰", "방문수", "방문당 페이지뷰", "평균체류시간"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** popularDirectory summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "463", "5044.44%\n상승", "208", "2211.11%\n상승", "2.23", "123.00%\n상승", "00:00:10", "상승"};
  		for(int i=0;i<=8;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** popularDirectory summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "9", "1.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** popularDirectory summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"디렉토리명", "페이지뷰", "비율", "방문수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** popularDirectory statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  /search/label/", "239", "51.62%", "120", "2.  /", "173", "37.37%", "38", "3.  /search/label/missing/", "48", "10.37%", "48", "4.  /2019/08/", "3", "0.65%", "2"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** popularDirectory statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "463", "100.00%", "208"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** popularDirectory statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** popularDirectory no-data search check Success !! *** ");
			} else {
				System.out.println(" *** popularDirectory no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		if(!$(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
  				System.out.println(" *** popularDirectory search check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory search check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_5").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_5").click();
  		$("#cell_5").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("평균체류시간")) {
  				System.out.println(" *** popularDirectory cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_5").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_5").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문당 페이지뷰")) {
  				System.out.println(" *** popularDirectory cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] BarChartData = {"/search/label/", "239", "/", "173", "/search/label/missing/", "48", "/2019/08/", "3"
  				, "/search/label/", "120", "/search/label/missing/", "48", "/", "38", "/2019/08/", "2"};
  		for(int i=0,z=0;i<=1;i++) {
  		    for(int x=0;x<=3;x++) {
  		        for(int y=0;y<=5;y++) {
  		            $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).hover();
  		        }
  		        $(".highcharts-tooltip", 0).waitUntil(visible, 10000);
  		        for(int y=0;y<=1;y++) {
  		            if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[z])) {
  		                System.out.println(" *** popularDirectory BarChartData(" + z + ") check Success !! *** ");
  		            } else {
  		                System.out.println(" *** popularDirectory BarChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  		                System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  		                close();
  		            }
  		            z++;
  		        }
  		    }
  		    $("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a  > font").click();
  		    sleep(800);
  		    $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "/search/label/의 페이지뷰 추이: 239", "2019.08.08 (목)", "/search/label/의 페이지뷰 추이: 0"
  		        , "2019.08.09 (금)", "/의 페이지뷰 추이: 165", "2019.08.08 (목)", "/의 페이지뷰 추이: 8"
  				, "2019.08.09 (금)", "/search/label/missing/의 페이지뷰 추이: 48", "2019.08.08 (목)", "/search/label/missing/의 페이지뷰 추이: 0"
  		        , "2019.08.09 (금)", "/2019/08/의 페이지뷰 추이: 3", "2019.08.08 (목)", "/2019/08/의 페이지뷰 추이: 0"
  		        , "2019.08.09 (금)", "/search/label/의 방문수 추이: 120", "2019.08.08 (목)", "/search/label/의 방문수 추이: 0"
  				, "2019.08.09 (금)", "/search/label/missing/의 방문수 추이: 48", "2019.08.08 (목)", "/search/label/missing/의 방문수 추이: 0"
  		        , "2019.08.09 (금)", "/의 방문수 추이: 30", "2019.08.08 (목)", "/의 방문수 추이: 8"
  		        , "2019.08.09 (금)", "/2019/08/의 방문수 추이: 2", "2019.08.08 (목)", "/2019/08/의 방문수 추이: 0"};
  		for(int i=0,a=0;i<=1;i++) {
  		    for(int x=0;x<=3;x++) {
  		        $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).click();
  		        for(int y=0;y<=1;y++) {
  		            for(int z=0;z<=5;z++) {
  		                $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).waitUntil(visible, 10000);
  		                $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();
  		            }
  		            for(int z=0;z<=1;z++) {
  		                if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[a])) {
  		                    System.out.println(" *** popularDirectory LineChartData(" + a + ") check Success !! *** ");
  		                } else {
  		                    System.out.println(" *** popularDirectory LineChartData(" + a + ") check Fail ... !@#$%^&*() *** ");
  		                    System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  		                    close();
  		                }
  		                a++;
  		            }
  		        }
  		    }
  		    $("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a  > font").click();
  		    sleep(800);
  		    $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		    $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
  		    $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		    $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("많이 찾는 디렉토리 추이")) {
			System.out.println(" *** popularDirectory pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** popularDirectory pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "239"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** popularDirectory Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** popularDirectory Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** popularDirectory Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** popularDirectory Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** popularDirectory Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** popularDirectory Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/search/label/의 페이지뷰 추이: 239", "2019.08.08 (목)", "/search/label/의 페이지뷰 추이: 0"};
		for(int i=0,y=0;i<=1;i++) {
			for(int x=0;x<=5;x++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();				
			}
  			for(int x=0;x<=1;x++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[x].equals(progressLineChartData[y])) {
  					System.out.println(" *** popularDirectory Progress LineChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** popularDirectory Progress LineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[x]);
  					close();
  	  			}
  	  			y++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("많이 찾는 디렉토리")) {
  			System.out.println(" *** popularDirectory window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** popularDirectory window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- popularDirectory End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void refreshPage() {
  		System.out.println(" ! ----- refreshPage Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("페이지별 새로고침")) {
			System.out.println(" *** refreshPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** refreshPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** refreshPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "11"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** refreshPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** refreshPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"페이지명", "새로고침수"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** refreshPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   \n  /index.html", "9", "", "1", "", "1"};
  		for(int i=0;i<=5;i++) {
  			if(i==2 || i==4) {
  				
  			} else {
  	  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  	  				System.out.println(" *** refreshPage statTableData(" + i + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** refreshPage statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  	  				close();
  	  			}
  			}
  		}
  		String[] statFootTableData = {"합계", "11"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** refreshPage statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** refreshPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** refreshPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_2").click();
  		$("#cell_2").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("새로고침수")) {
  				System.out.println(" *** refreshPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
  				System.out.println(" *** refreshPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] BarChartData = {"/index.html", "9", "", "1", "", "1"};
	    for(int x=0,z=0;x<=2;x++) {
	        for(int y=0;y<=5;y++) {
	            $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).hover();
	        }
	        $(".highcharts-tooltip", 0).waitUntil(visible, 10000);
	        for(int y=0;y<=1;y++) {
	        	if(y==0 && z==2 || z==4) {
	        		z++;
	        	} else {
		            if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[z])) {
		                System.out.println(" *** refreshPage BarChartData(" + z + ") check Success !! *** ");
		            } else {
		                System.out.println(" *** refreshPage BarChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
		                System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
		                close();
		            }
		            z++;
	        	}
	        }
	    }
	    String[] LineChartData = {"2019.08.09 (금)", "/index.html의 새로고침수 추이: 9", "2019.08.08 (목)", "/index.html의 새로고침수 추이: 0"
	    		, "2019.08.09 (금)", "/search/label/fileDownload의 새로고침수 추이: 1", "2019.08.08 (목)", "/search/label/fileDownload의 새로고침수 추이: 0"
	    		, "2019.08.09 (금)", "/2019/08/index.html의 새로고침수 추이: 1", "2019.08.08 (목)", "/2019/08/index.html의 새로고침수 추이: 0"};
	    for(int i=0,z=0;i<=2;i++) {
	    	$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
	    	for(int x=0;x<=1;x++) {
	    		sleep(800);
	    		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).waitUntil(visible, 10000);
	    		for(int y=0;y<=5;y++) {
	    			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", x).hover();				
	    		}
	    		for(int y=0;y<=1;y++) {
	    			if($(".highcharts-tooltip", 1).text().trim().split("● ")[y].equals(LineChartData[z])) {
	    				System.out.println(" *** refreshPage LineChartData(" + z + ") check Success !! *** ");
	    			} else {
	    				System.out.println(" *** refreshPage LineChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
	    				System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[y]);
	    				close();
	    			}
	    			z++;
	    		}
	    	}
	    }
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("페이지별 새로고침 추이")) {
			System.out.println(" *** refreshPage pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** refreshPage pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** refreshPage Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** refreshPage Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** refreshPage Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** refreshPage Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** refreshPage Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** refreshPage Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 새로고침수 추이: 9", "2019.08.08 (목)", "/index.html의 새로고침수 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** refreshPage Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** refreshPage Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("페이지별 새로고침")) {
  			System.out.println(" *** refreshPage window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** refreshPage window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- refreshPage End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void startPage() {
  		System.out.println(" ! ----- startPage Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("시작된 페이지")) {
			System.out.println(" *** startPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** startPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** startPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** startPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** startPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"페이지명", "시작횟수"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** startPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   \n  /index.html", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** startPage statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** startPage statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight").text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** startPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** startPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight").text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_2").click();
  		$("#cell_2").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("시작횟수")) {
  				System.out.println(" *** startPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** startPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
  				System.out.println(" *** startPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** startPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		for(int i=0;i<=5;i++) {
  			$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 0).hover();
  		}
  		String[] BarChartData = {"/index.html", "38"};
		for(int y=0,x=0;y<=1;y++) {
  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[x])) {
				System.out.println(" *** startPage startCountBarChartData(" + x + ") check Success !! *** ");
			} else {
				System.out.println(" *** startPage startCountBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
				close();
  			}
  			x++;
		}
  		String[] LineChartData = {"2019.08.09 (금)", "/index.html의 시작횟수 추이: 30", "2019.08.08 (목)", "/index.html의 시작횟수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();  				
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[x])) {
  					System.out.println(" *** startPage startCountLineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** startPage startCountLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(4);
  		if($("h3", 0).text().trim().equals("시작된 페이지 추이")) {
			System.out.println(" *** startPage pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** startPage pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** startPage Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** startPage Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** startPage Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** startPage Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** startPage Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** startPage Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 시작횟수 추이: 30", "2019.08.08 (목)", "/index.html의 시작횟수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** startPage Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** startPage Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("시작된 페이지")) {
  			System.out.println(" *** startPage window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** startPage window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- startPage End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void endPage() {
  		System.out.println(" ! ----- endPage Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("종료된 페이지")) {
			System.out.println(" *** endPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** endPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** endPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** endPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** endPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"페이지명", "종료횟수"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** endPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   \n  /index.html", "21", "2.   \n  /search/label/missing/missingC", "16", "3.   \n  /2019/08/index.html", "1"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** endPage statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38"};
  		for(int i=0;i<=1;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** endPage statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** endPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** endPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_2").click();
  		$("#cell_2").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("종료횟수")) {
  				System.out.println(" *** endPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** endPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
  				System.out.println(" *** endPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** endPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] BarChartData = {"/index.html", "21", "/search/label/missing/missingC", "16", "/2019/08/index.html", "1"};
  		for(int i=0,x=0;i<=2;i++) {
  			for(int z=0;z<=5;z++) {
  	  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  			}
  			for(int y=0;y<=1;y++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[x])) {
  					System.out.println(" *** endPage endCountBarChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** endPage endCountBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  	  			}
  	  			x++;
  			}
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "/index.html의 종료횟수 추이: 13", "2019.08.08 (목)", "/index.html의 종료횟수 추이: 8", 
  				"2019.08.09 (금)", "/search/label/missing/missingC의 종료횟수 추이: 16", "2019.08.08 (목)", "/search/label/missing/missingC의 종료횟수 추이: 0", 
  				"2019.08.09 (금)", "/2019/08/index.html의 종료횟수 추이: 1", "2019.08.08 (목)", "/2019/08/index.html의 종료횟수 추이: 0"};
  		for(int i=0,x=0;i<=2;i++) {
  			for(int a=0;a<=5;a++) {
  	 			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover(); 				
  			}
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  			for(int y=0;y<=1;y++) {
  	  			for(int a=0;a<=5;a++) {
  	  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  	  			}
  	  			$(".highcharts-tooltip", 1).waitUntil(visible, 10000);
  	  			for(int z=0;z<=1;z++) {
	  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[x])) {
	  					System.out.println(" *** endPage endCountLineChartData(" + x + ") check Success !! *** ");
	  				} else {
	  					System.out.println(" *** endPage endCountLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
	  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
	  					close();
	  	  			}
	  	  			x++;
  	  			}
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(5);
  		if($("h3", 0).text().trim().equals("종료된 페이지 추이")) {
			System.out.println(" *** endPage pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** endPage pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "21"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** endPage Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** endPage Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** endPage Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** endPage Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** endPage Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** endPage Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 종료횟수 추이: 13", "2019.08.08 (목)", "/index.html의 종료횟수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** endPage Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** endPage Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("종료된 페이지")) {
  			System.out.println(" *** endPage window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** endPage window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- endPage End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void returnPage() {
  		System.out.println(" ! ----- returnPage Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("반송된 페이지")) {
			System.out.println(" *** returnPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** returnPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** returnPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "18"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** returnPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** returnPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"페이지명", "반송횟수", "시작횟수"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** returnPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   \n  /index.html", "18", "38"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** returnPage statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "18", "38"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** returnPage statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** returnPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** returnPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_3").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_3").click();
  		$("#cell_3").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("시작횟수")) {
  				System.out.println(" *** returnPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("반송률")) {
  				System.out.println(" *** returnPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		String[] BarChartData = {"/index.html", "18"};
		for(int y=0,x=0;y<=1;y++) {
  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[x])) {
				System.out.println(" *** returnPage returnCountBarChartData(" + x + ") check Success !! *** ");
			} else {
				System.out.println(" *** returnPage returnCountBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
				close();
  			}
  			x++;
		}
  		String[] LineChartData = {"2019.08.09 (금)", "/index.html의 반송횟수 추이: 10", "2019.08.08 (목)", "/index.html의 반송횟수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[x])) {
  					System.out.println(" *** returnPage returnCountLineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** returnPage returnCountLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(6);
  		if($("h3", 0).text().trim().equals("반송된 페이지 추이")) {
			System.out.println(" *** returnPage pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** returnPage pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "18"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** returnPage Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** returnPage Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** returnPage Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** returnPage Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** returnPage Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** returnPage Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 반송횟수 추이: 10", "2019.08.08 (목)", "/index.html의 반송횟수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** returnPage Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** returnPage Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("반송된 페이지")) {
  			System.out.println(" *** returnPage window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** returnPage window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- returnPage End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void menuPageview() {
  		System.out.println(" ! ----- menuPageview Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("메뉴별 페이지뷰")) {
			System.out.println(" *** menuPageview pageLoad Success !! *** ");
		} else {
			System.out.println(" *** menuPageview pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "페이지뷰", "방문수"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** menuPageview summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "237", "상승", "17", "상승", "13.94"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** menuPageview summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** menuPageview summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"메뉴명", "페이지뷰", "비율", "방문수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** menuPageview statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"전환테스트", "237", "100.00%", "17"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** menuPageview statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "237", "100.00%", "17"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** menuPageview statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** menuPageview no-data search check Success !! *** ");
			} else {
				System.out.println(" *** menuPageview no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();  		
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_4").click();
  		$("#cell_4").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 1).text().trim().equals("방문수")) {
  				System.out.println(" *** menuPageview cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 1).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_4").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 1).text().trim().equals("방문당 페이지뷰")) {
  				System.out.println(" *** menuPageview cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 1).text().trim());
  				close();
  		}
  		String[] BarChartData = {"전환테스트", "237", "전환테스트", "17"};
  		for(int i=0,z=0;i<=1;i++) {
  		    for(int x=0;x<=0;x++) {
  		        for(int y=0;y<=5;y++) {
  		            $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).hover();
  		        }
  		        $(".highcharts-tooltip", 0).waitUntil(visible, 10000);
  		        for(int y=0;y<=1;y++) {
  		            if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[z])) {
  		                System.out.println(" *** menuPageview BarChartData(" + z + ") check Success !! *** ");
  		            } else {
  		                System.out.println(" *** menuPageview BarChartData(" + z + ") check Fail ... !@#$%^&*() *** ");
  		                System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  		                close();
  		            }
  		            z++;
  		        }
  		    }
  		    $("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a  > font").click();
  		    $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		}
  		String[] LineChartData = {"2019.08.09 (금)", "전환테스트의 페이지뷰 추이: 237", "2019.08.08 (목)", "전환테스트의 페이지뷰 추이: 0"
  				, "2019.08.09 (금)", "전환테스트의 방문수 추이: 17", "2019.08.08 (목)", "전환테스트의 방문수 추이: 0"};
  		for(int i=0,a=0;i<=1;i++) {
  		    for(int x=0;x<=0;x++) {
  		        $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", x).click();
  		        for(int y=0;y<=1;y++) {
  		            for(int z=0;z<=5;z++) {
  		                $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).waitUntil(visible, 10000);
  		                $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();
  		            }
  		            for(int z=0;z<=1;z++) {
  		                if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[a])) {
  		                    System.out.println(" *** popularPage LineChartData(" + a + ") check Success !! *** ");
  		                } else {
  		                    System.out.println(" *** popularPage LineChartData(" + a + ") check Fail ... !@#$%^&*() *** ");
  		                    System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  		                    close();
  		                }
  		                a++;
  		            }
  		        }
  		    }
  		    $("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a  > font").click();
  		    $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  		    $(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).hover();
  		    $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		    $(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(7);
  		if($("h3", 0).text().trim().equals("메뉴별 페이지뷰 추이")) {
			System.out.println(" *** menuPageview pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** menuPageview pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "237"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** menuPageview Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** menuPageview Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** menuPageview Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuPageview Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** menuPageview Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** menuPageview Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "전환테스트의 페이지뷰 추이: 237", "2019.08.08 (목)", "전환테스트의 페이지뷰 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** menuPageview Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** menuPageview Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("메뉴별 페이지뷰")) {
  			System.out.println(" *** menuPageview window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** menuPageview window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- menuPageview End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void scriptErrorPage() {
  		System.out.println(" ! ----- scriptErrorPage Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("스크립트 오류 페이지")) {
			System.out.println(" *** scriptErrorPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** scriptErrorPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "오류횟수"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** scriptErrorPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "10", "상승", "2.16%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** scriptErrorPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0.00%"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** scriptErrorPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"스크립트 오류 페이지", "페이지뷰", "오류 횟수"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** scriptErrorPage statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.   \n  /index.html", "173", "10"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** scriptErrorPage statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "463", "10"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** scriptErrorPage statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statFootCenter").waitUntil(hidden, 10000);
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** scriptErrorPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** scriptErrorPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".formgray", 0).setValue("");  		
  		$("form > table > tbody > tr > td > img", 0).click();
	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
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
  		if($(".statDataRight", 0).text().trim().equals("오류 횟수")) {
  				System.out.println(" *** scriptErrorPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("오류율")) {
  				System.out.println(" *** scriptErrorPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] BarChartData = {"/index.html", "10"};
		for(int i=0;i<=5;i++) {
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
		}
		for(int y=0,x=0;y<=1;y++) {
  			if($(".highcharts-tooltip", 0).text().trim().split(" : ")[y].equals(BarChartData[x])) {
				System.out.println(" *** scriptErrorPage errorCountBarChartData(" + x + ") check Success !! *** ");
			} else {
				System.out.println(" *** scriptErrorPage errorCountBarChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
				close();
  			}
  			x++;
		}
  		String[] LineChartData = {"2019.08.09 (금)", "/index.html의 오류횟수 추이: 10", "2019.08.08 (목)", "/index.html의 오류횟수 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(LineChartData[x])) {
  					System.out.println(" *** scriptErrorPage errorCountLineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** scriptErrorPage errorCountLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 1).click();
		switchTo().window(8);
  		if($("h3", 0).text().trim().equals("스크립트 오류 페이지 추이")) {
			System.out.println(" *** scriptErrorPage pageLoad Progress Success !! *** ");
		} else {
			System.out.println(" *** scriptErrorPage pageLoad Progress Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "10"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** scriptErrorPage Progress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage Progress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** scriptErrorPage Progress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage Progress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** scriptErrorPage Progress statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** scriptErrorPage Progress statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** scriptErrorPage Progress statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** scriptErrorPage Progress statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "/index.html의 오류횟수 추이: 10", "2019.08.08 (목)", "/index.html의 오류횟수 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** scriptErrorPage Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** scriptErrorPage Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("스크립트 오류 페이지")) {
  			System.out.println(" *** scriptErrorPage window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** scriptErrorPage window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- scriptErrorPage End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void errorPage() {
  		System.out.println(" ! ----- errorPage Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("에러 페이지")) {
			System.out.println(" *** errorPage pageLoad Success !! *** ");
		} else {
			System.out.println(" *** errorPage pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간"};
  		for(int i=0;i<=0;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** errorPage summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** errorPage summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** errorPage summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** errorPage summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** errorPage summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** errorPage summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statDataTopTable = {"에러 페이지"};
  		for(int i=0;i<=0;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statDataTopTable[i])) {
  				System.out.println(" *** errorPage statDataTable Detail (" + i + ") Success !! *** ");
  			} else {
  				System.out.println(" *** errorPage statDataTable Detail (" + i + ") Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  	  		}	
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
				System.out.println(" *** errorPage no-data search check Success !! *** ");
			} else {
				System.out.println(" *** errorPage no-data search check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statFootRight", 0).text().trim());
				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_2").click();
  		$("#cell_2").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("에러요청횟수")) {
  				System.out.println(" *** errorPage cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** errorPage cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_2").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
  				System.out.println(" *** errorPage cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** errorPage cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- errorPage End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}