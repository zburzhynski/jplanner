package com.zburzhynski.jplanner.impl.jsf.loader;

import com.zburzhynski.jplanner.impl.jsf.bean.ConfigBean;
import com.zburzhynski.jplanner.impl.rest.client.IPatientRestClient;
import com.zburzhynski.jplanner.impl.rest.domain.PatientDto;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest;
import com.zburzhynski.jplanner.impl.rest.domain.SearchPatientResponse;
import org.apache.commons.collections.CollectionUtils;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import java.util.List;
import java.util.Map;

/**
 * Patient lazy data loader.
 * <p/>
 * Date: 02.07.2015
 *
 * @author Vladimir Zburzhynski
 */
public class PatientLazyDataLoader extends LazyDataModel<PatientDto> {

    private IPatientRestClient patientRestClient;

    private ConfigBean configBean;

    private SearchPatientRequest searchRequest;

    /**
     * Constructor.
     *
     * @param patientRestClient {@link com.zburzhynski.jplanner.impl.rest.client.PatientRestClient}
     * @param configBean        {@link com.zburzhynski.jplanner.impl.jsf.bean.ConfigBean}
     * @param searchRequest     {@link com.zburzhynski.jplanner.impl.rest.domain.SearchPatientRequest}
     */
    public PatientLazyDataLoader(IPatientRestClient patientRestClient, ConfigBean configBean,
                                 SearchPatientRequest searchRequest) {
        this.patientRestClient = patientRestClient;
        this.configBean = configBean;
        this.searchRequest = searchRequest;
    }

    @Override
    public List<PatientDto> load(int first, int pageSize, String sortField, SortOrder sortOrder,
                                 Map<String, Object> filters) {
        searchRequest.setStart(Long.valueOf(first));
        searchRequest.setEnd(Long.valueOf(first + pageSize));
        SearchPatientResponse response = patientRestClient.getByCriteria(searchRequest,
            configBean.getJdentUrl());
        setRowCount(response.getTotalCount());
        return response.getPatients();
    }

    @Override
    public PatientDto getRowData(String rowKey) {
        List<PatientDto> wrapped = (List<PatientDto>) getWrappedData();
        if (CollectionUtils.isNotEmpty(wrapped)) {
            for (PatientDto selected : wrapped) {
                if (selected.getId().equals(rowKey)) {
                    return selected;
                }
            }
        }
        return null;
    }

}
