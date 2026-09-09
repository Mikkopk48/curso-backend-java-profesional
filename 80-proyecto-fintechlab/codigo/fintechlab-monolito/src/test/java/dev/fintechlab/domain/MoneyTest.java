package dev.fintechlab.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class MoneyTest {
    @Test void addsOnlyTheSameCurrency(){assertThat(Money.of("10.10","ARS").add(Money.of("0.90","ARS")).amount()).isEqualByComparingTo("11.00");}
    @Test void rejectsMoreThanTwoDecimals(){assertThatThrownBy(()->Money.of("1.001","ARS")).isInstanceOf(ArithmeticException.class);}
    @Test void rejectsDifferentCurrencies(){assertThatThrownBy(()->Money.of("1.00","ARS").add(Money.of("1.00","USD"))).isInstanceOf(IllegalArgumentException.class);}
}
