/*
 * Copyright: this code is distributed under WTFPL version2
 * In short: You just DO WHAT THE FUCK YOU WANT TO.
 */

package setup;

import it.haslearnt.cassandra.CassandraColumnFamilyManager;
import it.haslearnt.security.UserAuthenticationInBackend;
import it.haslearnt.user.User;
import it.haslearnt.user.UserRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.scale7.cassandra.pelops.Cluster;
import org.scale7.cassandra.pelops.pool.IThriftPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import predefinedData.UserPredefinedData;

import javax.annotation.Resource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/ioc/testContext.xml", "classpath:/ioc/backend/applicationContext.xml", "classpath:/ioc/backend/security.xml"})
@Ignore
public abstract class IntegrationTest {

    @Autowired
    protected IThriftPool pool;

    @Autowired
    protected Cluster cluster;

    @Resource(name = "authenticationManager")
    private AuthenticationManager authenticationManager;

    @Resource(name = "userRepository")
    private UserRepository userRepository;

    @Resource(name = "userAuthenticationInBackend")
    UserAuthenticationInBackend userAuthenticationInBackend;

    @Before
    public void cleanUp() throws Exception {
        CassandraColumnFamilyManager.cleanColumnFamilies(cluster, pool.getKeyspace());
    }

    @After
    public void tearDown() {
        userAuthenticationInBackend.logout();
    }

    protected void authenticateTestUser() {
        saveUserInDatabase();
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(UserPredefinedData.name, UserPredefinedData.password));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected void saveUserInDatabase() {
        User user = new User().withName(UserPredefinedData.name).withEmail(UserPredefinedData.email).withPassword(UserPredefinedData.hashedPassword);
        userRepository.save(user);
    }

}
