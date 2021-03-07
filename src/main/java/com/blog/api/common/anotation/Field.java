package com.blog.api.common.anotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {



    public String queryName() default "";
    /**
     * 字段描述
     *
     * @return
     */
    public String description() default "fieldName";


    /**
     * 字段是否唯一
     *
     * @return
     */
    public boolean unique() default false;


    /**
     * 作为查询字段
     *
     * @return
     */
    public boolean query() default true;


    /**
     * 运算符
     *
     * @return
     */
    public Operator queryOp() default Operator.equal;


    public enum Operator {
        equal, like, notEqual, greaterThan, lessThan

//        private String val;
//        private Operator(String val){
//            this.val=val;
//        }
    }

    ;


}

