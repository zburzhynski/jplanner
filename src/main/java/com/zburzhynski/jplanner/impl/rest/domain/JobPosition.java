package com.zburzhynski.jplanner.impl.rest.domain;

import com.zburzhynski.jplanner.api.domain.PositionType;

import java.util.EnumSet;

/**
 * Contains system job positions, which can't be removed from UI.
 * <p/>
 * Date: 20.09.14
 *
 * @author Vladimir Zburzhynski
 */
public enum JobPosition {

    DOCTOR("dd2e7908-a2f8-45a8-8735-d59b22f9f12a", PositionType.DOCTOR),
    ASSISTANT("4af10251-210c-4fd3-8862-d98f0e946de6", PositionType.ASSISTANT),
    ADMIN("b6d2993b-4f59-4d3b-a5b6-8a82152c1b4f", PositionType.ADMIN);

    private String id;

    private PositionType type;

    /**
     * Constructor.
     *
     * @param id job position id
     */
    private JobPosition(String id, PositionType type) {
        this.id = id;
        this.type = type;
    }

    /**
     * Gets position type by job position id.
     *
     * @param id job position id
     * @return position type
     */
    public static PositionType getById(String id) {
        EnumSet<JobPosition> positions = EnumSet.allOf(JobPosition.class);
        for (JobPosition position : positions) {
            if (position.getId().equals(id)) {
                return position.getType();
            }
        }
        return null;
    }

    /**
     * Gets job position id.
     *
     * @return job position id
     */
    public String getId() {
        return id;
    }

    /**
     * Gets position type.
     *
     * @return position type
     */
    public PositionType getType() {
        return type;
    }

}
