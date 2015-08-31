package com.zburzhynski.jplanner.impl.jsf.bean;

import com.zburzhynski.jplanner.api.domain.QuotaType;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 * Quota type bean.
 * <p/>
 * Date: 31.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class QuotaTypeBean implements Serializable {

    /**
     * Gets quota types.
     *
     * @return quota types
     */
    public QuotaType[] getQuotaTypes() {
        return QuotaType.values();
    }

}
