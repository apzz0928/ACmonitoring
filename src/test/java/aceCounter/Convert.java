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
  	public void convertPageInflow() {
  		System.out.println(" ! ----- convertPageInflow Start ----- ! ");
  		$("#m_stat > ul > li > a", 7).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3").text().trim().equals("전환 페이지 유입출처")) {
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
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n상승", "119"};
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
  		String[] statTopTableData = {"유입출처", "유입수", "전체전환수", "반송수", "전환-가입", "전환-주문", "전환-예약", "전환-신청", "전환-기타1", "전환-기타2"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertPageInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  직접유입", "36", "119", "18", "17", "17", "17", "17", "17", "17", "2.  내부유입", "2", "0", "0", "0", "0", "0", "0", "0", "0"};
  		for(int i=0;i<=19;i++) {
  			if($(".statDataCenter", (i+10)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertPageInflow statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+10)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "119", "18", "17", "17", "17", "17", "17", "17"};
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
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_10").click();
  		$("#cell_10").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("전환-기타2")) {
  				System.out.println(" *** visitInflow cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_10").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_10").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("전환-기타3")) {
  				System.out.println(" *** visitInflow cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] convertPageInflow = {"직접유입 : 119", "직접유입 : 18", "직접유입 : 36", "내부유입 : 2"};
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
  		if($("h3", 0).text().trim().equals("전환 페이지 유입출처 추이")) {
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
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** convertPageInflow progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertPageInflow progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** convertPageInflow progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertPageInflow progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "직접유입의 전체전환수 추이: 119", "2019.08.08 (목)", "직접유입의 전체전환수 추이: 0"};
		$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** convertPageInflow Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** convertPageInflow Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
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
  		if($("h3", 0).text().trim().equals("전환 페이지 경로")) {
			System.out.println(" *** convertPagePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertPagePath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"*전환-예약", "*전환-주문", "*전환-기타1", "*전환-기타2", "*전환-기타3", "*전환-신청", "*전환-가입"};
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
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
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
  		String[] statTopTableData = {"페이지 이동경로(아이콘)\n         \n페이지 이동경로(URL)", "페이지수", "방문수"};
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
  		String[] statFootTableData = {"합계", "-", "17"};
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
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_3").click();
  		$("#cell_3").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("방문수")) {
  				System.out.println(" *** convertPagePath cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** convertPagePath cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("방문비율")) {
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
  		if($("h3", 0).text().trim().equals("전환 방문자 상세")) {
			System.out.println(" *** convertVisitDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertVisitDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"순번", "IP", "유입출처", "방문구분", "이동경로", "전환구분", "회원ID", "방문일시"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertVisitDetail statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertVisitDetail statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"10.77.129.79", "[직접유입]", "신규방문", "25", "전환-기타3", "", "2019-08-09 17:28:04"};
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
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
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
  		if($("h3", 0).text().trim().equals("전환까지 소요기간")) {
			System.out.println(" *** convertRequiredPeriod pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertRequiredPeriod pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"소요기간", "전환수", "전환비율", "전환-가입", "전환-주문", "전환-예약", "전환-신청", "전환-기타1", "전환-기타2"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  당일", "119", "100.00%", "17", "17", "17", "17", "17", "17"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+9)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+9)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "119", "100.00%", "17", "17", "17", "17", "17", "17"};
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
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_9").click();
  		$("#cell_9").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("전환-기타2")) {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_9").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_9").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("전환-기타3")) {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		
  		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("전환까지 소요기간 추이")) {
			System.out.println(" *** convertRequiredPeriod progress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertRequiredPeriod progress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** convertRequiredPeriod progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertRequiredPeriod progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** convertRequiredPeriod progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertRequiredPeriod progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "당일의 전체 전환수 추이: 119", "2019.08.08 (목)", "당일의 전체 전환수 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** convertRequiredPeriod Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** convertRequiredPeriod Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
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
  		if($("h3", 0).text().trim().equals("전환까지 유입횟수")) {
			System.out.println(" *** convertInflowCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertInflowCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"유입횟수", "전환수"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertInflowCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1회", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertInflowCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "119"};
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
  		String[] subStatTopTableData = {"유입출처 경로", "전환수"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(subStatTopTableData[i])) {
  				System.out.println(" *** convertInflowCount subStatTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount subStatTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] subStatTableData = {"1.   직접유입", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(subStatTableData[i])) {
  				System.out.println(" *** convertInflowCount subStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount subStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		String[] subStatFootTableData = {"합계", "119"};
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
  		if($("h3", 0).text().trim().equals("전환까지 유입횟수 추이")) {
			System.out.println(" *** convertInflowCount progress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertInflowCount progress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** convertInflowCount progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflowCount progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** convertInflowCount progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** convertInflowCount progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "1회의 전체 전환수 추이: 119", "2019.08.08 (목)", "1회의 전체 전환수 추이: 0"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** convertInflowCount Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** convertInflowCount Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
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
  		if($("h3", 0).text().trim().equals("전환까지 유입경로")) {
			System.out.println(" *** convertInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** convertInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"유입출처", "전환수"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** convertInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1. \n직접유입", "119"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+2)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** convertInflow statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** convertInflow statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "119"};
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
  		if($("#cString_3", 0).text().trim().equals("네이버(사이트검색광고) - 유입별 간접전환")) {
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
  		String[] statTopTableData = {"검색어", "직접 전환수", "간접 전환수", "직접 전환수+간접 전환수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** inflowIndirectConvert statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** inflowIndirectConvert statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
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