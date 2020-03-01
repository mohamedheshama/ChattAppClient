package org.project.controller.ChatBot;

import org.project.controller.MainDeligator;
import org.project.controller.chat_home.HomeController;
import org.project.controller.chat_home.right_side.MainChatController;
import org.project.controller.messages.Message;

import java.util.Scanner;

public class Chatbot {
    private static Bot bot;
   // private static MainDeligator homeController;

   private MainChatController homeController;



    public Chatbot(){

        // construct a data parser
        DataParser dp = new DataParser();

        // construct new bot with level 0 as default and given data parser
        bot = new Bot("0", dp);
     //   System.out.println(bot.getMessage()+"first");


    }

    public  String getResponse(String s){
        String response = bot.send(s);
        if (response.length() > 0) {
             response +=" ,"+bot.getMessage();
        }else{
            response= bot.getMessage();
        }
        return response;
    }


    public  String botSendMessage(){
        //todo text from bot
      return bot.getMessage();

    }


    public String getText(String text){

    //todo text from user
        return  text;
    }

    public static void main(String args []){
Chatbot chatbot=new Chatbot();
while(true) {
    Scanner scanner = new Scanner(System.in);
    String s = scanner.nextLine();
    chatbot.getResponse(s);
}
    }


}
