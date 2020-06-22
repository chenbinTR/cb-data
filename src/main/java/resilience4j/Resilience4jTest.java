package resilience4j;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.vavr.control.Try;
import org.junit.Test;
import utils.Utils;

import java.time.Duration;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author ChenOT
 * @date 2019-10-08
 * @see
 * @since
 */
public class Resilience4jTest {
    @Test
    public void testResilience4j() {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(5) // 百分比，从关闭（不熔断）到打开（荣丹）的阈值
                .ringBufferSizeInClosedState(10) // 关闭状态（不熔断）需要统计的处理次数
                .waitDurationInOpenState(Duration.ofMinutes(1)) // 打开状态（熔断）到半打开状态的等待时间
                .ringBufferSizeInHalfOpenState(100) // 在半打开状态下，根据n个处理，判断是否恢复到关闭状态
                .build();

        CircuitBreaker circuitBreaker = CircuitBreaker.of("my", config);
        circuitBreaker.getEventPublisher()
                .onError(event -> System.out.println("***************************redisCircuitBreaker error"+ event.toString()))
                .onStateTransition(event -> System.out.println("**************************redisCircuitBreaker state:" + event.toString()));
        Supplier<String> supplier = CircuitBreaker.decorateSupplier(circuitBreaker, new Supplier<String>() {
                    @Override
                    public String get() {
//                        int i = 1 / 0;
                        Utils.httpGet("");
                        return "ok";
                    }
                }
        );
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("*********************** "+i);
            String result = Try.ofSupplier(supplier).recover(throwable -> "error*********************").get();
            System.out.println("***************************** result: "+result);
        }

    }
}
