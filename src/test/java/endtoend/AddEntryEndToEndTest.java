package endtoend;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import setup.IntegrationTest;

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
        driver.findElement(By.id("submit-entry")).submit();

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
