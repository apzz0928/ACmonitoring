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

public class marketingEffect {
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
  	@Test(priority = 1) //여기서부터 수정 필요
  	public void bannerAD() {
  		System.out.println(" ! ----- bannerAD Start ----- ! ");
  		$("#m_stat > ul > li > a", 6).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("배너광고")) {
			System.out.println(" *** bannerAD pageLoad Success !! *** ");
		} else {
			System.out.println(" *** bannerAD pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** bannerAD summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerAD summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** bannerAD summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerAD summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"배너프로모션", "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** bannerAD statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerAD statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** bannerAD statTableData check Success !! *** ");
		} else {
			System.out.println(" *** bannerAD statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 1).click();
		$("#cell_check_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
		confirm("현재구성이 기본구성과 같습니다!");
		$("#cell_check_13").click();
		$("#cell_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("반송률")) {
				System.out.println(" *** visitInflow cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** visitInflow cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
		$(".floatLeft > a > img", 1).click();
		$("#cell_check_13").waitUntil(visible, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
		$("#cell_13").waitUntil(hidden, 10000);
		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
		$("#cell_edit_div").waitUntil(hidden, 10000);
		sleep(800);
		if($(".statDataRight", 0).text().trim().equals("매출액")) {
				System.out.println(" *** visitInflow cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** visitInflow cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- bannerAD End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void bannerADTimeAvg() {
  		System.out.println(" ! ----- bannerADTimeAvg Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("배너광고 시간대별 평균")) {
			System.out.println(" *** bannerADTimeAvg pageLoad Success !! *** ");
		} else {
			System.out.println(" *** bannerADTimeAvg pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		$(".myValue", 1).click();
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0.00", "-", "0.00", "-", "0.00"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** bannerADTimeAvg summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerADTimeAvg summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.00", "0.00", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** bannerADTimeAvg summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerADTimeAvg summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"시간대", "전체 배너프로모션\n      \n배너광고\n토스트익스체인지-AUTO\n      의 시간대별 평균", "프로모션없음->검색어없음"
  				, "유입수", "유입률", "전환수", "전환율", "구매건수", "매출액", "평균유입수", "유입률", "평균전환수"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** bannerADTimeAvg statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerADTimeAvg statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계(평균)", "0.00 (0.00)", "0.00%", "0.00 (0.00)", "0.00%", "0.00 (0.00)", "0.00 (0.00)", "0.00 (0.00)", "0.00%", "0.00 (0.00)"};
  		for(int i=0;i<=9;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** bannerADTimeAvg statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** bannerADTimeAvg statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- bannerADTimeAvg End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void emailMarketing() {
  		System.out.println(" ! ----- emailMarketing Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("이메일마케팅")) {
			System.out.println(" *** emailMarketing pageLoad Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "17", "88.89%\n상승", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=9;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** emailMarketing summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** emailMarketing summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"이메일", "오픈수", "유입수", "유입/오픈", "유입률", "전환수", "전환율", "구매건수", "구매율"
  				, "전체\n전환-가입\n전환-주문\n전환-예약\n전환-신청\n전환-기타1\n전환-기타2\n전환-기타3", "전체"};
  		for(int i=0;i<=10;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** emailMarketing statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  이메일마케팅", "17", "0", "0.00%", "0.00%", "0", "0.00%", "0", "0.00%"};
  		for(int i=0;i<=8;i++) {
  			if($(".statDataCenter", (i+11)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** emailMarketing statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+11)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "17", "0", "0.00%", "0.00%", "0", "0.00%", "0", "0.00%"};
  		for(int i=0;i<=8;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** emailMarketing statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_8").click();
  		$("#cell_8").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** emailMarketing cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** emailMarketing cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		for(int i=0;i<=5;i++) {
  	  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();	
  		}
  		String[] pieChartData = {"이메일마케팅17", "100.00%"};
  		for(int i=0,y=0;i<=0;i++) {
  			for(int x=0;x<=5;x++) {
  				$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();	
  			}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split(": ")[x].equals(pieChartData[y])) {
  					System.out.println(" *** emailMarketing pieChartData(" + y + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** emailMarketing pieChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  				}
  				y++;
  			}
  		}
  		String[] lineChartData = {"2019.08.09 (금)", "이메일마케팅의 오픈수 추이: 9", "2019.08.08 (목)", "이메일마케팅의 오픈수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
  			for(int i=0;i<=5;i++) {
  	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(lineChartData[x])) {
  					System.out.println(" *** emailMarketing lineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** emailMarketing lineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}	
  		$(".statDataCenter > table > tbody > tr > td > a > img", 1).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("이메일마케팅 > 구매 상세")) {
			System.out.println(" *** emailMarketing buyDetail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing buyDetail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] buyDetailSummaryTableData = {"이메일마케팅", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(buyDetailSummaryTableData[i])) {
  				System.out.println(" *** emailMarketing Buy summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing Buy summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** emailMarketing Buy statTableData check Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing Buy statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		$(".statDataCenter > table > tbody > tr > td > a > img", 2).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("이메일마케팅 > 구매제품 상세")) {
			System.out.println(" *** emailMarketing Buy pageLoad Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing Buy pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] productDetailSummaryTableData = {"이메일마케팅", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(productDetailSummaryTableData[i])) {
  				System.out.println(" *** emailMarketing Product summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing Product summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** emailMarketing Product statTableData check Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing Product statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		$(".statDataCenter > table > tbody > tr > td > a > img", 3).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("이메일마케팅 추이")) {
			System.out.println(" *** emailMarketing Progress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing Progress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] progressSummaryTableData = {"2019.08.08 ~ 2019.08.09", "17"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(progressSummaryTableData[i])) {
  				System.out.println(" *** emailMarketing progressSummaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing progressSummaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressSummaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(progressSummaryFootTableData[i])) {
  				System.out.println(" *** emailMarketing progressSummaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing progressSummaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] progressStatTableData = {"2019.08.08 (목)", "2019.08.09 (금)"};
  		for(int i=0;i<=1;i++) {
  			if($(".statDataCenter", (i+1)).text().trim().equals(progressStatTableData[i])) {
  				System.out.println(" *** emailMarketing progressStatTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** emailMarketing progressStatTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+1)).text().trim());
  				close();
  			}
  		}
		if($(".statFootCenter", 0).text().trim().equals("합계")) {
			System.out.println(" *** emailMarketing progressStatFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** emailMarketing progressStatFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
		String[] progressLineChartData = {"2019.08.09 (금)", "이메일마케팅의 오픈수 추이: 9", "2019.08.08 (목)", "이메일마케팅의 오픈수 추이: 8"};
		for(int y=0,x=0;y<=1;y++) {
			for(int i=0;i<=5;i++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();				
			}
  			for(int z=0;z<=1;z++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split("● ")[z].equals(progressLineChartData[x])) {
  					System.out.println(" *** emailMarketing Progress LineChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** emailMarketing Progress LineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split("● ")[z]);
  					close();
  	  			}
  	  			x++;
  			}
		}
  		switchTo().window(0);
  		System.out.println(" ! ----- emailMarketing End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void QRcodeAnalysis() {
  		System.out.println(" ! ----- QRcodeAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("QR코드 분석")) {
			System.out.println(" *** QRcodeAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** QRcodeAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** QRcodeAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** QRcodeAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** QRcodeAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** QRcodeAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"QR코드명", "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** QRcodeAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** QRcodeAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** QRcodeAnalysis statTableData check Success !! *** ");
		} else {
			System.out.println(" *** QRcodeAnalysis statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_7").click();
  		$("#cell_7").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** QRcodeAnalysis cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** QRcodeAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** QRcodeAnalysis cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** QRcodeAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- QRcodeAnalysis End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void koreanInternetAddress() {
  		System.out.println(" ! ----- koreanInternetAddress Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("한글인터넷 주소")) {
			System.out.println(" *** koreanInternetAddress pageLoad Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** koreanInternetAddress summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** koreanInternetAddress summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** koreanInternetAddress summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** koreanInternetAddress summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"한글인터넷주소", "유입수", "유입률", "전환수", "구매율", "전체\n전환-가입\n전환-주문\n전환-예약\n전환-신청\n전환-기타1\n전환-기타2\n전환-기타3"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** koreanInternetAddress statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** koreanInternetAddress statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** koreanInternetAddress statTableData check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_7").click();
  		$("#cell_7").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** koreanInternetAddress cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** koreanInternetAddress cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** koreanInternetAddress cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** koreanInternetAddress cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- koreanInternetAddress End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void comparePricesSite() {
  		System.out.println(" ! ----- comparePricesSite Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("가격비교 사이트")) {
			System.out.println(" *** comparePricesSite pageLoad Success !! *** ");
		} else {
			System.out.println(" *** comparePricesSite pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00", "-", "00:00:00"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** comparePricesSite summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** comparePricesSite summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00", "00:00:00"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** comparePricesSite summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** comparePricesSite summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"가격비교사이트", "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** comparePricesSite statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** comparePricesSite statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** comparePricesSite statTableData check Success !! *** ");
		} else {
			System.out.println(" *** comparePricesSite statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_7").click();
  		$("#cell_7").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** comparePricesSite cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** comparePricesSite cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** comparePricesSite cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** comparePricesSite cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- comparePricesSite End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void campaignGroupAnalysis() {
  		System.out.println(" ! ----- campaignGroupAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("캠페인 그룹 분석")) {
			System.out.println(" *** campaignGroupAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** campaignGroupAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** campaignGroupAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** campaignGroupAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** campaignGroupAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** campaignGroupAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"캠페인 그룹", "유입수", "유입률", "구매건수", "구매율"};
  		for(int i=0;i<=4;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** campaignGroupAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** campaignGroupAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** campaignGroupAnalysis statTableData check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroupAnalysis statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_11").click();
  		$("#cell_11").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** campaignGroupAnalysis cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** campaignGroupAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$(".floatLeft > a > img", 1).click();
  		$("#cell_check_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_11").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** campaignGroupAnalysis cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** campaignGroupAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- campaignGroupAnalysis End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}