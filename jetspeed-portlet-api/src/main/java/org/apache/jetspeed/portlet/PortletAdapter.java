package org.apache.jetspeed.portlet;

import java.io.IOException;

import org.apache.jetspeed.portlet.event.ActionEvent;
import org.apache.jetspeed.portlet.event.ActionListener;
import org.apache.jetspeed.portlet.event.MessageEvent;
import org.apache.jetspeed.portlet.event.MessageListener;

public class PortletAdapter implements
		Portlet,
		MessageListener,
		PortletSessionListener,
		ActionListener,
		PortletPageListener,
		PortletTitleListener,
		EventPhaseListener {

	private PortletConfig config;

	/**
	 * @see org.apache.jetspeed.portlet.Portlet#getPortletConfig()
	 */
	public PortletConfig getPortletConfig() {
		return config;
	}

	public PortletContext getPortletContext() {
		return getPortletConfig().getContext();
	}

	public void actionPerformed(ActionEvent event) throws PortletException {
	}

	public void beginEventPhase(PortletRequest request) {
	}

	public void beginPage(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
	}

	public void doTitle(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
	}

	public void endEventPhase(PortletRequest request) {
	}

	public void endPage(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
	}

	public void login(PortletRequest request) throws PortletException {
	}

	/**
	 * Invokes onLogout(PortletSession) method
	 * 
	 * @param request
	 * @throws PortletException
	 */
	public void logout(PortletSession session) throws PortletException {
	}

	public void messageReceived(MessageEvent event) throws PortletException {
	}

	/**
	 * @see org.apache.jetspeed.portlet.extension.LegacyPortlet#init(org.apache.jetspeed.portlet.PortletConfig)
	 */
	public void init(PortletConfig config) throws UnavailableException {
		this.config = config;
	}

	public void initConcrete(PortletSettings settings) throws UnavailableException {
	}

	public void service(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {
		if (request.getMode() == Portlet.Mode.VIEW)
			doView(request, response);
		else if (request.getMode() == Portlet.Mode.EDIT)
			doEdit(request, response);
		else if (request.getMode() == Portlet.Mode.HELP)
			doHelp(request, response);
		else if (request.getMode() == Portlet.Mode.CONFIGURE)
			doConfigure(request, response);
		else
			doView(request, response);
	}

	/**
	 * @see org.apache.jetspeed.portlet.extension.LegacyPortlet#destroy(org.apache.jetspeed.portlet.PortletConfig)
	 */
	public void destroy(PortletConfig config) {

	}

	/**
	 * @see org.apache.jetspeed.portlet.extension.LegacyPortlet#destroyConcrete(org.apache.jetspeed.portlet.PortletSettings)
	 */
	public void destroyConcrete(PortletSettings settings) {

	}

	/**
	 * @param request
	 * @param response
	 */
	protected void doConfigure(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {

	}

	/**
	 * @param request
	 * @param response
	 */
	protected void doHelp(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {

	}

	/**
	 * @param request
	 * @param response
	 */
	protected void doEdit(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {

	}

	/**
	 * @param request
	 * @param response
	 */
	protected void doView(PortletRequest request, PortletResponse response)
			throws PortletException, IOException {

	}

}
