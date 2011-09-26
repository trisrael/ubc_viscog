/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package StevensLevel;

import interaction.InteractionReactor;
import java.util.List;

/**
 *
 * @author tristangoffman
 */
public interface HasInteractionReactor {
    public boolean hasInteractionReactor();
        
    public List<InteractionReactor> getInteractionReactors();
}
