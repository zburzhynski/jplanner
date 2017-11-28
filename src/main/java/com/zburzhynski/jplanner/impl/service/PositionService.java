package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.criteria.EmployeeSearchCriteria;
import com.zburzhynski.jplanner.api.criteria.PositionSearchCriteria;
import com.zburzhynski.jplanner.api.exception.LinkedEmployeeExistException;
import com.zburzhynski.jplanner.api.repository.IEmployeeRepository;
import com.zburzhynski.jplanner.api.repository.IPositionRepository;
import com.zburzhynski.jplanner.api.service.IPositionService;
import com.zburzhynski.jplanner.impl.domain.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link IPositionService} interface.
 * <p/>
 * Date: 04.05.15
 *
 * @author Vladimir Zburzhynski
 */
@Service("positionService")
@Transactional(readOnly = true)
public class PositionService implements IPositionService<String, Position> {

    @Autowired
    private IPositionRepository positionRepository;

    @Autowired
    private IEmployeeRepository employeeRepository;

    @Override
    public Position getById(String id) {
        return (Position) positionRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Position position) {
        boolean result = false;
        if (position != null) {
            positionRepository.saveOrUpdate(position);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public void replicate(Position position) {
        positionRepository.replicate(position);
    }

    @Override
    @Transactional(readOnly = false)
    public void delete(Position position) throws LinkedEmployeeExistException {
        EmployeeSearchCriteria searchCriteria = new EmployeeSearchCriteria();
        searchCriteria.setPositionId(position.getId());
        if (employeeRepository.countByCriteria(searchCriteria) > 0) {
            throw new LinkedEmployeeExistException();
        }
        positionRepository.delete(position);
    }

    @Override
    public List<Position> getByCriteria(PositionSearchCriteria criteria) {
        return positionRepository.findByCriteria(criteria);
    }

    @Override
    public int countByCriteria(PositionSearchCriteria searchCriteria) {
        return positionRepository.countByCriteria(searchCriteria);
    }

    @Override
    public List<Position> getAll() {
        return positionRepository.findAll();
    }

    @Override
    public boolean isUsed(Position position) {
        return positionRepository.isUsed(position);
    }

}
