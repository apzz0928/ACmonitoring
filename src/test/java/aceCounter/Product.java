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

public class Product {
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
  	public void productSalesAnalysis() {
  		System.out.println(" ! ----- productSalesAnalysis Start ----- ! ");
  		$("#m_stat > ul > li > a", 16).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("제품별 매출 분석")) {
			System.out.println(" *** productSalesAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** productSalesAnalysis pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "매출액"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** productSalesAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSalesAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** productSalesAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSalesAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** productSalesAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSalesAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품명", "노출수", "구매건수", "구매수량", "구매율", "매출액"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** productSalesAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSalesAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** productSalesAnalysis statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** productSalesAnalysis statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** productSalesAnalysis cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** productSalesAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
			System.out.println(" *** productSalesAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** productSalesAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- productSalesAnalysis End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void productPurchaseRate() {
  		System.out.println(" ! ----- productPurchaseRate Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("제품별 구매율 분석")) {
			System.out.println(" *** productPurchaseRate pageLoad Success !! *** ");
		} else {
			System.out.println(" *** productPurchaseRate pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "담긴수/노출수(%)", "구매건수/노출수(%)"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** productPurchaseRate summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productPurchaseRate summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0.00%", "-", "0.00%", "-", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** productPurchaseRate summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productPurchaseRate summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0.00%", "0.00%", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** productPurchaseRate summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productPurchaseRate summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품명", "노출수", "담긴수", "구매건수", "구매수량", "담긴수/노출수 (%)", "구매건수/노출수 (%)"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** productPurchaseRate statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productPurchaseRate statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** productPurchaseRate statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** productPurchaseRate statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("구매건수/노출수 (%)")) {
			System.out.println(" *** productSalesAnalysis cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** productSalesAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매건수/담긴수 (%)")) {
			System.out.println(" *** productSalesAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** productSalesAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- productPurchaseRate End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void productSuiteSalesAnalysis() {
  		System.out.println(" ! ----- productSuiteSalesAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("제품군별 매출 분석")) {
			System.out.println(" *** productSuiteSalesAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** productSuiteSalesAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "매출액"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** productSuiteSalesAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuiteSalesAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** productSuiteSalesAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuiteSalesAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** productSuiteSalesAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuiteSalesAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품군명", "노출수", "구매건수", "구매수량", "구매율", "매출액"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** productSuiteSalesAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuiteSalesAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** productSuiteSalesAnalysis statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** productSuiteSalesAnalysis statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** productSuiteSalesAnalysis cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** productSuiteSalesAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
			System.out.println(" *** productSuiteSalesAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** productSuiteSalesAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- productSuiteSalesAnalysis End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void productSuitPurchaseRate() {
  		System.out.println(" ! ----- productSuitPurchaseRate Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("제품군별 구매율 분석")) {
			System.out.println(" *** productSuitPurchaseRate pageLoad Success !! *** ");
		} else {
			System.out.println(" *** productSuitPurchaseRate pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "담긴수/노출수(%)", "구매건수/노출수(%)"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** productSuitPurchaseRate summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuitPurchaseRate summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0.00%", "-", "0.00%", "-", "0.00%"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** productSuitPurchaseRate summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuitPurchaseRate summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0.00%", "0.00%", "0.00%"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** productSuitPurchaseRate summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuitPurchaseRate summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품군명", "노출수", "담긴수", "구매건수", "구매수량", "담긴수/노출수 (%)", "구매건수/노출수 (%)"};
  		for(int i=0;i<=6;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** productSuitPurchaseRate statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productSuitPurchaseRate statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** productSuitPurchaseRate statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** productSuitPurchaseRate statFootTableData no-data check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("구매건수/노출수 (%)")) {
			System.out.println(" *** productSuitPurchaseRate cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** productSuitPurchaseRate cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("구매건수/담긴수 (%)")) {
			System.out.println(" *** productSuiteSalesAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** productSuiteSalesAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- productSuitPurchaseRate End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void productInflow() {
  		System.out.println(" ! ----- productInflow Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("제품별 유입출처")) {
			System.out.println(" *** productInflow pageLoad Success !! *** ");
		} else {
			System.out.println(" *** productInflow pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "매출액"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** productInflow summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productInflow summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** productInflow summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productInflow summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** productInflow summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productInflow summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품명", "유입출처 종류", "구매건수", "구매수량", "구매건수가 높은 유입출처", "매출액"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** productInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** productInflow statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** productInflow statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootCenter", 0).text().trim());
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
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
  				System.out.println(" *** productInflow cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** productInflow cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("매출액비율(%)")) {
  				System.out.println(" *** productInflow cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** productInflow cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- productInflow End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void interestProductAnalysis() {
  		System.out.println(" ! ----- interestProductAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("관심제품 분석")) {
			System.out.println(" *** interestProductAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** interestProductAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "구매건수", "구매율"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** interestProductAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** interestProductAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0.00%", "-", "0"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** interestProductAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** interestProductAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0.00%", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** interestProductAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** interestProductAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품명", "노출수", "담긴수", "구매건수", "구매수량", "구매율"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** interestProductAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** interestProductAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** interestProductAnalysis statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** interestProductAnalysis statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		confirm("현재구성이 기본구성과 같습니다!");
  		$("#cell_check_7").click();
  		$("#cell_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("비율")) {
			System.out.println(" *** interestProductAnalysis cellEdit check 0 Success !! *** ");
		} else {
			System.out.println(" *** interestProductAnalysis cellEdit check 0 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		$(".floatLeft > a > img", 0).click();
  		$("#cell_check_7").waitUntil(visible, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click(); 		
  		confirm("기본구성으로 되돌리시겠습니까?\n현재구성은 다시 복구되지 않습니다.");
  		$("#cell_7").waitUntil(hidden, 10000);
  		$("#cell_edit_div > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).click();
  		$("#cell_edit_div").waitUntil(hidden, 10000);
  		sleep(800);
  		if($(".statDataRight", 0).text().trim().equals("매출액")) {
			System.out.println(" *** interestProductAnalysis cellEdit check 1 Success !! *** ");
		} else {
			System.out.println(" *** interestProductAnalysis cellEdit check 1 Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataRight", 0).text().trim());
			close();
  		}
  		System.out.println(" ! ----- interestProductAnalysis End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void productMemberAnalysis() {
  		System.out.println(" ! ----- productMemberAnalysis Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("제품별 회원 분석")) {
			System.out.println(" *** productMemberAnalysis pageLoad Success !! *** ");
		} else {
			System.out.println(" *** productMemberAnalysis pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTopTableData = {"분석 기간", "회원", "구매수량", "매출액", "구매수량"};
  		for(int i=0;i<=4;i++) {
  			if($(".summaryTopCenter", i).text().trim().equals(summaryTopTableData[i])) {
  				System.out.println(" *** productMemberAnalysis summaryTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productMemberAnalysis summaryTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryTopCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "0", "-", "0", "-", "0", "-", "0"};
  		for(int i=0;i<=7;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** productMemberAnalysis summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productMemberAnalysis summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "0", "0", "0"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** productMemberAnalysis summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productMemberAnalysis summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"제품명", "회원", "비회원", "회원 구매수량", "회원 매출액", "비회원 구매수량"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** productMemberAnalysis statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** productMemberAnalysis statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** productMemberAnalysis statFootTableData no-data check Success !! *** ");
		} else {
			System.out.println(" *** productMemberAnalysis statFootTableData no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statFootRight", 0).text().trim());
			close();
		}
  		System.out.println(" ! ----- productMemberAnalysis End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}