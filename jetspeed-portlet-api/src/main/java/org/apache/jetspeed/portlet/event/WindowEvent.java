package org.apache.jetspeed.portlet.event;

/**
 * @deprecated Interface WindowEvent is deprecated
 */

public interface WindowEvent extends Event {

    int getEventId();

    int WINDOW_DETACHED = 1001;
    
    int WINDOW_MAXIMIZED = 1004;
    
    int WINDOW_MINIMIZED = 1005;
    
    int WINDOW_RESTORED = 1006;
    
    int WINDOW_CLOSED = 1007;
    
    int WINDOW_SOLO = 1008;
}
