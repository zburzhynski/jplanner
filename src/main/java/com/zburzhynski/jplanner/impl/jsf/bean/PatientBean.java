package com.zburzhynski.jplanner.impl.jsf.bean;

import static com.zburzhynski.jplanner.api.domain.View.SCHEDULE_EVENT;
import com.zburzhynski.jplanner.api.domain.Gender;
import com.zburzhynski.jplanner.impl.rest.client.IPatientRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.Patient;
import com.zburzhynski.jplanner.impl.rest.domain.PatientResponse;
import com.zburzhynski.jplanner.impl.rest.domain.PatientSearchRequest;
import com.zburzhynski.jplanner.impl.util.JsfUtils;
import org.apache.commons.collections.CollectionUtils;
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

    private LazyDataModel<Patient> patientModel;

    private Patient patient;

    private Integer rowCount = PATIENT_PAGE_COUNT;

    @ManagedProperty(value = "#{patientRestClient}")
    private IPatientRestClient patientRestClient;

    /**
     * Inits bean state.
     */
    @PostConstruct
    public void init() {
        patientModel = new LazyDataModel<Patient>() {
            @Override
            public List<Patient> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                      Map<String, Object> filters) {
                PatientSearchRequest searchRequest = buildPatientSearchRequest(first, pageSize);
                PatientResponse response = patientRestClient.getByCriteria(searchRequest);
                patientModel.setRowCount(response.getTotalCount());
                return response.getPatients();
            }

            @Override
            public Patient getRowData(String rowKey) {
                List<Patient> wrapped = (List<Patient>) patientModel.getWrappedData();
                if (CollectionUtils.isNotEmpty(wrapped)) {
                    for (Patient selected : wrapped) {
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
                scheduleBean.getEvent().getPerson().setGender(patient.getGender().equals(Gender.M.name())
                    ? Gender.M : Gender.F);
            }
        }
        return SCHEDULE_EVENT.getPath();
    }

    public LazyDataModel<Patient> getPatientModel() {
        return patientModel;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Integer getRowCount() {
        return rowCount;
    }

    public void setPatientRestClient(IPatientRestClient patientRestClient) {
        this.patientRestClient = patientRestClient;
    }

    private PatientSearchRequest buildPatientSearchRequest(int first, int pageSize) {
        PatientSearchRequest searchRequest = new PatientSearchRequest();
        searchRequest.setStart(Long.valueOf(first));
        searchRequest.setEnd(Long.valueOf(first + pageSize));
        return searchRequest;
    }

}
