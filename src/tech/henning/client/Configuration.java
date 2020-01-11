package tech.henning.client;

import java.math.BigInteger;

public class Configuration {

    /**
     * IP Address or Hostname of the server to establish a connection.
     */
    public static final String SERVER_ADDRESS = "127.0.0.1";

    /**
     * Name of the cache folder located in the users home directory.
     */
    public static final String CACHE_NAME = ".377cache";

    /**
     * Port for establishing a connection to the game server.
     */
    public static final int GAME_PORT = 43594;

    /**
     * Port for establishing a connection to the on demand service.
     */
    public static final int ONDEMAND_PORT = 43596;

    /**
     * Port for establishing a connection to the update server.
     */
    public static final int JAGGRAB_PORT = 43595;

    /**
     * Port for establishing a backup connection to the update
     * server in case the initial JAGGRAB connection fails.
     */
    public static final int HTTP_PORT = 80;

    /**
     * Whether or not the update server should be used.
     */
    public static final boolean JAGGRAB_ENABLED = false;

    /**
     * Whether or not the network packets should be encrypted.
     */
    public static final boolean RSA_ENABLED = true;

    /**
     * Public key to be used in RSA network encryption.
     */
    public static final BigInteger RSA_PUBLIC_KEY = new BigInteger("65537");

    /**
     * Modulus to be used in the RSA network encryption.
     */
    public static final BigInteger RSA_MODULUS = new BigInteger("119568088839203297999728368933573315070738693395974011872885408638642676871679245723887367232256427712869170521351089799352546294030059890127723509653145359924771433131004387212857375068629466435244653901851504845054452735390701003613803443469723435116497545687393297329052988014281948392136928774011011998343");

    /**
     * Use static username/password pair.
     */
    public static final boolean USE_STATIC_DETAILS = true;

    /**
     * Static username and password
     */

    public static final String USERNAME = "qq";
    public static final String PASSWORD = "";

    /**
     * Do you want to render roofs
     */
    public static final boolean ROOFS_ENABLED = true;

}
