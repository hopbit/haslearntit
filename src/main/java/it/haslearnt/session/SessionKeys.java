package it.haslearnt.session;

/**
 * This class is responsible for returning non configurable session keys.
 * It's all done with static final, because session configuration has to be lightning fast and accessible from frontend by static facade methods.
 * All session keys have to be in one place, so that we do not override anything by accident.
 */
public class SessionKeys {
    private SessionKeys() {
    }

    public static final String openIdAttributes = "USER_OPENID_CREDENTIAL";
}
