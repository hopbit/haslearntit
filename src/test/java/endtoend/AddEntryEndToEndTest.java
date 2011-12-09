package endtoend;

import org.junit.After;
import org.junit.Test;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.bio.SocketConnector;
import org.mortbay.jetty.webapp.WebAppContext;
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

        driver.navigate().to("http://localhost:" + PORT + "/hello");

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
        server.addHandler(webAppContext);
    }
}
