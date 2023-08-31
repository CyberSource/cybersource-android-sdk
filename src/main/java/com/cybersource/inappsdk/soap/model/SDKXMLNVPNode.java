package com.cybersource.inappsdk.soap.model;

/**
 * Class representing single XML NVP text node.
 *
 * Created by fzubair on 9/25/2015.
 */
public class SDKXMLNVPNode extends SDKXMLNode {
    /**
     * Creates a new XML Element.
     *
     * @param namespace The namespace of the element (can be null).
     * @param name      The name of the element.
     */
    public SDKXMLNVPNode(String namespace, String name) {
        super(namespace, name);
    }

    /**
     * Creates a new XML Element.
     *
     * @param namespace The namespace of the element (can be null).
     * @param nameValuePair The name-value pair of the nvp request.
     */
/*    public SDKXMLNVPNode(String namespace, VMposCyberSourceNameValuePair nameValuePair) {
        super(namespace, nameValuePair.getName());
        this.nameValuePair = nameValuePair;
    }


/*    protected SDKXMLNVPNode(Parcel in){
        super(in);
    }

    public static final Parcelable.Creator<SDKXMLNVPNode> CREATOR = new Parcelable.Creator<SDKXMLNVPNode>() {

        @Override
        public SDKXMLNVPNode createFromParcel(Parcel in) {
            return new SDKXMLNVPNode(in);
        }

        @Override
        public SDKXMLNVPNode[] newArray(int size) {
            return new SDKXMLNVPNode[size];
        }
    };*/

}