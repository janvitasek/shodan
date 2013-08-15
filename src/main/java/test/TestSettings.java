package test;

import com.trioptimum.shodan.facade.service.AbstractSettings;

import static com.trioptimum.shodan.lookup.api.BindingContextMatchers.binding;
import static com.trioptimum.shodan.lookup.api.BindingMatchers.compatibleParams;
import static com.trioptimum.shodan.matcher.api.CallablePointMatchers.method;
import static com.trioptimum.shodan.matcher.api.ReflectMatchers.annotation;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 1.12.12
 * Time: 17:10
 * To change this template use File | Settings | File Templates.
 */
public class TestSettings extends AbstractSettings {

    @Override
    protected void configure() {
        bind(method(annotation(Deprecated.class))).when(binding(compatibleParams()));
    }
}
