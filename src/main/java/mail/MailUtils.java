package mail;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.*;

/**
 * @author cb
 * @date 2018-10-22 16:26
 */
public class MailUtils {
    /**
     * 发送服务器
     */
    private static final String SMTP_HOST = "smtp.exmail.qq.com";
    /**
     * 发件箱
     */
    private static final String FROM = "chenbin@uzoo.cn";
//    private static final String FROM = "turing@uzoo.cn";
    /**
     * 发件箱密码
     */
    private static final String FROM_PWD = "wd6ghVAzBymfEExc";
//    private static final String FROM_PWD = "TUring2013";
//    private static final String FROM_PWD = "Tuling123";
    /**
     * 邮件格式
     */
    private static final String MSG_TYPE = "text/html;charset=gb2312";

    /**
     * 收件人
     */
    public static void sendMessage(String subject,
                                   String messageText, String[] ADDRESSES) throws MessagingException, UnsupportedEncodingException {
        // 第一步：配置javax.mail.Session对象
        Properties props = new Properties();
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.auth", "true");
        // props.put("mail.debug", "true");
        //SSL验证
        String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";
        props.put("mail.smtp.socketFactory.class", SSL_FACTORY);
        props.put("mail.smtp.socketFactory.fallback", "false");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.port", "465");
        MailSSLSocketFactory sf = null;
        try {
            sf = new MailSSLSocketFactory();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.socketFactory", sf);
        Session mailSession = Session.getInstance(props, new MyAuthenticator(FROM, FROM_PWD));
        // 第二步：编写消息
        InternetAddress fromAddress = new InternetAddress(FROM);
        InternetAddress[] toAddresses = new InternetAddress[ADDRESSES.length];
        for (int i = 0; i < ADDRESSES.length; i++) {
            toAddresses[i] = new InternetAddress(ADDRESSES[i]);
        }
        MimeMessage message = new MimeMessage(mailSession);
        message.setFrom(fromAddress);
        message.addRecipients(RecipientType.TO, toAddresses);
        message.setSentDate(Calendar.getInstance().getTime());
        message.setSubject(subject);
//        message.setContent(messageText, MSG_TYPE);

        // 6. 创建文本"节点"
        MimeBodyPart text = new MimeBodyPart();
        // 这里添加图片的方式是将整个图片包含到邮件内容中, 实际上也可以以 http 链接的形式添加网络图片
        text.setContent(messageText, MSG_TYPE);

        // 9. 创建附件"节点"
//        MimeBodyPart attachment = new MimeBodyPart();
        // 读取本地文件
//        DataHandler dh2 = new DataHandler(new FileDataSource("Q:\\"));
        // 将附件数据添加到"节点"
//        attachment.setDataHandler(dh2);
        // 设置附件的文件名（需要编码）
//        attachment.setFileName(MimeUtility.encodeText(dh2.getName()));

        // 10. 设置（文本+图片）和 附件 的关系（合成一个大的混合"节点" / Multipart ）
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(text);
//        mm.addBodyPart(attachment);     // 如果有多个附件，可以创建多个多次添加
        mm.setSubType("mixed");         // 混合关系

        message.setContent(mm);
        // 第三步：发送消息
//        Transport transport = mailSession.getTransport("smtp");
//        transport.connect(SMTP_HOST, FROM, FROM_PWD);
        Transport.send(message, message.getRecipients(RecipientType.TO));
//        transport.close();
    }

    public static void main(String[] args) {
        String[] addrss = addrs.split("\\|");
        int successCount = 0;
        for (String addr : addrss) {
            try {

                sendMessage("图灵机器人新服务通知",
                        content, new String[]{addr});
                System.out.println("成功：" + ++successCount);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                System.out.println("error: " + addr);
            }
        }

    }

    private static final String content = "123";
    private static final String addrs = "fengxinxin@uzoo.cn";
}

class MyAuthenticator extends Authenticator {

    String userName = "";
    String password = "";


    public MyAuthenticator() {
    }

    public MyAuthenticator(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }
}
