import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.ArrayList;

public class Foo {
//  static boolean boolValue;
//  public static void main(String[] args) {
//    boolValue = true; // 将这个 true 替换为 2 或者 3，再看看打印结果
//    if (boolValue) System.out.println("Hello, Java!");
//    if (boolValue == true) System.out.println("Hello, JVM!");
//
//  }
  public void bar(Object o) {
  }

  public void foo() {
//    var value = 1;
//    var list = new ArrayList<Integer>();
//    list.add(value);
//     list.add("1"); //这一句能够编译吗？
  }

  public static void main(String[] args) throws Throwable {
    MethodHandles.Lookup l = MethodHandles.lookup();
    MethodType t = MethodType.methodType(void.class, Object.class);
    MethodHandle mh = l.findVirtual(Foo.class, "bar", t);

    long current = System.currentTimeMillis();
    for (int i = 1; i <= 2_000_000_000; i++) {
      if (i % 100_000_000 == 0) {
        long temp = System.currentTimeMillis();
        System.out.println(temp - current);
        current = temp;
      }
      mh.invokeExact(new Foo(), new Object());
    }
  }
}