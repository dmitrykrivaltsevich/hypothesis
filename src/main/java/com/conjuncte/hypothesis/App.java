package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.domain.FreeRadixRegister;
import com.conjuncte.hypothesis.domain.Hypothesis;
import com.conjuncte.hypothesis.domain.Pair;
import com.conjuncte.hypothesis.domain.Register;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

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
        Pair<Register, Register> factors = new App().checkAllHypothesises(new FreeRadixRegister("49", 10));
        if (factors != null) {
            System.out.println(String.format("Found:\n%s\n%s", factors.getFirst(), factors.getSecond()));
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

    private Collection<Hypothesis> createHypothesises(Pair<Register, Register> factors, int cellOffsetToCheck) {
        // todo: not implement yet
        return Collections.emptyList();
    }
}
