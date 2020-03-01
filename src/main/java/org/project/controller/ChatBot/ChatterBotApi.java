package org.project.controller.ChatBot;

import com.google.code.chatterbotapi.ChatterBot;
import com.google.code.chatterbotapi.ChatterBotFactory;
import com.google.code.chatterbotapi.ChatterBotSession;
import com.google.code.chatterbotapi.ChatterBotType;

public class ChatterBotApi {

    public String getMessage(String message) {
        ChatterBotFactory factory = new ChatterBotFactory();
        String response = "";
        try {
            ChatterBot bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession bot2session = bot2.createSession();
            response = bot2session.think(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;

    }
}

