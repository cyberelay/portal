package org.apache.jetspeed.portlet;

public interface Client {

	String getManufacturer();

	String getModel();

	String getVersion();

	String getUserAgent();

	boolean isCapableOf(Capability capability);

	boolean isCapableOf(Capability acapability[]);

	String getMimeType();

	String getMarkupName();
}
