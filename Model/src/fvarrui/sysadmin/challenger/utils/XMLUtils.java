package fvarrui.sysadmin.challenger.utils;

import java.io.File;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class XMLUtils {
	
	private static XPathFactory xfact = XPathFactory.newInstance();

	public static Document stringToDocument(String xmlStr) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			return builder.parse(new InputSource(new StringReader(xmlStr)));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static Document fileToDocument(File file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder;
		try {
			builder = factory.newDocumentBuilder();
			return builder.parse(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static NodeList search(Object node, String xpathStr) {
		NodeList res = null;
		try {
			XPath xpath = xfact.newXPath();
			res = (NodeList) xpath.evaluate(xpathStr, node, XPathConstants.NODESET);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static Node searchNode(Object node, String xpathStr) {
		Node result = null;
		try {
			XPath xpath = xfact.newXPath();
			result = (Node) xpath.evaluate(xpathStr, node, XPathConstants.NODE);
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String searchText(Object node, String xpathStr) {
		return searchNode(node, xpathStr).getTextContent();
	}

	public static String searchAttribute(Object node, String xpathStr, String attribute) {
		return searchNode(node, xpathStr).getAttributes().getNamedItem(attribute).getTextContent();
	}


}
