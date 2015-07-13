package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.impl.domain.Cabinet;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

/**
 * Cabinet validator.
 * <p/>
 * Date: 13.07.2015
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class CabinetValidator extends BaseValidator {

    private static final String WORKPLACES_NOT_ADDED = "cabinetValidator.workplacesNotAdded";

    /**
     * Validates cabinet.
     *
     * @param cabinet cabinet to validate
     * @return true if valid, else false
     */
    public boolean validate(Cabinet cabinet) {
        if (CollectionUtils.isEmpty(cabinet.getWorkplaces())) {
            addMessage(WORKPLACES_NOT_ADDED);
            return false;
        }
        return true;
    }

}
