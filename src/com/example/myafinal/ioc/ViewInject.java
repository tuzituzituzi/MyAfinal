package com.example.myafinal.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//@Target˵����Annotation�����εĶ���Χ
@Target(ElementType.FIELD)
//@Retention�����˸�Annotation��������ʱ�䳤��
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewInject {
	
	public int id();
	
	public String click() default "";	
}
