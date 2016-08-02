/*
 * Copyright 2006 IBM Corporation.
 *
 */
package javax.portlet.filter;

import java.io.IOException;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.PortletException;

/**
 * The <code>RenderFilter</code> is an object that performs filtering 
 * tasks on either the render request to a portlet, or on the render response from 
 * a portlet, or both.
 * <p>
 * Filters perform filtering in the <code>doFilter</code> method. Every Filter has 
 * access to a <code>FilterConfig</code> object from which it can obtain 
 * its initialization parameters, a reference to the PortletContext 
 * which it can use, for example, to load resources needed for filtering tasks.
 * <p>
 * Filters are configured in the portlet deployment descriptor of a 
 * portlet application. 
 * 
 * @since 2.0
 */
public interface RenderFilter extends PortletFilter {

    /**
     * The <code>doFilter</code> method of the Filter is called by the 
     * portlet container each time a render request/response pair is passed 
     * through the chain due to a client request for a portlet method 
     * at the end of the chain. 
     * <p>
     * The <code>FilterChain</code> passed in to this method allows 
     * the Filter to pass on the render request and response to the next 
     * component in the chain.
     * <p>
     * The <code>doFilter</code> method of a filter will typically be implemented 
     * following this or some subset of the following pattern:
     * <ul>
     *  <li>The method examines the request information.</li>
     *  <li>The method may wrap the request object passed in to 
     *      its doFilter method with a customized implementation 
     *      the request wrapper <code>RenderRequestWrapper</code> 
     *      in order to modify request data.</li>
     *  <li>The method may wrap the response object passed in to its 
     *      <code>doFilter</code> method with a customized implementation 
     *      of the response wrapper <code>RenderResponseWrapper</code> 
     *      to modify response data.</li>
     *  <li>The filter may invoke the next component in the filter chain. 
     *      The next component may be another filter, or if the filter 
     *      making the invocation is the last filter configured in the 
     *      deployment descriptor for this chain, the next component 
     *      is the target method of the portlet. The invocation of the 
     *      next component is effected by calling the <code>doFilter</code>
     *      method on the <code>FilterChain</code> object, and passing in 
     *      the request and response with which it was called or passing 
     *      in wrapped versions it may have created. 
     *      The filter chain�s implementation of the <code>doFilter</code> 
     *      method, provided by the portlet container, must locate the 
     *      next component in the filter chain and invoke its <code>doFilter</code>
     *      method, passing in the appropriate request and response objects. 
     *      Alternatively, the filter chain can block the request by not 
     *      making the call to invoke the next component, leaving the filter 
     *      responsible for filling out the response object.</li>
     *  <li>After invocation of the next filter in the chain, the filter 
     *      may examine the response data.</li>
     *  <li>Alternatively, the filter may have thrown an exception to 
     *      indicate an error in processing. If the filter throws an 
     *      <code>UnavailableException</code> during its <code>doFilter</code> 
     *      processing, the portlet container must not attempt continued 
     *      processing down the filter chain. It may choose to retry the 
     *      whole chain at a later time if the exception is not marked permanent.</li>
     *  <li>When the last filter in the chain has been invoked, the next 
     *      component accessed is the target method on the portlet at 
     *      the end of the chain.</li>
     * </ul>
     * 
     * @param request  the current render request 
     * @param response  the current render response 
     * @param chain  the remaining filter chain
     * @throws IOException  if an IO error occurred in the filter processing
     * @throws PortletException  if a portlet exception occurred in the filter processing
     */
    public void doFilter(RenderRequest request, RenderResponse response,
                         FilterChain chain)
     throws IOException, PortletException;
        
}
