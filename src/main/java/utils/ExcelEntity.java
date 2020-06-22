package utils;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.StringUtils;

/**
 * @author chenbin
 * @Description:
 * @date 2018-07-01 21:24
 */
@Getter
@Setter
@Data
public class ExcelEntity {
    private String value0;
    private String value1;
    private String value2;
    private String value3;
    private String value4;
    private String value5;
    private String value6;
    private String value7;

    @Override
    public String toString() {
        return "ExcelEntity{" +
                "value0='" + value0 + '\'' +
                ", value1='" + value1 + '\'' +
                ", value2='" + value2 + '\'' +
                ", value3='" + value3 + '\'' +
                ", value4='" + value4 + '\'' +
                ", value5='" + value5 + '\'' +
                ", value6='" + value6 + '\'' +
                ", value7='" + value7 + '\'' +
                '}';
    }

    /**
     * 判断是否为空
     * @return
     */
    public boolean isBlank(){
        return StringUtils.isBlank(value0)
                && StringUtils.isBlank(value1)
                && StringUtils.isBlank(value2)
                && StringUtils.isBlank(value3)
                && StringUtils.isBlank(value4)
                && StringUtils.isBlank(value5)
                && StringUtils.isBlank(value6)
                && StringUtils.isBlank(value7);
    }
}
