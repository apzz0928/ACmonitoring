package aceCounter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

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

public class temporarily {
	private static WebDriver driver;
	@SuppressWarnings("unused")
	private static String baseUrl, hubUrl, TestBrowser, pw, A, B, C, pageLoadCheck, nodata;
	private static HttpURLConnection huc;
	private static int respCode;

	@Parameters("browser")
	@BeforeClass
	public void beforeTest(String browser) throws MalformedURLException {
		baseUrl = "https://www.acecounter.com/www2/main.amz";
		hubUrl = "http://10.0.75.1:5555/wd/hub";
		//hubUrl = "http://10.77.129.79:5555/wd/hub";
		pw = "qordlf";
		A = "!@34";
		B = "@#14";
		C = "12#$";
		nodata = "조회된 데이터가 없습니다.";

		String urlToRemoteWD = hubUrl;
		DesiredCapabilities cap;
		/*ScreenShooter.captureSuccessfulTests = false;*/

		if (browser.equals("chrome")) {
			/*
			 * ChromeOptions options = new ChromeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.chrome();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("firefox")) {
			TestBrowser = "firefox";
			// cap = DesiredCapabilities.firefox();
			// RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			FirefoxOptions options = new FirefoxOptions();
			driver = new RemoteWebDriver(new URL(urlToRemoteWD), options);
			WebDriverRunner.setWebDriver(driver);
			//driver.manage().window().setSize(new Dimension(1650, 1000));
			driver.manage().window().maximize();
		} else if (browser.equals("edge")) {
			TestBrowser = "edge";
			/*
			 * EdgeOptions options = new EdgeOptions(); driver = new RemoteWebDriver(new
			 * URL(urlToRemoteWD), options);
			 */
			cap = DesiredCapabilities.edge();
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		} else if (browser.equals("internetExplorer")) {
			TestBrowser = "internetExplorer";
			/*
			 * InternetExplorerOptions options = new InternetExplorerOptions(); driver = new
			 * RemoteWebDriver(new URL(urlToRemoteWD), options);
			 */
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
	// 입력된 URL 정상 여부 확인
	public static boolean brokenLinkCheck(String urlName, String urlLink) {
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
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException ex) {
			ex.printStackTrace();
		}
	}

	private static void js(String javaScript) {
		executeJavaScript(javaScript);
	}

	@Test(priority = 0)
	public void login() {
		System.out.println(" ! ----- login Start ----- ! ");
		open(baseUrl);
		$(By.name("id")).waitUntil(visible, 15000);
		$(By.name("id")).setValue("apzz09288");
		$(By.name("pw")).setValue(pw + A);
		$(".login_btn").click();
		$(".id_area").waitUntil(visible, 10000);
		System.out.println($(".id_area").text().trim());
		if ($(".id_area").text().trim().equals("최영권님 환영합니다.")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_gray").click();
		$("h3", 0).waitUntil(visible, 10000);
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("서비스 관리")) {
				System.out.println(" *** my_notice page loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** my_notice page loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** my_notice page loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		System.out.println(" ! ----- login End ----- ! ");
	}
	@Test(priority = 1)
	public void my_script_codeset() {
		System.out.println(" ! ----- my_script_codeset Start ----- ! ");
		$(By.linkText("분석스크립트")).waitUntil(visible, 10000);
		$(By.linkText("분석스크립트")).click();
		$("h3", 0).waitUntil(visible, 10000);
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("분석스크립트")) {
				System.out.println(" *** my_script page loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** my_script page loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** my_script page loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(By.linkText("신청하기")).click();
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("분석스크립트 설치신청")) {
				System.out.println(" *** my_script_codeset page loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** my_script_codeset page loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** my_script_codeset page loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(By.linkText("계속진행")).click();
		confirm("신청할 서비스를 선택해주세요.");
		$("td", 28).click();
		$(By.linkText("계속진행")).click();
		for(int i=0;i<=20;i++) {
			if($("h3", 1).text().trim().equals("결제방법을 선택하세요")) {
				System.out.println(" *** my_script_codeset_pre page loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** my_script_codeset_pre page loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** my_script_codeset_pre page loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(By.linkText("신청")).click();
		confirm("신청인 이름을 입력해주세요.");
		$(".input01", 0).setValue("테스트 입니다.");
		$(By.linkText("신청")).click();
		confirm("연락처를 입력하세요.");
		$(".input01", 1).setValue("asd");
		$(By.linkText("신청")).click();
		confirm("연락처는 숫자만 가능합니다. 확인해주세요.");
		$(".input01", 1).setValue("01000000000");
		$(By.linkText("신청")).click();
		confirm("이메일을 입력하세요.");
		$(".input01", 2).setValue("apzz092888");
		$(By.linkText("신청")).click();
		confirm("이메일주소가 올바르지 않습니다.");
		$(".input01", 2).setValue("apzz092888@daum.net");
		$(By.linkText("신청")).click();
		confirm("개인정보 수집 및 이용에 대한 안내를 동의하여 주세요.");
		$("#c1").click();
		$(By.linkText("신청")).click();
		confirm("FTP정보를 입력해 주세요.");
		$(".input01", 3).setValue("http://localhost");
		$(By.linkText("신청")).click();
		confirm("포트번호를 입력해 주세요.");
		$(".input01", 4).setValue("8080");
		$(By.linkText("신청")).click();
		confirm("아이디를 입력해 주세요.");
		$(".input01", 6).setValue("8080");
		$(By.linkText("신청")).click();
		confirm("비밀번호를 입력해 주세요.");
		$(By.linkText("분석스크립트")).click();
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("분석스크립트")) {
				System.out.println(" *** my_script page loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** my_script page loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** my_script page loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		brokenLinkCheck("eComGuide", "https://www.acecounter.com/stat/jscode_new/guide/AceCounter_S001(eCom).pdf");
		brokenLinkCheck("proGuide", "https://www.acecounter.com/stat/jscode_new/guide/AceCounter_S001(Pro).pdf");
		brokenLinkCheck("mWebGuide", "https://www.acecounter.com/stat/jscode_new/guide/AceCounter_MS001(mWeb).pdf");
		brokenLinkCheck("mAppGuide", "https://www.acecounter.com/stat/jscode_new/guide/AceCounter_S001(Pro).pdf");
		js("window.open('https://www.acecounter.com/stat/my/my_script_mail_send_popup.amz?mysid=74254');");
		switchTo().window(1);
		for(int i=0;i<=2;i++) {
			$(".input01", i).setValue("");
		}
		$(By.linkText("발송")).click();
		confirm("제목을 입력해주세요.");
		$(".input01", 0).setValue("스크립트 설치 매일");
		$(By.linkText("발송")).click();
		confirm("발송 이메일을 입력해주세요.");
		$(".input01", 1).setValue("apzz092888");
		$(By.linkText("발송")).click();
		confirm("수신 이메일을 입력해주세요.");		
		$(".input01", 2).setValue("apzz092888");
		$(By.linkText("발송")).click();
		confirm("이메일 주소가 올바르지 않습니다.");
		$(".input01", 1).setValue("apzz092888@daum.net");
		$(By.linkText("발송")).click();
		$(".input01", 2).setValue("apzz092888@daum.net");
		$(By.linkText("발송")).click();
		confirm("설치안내 메일이 발송되었습니다.");
		switchTo().window(0);
		js("window.open('https://logins.daum.net/accounts/loginform.do?url=https%3A%2F%2Fmail.daum.net%2F');");
		switchTo().window(1);
		$("#id").setValue("apzz092888");
		$("#inputPwd").setValue(pw + A);
		$("#loginBtn").click();
		$(".link_check").waitUntil(visible, 15000);
		sleep(1000);
		refresh();
		pageLoadCheck = $("h1").text().trim();
		if (pageLoadCheck.equals("Daum\n" + "메일")) {
			System.out.println(" *** scriptList daum mail list page load Success !! *** ");
		} else {
			System.out.println(" *** scriptList daum mail list page load Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".tit_subject", 0).waitUntil(visible, 15000);
		$(".tit_subject", 0).click();
		$(".txt_filename").waitUntil(visible, 15000);
		String[] pLC = $("td", 10).text().trim().split(" ");
		if(pLC[1].equals("apzz0928.egloos.com")) {
			System.out.println(" *** scriptList send mail URL check Success !! *** ");
		} else {
			System.out.println(" *** scriptList send mail URL check Fail ... !@#$%^&*() *** ");
			close();
		}
		//메일삭제
		$(By.linkText("받은메일함")).click();
		sleep(1000);
		$(".select_all").waitUntil(visible, 10000);
		$(".select_all").click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("휴지통")).click();
		sleep(1000);
		$(".select_all").waitUntil(visible, 10000);
		$(".select_all").click();
		$(".wrap_bold > .btn_permanent").click();
		sleep(2000);
		$(".check_type2").click();
		$(By.linkText("받은메일함")).click();
		switchTo().window(0);
		System.out.println(" ! ----- my_script_codeset End ----- ! ");
	}
	@Test(priority = 2)
	public void my_member_modify() {
		System.out.println(" ! ----- my_member_modify Start ----- ! ");
		$(By.linkText("정보수정/관리")).waitUntil(visible, 10000);
		$(By.linkText("정보수정/관리")).click();
		System.out.println($("span", 9).text().trim());
		$(By.linkText("회원정보수정")).waitUntil(visible, 10000);
		$(By.linkText("회원정보수정")).click();
		$("h3", 0).waitUntil(visible, 10000);
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("회원정보수정")) {
				System.out.println(" *** my_member_modify page loading check Success !! *** ");
				break;
		    } else if(i<=19) {
				System.out.println(" *** my_member_modify page loading wait 0." + i + " second *** ");
				sleep(100);
		    } else {
				System.out.println(" *** my_member_modify page loading check Fail ... !@#$%^&*() *** ");
		        close();
		    }
		}
		$(By.linkText("변경하기")).click();
		$(By.linkText("변경")).waitUntil(visible, 10000);
		$(By.linkText("변경")).click();
		confirm("현재 비밀번호를 입력하세요.");
		$(".input01", 0).setValue("123");
		$(By.linkText("변경")).click();
		confirm("현재 비밀번호가 정확하지 않습니다.");
		$(".input01", 0).setValue(pw + A);
		$(By.linkText("변경")).click();
		confirm("새 비밀번호를 입력하세요.");		
		$(".input01", 1).setValue(pw + B);
		$(By.linkText("변경")).click();
		confirm("새 비밀번호 확인이 필요합니다.");
		$(".input01", 2).setValue(pw + C);
		$(By.linkText("변경")).click();
		confirm("새 비밀번호가 일치 하지 않습니다.");		
		$(".input01", 2).setValue(pw + B);
		$(By.linkText("변경")).click();
		$(By.linkText("확인")).waitUntil(visible, 10000);
		$(By.linkText("확인")).click();
		
		
		
		
		
		
		System.out.println(" ! ----- my_member_modify End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}