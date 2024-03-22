package domain.common;

import java.util.Objects;

public class Money {
    private final Long money;
    private final static long MINIMUM_MONEY = 0;


    public static Money create(long money) {
        if (money < MINIMUM_MONEY) {
            throw new IllegalArgumentException("0원 이하의 금액은 입력할 수 없습니다.");
        }
        return new Money(money);

    }

    public Money(long money) {
        this.money = money;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money1 = (Money) o;
        return Objects.equals(money, money1.money);
    }

    @Override
    public int hashCode() {
        return Objects.hash(money);
    }

    public long divide(Money money) {
        return money.money() / this.money;
    }

    private long money() {
        return this.money;
    }
}
