import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author molax
 * @date 2023/3/7 10:58
 */
public class CompilationTest {

    // race 1 -XX:+PrintCompilation
//    public static void main(String[] args) {
//        List list = new ArrayList<Integer>();
//        for(int i = 0;i<1_0000;i++) {
//            list.add(i);
//        }
//    }

    // race 2 -XX:CompileCommand='print,CompilationTest.foo'
    public static int foo(boolean f, int in) {
        int v;
        if (f) {
            v = in;
        } else {
            v = (int) Math.sin(in);
        }
        if (v == in) {
            return 0;
        } else {
            return (int) Math.cos(v);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 500000; i++) {
            foo(true, 2);
        }
        Thread.sleep(2000);
    }

    // race 3 -XX:+UnlockExperimentalVMOptions -XX:+UseJVMCICompiler -XX:CompileCommand='print,CompilationTest2.hash'
//    public static int hash(Object input) {
//        if (input instanceof Exception) {
//            return System.identityHashCode(input);
//        } else {
//            return input.hashCode();
//        }
//    }
//    public static void main(String[] args) throws InterruptedException {
//        for (int i = 0; i < 500000; i++) {
//            hash(i);
//        }
//        Thread.sleep(2000);
//    }
}
