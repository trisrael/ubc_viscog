/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.InferenceUtil;

/**
 * The design for a single Round/TaskRound (all these things), wanna change the numTrials for a particular Round do it, need to change what 
 * point drawing is being performed? Do it.  If props are asked for that are not directly set on task design, then the BaseDesign check BaseDesign for default..
 * BaseDesign can also be overwritten by yaml.
 * 
 * @author Tristan Goffman(tgoffman@gmail.com) Sep 4, 2011
 */
public class TaskDesign extends Design {

    public BaseDesign getBaseDesign() {
        return baseDesign;
    }

    public void setBaseDesign(BaseDesign baseDesign) {
        this.baseDesign = baseDesign;
    }
    
    private BaseDesign baseDesign;
    
    /**
     * Get properties from TaskDesign (Notice that there is no method to set them, this is to deter later setting.  All setting of properties should have been completed
     * from transition form yaml.
     * @param memberName
     */
    public Object prop(String memberName) {
        Object val = null;
        try {
            val = InferenceUtil.prop(this, memberName);
        } catch (Exception ex) {
            Logger.getLogger(TaskDesign.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (val == null) {
            try {
                val = InferenceUtil.prop(getBaseDesign(), memberName);

            } catch (Exception ex) {
                Logger.getLogger(TaskDesign.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return val;
    }
}