package com.zburzhynski.jplanner.impl.service;

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
    public boolean delete(Position position) {
        boolean deleted = false;
        if (position != null) {
            positionRepository.delete(position);
            deleted = true;
        }
        return deleted;
    }

//    @Override
//    public List<Position> getRange(Long start, Long end, SortCriteria[] sortCriteria, Map<String, String> filters) {
//        return positionRepository.findRange(start, end, sortCriteria, filters);
//    }
//
//    @Override
//    public Integer countByRange(Map<String, String> filters) {
//        return positionRepository.countByRange(filters);
//    }

    @Override
    public List<Position> getAll() {
        return positionRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsed(Position position) {
        return positionRepository.isUsed(position);
    }

}
