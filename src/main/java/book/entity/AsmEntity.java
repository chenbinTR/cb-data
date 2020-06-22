package book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ChenOT
 * @date 2019-12-14
 * @see
 * @since
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class AsmEntity {
    private String topLeftX;
    private String topLeftY;
    private String lowerRightX;
    private String lowerRightY;
}
