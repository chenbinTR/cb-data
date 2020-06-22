package test;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ChenOT
 * @date 2020-03-10
 * @see
 * @since
 */
@Getter
@Setter
@AllArgsConstructor
public class Bean2 {
    private int id;
    private Float name;

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public boolean equals(Object obj) {
//        super.equals(obj);
//        return this == obj;
        return true;
    }
}
