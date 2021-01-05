package Frame;

public class UnitTestingJAva extends Object {
    public int someVariable = 0;
    public void setSomeVariable(int value) { someVariable = value; }
    public int getSomeVariable() { return someVariable; }
    public static void main(String ... args) {
        // give the window a reference to this object
        UnitTestingJAva foo = new UnitTestingJAva();
        WindowViewJava windowViewJava = new WindowViewJava(foo);
    }
}
