package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.PATIENTS;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.api.domain.View;
import com.zburzhynski.jplanner.impl.domain.Workplace;
import com.zburzhynski.jplanner.impl.jsf.loader.PatientLazyDataLoader;
import com.zburzhynski.jplanner.impl.rest.client.IPatientRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.PatientDto;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.LazyDataModel;

import java.io.Serializable;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Patient bean.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@SessionScoped
public class PatientBean implements Serializable {

    private LazyDataModel<PatientDto> patientModel;

    private PatientDto patient;

    private SearchPatientRequest searchPatientRequest = new SearchPatientRequest();

    private Workplace workplace;

    private Date startDate;

    private Date endDate;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        workplace = (Workplace) JsfUtils.getFlashAttribute(ScheduleBean.WORKPLACE_PARAM);
        startDate = (Date) JsfUtils.getFlashAttribute(ScheduleBean.START_DATE_PARAM);
        endDate = (Date) JsfUtils.getFlashAttribute(ScheduleBean.END_DATE_PARAM);
        search();
    }

    /**
     * Searches patient by criteria.
     *
     * @return path for navigation
     */
    public String searchPatient() {
        search();
        return PATIENTS.getPath();
    }

    /**
     * Cancels patient search.
     */
    public void cancelSearchPatient() {
        searchPatientRequest = new SearchPatientRequest();
        search();
    }

    /**
     * Clears  patient search.
     */
    public void clearSearchFilter() {
        searchPatientRequest = new SearchPatientRequest();
    }

    /**
     * Selects patients.
     *
     * @return path for navigating
     */
    public String selectPatient() {
        if (patient != null) {
            ScheduleBean scheduleBean = JsfUtils.getSessionBean("scheduleBean");
            if (scheduleBean != null) {
                scheduleBean.getEvent().getClient().setJdentPatientId(patient.getId());
                scheduleBean.getEvent().getClient().getPerson().setSurname(patient.getSurname());
                scheduleBean.getEvent().getClient().getPerson().setName(patient.getName());
                scheduleBean.getEvent().getClient().getPerson().setPatronymic(patient.getPatronymic());
                scheduleBean.getEvent().getClient().getPerson().setBirthday(patient.getBirthday());
                scheduleBean.getEvent().getClient().getPerson().setGender(Gender.valueOf(patient.getGender()));
            }
        }
        return redirectToScheduleEvent();
    }

    /**
     * Redirects to schedule event form.
     *
     * @return path for navigating
     */
    public String redirectToScheduleEvent() {
        JsfUtils.setFlashAttribute(ScheduleBean.WORKPLACE_PARAM, workplace);
        JsfUtils.setFlashAttribute(ScheduleBean.START_DATE_PARAM, startDate);
        JsfUtils.setFlashAttribute(ScheduleBean.END_DATE_PARAM, endDate);
        return View.SCHEDULE_EVENT.getPath();
    }

    /**
     * Search patients.
     */
    public void search() {
        patientModel = new PatientLazyDataLoader(patientRestClient, configBean, searchPatientRequest);
    }

    /**
     * Select patient listener.
     *
     * @param event {@link SelectEvent} select event
     */
    public void selectPatientListener(SelectEvent event) {
        patient = (PatientDto) event.getObject();
    }

    /**
     * Unselect patient listener.
     *
     * @param event {@link UnselectEvent} unselect event
     */
    public void unselectPatientListener(UnselectEvent event) {
        patient = null;
    }

    public LazyDataModel<PatientDto> getPatientModel() {
        return patientModel;
    }

    public PatientDto getPatient() {
        return patient;
    }

    public void setPatient(PatientDto patient) {
        this.patient = patient;
    }

    public SearchPatientRequest getSearchPatientRequest() {
        return searchPatientRequest;
    }

    public void setSearchPatientRequest(SearchPatientRequest searchPatientRequest) {
        this.searchPatientRequest = searchPatientRequest;
    }

    public Integer getRowCount() {
        return configBean.getPatientsPerPageCount();
    }

    public void setPatientRestClient(IPatientRestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

}
