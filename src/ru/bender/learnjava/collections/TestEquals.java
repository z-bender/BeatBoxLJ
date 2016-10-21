package ru.bender.learnjava.collections;

/**
 * Created by bender on 19.10.16.
 */
public class TestEquals {
    int x;

    public static void main(String[] args) {
        TestEquals A = new TestEquals(5);
        TestEquals B = new TestEquals(5);
        System.out.println((A == B));
        System.out.println(A.equals(B));
        System.out.println(A.hashCode());
        System.out.println(B.hashCode());

    }

    public TestEquals(int x) {
        this.x = x;
    }

    @Override
    public boolean equals(Object o) {
        TestEquals tO = (TestEquals) o;
        return (this.x == tO.x);
    }

    @Override
    public int hashCode() {
        return x;
    }
}
