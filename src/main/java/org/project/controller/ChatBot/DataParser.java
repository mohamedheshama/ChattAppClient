package org.project.controller.ChatBot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * MajBot 1.0
 *
 * @author Seyed Majid Khosravi
 */
public class DataParser {

    private Document dom;
    private HashMap<String, State> states = new HashMap<String, State>();
    private ArrayList<String> invalidMessages = new ArrayList();
    private int invalidMessageIndex = 0;
    public int stateCounter=1000 ;

    // default constructor
    public DataParser() {

        // Load the XML file and parse it
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {

            //Using factory get an instance of document builder
            DocumentBuilder db = dbf.newDocumentBuilder();

            //parse using builder to get DOM representation of the XML file
            dom = db.parse("data.xml");

            // Load configuration and states from the XML file
            loadConfiguration();
            loadStates();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (SAXException se) {
            se.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    // Load states from XML file
    private void loadStates() {

        // get document element object
        Element docEle = dom.getDocumentElement();

        // get all State node names
        NodeList nl = docEle.getElementsByTagName("State");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get state element
                Element el = (Element) nl.item(i);

                // get state id
                String id = el.getAttribute("id");

                // get all state messages
                ArrayList messages = new ArrayList();
                NodeList messagesNodeList = el.getElementsByTagName("message");

                // if messages node is not null and has children
                if (messagesNodeList != null && messagesNodeList.getLength() > 0) {

                    // loop through all children
                    for (int j = 0; j < messagesNodeList.getLength(); j++) {

                        // get current message element
                        Element elmsg = (Element) messagesNodeList.item(j);

                        // append message node value to the messages list
                        messages.add(elmsg.getFirstChild().getNodeValue());
                    }
                }

                // get keywords in the current state
                ArrayList keywords = getKeywords(el);

                // construct a new State object
                State state = new State(id, messages, keywords);

                stateCounter++;

                // add the state to the states hashmap
                states.put(id, state);
            }
            stateCounter+=states.size()+1;
        }
    }

    // get state object by id
    public State getState(String id) {
        return states.get(id);
    }

    // create a new state
    public void addState(State state) {

        try {
            Element docEle = dom.getDocumentElement();
            System.out.println("childs" + docEle.getChildNodes());
            
            Element child = dom.createElement("State");
            
            
            docEle.appendChild(child);
            System.out.println("childs" + child.getChildNodes());
            Attr attr = dom.createAttribute("id");
            attr.setValue(state.getId());
            child.setAttributeNode(attr);
          //  for(int i=0;i<state.getMessagess().size()-1;i++){
            Element mess = dom.createElement("message");
            mess.appendChild(dom.createTextNode(state.getMessage()));
            child.appendChild(mess);
            System.out.println("childs" + child.getChildNodes());
            
         //   }
             
            
            
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(dom);
            StreamResult streamResult = new StreamResult(new File("data.xml"));

            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging 
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

            transformer.transform(domSource, streamResult);

            System.out.println("Done creating XML File");
            
            
            
            
            
            // docEle.appendChild(docEle)
            states.put(state.getId(), state);
            stateCounter++;
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(DataParser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(DataParser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addNewKeyword(State state, Keyword keyword ,String variable) {
        String newid = state.getId();
        // String id;
        states.get(newid);
        Element docEle = dom.getDocumentElement();

        NodeList nl = docEle.getElementsByTagName("State");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get state element
                Element el = (Element) nl.item(i);

                // get state id
                if (el.getAttribute("id").equals(newid)) {

                    try {
                        Element child = dom.createElement("keyword");
                            child.appendChild(dom.createTextNode(keyword.keyword));
                            Element childref=(Element) el.getElementsByTagName("keyword").item(1);
                            el.insertBefore(child, childref);
                        
                        // set an attribute to staff element
//                        Attr attr = dom.createAttribute("variable");
//                        attr.setValue("subject");
//                        child.setAttributeNode(attr);
                        
                        Attr childnameattr = dom.createAttribute("target");
                        
                        childnameattr.setValue(keyword.target);
                        child.setAttributeNode(childnameattr);
                        
                        
                          Attr var = dom.createAttribute("variable");
                        
                        var.setValue(variable);
                        child.setAttributeNode(var);
                        
                        
                        
                        Attr points = dom.createAttribute("points");
                        
                        points.setValue(""+keyword.points);
                        child.setAttributeNode(points);
                        
                        state.getKeywords().add(keyword);
                        //   states.put(state.getId(), state);
                        // stateCounter++;
                        
                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource domSource = new DOMSource(dom);
                        StreamResult streamResult = new StreamResult(new File("data.xml"));
                        
                        // If you use
                        // StreamResult result = new StreamResult(System.out);
                        // the output will be pushed to the standard output ...
                        // You can use that for debugging
                        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                        
                        transformer.transform(domSource, streamResult);
                        
                        System.out.println("Done creating XML File");
                    } catch (TransformerConfigurationException ex) {
                        Logger.getLogger(DataParser.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (TransformerException ex) {
                        Logger.getLogger(DataParser.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    
                    
                    

                }

            }
        }

    }

    // get all keywords in an State tag
    public ArrayList getKeywords(Element ele) {

        // construct keywords arraylist
        ArrayList keywords = new ArrayList();

        // get all nodes by keyword tag name
        NodeList nl = ele.getElementsByTagName("keyword");

        // if the tag is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all the children
            for (int i = 0; i < nl.getLength(); i++) {

                //get the keyword element
                Element el = (Element) nl.item(i);

                // find the keyword target, classname and argument attributes
                String wordTag = el.getFirstChild().getNodeValue();
                String target = el.getAttribute("target");
                String className = el.getAttribute("className");
                String arg = el.getAttribute("arg");
                String variable = el.getAttribute("variable");
                String points = el.getAttribute("points");
                System.out.println("pontss "+points);
                int p2=0;
                if(points.length()>0){
               p2 =Integer.parseInt(points);
                }
                try {
                    System.out.println("node" + el.getAttribute("id"));
                    //points = Integer.valueOf(el.getAttribute("points"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

                String learn = el.getAttribute("learn");
                // split keyword by comma
                String[] words = wordTag.split(",");

                // loop through all words
                for (String word : words) {

                    // trim the word to remove spaces
                    word = word.trim();

                    // construct a new keyword
                    Keyword keyword = new Keyword(word, target, className, arg, variable,p2, learn);

                    // add the keyword to keywords array list
                    keywords.add(keyword);
                }
            }
        }

        // return all the keywords in the given node
        return keywords;
    }

    // returns one of the invalid messages and move the index to the next message
    public String getInvalidAnswer() {

        // get current answer
        String answer = invalidMessages.get(invalidMessageIndex);

        // increase the index, if it is end of messages, reset the index to 0
        invalidMessageIndex++;
        if (invalidMessageIndex >= invalidMessages.size()) {
            invalidMessageIndex = 0;
        }
        return answer;
    }

    // load cofig tags from data xml file
    private void loadConfiguration() {

        // get document element
        Element docEle = dom.getDocumentElement();

        // get all node names for invalid messages
        NodeList node = docEle.getElementsByTagName("InvalidMessages");

        // get all message nodes inside invalid messages node
        NodeList nl = ((Element) node.item(0)).getElementsByTagName("message");

        // if node is not null and has children
        if (nl != null && nl.getLength() > 0) {

            // loop through all children
            for (int i = 0; i < nl.getLength(); i++) {

                // get message node
                Element el = (Element) nl.item(i);

                // get message and add it to invalid messages array
                String message = el.getFirstChild().getNodeValue();
                invalidMessages.add(message);
            }
        }
    }
}
