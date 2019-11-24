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
			cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true); // ���ȼ��� ����
			cap.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability("requireWindowFocus", true); // ie text �Է� �ӵ� ����� ���� �κ�
			cap.setCapability(InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true); // ie ĳ�� ������ ���� �κ�
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
	// �Էµ� URL ���� ���� Ȯ��
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
		if(pageLoadCheck.equals("�����̸�")) {
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
		if($("h3", 0).text().trim().equals("���� ����")) {
			System.out.println(" *** myNotice pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myNotice pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		String[] pageLoadCheck = {"[eCom] �̱۷罺 (apzz09288.egloos.com)", "[eCom] blogspot (apzz092888.blogspot.com)"};
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
		if($("h3", 0).text().trim().equals("�м���ũ��Ʈ")) {
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
		if($("h3", 0).text().trim().equals("ȸ����������")) {
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
		alertCheck("memberInfo pw null msg", "���� ��й�ȣ�� �Է��ϼ���.");
		$(".input01", 0).setValue(pw);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo pw valCheck msg", "���� ��й�ȣ�� ��Ȯ���� �ʽ��ϴ�.");
		$(".input01", 0).setValue(pw+A);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo change pw null msg", "�� ��й�ȣ�� �Է��ϼ���.");
		$(".input01", 1).setValue(pw);
		$(".btn_pack.large", 0).click();
		alertCheck("memberInfo change pw check msg", "�� ��й�ȣ Ȯ���� �ʿ��մϴ�.");
		$(".input01", 2).setValue(pw);
		$(".btn_pack.large", 0).click();
		$(".bx_tip").waitUntil(visible, 10000);
		$(".btn_pack.large", 2).waitUntil(hidden, 10000);
		if($(".bx_tip").text().trim().equals("��й�ȣ�� ����Ǿ����ϴ�.")) {
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
		if($(".bx_tip").text().trim().equals("��й�ȣ�� ����Ǿ����ϴ�.")) {
			System.out.println(" *** memberInfo password restoration check Success !! *** ");
		} else {
			System.out.println(" *** memberInfo password restoration check Fail ... !@#$%^&*() *** ");
			System.out.println($(".bx_tip").text().trim() + ".");
			close();
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).setValue("");
		$(".btn_pack.large", 1).click();
		alertCheck("memberInfo name null msg", "����� �̸��� �Է��Ͽ��ּ���");
		$(".input01", 0).setValue("�����̸�");
		$(".input01", 1).setValue("");
		$(".btn_pack.large", 1).click();
		alertCheck("memberInfo companyName null msg", "ȸ����� �Է��Ͽ��ּ���.");
		$(".input01", 1).setValue("����ȸ���");
		$(".input01", 5).setValue("");
		$(".btn_pack.large", 1).click();
		alertCheck("memberInfo email null msg", "����� �̸����ּҸ� �Է��Ͽ��ּ���");
		$(".input01", 5).setValue("apzz0928888@daum.net");
		$(".btn_pack.large", 1).click();
		$(".input01", 0).waitUntil(hidden, 10000);
		String[] dataCheck = {"�����̸�", "����ȸ���"};
		for(int i=4;i<=5;i++) {
			if($("td", i).text().trim().equals(dataCheck[i-4])) {
				System.out.println(" *** memberInfo modify " + (i-4) + " check Success !! *** ");
			} else {
				System.out.println(" *** memberInfo modify " + (i-4) + " check Fail ... !@#$%^&*() *** ");
				System.out.println($("td", i).text().trim());
				close();
			}
			if(i==4) {
				dataCheck[0] = "�����̸�";
			} else {
				dataCheck[1] = "����ȸ���";
			}
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".input01", 0).setValue("�����̸�");
		$(".input01", 1).setValue("����ȸ���");
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
		if($("h3", 0).text().trim().equals("������������")) {
			System.out.println(" *** serviceInfo pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceInfo pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo change null msg", "����� ������ �����ϴ�.");
		$(".btn_del", 0).click();
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo domain null msg", "�������� �Է��ϼ���");
		refresh();
		$(".input01", 2).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo siteName null msg", "������Ʈ�̸��� �Է��ϼ���");
		$(".input01", 2).setValue("blogger");
		$(".input01", 3).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceInfo defaultPage null msg", "������Ʈ �⺻�������� �Է��ϼ���");
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
		if($("h3", 0).text().trim().equals("���� �߰�")) {
			System.out.println(" *** addService pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** addService pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("addService domain null msg", "������Ʈ ������ �Է��� �ּ���.");
		$(".input01", 0).setValue(id_date + ".com");
		$(".btn_sty01", 0).click();
		sleep(500);
		$(".btn_del", 0).waitUntil(visible, 10000);
		$("#domain_1").waitUntil(visible, 10000);
		$(".input01", 2).setValue("");
		$(".input01", 3).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("addService siteName null msg", "������Ʈ�̸��� �Է��ϼ���");
		$(".input01", 2).setValue(id_date);
		$(".btn_pack.large", 0).click();
		alertCheck("addService defaultPage null msg", "������Ʈ �⺻�������� �Է��ϼ���");
		$(".input01", 3).setValue("index.html");
		$(".btn_pack.large", 0).click();
		alertCheck("addService class null msg", "������Ʈ �з��� �����ϼ���");
		$(".select01", 0).selectOption("��Ÿ");
		$(".btn_pack.large", 0).click();
		alertCheck("addService add confirm msg", "���̽�ī���� ���񽺸� ��û�Ͻðڽ��ϱ�?");
		alertCheck("addService chaptcha valCheck msg", "�����ڵ尡 �Էµ��� �ʾҽ��ϴ�.");
		$(".input01", 0).waitUntil(visible, 10000);
		$("ul > li > a > span", 7).waitUntil(visible, 10000);
		if($("h3", 0).text().trim().equals("���� �߰�")) {
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
		if($("h3", 0).text().trim().equals("�ΰ����� ����")) {
			System.out.println(" *** subAdmin pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** subAdmin pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin id null msg", "���̵� �Է����ּ���.");
		$(".input01", 0).setValue(id_date);
		$(".btn_pack.large", 1).click();		
		alertCheck("subAdmin pw null msg", "��й�ȣ�� �Է����ּ���.");
		$(".input01", 1).setValue(pw+B);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin change pw null msg", "��й�ȣȮ���� �Է��Ͽ� �ּ���");
		$(".input01", 2).setValue(pw+A);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin change pw check msg", "��й�ȣ�� ��ġ���� �ʽ��ϴ�");
		$(".input01", 2).setValue(pw+B);
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin name null msg", "����� �̸��� �Է��Ͽ��ּ���");
		$(".input01", 3).setValue("�ΰ������̸�");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin email null msg", "����� �̸����ּҸ� �Է��Ͽ��ּ���");
		$(".input01", 4).setValue("apzz0928888");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin email valCheck msg", "�̸����ּҰ� �ùٸ��� �ʽ��ϴ�.");
		$(".input01", 4).setValue("apzz0928888@daum.net");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin add msg", "�ΰ����� ����� �Ϸ�Ǿ����ϴ�");		
		$("td > img", 1).waitUntil(visible, 10000);
		String[] dataCheck = {id_date, "�ΰ������̸�"};
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
		$(".input01", 3).setValue("�ΰ������̸�����");
		$(".btn_pack.large", 1).click();
		alertCheck("subAdmin modify info msg", "�ΰ����� ������ �Ϸ�Ǿ����ϴ�.");	
		if($("td", 6).text().trim().equals("�ΰ������̸�����")) {
			System.out.println(" *** subAdmin modify info check Success !! *** ");
		} else {
			System.out.println(" *** subAdmin modify info check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 6).text().trim());
			close();
		}
		$("td > img", 2).waitUntil(visible, 10000);
		$("td > img", 2).click();
		alertCheck("subAdmin del confirm msg", "������ �����Ͻðڽ��ϱ�??");
		alertCheck("subAdmin del msg", "�ΰ����� ������ �����Ǿ����ϴ�.");
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($("td", 3).text().trim().equals("��ϵ� �ΰ����ڰ� �����ϴ�.")) {
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
		if($("h3", 0).text().trim().equals("��������")) {
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
		if($("h3", 0).text().trim().equals("���� ������û")) {
			System.out.println(" *** leaveService pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** leaveService pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_sty01").click();
		alertCheck("leaveService pw null msg", "��й�ȣ�� �Է����ּ���.");
		$(".input01").setValue(pw);
		$(".btn_sty01").click();		
		alertCheck("leaveService pw valCheck msg", "�н����尡 ��ġ���� �ʽ��ϴ�. �ٽ� �Է��Ͽ� �ֽʽÿ�.");
		$(".input01").setValue(pw+A);
		$(".btn_sty01").click();
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService site null msg", "��û�� ����Ʈ�� �������ּ���.");
		$(By.name("checkbox_1")).click();
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService name null msg", "��û�� �̸��� �Է��� �ּ���.");
		$(".input01", 0).setValue("�׽�Ʈ �̸�");
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService email null msg", "�̸����� �Է��ϼ���.");
		$(".input01", 3).setValue("test");
		$(".btn_pack.large", 0).click();
		alertCheck("leaveService email valCheck msg", "�̸����ּҰ� �ùٸ��� �ʽ��ϴ�.");		
		$(".input01", 3).setValue("test@mail.com");
		$(".btn_pack.large", 0).click();
		if(switchTo().alert().getText().equals("������û�Ͻðڽ��ϱ�?")) {
			dismiss("������û�Ͻðڽ��ϱ�?");
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
		if($("h3", 0).text().trim().equals("�����ݰ���")) {
			System.out.println(" *** extendCharge pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge site null msg", "������Ʈ�� �����Ͽ� �ּ���");
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
		if($("td", 28).text().trim().equals("165,000 ��")) {
			System.out.println(" *** extendCharge creditCard check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge creditCard check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 28).text().trim());
			close();		
		}
		$(".input01", 0).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge creditCard name null msg", "����ڸ��� �Է����ּ���.");
		$(".input01", 0).setValue("�����̸�");
		$(".input01", 1).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge creditCard phone null msg", "�޴���ȭ��ȣ�� �Է����ּ���.");
		$(".input01", 1).setValue("010-9743-0928");
		$(".input01", 2).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge creditCard email null msg", "�̸����� �Է����ּ���.");
		$("input[name=pay_mode_select]", 1).click();
		$(".btn_pack.medium").waitUntil(visible, 10000);
		if($(".pnt04", 1).text().trim().equals("165,000��")) {
			System.out.println(" *** extendCharge virtual account check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge virtual account check Fail ... !@#$%^&*() *** ");
			System.out.println($(".pnt04", 1).text().trim());
			close();		
		}
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account bankAccount null msg", "������¸� Ȯ�����ּ���.");
		$(".form2").selectOption("�츮����(20)");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account name null msg", "�Ա��ڸ��� �Է����ּ���.");
		$(".input01", 0).setValue("�Ա��ڸ�");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account phone null msg", "�޴���ȭ��ȣ�� �Է����ּ���.");
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account email null msg", "�̸����ּҸ� �Է����ּ���.");
		$(".input01", 5).setValue("test");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();	
		alertCheck("extendCharge virtual account email null msg", "�̸����ּҰ� �ùٸ��� �ʽ��ϴ�.");
		$(".input01", 5).setValue("test@mail.com");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Business license null msg", "����� ��ȣ�� �Է����ּ���.");
		$(".btn_pack.medium").click();
		alertCheck("extendCharge virtual account Business license defaultCheck msg", "����� ������ �����ϴ�.");
		$(".input01", 6).setValue("000");
		$(".input01", 7).setValue("00");
		$(".input01", 8).setValue("00000");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Company name null msg", "ȸ����� �Է��� �ּ���.");	
		$(".input01", 9).setValue("ȸ���");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Boss name null msg", "��ǥ�ڸ��� �Է����ּ���.");
		$(".input01", 10).setValue("��ǥ�ڸ�");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Business condition null msg", "���¸� �Է����ּ���.");
		$(".input01", 12).setValue("����");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Company address null msg", "ȸ���ּҸ� �Է����ּ���.");
		$(".input01", 11).setValue("ȸ���ּ�");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account Company type null msg", "������ �Է����ּ���.");
		$(".input01", 13).setValue("����");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account In charge name null msg", "����ڸ��� �Է����ּ���.");
		$(".input01", 14).setValue("����ڸ�");
		$(".btn_pack.large", 0).scrollIntoView(false);
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge virtual account In charge phone null msg", "����� ����ó�� �Է����ּ���.");
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
		if($(".pnt04", 1).text().trim().equals("165,000 ��")) {
			System.out.println(" *** extendCharge no passbook check Success !! *** ");
		} else {
			System.out.println(" *** extendCharge no passbook check Fail ... !@#$%^&*() *** ");
			System.out.println($(".pnt04", 1).text().trim());
			close();		
		}
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook bankAccount null msg", "������¸� Ȯ�����ּ���.");
		$(".form2").selectOption("�츮����(742-249776-13-001)");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook name null msg", "�Ա��ڸ��� �Է����ּ���.");
		$(".input01", 0).setValue("�Ա��ڸ�");
		$(".input01", 2).setValue("");
		$(".input01", 3).setValue("");
		$(".input01", 4).setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook year null msg", "�Ա��� ������ �Է����ּ���.");	
		$(".input01", 2).setValue("0000");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook month null msg", "�Ա��� ���� �Է����ּ���.");
		$(".input01", 3).setValue("00");
		$(".btn_pack.large", 0).click();
		alertCheck("extendCharge no passbook day null msg", "�Ա����� �Է����ּ���.");
		$("span.title", 2).click();
		$(".active").waitUntil(visible, 10000);
		System.out.println(" ! ----- extendCharge End ----- ! ");
  	}
    @Test(priority = 10)
  	public void additionalCharge() {
		System.out.println(" ! ----- additionalCharge Start ----- ! ");
		$("ul > li > a > span", 15).waitUntil(visible, 10000);
		$("ul > li > a > span", 15).click();
		if($("h3", 0).text().trim().equals("�߰���ݰ���")) {
			System.out.println(" *** additionalCharge pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** additionalCharge pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("additionalCharge service null msg", "��û�� ���񽺸� �������ּ���.");
		if($("td", 12).text().trim().equals("�߰���� �̰��� ������ �����ϴ�.")) {
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
		if($("h3", 0).text().trim().equals("���ݰ�꼭")) {
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
		if($("h3", 0).text().trim().equals("����������ȸ")) {
			System.out.println(" *** myPayInfo pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** myPayInfo pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		if($("td", 3).text().trim().equals("�̳������ �����ϴ�.")) {
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
		if($("h3", 0).text().trim().equals("������ �Աݾȳ�")) {
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
		alertCheck("myBankInfo bank null msg", "�Ա��Ͻ� ������ �����Ͽ� �ּ���");
		$(".select01").selectOption("�츮����(742-249776-13-001)");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo name null msg", "�Ա��Ͻ� �� ������ �Է��Ͽ� �ּ���");	
		$(".input01", 0).setValue("�Ա��ڸ�");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo phone null msg", "�Ա��Ͻ� �� ����ó�� �Է��Ͽ� �ּ���");	
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo year null msg", "�Ա��Ͻ� ������ �Է��Ͽ� �ּ���");
		$(".input01", 2).setValue("0000");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo month null msg", "�Ա��Ͻ� ������ �Է��Ͽ� �ּ���");
		$(".input01", 3).setValue("00");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo day null msg", "�Ա��Ͻ� ������ �Է��Ͽ� �ּ���");
		$(".input01", 4).setValue("00");
		$(".btn_pack.large", 0).click();
		alertCheck("myBankInfo day null msg", "�Ա��Ͻ� �ݾ��� �Է��Ͽ� �ּ���");
		System.out.println(" ! ----- myBankInfo End ----- ! ");
  	}
    @Test(priority = 14)
  	public void installScript() {
		System.out.println(" ! ----- installScript Start ----- ! ");
		$("span.title", 3).click();
		$("ul > li > a > span", 22).waitUntil(visible, 10000);
		$("ul > li > a > span", 22).click();
		if($("h3", 0).text().trim().equals("�м���ũ��Ʈ ��ġ��û")) {
			System.out.println(" *** installScript pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** installScript pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("installScript service null msg", "��û�� ���񽺸� �������ּ���.");
		$(By.name("radio_ck"), 0).click();
		$(".btn_pack.large", 0).click();
		$(".input01", 0).waitUntil(visible, 10000);
		$("img", 18).click();
		$(".btn_pack.large", 0).click();
		alertCheck("installScript name null msg", "��û�� �̸��� �Է����ּ���.");
		$(".input01", 0).setValue("��û�� �̸�");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript phone null msg", "����ó�� �Է��ϼ���.");
		$(".input01", 1).setValue("aaa");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript phone valCheck msg", "����ó�� ���ڸ� �����մϴ�. Ȯ�����ּ���.");
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript email null msg", "�̸����� �Է��ϼ���.");
		$(".input01", 2).setValue("�����ּ�");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript email valCheck msg", "�̸����ּҰ� �ùٸ��� �ʽ��ϴ�.");
		$(".input01", 2).setValue("�����ּ�@mail.com");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript agree null msg", "�������� ���� �� �̿뿡 ���� �ȳ��� �����Ͽ� �ּ���.");
		$("#c1").click();
		$(".btn_pack.large", 0).click();
		alertCheck("installScript FTP URL null msg", "FTP������ �Է��� �ּ���.");
		$(".input01", 3).setValue("ftp");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript port null msg", "��Ʈ��ȣ�� �Է��� �ּ���.");
		$(".input01", 4).setValue("1234");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript ID null msg", "���̵� �Է��� �ּ���.");
		$(".input01", 6).setValue("���̵�");
		$(".btn_pack.large", 0).click();
		alertCheck("installScript PW null msg", "��й�ȣ�� �Է��� �ּ���.");
		System.out.println(" ! ----- installScript End ----- ! ");
  	}
    @Test(priority = 15)
  	public void serviceModify() {
		System.out.println(" ! ----- serviceModify Start ----- ! ");
		$("ul > li > a > span", 23).waitUntil(visible, 10000);
		$("ul > li > a > span", 23).click();
		if($("h3", 0).text().trim().equals("���񽺹��� �����û")) {
			System.out.println(" *** serviceModify pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceModify pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify site null msg", "��û�� ����Ʈ�� �������ּ���.");
		$(By.name("checkbox_0")).click();
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify name null msg", "�̸��� �Է��ϼ���.");
		$(".input01", 0).setValue("��û�� �̸�");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify phone null msg", "����ó�� �Է��ϼ���.");
		$(".input01", 1).setValue("010-0000-0000");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify email null msg", "�̸����� �Է��ϼ���.");
		$(".input01", 2).setValue("email");
		$(".btn_pack.large", 0).click();
		alertCheck("serviceModify email valCheck msg", "�̸����ּҰ� �ùٸ��� �ʽ��ϴ�.");
		System.out.println(" ! ----- serviceModify End ----- ! ");
  	}
    @Test(priority = 16)
  	public void Report() {
		System.out.println(" ! ----- Report Start ----- ! ");
		$("ul > li > a > span", 24).waitUntil(visible, 10000);
		$("ul > li > a > span", 24).click();
		if($("h3", 0).text().trim().equals("��ฮ��Ʈ �߼ۼ���")) {
			System.out.println(" *** Report pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** Report pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$("#summary_mail").setValue("");
		$(".btn_pack.large", 0).click();
		alertCheck("Report summary email null msg", "�����̸����� �Է����ּ���!");
		$("#summary_mail").setValue("apzz0928888@daum.net");
		$(".btn_pack.large", 0).click();
		alertCheck("Report send email msg", "��ฮ��Ʈ�� �߼۵Ǿ����ϴ�.");
		js("window.open('https://logins.daum.net/accounts/signinform.do?url=https%3A%2F%2Fmail.daum.net%2F');");
		//�������� ������ ��Ŀ�� ����
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
		//�������������� �̵� �� ���� ����
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
		alertCheck("Report modify msg", "�����Ͻ� ������ ����Ǿ����ϴ�.\n" + "���������� ���Ϻ��� �ݿ��˴ϴ�.");
		$(".btn_pack.large", 1).waitUntil(visible, 10000);
		for(int i=7;i<=13;i++) {
			$("td > input", i).click();
		}
		$(".btn_pack.large", 1).click();
		alertCheck("Report save msg", "�����Ͻ� ������ ����Ǿ����ϴ�.\n" + "���������� ���Ϻ��� �ݿ��˴ϴ�.");		
		$(".btn_pack.large", 1).waitUntil(visible, 10000);	
		System.out.println(" ! ----- Report End ----- ! ");
  	}
    @Test(priority = 17)
  	public void Monitoring() {
		System.out.println(" ! ----- Monitoring Start ----- ! ");
		$("ul > li > a > span", 25).waitUntil(visible, 10000);
		$("ul > li > a > span", 25).click();
		if($("h3", 0).text().trim().equals("�˸����� �߼ۼ���")) {
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
			$(".select01", i).selectOption("7�� ���");
		}
		for(int i=1;i<=8;i++) {
			$(".input01", i).setValue("0");
		}
		$(".btn_pack.large", 0).click();
		alertCheck("Monitoring modify confirm msg", "�����Ͻ� �˸� ������ �����ϴ� \n" + "�����Ͻðڽ��ϱ�?");
		alertCheck("Monitoring modify msg", "�����Ͻ� ������ ����Ǿ����ϴ�. \n\n" + "���������� ���Ϻ��� �ݿ��˴ϴ�.");		
		$(".btn_pack.large", 0).waitUntil(visible, 10000);		
		for(int i=0;i<=8;i++) {
			$("label > input", i).click();
		}
		for(int i=1;i<=8;i++) {
			$(".select01", i).selectOption("������ ���� ����");
		}
		for(int i=1;i<=8;i++) {
			$(".input01", i).setValue("100");
		}
		$(".btn_pack.large", 0).click();
		alertCheck("Monitoring save confirm msg", "�����Ͻðڽ��ϱ�?");
		alertCheck("Monitoring modify msg", "�����Ͻ� ������ ����Ǿ����ϴ�. \n\n" + "���������� ���Ϻ��� �ݿ��˴ϴ�.");		
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		System.out.println(" ! ----- Monitoring End ----- ! ");
  	}
    @Test(priority = 18)
  	public void serviceNotice() {
		System.out.println(" ! ----- serviceNotice Start ----- ! ");
		$("span.title", 4).click();
		$("ul > li > a > span", 29).waitUntil(visible, 10000);
		$("ul > li > a > span", 29).click();
		if($("h3", 0).text().trim().equals("���� ����")) {
			System.out.println(" *** serviceNotice pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceNotice pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("1234567890");
		$("img", 14).click();
		$(".next").waitUntil(hidden, 10000);
		if($("td", 3).text().trim().equals("�����Ͱ� �����ϴ�.")) {
			System.out.println(" *** serviceNotice empty search check Success !! *** ");
		} else {
			System.out.println(" *** serviceNotice empty search check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 3).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("����");
		$("img", 14).click();
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("���̽�ī���Ͱ� ���Ӱ� ����Ǿ����ϴ�")) {
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
		if($("h3", 0).text().trim().equals("���� �˸�����")) {
			System.out.println(" *** serviceAlarm pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("1234567890");
		$("img", 14).click();
		$(".next").waitUntil(hidden, 10000);
		if($("td", 3).text().trim().equals("�����Ͱ� �����ϴ�.")) {
			System.out.println(" *** serviceAlarm empty search check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm empty search check Fail ... !@#$%^&*() *** ");
			System.out.println($("td", 3).text().trim());
			close();
		}
		$("input[name=search_keyword]", 1).setValue("����");
		$("img", 14).click();
		$(".left", 3).waitUntil(visible, 10000);
		if($(".left", 3).text().trim().equals("[����] ���Ἥ�� ���Ⱓ�� ���� ����˴ϴ�.")) {
			System.out.println(" *** serviceAlarm search check Success !! *** ");
		} else {
			System.out.println(" *** serviceAlarm search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 3).text().trim());
			close();
		}
		$(".left > a", 2).click();
		$(".cont_div", 2).waitUntil(visible, 10000);
		if($(".cont_div", 2).text().trim().split(" ")[7].equals("����˴ϴ�.\n\n" + "������Ʈ")) {
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
		if($("h3", 0).text().trim().equals("������� ķ���� �����û")) {
			System.out.println(" *** mWebGuide pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** mWebGuide pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h3", 0).text().trim());
			close();
		}
		$(".mo_btn_blue.mt20", 0).click();
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($(".on", 0).text().trim().equals("�����û�ϱ�")) {
			System.out.println(" *** mWebJoinRequest pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** mWebJoinRequest pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($(".on", 0).text().trim());
			close();
		}
		$(".btn_pack.large", 0).click();
		alertCheck("mWebJoinRequest Web null msg", "�Ϲ� ������Ʈ�� �������ּ���.");
		$("#web_btn").click();
		$(".webList").waitUntil(visible, 10000);
		$(".webList > li", 0).click();
		$("#web_btn").click();
		$(".btn_sty01", 0).click();
		$("#web_url").waitUntil(visible, 10000);
		$(".btn_pack.large", 0).click();
		alertCheck("mWebJoinRequest mWeb null msg", "����� ������Ʈ�� �������ּ���.");
		$(".tab_sty01 > li > a > span", 0).click();
		$("#web_btn").waitUntil(hidden, 10000);
		$(".btn_pack.large", 0).waitUntil(visible, 10000);
		if($(".on", 0).text().trim().equals("��ü������Ȳ")) {
			System.out.println(" *** mWebJoinList pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** mWebJoinList pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($(".on", 0).text().trim());
			close();
		}
		if($("td", 4).text().trim().equals("�̿����� ������� ķ���� ���� ������ �����ϴ�.")) {
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
		if($("h3", 0).text().trim().equals("�ٿ�ε� ����")) {
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
	  		System.out.println("sub" + i + " Ŭ��");
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