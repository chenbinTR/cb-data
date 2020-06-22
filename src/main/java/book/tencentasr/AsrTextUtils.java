package book.tencentasr;

import com.tencentcloudapi.asr.v20190614.models.DescribeTaskStatusRequest;
import com.tencentcloudapi.asr.v20190614.models.DescribeTaskStatusResponse;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;

public class AsrTextUtils {
//    private static final Credential cred = new Credential("AKIDtZ0CQP3pGTP4TcMAl6D5g16xX5NPbesq", "3qdAUcU6SbTLwINBj04rPhkXLsFNEpAG");
//    private static final HttpProfile httpProfile = new HttpProfile();
//    private static final ClientProfile clientProfile = new ClientProfile();
//    static{
//        httpProfile.setEndpoint("asr.tencentcloudapi.com");
//        clientProfile.setHttpProfile(httpProfile);
//    }
//    private static final AsrClient client = new AsrClient(cred, "ap-beijing", clientProfile);

    /**
     * 获取识别结果
     *
     * @param taskId
     * @return
     */
    public static String reqAsrText(Integer taskId) {
        try {
            String params = String.format("{\"TaskId\":%d}", taskId);
            DescribeTaskStatusRequest req = DescribeTaskStatusRequest.fromJsonString(params, DescribeTaskStatusRequest.class);
            DescribeTaskStatusResponse resp = TencentClient.getClientInstance().DescribeTaskStatus(req);
            return DescribeTaskStatusRequest.toJsonString(resp);
        } catch (TencentCloudSDKException e) {
            System.err.println(e.toString());
            return null;
        }
    }

}