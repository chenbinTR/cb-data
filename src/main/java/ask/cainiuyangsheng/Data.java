package ask.cainiuyangsheng;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-11-28
 * @see
 * @since
 */
public class Data {
    public Data(String position, String title, List<String> contents, String url){
        this.position = position;
        this.title = title;
        this.contents = contents;
        this.url = url;
    }

    private String position;
    private String title;
    private List<String> contents;
    private String url;

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getContents() {
        return contents;
    }

    public void setContents(List<String> contents) {
        this.contents = contents;
    }
}
