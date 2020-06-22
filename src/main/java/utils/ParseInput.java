package utils;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by CheN on 2017/10/9.
 *
 * 该类为解析传入参数的bean
 * text 及 code为必传参数
 */
public class ParseInput{

    /**
     * 全局id
     */
    @Getter
    @Setter
    private String globalId;

    /**
     * 待解析文本内容
     */
    @Getter
    @Setter
    private String text;

    /**
     * 要使用的业务规则集code
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
     * 是否使用机器学习算法,默认为false
     */
    @Getter
    @Setter
    private boolean isLearning;

}