package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.domain.*;

import java.util.*;

/**
 * Hello world!
 */
public class App {

    // todo: add initial hypothesises
    private Queue<Hypothesis> hypothesises = new LinkedList<Hypothesis>() {{
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("1", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("1", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("3", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("1", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("7", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("1", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("9", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("1", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("3", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("3", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("7", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("3", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("9", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("3", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("7", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("7", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("9", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("7", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
        add(new Hypothesis() {
            @Override
            public Pair<Register, Register> getFactors() {
                return new Pair<Register, Register>() {
                    @Override
                    public Register getFirst() {
                        return new FreeRadixRegister("9", 10);
                    }

                    @Override
                    public Register getSecond() {
                        return new FreeRadixRegister("9", 10);
                    }
                };
            }

            @Override
            public Integer getCellOffsetToCheck() {
                return 1;
            }
        });
    }};


    public static void main(String[] args) {
        FreeRadixRegister targetProduct = new FreeRadixRegister("49", 10);
        Pair<Register, Register> factors = new App().checkAllHypothesises(targetProduct);
        if (factors != null) {
            System.out.println(String.format("Found factors for %s:\n%s\n%s",
                    targetProduct,
                    factors.getFirst(),
                    factors.getSecond()));
        } else {
            System.out.println("Factors not found.");
        }
    }

    private Pair<Register, Register> checkAllHypothesises(Register targetProduct) {
        Hypothesis hypothesis;
        while ((hypothesis = hypothesises.poll()) != null) {
            Pair<Register, Register> factors = hypothesis.getFactors();
            Integer cellOffsetToCheck = hypothesis.getCellOffsetToCheck();

            Register partialProduct = factors.getFirst().partialProduct(factors.getSecond(), cellOffsetToCheck);
            if (!partialProduct.hasCell(cellOffsetToCheck)) {
                continue;
            }

            if (targetProduct.getCell(cellOffsetToCheck).equals(partialProduct.getCell(cellOffsetToCheck))) {
                if (targetProduct.getCapacity().equals(partialProduct.getCapacity())) {
                    return factors;
                } else {
                    Collection<Hypothesis> derivedHypothesises = createHypothesises(factors, cellOffsetToCheck + 1);
                    hypothesises.addAll(derivedHypothesises);
                }
            }
        }

        // todo: add guava optional
        return null;
    }

    private Collection<Hypothesis> createHypothesises(final Pair<Register, Register> factors, final int cellOffsetToCheck) {
        // todo: dirty implementation - knows about radix = 10
        // todo: add proper collection initialization
        List<Hypothesis> newHypothesises = new ArrayList<Hypothesis>();
        for (final Cell cellFirst : factors.getFirst().getPossibleCellValues()) {
            for (final Cell cellSecond : factors.getSecond().getPossibleCellValues()) {
                newHypothesises.add(new Hypothesis() {
                    @Override
                    public Pair<Register, Register> getFactors() {
                        return new Pair<Register, Register>() {
                            @Override
                            public Register getFirst() {
                                return new FreeRadixRegister(cellFirst.getValue() + factors.getFirst().toString(), 10);
                            }

                            @Override
                            public Register getSecond() {
                                return new FreeRadixRegister(cellSecond.getValue() + factors.getSecond().toString(), 10);
                            }
                        };
                    }

                    @Override
                    public Integer getCellOffsetToCheck() {
                        return cellOffsetToCheck;
                    }
                });
            }
        }

        return Collections.emptyList();
    }
}
