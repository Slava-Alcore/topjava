package ru.javawebinar.topjava.util.TestRules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class TestTimeLoggerRule implements TestRule {

    private Logger logger;

    public TestTimeLoggerRule() {
        this.logger = LoggerFactory.getLogger(TestTimeLoggerRule.class);
    }

    private static Map<String,Long> testMap = new HashMap<>();

    public static Map<String, Long> getTestMap() {
        return testMap;
    }

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.nanoTime();
                base.evaluate();
                long endTime = System.nanoTime();
                testMap.put(description.getMethodName(),endTime-startTime);
                logger.info( "{} complete in {} nanos",description.getMethodName(),(endTime-startTime));
            }
        };
    }
}
