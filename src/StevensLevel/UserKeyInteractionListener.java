package StevensLevel;

import java.util.EventListener;

/**
 *
 * @author tristangoffman
 */
public interface UserKeyInteractionListener extends EventListener{
    /**
     * An event that should be invoked after an outgoing user interaction has been listened to and dealt.
     * WARNING: This case does not speak to the possibility that multiple listeners send off this event. Could cause an implementer to be 'ready' for dealing with
     * new key interactions sooner than expected.
     */
    public void userInteractionDealtWith();
    
    /**
     * Event which explains to listener that user interactions should be ignored until some later point.
     */
    public void ignoreUserInteractions();
}
