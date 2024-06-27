package com.gf.biz.common.entity;



import java.io.Serializable;


/**
 * @Author Gf
 */
public abstract class BaseEntity<T extends Serializable,V extends Serializable>  {


    public abstract T getId();
    public abstract void setId(T id);

    public abstract V getCreatedBy();
    public abstract void setCreatedBy(V id);
    public abstract V getUpdatedBy();
    public abstract void setUpdatedBy(V id);
}
