package com.zburzhynski.jplanner.impl.jsf.validator;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import com.zburzhynski.jplanner.impl.domain.Schedule;

import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;

/**
 * Schedule event validator.
 * <p/>
 * Date: 08.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class ScheduleValidator implements Serializable {

    /**
     * Validates schedule event.
     *
     * @param schedule {@link Schedule}
     * @throws ValidatorException if any
     */
    public void validate(Schedule schedule) throws ValidatorException {
        checkRequiredFields(schedule);
        checkPeriod(schedule);
    }

    private void checkRequiredFields(Schedule schedule) {
        if (schedule.getWorkplace() == null) {
            addMessage("Не выбрано рабочее место");
            throw new ValidatorException(new FacesMessage());
        }
    }

    private void checkPeriod(Schedule schedule) {
        if (schedule.getStartDate().after(schedule.getEndDate())
            || schedule.getStartDate().equals(schedule.getEndDate())) {
            addMessage("Дата начала события больше чем дата окончания");
            throw new ValidatorException(new FacesMessage(""));
        }
    }

    private void addMessage(String validationMessage) {
        FacesContext context = FacesContext.getCurrentInstance();
        FacesMessage facesMessage = new FacesMessage(validationMessage);
        facesMessage.setSeverity(SEVERITY_ERROR);
        context.addMessage(null, facesMessage);
    }

}
