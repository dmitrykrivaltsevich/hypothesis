package com.conjuncte.hypothesis.domain;

import java.util.Collection;

public interface Register {

    /**
     * Makes partial product of current and target register to {@code cellOffset} inclusive.
     *
     * @param target     target register
     * @param cellOffset offset of reigister's cell to which multiplication should be done (inclusive).
     * @return
     */
    Register partialProduct(Register target, Integer cellOffset);

    Cell getCell(Integer cellOffset);

    Integer getCapacity();

    Collection<Cell> getPossibleCellValues();
}
