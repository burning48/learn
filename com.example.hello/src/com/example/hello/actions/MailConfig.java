package com.example.hello.actions;

public class MailConfig {
	String popServer; 
    String popUser; 
    String popPassword; 
    
 // �� �� �� Ҫ �� �� �� �� �� �� �� �� �� �� �� �� �� �� ��
    String [] strFrom; 
    String [] strSubject;    
    
    // �� �� �� ʾ �� �� �� �� 
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
