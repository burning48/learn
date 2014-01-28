package com.example.hello.actions;

public class MailConfig {
	String popServer; 
    String popUser; 
    String popPassword; 
    
 // 设 置 需 要 过 滤 的 关 键 字 ： 发 件 人 和 邮 件 主 题
    String [] strFrom; 
    String [] strSubject;    
    
    // 是 否 显 示 邮 件 内 容 
    boolean isViewContent=false;
    
    public String getPopServer(){
    	return popServer;
    }
    public String getPopUser(){
    	return popUser;
    }
    public String getPopPassword(){
    	return popPassword;
    }
    public String[] getStrFrom(){
    	return strFrom;
    }
    public String[] getStrSubject(){
    	return strSubject;
    }
}
