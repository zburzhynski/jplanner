package com.zburzhynski.jplanner.impl.jsf.validator;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jplanner.impl.domain.Schedule;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 * Schedule event validator.
 * <p/>
 * Date: 08.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Component
public class ScheduleValidator implements Serializable {

    /**
     * Validates schedule event.
     *
     * @param schedule {@Schedule}
     * @return true if valid, else false
     */
    public boolean validate(Schedule schedule) {
        Set<Boolean> result = new HashSet<>();
        result.add(checkRequiredFields(schedule));
        result.add(checkPeriod(schedule));
        return !result.contains(false);
    }

    private boolean checkRequiredFields(Schedule schedule) {
        if (schedule.getWorkplace() == null) {
            addMessage("Не выбрано рабочее место");
            return false;
        }
        return true;
    }

    private boolean checkPeriod(Schedule schedule) {
        if (schedule.getStartDate().after(schedule.getEndDate())
            || schedule.getStartDate().equals(schedule.getEndDate())) {
            addMessage("Дата начала события больше чем дата окончания");
            return false;
        }
        return true;
    }

    private void addMessage(String validationMessage) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(validationMessage);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

}
