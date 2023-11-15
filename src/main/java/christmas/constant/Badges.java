package christmas.constant;


import java.math.BigDecimal;


public enum Badges {
    SANTA("산타", BigDecimal.valueOf(20000)),
    TREE("트리", BigDecimal.valueOf(10000)),
    STAR("별", BigDecimal.valueOf(5000)),
    NONE("없음", BigDecimal.valueOf(0));

    private final String badgeName;
    private final BigDecimal minBenefitAmount;

    Badges(String badgeName, BigDecimal minBenefitAmount) {
        this.badgeName = badgeName;
        this.minBenefitAmount = minBenefitAmount;
    }

    public static Badges valueOfAmount(BigDecimal totalBenefitAmount) {
        if (totalBenefitAmount == null) {
            return NONE;
        }
        if (totalBenefitAmount.compareTo(SANTA.minBenefitAmount) >= 0) {
            return SANTA;
        }
        if (totalBenefitAmount.compareTo(TREE.minBenefitAmount) >= 0) {
            return TREE;
        }
        if (totalBenefitAmount.compareTo(STAR.minBenefitAmount) >= 0) {
            return STAR;
        }
        return NONE;
    }

    @Override
    public String toString() {
        return badgeName;
    }
}

