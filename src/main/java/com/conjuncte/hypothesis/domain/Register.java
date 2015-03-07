package com.conjuncte.hypothesis.domain;

import javax.annotation.Nonnull;
import java.util.Collection;

public interface Register {

    /**
     * Makes partial product of current and target register to {@code cellOffset} inclusive.
     *
     * @param target     target register
     * @param cellOffset offset of reigister's cell to which multiplication should be done (inclusive).
     * @return
     */
    @Nonnull
    Register partialMultiply(Register target, Integer cellOffset);

    @Nonnull
    Cell getCell(Integer cellOffset);

    @Nonnull
    Integer getCapacity();

    @Nonnull
    Collection<Cell> getPossibleCellValues();
}
