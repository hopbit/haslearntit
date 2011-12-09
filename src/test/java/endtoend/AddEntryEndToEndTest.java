package endtoend;

import org.eclipse.jetty.server.*;
import org.eclipse.jetty.server.bio.*;
import org.eclipse.jetty.webapp.*;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.*;

import setup.*;

public class AddEntryEndToEndTest extends IntegrationTest {

	private static final int PORT = 6345;
	private WebDriver driver;
	private Server server;

	@Test
	public void shouldAddEntry() throws Exception {
		server = createServer();
		addContextToServer(server);
		server.start();

		driver = new FirefoxDriver();

		driver.navigate().to("http://localhost:" + PORT + "/");

		driver.findElement(By.id("entry")).submit();

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
