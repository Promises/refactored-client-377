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
    public static final BigInteger RSA_MODULUS = new BigInteger("170266381807335046121774073514220583891686029487165562794998484549236036467227923571770256617931840775621072487838687650522710227973331693237285456731778528244126984080232314114323601116304887478969296070648644633713088027922830600712492972687351204275625149978223159432963210789506993409208545916714905193639");

    /**
     * Use static username/password pair.
     */
    public static final boolean USE_STATIC_DETAILS = true;

    /**
     * Static username and password
     */

    public static final String USERNAME = "Promises";
    public static final String PASSWORD = "Testing";
}
