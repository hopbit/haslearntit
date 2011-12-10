package endtoend;

import com.google.common.base.Function;
import it.haslearnt.entry.Entry;
import it.haslearnt.entry.EntryRepository;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.scale7.cassandra.pelops.pool.IThriftPool;
import org.springframework.beans.factory.annotation.Autowired;

import setup.IntegrationTest;

import com.google.common.base.Predicate;

import javax.annotation.Nullable;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

public class AddEntryEndToEndTest extends IntegrationTest {

	private static final int PORT = 6345;
	private WebDriver driver;
	private Server server;

	@Autowired
	IThriftPool thriftPool;

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
	public void shouldAddEntry() throws Exception {
		server = createServer();
		addContextToServer(server);
		server.start();

		driver = new FirefoxDriver();

		driver.navigate().to("http://localhost:" + PORT + "/");

		driver.findElement(By.id("skill")).sendKeys("szydełkowanie");
		driver.findElement(By.id("learningtime")).sendKeys("20");
		driver.findElement(By.id("entry")).submit();

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

    @Test
    public void shouldAutocompleteSkill() throws Exception {
        server = createServer();
        addContextToServer(server);
        server.start();
        String expectedSkill = "scala";
//        Entry entry = new Entry().iveLearnt(expectedSkill).today().andItWas("easy").itTook(10, Entry.TimeType.MINUTES).build();
//        entryRepository.saveEntry(entry, "dummy user");
        driver = new FirefoxDriver();
        driver.navigate().to("http://localhost:" + PORT + "/");
        driver.findElement(By.id("learningtime")).sendKeys("20");
        driver.findElement(By.id("skill")).sendKeys(expectedSkill);
        driver.findElement(By.id("entry")).submit();

        driver.navigate().to("http://localhost:" + PORT + "/");
        driver.findElement(By.id("skill")).sendKeys("sca");
        List<WebElement> hints = new WebDriverWait(driver, 5).until(new Function<WebDriver, List<WebElement>>() {
            @Override
            public List<WebElement> apply(@Nullable WebDriver input) {
                List<WebElement> elements = driver.findElements(By.className("ui-corner-all"));
                if (elements.isEmpty())   {
                        throw new NoSuchElementException("ui-corner-all");
                    }

                return elements;
            }
        }
        );
        for (WebElement hint : hints) {
            assertThat(hint.getText()).contains(expectedSkill);
        }
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
