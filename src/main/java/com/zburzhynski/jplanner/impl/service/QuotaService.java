package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.QuotaSearchCriteria;
import com.zburzhynski.jplanner.api.domain.QuotaType;
import com.zburzhynski.jplanner.api.domain.TimetableStatus;
import com.zburzhynski.jplanner.api.repository.IEmployeeRepository;
import com.zburzhynski.jplanner.api.repository.IQuotaRepository;
import com.zburzhynski.jplanner.api.service.IQuotaService;
import com.zburzhynski.jplanner.impl.domain.Quota;
import com.zburzhynski.jplanner.impl.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * Implementation of {@link IQuotaService} interface.
 * <p/>
 * Date: 02.11.2015
 *
 * @author Vladimir Zburzhynski
 */
@Service("quotaService")
@Transactional(readOnly = true)
public class QuotaService implements IQuotaService<String, Quota> {

    @Autowired
    private IQuotaRepository quotaRepository;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public Quota getById(String id) {
        return (Quota) quotaRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Quota quota) {
        boolean result = false;
        if (quota != null) {
            quotaRepository.saveOrUpdate(quota);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Quota quota) {
        quotaRepository.delete(quota);
    }

    @Override
    public List<Quota> getByCriteria(QuotaSearchCriteria searchCriteria) {
        return quotaRepository.findByCriteria(searchCriteria);
    }

    @Override
    public Integer countByCriteria(QuotaSearchCriteria searchCriteria) {
        return quotaRepository.countByCriteria(searchCriteria);
    }

    @Override
    public Quota getWorkPeriod(Date startDate, Date endDate, String doctorId, String workplaceId) {
        QuotaSearchCriteria searchCriteria = new QuotaSearchCriteria();
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
        searchCriteria.setTypes(Arrays.asList(QuotaType.WORK_TIME));
        searchCriteria.setDoctorId(doctorId);
        searchCriteria.setWorkplaceId(workplaceId);
        searchCriteria.setTimetableStatuses(Arrays.asList(TimetableStatus.APPROVED));
        searchCriteria.setIntersectingPeriod(true);
        List<Quota> quotas = quotaRepository.findByCriteria(searchCriteria);
        if (sameDoctorAndWorkplace(quotas)) {
            return mergePeriod(quotas, startDate, endDate);
        }
        return null;
    }

    @Override
    public List<Quota> getAll() {
        return quotaRepository.findAll();
    }

    private boolean sameDoctorAndWorkplace(List<Quota> quotas) {
        Set<String> doctorIds = quotas.stream().map(quota -> quota.getTimetable().getAvailableResource()
            .getDoctor().getId()).collect(Collectors.toSet());
        Set<String> workplaceIds = quotas.stream().map(quota -> quota.getTimetable().getAvailableResource()
            .getWorkplace().getId()).collect(Collectors.toSet());
        return quotas.size() == doctorIds.size() && quotas.size() == workplaceIds.size();
    }

    private Quota mergePeriod(List<Quota> quotas, Date startDate, Date endDate) {
        Set<Range> ranges = new TreeSet<>();
        for (Quota quota : quotas) {
            ranges.add(new Range(quota.getStartDate(), 1));
            ranges.add(new Range(quota.getEndDate(), -1));
        }
        Range startRange = null;
        int counter = 0;
        for (Range range : ranges) {
            if (startRange == null) {
                startRange = range;
            }
            counter += range.getLimit();
            if (counter == 0) {
                if (DateUtils.beforeOrEquals(startRange.getDate(), startDate) &&
                    DateUtils.afterOrEquals(range.getDate(), endDate)) {
                    Quota quota = new Quota();
                    quota.setStartDate(startRange.getDate());
                    quota.setEndDate(range.getDate());
                    quota.setQuotaType(QuotaType.WORK_TIME);
                    quota.setTimetable(quotas.get(0).getTimetable());
                    return quota;
                }
                startRange = null;
            }
        }
        return null;
    }

    private static class Range implements Comparable<Range> {

        private Date date;

        private int limit;

        public Range(Date date, int limit) {
            this.date = date;
            this.limit = limit;
        }

        public Date getDate() {
            return date;
        }

        public int getLimit() {
            return limit;
        }

        @Override
        public int compareTo(Range o) {
            int result = date.compareTo(o.getDate());
            if (result != 0) {
                return result;
            }
            result = o.getLimit() - this.getLimit();
            if (result != 0) {
                return result;
            }
            return 1;
        }
    }

}
