package aceCounter;

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

public class demoAmDighty {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, pw1, domain, checkMsg, temp_pw, A, B, C, pageLoadCheck;
    
	Date date = new Date(); //web 회원가입과 app 회원가입의 도메인을 동일하게 하기위해 공통선언
    SimpleDateFormat number_format = new SimpleDateFormat("YYYYMMddHHmmss");
    String am_date = number_format.format(date);
    String message = "";
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "http://demo-am.dighty.com/login";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "aceadmin";
		pw = "ace!@#123";

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
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}
  	public static void sleep(long millis) {
  		try {
  			Thread.sleep(millis);
  		} catch (InterruptedException ex) {
  		}
  	}
  	@SuppressWarnings("unused")
	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}
  	@Test(priority = 0)
  	public void combineSegment() {
  		System.out.println(" ! ----- combineSegment test Start ----- ! ");
		open(baseUrl);
		$("#username").setValue(id);
		$("#password").setValue(pw);		
		$(".btn_login_block").click();
		System.out.println("로그인");
		$(".nav-link", 1).waitUntil(visible, 10000);
		$(".nav-link", 1).click();
		$(".ico_check", 0).waitUntil(visible, 10000);
		System.out.println("세그먼트 사용 페이지 접근");
		$(".ico_check", 0).click();
		$(".ico_check", 1).click();
		$(".bg_green").click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		$(".close-btn", 0).click();
		$(".bg_green", 0).click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		$(".gray_line", 0).click();
		$(".bg_green").click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		$(".bg_gray", 0).click();
		$$("span").last().waitUntil(visible, 100000);
		if($$("span").last().text().trim().equals("세그먼트 이름을 입력해주세요.")) {
			System.out.println("세그먼트 조합 이름 미입력 체크");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("세그먼트 조합 이름 미입력 체크 실패");
	  		close();
		}
		$("#combineSegmentSubject").setValue(am_date + " 조합 테스트");
		$(".tit", 1).click();
		sleep(1000);
		$(".bg_gray", 0).click();
		$$("span").last().waitUntil(visible, 100000);
		if($$("span").last().text().trim().equals("조합 방식을 선택하세요.")) {
			System.out.println("세그먼트 조합 방식 미선택 체크");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("세그먼트 조합 방식 미선택 체크 실패");
	  		close();
		}
		$(".tit", 1).click();
		$(".btn", 0).click();
		for(int i=0;i<=10;i++) {
			if($(".info_txt_bx", 2).text().trim().equals("조합 방식을 선택하세요.") || $(".info_txt_bx", 2).text().trim().equals("데이터 처리 중입니다") ) {
				System.out.println("세그먼트 조합 모수 조회중 " + (i+1) + "초");
				System.out.println($(".info_txt_bx", 2).text().trim());
			} else {
				System.out.println("세그먼트 조합 모수 조회 완료");
				System.out.println($(".info_txt_bx", 2).text().trim());
				break;
			}
		}
		$(".bg_gray", 0).click();
		$(".bg_gray", 3).waitUntil(visible, 10000);
		sleep(500);
		$(".bg_gray", 3).click();
		$("span", 29).waitUntil(visible, 100000);
		if($("span", 29).text().trim().equals("추가되었습니다.")) {
			System.out.println("세그먼트 조합 등록 완료");
			$("span", 29).waitUntil(hidden, 100000);
		} else {
			System.out.println("세그먼트 조합 등록 완료 실패");
	  		close();
		}
		if($(".tree_link", 0).text().trim().equals(am_date + " 조합 테스트")) {
			System.out.println("세그먼트 조합 등록 확인 완료");
		} else {
			System.out.println("세그먼트 조합 등록 확인 실패");
	  		close();
		}
		$(".bg_green", 1).click();
		$(".bg_gray", 1).waitUntil(visible, 10000);
		$(".close-btn", 1).click();
		$(".bg_green", 1).click();
		$(".bg_gray", 1).waitUntil(visible, 10000);
		$(".gray_line", 1).click();
		$(".bg_green", 1).click();
		$(".bg_gray", 1).waitUntil(visible, 10000);
		$(".bg_gray", 1).click();
		$(".bg_gray", 4).waitUntil(visible, 10000);
		sleep(500);
		$(".bg_gray", 4).click();
		$("span", 29).waitUntil(visible, 100000);
		if($("span", 29).text().trim().equals("세그먼트 사용 설정이 완료되었습니다.")) {
			System.out.println("조합 세그먼트 사용 설정 완료");
			$("span", 29).waitUntil(hidden, 100000);
		} else {
			System.out.println("조합 세그먼트 사용 설정 실패");
	  		close();
		}
		if($("td", 2).text().trim().equals(am_date + " 조합 테스트")) {
			System.out.println("조합 세그먼트 사용 확인 완료");
		} else {
			System.out.println("조합 세그먼트 사용 확인 실패");
			close();
		}
		$(".nav-link", 1).click();
		$(".l_green", 0).waitUntil(visible, 10000);
		$(".l_green", 0).click();
		$(".bg_gray", 2).waitUntil(visible, 10000);
		$(".bg_gray", 2).click();
		$$("span").last().waitUntil(visible, 100000);
		if($$("span").last().text().trim().equals("확장 세그먼트 이름을 입력해주세요.")) {
			System.out.println("세그먼트 확장 이름 미입력 체크");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("세그먼트 확장 이름 미입력 체크 실패");
	  		close();
		}
		$("#expandSegmentSubject").setValue(am_date + " 조합 세그먼트 확장");
		$(".bg_gray", 2).click();
		$(".bg_gray", 3).waitUntil(visible, 10000);
		sleep(500);
		$(".bg_gray", 3).click();
		if($$("span").last().text().trim().equals("조합 세그먼트 확장 신청이 완료되었습니다.")) {
			System.out.println("조합 세그먼트 확장 신청 완료");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("조합 세그먼트 확장 신청 실패");
	  		close();
		}
		if($(".tree_link", 0).text().trim().equals(am_date + " 조합 테스트")) {
			System.out.println("조합 세그먼트 확장 신청 확인 완료");
		} else {
			System.out.println("조합 세그먼트 확장 신청 확인 실패");
	  		close();
		}
		$(".bg_green", 0).click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		$(".close-btn", 0).click();
		$(".bg_green", 0).click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		if($(".pop_tit", 0).text().trim().equals("확장 세그먼트 사용")) {
			System.out.println("확장 세그먼트 사용 레이어 확인 완료");			
		} else {
			System.out.println("확장 세그먼트 사용 레이어 확인 실패");
	  		close();
		}
		$(".gray_line", 0).click();
		$(".bg_gray", 0).waitUntil(hidden, 10000);
		$(".btn_line_sm2").click();
		$(".bg_gray", 1).waitUntil(hidden, 10000);		
		sleep(500);
		$(".bg_gray", 1).click();
		if($$("span").last().text().trim().equals("선택한 확장 세그먼트가 삭제되었습니다.")) {
			System.out.println("확장 세그먼트 삭제 완료");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("확장 세그먼트 삭제 실패");
	  		close();
		}
		if($(".no-data", 0).text().trim().equals("세그먼트 확장 신청 이력이 없습니다.")) {
			System.out.println("확장 세그먼트 삭제 확인 완료");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("확장 세그먼트 삭제 확인 실패");
	  		close();
		}
		sleep(1000000);
		System.out.println(" ! ----- combineSegmentr test End ----- ! ");
  	}
  	//@Test(priority = 1)
  	public void defaultSegment() {
  		System.out.println(" ! ----- defaultSegment test Start ----- ! ");
		$(".bg_green", 1).click();
		sleep(3500);
		$(".bg_gray", 1).click();
		sleep(500);
		$(".bg_gray", 3).click();
		System.out.println("기본 세그먼트 사용");
		sleep(5000);
		$(".nav-link", 1).click();
		$(".ico_check", 0).waitUntil(visible, 10000);
		$(".l_green", 0).click();
		sleep(500);
		$("#expandSegmentSubject").setValue(am_date + " 기본 세그먼트 확장");
		$(".bg_gray", 2).click();
		sleep(500);
		$(".bg_gray", 3).click();
		System.out.println("기본 확장 세그먼트 등록");
		sleep(5000);
		$(".btn_line_sm2", 1).click();
		sleep(500);
		$(".bg_gray", 1).click();
		sleep(500);
		System.out.println("기본 확장 세그먼트 삭제");
  		System.out.println(" ! ----- defaultSegment test End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}