package com.cafe24.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//어디에 붙일건지 (parameter? method?) // 클래스에 붙이면 requestMapping 붙어있는 handler모두 붙는다.  
@Target({ElementType.TYPE, ElementType.METHOD})
// retention 유효기간 
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {
	
//	String value() default "user";
//	int test() default 1;
	
	public enum Role {USER, ADMIN}
	
	public Role role() default Role.USER;
	
}

