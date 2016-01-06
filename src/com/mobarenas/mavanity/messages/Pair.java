package com.mobarenas.mavanity.messages;

/**
 * Created by HP1 on 12/23/2015.
 */
public class Pair {

    private final String find, replace;

    public Pair(String find, String replace) {
        this.find = find;
        this.replace = replace;
    }

    public String getKey() {
        return find;
    }

    public String getValue() {
        return replace;
    }

}
