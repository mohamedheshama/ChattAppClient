package org.project.controller.ChatBot;

import java.util.Scanner;

public class Chatbot {
    private static Bot bot;

    public Chatbot(){



        // construct a data parser
        DataParser dp = new DataParser();

        // construct new bot with level 0 as default and given data parser
        bot = new Bot("0", dp);
        System.out.println(bot.getMessage());


    }

    public static void sendResponse(String s){

        String response = bot.send(s);
        if (response.length() > 0) {
            botSendMessage(response);
        }

        // display new state message
        botSendMessage(bot.getMessage());
    }


    public static void botSendMessage(String s){
        //todo text from bot
        System.out.println(s);

    }


    public String getText(String text){

    //todo text from user
        return  text;
    }

    public static void main(String args []){
Chatbot chatbot=new Chatbot();

        Scanner scanner=new Scanner(System.in);
        String s=scanner.nextLine();
        sendResponse(s);




    }


}
