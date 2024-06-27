package com.gf.biz.common.entity;

import java.io.Serializable;
import java.util.HashMap;

public class BOContextImpl<T,V> extends HashMap<T,V> implements BOContext<T,V>, Serializable {

    private static final long serialVersionUID = 3658221590414947614L;

    public BOContextImpl() {
    }
}
