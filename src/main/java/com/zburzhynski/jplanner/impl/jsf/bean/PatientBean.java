package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.PATIENTS;
import static com.zburzhynski.jplanner.api.domain.View.PATIENT_SEARCH;
import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.impl.rest.client.IPatientRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.PatientDto;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientResponse;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * Patient bean.
 * <p/>
 * Date: 24.05.15
 *
 * @author Vladimir Zburzhynski
 */
@ManagedBean
@ViewScoped
public class PatientBean implements Serializable {

    private static final int PATIENT_PAGE_COUNT = 15;

    private LazyDataModel<PatientDto> patientModel;

    private PatientDto patient;

    private SearchPatientRequest searchPatientRequest = new SearchPatientRequest();

    private SearchPatientRequest beforeSearchPatientRequest;

    private Integer rowCount = PATIENT_PAGE_COUNT;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    @ManagedProperty(value = "#{configBean}")
    private ConfigBean configBean;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        patientModel = new LazyDataModel<PatientDto>() {
            @Override
            public List<PatientDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                         Map<String, Object> filters) {
                SearchPatientRequest searchRequest = buildPatientSearchRequest(first, pageSize);
                SearchPatientResponse response = patientRestClient.getByCriteria(searchRequest,
                    configBean.getJdentUrl());
                patientModel.setRowCount(response.getTotalCount());
                return response.getPatients();
            }

            @Override
            public PatientDto getRowData(String rowKey) {
                List<PatientDto> wrapped = (List<PatientDto>) patientModel.getWrappedData();
                if (CollectionUtils.isNotEmpty(wrapped)) {
                    for (PatientDto selected : wrapped) {
                        if (selected.getId().equals(rowKey)) {
                            return selected;
                        }
                    }
                }
                return null;
            }
        };
    }

    /**
     * Searches patient by criteria.
     *
     * @return path for navigation
     */
    public String searchPatient() {
//        searched = true;
//        patients = new LazyDataModel<IPatient>() {
//            @Override
//            public List<IPatient> load(int first, int pageSize, SortCriteria[] sortCriteria,
//                                       Map<String, String> filters) {
//                return patientService.getByCriteria(patientSearchCriteria, Long.valueOf(first),
//                    Long.valueOf(first + pageSize));
//            }
//        };
//        patients.setRowCount(patientService.countByCriteria(patientSearchCriteria));
//        return PATIENTS_VIEW.getPath();
        return null;
    }

    /**
     * Cancels patient search.
     */
    public void cancelSearchPatient() {
//        searched = false;
//        clearSearchFilter();
//        init();
    }

    /**
     * Clears  patient search.
     */
    public void clearSearchFilter() {
        searchPatientRequest = new SearchPatientRequest();
    }

    /**
     * Shows search patient form.
     *
     * @return path for navigation
     */
    public String showSearchPatientForm() {
        beforeSearchPatientRequest = SerializationUtils.clone(searchPatientRequest);
        return PATIENT_SEARCH.getPath();
    }

    /**
     * Hides search patient form.
     *
     * @return path for navigation
     */
    public String hideSearchPatientForm() {
        searchPatientRequest = beforeSearchPatientRequest;
        return PATIENTS.getPath();
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
                scheduleBean.getEvent().setPatientId(patient.getId());
                scheduleBean.getEvent().getPerson().setSurname(patient.getSurname());
                scheduleBean.getEvent().getPerson().setName(patient.getName());
                scheduleBean.getEvent().getPerson().setPatronymic(patient.getPatronymic());
                scheduleBean.getEvent().getPerson().setBirthday(patient.getBirthday());
                scheduleBean.getEvent().getPerson().setGender(Gender.valueOf(patient.getGender()));
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
        return rowCount;
    }

    public void setPatientRestClient(IPatientRestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
    }

    public void setConfigBean(ConfigBean configBean) {
        this.configBean = configBean;
    }

    private SearchPatientRequest buildPatientSearchRequest(int first, int pageSize) {
        SearchPatientRequest searchRequest = new SearchPatientRequest();
        searchRequest.setStart(Long.valueOf(first));
        searchRequest.setEnd(Long.valueOf(first + pageSize));
        return searchRequest;
    }

}
