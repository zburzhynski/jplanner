package com.zburzhynski.jplanner.impl.jsf.validator;

import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Quota validator.
 * <p/>
 * Date: 30.08.2015
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class QuotaValidator extends BaseValidator {

    private static final String START_DATE_NOT_SELECTED = "quotaValidator.startDateNotSelected";
    private static final String END_DATE_NOT_SELECTED = "quotaValidator.endDateNotSelected";
    private static final String START_TIME_GREATER_THEN_END_TIME = "quotaValidator.startTimeGreaterThenEndTime";
    private static final String QUOTA_TIME_OVERLAPPED = "quotaValidator.quotaTimeOverlapped";

    /**
     * Validates schedule event.
     *
     * @param quota {@link Quota}
     * @param quotas collection of quota
     * @return true if valid, else false
     */
    public boolean validate(Quota quota, List<Quota> quotas) {
        if (quota.getStartDate() == null) {
            addMessage(START_DATE_NOT_SELECTED);
            return false;
        }
        if (quota.getEndDate() == null) {
            addMessage(END_DATE_NOT_SELECTED);
            return false;
        }
        if (quota.getStartDate().after(quota.getEndDate()) || quota.getStartDate().equals(quota.getEndDate())) {
            addMessage(START_TIME_GREATER_THEN_END_TIME);
            return false;
        }
        for (Quota src : quotas) {
            if (DateUtils.isOverlapExcluding(quota.getStartDate(), quota.getEndDate(),
                src.getStartDate(), src.getEndDate())) {
                addMessage(QUOTA_TIME_OVERLAPPED);
                return false;
            }
        }
        return true;
    }

}
