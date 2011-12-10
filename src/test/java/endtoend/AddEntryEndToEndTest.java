package endtoend;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Test;
import org.junit.Ignore;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.scale7.cassandra.pelops.pool.IThriftPool;
import org.springframework.beans.factory.annotation.Autowired;
import setup.IntegrationTest;

public class AddEntryEndToEndTest extends IntegrationTest {

	private static final int PORT = 6345;
	private WebDriver driver;
	private Server server;

    @Autowired
    IThriftPool thriftPool;

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

        driver.findElement(By.id("learningtime")).sendKeys("20");
        driver.findElement(By.id("entry")).submit();

        //FIXME this test does no verifications! blocked by not-yet-implemented timeline feature
	}
	
    @Test
    @Ignore
	public void shouldAddCompletedEntry() throws Exception {
        //as above
        //FIXME this test does no verifications! blocked by not-yet-implemented timeline feature
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
