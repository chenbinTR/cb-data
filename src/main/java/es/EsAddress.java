package es;

/**
 * 服务器地址.
 *
 * @author kong
 */
public enum EsAddress {
    ALPHA_ES("bIQlPORw3/vNq0GINmL5kA==", 9301),
    BETA_ES("hPN4hxW+E+aTG7VrIZCqHg==", 9301),
    PROD_ES_1("TWzsEt9wEe2UaBFVIV6gQg==", 9301);
//    PROD_ES_2("8UKRcddeyz78QQ1G5etx1A==", 9301);
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
