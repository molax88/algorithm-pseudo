//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package tree;

public class RBColor {
    public static final RBColor RED = new RBColor("red");
    public static final RBColor BLACK = new RBColor("black");
    private String name;

    public String getName() {
        return this.name;
    }

    private RBColor(String name) {
        this.name = name;
    }
}
