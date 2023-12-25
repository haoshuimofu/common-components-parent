package test.exception;

/**
 * 异常catch test
 * @author dewu.de
 * @date 2023-12-21 10:59 上午
 */
public class ExceptionTest {


    public static void main(String[] args) {
        try {
            throw new IllegalArgumentException("TEST");
        } catch (ArithmeticException ae) {
            System.out.println("ae");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("exception");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            System.out.println("throwable");
        }
    }

}
