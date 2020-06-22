package book.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ChenOT
 * @date 2019-12-13
 * @see
 * @since
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookAreaEntity {
    private String areaId;
    private String enId;
    private List<String> enIDs;
    private List<String> chIDs;
    private String enContent;
    private String chId;
    private String chContent;
    private String page;
    private String role;

}
