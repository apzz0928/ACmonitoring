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
    
	Date date = new Date(); //web ȸ�����԰� app ȸ�������� �������� �����ϰ� �ϱ����� ���뼱��
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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
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
		System.out.println("�α���");
		$(".nav-link", 1).waitUntil(visible, 10000);
		$(".nav-link", 1).click();
		$(".ico_check", 0).waitUntil(visible, 10000);
		System.out.println("���׸�Ʈ ��� ������ ����");
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
		if($$("span").last().text().trim().equals("���׸�Ʈ �̸��� �Է����ּ���.")) {
			System.out.println("���׸�Ʈ ���� �̸� ���Է� üũ");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("���׸�Ʈ ���� �̸� ���Է� üũ ����");
	  		close();
		}
		$("#combineSegmentSubject").setValue(am_date + " ���� �׽�Ʈ");
		$(".tit", 1).click();
		sleep(1000);
		$(".bg_gray", 0).click();
		$$("span").last().waitUntil(visible, 100000);
		if($$("span").last().text().trim().equals("���� ����� �����ϼ���.")) {
			System.out.println("���׸�Ʈ ���� ��� �̼��� üũ");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("���׸�Ʈ ���� ��� �̼��� üũ ����");
	  		close();
		}
		$(".tit", 1).click();
		$(".btn", 0).click();
		for(int i=0;i<=10;i++) {
			if($(".info_txt_bx", 2).text().trim().equals("���� ����� �����ϼ���.") || $(".info_txt_bx", 2).text().trim().equals("������ ó�� ���Դϴ�") ) {
				System.out.println("���׸�Ʈ ���� ��� ��ȸ�� " + (i+1) + "��");
				System.out.println($(".info_txt_bx", 2).text().trim());
			} else {
				System.out.println("���׸�Ʈ ���� ��� ��ȸ �Ϸ�");
				System.out.println($(".info_txt_bx", 2).text().trim());
				break;
			}
		}
		$(".bg_gray", 0).click();
		$(".bg_gray", 3).waitUntil(visible, 10000);
		sleep(500);
		$(".bg_gray", 3).click();
		$("span", 29).waitUntil(visible, 100000);
		if($("span", 29).text().trim().equals("�߰��Ǿ����ϴ�.")) {
			System.out.println("���׸�Ʈ ���� ��� �Ϸ�");
			$("span", 29).waitUntil(hidden, 100000);
		} else {
			System.out.println("���׸�Ʈ ���� ��� �Ϸ� ����");
	  		close();
		}
		if($(".tree_link", 0).text().trim().equals(am_date + " ���� �׽�Ʈ")) {
			System.out.println("���׸�Ʈ ���� ��� Ȯ�� �Ϸ�");
		} else {
			System.out.println("���׸�Ʈ ���� ��� Ȯ�� ����");
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
		if($("span", 29).text().trim().equals("���׸�Ʈ ��� ������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println("���� ���׸�Ʈ ��� ���� �Ϸ�");
			$("span", 29).waitUntil(hidden, 100000);
		} else {
			System.out.println("���� ���׸�Ʈ ��� ���� ����");
	  		close();
		}
		if($("td", 2).text().trim().equals(am_date + " ���� �׽�Ʈ")) {
			System.out.println("���� ���׸�Ʈ ��� Ȯ�� �Ϸ�");
		} else {
			System.out.println("���� ���׸�Ʈ ��� Ȯ�� ����");
			close();
		}
		$(".nav-link", 1).click();
		$(".l_green", 0).waitUntil(visible, 10000);
		$(".l_green", 0).click();
		$(".bg_gray", 2).waitUntil(visible, 10000);
		$(".bg_gray", 2).click();
		$$("span").last().waitUntil(visible, 100000);
		if($$("span").last().text().trim().equals("Ȯ�� ���׸�Ʈ �̸��� �Է����ּ���.")) {
			System.out.println("���׸�Ʈ Ȯ�� �̸� ���Է� üũ");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("���׸�Ʈ Ȯ�� �̸� ���Է� üũ ����");
	  		close();
		}
		$("#expandSegmentSubject").setValue(am_date + " ���� ���׸�Ʈ Ȯ��");
		$(".bg_gray", 2).click();
		$(".bg_gray", 3).waitUntil(visible, 10000);
		sleep(500);
		$(".bg_gray", 3).click();
		if($$("span").last().text().trim().equals("���� ���׸�Ʈ Ȯ�� ��û�� �Ϸ�Ǿ����ϴ�.")) {
			System.out.println("���� ���׸�Ʈ Ȯ�� ��û �Ϸ�");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("���� ���׸�Ʈ Ȯ�� ��û ����");
	  		close();
		}
		if($(".tree_link", 0).text().trim().equals(am_date + " ���� �׽�Ʈ")) {
			System.out.println("���� ���׸�Ʈ Ȯ�� ��û Ȯ�� �Ϸ�");
		} else {
			System.out.println("���� ���׸�Ʈ Ȯ�� ��û Ȯ�� ����");
	  		close();
		}
		$(".bg_green", 0).click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		$(".close-btn", 0).click();
		$(".bg_green", 0).click();
		$(".bg_gray", 0).waitUntil(visible, 10000);
		if($(".pop_tit", 0).text().trim().equals("Ȯ�� ���׸�Ʈ ���")) {
			System.out.println("Ȯ�� ���׸�Ʈ ��� ���̾� Ȯ�� �Ϸ�");			
		} else {
			System.out.println("Ȯ�� ���׸�Ʈ ��� ���̾� Ȯ�� ����");
	  		close();
		}
		$(".gray_line", 0).click();
		$(".bg_gray", 0).waitUntil(hidden, 10000);
		$(".btn_line_sm2").click();
		$(".bg_gray", 1).waitUntil(hidden, 10000);		
		sleep(500);
		$(".bg_gray", 1).click();
		if($$("span").last().text().trim().equals("������ Ȯ�� ���׸�Ʈ�� �����Ǿ����ϴ�.")) {
			System.out.println("Ȯ�� ���׸�Ʈ ���� �Ϸ�");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("Ȯ�� ���׸�Ʈ ���� ����");
	  		close();
		}
		if($(".no-data", 0).text().trim().equals("���׸�Ʈ Ȯ�� ��û �̷��� �����ϴ�.")) {
			System.out.println("Ȯ�� ���׸�Ʈ ���� Ȯ�� �Ϸ�");
			$$("span").last().waitUntil(hidden, 100000);
		} else {
			System.out.println("Ȯ�� ���׸�Ʈ ���� Ȯ�� ����");
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
		System.out.println("�⺻ ���׸�Ʈ ���");
		sleep(5000);
		$(".nav-link", 1).click();
		$(".ico_check", 0).waitUntil(visible, 10000);
		$(".l_green", 0).click();
		sleep(500);
		$("#expandSegmentSubject").setValue(am_date + " �⺻ ���׸�Ʈ Ȯ��");
		$(".bg_gray", 2).click();
		sleep(500);
		$(".bg_gray", 3).click();
		System.out.println("�⺻ Ȯ�� ���׸�Ʈ ���");
		sleep(5000);
		$(".btn_line_sm2", 1).click();
		sleep(500);
		$(".bg_gray", 1).click();
		sleep(500);
		System.out.println("�⺻ Ȯ�� ���׸�Ʈ ����");
  		System.out.println(" ! ----- defaultSegment test End ----- ! ");
  	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}