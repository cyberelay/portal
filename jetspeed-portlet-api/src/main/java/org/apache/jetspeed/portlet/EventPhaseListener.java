package org.apache.jetspeed.portlet;

public interface EventPhaseListener {

	void beginEventPhase(PortletRequest request);

	void endEventPhase(PortletRequest request);

}
