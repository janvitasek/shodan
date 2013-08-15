package com.trioptimum.shodan.perf;

import com.trioptimum.shodan.lookup.api.LookupByRules;
import com.trioptimum.shodan.lookup.service.LookupRule;
import com.trioptimum.shodan.matcher.api.ReflectMatchers;

import javax.swing.*;

import static com.trioptimum.shodan.lookup.api.LookupRuleBuilder.bind;
import static com.trioptimum.shodan.matcher.api.BasicMatchers.is;
import static com.trioptimum.shodan.matcher.api.CallablePointMatchers.method;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 12.11.12
 * Time: 19:51
 * To change this template use File | Settings | File Templates.
 */
public class RegisterTest extends JTable {

    public static void main(String[] args) {
//        LookupRule rrule = bind(all(method(annotation(Deprecated.class)), method(declaredClass(simpleName("TestClass"))))).done();
        LookupRule rrule = bind(method(ReflectMatchers.declaredClass(is(JTable.class)))).done();
        for (int i = 0; i < 10; i++) {
            RegisterTest testObject = new RegisterTest();
            //EventBus bus = new EventBus();
            LookupByRules lookup = new LookupByRules(rrule);
            long start = System.nanoTime();
            lookup.add(testObject);
//            bus.register(testObject);


            System.out.printf("Register took %s msecs\n", (System.nanoTime() - start) / 1000000d);
        }
    }
}
