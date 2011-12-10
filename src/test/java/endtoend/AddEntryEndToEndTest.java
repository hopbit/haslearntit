package endtoend;

import static org.junit.Assert.*;
import it.haslearnt.entry.*;
import it.haslearnt.skill.trends.*;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.bio.*;
import org.eclipse.jetty.webapp.*;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;
import org.openqa.selenium.support.ui.*;
import org.scale7.cassandra.pelops.pool.*;
import org.springframework.beans.factory.annotation.*;

import setup.*;

import com.google.common.base.*;

public class AddEntryEndToEndTest extends IntegrationTest {

	private static final int PORT = 6345;
	private WebDriver driver;
	private Server server;

	@Autowired
	IThriftPool thriftPool;

	@Autowired
	SkillTrendsRepository repository;

	@Autowired
	EntryRepository entryRepository;

	@Test
	public void shouldDisplayPage() throws Exception {
		server = createServer();
		addContextToServer(server);
		server.start();

		driver = new FirefoxDriver();

		driver.navigate().to("http://localhost:" + PORT + "/");
		driver.findElement(By.id("entry"));
	}

	@Test
	@Ignore
	public void shouldDisplayTopTrends() throws Exception {
		server = createServer();
		addContextToServer(server);
		server.start();

		driver = new FirefoxDriver();

		driver.navigate().to("http://localhost:" + PORT + "/");

		Thread.sleep(300);

		String trendsSection = driver.findElement(By.id("trends-list")).getText();

		assertTrue(trendsSection.contains("test"));
	}

	@Test
	@Ignore
	public void shouldAddEntry() throws Exception {
		server = createServer();
		addContextToServer(server);
		server.start();
		driver = new FirefoxDriver();

		driver.navigate().to("http://localhost:" + PORT + "/");

		driver.findElement(By.id("skill")).sendKeys("szydełkowanie");
		driver.findElement(By.id("learningtime")).sendKeys("20");
		driver.findElement(By.id("entry")).submit();

		// FIXME this test does no verifications! blocked by not-yet-implemented
		// timeline feature
		WebDriverWait wait = new WebDriverWait(driver, 3);
		wait.until(new Predicate<WebDriver>() {

			@Override
			public boolean apply(WebDriver d) {
				return d.getPageSource().contains("szydełkowanie");
			}
		});

	}

	@Test
	@Ignore
	public void shouldAddCompletedEntry() throws Exception {
		// as above
		// FIXME this test does no verifications! blocked by not-yet-implemented
		// timeline feature
	}

	@After
	public void shutdownSeleniumAndServer() throws Exception {
		driver.close();
		server.stop();
	}

	private static Server createServer() {
		Server server = new Server();
		SocketConnector connector = new SocketConnector();

		// Set some timeout options to make debugging easier.
		connector.setMaxIdleTime(1000 * 60 * 60);
		connector.setSoLingerTime(-1);
		connector.setPort(PORT);
		server.setConnectors(new Connector[] { connector });
		return server;
	}

	private static void addContextToServer(Server server) {
		WebAppContext webAppContext = new WebAppContext();
		webAppContext.setServer(server);
		webAppContext.setContextPath("/");
		webAppContext.setWar("src/main/webapp");
		server.setHandler(webAppContext);
	}
}
