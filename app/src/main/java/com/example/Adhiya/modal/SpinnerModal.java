package com.example.Adhiya.modal;

public class SpinnerModal {
    private String text;
    private String value;


    public SpinnerModal(String text, String value){
        this.text = text;
        this.value = value;
    }

    public void setText(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }

    public void setValue(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }
}
