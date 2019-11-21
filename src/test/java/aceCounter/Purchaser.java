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

public class Purchaser {
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
  	public void purchaseAnalysis() {
  		System.out.println(" ! ----- purchaseAnalysis Start ----- ! ");
  		$("#m_stat > ul > li > a", 15).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("구매분석")) {
			System.out.println(" *** purchaseAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** purchaseAnalysis pageLoad Fail ... !@#$%^&*() *** ");
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
  		$(".statTableHeaderText > select").selectOption("일간으로보기");
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "매출액", "순방문수", "구매건수"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** purchaseAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "36", "300.00%\n상승", "0", "-", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** purchaseAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "9", "0", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** purchaseAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"시간으로보기\n        \n일간으로보기\n        \n주간으로보기\n        \n월간으로보기\n        \n분기로보기", "매출액", "순방문수"
  				, "구매건수", "구매비율", "구매율", "최근1개월평균 구매건수\n        \n최근3개월평균 구매건수\n        \n최근1주평균 구매건수"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** purchaseAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (목)", "0", "8", "0", "0.00%", "0.00%", "0", "2019.08.09 (금)", "0", "28", "0", "0.00%", "0.00%", "0"};
  		for(int i=0;i<=13;i++) {
  			if($(".statDataCenter", (i+7)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** purchaseAnalysis statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+7)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "0(0.00)", "36(18.00)", "0(0.00)", "0.00%", "0.00%", "0(0.00)"};
  		for(int i=0;i<=6;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** purchaseAnalysis statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_8").click();
  		$("#cell_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("증감")) {
				System.out.println(" *** purchaseAnalysis cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** purchaseAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_8").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_8").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("대비")) {
				System.out.println(" *** purchaseAnalysis cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** purchaseAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
  		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
  		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("구매분석 > 구매 상세")) {
			System.out.println(" *** purchaseAnalysis pageLoad Detail Success !! *** ");
		} else {
			System.out.println(" *** purchaseAnalysis pageLoad Detail Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] DetailSummaryTableData = {"0", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(DetailSummaryTableData[i])) {
  				System.out.println(" *** purchaseAnalysis Detail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaseAnalysis Detail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** purchaseAnalysis Detail statFootTableData check Success !! *** ");
		} else {
			System.out.println(" *** purchaseAnalysis Detail statFootTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("구매분석")) {
  			System.out.println(" *** purchaseAnalysis window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** purchaseAnalysis window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- purchaseAnalysis End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void newReVisitPurchaser() {
  		System.out.println(" ! ----- newReVisitPurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("신규방문과 재방문구매")) {
			System.out.println(" *** newReVisitPurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newReVisitPurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "신규방문", "구매건수", "매출액", "구매건수"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0", "0"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"시간으로보기\n        \n일간으로보기\n        \n주간으로보기\n        \n월간으로보기\n        \n분기로보기", "신규방문", "재방문"
  				, "방문수", "구매건수", "구매율", "매출액", "방문수", "구매건수", "구매율"};
  		for(int i=0;i<=9;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (목)", "8", "0", "0.00%", "0", "0", "0", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+10)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+10)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계(평균)", "36(18.00)", "0(0.00)", "0.00%", "0(0.00)", "2(1.00)", "0(0.00)", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newReVisitPurchaser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newReVisitPurchaser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- newReVisitPurchaser End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void visitVSpurchaser() {
  		System.out.println(" ! ----- visitVSpurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("방문수vs구매수")) {
			System.out.println(" *** visitVSpurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** visitVSpurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "방문수", "구매건수"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** visitVSpurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n상승", "0", "-", "0.00%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** visitVSpurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** visitVSpurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"시간으로보기\n        \n일간으로보기\n        \n주간으로보기\n        \n월간으로보기\n        \n분기로보기", "방문수", "구매건수"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** visitVSpurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (목)", "8", "0", "2019.08.09 (금)", "30", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** visitVSpurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38(19.00)", "0(0.00)"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** visitVSpurchaser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("구매건수")) {
  				System.out.println(" *** visitVSpurchaser cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** visitVSpurchaser cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** visitVSpurchaser cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		String[] BarChartData = {"2019.08.08 (목)", "방문수: 8", "2019.08.09 (금)", "방문수: 30"};
  		for(int i=0,y=0;i<=1;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("● ")[x].equals(BarChartData[y])) {
  	  				System.out.println(" *** visitVSpurchaser BarChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** visitVSpurchaser BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		System.out.println(" ! ----- visitVSpurchaser End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void newRePurchaser() {
  		System.out.println(" ! ----- newRePurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("신규구매와 재구매")) {
			System.out.println(" *** newRePurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** newRePurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "신규구매", "구매건수", "매출액", "구매건수"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** newRePurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** newRePurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** newRePurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"시간으로보기\n        \n일간으로보기\n        \n주간으로보기\n        \n월간으로보기\n        \n분기로보기", "신규구매", "재구매"
  				, "구매수량", "구매건수", "매출액", "구매수량", "구매건수"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** newRePurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (목)", "0", "0", "0", "0", "0", "2019.08.09 (금)", "0", "0", "0", "0", "0"};
  		for(int i=0;i<=11;i++) {
  			if($(".statDataCenter", (i+8)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** newRePurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+8)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "0(0.00)", "0(0.00)", "0(0.00)", "0(0.00)", "0(0.00)"};
  		for(int i=0;i<=5;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** newRePurchaser statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** newRePurchaser statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- newRePurchaser End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void purchaserTime() {
  		System.out.println(" ! ----- purchaserTime Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("구매까지 걸린시간")) {
			System.out.println(" *** purchaserTime pageLoad Success !! *** ");
		} else {
			System.out.println(" *** purchaserTime pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** purchaserTime summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** purchaserTime summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** purchaserTime summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"소요시간", "구매건수", "구매비율", "매출액"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** purchaserTime statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"소요시간", "구매건수", "구매비율", "매출액"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** purchaserTime statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** purchaserTime statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** purchaserTime statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
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
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** purchaserTime cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액비율")) {
  				System.out.println(" *** purchaserTime cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** purchaserTime cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- purchaserTime End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void rePurchaserTerm() {
  		System.out.println(" ! ----- rePurchaserTerm Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("재구매주기")) {
			System.out.println(" *** rePurchaserTerm pageLoad Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** rePurchaserTerm summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** rePurchaserTerm summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** rePurchaserTerm summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"소요기간", "구매건수", "구매비율", "매출액"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** rePurchaserTerm statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** rePurchaserTerm statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** rePurchaserTerm cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액비율")) {
			System.out.println(" *** rePurchaserTerm cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- rePurchaserTerm End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void memberNoMemberPurchaser() {
  		System.out.println(" ! ----- memberNoMemberPurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("회원vs비회원 구매")) {
			System.out.println(" *** memberNoMemberPurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberNoMemberPurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "회원", "구매건수", "매출액", "구매건수"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"일시", "회원", "비회원", "구매건수", "매출액", "구매건수"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"2019.08.08 (목)", "0", "0", "0", "2019.08.09 (금)", "0", "0", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", (i+6)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaser statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaser statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+6)).text().trim());
  				close();
  			}
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 0).click();
  		switchTo().window(2);
  		if($("h3", 0).text().trim().equals("회원vs비회원 구매 > 구매 상세 > 회원")) {
  			System.out.println(" *** memberNoMemberPurchaserProgress window(1) Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaserProgress window(1) Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		String[] summaryTopProgressTableData = {"구매건수", "구매수량"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryProgressTableData = {"0", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopProgressTableData = {"순번", "IP", "유입출처", "구매수량", "매출액", "결제 수단", "회원ID", "방문일시"};
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress statTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserMemberProgress statTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("회원vs비회원 구매")) {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		$(".statDataCenter > table > tbody > tr > td > a > img", 1).click();
  		switchTo().window(3);
  		if($("h3", 0).text().trim().equals("회원vs비회원 구매 > 구매 상세 > 비회원")) {
  			System.out.println(" *** memberNoMemberPurchaserMemberProgress window(3) Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaserProgress window(3) Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		for(int i=0;i<=1;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress summaryProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		for(int i=0;i<=7;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopProgressTableData[i])) {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress statTopProgressTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberNoMemberPurchaserNoMemberProgress statTopProgressTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** rePurchaserTerm statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		switchTo().window(0);
  		if($("h3", 0).text().trim().equals("회원vs비회원 구매")) {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Success !! *** ");
  		} else {
  			System.out.println(" *** memberNoMemberPurchaser window(0) check Fail ... !@#$%^&*() *** ");
  			System.out.println($("h3", 0).text().trim());
  			close();
  		}
  		System.out.println(" ! ----- memberNoMemberPurchaser End ----- ! ");
  	}
  	@Test(priority = 8)
  	public void memberAttributePurchaser() {
  		System.out.println(" ! ----- memberAttributePurchaser Start ----- ! ");
  		$(".active > ul > li > a > span", 7).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 7).click();
  		if($("h3", 0).text().trim().equals("회원특성별 구매")) {
			System.out.println(" *** memberAttributePurchaser pageLoad Success !! *** ");
		} else {
			System.out.println(" *** memberAttributePurchaser pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "구매수량"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"연령대vs성별\n회원 그룹명vs성별", "소계", "남성", "여성", "알수없음", "구매건수", "구매수량", "매출액", "구매건수", "구매수량", "매출액", "구매건수", "구매수량"
  				, "매출액", "구매건수", "구매수량"};
  		for(int i=0;i<=15;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** memberAttributePurchaser statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** memberAttributePurchaser statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** memberAttributePurchaser statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** memberAttributePurchaser statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- memberAttributePurchaser End ----- ! ");
  	}
  	@Test(priority = 9)
  	public void faverPurchasePrice() {
  		System.out.println(" ! ----- faverPurchasePrice Start ----- ! ");
  		$(".active > ul > li > a > span", 8).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 8).click();
  		if($("h3", 0).text().trim().equals("구매선호 가격대")) {
			System.out.println(" *** faverPurchasePrice pageLoad Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매수량"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** faverPurchasePrice summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** faverPurchasePrice summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0"};
  		for(int i=0;i<=1;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** faverPurchasePrice summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"가격대", "노출수", "장바구니 담긴수", "구매건수", "구매수량", "구매수량비율", "매출액"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** faverPurchasePrice statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** faverPurchasePrice statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** faverPurchasePrice statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_7").click();
  		$("#cell_7").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** faverPurchasePrice cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_6").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액비율")) {
			System.out.println(" *** faverPurchasePrice cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** faverPurchasePrice cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- faverPurchasePrice End ----- ! ");
  	}
  	@Test(priority = 10)
  	public void paymentAnalysis() {
  		System.out.println(" ! ----- paymentAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 9).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 9).click();
  		if($("h3", 0).text().trim().equals("결제 수단 분석")) {
			System.out.println(" *** paymentAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "매출액", "구매건수가 많은 결제 수단"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** paymentAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** paymentAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** paymentAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"결제 수단", "구매건수", "구매건수 비율", "매출액"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** paymentAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** paymentAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** paymentAnalysis statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** paymentAnalysis cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액 비율")) {
			System.out.println(" *** paymentAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** paymentAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- paymentAnalysis End ----- ! ");
  	}
  	@Test(priority = 11)
  	public void timeAvgBuyCount() {
  		System.out.println(" ! ----- timeAvgBuyCount Start ----- ! ");
  		$(".active > ul > li > a > span", 10).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 10).click();
  		if($("h3", 0).text().trim().equals("시간대별평균구매수")) {
			System.out.println(" *** timeAvgBuyCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** timeAvgBuyCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "시간평균 매출액", "시간평균 구매건수"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0.00", "-", "0.00", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0.00", "0.00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"시간대", "평균 매출액", "평균 순방문수", "평균 구매건수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.50", "1.00", "1.00", "1.00", "1.00", "2.00", "2.50", "5.00", "3.00"};
  		for(int i=0,x=0;i<=8;i=i+4) {
  			if($(".statDataCenter", (i+42)).text().trim().equals(statTableData[x])) {
  				System.out.println(" *** timeAvgBuyCount statTableData(" + x + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount statTableData(" + x + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+42)).text().trim());
  				close();
  			}
  			x++;
  		}
  		String[] statFootTableData = {"시간평균", "0.00", "0.75", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** timeAvgBuyCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** timeAvgBuyCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("평균 구매건수")) {
			System.out.println(" *** timeAvgBuyCount cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** timeAvgBuyCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
			System.out.println(" *** timeAvgBuyCount cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** timeAvgBuyCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > a > font", 1).click();
  		$(".highcharts-series.highcharts-series-1").waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		String[] BarChartData = {"09시", "순방문수: 1.5", "10시", "순방문수: 1", "11시", "순방문수: 1", "12시", "순방문수: 1", "13시", "순방문수: 1", 
  				"14시", "순방문수: 2", "15시", "순방문수: 2.5", "16시", "순방문수: 5", "17시", "순방문수: 3"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 9).waitUntil(visible, 10000);
  		for(int i=9,y=0;i<=17;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("● ")[x].equals(BarChartData[y])) {
  	  				System.out.println(" *** timeAvgBuyCount BarChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** timeAvgBuyCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		System.out.println(" ! ----- timeAvgBuyCount End ----- ! ");
  	}
  	@Test(priority = 12)
  	public void dayAvgBuyCount() {
  		System.out.println(" ! ----- dayAvgBuyCount Start ----- ! ");
  		$(".active > ul > li > a > span", 11).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 11).click();
  		if($("h3", 0).text().trim().equals("요일별평균구매수")) {
			System.out.println(" *** dayAvgBuyCount pageLoad Success !! *** ");
		} else {
			System.out.println(" *** dayAvgBuyCount pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "일평균 매출액", "평균 구매건수"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.03 ~ 2019.08.09", "0.00", "-", "0.00", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.07.27 ~ 2019.08.02", "0.00", "0.00"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"요일", "평균 매출액", "평균 순방문수", "평균구매건수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"(월)", "0.00", "0.00", "0.00", "(화)", "0.00", "0.00", "0.00", "(수)", "0.00", "9.00", "0.00", "(목)", "0.00", "8.00", "0.00"
  				, "(금)", "0.00", "28.00", "0.00", "(토)", "0.00", "0.00", "0.00", "(일)", "0.00", "0.00", "0.00"};
  		for(int i=0;i<=27;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"일평균", "0.00", "6.43", "0.00"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** dayAvgBuyCount statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("평균구매건수")) {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("구매율")) {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** dayAvgBuyCount cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > a > font", 1).click();
  		$(".highcharts-series.highcharts-series-1").waitUntil(visible, 10000);
  		js("$j('.highcharts-series.highcharts-series-1').css('display', 'none')");
  		String[] BarChartData = {"(수)", "순방문수: 9", "(목)", "순방문수: 8", "(금)", "순방문수: 28"};
  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", 2).waitUntil(visible, 10000);
  		for(int i=2,y=0;i<=4;i++) {
  	  		for(int x=0;x<=5;x++) {
  	  	  		$(".highcharts-series.highcharts-series-0.highcharts-tracker > rect", i).hover();	
  	  		}
  			for(int x=0;x<=1;x++) {
  				if($(".highcharts-tooltip", 0).text().trim().split("● ")[x].equals(BarChartData[y])) {
  	  				System.out.println(" *** dayAvgBuyCount BarChartData(" + y + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** dayAvgBuyCount BarChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[x]);
  	  				close();
  	  			}
  				y++;
  	  		}	
  		}
  		System.out.println(" ! ----- dayAvgBuyCount End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}