package book;

/**
 * @author ChenOT
 * @date 2020-04-17
 * @see
 * @since
 */
public enum EnvEnum {
    ALPHA(1),BETA(2),PROD(3);

    private final int type;
    EnvEnum(int type) {
        this.type = type;
    }
    public  Integer getType(){
        return type;
    }
}
