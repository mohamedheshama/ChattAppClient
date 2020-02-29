package org.project.controller.ChatBot;


//package com.google.code.chatterbotapi.test;

import com.google.code.chatterbotapi.*;

import java.util.Scanner;

public class ChatterBotApiTest {

    public String getMessage(String message) {
        ChatterBotFactory factory = new ChatterBotFactory();
        String response="";
        ChatterBot bot1 = null;
        try {
            bot1 = factory.create(ChatterBotType.CLEVERBOT);
            ChatterBotSession bot1session = bot1.createSession();
             response = bot1session.think(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
        // ChatterBotSession bot2session = bot2.createSession();
        return response;

}
    }

