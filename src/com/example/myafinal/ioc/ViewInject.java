package com.example.myafinal.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target说明了Annotation所修饰的对象范围
@Target(ElementType.FIELD)
//@Retention定义了该Annotation被保留的时间长短
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
	
	public int id();
	
	public String click() default "";	
}
