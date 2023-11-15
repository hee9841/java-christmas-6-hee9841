package christmas;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import christmas.constant.Menu;
import christmas.policy.input.CheckCondition;
import java.util.EnumMap;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;


class CheckConditionTest {
    CheckCondition checkCondition;

    @BeforeEach
    void beforeEach() {
        checkCondition = new CheckCondition();
    }

    @ParameterizedTest
    @DisplayName("날짜 형태 체크")
    @ValueSource(strings = {"1", "15", "31"})
    void validDateFormatCheck(String input) {
        assertDoesNotThrow(() -> {
            checkCondition.validateDateFormat(input);
        });
    }

    @ParameterizedTest
    @DisplayName("날짜 형태 예외")
    @ValueSource(strings = {"숫자아님"})
    @NullSource
    @EmptySource
    void validDateFormatCheck_exception(String input) {
        assertThatThrownBy(() -> {
            checkCondition.validateDateFormat(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @ParameterizedTest
    @DisplayName("날짜 범위 체크")
    @ValueSource(ints = {1, 15, 31})
    void validateDatePeriod(int input) {
        assertDoesNotThrow(() -> {
            checkCondition.validateDatePeriod(input);
        });
    }

    @ParameterizedTest
    @DisplayName("날짜 범위 체크 예외")
    @ValueSource(ints = {-1, 0, 32})
    void validateDatePeriod_exception(int input) {
        assertThatThrownBy(() -> {
            checkCondition.validateDatePeriod(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 날짜입니다. 다시 입력해 주세요.");
    }

    @Test
    @DisplayName("주문(메뉴 여러개) 형태 체크")
    void validateInputOrders() {
        assertDoesNotThrow(() -> {
            checkCondition.validateInputOrders("메뉴1-1,,메뉴2-2");
        });
    }

    @ParameterizedTest
    @DisplayName("주문(메뉴 여러개) 형태 체크예외")
    @ValueSource(strings = {",메뉴1-1,메뉴2-2", "메뉴1-1,메뉴2-2,", ",메뉴1-1,메뉴2-2,"})
    @NullSource
    @EmptySource
    void validateInputOrders_exception(String input) {
        assertThatThrownBy(() -> {
            checkCondition.validateInputOrders(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @Test
    @DisplayName("주문 형태 체크")
    void validateOrderFormat() {
        assertDoesNotThrow(() -> {
            checkCondition.validateOrderFormat("메뉴1-1");
        });
    }

    @ParameterizedTest
    @DisplayName("주문 형태 체크 예외")
    @ValueSource(strings = {"메뉴1-1-3", "메뉴1-a"})
    @EmptySource
    @NullSource
    void validateOrderFormat_exception(String input) {

        assertThatThrownBy(() -> {
            checkCondition.validateOrderFormat(input);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    @Test
    @DisplayName("메뉴 중복 체크")
    void isDuplicateMenu() {
        EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class) {{
            put(Menu.MUSHROOM_SOUP, 1);
            put(Menu.CHOCOLATE_CAKE, 1);
        }};
        assertDoesNotThrow(() -> {
            checkCondition.isDuplicateMenu(orders, Menu.ICE_CRAM);
        });
    }

    @Test
    @DisplayName("메뉴 중복 체크 예외")
    void isDuplicateMenu_exception() {
        EnumMap<Menu, Integer> orders = new EnumMap<>(Menu.class) {{
            put(Menu.MUSHROOM_SOUP, 1);
            put(Menu.CHOCOLATE_CAKE, 1);
        }};

        assertThatThrownBy(() -> {
            checkCondition.isDuplicateMenu(orders, Menu.CHOCOLATE_CAKE);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }


    @ParameterizedTest
    @DisplayName("최소 주문수 확인")
    @MethodSource("orderDataForMinCount")
    void checkMinOrderCount(EnumMap<Menu, Integer> orders) {
        assertDoesNotThrow(() -> {
            checkCondition.checkMinOrderCount(orders);
        });
    }

    @ParameterizedTest
    @DisplayName("최소 주문수 확인 예외")
    @MethodSource("orderDataForMinCountException")
    void checkMinOrderCount_exception(EnumMap<Menu, Integer> orders) {

        assertThatThrownBy(() -> {
            checkCondition.checkMinOrderCount(orders);
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효하지 않은 주문입니다. 다시 입력해 주세요.");
    }

    static Stream<Arguments> orderDataForMinCount() {
        return Stream.of(
                Arguments.of(
                        new EnumMap<>(Menu.class) {{
                            put(Menu.MUSHROOM_SOUP, 10);
                            put(Menu.CHOCOLATE_CAKE, 10);
                        }}
                ),
                Arguments.of(
                        new EnumMap<>(Menu.class) {{
                            put(Menu.MUSHROOM_SOUP, 1);
                        }}
                )
        );
    }

    static Stream<Arguments> orderDataForMinCountException() {
        return Stream.of(
                Arguments.of(
                        new EnumMap<>(Menu.class) {{
                            put(Menu.MUSHROOM_SOUP, 10);
                            put(Menu.CHOCOLATE_CAKE, 11);
                        }}
                ),
                Arguments.of(
                        new EnumMap<>(Menu.class) {{
                            put(Menu.MUSHROOM_SOUP, 0);
                        }}
                )
        );
    }


}
