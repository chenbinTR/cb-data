package book;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-09-12
 * @see
 * @since
 */
public class ResultEntity {
    private String msg;
    private int code;
    private List<Data> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
