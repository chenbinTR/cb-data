package tts;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author wangsx
 * @Title: com.turing.tts.bean.v1.TtsRequest
 * @Description: 原版协议请求
 * @date 2018/12/27
 */
@NoArgsConstructor
public class TtsRequest implements Serializable {

    /**
     * 全局跟踪id
     */
    @Setter
    @Getter
    private String globalId;
    /**
     * 需要识别转换的文本
     */
    @Setter
    @Getter
    private String text_str;
    /**
     * 选择的声音
     */
    @Setter
    @Getter
    private String biz_code;
    /**
     * PCM编码格式，字符串类型，默认为'csv'，建议使用'base64'
     */
    @Setter
    @Getter
    private String encode_fmt = "csv";
    /**
     * 调整语速，默认为1.0，建议取值范围为0.5~2.0，数值越大，速度越慢
     */
    @Setter
    @Getter
    private float speed = 1.0F;
    /**
     * 调整音量，默认为1.0，建议取值范围为0.1～2.5，数值越大，音量越大
     */
    @Setter
    @Getter
    private float loudness = 1.0F;
    /**
     * 控制音高，默认值为1.0，建议取值范围为0.1～3
     */
    @Setter
    @Getter
    private float f0_bias = 1.0F;
    /**
     * 调整语音波动，默认值为1.0，建议取值范围为0～4
     */
    @Setter
    @Getter
    private float arousal = 1.0F;
    /**
     * 为1时，进行rap合成；否则为普通的TTS
     */
    ///@Setter @Getter private int rap;
    /**
     * 为1时，混响；否则为普通的TTS
     */
    ///@Setter @Getter private int reverb;
    /**
     * 双子星专用参数
     */
    ///@Setter @Getter private int syl_flag;
    /**
     * 跟踪字符串，调试时定位问题
     */
    ///@Setter @Getter private String track_key;
    /**
     * 是否流式合成，整数类型，默认为1
     */
    @Setter
    @Getter
    private int stream = 1;
    /**
     * 是否启用快速合成模式，默认为0。若启用，则fft size减半，音质可能有损
     */
    @Setter
    @Getter
    private int fast_mode;
    /**
     * 后处理参数
     */
    //@Setter @Getter private Float pf_coef;
    /**
     * 调整标点的停顿时长
     */
    ///@Setter @Getter private String punc_len;
    /**
     * "normal"       "normal"空格不改变   "douhao"空格看成逗号 "dunhao"空格看成顿号  "juhao"空格看成句号
     */
    ///@Setter @Getter private String space_tab_type;
}
