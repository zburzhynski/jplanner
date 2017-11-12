package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.IntersectedQuotaSearchCriteria;
import com.zburzhynski.jplanner.api.domain.QuotaType;
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
    public List<Quota> getIntersecting(IntersectedQuotaSearchCriteria searchCriteria) {
        return quotaRepository.findIntersecting(searchCriteria);
    }

    @Override
    public boolean isWorkPeriod(Date startDate, Date endDate, String doctorId) {
        IntersectedQuotaSearchCriteria searchCriteria = new IntersectedQuotaSearchCriteria();
        searchCriteria.setStartDate(startDate);
        searchCriteria.setEndDate(endDate);
        searchCriteria.setTypes(Arrays.asList(QuotaType.WORK_TIME));
        searchCriteria.setDoctorId(doctorId);
        List<Quota> quotas = quotaRepository.findIntersecting(searchCriteria);
        return containsPeriod(quotas, startDate, endDate);
    }

    @Override
    public List<Quota> getAll() {
        return quotaRepository.findAll();
    }

    private boolean containsPeriod(List<Quota> quotas, Date startDate, Date endDate) {
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
                    return true;
                }
                startRange = null;
            }
        }
        return false;
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
