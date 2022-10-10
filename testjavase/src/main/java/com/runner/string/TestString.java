package com.runner.string;

import org.junit.Test;

public class TestString {
    @Test
    public void demo01(){
        String a= "jkl";
        String b= "jkl";
        String c= new String("jkl");
        System.out.println(a==b);
    }

    @Test
    public void demo02(){
        String filename = "jklj.jpg";
        String filetype = filename.substring(filename.lastIndexOf("."));
        System.out.println(filetype);
        if(!filetype.equals(".png") && !filetype.equals(".PNG") && !filetype.equals(".JPG") && !filetype.equals(".jpg") && !filetype.equals(".JPEG") && !filetype.equals(".jpeg")){
            System.out.println("格式有误");
        }
        if(!(filetype.equals(".png") || filetype.equals(".PNG") || filetype.equals(".JPG") || filetype.equals(".jpg") || filetype.equals(".JPEG") || filetype.equals(".jpeg"))){
            System.out.println("格式有误");
        }
    }
}
