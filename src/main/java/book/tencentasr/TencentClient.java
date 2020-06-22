package book.tencentasr;

import com.tencentcloudapi.asr.v20190614.AsrClient;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;

/**
 * @author ChenOT
 * @date 2020-01-07
 * @see
 * @since
 */
public class TencentClient {
    private static final Credential cred = new Credential("AKIDtZ0CQP3pGTP4TcMAl6D5g16xX5NPbesq", "3qdAUcU6SbTLwINBj04rPhkXLsFNEpAG");
    private static final HttpProfile httpProfile = new HttpProfile();
    private static final ClientProfile clientProfile = new ClientProfile();
    static{
        clientProfile.setHttpProfile(httpProfile);
        httpProfile.setEndpoint("asr.tencentcloudapi.com");
    }
    private static final AsrClient client = new AsrClient(cred, "ap-shanghai", clientProfile);
    public static AsrClient getClientInstance(){
        return client;
    }

}
