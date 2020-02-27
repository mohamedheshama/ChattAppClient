package org.project.controller.ChatBot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * MajBot 1.0
 *
 * @author Seyed Majid Khosravi
 */
public class Bot {

    // Store all regular expression matches
    private HashMap<String, String> dictionary;

    // Default state to start the bot
    String level = "0";
    DataParser parser;

    // default constructor
    public Bot(String level, DataParser parser) {
        dictionary = new HashMap<String, String>();

        this.level = level;
        this.parser = parser;
    }

    // get current state message
    public String getMessage() {

        State state = parser.getState(level);
        if (level.equals("0")) {
            State state2 = parser.getState("0");

            // Keyword k= state2.getKeywords().get(0);
            for (Keyword keyword : state2.getKeywords()) {
                if (keyword.points == 10) {
                    dictionary.put(keyword.variable, keyword.keyword);
                    System.out.println("variable" + keyword.variable);
                    System.out.println("value" + keyword.variableValue);
                    System.out.println("keyword" + keyword.keyword);
                    level = "16";
                    state = parser.getState(level);
                }

            }

        }

        return replaceMatches(state.getMessage()).trim();
    }

    // send user message to the bot and get the response
    public String send(String message) {

        if (message.contains("my")) {
            String correct1 = "your";
            message = message.replaceAll("my", correct1);
            System.out.println("print check" + message);
        } else if (message.contains("you")) {

            String correct2 = "my";
            message = message.replaceAll("you", correct2);
            System.out.println("print check" + message);
        } else if (message.contains("i")) {
            String correct2 = "you";
            message = message.replaceAll("i", correct2);
            System.out.println("print check" + message);
        }

        String response = "";
        State state = parser.getState(level);
        if (level.equals("0")) {
           if (message.matches("([a-zA-Z ]+)")){
            Keyword k = new Keyword(message, "16", "", level, "name", 10, "");
            parser.addNewKeyword(state, k, "name");}
        }

        // end of the tree
        if (state.getKeywords().isEmpty()) {
            this.level = "1";
        }

        // match the keyword with given message
        Keyword match = parse(message, state.getKeywords());

        // if no keyword is matched, display one of the invalid answers
        if (match == null) {
            response = parser.getInvalidAnswer();
        } else {

            // if match classname is provided, check to get the dynamic response
            if (match.className.length() > 0) {

                // check for Weather dynamic response
                if (match.className.equals("Weather")) {
                    // weather = new Weather();
                    //  response = weather.getResponse(match.arg);
                    this.level = "1";
                }
            } else {

                // get the new state and return the new message
                if (response.length() == 0) {

                    this.level = match.target;
                    state = parser.getState(level);

                    // if it is end of the tree
                    if (state.getKeywords().isEmpty()) {
                        response = this.getMessage();
                        this.level = "1";

                    }
                }
            }
        }
        return response;
    }

    // parse the given text to find best match in the keywords
    private Keyword parse(String text, ArrayList<Keyword> keylist) {

        // set the default match to none
        int bestMatch = -1;
        Keyword match = null;

        // loop through keywords
        for (int i = 0; i < keylist.size(); i++) {

            // get number of matches of the keyword with given text
            int matches = getMatches(text, keylist.get(i));

            // if match is better than best match, replace it
            if (matches > -1 && matches > bestMatch) {
                match = keylist.get(i);
                bestMatch = matches;
            }
        }

        // add best answers regex variable value into the dictionary for future reference
        if (match != null) {
            if (match.learn.length() > 0) {

                // get training data keyword and description
                String subject = dictionary.get(match.learn);//amr
                String result = match.variableValue;       //my friend
                System.out.println("subject is " + subject);
                System.out.println("varialble value" + result);

                // create a new state for new trained data
                ArrayList<String> messages = new ArrayList<String>();
                messages.add(result);
                State myState = new State(String.valueOf(parser.stateCounter), messages, new ArrayList());
                parser.addState(myState);

                // add the new trained keyword
                Keyword keyword = new Keyword(subject, myState.getId(), "", "", "", 1, "");
                State state = parser.getState("1");
                parser.addNewKeyword(state, keyword, "subject");
                ArrayList<Keyword> keywords = state.getKeywords();
                keywords.add(keyword);

            } else {
                if (match.variableValue.length() > 0) {
                    dictionary.put(match.variable, match.variableValue);
                }
            }
        }
        return match;
    }

    // get number of matches of the given keywords in the given list
    private int getMatches(String text, Keyword keyword) {

        // no match by default
        int result = -1;

        // return 0 match when keyword is *
        if (keyword.keyword.equals("*")) {
            return keyword.points;
        }

        // if regex is expected
        if (keyword.variable.length() > 0) {
            String match = Regex.match(keyword.keyword, text);

            if (match.length() > 0) {
                keyword.variableValue = match;
                return keyword.points + 3;
            }
        }

        String[] words = keyword.keyword.split(" ");

        // loop through list of the keywords
        for (String word : words) {
            System.out.println("word" + word);
            // if current keyword is in the text, add points
            if (text.toLowerCase().indexOf(word.toLowerCase()) >= 0) {
                result = result + keyword.points + 4;////////////////////////////this keyword increase points
            } else {
                // return null if one of the keywords does not exists
                return -1;
            }
        }
        return result;
    }

    // replace given text with variables in the dictionary
    public String replaceMatches(String text) {

        // replace variables within dictionary in the text
        for (Map.Entry<String, String> entry : dictionary.entrySet()) {
            text = text.replaceAll("\\[" + entry.getKey() + "\\]", entry.getValue());
        }

        // remove empty variables tags
        return Regex.clear(text);
    }
}
