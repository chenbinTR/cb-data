package es;

/**
 * 服务器地址.
 *
 * @author kong
 */
public enum EsAddress {
    ALPHA_ES("47.93.47.187", 9301),
    BETA_ES("121.199.52.145", 9301),
    PROD_ES_1("101.201.140.59", 9301),
    PROD_ES_2("47.94.57.146", 9301),
    ALPHA_TEST("39.107.91.143", 9700);

    /**
     * host地址.
     */
    private String host;
    /**
     * 端口号.
     */
    private int port;

    /**
     * 构造函数.
     *
     * @param host host地址
     * @param port 端口号
     */
    EsAddress(final String host, final int port) {
        this.host = host;
        this.port = port;
    }
    public String getHost(){
        return host;
    }
    public int getPort(){
        return port;
    }
}
