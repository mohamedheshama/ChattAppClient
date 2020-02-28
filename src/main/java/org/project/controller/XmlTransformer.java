package org.project.controller;

import org.project.controller.messages.Message;
import org.project.controller.messages.jaxb.MessageType;
import org.project.controller.messages.jaxb.MyMessagesType;
import org.project.controller.messages.jaxb.ObjectFactory;
import org.project.model.dao.users.UserStatus;
import org.project.model.dao.users.Users;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class XmlTransformer {
    ArrayList<Message> messages;
    MyMessagesType myMessagesType ;
    int numOfUsers;
    public XmlTransformer(ArrayList<Message> messages , List<Users> to)  {
        this.messages = messages;
        myMessagesType = new MyMessagesType();
        for (Users toUsers : to) {
                messages.forEach(message -> {
                MessageType messageType = tranformTomsgType(message , toUsers);
                myMessagesType.getMessage().add(messageType);
            });
        }

    }
    private MessageType tranformTomsgType(Message msg , Users to){
        MessageType messageType = new MessageType();
        messageType.setFrom(msg.getUser().getName());
        messageType.setTo(to.getName());
        messageType.setColor(msg.getTextFill());
        messageType.setFontSize(msg.getFontSize()+"");
        messageType.setFontWeight(msg.getFontWeight());
        messageType.setContent(msg.getMsg());
        return messageType;
    }
    public void transform() throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
        ObjectFactory factory = new ObjectFactory();
        JAXBElement<MyMessagesType> myMessages = factory.createMyMessages(myMessagesType);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("schema.xsd"));
            marshaller.setSchema(schema);
            marshaller.marshal(myMessages, new FileOutputStream("output3.xml"));
        }catch (SAXParseException e){
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }catch (MarshalException e){
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
