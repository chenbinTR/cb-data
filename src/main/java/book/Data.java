package book;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-12
 * @see
 * @since
 */
public class Data {
    private List<List<String>> qas;
    private String sentence;

    public List<List<String>> getQas() {
        return qas;
    }

    public void setQas(List<List<String>> qas) {
        this.qas = qas;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }
}
