package utils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.io.FileUtils;

/**
 * @author ChenOT
 * @date 2019-10-25
 * @see
 * @since
 */
public class SSHUtils {
    private static Session session;
    private static Channel channel;
    private static final String SSH_USER = "chenbin";
    private static final String SSH_PASSWORD = "kd5qfBB2CPEVONjq";
    private static final String SSH_HOST = "jumpserver.uzoo.cn";
    private static final int SSH_PORT = 21022;
    private static final String SSH_PRIVATE_KEY = "bl0dbLNQlg+jdAOkYrCYDxAq0Mgdr8DmSWyukWOmUTiG2qnYskdRCpCoAOPWQcE9ZpnqaijDhSvnt+aMsgc8wz6Qy09TBUxJwNLFeKHZ8CpPLEp2wsDB8qypi+5tzQEIo8w/wr4CKUfvJri5Rq4lg8NravJTpHTkv3HMggoDU4P5V2CA8sLOkHuuIQ7WZbFqQLnuALo+ToY4lJU4X6AULLPFR+9W5RjfA2gtOOdrAWpL6Zz00oAMHy8s7wkUnKif0MaxpaGvglCfnnk45UFZXdSMtst/PI8Hz2njbbSrUc9JBueKznVdrGE4Q1eftkbch7fsprKrT9zqcFZ3l9DiSUb1Vy2DunVyHERuNdz1z8qQFzieLjfkUfi6k/6/asUIAEdphCldDKfGW/ysTEA38BergbggmwVW3G3PCZr6GkmWgtUO35nYmzNuHk09mSpxz6x4grXgQYUYIx1s3x+YeABfioumlxPsxqsFO/jXu+4ZQklQrtqOcVRNsbSdCtU92c0yn2vPA5v8+EzN+aY6kahLcfPaSrp2teTUHnmYcGxvUwLdzHk90Ni8Cf5fJ1BhXKdLndZq6ZiuM9raHdesiRy07+ZuqIvUNom/i3nGlJmwUFTYBea31a6RqRMc1oy0sQMmTle+N/tZUYnaiXMC37h7mVbvl39yujYhihx/uPhm6fZfCgRIKqdT55Vsy0F7Tx1YQPjIbYnxrj0Tv0pN+F/TXke5+VeXJBBMw6QIvA2sZdRl4SLs7vz0YxzfkG+KyyOEkWJrTeNDs9mXX3qd+xAe4cBj1x9kuRcTJCbFd23EFHErcnROH8V0KjAdHXTd9118pMtoHCewgyerrKe0Vg0VlidpiViugsoldrU+gam4gYnZrnLFlDxcLBuUBDLZ6C8U9RGOVtC+Ae7P4m5ZMqKhRuBPmSUXpAcr+lalCK2ojwXQ87ARCzlJmt0bz9e3zCHk77nGdUvne6OycQtcfxIZWazbiaq+gAkByX9D+0+Hq6tWXKW4Ln/nLJ7oJ5Z/5ryikGAJHuLgEJdTeTiDJc7KWvSZlrngZexaPiDSoCcX3KXvq6rlBO2FHmuIojbfMAFUMao1fjMS4+I3NZq2p1xd0hNM13RSsUgDkEdoXaZLCxnpI0S4Kb6wvDNjBcXwJLLYgLW7EJJcZv4YhDF3yqPx2H181SES603GMS9aE6935f6RCLVerCfOh5jLg+UjnHsgtpLPIjTkGRYPNsNYSNDI9iKGn8JBC8+fzqoP3LT2tJppEjj1BcdehgAn00EhALXsGHweTHysMHA8eYJpYScTcT3nLIs/XF/i2Pd6bV1GA92yr5bQy6r4Xsd6NP/T09YJD+Rz+yWsjdAWWwr97klAJeYQpqmI4wgC3Dtii4Fd8aO/7BUMMGOSqdx8HxYrQEXy61v3BjgxFAAoLox1g/iJcJgFKvTELTgS1/a65CPr9TJSMVhyF2dXOB8OBkB60oalWOqvTntyrXmCBQSNq7hiNbnmd2WCb+8uID2J6qcU1jMcKkxAx3515SJa4Y3e3xTzqSnEvFn6K+PjZjGN72qJYiDI3Dmf55FyHPBE3V3E4e9UJjVRIrzMLjpHlZ9E";

    /**
//     * @param localPort  本地host 建议mysql 3306 redis 6379
//     * @param remotoHost 远程机器地址
//     * @param remotoPort 远程机器端口
     */
//    public static void goSSH(int localPort, String remotoHost, int remotoPort) {
    public static void goSSH() {
        try {
            JSch jsch = new JSch();
            jsch.addIdentity(
                    "111",
                    FileUtils.readFileToByteArray(new java.io.File("C:\\Users\\cb\\chenbin.pem")),
                    FileUtils.readFileToByteArray(new java.io.File("C:\\Users\\cb\\chenbin.pem.pub")), null);
            jsch.setKnownHosts("C:\\Users\\cb\\hosts");
            //登陆跳板机
            session = jsch.getSession(SSH_USER, SSH_HOST, SSH_PORT);
            session.setPassword(SSH_PASSWORD);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();
            //建立通道
            channel = session.openChannel("session");
            channel.connect();
            //通过ssh连接到mysql机器
//            int assinged_port = session.setPortForwardingL(localPort, remotoHost, remotoPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        goSSH();
        close();
    }

    /**
     * 关闭
     */
    public static void close() {
        if (session != null && session.isConnected()) {
            session.disconnect();
        }

        if (channel != null && session.isConnected()) {
            channel.disconnect();
        }
    }
}
