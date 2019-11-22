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

public class movePath {
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
  	public void majorPagePath() {
  		System.out.println(" ! ----- majorPagePath Start ----- ! ");
  		$("#m_stat > ul > li > a", 8).click();
  		$(".active > ul > li > a > span", 0).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 0).click();
  		if($("h3", 0).text().trim().equals("주요 페이지경로")) {
			System.out.println(" *** majorPagePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** majorPagePath pageLoad Fail ... !@#$%^&*() *** ");
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
  		String[] summaryTableData = {"/index.html", "전환-주문", "전환-가입", "전환-예약", "전환-신청", "전환-기타1", "전환-기타2", "전환-기타3", 
  				"/search/label/miss..", "/search/label/miss..", "/search/label/miss..", "/search/label/file..", "/2019/08/1.html", "/2019/08/index.htm.."};
  		for(int i=0;i<=13;i++) {
  			if($("#data_summary > table > tbody > tr > td > table > tbody > tr > td > a", (i+2)).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** majorPagePath summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** majorPagePath summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($("#data_summary > table > tbody > tr > td > table > tbody > tr > td > a", (i+2)).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.", "1", "18", "2.  (8)", "25", "16", "3.  (8)", "8", "1", "4.  (4)", "4", "1", "5.  (3)", "3", "1", "6.  (2) (2) (9) (2)", "30", "1"};
  		for(int i=0;i<=17;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** majorPagePath statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** majorPagePath statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "-", "38"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** majorPagePath statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** majorPagePath statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$("#data_subimg1").click();
  		$(".statDataRight > table > tbody > tr > td", 4).waitUntil(visible, 10000);
  		if($(".statDataRight > table > tbody > tr > td", 4).text().trim().equals("/index.html[http://apzz092888.blogspot.com/index.html]")) {
				System.out.println(" *** majorPagePath statTableDetailData check Success !! *** ");
			} else {
				System.out.println(" *** majorPagePath statTableDetailData check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight > table > tbody > tr > td", 4).text().trim());
				close();
		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(hidden, 10000);
  		$(".statFootRight", 0).waitUntil(visible, 10000);
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** majorPagePath no-data check Success !! *** ");
		} else {
			System.out.println(" *** majorPagePath no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(visible, 10000);
  		$(".statDataCenter", 4).waitUntil(visible, 10000);
  		if($(".statDataCenter", 4).text().trim().equals("1")) {
			System.out.println(" *** majorPagePath statTableData check Success !! *** ");
		} else {
			System.out.println(" *** majorPagePath statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 4).text().trim());
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
  		if($(".statDataRight", 0).text().trim().equals("방문수")) {
  				System.out.println(" *** majorPagePath cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** majorPagePath cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  				System.out.println(" *** majorPagePath cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** majorPagePath cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- majorPagePath End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void customerMovePath() {
  		System.out.println(" ! ----- customerMovePath Start ----- ! ");
  		$(".active > ul > li > a > span", 1).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 1).click();
  		if($("h3", 0).text().trim().equals("구매자 이동경로")) {
			System.out.println(" *** customerMovePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** customerMovePath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000); 
  		String[] statTopTableData = {"페이지 이동경로(아이콘)\n         \n페이지 이동경로(URL)", "페이지수", "방문수"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** customerMovePath statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** customerMovePath statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** customerMovePath no-data check Success !! *** ");
		} else {
			System.out.println(" *** customerMovePath no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
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
  		if($(".statDataRight", 0).text().trim().equals("방문수")) {
  				System.out.println(" *** customerMovePath cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** customerMovePath cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  				System.out.println(" *** customerMovePath cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** customerMovePath cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- customerMovePath End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void nextPrevmovePath() {
  		System.out.println(" ! ----- nextPrevmovePath Start ----- ! ");
  		$(".active > ul > li > a > span", 2).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 2).click();
  		if($("h3", 0).text().trim().equals("이전,다음 페이지경로")) {
			System.out.println(" *** nextPrevmovePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** nextPrevmovePath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] nextPrevmovePathpageInfo = {"/index.html", "", "방문수 38", "페이지뷰 173", "방문당페이지뷰 4.55", "내부유출수 17", "내부유출율 44.74%", "방문종료수 21", "방문종료율 55.26%"};
  		for(int i=0;i<=8;i++) {
  			if($("#t_layer > tbody > tr", i).text().trim().equals(nextPrevmovePathpageInfo[i])) {
  				System.out.println(" *** nextPrevmovePath pageInfo(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nextPrevmovePath pageInfo(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($("#t_layer > tbody > tr", i).text().trim());
  				close();
  			}
  		}
  		$("#t_layer > tbody > tr > td > a > img").click();
  		$("#page_search_keyword").waitUntil(visible, 10000);
  		$("#page_search_keyword").setValue("signIn");
  		$("#page_search_div > table > tbody > tr > td > table >  tbody > tr > td > table > tbody > tr > td > a > img").click();
  		$("#page_search_div > table > tbody > tr > td > table >  tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 1).waitUntil(hidden, 10000);
  		$("#page_search_div > table > tbody > tr > td > table >  tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > img", 0).click();
  		$("#page_search_keyword").waitUntil(hidden, 10000);
  		sleep(200);
  		String[] nextPrevmovePathChangePageInfo = {"전환-가입", "", "방문수 17", "페이지뷰 34", "방문당페이지뷰 2.00", "내부유출수 17", "내부유출율 100.00%", "방문종료수 0", "방문종료율 0.00%"};
  		for(int i=0;i<=8;i++) {
  			if($("#t_layer > tbody > tr", i).text().trim().equals(nextPrevmovePathChangePageInfo[i])) {
  				System.out.println(" *** nextPrevmovePath changePageInfo(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nextPrevmovePath changePageInfo(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($("#t_layer > tbody > tr", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- nextPrevmovePath End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void menuMovePath() {
  		System.out.println(" ! ----- menuMovePath Start ----- ! ");
  		$(".active > ul > li > a > span", 3).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 3).click();
  		if($("h3", 0).text().trim().equals("메뉴별 이동경로")) {
			System.out.println(" *** menuMovePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** menuMovePath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		if($("#data_summary > table > tbody > tr", 1).text().trim().equals("/전환테스트")) {
  			System.out.println(" *** menuMovePath menuList Success !! *** ");
		} else {
			System.out.println(" *** menuMovePath menuList Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] statTopTableData = {"메뉴 이동경로(아이콘)\n         \n메뉴 이동경로(메뉴명)", "페이지수", "방문수"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** menuMovePath statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuMovePath statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  (14)", "14", "16", "2.  (13)", "13", "1"};
  		for(int i=0;i<=5;i++) {
  			if($(".statDataCenter", (i+3)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** menuMovePath statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuMovePath statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+3)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "-", "17"};
  		for(int i=0;i<=2;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** menuMovePath statFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** menuMovePath statFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statFootCenter", i).text().trim());
  				close();
  			}
  		}
  		$("#data_subimg1").click();  		
  		$("#data_subimg2").click();
  		$(".statDataRight", 8).waitUntil(visible, 10000);
  		String[] statDataRightTableData = {"방문비율", "94.12%", "", "메뉴별이동경로", "[/전환테스트](14)", "5.88%", "", "메뉴별이동경로", "[/전환테스트](13)"};  		
  		for(int i=0;i<=8;i++) {
  			if(i==2 || i==6 ) {
  				
  			} else {
  				if($(".statDataRight", i).text().trim().equals(statDataRightTableData[i])) {
  	  				System.out.println(" *** menuMovePath statDataRightTableData(" + i + ") check Success !! *** ");
  	  			} else {
  	  				System.out.println(" *** menuMovePath statDataRightTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  	  				System.out.println($(".statDataRight", i).text().trim());
  	  				close();	
  				}
  			}
  		}
  		if($(".statDataRight > table > tbody > tr > td", 4).text().trim().equals("[/전환테스트](14)")) {
				System.out.println(" *** menuMovePath statTableDetailData check Success !! *** ");
			} else {
				System.out.println(" *** menuMovePath statTableDetailData check Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight > table > tbody > tr > td", 4).text().trim());
				close();
		}
  		$(".formgray", 0).setValue("1234");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(hidden, 10000);
  		$(".statFootRight", 0).waitUntil(visible, 10000);
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** menuMovePath no-data check Success !! *** ");
		} else {
			System.out.println(" *** menuMovePath no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
			close();
		}
  		$(".formgray", 0).setValue("");
  		$("form > table > tbody > tr > td > img", 0).click();
  		$(".statDataCenterTableSub", 1).waitUntil(visible, 10000);
  		$(".statDataCenter", 4).waitUntil(visible, 10000);
  		if($(".statDataCenter", 4).text().trim().equals("14")) {
			System.out.println(" *** menuMovePath statTableData check Success !! *** ");
		} else {
			System.out.println(" *** menuMovePath statTableData check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 4).text().trim());
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
  		if($(".statDataRight", 0).text().trim().equals("방문수")) {
  				System.out.println(" *** menuMovePath cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** menuMovePath cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  				System.out.println(" *** menuMovePath cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** menuMovePath cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		System.out.println(" ! ----- menuMovePath End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void nextPrevMenuPath() {
  		System.out.println(" ! ----- nextPrevMenuPath Start ----- ! ");
  		$(".active > ul > li > a > span", 4).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 4).click();
  		if($("h3", 0).text().trim().equals("이전,다음 메뉴경로")) {
			System.out.println(" *** nextPrevMenuPath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** nextPrevMenuPath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] nextPrevmovePathpageInfo = {"전환테스트", "", "방문수 17", "페이지뷰 237", "방문당페이지뷰 13.94", "내부유출수 0", "내부유출율 0.00%", "방문종료수 17", "방문종료율 100.00%"};
  		for(int i=0;i<=8;i++) {
  			if($(".copy > tbody > tr", i).text().trim().equals(nextPrevmovePathpageInfo[i])) {
  				System.out.println(" *** nextPrevmovePath pageInfo(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** nextPrevmovePath pageInfo(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".copy > tbody > tr", i).text().trim());
  				close();
  			}
  		}
  		System.out.println(" ! ----- nextPrevMenuPath End ----- ! ");
  	}
  	@Test(priority = 6)
  	public void pageMoveDepth() {
  		System.out.println(" ! ----- pageMoveDepth Start ----- ! ");
  		$(".active > ul > li > a > span", 5).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 5).click();
  		if($("h3", 0).text().trim().equals("페이지 이동 깊이")) {
			System.out.println(" *** pageMoveDepth pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pageMoveDepth pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] summaryTableData = {"2019.08.08 ~ 2019.08.09", "38", "322.22%\n상승", "119", "상승", "313.16%"};
  		for(int i=0;i<=5;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(summaryTableData[i])) {
  				System.out.println(" *** pageMoveDepth summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] summaryFootTableData = {"2019.08.06 ~ 2019.08.07", "9", "0", "0.00%"};
  		for(int i=0;i<=3;i++) {
  			if($(".summaryFootCenter", i).text().trim().equals(summaryFootTableData[i])) {
  				System.out.println(" *** pageMoveDepth summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryFootCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTopTableData = {"방문경로깊이", "방문수", "방문비율", "전환수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** pageMoveDepth statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] statTableData = {"1.  1 페이지", "18", "47.37%", "0", "2.  2~4 페이지", "2", "5.26%", "0", "3.  5~9 페이지", "1", "2.63%", "0", "4.  21 페이지 이상", "17", "44.74%", "119"};
  		for(int i=0;i<=15;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(statTableData[i])) {
  				System.out.println(" *** pageMoveDepth summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
  		String[] statFootTableData = {"합계", "38", "100.00%", "119"};
  		for(int i=0;i<=3;i++) {
  			if($(".statFootCenter", i).text().trim().equals(statFootTableData[i])) {
  				System.out.println(" *** pageMoveDepth summaryFootTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth summaryFootTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("전환수")) {
  				System.out.println(" *** pageMoveDepth cellEdit check 0 Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
  		if($(".statDataRight", 0).text().trim().equals("전환율")) {
  				System.out.println(" *** pageMoveDepth cellEdit check 1 Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth cellEdit check 1 Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataRight", 0).text().trim());
  				close();
  		}
  		String[] visitCountPieChartData = {"1 페이지18", "47.37%", "21 페이지 이상17", "44.74%", "2~4 페이지2", "5.26%", "5~9 페이지1", "2.63%"};
  		for(int i=0,x=0;i<=3;i++) {
  			for(int z=0;z<=5;z++) {
  	  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();  				
  			}
  			for(int y=0;y<=1;y++) {
  	  			if($(".highcharts-tooltip", 0).text().trim().split(": ")[y].equals(visitCountPieChartData[x])) {
  					System.out.println(" *** pageMoveDepth visitPieChartData(" + x + ") check Success !! *** ");
  				} else {
  					System.out.println(" *** pageMoveDepth visitPieChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
  					System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[y]);
  					close();
  	  			}
  	  			x++;
  			}
  		}
  		String[] visitCountLineChartData = {"2019.08.09 (금)", "1 페이지 방문수: 10", "2019.08.08 (목)", "1 페이지 방문수: 8", "2019.08.09 (금)", "21 페이지 이상 방문수: 17", 
  				"2019.08.08 (목)", "21 페이지 이상 방문수: 0", "2019.08.09 (금)", "2~4 페이지 방문수: 2", "2019.08.08 (목)", "2~4 페이지 방문수: 0", 
  				"2019.08.09 (금)", "5~9 페이지 방문수: 1", "2019.08.08 (목)", "5~9 페이지 방문수: 0"};
  		for(int i=0,x=0;i<=3;i++) {
  			for(int a=0;a<=5;a++) {
  	  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).hover();  				
  			}
  			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", i).click();
  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", 0).waitUntil(visible, 10000);
  			for(int y=0;y<=1;y++) {
  				for(int b=0;b<=5;b++) {
  	  				$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", y).hover();	
  				}
  	  			for(int z=0;z<=1;z++) {
	  	  			if($(".highcharts-tooltip", 1).text().trim().split("● ")[z].equals(visitCountLineChartData[x])) {
	  					System.out.println(" *** pageMoveDepth visitCountLineChartData(" + x + ") check Success !! *** ");
	  				} else {
	  					System.out.println(" *** pageMoveDepth visitCountLineChartData(" + x + ") check Fail ... !@#$%^&*() *** ");
	  					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[z]);
	  					close();
	  	  			}
	  	  			x++;
  	  			}
  			}
  		}
  		$("#stat_graph > table > tbody > tr > td > table > tbody > tr > td > table > tbody > tr > td > a > font").click();
  		sleep(500);
		$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).waitUntil(visible, 10000);
  		for(int i=0;i<=5;i++) {
			$(".highcharts-data-labels.highcharts-series-0.highcharts-tracker > g", 0).hover();
		}
  		String[] convertCountPieChartData = {"21 페이지 이상119", "100.00%"};
		for(int i=0;i<=1;i++) {
  			if($(".highcharts-tooltip", 0).text().trim().split(": ")[i].equals(convertCountPieChartData[i])) {
				System.out.println(" *** pageMoveDepth convertCountPieChartData(" + i + ") check Success !! *** ");
			} else {
				System.out.println(" *** pageMoveDepth convertCountPieChartData(" + i + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".highcharts-tooltip", 0).text().trim().split(": ")[i]);
				close();
  			}
		}
		String[] convertCountLineChartData = {"2019.08.09 (금)", "21 페이지 이상 전환수: 119", "2019.08.08 (목)", "21 페이지 이상 전환수: 0"};
		for(int i=0,y=0;i<=1;i++) {
			for(int z=0;z<=5;z++) {
	  			$(".highcharts-markers.highcharts-series-0.highcharts-tracker > path", i).hover();	
			}
			for(int x=0;x<=1;x++) {
				if($(".highcharts-tooltip", 1).text().trim().split("● ")[x].equals(convertCountLineChartData[y])) {
					System.out.println(" *** pageMoveDepth convertCountLineChartData(" + y + ") check Success !! *** "); 
				} else {
					System.out.println(" *** pageMoveDepth convertCountLineChartData(" + y + ") check Fail ... !@#$%^&*() *** ");
					System.out.println($(".highcharts-tooltip", 1).text().trim().split("● ")[x]);
					close(); 
				}
				y++;
			}
		}
		$(".statDataCenter > table > tbody > tr > td  > a > img", 0).click();
		switchTo().window(1);
  		if($("h3", 0).text().trim().equals("페이지 이동 깊이 > 페이지 이동 상세")) {
			System.out.println(" *** pageMoveDepth Detail pageLoad Success !! *** ");
		} else {
			System.out.println(" *** pageMoveDepth Detail pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		String[] DetailSummaryTableData = {"1 페이지", "18", "0"};
  		for(int i=0;i<=2;i++) {
  			if($(".summaryDataCenter", i).text().trim().equals(DetailSummaryTableData[i])) {
  				System.out.println(" *** pageMoveDepth Detail summaryTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth Detail summaryTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".summaryDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] DetailStatTopTableData = {"순번", "페이지이동경로(URL)", "페이지수", "방문수"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", i).text().trim().equals(DetailStatTopTableData[i])) {
  				System.out.println(" *** visitInflow statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** visitInflow statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
  		String[] DetailStatTableData = {"1", "[http://apzz092888.blogspot.com/index.html]", "1", "18"};
  		for(int i=0;i<=3;i++) {
  			if($(".statDataCenter", (i+4)).text().trim().equals(DetailStatTableData[i])) {
  				System.out.println(" *** pageMoveDepth Detail statTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** pageMoveDepth Detail statTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", (i+4)).text().trim());
  				close();
  			}
  		}
		switchTo().window(0);
  		System.out.println(" ! ----- pageMoveDepth End ----- ! ");
  	}
  	@Test(priority = 7)
  	public void keywordMovePath() {
  		System.out.println(" ! ----- keywordMovePath Start ----- ! ");
  		$(".active > ul > li > a > span", 6).waitUntil(visible, 10000);
  		$(".active > ul > li > a > span", 6).click();
  		if($("h3", 0).text().trim().equals("키워드별 이동경로")) {
			System.out.println(" *** keywordMovePath pageLoad Success !! *** ");
		} else {
			System.out.println(" *** keywordMovePath pageLoad Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
  		}
  		$(".btn_help", 0).waitUntil(visible, 10000);
  		String[] statTopTableData = {"페이지 이동경로(아이콘)\n         \n페이지 이동경로(URL)", "페이지수", "방문수"};
  		for(int i=0;i<=2;i++) {
  			if($(".statDataCenter", i).text().trim().equals(statTopTableData[i])) {
  				System.out.println(" *** keywordMovePath statTopTableData(" + i + ") check Success !! *** ");
  			} else {
  				System.out.println(" *** keywordMovePath statTopTableData(" + i + ") check Fail ... !@#$%^&*() *** ");
  				System.out.println($(".statDataCenter", i).text().trim());
  				close();
  			}
  		}
		if($(".statFootRight", 0).text().trim().equals("자료가 없습니다.")) {
			System.out.println(" *** keywordMovePath no-data check Success !! *** ");
		} else {
			System.out.println(" *** keywordMovePath no-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".statDataCenter", 0).text().trim());
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
		if($(".statDataRight", 0).text().trim().equals("방문수")) {
				System.out.println(" *** keywordMovePath cellEdit check 0 Success !! *** ");
			} else {
				System.out.println(" *** keywordMovePath cellEdit check 0 Fail ... !@#$%^&*() *** ");
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
				System.out.println(" *** keywordMovePath cellEdit check 1 Success !! *** ");
			} else {
				System.out.println(" *** keywordMovePath cellEdit check 1 Fail ... !@#$%^&*() *** ");
				System.out.println($(".statDataRight", 0).text().trim());
				close();
		}
  		System.out.println(" ! ----- keywordMovePath End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}