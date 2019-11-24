package aceCounter;

import java.io.IOException;
import java.net.HttpURLConnection;
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

public class serviceManage {
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
	private static void alertCheck(String msg, String alert) {
		if(switchTo().alert().getText().equals(alert)) {
			System.out.println(" *** " + msg + " check Success !! *** ");
			confirm(alert);
		} else {
			System.out.println(" *** " + msg + " check Fail ... !@#$%^&*() *** ");
			System.out.println(switchTo().alert().getText());
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
  	public void myNotice() {
		System.out.println(" ! ----- myNotice Start ----- ! ");
		open("https://www.acecounter.com/stat/my/my_notice.amz");
		if($("h3", 0).text().trim().equals("서비스 관리")) {
			System.out.println(" *** myNotice pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myNotice pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		String[] pageLoadCheck = {"[eCom] 이글루스 (apzz09288.egloos.com)", "[eCom] blogspot (apzz092888.blogspot.com)"};
		for(int i=0;i<=1;i++) {
			if($(".l", i).text().trim().equals(pageLoadCheck[i])) {
				System.out.println(" *** myNotice service list (" + i + ") check Success !! *** ");			
			} else {
				System.out.println(" *** myNotice service list (" + i + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($(".l", i).text().trim());
				close();
			}
		}
		System.out.println(" ! ----- myNotice End ----- ! ");
  	}
  	@Test(priority = 2)
  	public void myScript() {
		System.out.println(" ! ----- myScript Start ----- ! ");
		$(".m_stat", 0).click();
		if($("h3", 0).text().trim().equals("분석스크립트")) {
			System.out.println(" *** myScript pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myScript pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- myScript End ----- ! ");
  	}
  	@Test(priority = 3)
  	public void memberInfo() {
		System.out.println(" ! ----- memberInfo Start ----- ! ");
		$(".btn_menu").click();
		$("ul > li > a > span", 5).waitUntil(visible, 10000);
		$("ul > li > a > span", 5).click();
		if($("h3", 0).text().trim().equals("회원정보수정")) {
			System.out.println(" *** memberInfo pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** memberInfo pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".button.blue").click();
		$(".lay_pop").waitUntil(visible, 10000);
		$(".btn_pack.large", 2).waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo pw null msg", "현재 비밀번호를 입력하세요.");
		$(".input01", 0).setValue(pw);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo pw valCheck msg", "현재 비밀번호가 정확하지 않습니다.");
		$(".input01", 0).setValue(pw+A);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo change pw null msg", "새 비밀번호를 입력하세요.");
		$(".input01", 1).setValue(pw);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo change pw check msg", "새 비밀번호 확인이 필요합니다.");
		$(".input01", 2).setValue(pw);
		$(".btn_pack.large", 0).click();
		$(".bx_tip").waitUntil(visible, 10000);
		$(".btn_pack.large", 2).waitUntil(hidden, 10000);
		if($(".bx_tip").text().trim().equals("비밀번호가 변경되었습니다.")) {
			System.out.println(" *** memberInfo password change check Success !! *** ");
		} else {
			System.out.println(" *** memberInfo password change check Fail ... !@#$%^&*() *** ");
			System.out.println($(".bx_tip").text().trim() + ".");
			close();
		}
		$(".btn_pack.large", 0).click();
		$(".button.blue").click();
		$(".lay_pop").waitUntil(visible, 10000);
		$(".btn_pack.large", 2).waitUntil(visible, 10000);
		$(".input01", 0).waitUntil(visible, 10000);
		$(".input01", 0).setValue(pw);
		$(".input01", 1).setValue(pw+A);
		$(".input01", 2).setValue(pw+A);
		$(".btn_pack.large", 0).click();
		$(".bx_tip").waitUntil(visible, 10000);
		$(".btn_pack.large", 2).waitUntil(hidden, 10000);
		if($(".bx_tip").text().trim().equals("비밀번호가 변경되었습니다.")) {
			System.out.println(" *** memberInfo password restoration check Success !! *** ");
		} else {
			System.out.println(" *** memberInfo password restoration check Fail ... !@#$%^&*() *** ");
			System.out.println($(".bx_tip").text().trim() + ".");
			close();
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).setValue("");
		$(".btn_pack.large", 1).click();
		alertCheck("memberInfo name null msg", "담당자 이름을 입력하여주세요");
		$(".input01", 0).setValue("변경이름");
		$(".input01", 1).setValue("");
		$(".btn_pack.large", 1).click();
		alertCheck("memberInfo companyName null msg", "회사명을 입력하여주세요.");
		$(".input01", 1).setValue("변경회사명");
		$(".input01", 5).setValue("");
		$(".btn_pack.large", 1).click();
		alertCheck("memberInfo email null msg", "담당자 이메일주소를 입력하여주세요");
		$(".input01", 5).setValue("apzz0928888@daum.net");
		$(".btn_pack.large", 1).click();
		$(".input01", 0).waitUntil(hidden, 10000);
		String[] dataCheck = {"변경이름", "변경회사명"};
		for(int i=4;i<=5;i++) {
			if($("td", i).text().trim().equals(dataCheck[i-4])) {
				System.out.println(" *** memberInfo modify " + (i-4) + " check Success !! *** ");
			} else {
				System.out.println(" *** memberInfo modify " + (i-4) + " check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
			if(i==4) {
				dataCheck[0] = "원래이름";
			} else {
				dataCheck[1] = "원래회사명";
			}
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".input01", 0).setValue("원래이름");
		$(".input01", 1).setValue("원래회사명");
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(hidden, 10000);
		for(int i=4;i<=5;i++) {
			if($("td", i).text().trim().equals(dataCheck[i-4])) {
				System.out.println(" *** memberInfo restoration " + (i-4) + " check Success !! *** ");
			} else {
				System.out.println(" *** memberInfo restoration " + (i-4) + " check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
		}
		System.out.println(" ! ----- memberInfo End ----- ! ");
  	}
  	@Test(priority = 4)
  	public void serviceInfo() {
		System.out.println(" ! ----- serviceInfo Start ----- ! ");
		$("ul > li > a > span", 6).click();
		if($("h3", 0).text().trim().equals("서비스정보수정")) {
			System.out.println(" *** serviceInfo pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceInfo pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo change null msg", "변경된 정보가 없습니다.");
		$(".btn_del", 0).click();
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo domain null msg", "도메인을 입력하세요");
		refresh();
		$(".input01", 2).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo siteName null msg", "웹사이트이름을 입력하세요");
		$(".input01", 2).setValue("blogger");
		$(".input01", 3).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo defaultPage null msg", "웹사이트 기본페이지를 입력하세요");
		$(".input01", 3).setValue("index1.html");
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(hidden, 10000);
		String[] dataCheck = {"blogger", "", "index1.html"};
		for(int i=3;i<=5;i=i+2) {
			if($("td", i).text().trim().equals(dataCheck[i-3])) {
				System.out.println(" *** serviceInfo Modify " + (i-3) + " check Success !! *** ");
			} else {
				System.out.println(" *** serviceInfo Modify " + (i-3) + " check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
			if(i==3) {
				dataCheck[0] = "blogspot";
			} else if(i==5) {
				dataCheck[2] = "index.html";		
			}
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".input01", 2).setValue("blogspot");
		$(".input01", 3).setValue("index.html");
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(hidden, 10000);
		for(int i=3;i<=5;i=i+2) {
			if($("td", i).text().trim().equals(dataCheck[i-3])) {
				System.out.println(" *** serviceInfo restoration " + (i-3) + " check Success !! *** ");
			} else {
				System.out.println(" *** serviceInfo restoration " + (i-3) + " check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
		}
		System.out.println(" ! ----- serviceInfo End ----- ! ");
  	}
  	@Test(priority = 5)
  	public void addService() {
		System.out.println(" ! ----- addService Start ----- ! ");
		$("ul > li > a > span", 7).click();
		if($("h3", 0).text().trim().equals("서비스 추가")) {
			System.out.println(" *** addService pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** addService pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("addService domain null msg", "웹사이트 도메인 입력해 주세요.");
		$(".input01", 0).setValue(id_date + ".com");
		$(".btn_sty01", 0).click();
		sleep(500);
		$(".btn_del", 0).waitUntil(visible, 10000);
		$("#domain_1").waitUntil(visible, 10000);
		$(".input01", 2).setValue("");
		$(".input01", 3).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("addService siteName null msg", "웹사이트이름을 입력하세요");
		$(".input01", 2).setValue(id_date);
		$(".btn_pack.large", 0).click();
		alertCheck("addService defaultPage null msg", "웹사이트 기본페이지를 입력하세요");
		$(".input01", 3).setValue("index.html");
		$(".btn_pack.large", 0).click();
		alertCheck("addService class null msg", "웹사이트 분류를 선택하세요");
		$(".select01", 0).selectOption("기타");
		$(".btn_pack.large", 0).click();
		alertCheck("addService add confirm msg", "에이스카운터 서비스를 신청하시겠습니까?");
		alertCheck("addService chaptcha valCheck msg", "인증코드가 입력되지 않았습니다.");
		$(".input01", 0).waitUntil(visible, 10000);
		$("ul > li > a > span", 7).waitUntil(visible, 10000);
		if($("h3", 0).text().trim().equals("서비스 추가")) {
			System.out.println(" *** addService validation check Success !! *** ");
		} else {
			System.out.println(" *** addService validation check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- addService End ----- ! ");
  	}
    @Test(priority = 6)
  	public void subAdmin() {
		System.out.println(" ! ----- subAdmin Start ----- ! ");
		$("ul > li > a > span", 8).click();
		if($("h3", 0).text().trim().equals("부관리자 관리")) {
			System.out.println(" *** subAdmin pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** subAdmin pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin id null msg", "아이디를 입력해주세요.");
		$(".input01", 0).setValue(id_date);
		$(".btn_pack.large", 1).click();		
		alertCheck("subAdmin pw null msg", "비밀번호를 입력해주세요.");
		$(".input01", 1).setValue(pw+B);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin change pw null msg", "비밀번호확인을 입력하여 주세요");
		$(".input01", 2).setValue(pw+A);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin change pw check msg", "비밀번호가 일치하지 않습니다");
		$(".input01", 2).setValue(pw+B);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin name null msg", "담당자 이름을 입력하여주세요");
		$(".input01", 3).setValue("부관리자이름");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin email null msg", "담당자 이메일주소를 입력하여주세요");
		$(".input01", 4).setValue("apzz0928888");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin email valCheck msg", "이메일주소가 올바르지 않습니다.");
		$(".input01", 4).setValue("apzz0928888@daum.net");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin add msg", "부관리자 등록이 완료되었습니다");		
		$("td > img", 1).waitUntil(visible, 10000);
		String[] dataCheck = {id_date, "부관리자이름"};
		for(int i=5;i<=6;i++) {
			if($("td", i).text().trim().equals(dataCheck[i-5])) {
				System.out.println(" *** subAdmin add check Success !! *** ");
			} else {
				System.out.println(" *** subAdmin add check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
		}
		$("td > img", 1).click();
		$(".input01", 1).setValue(pw+B);
		$(".input01", 2).setValue(pw+B);
		$(".input01", 3).setValue("부관리자이름수정");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin modify info msg", "부관리자 수정이 완료되었습니다.");	
		if($("td", 6).text().trim().equals("부관리자이름수정")) {
			System.out.println(" *** subAdmin modify info check Success !! *** ");
		} else {
			System.out.println(" *** subAdmin modify info check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 6).text().trim());
			close();
		}
		$("td > img", 2).waitUntil(visible, 10000);
		$("td > img", 2).click();
		alertCheck("subAdmin del confirm msg", "정말로 삭제하시겠습니까??");
		alertCheck("subAdmin del msg", "부관리자 정보가 삭제되었습니다.");
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($("td", 3).text().trim().equals("등록된 부관리자가 없습니다.")) {
			System.out.println(" *** subAdmin del check Success !! *** ");
		} else {
			System.out.println(" *** subAdmin del check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 3).text().trim());
			close();
		}
		System.out.println(" ! ----- subAdmin End ----- ! ");
  	}
    @Test(priority = 7)
  	public void coupon() {
		System.out.println(" ! ----- coupon Start ----- ! ");
		$("ul > li > a > span", 9).click();
		if($("h3", 0).text().trim().equals("쿠폰관리")) {
			System.out.println(" *** coupon pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** coupon pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- coupon End ----- ! ");
  	}
    @Test(priority = 8)
  	public void leaveService() {
		System.out.println(" ! ----- leaveService Start ----- ! ");
		$("ul > li > a > span", 10).waitUntil(visible, 10000);
		$("ul > li > a > span", 10).click();
		if($("h3", 0).text().trim().equals("서비스 해지신청")) {
			System.out.println(" *** leaveService pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** leaveService pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_sty01").click();
		alertCheck("leaveService pw null msg", "비밀번호를 입력해주세요.");
		$(".input01").setValue(pw);
		$(".btn_sty01").click();		
		alertCheck("leaveService pw valCheck msg", "패스워드가 일치하지 않습니다. 다시 입력하여 주십시오.");
		$(".input01").setValue(pw+A);
		$(".btn_sty01").click();
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService site null msg", "신청할 사이트를 선택해주세요.");
		$(By.name("checkbox_1")).click();
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService name null msg", "신청인 이름을 입력해 주세요.");
		$(".input01", 0).setValue("테스트 이름");
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService email null msg", "이메일을 입력하세요.");
		$(".input01", 3).setValue("test");
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService email valCheck msg", "이메일주소가 올바르지 않습니다.");		
		$(".input01", 3).setValue("test@mail.com");
		$(".btn_pack.large", 0).click();
		if(switchTo().alert().getText().equals("해지신청하시겠습니까?")) {
			dismiss("해지신청하시겠습니까?");
			System.out.println(" *** leaveService confirm msg check Success !! *** ");
		} else {
			System.out.println(switchTo().alert().getText());
			System.out.println(" *** leaveService confirm msg check Fail ... !@#$%^&*() *** ");
	  		close();
		}
		System.out.println(" ! ----- leaveService End ----- ! ");
  	}
    @Test(priority = 9)
  	public void extendCharge() {
		System.out.println(" ! ----- extendCharge Start ----- ! ");
		$("span.title", 2).click();
		$("ul > li > a > span", 14).waitUntil(visible, 10000);
		$("ul > li > a > span", 14).click();
		if($("h3", 0).text().trim().equals("연장요금결제")) {
			System.out.println(" *** extendCharge pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge site null msg", "웹사이트를 선택하여 주세요");
		$(By.name("ck_1")).click();
		$(".btn_pack.large", 0).click();
		$(".btn_sty01", 0).waitUntil(visible, 10000);
		String[] dataCheck = {"blogspot(apzz092888.blogspot.com)", "eCommerce"};
		for(int i=15;i<=16;i++) {
			if($("td", i).text().trim().equals(dataCheck[i-15])) {
				System.out.println(" *** extendCharge table data(" + (i-15) + ") check Success !! *** ");
			} else {
				System.out.println(" *** extendCharge table data(" + (i-15) + ") check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
		}
		$(".btn_pack.large", 0).click();
		$("input[name=pay_mode_select]", 0).waitUntil(visible, 10000);
		$("input[name=pay_mode_select]", 0).click();
		$("td", 28).waitUntil(visible, 10000);
		if($("td", 28).text().trim().equals("165,000 원")) {
			System.out.println(" *** extendCharge creditCard check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge creditCard check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 28).text().trim());
			close();		
		}
		$(".input01", 0).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge creditCard name null msg", "담당자명을 입력해주세요.");
		$(".input01", 0).setValue("원래이름");
		$(".input01", 1).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge creditCard phone null msg", "휴대전화번호를 입력해주세요.");
		$(".input01", 1).setValue("010-9743-0928");
		$(".input01", 2).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge creditCard email null msg", "이메일을 입력해주세요.");
		$("input[name=pay_mode_select]", 1).click();
		$(".btn_pack.medium").waitUntil(visible, 10000);
		if($(".pnt04", 1).text().trim().equals("165,000원")) {
			System.out.println(" *** extendCharge virtual account check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge virtual account check Fail ... !@#$%^&*() *** ");
			System.out.println($(".pnt04", 1).text().trim());
			close();		
		}
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account bankAccount null msg", "은행계좌를 확인해주세요.");
		$(".form2").selectOption("우리은행(20)");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account name null msg", "입금자명을 입력해주세요.");
		$(".input01", 0).setValue("입금자명");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account phone null msg", "휴대전화번호를 입력해주세요.");
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account email null msg", "이메일주소를 입력해주세요.");
		$(".input01", 5).setValue("test");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();	
		alertCheck("extendCharge virtual account email null msg", "이메일주소가 올바르지 않습니다.");
		$(".input01", 5).setValue("test@mail.com");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Business license null msg", "사업자 번호를 입력해주세요.");
		$(".btn_pack.medium").click();
		alertCheck("extendCharge virtual account Business license defaultCheck msg", "저장된 정보가 없습니다.");
		$(".input01", 6).setValue("000");
		$(".input01", 7).setValue("00");
		$(".input01", 8).setValue("00000");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Company name null msg", "회사명을 입력해 주세요.");	
		$(".input01", 9).setValue("회사명");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Boss name null msg", "대표자명을 입력해주세요.");
		$(".input01", 10).setValue("대표자명");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Business condition null msg", "업태를 입력해주세요.");
		$(".input01", 12).setValue("업태");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Company address null msg", "회사주소를 입력해주세요.");
		$(".input01", 11).setValue("회사주소");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Company type null msg", "종목을 입력해주세요.");
		$(".input01", 13).setValue("종목");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account In charge name null msg", "담당자명을 입력해주세요.");
		$(".input01", 14).setValue("담당자명");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account In charge phone null msg", "담당자 연락처를 입력해주세요.");
		$("input[name=pay_mode_select]", 2).scrollIntoView(false);
		$("input[name=pay_mode_select]", 2).click();
		for(int i=0;i<=5;i++) {
			if($("strong", 5).text().trim().equals("apzz092888")) {
				System.out.println(" *** extendCharge no passbook load wait " + (i+1) + " Success !! *** ");
				break;
			} else {
				System.out.println(" *** extendCharge no passbook load wait " + (i+1) + " second *** ");
				sleep(1000);
			}
		}
		if($(".pnt04", 1).text().trim().equals("165,000 원")) {
			System.out.println(" *** extendCharge no passbook check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge no passbook check Fail ... !@#$%^&*() *** ");
			System.out.println($(".pnt04", 1).text().trim());
			close();		
		}
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook bankAccount null msg", "은행계좌를 확인해주세요.");
		$(".form2").selectOption("우리은행(742-249776-13-001)");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook name null msg", "입금자명을 입력해주세요.");
		$(".input01", 0).setValue("입금자명");
		$(".input01", 2).setValue("");
		$(".input01", 3).setValue("");
		$(".input01", 4).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook year null msg", "입금한 연도를 입력해주세요.");	
		$(".input01", 2).setValue("0000");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook month null msg", "입금한 달을 입력해주세요.");
		$(".input01", 3).setValue("00");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook day null msg", "입금일을 입력해주세요.");
		$("span.title", 2).click();
		$(".active").waitUntil(visible, 10000);
		System.out.println(" ! ----- extendCharge End ----- ! ");
  	}
    @Test(priority = 10)
  	public void additionalCharge() {
		System.out.println(" ! ----- additionalCharge Start ----- ! ");
		$("ul > li > a > span", 15).waitUntil(visible, 10000);
		$("ul > li > a > span", 15).click();
		if($("h3", 0).text().trim().equals("추가요금결제")) {
			System.out.println(" *** additionalCharge pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** additionalCharge pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("additionalCharge service null msg", "신청할 서비스를 선택해주세요.");
		if($("td", 12).text().trim().equals("추가요금 미결제 내역이 없습니다.")) {
			System.out.println(" *** additionalCharge tableData check Success !! *** ");
		} else {
			System.out.println(" *** additionalCharge tableData check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 12).text().trim());
			close();
		}
		System.out.println(" ! ----- additionalCharge End ----- ! ");
  	}
    @Test(priority = 11)
  	public void myTaxList() {
		System.out.println(" ! ----- myTaxList Start ----- ! ");
		$("ul > li > a > span", 16).waitUntil(visible, 10000);
		$("ul > li > a > span", 16).click();
		if($("h3", 0).text().trim().equals("세금계산서")) {
			System.out.println(" *** myTaxList pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myTaxList pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- myTaxList End ----- ! ");
  	}
    @Test(priority = 12)
  	public void myPayInfo() {
		System.out.println(" ! ----- myPayInfo Start ----- ! ");
		$("ul > li > a > span", 17).waitUntil(visible, 10000);
		$("ul > li > a > span", 17).click();
		if($("h3", 0).text().trim().equals("결제내역조회")) {
			System.out.println(" *** myPayInfo pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myPayInfo pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		if($("td", 3).text().trim().equals("미납요금이 없습니다.")) {
			System.out.println(" *** myPayInfo virtual account check Success !! *** ");
		} else {
			System.out.println(" *** myPayInfo virtual account check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- myPayInfo End ----- ! ");
  	}
    @Test(priority = 13)
  	public void myBankInfo() {
		System.out.println(" ! ----- myBankInfo Start ----- ! ");
		$("ul > li > a > span", 18).waitUntil(visible, 10000);
		$("ul > li > a > span", 18).click();
		if($("h3", 0).text().trim().equals("무통장 입금안내")) {
			System.out.println(" *** myBankInfo pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myBankInfo pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".input01", 2).setValue("");
		$(".input01", 3).setValue("");
		$(".input01", 4).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo bank null msg", "입금하신 은행을 선택하여 주세요");
		$(".select01").selectOption("우리은행(742-249776-13-001)");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo name null msg", "입금하신 분 성함을 입력하여 주세요");	
		$(".input01", 0).setValue("입금자명");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo phone null msg", "입금하신 분 연락처를 입력하여 주세요");	
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo year null msg", "입금하신 일자을 입력하여 주세요");
		$(".input01", 2).setValue("0000");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo month null msg", "입금하신 일자을 입력하여 주세요");
		$(".input01", 3).setValue("00");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo day null msg", "입금하신 일자을 입력하여 주세요");
		$(".input01", 4).setValue("00");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo day null msg", "입금하신 금액을 입력하여 주세요");
		System.out.println(" ! ----- myBankInfo End ----- ! ");
  	}
    @Test(priority = 14)
  	public void installScript() {
		System.out.println(" ! ----- installScript Start ----- ! ");
		$("span.title", 3).click();
		$("ul > li > a > span", 22).waitUntil(visible, 10000);
		$("ul > li > a > span", 22).click();
		if($("h3", 0).text().trim().equals("분석스크립트 설치신청")) {
			System.out.println(" *** installScript pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** installScript pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("installScript service null msg", "신청할 서비스를 선택해주세요.");
		$(By.name("radio_ck"), 0).click();
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$("img", 18).click();
		$(".btn_pack.large", 0).click();
		alertCheck("installScript name null msg", "신청인 이름을 입력해주세요.");
		$(".input01", 0).setValue("신청인 이름");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript phone null msg", "연락처를 입력하세요.");
		$(".input01", 1).setValue("aaa");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript phone valCheck msg", "연락처는 숫자만 가능합니다. 확인해주세요.");
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript email null msg", "이메일을 입력하세요.");
		$(".input01", 2).setValue("메일주소");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript email valCheck msg", "이메일주소가 올바르지 않습니다.");
		$(".input01", 2).setValue("메일주소@mail.com");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript agree null msg", "개인정보 수집 및 이용에 대한 안내를 동의하여 주세요.");
		$("#c1").click();
		$(".btn_pack.large", 0).click();
		alertCheck("installScript FTP URL null msg", "FTP정보를 입력해 주세요.");
		$(".input01", 3).setValue("ftp");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript port null msg", "포트번호를 입력해 주세요.");
		$(".input01", 4).setValue("1234");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript ID null msg", "아이디를 입력해 주세요.");
		$(".input01", 6).setValue("아이디");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript PW null msg", "비밀번호를 입력해 주세요.");
		System.out.println(" ! ----- installScript End ----- ! ");
  	}
    @Test(priority = 15)
  	public void serviceModify() {
		System.out.println(" ! ----- serviceModify Start ----- ! ");
		$("ul > li > a > span", 23).waitUntil(visible, 10000);
		$("ul > li > a > span", 23).click();
		if($("h3", 0).text().trim().equals("서비스버전 변경신청")) {
			System.out.println(" *** serviceModify pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceModify pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify site null msg", "신청할 사이트를 선택해주세요.");
		$(By.name("checkbox_0")).click();
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify name null msg", "이름을 입력하세요.");
		$(".input01", 0).setValue("신청인 이름");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify phone null msg", "연락처를 입력하세요.");
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify email null msg", "이메일을 입력하세요.");
		$(".input01", 2).setValue("email");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify email valCheck msg", "이메일주소가 올바르지 않습니다.");
		System.out.println(" ! ----- serviceModify End ----- ! ");
  	}
    @Test(priority = 16)
  	public void Report() {
		System.out.println(" ! ----- Report Start ----- ! ");
		$("ul > li > a > span", 24).waitUntil(visible, 10000);
		$("ul > li > a > span", 24).click();
		if($("h3", 0).text().trim().equals("요약리포트 발송설정")) {
			System.out.println(" *** Report pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** Report pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$("#summary_mail").setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("Report summary email null msg", "수신이메일을 입력해주세요!");
		$("#summary_mail").setValue("apzz0928888@daum.net");
		$(".btn_pack.large", 0).click();
		alertCheck("Report send email msg", "요약리포트가 발송되었습니다.");
		js("window.open('https://logins.daum.net/accounts/signinform.do?url=https%3A%2F%2Fmail.daum.net%2F');");
		//다음메일 탭으로 포커스 변경
		switchTo().window(1);
		$("#id").waitUntil(visible, 10000);
		$("#id").setValue("apzz0928888");
		$("#inputPwd").setValue(pw + A);
		$("#loginBtn").click();
		sleep(3500);
		refresh();
		$(".tit_subject", 0).waitUntil(visible, 15000);
		$(".tit_subject", 0).click();
		$(".tit_mail").waitUntil(visible, 10000);
		if($(".tit_mail").text().trim().split(" ")[2].equals("blogspot(apzz092888.blogspot.com)")) {
			System.out.println(" *** Report mailTitle check Success !! *** ");
		} else {
			System.out.println(" *** Report mailTitle check Fail ... !@#$%^&*() *** ");
			System.out.println($(".tit_mail").text().trim().split(" ")[2]);
			close();
		}
		//받은메일함으로 이동 후 메일 삭제
		$(".ui-droppable", 0).click();
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_del").click();
		$(".info_none").waitUntil(visible, 10000);
		$(".ui-droppable", 4).click();
		sleep(1000);
		js("$('#allCk').click()");
		$(".btn_toolbar.btn_permanent").click();
		$(".btn_box").waitUntil(visible, 10000);
		$(".btn_g.check_type2").click();
		$(".btn_box").waitUntil(hidden, 10000);
		$(".ui-droppable", 0).click();
		switchTo().window(0);
		$("#mailing_n").click();
		$("#mailing_y").click();
		$(".btn_pack.large", 1).click();
		alertCheck("Report modify msg", "설정하신 내용이 저장되었습니다.\n" + "설정내용은 익일부터 반영됩니다.");
		$(".btn_pack.large", 1).waitUntil(visible, 10000);
		for(int i=7;i<=13;i++) {
			$("td > input", i).click();
		}
		$(".btn_pack.large", 1).click();
		alertCheck("Report save msg", "설정하신 내용이 저장되었습니다.\n" + "설정내용은 익일부터 반영됩니다.");		
		$(".btn_pack.large", 1).waitUntil(visible, 10000);	
		System.out.println(" ! ----- Report End ----- ! ");
  	}
    @Test(priority = 17)
  	public void Monitoring() {
		System.out.println(" ! ----- Monitoring Start ----- ! ");
		$("ul > li > a > span", 25).waitUntil(visible, 10000);
		$("ul > li > a > span", 25).click();
		if($("h3", 0).text().trim().equals("알림메일 발송설정")) {
			System.out.println(" *** Monitoring pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** Monitoring pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		for(int i=0;i<=8;i++) {
			$("label > input", i).click();
		}
		for(int i=1;i<=8;i++) {
			$(".select01", i).selectOption("7일 평균");
		}
		for(int i=1;i<=8;i++) {
			$(".input01", i).setValue("0");
		}
		$(".btn_pack.large", 0).click();
		alertCheck("Monitoring modify confirm msg", "선택하신 알림 메일이 없습니다 \n" + "저장하시겠습니까?");
		alertCheck("Monitoring modify msg", "설정하신 내용이 저장되었습니다. \n\n" + "설정내용은 익일부터 반영됩니다.");		
		$(".btn_pack.large", 0).waitUntil(visible, 10000);		
		for(int i=0;i<=8;i++) {
			$("label > input", i).click();
		}
		for(int i=1;i<=8;i++) {
			$(".select01", i).selectOption("지난주 같은 요일");
		}
		for(int i=1;i<=8;i++) {
			$(".input01", i).setValue("100");
		}
		$(".btn_pack.large", 0).click();
		alertCheck("Monitoring save confirm msg", "저장하시겠습니까?");
		alertCheck("Monitoring modify msg", "설정하신 내용이 저장되었습니다. \n\n" + "설정내용은 익일부터 반영됩니다.");		
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		System.out.println(" ! ----- Monitoring End ----- ! ");
  	}
    @Test(priority = 18)
  	public void serviceNotice() {
		System.out.println(" ! ----- serviceNotice Start ----- ! ");
		$("span.title", 4).click();
		$("ul > li > a > span", 29).waitUntil(visible, 10000);
		$("ul > li > a > span", 29).click();
		if($("h3", 0).text().trim().equals("서비스 공지")) {
			System.out.println(" *** serviceNotice pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceNotice pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("1234567890");
		$("img", 14).click();
		$(".next").waitUntil(hidden, 10000);
		if($("td", 3).text().trim().equals("데이터가 없습니다.")) {
			System.out.println(" *** serviceNotice empty search check Success !! *** ");
		} else {
			System.out.println(" *** serviceNotice empty search check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 3).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("단장");
		$("img", 14).click();
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("에이스카운터가 새롭게 단장되었습니다")) {
			System.out.println(" *** serviceNotice search check Success !! *** ");
		} else {
			System.out.println(" *** serviceNotice search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".left > a").click();
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($("td", 5).text().trim().equals("2003-01-01")) {
			System.out.println(" *** serviceNotice read check Success !! *** ");
		} else {
			System.out.println(" *** serviceNotice read check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 5).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		$(".next").waitUntil(visible, 10000);
		System.out.println(" ! ----- serviceNotice End ----- ! ");
  	}
    @Test(priority = 19)
  	public void serviceAlarm() {
		System.out.println(" ! ----- serviceAlarm Start ----- ! ");
		$("ul > li > a > span", 30).waitUntil(visible, 10000);
		$("ul > li > a > span", 30).click();
		if($("h3", 0).text().trim().equals("서비스 알림내역")) {
			System.out.println(" *** serviceAlarm pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("1234567890");
		$("img", 14).click();
		$(".next").waitUntil(hidden, 10000);
		if($("td", 3).text().trim().equals("데이터가 없습니다.")) {
			System.out.println(" *** serviceAlarm empty search check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm empty search check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 3).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("종료");
		$("img", 14).click();
		$(".left", 3).waitUntil(visible, 10000);
		if($(".left", 3).text().trim().equals("[서비스] 무료서비스 사용기간이 금일 종료됩니다.")) {
			System.out.println(" *** serviceAlarm search check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 3).text().trim());
			close();
		}
		$(".left > a", 2).click();
		$(".cont_div", 2).waitUntil(visible, 10000);
		if($(".cont_div", 2).text().trim().split(" ")[7].equals("종료됩니다.\n\n" + "웹사이트")) {
			System.out.println(" *** serviceAlarm read check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm read check Fail ... !@#$%^&*() *** ");
			System.out.println($(".cont_div", 2).text().trim().split(" ")[7]);
			close();
		}
		System.out.println(" ! ----- serviceAlarm End ----- ! ");
  	}
    @Test(priority = 20)
  	public void mWebConnect() {
		System.out.println(" ! ----- mWebConnect Start ----- ! ");
		$("span.title", 5).click();
		$("ul > li > a > span", 34).waitUntil(visible, 10000);
		$("ul > li > a > span", 34).click();
		if($("h3", 0).text().trim().equals("모바일웹 캠페인 연결신청")) {
			System.out.println(" *** mWebGuide pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** mWebGuide pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".mo_btn_blue.mt20", 0).click();
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($(".on", 0).text().trim().equals("연결신청하기")) {
			System.out.println(" *** mWebJoinRequest pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** mWebJoinRequest pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($(".on", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("mWebJoinRequest Web null msg", "일반 웹사이트를 선택해주세요.");
		$("#web_btn").click();
		$(".webList").waitUntil(visible, 10000);
		$(".webList > li", 0).click();
		$("#web_btn").click();
		$(".btn_sty01", 0).click();
		$("#web_url").waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("mWebJoinRequest mWeb null msg", "모바일 웹사이트를 선택해주세요.");
		$(".tab_sty01 > li > a > span", 0).click();
		$("#web_btn").waitUntil(hidden, 10000);
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($(".on", 0).text().trim().equals("전체연결현황")) {
			System.out.println(" *** mWebJoinList pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** mWebJoinList pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($(".on", 0).text().trim());
			close();
		}
		if($("td", 4).text().trim().equals("이용중인 모바일웹 캠페인 연결 내역이 없습니다.")) {
			System.out.println(" *** mWebJoinList empty check Success !! *** ");
		} else {
			System.out.println(" *** mWebJoinList empty check Fail ... !@#$%^&*() *** ");
			System.out.println($(".on", 0).text().trim());
			close();
		}
		System.out.println(" ! ----- mWebConnect End ----- ! ");
  	}
    @Test(priority = 21)
  	public void download() {
		System.out.println(" ! ----- download Start ----- ! ");
		$("ul > li > a > span", 36).waitUntil(visible, 10000);
		$("ul > li > a > span", 36).click();
		if($("h3", 0).text().trim().equals("다운로드 센터")) {
			System.out.println(" *** download pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** download pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		linkCheck("service guide", "https://www.acecounter.com/www/web/files/2018/AceCounter_introduce_20181205.pdf");
		linkCheck("service manual Std", "https://www.acecounter.com/www/web/files/guide/AceCounter_User_Guide(Standard).zip");
		linkCheck("service manual Pro", "https://www.acecounter.com/www/web/files/guide/AceCounter_User_Guide(professional).zip");
		linkCheck("service manual eBiz", "https://www.acecounter.com/www/web/files/guide/AceCounter_User_Guide(eBiz).zip");
		linkCheck("service manual eCom", "https://www.acecounter.com/www/web/files/guide/AceCounter_User_Guide(eCommerce).zip");
		linkCheck("service manual mWeb", "https://www.acecounter.com/www/web/files/guide/AceCounter_User_Guide(Mobile%20Web).zip");
		linkCheck("service manual mApp", "https://www.acecounter.com/www/web/files/guide/AceCounter_User_Guide(Mobile%20App).zip");
		linkCheck("UI guide", "https://www.acecounter.com/www/web/files/guide/AceCounter_UI_Guide(2015).pdf");
		linkCheck("script Std", "https://www.acecounter.com/www/web/files/guide/script_guide_std.zip");
		linkCheck("script Pro", "https://www.acecounter.com/www/web/files/guide/script_guide_pro.zip");
		linkCheck("script eBiz", "https://www.acecounter.com/www/web/files/guide/script_guide_ebiz.zip");
		linkCheck("script eCom", "https://www.acecounter.com/www/web/files/guide/script_guide_ecom.zip");
		linkCheck("script mWeb", "https://www.acecounter.com/www/web/files/guide/script_guide_mweb.zip");
		linkCheck("mall guide", "https://www.acecounter.com/www/web/files/guide/script_guide_(shoppingmall).zip");
		linkCheck("flash guide", "https://www.acecounter.com/www/web/files/guide/script_guide_FLASH.zip");
		System.out.println(" ! ----- download End ----- ! ");
  	}
  	//@Test(priority = 99)
  	public void increaseVisit() {
  		System.out.println(" ! ----- increaseVisit Start ----- ! ");
		open("http://apzz092888.blogspot.com/");
		$(".sub24").waitUntil(visible, 10000);
		for(int i=1;i<=24;i++) {
			$(".sub" + i).scrollIntoView(false);
			$(".sub" + i).click();
	  		System.out.println("sub" + i + " 클릭");
			sleep(500);
		}
		open("http://apzz98288.egloos.com");
  		System.out.println(" ! ----- increaseVisit End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}