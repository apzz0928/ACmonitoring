package aceCounter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
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

public class Setting {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, temp_pw, A, B, pageLoadCheck;
    
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
		B = "14@#";
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
	@SuppressWarnings("unused")
	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}
	private static void alertCheck(String msg, String alert) {
		if(switchTo().alert().getText().equals(alert)) {
			confirm(alert);
			System.out.println(" *** " + msg + " check Success !! *** ");
		} else {
			System.out.println(switchTo().alert().getText());
			System.out.println(" *** " + msg + " check Fail ... !@#$%^&*() *** ");
	  		close();
		}
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
  		$(".btn_blue").click();
  		$("#contents_top").waitUntil(visible, 10000);
  		$(".list3").click();
  		System.out.println(" ! ----- login End ----- ! ");
  	}
  	@Test(priority = 1)
  	public void IPFiltering() {
		System.out.println(" ! ----- IPFiltering Start ----- ! ");
		$(".manager_b", 0).click();
		if($("h2").text().trim().equals("IP필터링")) {
			System.out.println(" *** IPFiltering pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** IPFiltering pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().equals("등록된 IP가 없습니다.\n" + "추가를 클릭해 필터링할 IP를 등록하세요.")) {
			System.out.println(" *** IPFiltering list empty-data check Success !! *** ");			
		} else {
			System.out.println(" *** IPFiltering list empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple").click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("IPFiltering null msg", "IP를 입력하세요.");
		$(".numeric", 0).setValue("127");
		$(".numeric", 1).setValue("0");
		$(".numeric", 2).setValue("0");
		$(".numeric", 3).setValue("1");
		$(".btn_blue", 1).click();
		alertCheck("IPFiltering add msg", "IP가 등록되었습니다.");
		$(".empty_td").waitUntil(hidden, 10000);
		$(".left", 1).waitUntil(visible, 10000);
		$(".input_txt", 0).setValue("12345");
		$(".btn_blue", 0).click();
		$(".left", 1).waitUntil(hidden, 10000);
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** IPFiltering list empty-search check Success !! *** ");			
		} else {
			System.out.println(" *** IPFiltering list empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".input_txt", 0).setValue("127");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("127.0.0.1")) {
			System.out.println(" *** IPFiltering search check Success !! *** ");			
		} else {
			System.out.println(" *** IPFiltering search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".js-all_check").click();
		$(".btn_white", 1).click();
		alertCheck("IPFiltering del confirm msg", "선택한 IP를 삭제하시겠습니까?");
		alertCheck("IPFiltering del msg", "IP가 삭제되었습니다.");
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("등록된 IP가 없습니다.\n" + "추가를 클릭해 필터링할 IP를 등록하세요.")) {
			System.out.println(" *** IPFiltering list delete check Success !! *** ");			
		} else {
			System.out.println(" *** IPFiltering list delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		System.out.println(" ! ----- IPFiltering End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void convertPage() {
		System.out.println(" ! ----- convertPage Start ----- ! ");
		$(".manager_s", 0).click();
		if($("h2").text().trim().equals("페이지 ▶ 전환페이지")) {
			System.out.println(" *** convertPage pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** convertPage pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		String[] tableDataCheck = {"전환-기타3", "전환-기타2", "전환-기타1", "전환-신청", "전환-예약", "전환-주문", "전환-가입"};
		for(int i=2,x=0;i<=38;i=i+6) {
			if($(".left", i).text().trim().equals(tableDataCheck[x])) {
				System.out.println(" *** convertPage setting table data check(" + x + ") Success !! *** ");
			} else {
				System.out.println(" *** convertPage setting table data check(" + x + ") Fail ... !@#$%^&*() *** ");
				System.out.println($(".left", i).text().trim());
				close();
			}
			x++;
		}
		$(".input_txt").setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** convertPage setting list empty-search check Success !! *** ");			
		} else {
			System.out.println(" *** convertPage setting list empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
	    $(By.name("list_type")).selectOption("삭제리스트");
		$(".btn_purple").waitUntil(hidden, 10000);
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** convertPage setting list empty-search check Success !! *** ");
		} else {
			System.out.println(" *** convertPage setting list empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".input_txt").setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		if($(".left", 2).text().trim().equals("전환-주문")) {
			System.out.println(" *** convertPage delete list data check Success !! *** ");
		} else {
			System.out.println(" *** convertPage delete list data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		System.out.println(" ! ----- convertPage End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void pageName() {
		System.out.println(" ! ----- pageName Start ----- ! ");
		$(".manager_s", 1).click();
		if($("h2").text().trim().equals("페이지 ▶ 페이지명")) {
			System.out.println(" *** pageName pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** pageName pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		$(".input_txt").setValue("change");
		$(".btn_blue").click();
		$(".last").waitUntil(hidden, 10000);
		String[] tableDataCheck = {"/search/label/change-other3", "/search/label/change-other2", "/search/label/change-other1", "/search/label/change-request"
				, "/search/label/change-booking", "/search/label/change-signIn", "/search/label/change-order"};
		for(int i=1;i<=7;i++) {
			if($(".left", i).text().trim().equals(tableDataCheck[i-1])) {
				System.out.println(" *** pageName page list check(" + i + ") Success !! *** ");
			} else {
				System.out.println(" *** pageName page list check(" + i + ") Fail ... !@#$%^&*() *** ");
				System.out.println($(".left", i).text().trim());
				close();
			}
		}
		System.out.println(" *** pageName pageList check Success !! *** ");
	    $(By.name("list_type")).selectOption("미설정 리스트");
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** pageName not set list empty check Success !! *** ");
		} else {
			System.out.println(" *** pageName not set list empty check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
	    $(By.name("list_type")).selectOption("설정 리스트");
		$(".left", 2).waitUntil(visible, 10000);
		for(int i=1;i<=7;i++) {
			if($(".left", i).text().trim().equals(tableDataCheck[i-1])) {
				System.out.println(" *** pageName set list check(" + i + ") Success !! *** ");
			} else {
				System.out.println(" *** pageName set list check(" + i + ") Fail ... !@#$%^&*() *** ");
				System.out.println($(".left", i).text().trim());
				close();
			}
		}
		System.out.println(" *** pageName setting list check Success !! *** ");
	    $(By.name("list_type")).selectOption("삭제 리스트");
		$(".empty_td").waitUntil(visible, 10000);
	    $(".input_txt").setValue("");
		$(".btn_blue").click();
		if($(".empty_td").text().trim().substring(0, 14).equals("삭제된 페이지가 없습니다.")) {
			System.out.println(" *** pageName setting delete list check Success !! *** ");
		} else {
			System.out.println(" *** pageName setting delete list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim().substring(0, 14));
			close();
		}
		System.out.println(" ! ----- pageName End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void dynamicPage() {
		System.out.println(" ! ----- dynamicPage Start ----- ! ");
		$(".manager_s", 2).click();
		if($("h2").text().trim().equals("페이지 ▶ 동적페이지")) {
			System.out.println(" *** dynamicPage pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** dynamicPage pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("search")) {
			System.out.println(" *** dynamicPage list data check Success !! *** ");
		} else {
			System.out.println(" *** dynamicPage list data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt").setValue("test"); 
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000); 
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) { 
			System.out.println(" *** dynamicPage list empty-search check Success !! *** ");
		} else {
			System.out.println(" *** dynamicPage list empty-search check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".empty_td").text().trim()); close(); 
		}
		System.out.println(" ! ----- dynamicPage End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void menu() {
		System.out.println(" ! ----- menu Start ----- ! ");
		$(".manager_s", 3).click();
		if($("h2").text().trim().equals("페이지 ▶ 메뉴")) {
			System.out.println(" *** menu pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** menu pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		$("#newmenu_1").setValue("메뉴추가테스트");
		$(".btn_purple", 2).click();
		alertCheck("manageMenu add msg", "메뉴가 등록되었습니다.");
		$("#subtitle_66mU64m07LaU6rCA7YWM7Iqk7Yq4").waitUntil(visible, 10000);
		$("#subtitle_66mU64m07LaU6rCA7YWM7Iqk7Yq4").click();
		$(".blueB > img").waitUntil(visible, 10000);
		$(".blueB > img").click();
		alertCheck("manageMenu del confirm msg", "메뉴를 삭제하시겠습니까?\n" + "메뉴를 삭제하면, 메뉴에 대한 분석이 중지됩니다.");
		alertCheck("manageMenu del msg", "메뉴가 삭제되었습니다.");
		$("#subtitle_66mU64m07LaU6rCA7YWM7Iqk7Yq4").waitUntil(hidden, 10000);		
		if($("span", 243).text().trim().equals("전환테스트")) {
			System.out.println(" *** manageMenu delete menu check Success !! *** ");
		} else {
			System.out.println(" *** manageMenu delete menu check Fail ... !@#$%^&*() *** ");
		}
		$("#subtitle_7KCE7ZmY7YWM7Iqk7Yq4").click();
		$(".blueB").waitUntil(visible, 10000);
		$("#tab_menu2").click();
		$(".input_txt", 0).waitUntil(visible, 10000);
		String[] tableDataCheck = {"/search/label/change-signIn", "/search/label/change-request", "/search/label/change-other3", "/search/label/change-other2"
				, "/search/label/change-other1", "/search/label/change-order", "/search/label/change-booking"};
		for(int i=1,x=0;i<=13;i=i+2) {
			if($(".left", i).text().trim().equals(tableDataCheck[x])) {
				System.out.println(" *** pageManage list data check(" + x + ") Success !! *** ");
			} else {
				System.out.println(" *** pageManage list data check(" + x + ") Fail ... !@#$%^&*() *** ");
				System.out.println($(".left", i).text().trim());
				close();
			}
			x++;
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue").click();
		$(".empty_td", 0).waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) { 
			System.out.println(" *** pageManage list empty-search check Success !! *** ");
		} else {
			System.out.println(" *** pageManage list empty-search check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".empty_td").text().trim()); close(); 
		}
		$(".input_txt", 0).setValue("");
		$(".btn_blue").click();
		$(".empty_td", 0).waitUntil(hidden, 10000);
		$("#tab_menu3").click();
		$(".input_txt", 0).waitUntil(hidden, 10000);
		if($(".left", 15).text().trim().equals("전환테스트")) { 
			System.out.println(" *** addPattern pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** addPattern pageLoad check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".left", 1).text().trim()); close(); 
		}
		$("#pattern_1").setValue("/test/*");
		$(".btn_purple", 4).click();
		alertCheck("addPattern msg", "패턴이 등록되었습니다.\n"+ "등록된 패턴은 패턴관리에서 확인하실 수 있습니다.");
		sleep(500);
		$("#tab_menu4").click();
		$(".input_txt", 2).waitUntil(visible, 10000);
		if($(".left", 20).text().trim().equals("/test/*")) { 
			System.out.println(" *** patternManage list data check Success !! *** ");
		} else {
			System.out.println(" *** patternManage list data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 20).text().trim());

			close(); 
		}
		$(".input_txt", 2).setValue("/test/123");
		$(".btn_blue", 1).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) { 
			System.out.println(" *** patternManage empty-search check Success !! *** ");
		} else {
			System.out.println(" *** patternManage empty-search check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".empty_td").text().trim()); close(); 
		}
		$(".input_txt", 2).setValue("");
		$(".btn_blue", 1).click();
		$("th", 10).waitUntil(visible, 10000);
		$("th", 10).click();
		$(".btn_purple", 5).click();
		alertCheck("patternManage del confirm msg", "선택한 패턴을 삭제하시겠습니까?\n" + "(이미 메뉴에 등록된 페이지는 유지됩니다.)");
		alertCheck("patternManage del msg", "삭제되었습니다.");
		if($(".empty_td").text().trim().substring(0, 13).equals("등록된 패턴이 없습니다.")) { 
			System.out.println(" *** patternManage delete check Success !! *** ");
		} else {
			System.out.println(" *** patternManage delete check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".empty_td").text().trim().substring(0, 13)); close(); 
		}
		System.out.println(" ! ----- menu End ----- ! ");
  	}
    @Test(priority = 6)
  	public void linkClick() {
		System.out.println(" ! ----- linkClick Start ----- ! ");
		$(".manager_s", 4).click();
		if($("h2").text().trim().equals("페이지 ▶ 링크클릭")) {
			System.out.println(" *** linkClick pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** linkClick pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 2).text().trim().equals("/index.html")) {
			System.out.println(" *** linkClick list search check Success !! *** ");
		} else {
			System.out.println(" *** linkClick list search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt").setValue("test"); 
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000); 
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) { 
			System.out.println(" *** linkClick list empty-search check Success !! *** ");
		} else {
			System.out.println(" *** linkClick list empty-search check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".empty_td").text().trim()); close(); 
		}
		System.out.println(" ! ----- linkClick End ----- ! ");
  	}
    @Test(priority = 7)
  	public void inputForm() {
		System.out.println(" ! ----- inputForm Start ----- ! ");
		$(".manager_s", 5).click();
		if($("h2").text().trim().equals("페이지 ▶ 폼입력")) {
			System.out.println(" *** inputForm pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** inputForm pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 2).text().trim().equals("/index.html")) {
			System.out.println(" *** inputForm list search check Success !! *** ");
		} else {
			System.out.println(" *** inputForm list search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".input_txt").setValue("test"); 
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000); 
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) { 
			System.out.println(" *** inputForm list empty-search check Success !! *** ");
		} else {
			System.out.println(" *** inputForm list empty-search check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".empty_td").text().trim()); close(); 
		}
		System.out.println(" ! ----- inputForm End ----- ! ");
  	}
    @Test(priority = 8)
  	public void doubtInflowDiagnosis() {
		System.out.println(" ! ----- doubtInflowDiagnosis Start ----- ! ");
		$(".manager_s", 6).click();
		if($("h2").text().trim().equals("검색광고 모니터링 ▶ 의심유입 진단")) {
			System.out.println(" *** doubtInflowDiagnosis pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** doubtInflowDiagnosis pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".blue", 0).text().trim().equals("10분 이내 3회 이상")) {
			System.out.println(" *** doubtInflowDiagnosis default check Success !! *** ");
		} else {
			System.out.println(" *** doubtInflowDiagnosis default check Fail ... !@#$%^&*() *** ");
			System.out.println($(".blue", 0).text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("h2", 2).waitUntil(visible, 10000);
		$(".textred", 0).setValue("360");
		$(".textred", 1).selectOption("10");
		$(".btn_purple", 1).click();
		alertCheck("doubtInflowDiagnosis modify msg", "의심유입 진단기준이 수정되었습니다.");
		$("h2", 2).waitUntil(hidden, 10000);		
		if($(".blue", 0).text().trim().equals("360분 이내 10회 이상")) {
			System.out.println(" *** doubtInflowDiagnosis reset check Success !! *** ");
		} else {
			System.out.println(" *** doubtInflowDiagnosis reset check Fail ... !@#$%^&*() *** ");
			System.out.println($(".blue", 0).text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("h2", 2).waitUntil(visible, 10000);
		$(".textred", 0).setValue("10");
		$(".textred", 1).selectOption("3");
		$(".btn_purple", 1).click();
		alertCheck("doubtInflowDiagnosis restoration msg", "의심유입 진단기준이 수정되었습니다.");
		$("h2", 2).waitUntil(hidden, 10000);		
		if($(".blue", 0).text().trim().equals("10분 이내 3회 이상")) {
			System.out.println(" *** doubtInflowDiagnosis reset set check Success !! *** ");
		} else {
			System.out.println(" *** doubtInflowDiagnosis reset set check Fail ... !@#$%^&*() *** ");
			System.out.println($(".blue", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- doubtInflowDiagnosis End ----- ! ");
  	}
    @Test(priority = 9)
  	public void inflowNotification() {
		System.out.println(" ! ----- inflowNotification Start ----- ! ");
		$(".manager_s", 7).click();
		if($("h2").text().trim().equals("검색광고 모니터링 ▶ 유입수 증감알림")) {
			System.out.println(" *** inflowNotification pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().substring(0, 14).equals("등록된 검색어가 없습니다.")) {
			System.out.println(" *** inflowNotification empty-data check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple").click();
		$(".input_txt").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("최근 30일 동안 유입이 있었던 검색어(입찰키워드)가 없습니다.")) {
			System.out.println(" *** inflowNotification empty-keyword-data check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification empty-keyword-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".input_txt").setValue("test");		
		$(".btn_blue").click();
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** inflowNotification empty-search data check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification empty-search data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		System.out.println(" ! ----- inflowNotification End ----- ! ");
  	}
    @Test(priority = 10)
  	public void searchAdv() {
		System.out.println(" ! ----- searchAdv Start ----- ! ");
		$(".manager_s", 8).click();
		if($("h2").text().trim().equals("캠페인 ▶ 검색광고")) {
			System.out.println(" *** searchAdv pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 2).text().trim().equals("apzz092888")) {
			System.out.println(" *** searchAdv list check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".input_txt").setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** searchAdv empty-search check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("#keyword_1").waitUntil(visible, 10000);
		$(".btn_purple", 0).click();
		alertCheck("searchAdv product msg", "광고상품을 선택하세요.");
		$(By.name("ad_type")).selectOption("네오클릭");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv keyword msg", "검색어를 입력하세요.");
		$("#keyword_1").setValue(id_date + " 검색어^^");
		$(".btn_purple", 0).click();		
		alertCheck("searchAdv keyword valCheck msg", "검색어의 특수문자를 확인해주세요.\n" + "사용 가능한 특수문자:  ( !  % & ( ) + - _ = : . / ? ~ # )");
		$("#keyword_1").setValue(id_date + " 검색어");
		$("#s_url_1").setValue("");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv URL msg", "연결URL을 입력하세요.");
		$("#s_url_1").setValue(id_date + " 연결URL");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv URL valCheck msg", "연결 URL의 특수문자를 확인해 주세요.\n" + "사용 가능한 특수문자:   - ( ) % # ~ ? & = _ . :");
		$("#s_url_1").setValue(id_date + "연결URL");
		$(".btn_purple", 0).click();
		$(".btn_white", 3).waitUntil(visible, 10000);		
		if($(".left", 2).text().trim().equals(id_date + " 검색어")) {
			System.out.println(" *** searchAdv add check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("ad_url_1")).waitUntil(visible, 10000);
		$(By.name("ad_url_1")).setValue(id_date + " 검색광고 URL");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv modify msg", "연결URL이 수정되었습니다.\n" + "코드보기를 클릭해 광고코드를 확인하세요.");
		$(By.name("ad_url_1")).waitUntil(hidden, 10000);		
		if($(".left", 2).text().trim().equals(id_date + " 검색어")) {
			System.out.println(" *** searchAdv modify check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_white", 7).click();
		alertCheck("searchAdv del null msg", "삭제할 검색광고를 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 7).click();
		alertCheck("searchAdv del confirm msg", "검색광고를 삭제하시겠습니까?\n" + "삭제된 검색광고는 분석통계에서 kw 변수값으로 표기됩니다.");
		alertCheck("searchAdv del msg", "검색광고가 삭제되었습니다.");
		$(".input_txt").waitUntil(visible, 10000);
		if($(".left", 2).text().trim().equals("apzz092888")) {
			System.out.println(" *** searchAdv delete check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		System.out.println(" ! ----- searchAdv End ----- ! ");
  	}
    @Test(priority = 11)
  	public void bannerAdv() {
		System.out.println(" ! ----- bannerAdv Start ----- ! ");
		$(".manager_s", 9).click();
		if($("h2").text().trim().equals("캠페인 ▶ 배너광고")) {
			System.out.println(" *** bannerAdv pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("배너광고")) {
			System.out.println(" *** bannerAdv list check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** bannerAdv empty-data check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("#promo_name").waitUntil(visible, 10000);
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv promoName null msg", "프로모션명을 입력하세요.");
		$("#promo_name").setValue(id_date + " 프로모션명");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv name null msg", "배너광고명을 입력하세요.");
		$("#banner_name_1").setValue(id_date + " 배너광고명");		
		$("#banner_linkurl_1").setValue("");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv URL null msg", "연결URL을 입력하세요.");
		$("#banner_linkurl_1").setValue(id_date + " 연결URL");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv URL valCheck msg", "연결URL에 특수문자는\n" + "사용하실 수 없습니다.");
		$("#banner_linkurl_1").setValue(id_date + "연결URL");		
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv add msg", "프로모션에 1개의 배너광고가 등록되었습니다.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 프로모션명")) {
			System.out.println(" *** bannerAdv add check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$("#promo_name").waitUntil(visible, 10000);
		$("#banner_name_1").setValue(id_date + " 배너광고명 수정");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv modify msg", "프로모션이 수정되었습니다.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		$("#img_1").click();
		$(".left", 2).waitUntil(visible, 10000);
		if($(".left", 2).text().trim().equals(id_date + " 배너광고명 수정")) {
			System.out.println(" *** bannerAdv modify check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_white", 10).click();
		alertCheck("bannerAdv del null msg", "삭제할 프로모션을 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 10).click();
		alertCheck("bannerAdv del confirm msg", "프로모션을 삭제하시겠습니까?\n" + "등록된 배너광고가 모두 삭제되며,\n" + "삭제된 배너광고는 분석통계에서 kw 변수값으로 표기됩니다.");
		alertCheck("bannerAdv del msg", "프로모션과 배너광고가 모두 삭제되었습니다.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("배너광고")) {
			System.out.println(" *** bannerAdv delete check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- bannerAdv End ----- ! ");
  	}
    @Test(priority = 12)
  	public void emailMkt() {
		System.out.println(" ! ----- emailMkt Start ----- ! ");
		$(".manager_s", 10).click();
		if($("h2").text().trim().equals("캠페인 ▶ 이메일마케팅")) {
			System.out.println(" *** emailMkt pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("이메일마케팅")) {
			System.out.println(" *** emailMkt list check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** emailMkt empty-data check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(By.name("input_name")).waitUntil(visible, 10000);
		$(".btn_purple", 1).click();
		alertCheck("emailMkt name null msg", "이메일명을 입력하세요.");
		$(By.name("input_name")).setValue(id_date + " 이메일명");		
		$(By.name("lurl")).setValue("");		
		$(".btn_purple", 1).click();
		alertCheck("emailMkt URL null msg", "연결URL을 입력하세요.");
		$(By.name("lurl")).setValue(id_date + " URL.com");
		$(".btn_purple", 1).click();
		alertCheck("emailMkt URL valCheck msg", "연결URL에 특수문자는\n" + "사용하실 수 없습니다.");
		$(By.name("lurl")).setValue(id_date + "URL.com");
		$(".btn_purple", 1).click();
		alertCheck("emailMkt add msg", "이메일마케팅이 등록되었습니다.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 이메일명")) {
			System.out.println(" *** emailMkt add check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("lurl")).waitUntil(visible, 10000);
		$(By.name("lurl")).setValue(id_date + "URL수정.com");
		$(".btn_purple", 1).click();
		alertCheck("emailMkt modify msg", "이메일마케팅이 수정되었습니다.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		$("#url_txt_0").waitUntil(visible, 10000);
		if($("#url_txt_0").text().trim().substring(7, 30).equals(id_date + "URL수정.com")) {
			System.out.println(" *** emailMkt modify check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt modify check Fail ... !@#$%^&*() *** ");
			System.out.println($("#url_txt_0").text().trim().substring(7, 30));
			close();
		}
		sleep(500);
		$(".btn_white", 3).click();
		alertCheck("emailMkt del null msg", "삭제할 이메일마케팅을 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 3).click();
		alertCheck("emailMkt del confirm msg", "이메일마케팅을 삭제하시겠습니까?\n" + "삭제된 이메일마케팅은 분석통계에서 kw 변수값으로 표기됩니다.");
		alertCheck("emailMkt del msg", "이메일마케팅이 삭제되었습니다.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("이메일마케팅")) {
			System.out.println(" *** emailMkt delete check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- emailMkt End ----- ! ");
  	}
  	@Test(priority = 13)
  	public void viralMkt() {
		System.out.println(" ! ----- viralMkt Start ----- ! ");
		$(".manager_s", 11).click();
		if($("h2").text().trim().equals("캠페인 ▶ 바이럴마케팅")) {
			System.out.println(" *** viralMkt pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("바이럴마케팅")) {
			System.out.println(" *** viralMkt list check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** viralMkt empty-data check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("viralMkt name null msg", "콘텐츠 제목을 입력하세요.");
		$(".input_grey", 0).setValue(id_date + " 바이럴마케팅명");
		$(".btn_blue", 1).click();
		alertCheck("viralMkt URL null msg", "콘텐츠가 등록될 블로그, 카페의 주소를 입력하세요.");
		$(".input_grey", 1).setValue(id_date + " 바이럴마케팅URL");
		$(".btn_blue", 1).click();
		alertCheck("viralMkt URL valCheck msg", "콘텐츠URL에 특수문자는\n" + "사용하실 수 없습니다.");
		$(".input_grey", 1).setValue(id_date + "바이럴마케팅URL");
		$(".btn_blue", 1).click();
		alertCheck("viralMkt add msg", "바이럴마케팅이 등록되었습니다.");
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".btn_white", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 바이럴마케팅명")) {
			System.out.println(" *** viralMkt add check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("content_name_0")).waitUntil(visible, 10000);
		$(By.name("content_name_0")).setValue(id_date + " 바이럴마케팅명 수정");
		$(".btn_purple", 0).click();
		alertCheck("viralMkt modify msg", "바이럴마케팅이 수정되었습니다.");
		$(By.name("content_name_0")).waitUntil(hidden, 10000);		
		if($(".left", 1).text().trim().equals(id_date + " 바이럴마케팅명 수정")) {
			System.out.println(" *** viralMkt modify check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 5).click();
		alertCheck("viralMkt del null msg", "삭제할 바이럴마케팅을 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 5).click();
		alertCheck("viralMkt del confirm msg", "바이럴마케팅을 삭제하시겠습니까?\n" + "삭제된 바이럴마케팅은 분석통계에서 kw 변수값으로 표기됩니다.");
		alertCheck("viralMkt del msg", "바이럴마케팅이 삭제되었습니다.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("바이럴마케팅")) {
			System.out.println(" *** viralMkt delete check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- viralMkt End ----- ! ");
  	}
  	@Test(priority = 14)
  	public void QRcode() {
		System.out.println(" ! ----- QRcode Start ----- ! ");
		$(".manager_s", 12).click();
		if($("h2").text().trim().equals("캠페인 ▶ QR코드")) {
			System.out.println(" *** QRcode pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** QRcode pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("QR코드")) {
			System.out.println(" *** QRcode list check Success !! *** ");
		} else {
			System.out.println(" *** QRcode list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** QRcode empty-data check Success !! *** ");
		} else {
			System.out.println(" *** QRcode empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("QRcode name null msg", "QR코드명을 입력하세요.");
		$(".input_grey", 0).setValue(id_date + " QR코드명");
		$(".input_adress_txt").setValue("");		
		$(".btn_blue", 1).click();
		alertCheck("QRcode URL null msg", "연결URL을 입력하세요.");		
		$(".input_adress_txt").setValue(id_date + " QR코드URL");
		$(".btn_blue", 1).click();
		alertCheck("QRcode URL valCheck msg", "연결URL에 특수문자는\n" + "사용하실 수 없습니다.");	
		$(".input_adress_txt").setValue(id_date + "QR코드URL");
		$(".btn_blue", 1).click();
		alertCheck("QRcode add msg", "QR코드가 등록되었습니다.");		
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".btn_white", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " QR코드명")) {
			System.out.println(" *** QRcode add check Success !! *** ");
		} else {
			System.out.println(" *** QRcode add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$("#CodeName_1").waitUntil(visible, 10000);
		$("#CodeName_1").setValue(id_date + " QR코드명 수정");
		$(".btn_purple", 0).click();
		alertCheck("QRcode modify msg", "QR코드명이 수정되었습니다.");
		$("#CodeName_1").waitUntil(hidden, 10000);		
		if($(".left", 1).text().trim().equals(id_date + " QR코드명 수정")) {
			System.out.println(" *** QRcode modify check Success !! *** ");
		} else {
			System.out.println(" *** QRcode modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 11).click();
		alertCheck("QRcode del null msg", "삭제할 QR코드를 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 11).click();
		alertCheck("QRcode del confirm msg", "QR코드를 삭제하시겠습니까?\n" + "삭제된 QR코드는 분석통계에서 kw 변수값으로 표기됩니다.");
		alertCheck("QRcode del msg", "QR코드가 삭제되었습니다.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("QR코드")) {
			System.out.println(" *** QRcode delete check Success !! *** ");
		} else {
			System.out.println(" *** QRcode delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- QRcode End ----- ! ");
  	}
  	@Test(priority = 15)
  	public void koreanInternetAddress() {
		System.out.println(" ! ----- koreanInternetAddress Start ----- ! ");
		$(".manager_s", 13).click();
		if($("h2").text().trim().equals("캠페인 ▶ 한글인터넷주소")) {
			System.out.println(" *** koreanInternetAddress pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("한글인터넷주소")) {
			System.out.println(" *** koreanInternetAddress list check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** koreanInternetAddress empty-data check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress null msg", "한글인터넷주소를 입력하세요.");
		$(By.name("i_ad_keyword")).setValue(id_date + "한글인터넷주소");
		$(By.name("i_ad_url")).setValue("");
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress URL null msg", "연결URL을 입력하세요.");
		$(By.name("i_ad_url")).setValue(id_date + " 한글인터넷주소URL");
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress URL valCheck msg", "연결URL에 특수문자는\n" + "사용하실 수 없습니다.");
		$(By.name("i_ad_url")).setValue(id_date + "한글인터넷주소URL");
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress add msg", "한글인터넷주소가 등록되었습니다.");
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".btn_white", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + "한글인터넷주소")) {
			System.out.println(" *** koreanInternetAddress add check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("ad_keyword_0")).waitUntil(visible, 10000);
		$(By.name("ad_keyword_0")).setValue(id_date + "한글인터넷주소수정");
		$(".btn_purple", 0).click();
		alertCheck("koreanInternetAddress modify msg", "한글인터넷주소가 수정되었습니다.");
		$(".btn_purple", 0).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals(id_date + "한글인터넷주소수정")) {
			System.out.println(" *** koreanInternetAddress modify check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 5).click();
		alertCheck("koreanInternetAddress del null msg", "삭제할 한글인터넷주소를 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 5).click();
		alertCheck("koreanInternetAddress del confirm msg", "한글인터넷주소를 삭제하시겠습니까?\n" + "삭제된 한글인터넷주소는 분석통계에서 kw 변수값으로 표기됩니다.");
		alertCheck("koreanInternetAddress del msg", "한글인터넷주소가 삭제되었습니다.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("한글인터넷주소")) {
			System.out.println(" *** koreanInternetAddress delete check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- koreanInternetAddress End ----- ! ");
  	}
  	@Test(priority = 16)
  	public void campaignGroup() {
		System.out.println(" ! ----- campaignGroup Start ----- ! ");
		$(".manager_s", 14).click();
		if($("h2").text().trim().equals("캠페인 ▶ 캠페인 그룹")) {
			System.out.println(" *** campaignGroup pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("캠페인 그룹명")) {
			System.out.println(" *** campaignGroup list check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_purple", 1).click();
		$("#chkidx_8").waitUntil(visible, 10000);
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup name null msg", "캠페인그룹명을 입력하세요.");
		$("#camp_name").setValue(id_date + " 캠페인 그룹명");
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup null msg", "캠페인을 선택하세요.");
		$("#chkidx_8").click();
		$(By.name("promo_select")).selectOption("배너광고");
		$("#chkidx_1").waitUntil(visible, 10000);
		$("#chkidx_1").click();
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup add msg", "캠페인그룹이 등록되었습니다.");
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 캠페인 그룹명")) {
			System.out.println(" *** campaignGroup add check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("#camp_name").waitUntil(visible, 10000);
		$("#camp_name").setValue(id_date + " 캠페인 그룹명 수정");
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup modify msg", "캠페인그룹이 수정되었습니다.");	
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 캠페인 그룹명 수정")) {
			System.out.println(" *** campaignGroup modify check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 0).click();
		alertCheck("campaignGroup del confirm msg", "캠페인그룹을 삭제하시겠습니까?");
		alertCheck("campaignGroup del confirm msg", "캠페인그룹이 삭제되었습니다.");
		$(".left", 4).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("캠페인 그룹명")) {
			System.out.println(" *** campaignGroup delete check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- campaignGroup End ----- ! ");
  	}
  	@Test(priority = 17)
  	public void scenario() {
		System.out.println(" ! ----- scenario Start ----- ! ");
		$(".manager_s", 15).click();
		if($("h2").text().trim().equals("콘텐츠 ▶ 시나리오")) {
			System.out.println(" *** scenario pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** scenario pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("시나리오명")) {
			System.out.println(" *** scenario list check Success !! *** ");
		} else {
			System.out.println(" *** scenario list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** scenario empty-data check Success !! *** ");
		} else {
			System.out.println(" *** scenario empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_purple", 7).waitUntil(visible, 10000);
		$(".btn_purple", 7).click();
		alertCheck("scenario name null msg", "시나리오명을 입력하세요.");
		$(".input_grey", 0).setValue("시나리오명");
		$(".btn_purple", 7).click();
		alertCheck("scenario page null msg", "찾기를 클릭해 페이지를 선택하세요. ");
		$(".btn_white", 0).click();
		$(".js-checkbox").waitUntil(visible, 10000);
		$("#img_0").click();
		$(".left", 15).waitUntil(visible, 10000);
		String[] scenario = {"/search/label/change-order", "/search/label/change-booking", "/search/label/change-request", "/search/label/change-other3"
				, "/search/label/change-other2", "/search/label/change-other1", "/search/label/change-signIn"};
		for(int i=3, x=0;i<=15;i=i+2, x++) {
			if($(".left", i).text().trim().equals(scenario[x])) {
				System.out.println(" *** scenario pageURL[" + (x+1) + "] check Success !! *** ");
			} else {
				System.out.println(" *** scenario pageURL[" + (x+1) + "] check Fail ... !@#$%^&*() *** ");
				System.out.println($(".left", i).text().trim());
				close();
			}
		}
		$(".btn_white", 1).click();
		$(By.name("scen_name_0")).waitUntil(visible, 10000);
		$(By.name("scen_name_0")).setValue("시나리오명 수정");
		$(".btn_purple", 0).click();
		alertCheck("scenario modify msg", "시나리오가 수정되었습니다.");
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("시나리오명 수정")) {
			System.out.println(" *** scenario modify check Success !! *** ");
		} else {
			System.out.println(" *** scenario modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("scen_name_0")).waitUntil(visible, 10000);
		$(By.name("scen_name_0")).setValue("시나리오명");
		$(".btn_purple", 0).click();
		alertCheck("scenario restoration msg", "시나리오가 수정되었습니다.");
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("시나리오명")) {
			System.out.println(" *** scenario restoration check Success !! *** ");
		} else {
			System.out.println(" *** scenario restoration check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- scenario End ----- ! ");
  	}
  	@Test(priority = 18)
  	public void innerbanner() {
		System.out.println(" ! ----- innerbanner Start ----- ! ");
		$(".manager_s", 16).click();
		if($("h2").text().trim().equals("콘텐츠 ▶ 내부배너")) {
			System.out.println(" *** innerbanner pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("내부배너2")) {
			System.out.println(" *** innerbanner list check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** innerbanner empty-data check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_purple", 1).waitUntil(visible, 10000);
		$(".btn_purple", 1).click();
		alertCheck("innerbanner promoName null msg", "프로모션명을 입력하세요.");
		$("#promo_name").setValue(id_date + " 내부배너 프로모션명");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner name null msg", "내부배너명을 입력하세요.");
		$("#banner_name_1").setValue(id_date + " 내부배너명");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner filePath null msg", "파일경로를 입력하세요.");
		$("#banner_imgurl_1").setValue("/" + id_date);
		$(".btn_purple", 1).click();
		alertCheck("innerbanner URL null msg", "연결URL을 입력하세요.");
		$("#banner_linkurl_1").setValue(id_date + " 연결URL");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner URL valCheck msg", "연결URL에 특수문자는\n" + "사용하실 수 없습니다.");
		$("#banner_linkurl_1").setValue(id_date + "연결URL");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner add msg", "내부배너 프로모션이 등록되었습니다.");		
		$(".left", 3).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 내부배너 프로모션명")) {
			System.out.println(" *** innerbanner list check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$("#promo_name").setValue(id_date + " 내부배너 프로모션명 수정");		
		$("#banner_name_1").setValue(id_date + " 내부배너명 수정");
		$("#banner_imgurl_1").setValue("/" + id_date + "수정");		
		$("#banner_linkurl_1").setValue(id_date + "연결URL수정");		
		$(".btn_purple", 1).click();
		alertCheck("innerbanner modify msg", "내부배너 프로모션이 수정되었습니다.");
		$("#img_0").waitUntil(visible, 10000);
		$("#img_0").click();
		$(".left", 2).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " 내부배너 프로모션명 수정")) {
			System.out.println(" *** innerbanner modify promotion name check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner modify promotion name check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		if($(".left", 2).text().trim().equals(id_date + " 내부배너명 수정")) {
			System.out.println(" *** innerbanner modify innerbanner name check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner modify innerbanner name check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 9).click();
		alertCheck("innerbanner del null msg", "삭제할 프로모션을 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();		
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 9).click();
		alertCheck("innerbanner del confirm msg", "프로모션을 삭제하시겠습니까?\n" + "등록된 내부배너도 함께 삭제됩니다.\n\n" 
		+ "웹사이트에 적용된 내부배너 삽입코드도 삭제해주시기 바랍니다.\n" + "(미삭제시 노출수가 측정되어 추가요금이 발생할 수 있습니다.)");
		alertCheck("innerbanner del msg", "프로모션과 내부배너가 모두 삭제되었습니다.");
		$(".left", 2).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("내부배너2")) {
			System.out.println(" *** innerbanner modify promotion name check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner modify promotion name check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- innerbanner End ----- ! ");
  	}
  	@Test(priority = 19)
  	public void fileDownload() {
		System.out.println(" ! ----- fileDownload Start ----- ! ");
		$(".manager_s", 17).click();
		if($("h2").text().trim().equals("콘텐츠 ▶ 파일다운로드")) {
			System.out.println(" *** fileDownload pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** fileDownload pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("*.*")) {
			System.out.println(" *** fileDownload list check Success !! *** ");
		} else {
			System.out.println(" *** fileDownload list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("해당되는 검색결과가 없습니다.")) {
			System.out.println(" *** fileDownload empty-data check Success !! *** ");
		} else {
			System.out.println(" *** fileDownload empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("fileDownload null msg", "다운로드 패턴을 입력하세요.");
		$(By.name("f_download_pattern")).setValue(id_date + ".*");
		$(".btn_blue", 1).click();
		alertCheck("fileDownload add msg", "다운로드 패턴이 등록되었습니다.");
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".left", 2).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + ".*")) {
			System.out.println(" *** fileDownload add check Success !! *** ");
		} else {
			System.out.println(" *** fileDownload add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		sleep(500);
		$(".btn_white", 1).click();
		alertCheck("fileDownload del null msg", "삭제할 다운로드 패턴을 선택하세요.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 1).click();
		alertCheck("fileDownload del confirm msg", "다운로드 패턴을 삭제하시겠습니까?\n" + "\n" + "다운로드 패턴을 삭제하면, 콘텐츠의 파일다운로드\n" + "분석이 중지되며, 삭제 후 복구가 불가능합니다.");
		alertCheck("fileDownload del msg", "다운로드 패턴이 삭제되었습니다.");
		$(".left", 2).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("*.*")) {
			System.out.println(" *** fileDownload delete check Success !! *** ");
		} else {
			System.out.println(" *** fileDownload delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- fileDownload End ----- ! ");
  	}
  	@Test(priority = 20)
  	public void memberGroup() {
		System.out.println(" ! ----- memberGroup Start ----- ! ");
		$(".manager_b", 5).click();
		if($("h2").text().trim().equals("회원그룹")) {
			System.out.println(" *** memberGroup pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("회원 그룹명")) {
			System.out.println(" *** memberGroup list check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_purple", 1).click();
		$(".input_grey", 0).waitUntil(visible, 10000);
		$(".btn_purple", 0).click();
		alertCheck("memberGroup name null msg", "회원 그룹명을 입력하세요.");
		$(By.name("md_p_name")).setValue(id_date + " 회원 그룹명");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup var1 null msg", "회원 변수값을 입력하세요.");
		$(By.name("md_name_0")).setValue(id_date + "+A");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup var2 null msg", "회원 변수값을 입력하세요.");
		$(By.name("md_name_1")).setValue(id_date + "+B");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup add msg", "회원 그룹이 등록되었습니다.");
		$(".input_grey", 2).waitUntil(hidden, 10000);
		if($(".left", 2).text().trim().equals(id_date + " 회원 그룹명")) {
			System.out.println(" *** memberGroup add check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_purple", 1).click();
		$(By.name("md_p_name")).waitUntil(visible, 10000);
		$(By.name("md_p_name")).setValue(id_date + " 회원 그룹명 수정");		
		$(By.name("md_name_0")).setValue(id_date + "+A수정");
		$(By.name("md_name_1")).setValue(id_date + "+B수정");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup modify msg", "회원 그룹이 수정되었습니다.");
		$(By.name("md_name_1")).waitUntil(hidden, 10000);	
		if($(".left", 2).text().trim().equals(id_date + " 회원 그룹명 수정")) {
			System.out.println(" *** memberGroup modify check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		alertCheck("memberGroup del confirm msg", "회원그룹을 삭제하시겠습니까?");
		alertCheck("memberGroup del msg", "회원그룹이 삭제되었습니다.");
		$(".left", 2).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("회원 그룹명")) {
			System.out.println(" *** memberGroup delete check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup delete check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		System.out.println(" ! ----- memberGroup End ----- ! ");
  	}
  	@Test(priority = 21)
  	public void ProductOfInterest() {
		System.out.println(" ! ----- ProductOfInterest Start ----- ! ");
		$(".manager_s", 18).click();
		if($("h2").text().trim().equals("제품 ▶ 관심제품")) {
			System.out.println(" *** ProductOfInterest pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** ProductOfInterest pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().equals("등록된 관심제품이 없습니다.\n" + "추가를 클릭해 관심제품을 등록하세요.")) {
			System.out.println(" *** ProductOfInterest empty-data check Success !! *** ");
		} else {
			System.out.println(" *** ProductOfInterest empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		System.out.println(" ! ----- ProductOfInterest End ----- ! ");
  	}
  	@Test(priority = 22)
  	public void productPrice() {
		System.out.println(" ! ----- productPrice Start ----- ! ");
		$(".manager_s", 19).click();
		if($("h2").text().trim().equals("제품 ▶ 제품가격대")) {
			System.out.println(" *** productPrice pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** productPrice pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().equals("등록된 제품가격대가 없습니다.\n" + "신규등록을 클릭해 제품가격대를 등록하세요.")) {
			System.out.println(" *** productPrice empty-data check Success !! *** ");
		} else {
			System.out.println(" *** productPrice empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		System.out.println(" ! ----- productPrice End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}