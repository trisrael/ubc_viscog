/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.util.logging.Level;
import java.util.logging.Logger;
import util.InferenceUtil;

/**
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
