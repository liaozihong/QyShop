package com.zoey.goods.model.enums;

/**
 * Created in 2020.01.19
 *
 * @author ZH ·L
 */
public enum AreasTypeEnum {
    /**
     * Province areas type enum.
     */
    PROVINCE("省级", 1),
    /**
     * City areas type enum.
     */
    CITY("市级", 2),
    /**
     * Area areas type enum.
     */
    AREA("县级", 3),
    /**
     * Street areas type enum.
     */
    STREET("街道", 4);
    private String name;
    private int type;

    AreasTypeEnum(String name, int type) {
        this.name = name;
        this.type = type;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets type.
     *
     * @return the type
     */
    public int getType() {
        return type;
    }
}
