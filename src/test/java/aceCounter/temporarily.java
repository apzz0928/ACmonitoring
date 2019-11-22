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
		nodata = "��ȸ�� �����Ͱ� �����ϴ�.";

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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
			RemoteWebDriver driver = new RemoteWebDriver(new URL(urlToRemoteWD), cap);
			WebDriverRunner.setWebDriver(driver);
			driver.manage().window().setSize(new Dimension(1650, 1000));
		}
	}
	// �Էµ� URL ���� ���� Ȯ��
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
		if ($(".id_area").text().trim().equals("�ֿ��Ǵ� ȯ���մϴ�.")) {
			System.out.println(" *** Login Success !! *** ");
		} else {
			System.out.println(" *** Login Fail ... !@#$%^&*() *** ");
			close();
		}
		$(".btn_gray").click();
		$("h3", 0).waitUntil(visible, 10000);
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("���� ����")) {
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
		$(By.linkText("�м���ũ��Ʈ")).waitUntil(visible, 10000);
		$(By.linkText("�м���ũ��Ʈ")).click();
		$("h3", 0).waitUntil(visible, 10000);
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("�м���ũ��Ʈ")) {
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
		$(By.linkText("��û�ϱ�")).click();
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("�м���ũ��Ʈ ��ġ��û")) {
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
		$(By.linkText("�������")).click();
		confirm("��û�� ���񽺸� �������ּ���.");
		$("td", 28).click();
		$(By.linkText("�������")).click();
		for(int i=0;i<=20;i++) {
			if($("h3", 1).text().trim().equals("��������� �����ϼ���")) {
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
		$(By.linkText("��û")).click();
		confirm("��û�� �̸��� �Է����ּ���.");
		$(".input01", 0).setValue("�׽�Ʈ �Դϴ�.");
		$(By.linkText("��û")).click();
		confirm("����ó�� �Է��ϼ���.");
		$(".input01", 1).setValue("asd");
		$(By.linkText("��û")).click();
		confirm("����ó�� ���ڸ� �����մϴ�. Ȯ�����ּ���.");
		$(".input01", 1).setValue("01000000000");
		$(By.linkText("��û")).click();
		confirm("�̸����� �Է��ϼ���.");
		$(".input01", 2).setValue("apzz092888");
		$(By.linkText("��û")).click();
		confirm("�̸����ּҰ� �ùٸ��� �ʽ��ϴ�.");
		$(".input01", 2).setValue("apzz092888@daum.net");
		$(By.linkText("��û")).click();
		confirm("�������� ���� �� �̿뿡 ���� �ȳ��� �����Ͽ� �ּ���.");
		$("#c1").click();
		$(By.linkText("��û")).click();
		confirm("FTP������ �Է��� �ּ���.");
		$(".input01", 3).setValue("http://localhost");
		$(By.linkText("��û")).click();
		confirm("��Ʈ��ȣ�� �Է��� �ּ���.");
		$(".input01", 4).setValue("8080");
		$(By.linkText("��û")).click();
		confirm("���̵� �Է��� �ּ���.");
		$(".input01", 6).setValue("8080");
		$(By.linkText("��û")).click();
		confirm("��й�ȣ�� �Է��� �ּ���.");
		$(By.linkText("�м���ũ��Ʈ")).click();
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("�м���ũ��Ʈ")) {
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
		$(By.linkText("�߼�")).click();
		confirm("������ �Է����ּ���.");
		$(".input01", 0).setValue("��ũ��Ʈ ��ġ ����");
		$(By.linkText("�߼�")).click();
		confirm("�߼� �̸����� �Է����ּ���.");
		$(".input01", 1).setValue("apzz092888");
		$(By.linkText("�߼�")).click();
		confirm("���� �̸����� �Է����ּ���.");		
		$(".input01", 2).setValue("apzz092888");
		$(By.linkText("�߼�")).click();
		confirm("�̸��� �ּҰ� �ùٸ��� �ʽ��ϴ�.");
		$(".input01", 1).setValue("apzz092888@daum.net");
		$(By.linkText("�߼�")).click();
		$(".input01", 2).setValue("apzz092888@daum.net");
		$(By.linkText("�߼�")).click();
		confirm("��ġ�ȳ� ������ �߼۵Ǿ����ϴ�.");
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
		if (pageLoadCheck.equals("Daum\n" + "����")) {
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
		//���ϻ���
		$(By.linkText("����������")).click();
		sleep(1000);
		$(".select_all").waitUntil(visible, 10000);
		$(".select_all").click();
		$(".wrap_bold > .btn_del", 0).click();
		$(By.linkText("������")).click();
		sleep(1000);
		$(".select_all").waitUntil(visible, 10000);
		$(".select_all").click();
		$(".wrap_bold > .btn_permanent").click();
		sleep(2000);
		$(".check_type2").click();
		$(By.linkText("����������")).click();
		switchTo().window(0);
		System.out.println(" ! ----- my_script_codeset End ----- ! ");
	}
	@Test(priority = 2)
	public void my_member_modify() {
		System.out.println(" ! ----- my_member_modify Start ----- ! ");
		$(By.linkText("��������/����")).waitUntil(visible, 10000);
		$(By.linkText("��������/����")).click();
		System.out.println($("span", 9).text().trim());
		$(By.linkText("ȸ����������")).waitUntil(visible, 10000);
		$(By.linkText("ȸ����������")).click();
		$("h3", 0).waitUntil(visible, 10000);
		for(int i=0;i<=20;i++) {
			if($("h3", 0).text().trim().equals("ȸ����������")) {
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
		$(By.linkText("�����ϱ�")).click();
		$(By.linkText("����")).waitUntil(visible, 10000);
		$(By.linkText("����")).click();
		confirm("���� ��й�ȣ�� �Է��ϼ���.");
		$(".input01", 0).setValue("123");
		$(By.linkText("����")).click();
		confirm("���� ��й�ȣ�� ��Ȯ���� �ʽ��ϴ�.");
		$(".input01", 0).setValue(pw + A);
		$(By.linkText("����")).click();
		confirm("�� ��й�ȣ�� �Է��ϼ���.");		
		$(".input01", 1).setValue(pw + B);
		$(By.linkText("����")).click();
		confirm("�� ��й�ȣ Ȯ���� �ʿ��մϴ�.");
		$(".input01", 2).setValue(pw + C);
		$(By.linkText("����")).click();
		confirm("�� ��й�ȣ�� ��ġ ���� �ʽ��ϴ�.");		
		$(".input01", 2).setValue(pw + B);
		$(By.linkText("����")).click();
		$(By.linkText("Ȯ��")).waitUntil(visible, 10000);
		$(By.linkText("Ȯ��")).click();
		
		
		
		
		
		
		System.out.println(" ! ----- my_member_modify End ----- ! ");
	}
	@AfterClass
	public void afterTest() {
		closeWebDriver();
	}
}