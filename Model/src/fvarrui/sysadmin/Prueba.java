package fvarrui.sysadmin;

import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fvarrui.sysadmin.challenger.utils.XMLUtils;

public class Prueba {
	
	public static void main(String[] args) throws DatatypeConfigurationException {
		
		Document doc = XMLUtils.fileToDocument(new File("eventos.xml"));
		NodeList nodes = doc.getElementsByTagName("Event");
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			String command = XMLUtils.searchText(node, "EventData/Data[@Name='ScriptBlockText']");
			String timeCreated = XMLUtils.searchAttribute(node, "System/TimeCreated", "SystemTime");
			LocalDateTime tc = LocalDateTime.ofInstant(Instant.parse(timeCreated), ZoneId.systemDefault());
			System.out.println(tc + ": " + command);
		}
		
	}

}
