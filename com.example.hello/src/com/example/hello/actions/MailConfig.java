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
    public MailConfig(){
    	
    	popServer = null;
    	popUser = null;
    	popPassword = null;
    }
    public MailConfig(String server,String user,String password){
    	
    	popServer = server;
    	popUser = user;
    	popPassword = password;
    }
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
    public void setPopServer(String server){
    	popServer = server;
    }
    public void setPopUser(String user){
    	popUser = user;
    }
    public void setPopPassword(String password){
    	popPassword = password;
    }
    public void setStrFrom(String[] strFrom){
    	this.strFrom = strFrom;
    }
    public void setStrSubject(String[] strSubject){
    	this.strSubject = strSubject;
    }
}
