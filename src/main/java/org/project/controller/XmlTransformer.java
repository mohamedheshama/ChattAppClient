package org.project.controller;

import org.project.controller.messages.Message;
import org.project.controller.messages.jaxb.MessageType;
import org.project.controller.messages.jaxb.MyMessagesType;
import org.project.controller.messages.jaxb.ObjectFactory;
import org.project.model.dao.users.Users;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.*;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XmlTransformer {
    ArrayList<Message> messages;
    MyMessagesType myMessagesType;
    int numOfUsers;

    public XmlTransformer() {
    }

    private MessageType tranformTomsgType(Message msg, Users users, Users me) {
        MessageType messageType = new MessageType();
        messageType.setSender(me.getName());
        messageType.setFrom(msg.getUser().getName());
        messageType.setTo(users.getName());
        messageType.setColor(msg.getTextFill());
        messageType.setFontSize(msg.getFontSize() + "");
        messageType.setFontWeight(msg.getFontWeight());
        messageType.setContent(msg.getMsg().trim());
        return messageType;
    }

    public void transform(ArrayList<Message> messages, Users me) throws JAXBException {
        System.out.println("messages" + messages);

        myMessagesType = new MyMessagesType();
        messages.forEach(message -> {
            List<Users> receivers = message.getUsers().stream().filter(users -> users.getId() != message.getUser().getId()).collect(Collectors.toList());
            System.out.println("recivers : " + receivers);
            receivers.forEach(receiver -> {
                MessageType messageType = tranformTomsgType(message, receiver, me);
                myMessagesType.getMessage().add(messageType);
            });
        });
        JAXBContext context = JAXBContext.newInstance(ObjectFactory.class);
        ObjectFactory factory = new ObjectFactory();
        JAXBElement<MyMessagesType> myMessages = factory.createMyMessages(myMessagesType);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.setProperty("com.sun.xml.bind.xmlHeaders", "<?xml-stylesheet type='text/xsl' href=\"" + "message.xsl" + "\" ?>");
        try {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File("schema.xsd"));
            marshaller.setSchema(schema);
            marshaller.marshal(myMessages, new FileOutputStream("output3.xml"));
            System.out.println("XML Saved Succefully");

        } catch (MarshalException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}
