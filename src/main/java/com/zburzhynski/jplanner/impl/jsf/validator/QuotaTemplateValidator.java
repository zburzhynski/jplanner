package com.zburzhynski.jplanner.impl.jsf.validator;

import static com.zburzhynski.jplanner.api.domain.CommonConstant.RUSSIAN_DATE_FORMAT;
import com.zburzhynski.jplanner.api.criteria.QuotaCreateCriteria;
import com.zburzhynski.jplanner.api.service.IResourceTimetableService;
import com.zburzhynski.jplanner.impl.domain.ResourceTimetable;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Quota template validator.
 * <p/>
 * Date: 19.11.2017
 *
 * @author Nikita Shevtsov
 */
@Component
public class QuotaTemplateValidator extends BaseValidator {

    private static final String START_DATE_NOT_SELECTED = "quotaTemplateValidator.startDateNotSelected";
    private static final String END_DATE_NOT_SELECTED = "quotaTemplateValidator.endDateNotSelected";
    private static final String START_TIME_GREATER_THEN_END_TIME = "quotaTemplateValidator.startTimeGreaterThenEndTime";
    private static final String QUOTAS_NOT_SPECIFIED = "quotaTemplateValidator.quotasNotSpecified";
    private static final String TEMPLATE_DATE_RANGE_INCORRECT = "quotaTemplateValidator.templateDateRangeIncorrect";

    @Autowired
    private IResourceTimetableService timetableService;

    /**
     * Validates quota template.
     *
     * @param criteria {@link QuotaCreateCriteria}
     * @return true if valid, else false
     */
    public boolean validate(QuotaCreateCriteria criteria) {
        if (criteria.getStartDate() == null) {
            addMessage(START_DATE_NOT_SELECTED);
            return false;
        }
        if (criteria.getEndDate() == null) {
            addMessage(END_DATE_NOT_SELECTED);
            return false;
        }
        if (criteria.getStartDate().after(criteria.getEndDate()) ||
            criteria.getStartDate().equals(criteria.getEndDate())) {
            addMessage(START_TIME_GREATER_THEN_END_TIME);
            return false;
        }
        if (CollectionUtils.isEmpty(criteria.getQuotas())) {
            addMessage(QUOTAS_NOT_SPECIFIED);
            return false;
        }
        ResourceTimetable resourceTimetable = (ResourceTimetable) timetableService.getById(criteria.getTimetableId());
        if (resourceTimetable.getStartDate().after(criteria.getStartDate()) ||
            resourceTimetable.getEndDate().before(criteria.getEndDate())) {
            addMessage(TEMPLATE_DATE_RANGE_INCORRECT,
                DateUtils.formatDate(resourceTimetable.getStartDate(), RUSSIAN_DATE_FORMAT),
                DateUtils.formatDate(resourceTimetable.getEndDate(), RUSSIAN_DATE_FORMAT));
            return false;
        }
        return true;
    }

}
