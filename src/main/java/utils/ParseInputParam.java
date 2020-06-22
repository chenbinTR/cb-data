package utils;


import lombok.Getter;
import lombok.Setter;

/**
 * @Author Zhuan
 * @Date 2017/10/25.
 *
 *  该bean于InputParse对应，
 *  在controller层将InputParse转换为InputParam
 *  InputParam在service层使用
 */
public class ParseInputParam {

    /**
     * 全局id
     */
    @Getter
    @Setter
    private String globalId;
    /**
     * 带解析的文本内容
     */
    @Getter
    @Setter
    private String text;

    /**
     * 要执行解析的业务规则集code
     */
    @Getter
    @Setter
    private String code;

    /**
     * 上下文id
     */
    @Getter
    @Setter
    private int context;

    /**
     * 用户id
     */
    @Getter
    @Setter
    private String userId;

    /**
     * 是否使用机器学习算法
     */
    @Getter
    @Setter
    private boolean isLearning;

    /**
     * 构造函数
     */
    public ParseInputParam(){}

    public ParseInputParam(String text, String code, int context, String userId) {
        this.text = text;
        this.code = code;
        this.context = context;
        this.userId = userId;
    }
}
