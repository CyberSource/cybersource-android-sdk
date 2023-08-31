package com.cybersource.inappsdk.soap.parser;

import android.util.Log;

import com.cybersource.inappsdk.soap.model.SDKXMLNVPNode;
import com.cybersource.inappsdk.soap.model.SDKXMLTextNode;
import com.cybersource.inappsdk.soap.envelope.SDKBaseSoapEnvelope;
import com.cybersource.inappsdk.soap.model.SDKXMLAttribute;
import com.cybersource.inappsdk.soap.model.SDKXMLNode;
import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Basic SOAP parser.
 * 
 * @author fzubair
 */
public class SDKSoapParser {

	//private static final String XML_START_TAG = "<?xml version='1.0' encoding='UTF-8' standalone='yes' ?>";
    private static final String XML_START_TAG = "<?xml version='1.0' encoding='UTF-8'?>";
	private static final char OPENING_START_TAG = '<';
	private static final String CLOSING_START_TAG = "</";
	private static final char END_TAG = '>';

	private static final char TAG_COLON = ':';
	private static final char TAG_EQUAL = '=';
	private static final char TAG_QUOTE = '\"';

	private static final String NS_TAG = "xmlns";
	public static final String EQUAL = "=";
	public static final String NEW_LINE = "\n";

	/**
	 * Stores mapping prefixes to namespaces.
	 */
	private static Map<String, String> prefixesNamespacesMapping;
	/**
	 * Stores declared namespaces without prefixes. Used to avoid adding them
	 * multiple times in different sub-nodes.
	 */
	private static List<String> declaredNamespacesWithoutPrefixes;

	/**
	 * Generates XML SOAP request basing on the SOAP envelope.
	 * 
	 * @param envelope Provided SOAP envelope.
	 * @return XML SOAP request as a String.
	 */
	public static String parseEnvelope(SDKBaseSoapEnvelope envelope) {

        if(envelope == null)
            return null;
		prefixesNamespacesMapping = new HashMap<String, String>();
		declaredNamespacesWithoutPrefixes = new ArrayList<String>();
		// parse all available prefixes to the global HashMap used later
		parsePrefixes(envelope.getEnvelopeNode());
		/*
		 * create String using StringBuilder (this class is not synchronized,
		 * but we don't need synchronization here)
		 */
		StringBuilder sb = new StringBuilder();
		sb.append(XML_START_TAG);
		parseChildNodes(sb, envelope.getEnvelopeNode());

        //Log.d("SOAP Request", sb.toString());
		return sb.toString();
	}

	/**
	 * Parses provided node and all child nodes if any.
	 * 
	 * @param sb StringBuilder object where the subsequent elements are
	 *            appended.
	 * @param node Node to be parsed.
	 */
	private static void parseChildNodes(StringBuilder sb, SDKXMLNode node) {
		if(node instanceof SDKXMLNVPNode){
			parseNVPChileNodes(sb, (SDKXMLNVPNode)node);
			return;
		}
		sb.append(OPENING_START_TAG);
		appendName(sb, node);
		appendNamespace(sb, node);
		appendAttributes(sb, node);

		sb.append(END_TAG);

		/*
		 * Determine the type of node and if it has children then recursively
		 * parse them as well.
		 */
		if (node instanceof SDKXMLParentNode) {
			List<SDKXMLNode> nodes = ((SDKXMLParentNode)node).getChildNodes();
			for (Iterator<SDKXMLNode> iterator = nodes.iterator(); iterator.hasNext();) {
				SDKXMLNode SDKXMLNode = iterator.next();
				// parse child nodes
				parseChildNodes(sb, SDKXMLNode);
			}
		} else if (node instanceof SDKXMLTextNode) {
			// add text value for the text node
			sb.append(((SDKXMLTextNode)node).getValue());
		}

		// closing tag with node name
		sb.append(CLOSING_START_TAG);
		appendName(sb, node);
		sb.append(END_TAG);
	}

	/**
	 * Process a NVP Node and add it to the final XML
	 *
	 * @param sb StringBuilder object where the subsequent elements are
	 *            appended.
	 * @param node Node to be parsed.
	 */
	private static void parseNVPChileNodes(StringBuilder sb, SDKXMLNVPNode node){
/*		VMposCyberSourceNameValuePair pair = node.getNameValuePair();
		sb.append(pair.getName() + EQUAL +pair.getValue() + NEW_LINE);*/
	}

	/**
	 * Adds node prefixes to global prefixes list.
	 * 
	 * @param node Parent node.
	 */
	private static void parsePrefixes(SDKXMLNode node) {
		prefixesNamespacesMapping.putAll(node.getDeclaredPrefixes());
		if (node instanceof SDKXMLParentNode) {
			List<SDKXMLNode> nodes = ((SDKXMLParentNode)node).getChildNodes();
			for (Iterator<SDKXMLNode> iterator = nodes.iterator(); iterator.hasNext();) {
				SDKXMLNode SDKXMLNode = iterator.next();
				parsePrefixes(SDKXMLNode);
			}
		}
	}

	/**
	 * Finds prefix for desired namespace.
	 * 
	 * @param namespace Namespace for which we look for the prefix.
	 * @return Namespace declared prefix as a String or null if there is no such
	 *         namespace.
	 */
	private static String findPrefixForNamespace(String namespace) {
		for (Entry<String, String> entry : prefixesNamespacesMapping.entrySet()) {
			if (entry.getValue().equals(namespace)) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Appends node name with prefix if provided.
	 * 
	 * @param sb StringBuilder object where the subsequent elements are
	 *            appended.
	 * @param node Node to be parsed.
	 */
	private static void appendName(StringBuilder sb, SDKXMLNode node) {
		String namespace = node.getNamespace();
		if (namespace != null && namespace.length() > 0) {
			String prefix = findPrefixForNamespace(namespace);
			if (prefix != null && prefix.length() > 0) {
				sb.append(prefix);
				sb.append(TAG_COLON);
			}
		}
		sb.append(node.getName());
	}

	/**
	 * Appends node attributes with prefix if provided.
	 * 
	 * @param sb StringBuilder object where the subsequent elements are
	 *            appended.
	 * @param node Node to be parsed.
	 */
	private static void appendAttributes(StringBuilder sb, SDKXMLNode node) {
		Collection<SDKXMLAttribute> attrs = node.getAttributes();
		if (attrs == null) {
			return;
		}
		String namespace = node.getNamespace();
		String prefix = null;
		// check if this attribute should have prefix added
		if (namespace != null && namespace.length() > 0) {
			prefix = findPrefixForNamespace(namespace);
		}
		for (Iterator<SDKXMLAttribute> iterator = attrs.iterator(); iterator.hasNext();) {
			SDKXMLAttribute att = iterator.next();
			sb.append(' ');
            // add prefix if appropriate
            // -- Faizan -- added 'att.getNamespace() != null' since if an attribute doesn't have a namespace it should not be appended with a prefix.
            //if (prefix != null && prefix.length() > 0) {
            if (att.getNamespace() != null && prefix != null && prefix.length() > 0) {
				sb.append(prefix);
				sb.append(TAG_COLON);
			}
			sb.append(att.getName());
			sb.append(TAG_EQUAL);
			sb.append(TAG_QUOTE);
			sb.append(att.getValue());
			sb.append(TAG_QUOTE);
		}
	}

	/**
	 * Appends node namespaces declarations.
	 * 
	 * @param sb StringBuilder object where the subsequent elements are
	 *            appended.
	 * @param node Node to be parsed.
	 */
	private static void appendNamespace(StringBuilder sb, SDKXMLNode node) {
		// flag determining if namespace is already declared
		boolean isNSDeclared = false;
		String nodeNS = node.getNamespace();
		for (Entry<String, String> entry : node.getDeclaredPrefixes().entrySet()) {
			if (entry.getValue().equals(nodeNS)) {
				isNSDeclared = true;
			}
			sb.append(' ');
			sb.append(NS_TAG);
			sb.append(TAG_COLON);
			sb.append(entry.getKey());
			sb.append(TAG_EQUAL);
			sb.append(TAG_QUOTE);
			sb.append(entry.getValue());
			sb.append(TAG_QUOTE);
		}
		/*
		 * check if namespace is not declared and is not null
		 */
		if (!isNSDeclared && nodeNS != null) {
			/*
			 * skip adding namespace if is was already declared before or is one
			 * of the global namespaces with prefixes
			 */
			if (declaredNamespacesWithoutPrefixes.contains(nodeNS) || prefixesNamespacesMapping.containsValue(nodeNS)) {
				return;
			}
			// add namespace to declared namespaces
			declaredNamespacesWithoutPrefixes.add(nodeNS);
			sb.append(' ');
			sb.append(NS_TAG);
			sb.append(TAG_EQUAL);
			sb.append(TAG_QUOTE);
			sb.append(nodeNS);
			sb.append(TAG_QUOTE);
		}
	}
}
