package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.PATIENTS;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.impl.jsf.loader.PatientLazyDataLoader;
import com.zburzhynski.jplanner.impl.rest.client.IPatientRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.PatientDto;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.primefaces.model.LazyDataModel;

import java.io.Serializable;
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

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        patientModel = new PatientLazyDataLoader(patientRestClient, configBean, searchPatientRequest);
    }

    /**
     * Searches patient by criteria.
     *
     * @return path for navigation
     */
    public String searchPatient() {
        init();
        return PATIENTS.getPath();
    }

    /**
     * Cancels patient search.
     */
    public void cancelSearchPatient() {
        searchPatientRequest = new SearchPatientRequest();
        init();
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
            ScheduleBean scheduleBean = JsfUtils.getBean("scheduleBean");
            if (scheduleBean != null) {
                scheduleBean.getEvent().getPatient().setJdentPatientId(patient.getId());
                scheduleBean.getEvent().getPatient().getPerson().setSurname(patient.getSurname());
                scheduleBean.getEvent().getPatient().getPerson().setName(patient.getName());
                scheduleBean.getEvent().getPatient().getPerson().setPatronymic(patient.getPatronymic());
                scheduleBean.getEvent().getPatient().getPerson().setBirthday(patient.getBirthday());
                scheduleBean.getEvent().getPatient().getPerson().setGender(Gender.valueOf(patient.getGender()));
            }
        }
        return SCHEDULE_EVENT.getPath();
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
