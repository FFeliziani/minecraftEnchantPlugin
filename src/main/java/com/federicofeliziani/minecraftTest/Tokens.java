package com.federicofeliziani.minecraftTest;

/**
 * Created by f3l1x on 31/08/14.
 */
public enum Tokens {

    /*
     * @author F3l1x
     */
    PLAYER_NAME("%playerName%"),
    PLAYER_LEVEL("%playerLevel%"),
    DATABASE_IP("databaseIP"),
    DATABASE_NAME("databaseName"),
    DATABASE_USER("databaseUser"),
    DATABASE_PASSWORD("databasePassword"),
    TABLE_PREFIX("MEP_"),
    USERS_TABLE("USERS")
    ;

    private final String value;

    /*
     * @param value
     */
    private Tokens(String value) {
        this.value = value;
    }

    /*(Non-Javadoc)
     * @see java.lang.enum#toString()
     */
    @Override
    public String toString() {
        return value;
    }
}
