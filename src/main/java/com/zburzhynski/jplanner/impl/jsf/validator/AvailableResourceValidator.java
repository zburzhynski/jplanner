package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.api.criteria.AvailableResourceSearchCriteria;
import com.zburzhynski.jplanner.api.service.IAvailableResourceService;
import com.zburzhynski.jplanner.impl.domain.AvailableResource;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Available resource validator.
 * <p/>
 * Date: 18.11.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class AvailableResourceValidator extends BaseValidator {

    private static final String RESOURCE_ALREADY_EXISTS = "availableResourceValidator.resourceAlreadyExists";

    @Autowired
    private IAvailableResourceService resourceService;

    /**
     * Validates available resource.
     *
     * @param resource {@link AvailableResource}
     * @return true if valid, else false
     */
    public boolean validate(AvailableResource resource) {
        AvailableResourceSearchCriteria searchCriteria = new AvailableResourceSearchCriteria();
        searchCriteria.setDoctor(resource.getDoctor());
        searchCriteria.setWorkplace(resource.getWorkplace());
        if (StringUtils.isNotEmpty(resource.getId())) {
            searchCriteria.getExcludedIds().add(resource.getId());
        }
        if (resourceService.countByCriteria(searchCriteria) > 0) {
            addMessage(RESOURCE_ALREADY_EXISTS);
            return false;
        }
        return true;
    }

}
