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
  	public void IPFiltering() {
		System.out.println(" ! ----- IPFiltering Start ----- ! ");
		$(".manager_b", 0).click();
		if($("h2").text().trim().equals("IP���͸�")) {
			System.out.println(" *** IPFiltering pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** IPFiltering pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().equals("��ϵ� IP�� �����ϴ�.\n" + "�߰��� Ŭ���� ���͸��� IP�� ����ϼ���.")) {
			System.out.println(" *** IPFiltering list empty-data check Success !! *** ");			
		} else {
			System.out.println(" *** IPFiltering list empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple").click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("IPFiltering null msg", "IP�� �Է��ϼ���.");
		$(".numeric", 0).setValue("127");
		$(".numeric", 1).setValue("0");
		$(".numeric", 2).setValue("0");
		$(".numeric", 3).setValue("1");
		$(".btn_blue", 1).click();
		alertCheck("IPFiltering add msg", "IP�� ��ϵǾ����ϴ�.");
		$(".empty_td").waitUntil(hidden, 10000);
		$(".left", 1).waitUntil(visible, 10000);
		$(".input_txt", 0).setValue("12345");
		$(".btn_blue", 0).click();
		$(".left", 1).waitUntil(hidden, 10000);
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
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
		alertCheck("IPFiltering del confirm msg", "������ IP�� �����Ͻðڽ��ϱ�?");
		alertCheck("IPFiltering del msg", "IP�� �����Ǿ����ϴ�.");
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("��ϵ� IP�� �����ϴ�.\n" + "�߰��� Ŭ���� ���͸��� IP�� ����ϼ���.")) {
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
		if($("h2").text().trim().equals("������ �� ��ȯ������")) {
			System.out.println(" *** convertPage pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** convertPage pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		String[] tableDataCheck = {"��ȯ-��Ÿ3", "��ȯ-��Ÿ2", "��ȯ-��Ÿ1", "��ȯ-��û", "��ȯ-����", "��ȯ-�ֹ�", "��ȯ-����"};
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** convertPage setting list empty-search check Success !! *** ");			
		} else {
			System.out.println(" *** convertPage setting list empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
	    $(By.name("list_type")).selectOption("��������Ʈ");
		$(".btn_purple").waitUntil(hidden, 10000);
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** convertPage setting list empty-search check Success !! *** ");
		} else {
			System.out.println(" *** convertPage setting list empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".input_txt").setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		if($(".left", 2).text().trim().equals("��ȯ-�ֹ�")) {
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
		if($("h2").text().trim().equals("������ �� ��������")) {
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
	    $(By.name("list_type")).selectOption("�̼��� ����Ʈ");
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** pageName not set list empty check Success !! *** ");
		} else {
			System.out.println(" *** pageName not set list empty check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
	    $(By.name("list_type")).selectOption("���� ����Ʈ");
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
	    $(By.name("list_type")).selectOption("���� ����Ʈ");
		$(".empty_td").waitUntil(visible, 10000);
	    $(".input_txt").setValue("");
		$(".btn_blue").click();
		if($(".empty_td").text().trim().substring(0, 14).equals("������ �������� �����ϴ�.")) {
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
		if($("h2").text().trim().equals("������ �� ����������")) {
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) { 
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
		if($("h2").text().trim().equals("������ �� �޴�")) {
			System.out.println(" *** menu pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** menu pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		$("#newmenu_1").setValue("�޴��߰��׽�Ʈ");
		$(".btn_purple", 2).click();
		alertCheck("manageMenu add msg", "�޴��� ��ϵǾ����ϴ�.");
		$("#subtitle_66mU64m07LaU6rCA7YWM7Iqk7Yq4").waitUntil(visible, 10000);
		$("#subtitle_66mU64m07LaU6rCA7YWM7Iqk7Yq4").click();
		$(".blueB > img").waitUntil(visible, 10000);
		$(".blueB > img").click();
		alertCheck("manageMenu del confirm msg", "�޴��� �����Ͻðڽ��ϱ�?\n" + "�޴��� �����ϸ�, �޴��� ���� �м��� �����˴ϴ�.");
		alertCheck("manageMenu del msg", "�޴��� �����Ǿ����ϴ�.");
		$("#subtitle_66mU64m07LaU6rCA7YWM7Iqk7Yq4").waitUntil(hidden, 10000);		
		if($("span", 243).text().trim().equals("��ȯ�׽�Ʈ")) {
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) { 
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
		if($(".left", 15).text().trim().equals("��ȯ�׽�Ʈ")) { 
			System.out.println(" *** addPattern pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** addPattern pageLoad check Fail ... !@#$%^&*() *** "); 
			System.out.println($(".left", 1).text().trim()); close(); 
		}
		$("#pattern_1").setValue("/test/*");
		$(".btn_purple", 4).click();
		alertCheck("addPattern msg", "������ ��ϵǾ����ϴ�.\n"+ "��ϵ� ������ ���ϰ������� Ȯ���Ͻ� �� �ֽ��ϴ�.");
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) { 
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
		alertCheck("patternManage del confirm msg", "������ ������ �����Ͻðڽ��ϱ�?\n" + "(�̹� �޴��� ��ϵ� �������� �����˴ϴ�.)");
		alertCheck("patternManage del msg", "�����Ǿ����ϴ�.");
		if($(".empty_td").text().trim().substring(0, 13).equals("��ϵ� ������ �����ϴ�.")) { 
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
		if($("h2").text().trim().equals("������ �� ��ũŬ��")) {
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) { 
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
		if($("h2").text().trim().equals("������ �� ���Է�")) {
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) { 
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
		if($("h2").text().trim().equals("�˻����� ����͸� �� �ǽ����� ����")) {
			System.out.println(" *** doubtInflowDiagnosis pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** doubtInflowDiagnosis pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".blue", 0).text().trim().equals("10�� �̳� 3ȸ �̻�")) {
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
		alertCheck("doubtInflowDiagnosis modify msg", "�ǽ����� ���ܱ����� �����Ǿ����ϴ�.");
		$("h2", 2).waitUntil(hidden, 10000);		
		if($(".blue", 0).text().trim().equals("360�� �̳� 10ȸ �̻�")) {
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
		alertCheck("doubtInflowDiagnosis restoration msg", "�ǽ����� ���ܱ����� �����Ǿ����ϴ�.");
		$("h2", 2).waitUntil(hidden, 10000);		
		if($(".blue", 0).text().trim().equals("10�� �̳� 3ȸ �̻�")) {
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
		if($("h2").text().trim().equals("�˻����� ����͸� �� ���Լ� �����˸�")) {
			System.out.println(" *** inflowNotification pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().substring(0, 14).equals("��ϵ� �˻�� �����ϴ�.")) {
			System.out.println(" *** inflowNotification empty-data check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple").click();
		$(".input_txt").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ֱ� 30�� ���� ������ �־��� �˻���(����Ű����)�� �����ϴ�.")) {
			System.out.println(" *** inflowNotification empty-keyword-data check Success !! *** ");
		} else {
			System.out.println(" *** inflowNotification empty-keyword-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".input_txt").setValue("test");		
		$(".btn_blue").click();
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
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
		if($("h2").text().trim().equals("ķ���� �� �˻�����")) {
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** searchAdv empty-search check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv empty-search check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("#keyword_1").waitUntil(visible, 10000);
		$(".btn_purple", 0).click();
		alertCheck("searchAdv product msg", "�����ǰ�� �����ϼ���.");
		$(By.name("ad_type")).selectOption("�׿�Ŭ��");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv keyword msg", "�˻�� �Է��ϼ���.");
		$("#keyword_1").setValue(id_date + " �˻���^^");
		$(".btn_purple", 0).click();		
		alertCheck("searchAdv keyword valCheck msg", "�˻����� Ư�����ڸ� Ȯ�����ּ���.\n" + "��� ������ Ư������:  ( !  % & ( ) + - _ = : . / ? ~ # )");
		$("#keyword_1").setValue(id_date + " �˻���");
		$("#s_url_1").setValue("");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv URL msg", "����URL�� �Է��ϼ���.");
		$("#s_url_1").setValue(id_date + " ����URL");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv URL valCheck msg", "���� URL�� Ư�����ڸ� Ȯ���� �ּ���.\n" + "��� ������ Ư������:   - ( ) % # ~ ? & = _ . :");
		$("#s_url_1").setValue(id_date + "����URL");
		$(".btn_purple", 0).click();
		$(".btn_white", 3).waitUntil(visible, 10000);		
		if($(".left", 2).text().trim().equals(id_date + " �˻���")) {
			System.out.println(" *** searchAdv add check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("ad_url_1")).waitUntil(visible, 10000);
		$(By.name("ad_url_1")).setValue(id_date + " �˻����� URL");
		$(".btn_purple", 0).click();
		alertCheck("searchAdv modify msg", "����URL�� �����Ǿ����ϴ�.\n" + "�ڵ庸�⸦ Ŭ���� �����ڵ带 Ȯ���ϼ���.");
		$(By.name("ad_url_1")).waitUntil(hidden, 10000);		
		if($(".left", 2).text().trim().equals(id_date + " �˻���")) {
			System.out.println(" *** searchAdv modify check Success !! *** ");
		} else {
			System.out.println(" *** searchAdv modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_white", 7).click();
		alertCheck("searchAdv del null msg", "������ �˻����� �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 7).click();
		alertCheck("searchAdv del confirm msg", "�˻����� �����Ͻðڽ��ϱ�?\n" + "������ �˻������ �м���迡�� kw ���������� ǥ��˴ϴ�.");
		alertCheck("searchAdv del msg", "�˻����� �����Ǿ����ϴ�.");
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
		if($("h2").text().trim().equals("ķ���� �� ��ʱ���")) {
			System.out.println(" *** bannerAdv pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("��ʱ���")) {
			System.out.println(" *** bannerAdv list check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** bannerAdv empty-data check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("#promo_name").waitUntil(visible, 10000);
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv promoName null msg", "���θ�Ǹ��� �Է��ϼ���.");
		$("#promo_name").setValue(id_date + " ���θ�Ǹ�");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv name null msg", "��ʱ������ �Է��ϼ���.");
		$("#banner_name_1").setValue(id_date + " ��ʱ����");		
		$("#banner_linkurl_1").setValue("");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv URL null msg", "����URL�� �Է��ϼ���.");
		$("#banner_linkurl_1").setValue(id_date + " ����URL");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv URL valCheck msg", "����URL�� Ư�����ڴ�\n" + "����Ͻ� �� �����ϴ�.");
		$("#banner_linkurl_1").setValue(id_date + "����URL");		
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv add msg", "���θ�ǿ� 1���� ��ʱ��� ��ϵǾ����ϴ�.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " ���θ�Ǹ�")) {
			System.out.println(" *** bannerAdv add check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$("#promo_name").waitUntil(visible, 10000);
		$("#banner_name_1").setValue(id_date + " ��ʱ���� ����");
		$(".btn_purple", 1).click();
		alertCheck("bannerAdv modify msg", "���θ���� �����Ǿ����ϴ�.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		$("#img_1").click();
		$(".left", 2).waitUntil(visible, 10000);
		if($(".left", 2).text().trim().equals(id_date + " ��ʱ���� ����")) {
			System.out.println(" *** bannerAdv modify check Success !! *** ");
		} else {
			System.out.println(" *** bannerAdv modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_white", 10).click();
		alertCheck("bannerAdv del null msg", "������ ���θ���� �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 10).click();
		alertCheck("bannerAdv del confirm msg", "���θ���� �����Ͻðڽ��ϱ�?\n" + "��ϵ� ��ʱ��� ��� �����Ǹ�,\n" + "������ ��ʱ���� �м���迡�� kw ���������� ǥ��˴ϴ�.");
		alertCheck("bannerAdv del msg", "���θ�ǰ� ��ʱ��� ��� �����Ǿ����ϴ�.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("��ʱ���")) {
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
		if($("h2").text().trim().equals("ķ���� �� �̸��ϸ�����")) {
			System.out.println(" *** emailMkt pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("�̸��ϸ�����")) {
			System.out.println(" *** emailMkt list check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** emailMkt empty-data check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(By.name("input_name")).waitUntil(visible, 10000);
		$(".btn_purple", 1).click();
		alertCheck("emailMkt name null msg", "�̸��ϸ��� �Է��ϼ���.");
		$(By.name("input_name")).setValue(id_date + " �̸��ϸ�");		
		$(By.name("lurl")).setValue("");		
		$(".btn_purple", 1).click();
		alertCheck("emailMkt URL null msg", "����URL�� �Է��ϼ���.");
		$(By.name("lurl")).setValue(id_date + " URL.com");
		$(".btn_purple", 1).click();
		alertCheck("emailMkt URL valCheck msg", "����URL�� Ư�����ڴ�\n" + "����Ͻ� �� �����ϴ�.");
		$(By.name("lurl")).setValue(id_date + "URL.com");
		$(".btn_purple", 1).click();
		alertCheck("emailMkt add msg", "�̸��ϸ������� ��ϵǾ����ϴ�.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " �̸��ϸ�")) {
			System.out.println(" *** emailMkt add check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("lurl")).waitUntil(visible, 10000);
		$(By.name("lurl")).setValue(id_date + "URL����.com");
		$(".btn_purple", 1).click();
		alertCheck("emailMkt modify msg", "�̸��ϸ������� �����Ǿ����ϴ�.");
		$(".input_txt", 0).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		$("#url_txt_0").waitUntil(visible, 10000);
		if($("#url_txt_0").text().trim().substring(7, 30).equals(id_date + "URL����.com")) {
			System.out.println(" *** emailMkt modify check Success !! *** ");
		} else {
			System.out.println(" *** emailMkt modify check Fail ... !@#$%^&*() *** ");
			System.out.println($("#url_txt_0").text().trim().substring(7, 30));
			close();
		}
		sleep(500);
		$(".btn_white", 3).click();
		alertCheck("emailMkt del null msg", "������ �̸��ϸ������� �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 3).click();
		alertCheck("emailMkt del confirm msg", "�̸��ϸ������� �����Ͻðڽ��ϱ�?\n" + "������ �̸��ϸ������� �м���迡�� kw ���������� ǥ��˴ϴ�.");
		alertCheck("emailMkt del msg", "�̸��ϸ������� �����Ǿ����ϴ�.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("�̸��ϸ�����")) {
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
		if($("h2").text().trim().equals("ķ���� �� ���̷�������")) {
			System.out.println(" *** viralMkt pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("���̷�������")) {
			System.out.println(" *** viralMkt list check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** viralMkt empty-data check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("viralMkt name null msg", "������ ������ �Է��ϼ���.");
		$(".input_grey", 0).setValue(id_date + " ���̷������ø�");
		$(".btn_blue", 1).click();
		alertCheck("viralMkt URL null msg", "�������� ��ϵ� ��α�, ī���� �ּҸ� �Է��ϼ���.");
		$(".input_grey", 1).setValue(id_date + " ���̷�������URL");
		$(".btn_blue", 1).click();
		alertCheck("viralMkt URL valCheck msg", "������URL�� Ư�����ڴ�\n" + "����Ͻ� �� �����ϴ�.");
		$(".input_grey", 1).setValue(id_date + "���̷�������URL");
		$(".btn_blue", 1).click();
		alertCheck("viralMkt add msg", "���̷��������� ��ϵǾ����ϴ�.");
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".btn_white", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " ���̷������ø�")) {
			System.out.println(" *** viralMkt add check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("content_name_0")).waitUntil(visible, 10000);
		$(By.name("content_name_0")).setValue(id_date + " ���̷������ø� ����");
		$(".btn_purple", 0).click();
		alertCheck("viralMkt modify msg", "���̷��������� �����Ǿ����ϴ�.");
		$(By.name("content_name_0")).waitUntil(hidden, 10000);		
		if($(".left", 1).text().trim().equals(id_date + " ���̷������ø� ����")) {
			System.out.println(" *** viralMkt modify check Success !! *** ");
		} else {
			System.out.println(" *** viralMkt modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 5).click();
		alertCheck("viralMkt del null msg", "������ ���̷��������� �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 5).click();
		alertCheck("viralMkt del confirm msg", "���̷��������� �����Ͻðڽ��ϱ�?\n" + "������ ���̷��������� �м���迡�� kw ���������� ǥ��˴ϴ�.");
		alertCheck("viralMkt del msg", "���̷��������� �����Ǿ����ϴ�.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("���̷�������")) {
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
		if($("h2").text().trim().equals("ķ���� �� QR�ڵ�")) {
			System.out.println(" *** QRcode pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** QRcode pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("QR�ڵ�")) {
			System.out.println(" *** QRcode list check Success !! *** ");
		} else {
			System.out.println(" *** QRcode list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** QRcode empty-data check Success !! *** ");
		} else {
			System.out.println(" *** QRcode empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("QRcode name null msg", "QR�ڵ���� �Է��ϼ���.");
		$(".input_grey", 0).setValue(id_date + " QR�ڵ��");
		$(".input_adress_txt").setValue("");		
		$(".btn_blue", 1).click();
		alertCheck("QRcode URL null msg", "����URL�� �Է��ϼ���.");		
		$(".input_adress_txt").setValue(id_date + " QR�ڵ�URL");
		$(".btn_blue", 1).click();
		alertCheck("QRcode URL valCheck msg", "����URL�� Ư�����ڴ�\n" + "����Ͻ� �� �����ϴ�.");	
		$(".input_adress_txt").setValue(id_date + "QR�ڵ�URL");
		$(".btn_blue", 1).click();
		alertCheck("QRcode add msg", "QR�ڵ尡 ��ϵǾ����ϴ�.");		
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".btn_white", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " QR�ڵ��")) {
			System.out.println(" *** QRcode add check Success !! *** ");
		} else {
			System.out.println(" *** QRcode add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$("#CodeName_1").waitUntil(visible, 10000);
		$("#CodeName_1").setValue(id_date + " QR�ڵ�� ����");
		$(".btn_purple", 0).click();
		alertCheck("QRcode modify msg", "QR�ڵ���� �����Ǿ����ϴ�.");
		$("#CodeName_1").waitUntil(hidden, 10000);		
		if($(".left", 1).text().trim().equals(id_date + " QR�ڵ�� ����")) {
			System.out.println(" *** QRcode modify check Success !! *** ");
		} else {
			System.out.println(" *** QRcode modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 11).click();
		alertCheck("QRcode del null msg", "������ QR�ڵ带 �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 11).click();
		alertCheck("QRcode del confirm msg", "QR�ڵ带 �����Ͻðڽ��ϱ�?\n" + "������ QR�ڵ�� �м���迡�� kw ���������� ǥ��˴ϴ�.");
		alertCheck("QRcode del msg", "QR�ڵ尡 �����Ǿ����ϴ�.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("QR�ڵ�")) {
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
		if($("h2").text().trim().equals("ķ���� �� �ѱ����ͳ��ּ�")) {
			System.out.println(" *** koreanInternetAddress pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("�ѱ����ͳ��ּ�")) {
			System.out.println(" *** koreanInternetAddress list check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** koreanInternetAddress empty-data check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress null msg", "�ѱ����ͳ��ּҸ� �Է��ϼ���.");
		$(By.name("i_ad_keyword")).setValue(id_date + "�ѱ����ͳ��ּ�");
		$(By.name("i_ad_url")).setValue("");
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress URL null msg", "����URL�� �Է��ϼ���.");
		$(By.name("i_ad_url")).setValue(id_date + " �ѱ����ͳ��ּ�URL");
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress URL valCheck msg", "����URL�� Ư�����ڴ�\n" + "����Ͻ� �� �����ϴ�.");
		$(By.name("i_ad_url")).setValue(id_date + "�ѱ����ͳ��ּ�URL");
		$(".btn_blue", 1).click();
		alertCheck("koreanInternetAddress add msg", "�ѱ����ͳ��ּҰ� ��ϵǾ����ϴ�.");
		$(".btn_blue", 1).waitUntil(hidden, 10000);
		$(".input_txt", 0).setValue("");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(hidden, 10000);
		$(".btn_white", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + "�ѱ����ͳ��ּ�")) {
			System.out.println(" *** koreanInternetAddress add check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("ad_keyword_0")).waitUntil(visible, 10000);
		$(By.name("ad_keyword_0")).setValue(id_date + "�ѱ����ͳ��ּҼ���");
		$(".btn_purple", 0).click();
		alertCheck("koreanInternetAddress modify msg", "�ѱ����ͳ��ּҰ� �����Ǿ����ϴ�.");
		$(".btn_purple", 0).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals(id_date + "�ѱ����ͳ��ּҼ���")) {
			System.out.println(" *** koreanInternetAddress modify check Success !! *** ");
		} else {
			System.out.println(" *** koreanInternetAddress modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 5).click();
		alertCheck("koreanInternetAddress del null msg", "������ �ѱ����ͳ��ּҸ� �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 5).click();
		alertCheck("koreanInternetAddress del confirm msg", "�ѱ����ͳ��ּҸ� �����Ͻðڽ��ϱ�?\n" + "������ �ѱ����ͳ��ּҴ� �м���迡�� kw ���������� ǥ��˴ϴ�.");
		alertCheck("koreanInternetAddress del msg", "�ѱ����ͳ��ּҰ� �����Ǿ����ϴ�.");
		$(".js-checkbox", 1).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("�ѱ����ͳ��ּ�")) {
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
		if($("h2").text().trim().equals("ķ���� �� ķ���� �׷�")) {
			System.out.println(" *** campaignGroup pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("ķ���� �׷��")) {
			System.out.println(" *** campaignGroup list check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_purple", 1).click();
		$("#chkidx_8").waitUntil(visible, 10000);
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup name null msg", "ķ���α׷���� �Է��ϼ���.");
		$("#camp_name").setValue(id_date + " ķ���� �׷��");
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup null msg", "ķ������ �����ϼ���.");
		$("#chkidx_8").click();
		$(By.name("promo_select")).selectOption("��ʱ���");
		$("#chkidx_1").waitUntil(visible, 10000);
		$("#chkidx_1").click();
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup add msg", "ķ���α׷��� ��ϵǾ����ϴ�.");
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " ķ���� �׷��")) {
			System.out.println(" *** campaignGroup add check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$("#camp_name").waitUntil(visible, 10000);
		$("#camp_name").setValue(id_date + " ķ���� �׷�� ����");
		$(".btn_purple", 0).click();
		alertCheck("campaignGroup modify msg", "ķ���α׷��� �����Ǿ����ϴ�.");	
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " ķ���� �׷�� ����")) {
			System.out.println(" *** campaignGroup modify check Success !! *** ");
		} else {
			System.out.println(" *** campaignGroup modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 0).click();
		alertCheck("campaignGroup del confirm msg", "ķ���α׷��� �����Ͻðڽ��ϱ�?");
		alertCheck("campaignGroup del confirm msg", "ķ���α׷��� �����Ǿ����ϴ�.");
		$(".left", 4).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("ķ���� �׷��")) {
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
		if($("h2").text().trim().equals("������ �� �ó�����")) {
			System.out.println(" *** scenario pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** scenario pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("�ó�������")) {
			System.out.println(" *** scenario list check Success !! *** ");
		} else {
			System.out.println(" *** scenario list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** scenario empty-data check Success !! *** ");
		} else {
			System.out.println(" *** scenario empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_purple", 7).waitUntil(visible, 10000);
		$(".btn_purple", 7).click();
		alertCheck("scenario name null msg", "�ó��������� �Է��ϼ���.");
		$(".input_grey", 0).setValue("�ó�������");
		$(".btn_purple", 7).click();
		alertCheck("scenario page null msg", "ã�⸦ Ŭ���� �������� �����ϼ���. ");
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
		$(By.name("scen_name_0")).setValue("�ó������� ����");
		$(".btn_purple", 0).click();
		alertCheck("scenario modify msg", "�ó������� �����Ǿ����ϴ�.");
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("�ó������� ����")) {
			System.out.println(" *** scenario modify check Success !! *** ");
		} else {
			System.out.println(" *** scenario modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$(By.name("scen_name_0")).waitUntil(visible, 10000);
		$(By.name("scen_name_0")).setValue("�ó�������");
		$(".btn_purple", 0).click();
		alertCheck("scenario restoration msg", "�ó������� �����Ǿ����ϴ�.");
		$(".left", 1).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals("�ó�������")) {
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
		if($("h2").text().trim().equals("������ �� ���ι��")) {
			System.out.println(" *** innerbanner pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("���ι��2")) {
			System.out.println(" *** innerbanner list check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".input_txt", 0).setValue("test");
		$(".btn_blue", 0).click();
		$(".empty_td").waitUntil(visible, 10000);
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** innerbanner empty-data check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_purple", 1).waitUntil(visible, 10000);
		$(".btn_purple", 1).click();
		alertCheck("innerbanner promoName null msg", "���θ�Ǹ��� �Է��ϼ���.");
		$("#promo_name").setValue(id_date + " ���ι�� ���θ�Ǹ�");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner name null msg", "���ι�ʸ��� �Է��ϼ���.");
		$("#banner_name_1").setValue(id_date + " ���ι�ʸ�");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner filePath null msg", "���ϰ�θ� �Է��ϼ���.");
		$("#banner_imgurl_1").setValue("/" + id_date);
		$(".btn_purple", 1).click();
		alertCheck("innerbanner URL null msg", "����URL�� �Է��ϼ���.");
		$("#banner_linkurl_1").setValue(id_date + " ����URL");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner URL valCheck msg", "����URL�� Ư�����ڴ�\n" + "����Ͻ� �� �����ϴ�.");
		$("#banner_linkurl_1").setValue(id_date + "����URL");
		$(".btn_purple", 1).click();
		alertCheck("innerbanner add msg", "���ι�� ���θ���� ��ϵǾ����ϴ�.");		
		$(".left", 3).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " ���ι�� ���θ�Ǹ�")) {
			System.out.println(" *** innerbanner list check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		$("#promo_name").setValue(id_date + " ���ι�� ���θ�Ǹ� ����");		
		$("#banner_name_1").setValue(id_date + " ���ι�ʸ� ����");
		$("#banner_imgurl_1").setValue("/" + id_date + "����");		
		$("#banner_linkurl_1").setValue(id_date + "����URL����");		
		$(".btn_purple", 1).click();
		alertCheck("innerbanner modify msg", "���ι�� ���θ���� �����Ǿ����ϴ�.");
		$("#img_0").waitUntil(visible, 10000);
		$("#img_0").click();
		$(".left", 2).waitUntil(visible, 10000);
		if($(".left", 1).text().trim().equals(id_date + " ���ι�� ���θ�Ǹ� ����")) {
			System.out.println(" *** innerbanner modify promotion name check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner modify promotion name check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		if($(".left", 2).text().trim().equals(id_date + " ���ι�ʸ� ����")) {
			System.out.println(" *** innerbanner modify innerbanner name check Success !! *** ");
		} else {
			System.out.println(" *** innerbanner modify innerbanner name check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_white", 9).click();
		alertCheck("innerbanner del null msg", "������ ���θ���� �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();		
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 9).click();
		alertCheck("innerbanner del confirm msg", "���θ���� �����Ͻðڽ��ϱ�?\n" + "��ϵ� ���ι�ʵ� �Բ� �����˴ϴ�.\n\n" 
		+ "������Ʈ�� ����� ���ι�� �����ڵ嵵 �������ֽñ� �ٶ��ϴ�.\n" + "(�̻����� ������� �����Ǿ� �߰������ �߻��� �� �ֽ��ϴ�.)");
		alertCheck("innerbanner del msg", "���θ�ǰ� ���ι�ʰ� ��� �����Ǿ����ϴ�.");
		$(".left", 2).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("���ι��2")) {
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
		if($("h2").text().trim().equals("������ �� ���ϴٿ�ε�")) {
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
		if($(".empty_td").text().trim().equals("�ش�Ǵ� �˻������ �����ϴ�.")) {
			System.out.println(" *** fileDownload empty-data check Success !! *** ");
		} else {
			System.out.println(" *** fileDownload empty-data check Fail ... !@#$%^&*() *** ");
			System.out.println($(".empty_td").text().trim());
			close();
		}
		$(".btn_purple", 0).click();
		$(".btn_blue", 1).waitUntil(visible, 10000);
		$(".btn_blue", 1).click();
		alertCheck("fileDownload null msg", "�ٿ�ε� ������ �Է��ϼ���.");
		$(By.name("f_download_pattern")).setValue(id_date + ".*");
		$(".btn_blue", 1).click();
		alertCheck("fileDownload add msg", "�ٿ�ε� ������ ��ϵǾ����ϴ�.");
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
		alertCheck("fileDownload del null msg", "������ �ٿ�ε� ������ �����ϼ���.");
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".js-checkbox", 0).click();
		$(".btn_white", 1).click();
		alertCheck("fileDownload del confirm msg", "�ٿ�ε� ������ �����Ͻðڽ��ϱ�?\n" + "\n" + "�ٿ�ε� ������ �����ϸ�, �������� ���ϴٿ�ε�\n" + "�м��� �����Ǹ�, ���� �� ������ �Ұ����մϴ�.");
		alertCheck("fileDownload del msg", "�ٿ�ε� ������ �����Ǿ����ϴ�.");
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
		if($("h2").text().trim().equals("ȸ���׷�")) {
			System.out.println(" *** memberGroup pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".left", 1).text().trim().equals("ȸ�� �׷��")) {
			System.out.println(" *** memberGroup list check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup list check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 1).text().trim());
			close();
		}
		$(".btn_purple", 1).click();
		$(".input_grey", 0).waitUntil(visible, 10000);
		$(".btn_purple", 0).click();
		alertCheck("memberGroup name null msg", "ȸ�� �׷���� �Է��ϼ���.");
		$(By.name("md_p_name")).setValue(id_date + " ȸ�� �׷��");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup var1 null msg", "ȸ�� �������� �Է��ϼ���.");
		$(By.name("md_name_0")).setValue(id_date + "+A");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup var2 null msg", "ȸ�� �������� �Է��ϼ���.");
		$(By.name("md_name_1")).setValue(id_date + "+B");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup add msg", "ȸ�� �׷��� ��ϵǾ����ϴ�.");
		$(".input_grey", 2).waitUntil(hidden, 10000);
		if($(".left", 2).text().trim().equals(id_date + " ȸ�� �׷��")) {
			System.out.println(" *** memberGroup add check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup add check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_purple", 1).click();
		$(By.name("md_p_name")).waitUntil(visible, 10000);
		$(By.name("md_p_name")).setValue(id_date + " ȸ�� �׷�� ����");		
		$(By.name("md_name_0")).setValue(id_date + "+A����");
		$(By.name("md_name_1")).setValue(id_date + "+B����");
		$(".btn_purple", 0).click();
		alertCheck("memberGroup modify msg", "ȸ�� �׷��� �����Ǿ����ϴ�.");
		$(By.name("md_name_1")).waitUntil(hidden, 10000);	
		if($(".left", 2).text().trim().equals(id_date + " ȸ�� �׷�� ����")) {
			System.out.println(" *** memberGroup modify check Success !! *** ");
		} else {
			System.out.println(" *** memberGroup modify check Fail ... !@#$%^&*() *** ");
			System.out.println($(".left", 2).text().trim());
			close();
		}
		$(".btn_white", 1).click();
		alertCheck("memberGroup del confirm msg", "ȸ���׷��� �����Ͻðڽ��ϱ�?");
		alertCheck("memberGroup del msg", "ȸ���׷��� �����Ǿ����ϴ�.");
		$(".left", 2).waitUntil(hidden, 10000);
		if($(".left", 1).text().trim().equals("ȸ�� �׷��")) {
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
		if($("h2").text().trim().equals("��ǰ �� ������ǰ")) {
			System.out.println(" *** ProductOfInterest pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** ProductOfInterest pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().equals("��ϵ� ������ǰ�� �����ϴ�.\n" + "�߰��� Ŭ���� ������ǰ�� ����ϼ���.")) {
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
		if($("h2").text().trim().equals("��ǰ �� ��ǰ���ݴ�")) {
			System.out.println(" *** productPrice pageLoad check Success !! *** ");
		} else {
			System.out.println(" *** productPrice pageLoad check Fail ... !@#$%^&*() *** ");
			System.out.println($("h2").text().trim());
			close();
		}
		if($(".empty_td").text().trim().equals("��ϵ� ��ǰ���ݴ밡 �����ϴ�.\n" + "�űԵ���� Ŭ���� ��ǰ���ݴ븦 ����ϼ���.")) {
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