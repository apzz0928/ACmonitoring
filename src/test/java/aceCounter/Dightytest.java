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
    
	Date date = new Date(); //web ȸ�����԰� app ȸ�������� �������� �����ϰ� �ϱ����� ���뼱��
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
			$(".inp", 3).setValue("�׽�Ʈ" + i + "�̸�");
			$(".inp", 4).setValue("�׽�Ʈ" + i + "ȸ���");
			$(".ico_chk").click();
			sleep(1000);
			$(".dup_button").click();
			for(int a=0;a<=5;a++) {
				if($(".regist_validate_txt").text().trim().equals("��� ������ �̸����Դϴ�.")) {
					break;
				} else {
					sleep(1000);
					$(".dup_button").click();
					System.out.println("���� �ߺ� üũ ��ư " + (a+1) + " ��° Ŭ��");
				}
			}
			$(".btn_full").click();
			$(".inp", 0).waitUntil(hidden, 10000);
			$(".btn_point").waitUntil(visible, 10000);
			$(".btn_point").click();
			open("https://demo-am.dighty.com/logout");
			System.out.println(i + " ��° ȸ������ �Ϸ�");
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
				System.out.println(i + " �� �α��� ������ ����");
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
  			System.out.println("���׸�Ʈ �߰� ������ ����");
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
  			//�̸�
  			$("#segmentSubject").setValue((i+1) + "�� �ۼ� �׽�Ʈ");
  			$(".btn-am-success", 1).click();
  			$(".btn-am-primary", 0).waitUntil(visible, 10000);
  			sleep(500);
  			$(".btn-am-primary", 0).click();
  			sleep(500);
  			if($(".nofity_wrapper").text().trim().substring(2).equals("�߰��Ǿ����ϴ�.")) {
  				System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ");
  				$(".close", 0).click();
  				$(".close", 0).waitUntil(hidden, 10000);
  			} else {
  				System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ ����");
  				System.out.println($(".nofity_wrapper").text().trim().substring(2));
  				close();
  			}	
  			System.out.println(am_date + " " + am_name + " �ϳ����� ���׸�Ʈ �߰� ����" + i);
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
		if($(".no-data").text().trim().equals("�˻� ����� �����ϴ�.")) {
			System.out.println("���׸�Ʈ ����Ʈ ������ �˻���� ���� üũ");
		} else {
			System.out.println("���׸�Ʈ ����Ʈ ������ �˻���� ���� üũ ����");
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
		if($(".nofity_wrapper").text().trim().substring(2).equals("���׸�Ʈ �̸��� �Է����ּ���.")) {
			System.out.println("���׸�Ʈ ���� ���̾� �̸� ���Է� �޼��� üũ");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("���׸�Ʈ ���� ���̾� �̸� ���Է� �޼��� üũ ����");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".align_r", 0).setValue(am_date + ".���� ���׸�Ʈ");
		$(".btn_inline", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("���� ����� �����ϼ���.")) {
			System.out.println("���׸�Ʈ ���� ���̾� ���� ��� �̼��� üũ");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("���׸�Ʈ ���� ���̾� ���� ��� �̼��� üũ ����");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		//���� ��� ���� ����
		Random generator = new Random();
		int btnNum = generator.nextInt(4);
		System.out.println((btnNum+1) + "��° ���� ��ư Ŭ��");
		$(".btn_img", btnNum).click();
		combinationMethod =  $(".btn_img", btnNum).text().trim();
		for(int i=0;i<=10;i++) {
			if($(".info_txt_bx", 2).text().trim().equals("���� ����� �����ϼ���.")) {
				sleep(1000);
				System.out.println("���׸�Ʈ ���� ���̾� ��� ��ȸ " + i + " �� ���");
			} else if ($(".info_txt_bx", 2).text().trim().equals("������ ó�� ���Դϴ�")) {
				sleep(1000);
				System.out.println("���׸�Ʈ ���� ���̾� ��� ��ȸ " + i + " �� ���");				
			} else {
				System.out.println("���׸�Ʈ ���� ���̾� ��� ��ȸ �Ϸ� " + i + " ��");
				break;
			}
		}
		segmantParameter = $(".point_green", 2).text().trim();
		$(".btn_inline", 0).click();
		sleep(500);
		$(".btn-am-primary", 0).waitUntil(visible, 10000);
		$(".btn-am-primary", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("�߰��Ǿ����ϴ�.")) {
			System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ ����");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".btn_line_sm2", 1).click();
		$(".btn_inline", 2).waitUntil(visible, 10000);
		$(".btn_inline", 2).click();
		sleep(500);
		$(".btn_inline", 6).click();
		if($(".nofity_wrapper").text().trim().substring(2).equals("���׸�Ʈ ��� ������ �Ϸ�Ǿ����ϴ�.")) {
			System.out.println("���׸�Ʈ ��� ���̾� ��� �޼��� üũ");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("���׸�Ʈ ��� ���̾� ��� �޼��� üũ ����");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".btn-am-secondary", 0).waitUntil(hidden, 10000);
		if($(".text-left", 0).text().trim().equals(am_date + ".���� ���׸�Ʈ")) {
		} else {
			System.out.println("���׸�Ʈ ��볻�� ������ ���׸�Ʈ �̸� üũ ����");
			System.out.println($(".text-left", 0).text().trim());
			close();
		}
		if($(".text-right", 0).text().trim().equals(segmantParameter)) {
			System.out.println("���׸�Ʈ ��볻�� ������ ���׸�Ʈ ����� �� üũ");
		} else {
			System.out.println("���׸�Ʈ ��볻�� ������ ���׸�Ʈ ����� �� üũ ����");
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
			System.out.println("���� ���׸�Ʈ ���� ���� ��� üũ");
		} else {
			System.out.println("���� ���׸�Ʈ ���� ���� ��� üũ ����");
			System.out.println($(".point_green", 0).text().trim());
			close();
		}
		if($(".tit", 2).text().trim().substring(13).equals(segmantParameter + " ��")) {
			System.out.println("���� ���׸�Ʈ ���� ��� üũ");
		} else {
			System.out.println("���� ���׸�Ʈ ���� ��� üũ ����");
			close();
		}
		$(".tree_item", 0).click();
		$(".btn_line_sm2", 3).click();
		$(".createRanking_new", 0).waitUntil(visible, 10000);
		$(".createRanking_new", 0).click();
		$("input", 27).waitUntil(visible, 10000);
		$("input", 27).setValue(am_date + "." + am_name + ".�����뼼�׸�Ʈ�߰�");
		sleep(100);
		$(".btn-am-success", 2).click();
		sleep(500);
		$(".btn-am-primary", 0).click();
		sleep(500);
		if($(".nofity_wrapper").text().trim().substring(2).equals("�߰��Ǿ����ϴ�.")) {
			System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ ����");
			System.out.println($(".nofity_wrapper").text().trim().substring(2));
			close();
		}
		$(".inp_serch", 0).waitUntil(visible, 10000);
		$(".btn-am-outline-info", 1).click();
		$(".btn-am-primary").waitUntil(visible, 10000);
		sleep(500);
		$(".btn-am-primary").click();
		if($(".nofity_wrapper").text().trim().substring(2).equals("������ ���׸�Ʈ�� �����Ǿ����ϴ�.")) {
			System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ");
			$(".close", 0).click();
			$(".close", 0).waitUntil(hidden, 10000);
		} else {
			System.out.println("���׸�Ʈ ���� ���̾� ���� �޼��� üũ ����");
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