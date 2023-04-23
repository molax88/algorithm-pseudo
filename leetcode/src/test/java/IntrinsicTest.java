import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author molax
 * @date 2023/3/9 15:36
 */
public class IntrinsicTest {
    public static int bitCount(int i) {
        // HD, Figure 5-2
        i = i - ((i >>> 1) & 0x55555555);
        i = (i & 0x33333333) + ((i >>> 2) & 0x33333333);
        i = (i + (i >>> 4)) & 0x0f0f0f0f;
        i = i + (i >>> 8);
        i = i + (i >>> 16);
        return i & 0x3f;
    }
    public static void main(String[] args) {
        Instant start = Instant.now();
        int sum = 0;
        for (int i = Integer.MIN_VALUE; i < Integer.MAX_VALUE; i++) {
//            sum += bitCount(i); // In a second run, replace with Integer.bitCount
            sum += Integer.bitCount(i);
        }
        System.out.println(sum);
        System.out.println(start.until(Instant.now(), ChronoUnit.MILLIS));
    }
}
