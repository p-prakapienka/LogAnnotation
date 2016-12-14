package by.prakapienka.annotationlog;

public class TestMain {

    public static void main(String[] args) {
        TestClass testClass = LogAnnotationProcessor.process(new TestClass());
        testClass.method1(1, 2);
        testClass.method2("s1", "s2", "s3");
    }

    public static class TestClass {

        @Log
        public int method1(int i1, int i2) {
            return i1 + i2;
        }

        @Log
        public String method2(String s1, String s2, String s3) {
            return s1 + s2 + s3;
        }
    }
}
