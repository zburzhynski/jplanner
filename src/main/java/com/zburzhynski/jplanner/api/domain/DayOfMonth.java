package com.zburzhynski.jplanner.api.domain;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

/**
 * Day of month.
 * <p/>
 * Date: 29.08.2015
 *
 * @author Vladimir Zburzhynski
 */
public enum DayOfMonth {

    DAY_1(1),
    DAY_2(2),
    DAY_3(3),
    DAY_4(4),
    DAY_5(5),
    DAY_6(6),
    DAY_7(7),
    DAY_8(8),
    DAY_9(9),
    DAY_10(10),
    DAY_11(11),
    DAY_12(12),
    DAY_13(13),
    DAY_14(14),
    DAY_15(15),
    DAY_16(16),
    DAY_17(17),
    DAY_18(18),
    DAY_19(19),
    DAY_20(20),
    DAY_21(21),
    DAY_22(22),
    DAY_23(23),
    DAY_24(24),
    DAY_25(25),
    DAY_26(26),
    DAY_27(27),
    DAY_28(28),
    DAY_29(29),
    DAY_30(30),
    DAY_31(31);

    private Integer number;

    private DayOfMonth(int number) {
        this.number = number;
    }

    /**
     * Gets even day numbers.
     *
     * @return even day numbers
     */
    public List<Integer> evenDayNumbers() {
        EnumSet<DayOfMonth> evenDays = EnumSet.of(DAY_2, DAY_4, DAY_6, DAY_8, DAY_10, DAY_12, DAY_14,
            DAY_16, DAY_18, DAY_20, DAY_22, DAY_24, DAY_26, DAY_28, DAY_30);
        return convert(evenDays);
    }

    /**
     * Gets odd day numbers.
     *
     * @return odd day numbers
     */
    public List<Integer> oddDayNumbers() {
        EnumSet<DayOfMonth> oddDays = EnumSet.of(DAY_1, DAY_3, DAY_5, DAY_7, DAY_9, DAY_11, DAY_13,
            DAY_15, DAY_17, DAY_19, DAY_21, DAY_23, DAY_25, DAY_27, DAY_29, DAY_31);
        return convert(oddDays);
    }

    public Integer getNumber() {
        return number;
    }

    private List<Integer> convert(EnumSet<DayOfMonth> days) {
        List<Integer> numbers = new ArrayList<>();
        for (DayOfMonth day : days) {
            numbers.add(day.getNumber());
        }
        return numbers;
    }

}
