package com.conjuncte.hypothesis;

import com.conjuncte.hypothesis.domain.*;

import java.math.BigInteger;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
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
                return 0;
            }
        });
    }};


    public static void main(String[] args) {
// todo: this one is slow
//        FreeRadixRegister targetProduct = new FreeRadixRegister(
//                new BigInteger("122949829", 10).multiply(new BigInteger("122951513", 10)).toString(), 10);

// todo: highlights bugs with 0s (no factos found)
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11903 * 11587), 10);

        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("12231257", 10).multiply(new BigInteger("12413887", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("1182787", 10).multiply(new BigInteger("1571663", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(new BigInteger("124351", 10).multiply(new BigInteger("146383", 10)).toString(), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11777 * 11587), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(4721 * 2129), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(523 * 541), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(7* 7), 10);
//        FreeRadixRegister targetProduct = new FreeRadixRegister(String.valueOf(11* 11), 10);
        Pair<Register, Register> factors = new App().checkAllHypothesises(targetProduct);
        if (factors != null) {
            System.out.println(String.format("Found factors for %s (%d bits):\n%s\n%s",
                    targetProduct,
                    new BigInteger(targetProduct.toString()).bitLength(),
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
            Register factorsFirst = factors.getFirst();
            Register factorsSecond = factors.getSecond();
            Integer cellOffsetToCheck = hypothesis.getCellOffsetToCheck();

//            System.out.println(String.format("Check hypothesis: %s, %s, %s",
//                    factorsFirst,
//                    factorsSecond,
//                    cellOffsetToCheck));

            Register partialProduct = factorsFirst.partialProduct(factorsSecond, cellOffsetToCheck);
            // todo: seems we don't need this extra-check
            if (!partialProduct.hasCell(cellOffsetToCheck)) {
                continue;
            }

            if (targetProduct.getCell(cellOffsetToCheck).equals(partialProduct.getCell(cellOffsetToCheck))) {
                if (targetProduct.getCapacity().equals(partialProduct.getCapacity())
                        && targetProduct.equals(partialProduct)) {
                    return factors;
                } else if (factorsFirst.getCapacity() + factorsSecond.getCapacity() < targetProduct.getCapacity()) {
                    Collection<Hypothesis> derivedHypothesises = createHypothesises(
                            factors,
                            cellOffsetToCheck + 1);
                    hypothesises.addAll(derivedHypothesises);
                }
            }
        }

        // todo: add guava optional
        return null;
    }

    private Collection<Hypothesis> createHypothesises(final Pair<Register, Register> factors, final int cellOffsetToCheck) {
//        System.out.println(String.format("Creating derived hypothesis for: %s, %s, %s",
//                factors.getFirst(),
//                factors.getSecond(),
//                cellOffsetToCheck - 1));

        // todo: dirty implementation - knows about radix = 10
        // todo: add proper collection initialization
        List<Hypothesis> newHypothesises = new ArrayList<Hypothesis>();
        for (final Cell cellFirst : factors.getFirst().getPossibleCellValues()) {
            for (final Cell cellSecond : factors.getSecond().getPossibleCellValues()) {
                newHypothesises.add(new HypothesisImpl(
                        new FreeRadixRegister(cellFirst.getValue() + factors.getFirst().toString(), 10),
                        new FreeRadixRegister(cellSecond.getValue() + factors.getSecond().toString(), 10),
                        cellOffsetToCheck
                ));
            }
        }

        return newHypothesises;
    }
}
