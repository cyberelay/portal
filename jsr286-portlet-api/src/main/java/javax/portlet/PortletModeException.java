/**
  * Copyright 2006 IBM Corporation.
  */

package javax.portlet;

/**
 * The <CODE>PortletModeException</CODE> is thrown when a portlet
 * tries to use or set a portlet mode that is not supported by the current
 * runtime environment or the portlet.
 */

public class PortletModeException extends PortletException
{

  private transient PortletMode _mode = null;

  private static final long serialVersionUID = 1L;

  /**
   * Constructs a new portlet mode exception with the given text and the
   * portlet mode that caused this exception. The
   * portlet container may use the text and portlet mode write it to a log.
   *
   * @param   text
   *          the exception text
   * @param   mode
   *          the mode causing the exception
   */

  public PortletModeException (String text, PortletMode mode)
  {
    super (text);
    _mode = mode;
  }

  /**
   * Constructs a new portlet mode exception when the portlet needs to do
   * the following:
   * <ul>
   * <li>throw an exception 
   * <li>include a message about the "root cause" that interfered
   *     with its normal operation
   * <li>include a description message
   * <li>include the portlet mode that caused this exception
   * </ul>
   *
   * @param   text
   *          the exception text
   * @param   cause
   *          the root cause
   * @param   mode
   *          the mode causing the exception
   */
  
  public PortletModeException (String text, Throwable cause, PortletMode mode)
  {
    super(text, cause);
    _mode = mode;
  }

  /**
   * Constructs a new portlet mode exception when the portlet needs to throw an
   * exception. The exception message is based on the localized message
   * of the underlying exception and the portlet mode that caused this exception.
   *
   * @param   cause
   *          the root cause
   * @param   mode
   *          the mode causing the exception
   */

  public PortletModeException (Throwable cause, PortletMode mode)
  {
    super(cause);
    _mode = mode;
  }

  /**
   * Returns the unsupported portlet mode causing this exception.
   * 
   * @return  the portlet mode that caused this exception
   */

  public PortletMode getMode()
  {
    return _mode;
  }
}
