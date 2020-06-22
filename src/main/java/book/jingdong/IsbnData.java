package book.jingdong;

import org.apache.commons.lang3.StringUtils;

/**
 * @author ChenOT
 * @date 2020-03-17
 * @see
 * @since
 */
public class IsbnData {
    private String title;
    private String author;
    private String publisher;
    private String gist;
    private String img;

    private String process(String str){
        if(StringUtils.isBlank(str)){
            return "";
        }
        return str.trim().replace("\t","").replace("\r","").replace("\n","");
    }
    public String getTitle() {
        return process(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return process(author);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return process(publisher);
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getGist() {
        return process(gist);
    }

    public void setGist(String gist) {
        this.gist = gist;
    }

    public String getImg() {
        return process(img);
    }

    public void setImg(String img) {
        this.img = img;
    }
}
