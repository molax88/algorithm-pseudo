package tree;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Random;

/**
 * @author molax
 * @date 2022/2/22 10:44
 */
public class RBTree {
    private RBNode root;
    private Comparator comparator;
    protected static final boolean DEBUGGING = true;
    protected static final boolean VERBOSE = true;
    protected static final boolean REALLY_VERBOSE = false;

    public RBTree(Comparator comparator) {
        this.comparator = comparator;
    }

    public RBNode getRoot() {
        return this.root;
    }

    public void insertNode(RBNode x) {
        this.treeInsert(x);
        x.setColor(RBColor.RED);
        boolean shouldPropagate = x.update();
        RBNode propagateStart = x;

        while(x != this.root && x.getParent().getColor() == RBColor.RED) {
            RBNode y;
            if (x.getParent() == x.getParent().getParent().getLeft()) {
                y = x.getParent().getParent().getRight();
                if (y != null && y.getColor() == RBColor.RED) {
                    x.getParent().setColor(RBColor.BLACK);
                    y.setColor(RBColor.BLACK);
                    x.getParent().getParent().setColor(RBColor.RED);
                    x.getParent().update();
                    x = x.getParent().getParent();
                    shouldPropagate = x.update();
                    propagateStart = x;
                } else {
                    if (x == x.getParent().getRight()) {
                        x = x.getParent();
                        this.leftRotate(x);
                    }

                    x.getParent().setColor(RBColor.BLACK);
                    x.getParent().getParent().setColor(RBColor.RED);
                    shouldPropagate = this.rightRotate(x.getParent().getParent());
                    propagateStart = x.getParent();
                }
            } else {
                y = x.getParent().getParent().getLeft();
                if (y != null && y.getColor() == RBColor.RED) {
                    x.getParent().setColor(RBColor.BLACK);
                    y.setColor(RBColor.BLACK);
                    x.getParent().getParent().setColor(RBColor.RED);
                    x.getParent().update();
                    x = x.getParent().getParent();
                    shouldPropagate = x.update();
                    propagateStart = x;
                } else {
                    if (x == x.getParent().getLeft()) {
                        x = x.getParent();
                        this.rightRotate(x);
                    }

                    x.getParent().setColor(RBColor.BLACK);
                    x.getParent().getParent().setColor(RBColor.RED);
                    shouldPropagate = this.leftRotate(x.getParent().getParent());
                    propagateStart = x.getParent();
                }
            }
        }

        while(shouldPropagate && propagateStart != this.root) {
            propagateStart = propagateStart.getParent();
            shouldPropagate = propagateStart.update();
        }

        this.root.setColor(RBColor.BLACK);
        this.verify();
    }

    public void deleteNode(RBNode z) {
        RBNode y;
        if (z.getLeft() != null && z.getRight() != null) {
            y = this.treeSuccessor(z);
        } else {
            y = z;
        }

        RBNode x;
        if (y.getLeft() != null) {
            x = y.getLeft();
        } else {
            x = y.getRight();
        }

        RBNode xParent;
        if (x != null) {
            x.setParent(y.getParent());
            xParent = x.getParent();
        } else {
            xParent = y.getParent();
        }

        if (y.getParent() == null) {
            this.root = x;
        } else if (y == y.getParent().getLeft()) {
            y.getParent().setLeft(x);
        } else {
            y.getParent().setRight(x);
        }

        if (y != z) {
            z.copyFrom(y);
        }

        if (y.getColor() == RBColor.BLACK) {
            this.deleteFixup(x, xParent);
        }

        this.verify();
    }

    public void print() {
        this.printOn(System.out);
    }

    public void printOn(PrintStream tty) {
        this.printFromNode(this.root, tty, 0);
    }

    protected Object getNodeValue(RBNode node) {
        return node.getData();
    }

    protected void verify() {
        this.verifyFromNode(this.root);
    }

    private void treeInsert(RBNode z) {
        RBNode y = null;
        RBNode x = this.root;

        while(x != null) {
            y = x;
            if (this.comparator.compare(this.getNodeValue(z), this.getNodeValue(x)) < 0) {
                x = x.getLeft();
            } else {
                x = x.getRight();
            }
        }

        z.setParent(y);
        if (y == null) {
            this.root = z;
        } else if (this.comparator.compare(this.getNodeValue(z), this.getNodeValue(y)) < 0) {
            y.setLeft(z);
        } else {
            y.setRight(z);
        }

    }

    private RBNode treeSuccessor(RBNode x) {
        if (x.getRight() != null) {
            return this.treeMinimum(x.getRight());
        } else {
            RBNode y;
            for(y = x.getParent(); y != null && x == y.getRight(); y = y.getParent()) {
                x = y;
            }

            return y;
        }
    }

    private RBNode treeMinimum(RBNode x) {
        while(x.getLeft() != null) {
            x = x.getLeft();
        }

        return x;
    }

    private boolean leftRotate(RBNode x) {
        RBNode y = x.getRight();
        x.setRight(y.getLeft());
        if (y.getLeft() != null) {
            y.getLeft().setParent(x);
        }

        y.setParent(x.getParent());
        if (x.getParent() == null) {
            this.root = y;
        } else if (x == x.getParent().getLeft()) {
            x.getParent().setLeft(y);
        } else {
            x.getParent().setRight(y);
        }

        y.setLeft(x);
        x.setParent(y);
        boolean res = x.update();
        res = y.update() || res;
        return res;
    }

    private boolean rightRotate(RBNode y) {
        RBNode x = y.getLeft();
        y.setLeft(x.getRight());
        if (x.getRight() != null) {
            x.getRight().setParent(y);
        }

        x.setParent(y.getParent());
        if (y.getParent() == null) {
            this.root = x;
        } else if (y == y.getParent().getLeft()) {
            y.getParent().setLeft(x);
        } else {
            y.getParent().setRight(x);
        }

        x.setRight(y);
        y.setParent(x);
        boolean res = y.update();
        res = x.update() || res;
        return res;
    }

    private void deleteFixup(RBNode x, RBNode xParent) {
        while(x != this.root && (x == null || x.getColor() == RBColor.BLACK)) {
            RBNode w;
            if (x == xParent.getLeft()) {
                w = xParent.getRight();
                if (w == null) {
                    throw new RuntimeException("x's sibling should not be null");
                }

                if (w.getColor() == RBColor.RED) {
                    w.setColor(RBColor.BLACK);
                    xParent.setColor(RBColor.RED);
                    this.leftRotate(xParent);
                    w = xParent.getRight();
                }

                if ((w.getLeft() == null || w.getLeft().getColor() == RBColor.BLACK) && (w.getRight() == null || w.getRight().getColor() == RBColor.BLACK)) {
                    w.setColor(RBColor.RED);
                    x = xParent;
                    xParent = xParent.getParent();
                } else {
                    if (w.getRight() == null || w.getRight().getColor() == RBColor.BLACK) {
                        w.getLeft().setColor(RBColor.BLACK);
                        w.setColor(RBColor.RED);
                        this.rightRotate(w);
                        w = xParent.getRight();
                    }

                    w.setColor(xParent.getColor());
                    xParent.setColor(RBColor.BLACK);
                    if (w.getRight() != null) {
                        w.getRight().setColor(RBColor.BLACK);
                    }

                    this.leftRotate(xParent);
                    x = this.root;
                    xParent = x.getParent();
                }
            } else {
                w = xParent.getLeft();
                if (w == null) {
                    throw new RuntimeException("x's sibling should not be null");
                }

                if (w.getColor() == RBColor.RED) {
                    w.setColor(RBColor.BLACK);
                    xParent.setColor(RBColor.RED);
                    this.rightRotate(xParent);
                    w = xParent.getLeft();
                }

                if ((w.getRight() == null || w.getRight().getColor() == RBColor.BLACK) && (w.getLeft() == null || w.getLeft().getColor() == RBColor.BLACK)) {
                    w.setColor(RBColor.RED);
                    x = xParent;
                    xParent = xParent.getParent();
                } else {
                    if (w.getLeft() == null || w.getLeft().getColor() == RBColor.BLACK) {
                        w.getRight().setColor(RBColor.BLACK);
                        w.setColor(RBColor.RED);
                        this.leftRotate(w);
                        w = xParent.getLeft();
                    }

                    w.setColor(xParent.getColor());
                    xParent.setColor(RBColor.BLACK);
                    if (w.getLeft() != null) {
                        w.getLeft().setColor(RBColor.BLACK);
                    }

                    this.rightRotate(xParent);
                    x = this.root;
                    xParent = x.getParent();
                }
            }
        }

        if (x != null) {
            x.setColor(RBColor.BLACK);
        }

    }

    private int verifyFromNode(RBNode node) {
        if (node == null) {
            return 1;
        } else if (node.getColor() != RBColor.RED && node.getColor() != RBColor.BLACK) {
            System.err.println("Verify failed:");
            this.printOn(System.err);
            throw new RuntimeException("Verify failed (1)");
        } else {
            if (node.getColor() == RBColor.RED) {
                if (node.getLeft() != null && node.getLeft().getColor() != RBColor.BLACK) {
                    System.err.println("Verify failed:");
                    this.printOn(System.err);
                    throw new RuntimeException("Verify failed (2)");
                }

                if (node.getRight() != null && node.getRight().getColor() != RBColor.BLACK) {
                    System.err.println("Verify failed:");
                    this.printOn(System.err);
                    throw new RuntimeException("Verify failed (3)");
                }
            }

            int i = this.verifyFromNode(node.getLeft());
            int j = this.verifyFromNode(node.getRight());
            if (i != j) {
                System.err.println("Verify failed:");
                this.printOn(System.err);
                throw new RuntimeException("Verify failed (4) (left black count = " + i + ", right black count = " + j + ")");
            } else {
                return i + (node.getColor() == RBColor.RED ? 0 : 1);
            }
        }
    }

    private void printFromNode(RBNode node, PrintStream tty, int indentDepth) {
        for(int i = 0; i < indentDepth; ++i) {
            tty.print(" ");
        }

        tty.print("-");
        if (node == null) {
            tty.println();
        } else {
            tty.println(" " + this.getNodeValue(node) + (node.getColor() == RBColor.RED ? " (red)" : " (black)"));
            this.printFromNode(node.getLeft(), tty, indentDepth + 2);
            this.printFromNode(node.getRight(), tty, indentDepth + 2);
        }
    }

    public static void main(String[] args) {
        int treeSize = 10;
        int maxVal = treeSize;
        System.err.println("Building tree...");
        RBTree tree = new RBTree(new Comparator() {
            public int compare(Object o1, Object o2) {
                Integer i1 = (Integer)o1;
                Integer i2 = (Integer)o2;
                if (i1 < i2) {
                    return -1;
                } else {
                    return i1 == i2 ? 0 : 1;
                }
            }
        });
        Random rand = new Random(System.currentTimeMillis());

        int i;
        for(i = 0; i < treeSize; ++i) {
            Integer val = Integer.valueOf(rand.nextInt(maxVal) + 1);

            try {
                tree.insertNode(new RBNode(val));
                if (i > 0 && i % 100 == 0) {
                    System.err.print(i + "...");
                    System.err.flush();
                }
            } catch (Exception var11) {
                var11.printStackTrace();
                System.err.println("While inserting value " + val);
                tree.printOn(System.err);
                System.exit(1);
            }
        }

        System.err.println();
        System.err.println("Churning tree...");

        for(i = 0; i < treeSize; ++i) {
            System.err.println("Iteration " + i + ":");
            tree.printOn(System.err);
            RBNode xParent = null;
            RBNode x = tree.getRoot();

            int depth;
            for(depth = 0; x != null; ++depth) {
                xParent = x;
                if (rand.nextBoolean()) {
                    x = x.getLeft();
                } else {
                    x = x.getRight();
                }
            }

            int height = rand.nextInt(depth);
            if (height >= depth) {
                throw new RuntimeException("bug in java.util.Random");
            }

            while(height > 0) {
                xParent = xParent.getParent();
                --height;
            }

            System.err.println("(Removing value " + tree.getNodeValue(xParent) + ")");
            tree.deleteNode(xParent);
            Integer newVal = new Integer(rand.nextInt(maxVal) + 1);
            System.err.println("(Inserting value " + newVal + ")");
            tree.insertNode(new RBNode(newVal));
        }

        System.err.println("All tests passed.");
    }
}
