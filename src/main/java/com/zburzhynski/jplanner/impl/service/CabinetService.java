package com.zburzhynski.jplanner.impl.service;

import com.zburzhynski.jplanner.api.repository.ICabinetRepository;
import com.zburzhynski.jplanner.api.service.ICabinetService;
import com.zburzhynski.jplanner.impl.domain.Cabinet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of {@link ICabinetService} interface.
 * <p/>
 * Date: 05.05.2015
 *
 * @author Mikalai Karabeika
 */
@Service("cabinetService")
@Transactional(readOnly = true)
public class CabinetService implements ICabinetService<String, Cabinet> {

    @Autowired
    private ICabinetRepository cabinetRepository;

    @Override
    public Cabinet getById(String id) {
        return (Cabinet) cabinetRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = false)
    public boolean saveOrUpdate(Cabinet cabinet) {
        boolean result = false;
        if (cabinet != null) {
            cabinetRepository.saveOrUpdate(cabinet);
            result = true;
        }
        return result;
    }

    @Override
    @Transactional(readOnly = false)
    public boolean delete(Cabinet cabinet) {
        boolean deleted = false;
        if (cabinet != null) {
            cabinetRepository.delete(cabinet);
            deleted = true;
        }
        return deleted;
    }

    @Override
    public List<Cabinet> getAll() {
        return cabinetRepository.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isUsed(Cabinet cabinet) {
        return cabinetRepository.isUsed(cabinet);
    }

}