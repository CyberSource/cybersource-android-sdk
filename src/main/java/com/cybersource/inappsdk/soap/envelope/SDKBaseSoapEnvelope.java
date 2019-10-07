package com.cybersource.inappsdk.soap.envelope;

import com.cybersource.inappsdk.soap.model.SDKXMLParentNode;

import java.io.Serializable;

/**
 * Base implementation of SOAP envelope.
 * 
 * @author fzubair
 */
public class SDKBaseSoapEnvelope implements Serializable{
	/** Prefix for SOAP envelope namespace. */
	private static final String NS_PREFIX_SOAPENV = "soapenv";
	/** Prefix for SOAP encoding namespace. */
	private static final String NS_PREFIX_SOAPENC = "soapenc";
	/** Prefix for XSD namespace. */
	private static final String NS_PREFIX_XSD = "xsd";
	/** Prefix for XSI namespace. */
	private static final String NS_PREFIX_XSI = "xsi";
	/** SOAP envelope namespace URL. */
	private static final String NS_SOAPENV = "http://schemas.xmlsoap.org/soap/envelope/";
	/** SOAP encoding namespace URL. */
	private static final String NS_SOAPENC = "http://schemas.xmlsoap.org/soap/encoding/";
	/** XSD namespace URL. */
	private static final String NS_XSD = "http://www.w3.org/2001/XMLSchema";
	/** XSI namespace URL. */
	private static final String NS_XSI = "http://www.w3.org/2001/XMLSchema-instance";
	/** The default encoding to use for envelopes. */
	private static final String DEFAULT_ENCODING = "UTF-8";

    // -- Faizan -- added
    protected static final String HEADER_PREFIX = "wsse";

	/** The SOAP header element. */
	private SDKXMLParentNode header;
	/** The SOAP body element. */
	private SDKXMLParentNode body;
	/** The SOAP envelope element. */
	private SDKXMLParentNode envelopeNode;
	/** The encoding type. */
	private String encoding = DEFAULT_ENCODING;

	private static final String HEADER_TAG = "Header";
	private static final String BODY_TAG = "Body";
	private static final String ENVELOPE_TAG = "Envelope";

	/**
	 * Default envelope constructor. Uses default namespaces.
	 */
	public SDKBaseSoapEnvelope() {
		header = new SDKXMLParentNode(NS_SOAPENV, HEADER_TAG);
		body = new SDKXMLParentNode(NS_SOAPENV, BODY_TAG);
        prepareEnvelopeNode();
	}

	/**
	 * Parametrized envelope constructor that allows to specify envelope
	 * namespace and it's encoding.
	 * 
	 * @param envelopeNs The URI of the envelope namespace.
	 * @param encodingNs The URI of the encoding namespace.
	 */
	public SDKBaseSoapEnvelope(String envelopeNs, String encodingNs) {
		header = new SDKXMLParentNode(envelopeNs, HEADER_TAG);
		body = new SDKXMLParentNode(envelopeNs, BODY_TAG);
		encoding = encodingNs;
		prepareEnvelopeNode();
	}

	/**
	 * Method that prepares the envelope node.
	 */
	private void prepareEnvelopeNode() {
		// create node and add default namespaces with prefixes
		envelopeNode = new SDKXMLParentNode(NS_SOAPENV, ENVELOPE_TAG);
		envelopeNode.declarePrefix(NS_PREFIX_SOAPENV, NS_SOAPENV);
		envelopeNode.addNode(header);
		envelopeNode.addNode(body);
	}

	/**
	 * @return Returns envelope's header node.
	 */
	public SDKXMLParentNode getHeader() {
		return header;
	}

	/**
	 * @return Returns envelope's body node.
	 */
	public SDKXMLParentNode getBody() {
		return body;
	}

	/**
	 * @return Returns envelope node.
	 */
	public SDKXMLParentNode getEnvelopeNode() {
		return envelopeNode;
	}

	/**
	 * Sets the encoding for the envelope.
	 * 
	 * @param encoding Desired encoding.
	 */
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	/**
	 * @return Returns envelope's encoding.
	 */
	public String getEncoding() {
		return encoding;
	}

	/*
	protected SDKBaseSoapEnvelope(Parcel in) {
		readFromParcel(in);
	}

	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(body, flags);
        dest.writeParcelable(header, flags);
        dest.writeParcelable(envelopeNode, flags);
        dest.writeString(encoding);
    }

    public void readFromParcel(Parcel in) {
        body = in.readParcelable(SDKXMLBase.class.getClassLoader());
        header = in.readParcelable(SDKXMLBase.class.getClassLoader());
        envelopeNode = in.readParcelable(SDKXMLBase.class.getClassLoader());
        encoding = in.readString();
    }

    public static final Parcelable.Creator<SDKBaseSoapEnvelope> CREATOR = new Parcelable.Creator<SDKBaseSoapEnvelope>() {

        public SDKBaseSoapEnvelope createFromParcel(Parcel in) {
            return new SDKBaseSoapEnvelope(in);
        }

        public SDKBaseSoapEnvelope[] newArray(int size) {
            return new SDKBaseSoapEnvelope[size];
        }
    };*/
}
