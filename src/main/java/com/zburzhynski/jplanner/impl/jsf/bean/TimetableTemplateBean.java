package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.TimetableTemplate;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Timetable template bean.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class TimetableTemplateBean implements Serializable {

    /**
     * Gets timetable templates.
     *
     * @return timetable templates
     */
    public TimetableTemplate[] getTemplates() {
        return TimetableTemplate.values();
    }

}
