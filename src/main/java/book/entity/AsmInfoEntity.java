package book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author ChenOT
 * @date 2020-01-02
 * @see
 * @since
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AsmInfoEntity {
    private Map<Integer, List<AsmEntity>> asmEntityMap;
    private Integer type;
}
