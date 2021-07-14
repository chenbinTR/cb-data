package access;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public enum DataEnum {
    apple(Arrays.asList(""));
    private static final Date date = new Date();
    DataEnum(List<String> s) {

    }
}
