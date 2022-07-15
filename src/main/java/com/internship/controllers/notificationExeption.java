package com.internship.controllers;

class notificationException extends Exception {

    private String msg;
    private Long id;

    public notificationException(String message, Long notId){
        msg = message;
        id = notId;

    }

    public String getMsg(){
        return msg;
    }

}
