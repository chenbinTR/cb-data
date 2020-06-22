package es;

/**
 * 请求类型.
 */
public enum RequestType {
    CHAT, QA, FAQ, ATTR, EN, CUSTOMER_SERVICE,
    CHAT_KID, QA_COLD, QA_DUP, FAQ_DUP, FAQ_CS, FAQ_NEW,
    /**
     * 垂直领域，具体类别取决于tablename.
     */
    VERTICAL,

    /**
     * NLU语料匹配, 具体意图取决于tablename.
     */
    NLU;

    public boolean hasAccount() {
        return isFAQ() || isCS();
    }

    public boolean multiCluster() {
        return this.equals(CHAT) || this.equals(CHAT_KID);
    }

    public boolean onlineSeg() {
        return this.equals(CUSTOMER_SERVICE);
    }

    public boolean isCS() {
        return this.equals(CUSTOMER_SERVICE) || this.equals(FAQ_CS);
    }

    public boolean isFAQ() {
        return this.equals(FAQ) || this.equals(FAQ_DUP) || this.equals(FAQ_CS) || this.equals(FAQ_NEW);
    }

    public boolean isCHAT() {
        return this.equals(CHAT) || this.equals(CHAT_KID);
    }

    public boolean isQA() {
        return this.equals(QA) || this.equals(QA_COLD) || this.equals(QA_DUP);
    }

    public boolean isATTR() {
        return this.equals(ATTR);
    }

    public boolean isEN() {
        return this.equals(EN);
    }

    public boolean isVERT() {
        return this.equals(VERTICAL);
    }

    /**
     * question是否已经分词
     *
     * @return
     */
    public boolean isNLU() {
        return this.equals(NLU);
    }
}
