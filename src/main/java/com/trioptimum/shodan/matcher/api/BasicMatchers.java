package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.common.internal.Util;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.util.Collection;

@SuppressWarnings("unchecked")
public class BasicMatchers {
	
	private static final Matcher<Object> ALWAYS_TRUE_MATCHER = new ConstantResultMatcher(true);
	
	private static final Matcher<Object> ALWAYS_FALSE_MATCHER = new ConstantResultMatcher(false);

	public static Matcher<Object> any() {
		return ALWAYS_TRUE_MATCHER;
	}
	
	public static Matcher<Object> none() {
		return ALWAYS_FALSE_MATCHER;
	}
	
	public static <T> Matcher<T> all(Matcher<? super T> m1, Matcher<? super T> m2) {
		return constructAndMatcher(m1, m2);
	}
	
	public static <T> Matcher<T> all(Matcher<? super T> m1, Matcher<? super T> m2, Matcher<? super T> m3) {
		return constructAndMatcher(m1, m2, m3);
	}
	
	public static <T> Matcher<T> all(Matcher<? super T>... matchers) {
		return constructAndMatcher(matchers.clone());
	}
	
	public static <T> Matcher<T> all(Collection<? super T> matchers) {
		return constructAndMatcher(((Matcher<? super T>[]) matchers.toArray()).clone());
	}
	
	private static <T> Matcher<T> constructAndMatcher(final Matcher<? super T>... matchers) {
		return new OrAndMatcher<T>(false, matchers);
	}

    public static <T> Matcher<T> or(Matcher<? super T> m1, Matcher<? super T> m2) {
        return constructOrMatcher(m1, m2);
    }

    public static <T> Matcher<T> or(Matcher<? super T> m1, Matcher<? super T> m2, Matcher<? super T> m3) {
        return constructOrMatcher(m1, m2, m3);
    }

    public static <T> Matcher<T> or(Matcher<? super T>... matchers) {
        return constructOrMatcher(matchers.clone());
    }

    public static <T> Matcher<T> or(Collection<? super T> matchers) {
        return constructOrMatcher(((Matcher<? super T>[]) matchers.toArray()).clone());
    }

    private static <T> Matcher<T> constructOrMatcher(Matcher<? super T>... matchers) {
        return new OrAndMatcher<T>(true, matchers);
    }

	public static Matcher<Object> is(Object instance) {
        return new IsMatcher(instance);
    }

    public static <T> Matcher<T> not(Matcher<? super T> toNegateMatcher) {
        return new NotMatcher<T>(toNegateMatcher);
    }

    public static Matcher<Object> same(Object instance) {
        return new InstanceMatcher(instance);
    }

    public static Matcher<Object> type(Matcher<? super Class<?>> classMatcher) {
        return new TypeMatcher(classMatcher);
    }

    private static class ConstantResultMatcher implements Matcher<Object> {

        final boolean result;

        ConstantResultMatcher(boolean result) {
            this.result = result;
        }

        public boolean matches(Object tested) {
            return result;
        }
    }

    private static class InstanceMatcher implements Matcher<Object> {

        final Object instance;

        InstanceMatcher(Object instance) {
            this.instance = instance;
        }

        public boolean matches(Object tested) {
            return (tested == instance);
        }
    }

    private static class IsMatcher implements Matcher<Object> {

        final Object instance;

        IsMatcher(Object instance) {
            this.instance = instance;
        }

        public boolean matches(Object tested) {
            return Util.equals(tested, instance);
        }
    }

    private static class NotMatcher<T> implements Matcher<T> {

        final Matcher<? super T> toNegateMatcher;

        NotMatcher(Matcher<? super T> toNegateMatcher) {
            this.toNegateMatcher = toNegateMatcher;
        }

        public boolean matches(T tested) {
            return !toNegateMatcher.matches(tested);
        }
    }

    private static class OrAndMatcher<T> implements Matcher<T> {

        final boolean or;

        final Matcher<? super T>[] matchers;

        OrAndMatcher(boolean or, Matcher<? super T>... matchers) {
            this.or = or;
            this.matchers = matchers;
        }

        public boolean matches(T tested) {
            for (Matcher<? super T> matcher : matchers) {
                if (matcher.matches(tested) == or) {
                    return or;
                }
            }

            return !or;
        }
    }

    private static class TypeMatcher implements Matcher<Object> {

        final Matcher<? super Class<?>> classMatcher;

        TypeMatcher(Matcher<? super Class<?>> classMatcher) {
            this.classMatcher = classMatcher;
        }

        public boolean matches(Object tested) {
            return classMatcher.matches(tested.getClass());
        }
    }

}
