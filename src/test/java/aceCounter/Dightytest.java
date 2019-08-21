package aceCounter;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

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

public class Dightytest {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, id, pw, am_name, combinationMethod, segmantParameter, C, alertCheck;
    
	Date date = new Date(); //web 회원가입과 app 회원가입의 도메인을 동일하게 하기위해 공통선언
    SimpleDateFormat number_format = new SimpleDateFormat("YYYYMMddHHmmss");
    String am_date = number_format.format(date);
    String message = "";
    
	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://demo-am.dighty.com/signin";
		hubUrl = "http://10.77.129.79:5555/wd/hub";
		id = "payco";
		pw = "password";

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
  	//@Test(priority = 0)
  	public void login() {
  		System.out.println(" ! ----- login Start ----- ! ");
		open(baseUrl);
		$(".inp", 0).setValue(id);
		$(".inp", 1).setValue(pw);
		$(".btn_point").click();
		System.out.println(" ! ----- login End ----- ! ");
  	}
  	//@Test(priority = 0)
  	public void demo() {
  		System.out.println(" ! ----- demo Start ----- ! ");
		open(baseUrl);
  		for(int i=101;i<=1000;i++) {
			$(".inp", 0).waitUntil(visible, 10000);
			$(".inp", 0).setValue("test" + i + "@mail.com");
			$(".inp", 1).setValue("qordlf12");
			$(".inp", 2).setValue("qordlf12");
			$(".inp", 3).setValue("테스트" + i + "이름");
			$(".inp", 4).setValue("테스트" + i + "회사명");
			$(".ico_chk").click();
			sleep(1000);
			$(".dup_button").click();
			for(int a=0;a<=5;a++) {
				if($(".regist_validate_txt").text().trim().equals("등록 가능한 이메일입니다.")) {
					break;
				} else {
					sleep(1000);
					$(".dup_button").click();
					System.out.println("메일 중복 체크 버튼 " + (a+1) + " 번째 클릭");
				}
			}
			$(".btn_full").click();
			$(".inp", 0).waitUntil(hidden, 10000);
			$(".btn_point").waitUntil(visible, 10000);
			$(".btn_point").click();
			open("https://demo-am.dighty.com/logout");
			System.out.println(i + " 번째 회원가입 완료");
			$(".btn_line").waitUntil(visible, 10000);
			$(".btn_line").click();
		}
		System.out.println(" ! ----- demo End ----- ! ");
  	}
  	@Test(priority = 0)
  	public void demo1() {
  		System.out.println(" ! ----- demo1 Start ----- ! ");
		open("https://demo-am.dighty.com/login");
		$(".inp", 0).setValue("admin");
		$(".inp", 1).setValue("dighty@5420");
		$(".btn_full").click();
		$(".btn-confirm", 0).waitUntil(visible, 10000);
		for(int i=847;i>=19;i--){
			if(i%2 == 1) {
				open("https://demo-am.dighty.com/admin/apply/detail/" + i);
				$(".btn-cancel").waitUntil(visible, 10000);
				$("input").click();
				$(".today").click();
				$(".btn-confirm").click();
				System.out.println(i + " 의 로그인 종료일 변경");
			}
		}
		System.out.println(" ! ----- demo1 End ----- ! ");
  	}
  	
  	//@Test(priority = 1)
  	public void addSegment() {
  		System.out.println(" ! ----- addSegment Start ----- ! ");
  		String[] Segname = {"", "", "", "", "", "", "", "", "", ""};
  		for(int i=0;i<=9;i++) {
  			$(".w150", 0).waitUntil(visible, 10000);
  			$(".w150", 0).click();
  			$(".w150", 0).waitUntil(hidden, 10000);
  			$(".btn_h44", 0).waitUntil(visible, 10000);
  			System.out.println("세그먼트 추가 페이지 접근");
  			$(".item__wrapper", 6).waitUntil(visible, 10000);
  			$(".item__wrapper", 6).click();
  			$(".btn-am-success", 0).click();
  			$(".fa-filter", 0).waitUntil(visible, 10000);
  			$(".fa-filter", 0).click();
  			$(".attributes__info > ul > li", 1).waitUntil(visible, 10000);
  			$(".attributes__info > ul > li", 1).click();
  			$(".cdp-radio", 0).waitUntil(visible, 10000);
  			$(".cdp-radio", 0).click();
  			$(".btn-am-success", 1).click();
  			sleep(4000);
  			$(".createRanking", 0).click();
  			$("#segmentSubject").waitUntil(visible, 10000);
  			//이름
  			$("#segmentSubject").setValue((i+1) + "번 작성 테스트");
  			$(".btn-am-success", 1).click();
  			$(".btn-am-primary", 0).waitUntil(visible, 10000);
  			sleep(500);
  			$(".btn-am-primary", 0).click();
  			sleep(500);
  			if($(".nofity_wrapper").text().trim().substring(2).equals("추가되었습니다.")) {
  				System.out.println("세그먼트 저장 레이어 저장 메세지 체크");
  				$(".close", 0).click();
  				$(".close", 0).waitUntil(hidden, 10000);
  			} else {
  				System.out.println("세그먼트 저장 레이어 저장 메세지 체크 실패");
  				System.out.println($(".nofity_wrapper").text().trim().substring(2));
  				close();
  			}	
  			System.out.println(am_date + " " + am_name + " 하나선택 세그먼트 추가 성공" + i);
  		}

		
		System.out.println(" ! ----- addSegment End ----- ! ");
  	}
  	////@Test(priority = 2)
  	public void combinationSegment() {
  		System.out.println(" ! ----- combinationSegment Start ----- ! ");
		$(".inp_serch", 0).waitUntil(visible, 10000);
		$(".inp_serch").setValue("apzz0928");
		$(".ico-serch").click();
		$(".no-data").waitUntil(visible, 10000);
		if($(".no-data").text().trim().equals("검색 결과가 없습니다.")) {
			System.out.println("세그먼트 리스트 페이지 검색결과 없음 체크");
		} else {
			System.out.println("세그먼트 리스트 페이지 검색결과 없음 체크 실패");
			System.out.println($(".no-data").text().trim());
			close();
		}
		$(".inp_serch").setValue(am_date);
		$(".ico-serch").click();
		$(".checkbox", 0).waitUntil(visible, 10000);
		$(".checkbox", 0).click();
		$(".checkbox", 1).click();
		$(".btn-am-secondary").click();
		$(".btn_inline", 0).waitUntil(visible, 10000);
		$(".btn_inline", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("세그먼트 이름을 입력해주세요.")) {
			System.out.println("세그먼트 조합 레이어 이름 미입력 메세지 체크");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("세그먼트 조합 레이어 이름 미입력 메세지 체크 실패");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".align_r", 0).setValue(am_date + ".조합 세그먼트");
		$(".btn_inline", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("조합 방식을 선택하세요.")) {
			System.out.println("세그먼트 조합 레이어 조합 방식 미선택 체크");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("세그먼트 조합 레이어 조합 방식 미선택 체크 실패");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		//조합 방식 랜덤 선택
		Random generator = new Random();
		int btnNum = generator.nextInt(4);
		System.out.println((btnNum+1) + "번째 조합 버튼 클릭");
		$(".btn_img", btnNum).click();
		combinationMethod =  $(".btn_img", btnNum).text().trim();
		for(int i=0;i<=10;i++) {
			if($(".info_txt_bx", 2).text().trim().equals("조합 방식을 선택하세요.")) {
				sleep(1000);
				System.out.println("세그먼트 조합 레이어 모수 조회 " + i + " 초 대기");
			} else if ($(".info_txt_bx", 2).text().trim().equals("데이터 처리 중입니다")) {
				sleep(1000);
				System.out.println("세그먼트 조합 레이어 모수 조회 " + i + " 초 대기");				
			} else {
				System.out.println("세그먼트 조합 레이어 모수 조회 완료 " + i + " 초");
				break;
			}
		}
		segmantParameter = $(".point_green", 2).text().trim();
		$(".btn_inline", 0).click();
		sleep(500);
		$(".btn-am-primary", 0).waitUntil(visible, 10000);
		$(".btn-am-primary", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("추가되었습니다.")) {
			System.out.println("세그먼트 조합 레이어 저장 메세지 체크");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("세그먼트 조합 레이어 저장 메세지 체크 실패");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".btn_line_sm2", 1).click();
		$(".btn_inline", 2).waitUntil(visible, 10000);
		$(".btn_inline", 2).click();
		sleep(500);
		$(".btn_inline", 6).click();
		if($(".nofity_wrapper").text().trim().substring(2).equals("세그먼트 사용 설정이 완료되었습니다.")) {
			System.out.println("세그먼트 사용 레이어 사용 메세지 체크");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("세그먼트 사용 레이어 사용 메세지 체크 실패");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".btn-am-secondary", 0).waitUntil(hidden, 10000);
		if($(".text-left", 0).text().trim().equals(am_date + ".조합 세그먼트")) {
		} else {
			System.out.println("세그먼트 사용내역 페이지 세그먼트 이름 체크 실패");
			System.out.println($(".text-left", 0).text().trim());
			close();
		}
		if($(".text-right", 0).text().trim().equals(segmantParameter)) {
			System.out.println("세그먼트 사용내역 페이지 세그먼트 오디언스 수 체크");
		} else {
			System.out.println("세그먼트 사용내역 페이지 세그먼트 오디언스 수 체크 실패");
			System.out.println($(".text-right", 0).text().trim());
			close();
		}
		$(".nav-link", 0).click();
		$(".inp_serch").setValue(am_date);
		$(".ico-serch").click();
		$(".checkbox", 3).waitUntil(hidden, 10000);
		$(".tree_item", 0).click();
		$(".progress-bar").waitUntil(visible, 10000);
		if($(".point_green", 0).text().trim().equals(combinationMethod)) {
			System.out.println("조합 세그먼트 조합 선택 방식 체크");
		} else {
			System.out.println("조합 세그먼트 조합 선택 방식 체크 실패");
			System.out.println($(".point_green", 0).text().trim());
			close();
		}
		if($(".tit", 2).text().trim().substring(13).equals(segmantParameter + " 명")) {
			System.out.println("조합 세그먼트 조건 모수 체크");
		} else {
			System.out.println("조합 세그먼트 조건 모수 체크 실패");
			close();
		}
		$(".tree_item", 0).click();
		$(".btn_line_sm2", 3).click();
		$(".createRanking_new", 0).waitUntil(visible, 10000);
		$(".createRanking_new", 0).click();
		$("input", 27).waitUntil(visible, 10000);
		$("input", 27).setValue(am_date + "." + am_name + ".삭제용세그먼트추가");
		sleep(100);
		$(".btn-am-success", 2).click();
		sleep(500);
		$(".btn-am-primary", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("추가되었습니다.")) {
			System.out.println("세그먼트 저장 레이어 저장 메세지 체크");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("세그먼트 저장 레이어 저장 메세지 체크 실패");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".inp_serch", 0).waitUntil(visible, 10000);
		$(".btn-am-outline-info", 1).click();
		$(".btn-am-primary").waitUntil(visible, 10000);
		sleep(500);
		$(".btn-am-primary").click();
		if($(".nofity_wrapper").text().trim().substring(2).equals("선택한 세그먼트가 삭제되었습니다.")) {
			System.out.println("세그먼트 조합 레이어 삭제 메세지 체크");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("세그먼트 조합 레이어 삭제 메세지 체크 실패");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
  		System.out.println(" ! ----- combinationSegment End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}