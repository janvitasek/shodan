package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.common.internal.Call;
import com.trioptimum.shodan.common.internal.CallablePoint;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class CallMatchers {

    public static Matcher<Call> target(Matcher<? super CallablePoint> cpMatcher) {
		return new TargetMatcher(cpMatcher);
	}

    public static Matcher<Call> params(Matcher<? super List<?>> listMatcher) {
        return new ParamsMatcher(listMatcher);
    }

    public static Matcher<Call> source(Matcher<? super Class<?>> classMatcher) {
        return new SourceMatcher(classMatcher);
    }

    public static Matcher<Call> markers(Matcher<? super List<?>> listMatcher) {
        return new MarkersMatcher(listMatcher);
    }

    private static class TargetMatcher implements Matcher<Call> {

        final Matcher<? super CallablePoint> cpMatcher;

        TargetMatcher(Matcher<? super CallablePoint> cpMatcher) {
            this.cpMatcher = cpMatcher;
        }

        public boolean matches(Call tested) {
            return cpMatcher.matches(tested.getTarget());
        }
    }

    private static class ParamsMatcher implements Matcher<Call> {

        final Matcher<? super List<?>> listMatcher;

        ParamsMatcher(Matcher<? super List<?>> listMatcher) {
            this.listMatcher = listMatcher;
        }

        public boolean matches(Call tested) {
            return listMatcher.matches(Arrays.asList(tested.getParameters()));
        }
    }

    private static class SourceMatcher implements Matcher<Call> {

        final Matcher<? super Class<?>> instanceMatcher;

        SourceMatcher(Matcher<? super Class<?>> instanceMatcher) {
            this.instanceMatcher = instanceMatcher;
        }

        public boolean matches(Call tested) {
            return instanceMatcher.matches(tested.getMetadata().getSource());
        }
    }

    private static class MarkersMatcher implements Matcher<Call> {

        final Matcher<? super List<? extends Annotation>> listMatcher;

        MarkersMatcher(Matcher<? super List<? extends Annotation>> listMatcher) {
            this.listMatcher = listMatcher;
        }

        public boolean matches(Call tested) {
            return listMatcher.matches(Arrays.asList(tested.getMetadata().getMarkers()));
        }
    }
}
