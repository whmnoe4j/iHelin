package me.ianhe.junit;

import junit.framework.AssertionFailedError;
import junit.framework.TestResult;
import org.junit.Test;

/**
 * 测试TestResult中的方法
 *
 * @author iHelin
 * @create 2017-02-04 17:55
 */
public class TestJunit3 extends TestResult {

    // add the error
    public synchronized void addError(Test test, Throwable t) {
        super.addError((junit.framework.Test) test, t);
    }

    // add the failure
    public synchronized void addFailure(Test test, AssertionFailedError t) {
        super.addFailure((junit.framework.Test) test, t);
    }

    @Test
    public void testAdd() {
        // add any test
    }

    // Marks that the test run should stop.
    public synchronized void stop() {
        //stop the test here
    }

}
