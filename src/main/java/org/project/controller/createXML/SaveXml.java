package org.project.controller.createXML;

import javafx.scene.paint.Color;
import org.project.controller.messages.Message;
import org.project.model.dao.users.Users;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.stream.*;
import javax.xml.stream.events.*;
import javax.xml.transform.stax.StAXSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SaveXml {
    FileOutputStream fs;
    File fXmlFile;

    public static void main(String[] args) {
        Message message = new Message();
        Users users = new Users();
        users.setName("amr");
        users.setPhoneNumber("01091390584");
        users.setEmail("amrelbaz@gmail.com");
        message.setUser(users);
        message.setMsg("Hello From the other side");

        Message message2 = new Message();
        Users users2 = new Users();
        users2.setName("amr");
        users2.setPhoneNumber("01091390584");
        users2.setEmail("amrelbaz@gmail.com");
        message2.setUser(users);
        message2.setMsg("Hello From the other side");

        Message message3 = new Message();
        Users users3 = new Users();
        users3.setName("amr");
        users3.setPhoneNumber("01091390584");
        users3.setEmail("amrelbaz@gmail.com");
        message3.setUser(users);
        message3.setMsg("Hello From the other side");


        ArrayList<Message> messages = new ArrayList<Message>();
        messages.add(message);
        messages.add(message2);
        messages.add(message3);

        new SaveXml().writeXmlChat(messages , "hamed" ,"output.xml ");
    }
    public void writeXmlChat(List<Message> messages, String toUser, String path){
        try {
            XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
            XMLEventWriter eventWriter=null;
            fXmlFile = new File(path);
            fs = new FileOutputStream(fXmlFile);
            eventWriter = outputFactory.createXMLEventWriter(fs); // "output.xml"
            XMLEventFactory eventFactory = XMLEventFactory.newInstance();
            XMLEvent end = eventFactory.createDTD("\n");
            XMLEvent tab = eventFactory.createDTD("\t");
            StartDocument startDocument = eventFactory.createStartDocument();
            eventWriter.add(startDocument);
            eventWriter.add(end);
            StartElement rssStart = eventFactory.createStartElement("", "", "myMessages");
            eventWriter.add(rssStart);
            for (Message msg : messages) {
                eventWriter.add(end);
                eventWriter.add(tab);
                eventWriter.add(eventFactory.createStartElement("", "", "message"));
                eventWriter.add(end);
                createNode(eventWriter, "from", msg.getUser().getName());
                createNode(eventWriter, "to", toUser);
                createNode(eventWriter, "content", msg.getMsg());
                createNode(eventWriter, "color", msg.getTextFill());
                createNode(eventWriter, "fontSize", Integer.toString(msg.getFontSize()));
                createNode(eventWriter, "fontWeight", msg.getFontWeight());
                //createNode(eventWriter, "fontStyle", msg.getFontStyle().name());
                eventWriter.add(tab);
                eventWriter.add(eventFactory.createEndElement("", "", "message"));

            }
            eventWriter.add(end);
            eventWriter.add(eventFactory.createEndElement("", "", "myMessages"));
            eventWriter.add(eventFactory.createEndDocument());
            eventWriter.close();
            fs.close();

            XMLStreamReader reader = XMLInputFactory.newInstance().createXMLStreamReader(new FileInputStream("output.xml"));

            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("schema.xsd"));

            Validator validator = schema.newValidator();
            validator.validate(new StAXSource(reader));

            //no exception thrown, so valid

        } catch (XMLStreamException ex) {
            Logger.getLogger(SaveXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SaveXml.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
    public void createNode(XMLEventWriter eventWriter, String name, String value) throws XMLStreamException {
        XMLEventFactory eventFactory = XMLEventFactory.newInstance();
        XMLEvent end = eventFactory.createDTD("\n");
        XMLEvent tab = eventFactory.createDTD("\t\t");
        StartElement sElement = eventFactory.createStartElement("", "", name);
        eventWriter.add(tab);
        eventWriter.add(sElement);
        Characters characters = eventFactory.createCharacters(value);
        eventWriter.add(characters);
        EndElement eElement = eventFactory.createEndElement("", "", name);
        eventWriter.add(eElement);
        eventWriter.add(end);
    }
}
