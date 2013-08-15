package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.matcher.service.Matcher;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Member;

import static com.trioptimum.shodan.matcher.api.BasicMatchers.is;

/**
 * Created with IntelliJ IDEA.
 * User: Turtles
 * Date: 30.11.12
 * Time: 19:39
 * To change this template use File | Settings | File Templates.
 */
public class ReflectMatchers {

    public static Matcher<AnnotatedElement> annotation(Class<? extends Annotation> annotation) {
        return new AnnotationMatcher(annotation);
    }

    public static Matcher<Member> declaredClass(Matcher<? super Class<?>> classMatcher) {
        return new DeclaredInMatcher(classMatcher);
    }

    public static Matcher<Member> declaredInObject() {
        return new DeclaredInMatcher(is(Object.class));
    }

    public static Matcher<Class<?>> iface(Matcher<? super Class<?>> ifaceMatcher) {
        return new IFaceMatcher(ifaceMatcher);
    }

    public static Matcher<Member> name(Matcher<? super String> name) {
		return new NameMatcher(name);
	}

    public static Matcher<Class<?>> simpleName(Matcher<? super String> name) {
		return new SimpleNameMatcher(name);
	}

    private static class AnnotationMatcher implements Matcher<AnnotatedElement> {

        final Class<? extends Annotation> annotation;

        AnnotationMatcher(Class<? extends Annotation> annotation) {
            this.annotation = annotation;
        }

        public boolean matches(AnnotatedElement tested) {
            return tested.isAnnotationPresent(annotation);
        }
    }

    private static class DeclaredInMatcher implements Matcher<Member> {

        final Matcher<? super Class<?>> classMatcher;

        DeclaredInMatcher(Matcher<? super Class<?>> classMatcher) {
            this.classMatcher = classMatcher;
        }

        public boolean matches(Member tested) {
            return classMatcher.matches(tested.getDeclaringClass());
        }
    }

    private static class IFaceMatcher implements Matcher<Class<?>> {

        final Matcher<? super Class<?>> ifaceMatcher;

        IFaceMatcher(Matcher<? super Class<?>> ifaceMatcher) {
            this.ifaceMatcher = ifaceMatcher;
        }

        public boolean matches(Class<?> tested) {
            for (Class<?> iface : tested.getInterfaces()) {
                if (ifaceMatcher.matches(iface)) {
                    return  true;
                }
            }

            return false;
        }
    }

    private static class NameMatcher implements Matcher<Member> {

        final Matcher<? super String> nameMatcher;

        NameMatcher(Matcher<? super String> nameMatcher) {
            this.nameMatcher = nameMatcher;
        }

        public boolean matches(Member tested) {
            return nameMatcher.matches(tested.getName());
        }
    }

    private static class SimpleNameMatcher implements Matcher<Class<?>> {

        final Matcher<? super String> nameMatcher;

        SimpleNameMatcher(Matcher<? super String> nameMatcher) {
            this.nameMatcher = nameMatcher;
        }

        public boolean matches(Class<?> tested) {
            return nameMatcher.matches(tested.getSimpleName());
        }
    }
}
