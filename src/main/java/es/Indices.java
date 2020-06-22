package es;

/**
 * 索引地址.
 */
public class Indices {
    /**
     * 对话索引.
     */
    private static final Indices CHAT_INDICES = new Indices("nlp_chat");
    /**
     * chat kid 索引.
     */
    private static final Indices CHAT_KID_INDICES = new Indices("nlp_chat_kid");
    /**
     * qa索引.
     */
    private static final Indices QA_INDICES = new Indices("nlp_qa");
    /**
     * qa_dup索引.
     */
    private static final Indices QA_DUP_INDICES = new Indices("nlp_qa_dup");
    /**
     * qa cold data索引.
     */
    private static final Indices QA_COLD_INDICES = new Indices("nlp_qa_cold");
    /**
     * faq索引.
     */
    private static final Indices FAQ_INDICES = new Indices("nlp_faq");
    /**
     * faq_new索引.
     */
    private static final Indices FAQ_NEW_INDICES =  new Indices("nlp_faq_new");
    /**
     * faq dup索引.
     */
    private static final Indices FAQ_DUP_INDICES = new Indices("nlp_faq_dup");
    /**
     * faq cs索引.
     */
    private static final Indices FAQ_CS_INDICES = new Indices("nlp_faq_cs");
    /**
     * customer service索引.
     */
    private static final Indices CS_INDICES = new Indices("nlp_cs");
    /**
     * vertical索引
     */
    private static final Indices VERTICAL_INDICES = new Indices("nlp_vertical");

    /**
     * NLU索引
     */
    private static final Indices NLU_INDICES = new Indices("nlp_nlu");

    /**
     * 属性索引.
     */
    private static final Indices ATTR_INDICES = new Indices("nlp_attr");
    /**
     * 英文索引.
     */
    private static final Indices EN_INDICES = new Indices("nlp_english");
    /**
     * 索引.
     */
    private String index;

    /**
     * 构造索引.
     *
     * @param index 索引
     */
    private Indices(final String index) {
        this.index = index;
    }

    /**
     * 根据请求类型返回相应的indices.
     *
     * @param type 请求类型
     * @return indices
     */
    public static Indices getIndicesByType(final RequestType type) {
        switch (type) {
            case CHAT:
                return CHAT_INDICES;
            case CHAT_KID:
                return CHAT_KID_INDICES;
            case QA:
                return QA_INDICES;
            case QA_DUP:
                return QA_DUP_INDICES;
            case QA_COLD:
                return QA_COLD_INDICES;
            case FAQ:
                return FAQ_INDICES;
            case FAQ_NEW:
                return FAQ_NEW_INDICES;
            case FAQ_DUP:
                return FAQ_DUP_INDICES;
            case FAQ_CS:
                return FAQ_CS_INDICES;
            case CUSTOMER_SERVICE:
                return CS_INDICES;
            case ATTR:
                return ATTR_INDICES;
            case EN:
                return EN_INDICES;
            case VERTICAL:
                return VERTICAL_INDICES;
            case NLU:
                return NLU_INDICES;
            default:
                return null;
        }
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(final String index) {
        this.index = index;
    }
}
