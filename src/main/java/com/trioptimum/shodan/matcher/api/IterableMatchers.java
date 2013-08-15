package com.trioptimum.shodan.matcher.api;

import com.trioptimum.shodan.matcher.internal.ContainingCriteria;
import com.trioptimum.shodan.matcher.internal.ContainingCriterias;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.util.*;

public class IterableMatchers {

    public static <T> Matcher<Iterable<? extends T>> has(Matcher<? super T> subject) {
        return hasAnyOf(Collections.singleton(subject));
    }

    public static <T> Matcher<Iterable<? extends T>> hasAnyOf(Collection<? extends Matcher<? super T>> subjects) {
        return new HasAnyOfMatcher<T>(subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> hasFirstAnyOf(Collection<? extends Matcher<? super T>> subjects) {
        return new HasOnPositionMatcher<T>(0, subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> hasLastAnyOf(Collection<? extends Matcher<? super T>> subjects) {
        return new HasOnPositionMatcher<T>(-1, subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> has(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.inTrendOrder(1), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> hasInAnyOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.allOf(), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> hasTogetherInAnyOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.together(), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> hasInAscendingOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.inAscendingOrder(), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> hasInDescendingOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.inDescendingOrder(), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> contains(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsExactlyMatcher<T>(ExactMatchingType.EXACT, subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> containsInAnyOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.allOfOnly(), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> startWith(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsExactlyMatcher<T>(ExactMatchingType.START, subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> endWith(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsExactlyMatcher<T>(ExactMatchingType.END, subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> startWithInAnyOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.firstAllOf(), subjects);
    }

    public static <T> Matcher<Iterable<? extends T>> endWithInAnyOrder(Collection<? extends Matcher<? super T>> subjects) {
        return new ContainsMatcher<T>(ContainingCriterias.lastAllOf(), subjects);
    }

    private static class HasAnyOfMatcher<T> implements Matcher<Iterable<? extends T>> {

        final Collection<? extends Matcher<? super T>> subjects;

        HasAnyOfMatcher(Collection<? extends Matcher<? super T>> subjects) {
            this.subjects = subjects;
        }

        public boolean matches(Iterable<? extends T> tested) {
            for (Matcher<? super T> subject : subjects) {
                for (T t : tested) {
                    if (subject.matches(t)) {
                        return true;
                    }
                }
            }

            return false;
        }
    }

    private static class HasOnPositionMatcher<T> implements Matcher<Iterable<? extends T>> {

        final int pos;

        final Collection<? extends Matcher<? super T>> subjects;

        HasOnPositionMatcher(int pos, Collection<? extends Matcher<? super T>> subjects) {
            this.pos = pos;
            this.subjects = subjects;
        }

        public boolean matches(Iterable<? extends T> tested) {
            Iterator<? extends T> it = tested.iterator();
            T checked = null;
            for (int i = 0; i <= pos || pos < 0; i++) {
                if (it.hasNext()) {
                    checked = it.next();
                } else if (pos < 0) {
                    break;
                } else {
                    return false;
                }
            }
            for (Matcher<? super T> subject : subjects) {
                if (subject.matches(checked)) {
                    return true;
                }
            }

            return false;
        }
    }

    private static class ContainsExactlyMatcher<T> implements Matcher<Iterable<? extends T>> {

        final ExactMatchingType type;

        final Collection<? extends Matcher<? super T>> subjects;

        ContainsExactlyMatcher(ExactMatchingType type, Collection<? extends Matcher<? super T>> subjects) {
            this.type = type;
            this.subjects = subjects;
        }

        public boolean matches(Iterable<? extends T> tested) {
            Iterator<? extends Matcher<? super T>> subjectsIt;
            Iterator<? extends T> objIt;

            if (type == ExactMatchingType.END) {
                LinkedList<T> list = new LinkedList<T>();
                for (T t : tested) {
                    list.add(t);
                }
                subjectsIt = new LinkedList<Matcher<? super T>>(subjects).descendingIterator();
                objIt = list.descendingIterator();
            } else {
                subjectsIt = subjects.iterator();
                objIt = tested.iterator();
            }
            while (subjectsIt.hasNext()) {
                if (!objIt.hasNext() || !subjectsIt.next().matches(objIt.next())) {
                    return false;
                }
            }

            return (type != ExactMatchingType.EXACT || !objIt.hasNext());
        }
    }

    private static enum ExactMatchingType {

        EXACT, START, END
    }

    private static class ContainsMatcher<T> implements Matcher<Iterable<? extends T>> {

        final ContainingCriteria criteria;

        final Collection<? extends Matcher<? super T>> subjects;

        ContainsMatcher(ContainingCriteria criteria, Collection<? extends Matcher<? super T>> subjects) {
            this.criteria = criteria;
            this.subjects = subjects;
        }

        public boolean matches(Iterable<? extends T> tested) {
            return matchesRec(-1, tested);
        }

        public boolean matchesRec(int from, Iterable<? extends T> tested) {
            int min = Integer.MAX_VALUE, max = -1, last = -1, size = size(tested);
            Set<Integer> findPos = new HashSet<Integer>(subjects.size());
            outer: for (Matcher<? super T> next : subjects) {
                int pos = 0;
                for (Iterator<? extends T> itInner = tested.iterator(); itInner.hasNext(); pos++) {
                    T obj = itInner.next();
                    if (pos < from) {
                        continue;
                    }
                    if (next.matches(obj) && !findPos.contains(pos)) {
                        min = Math.min(pos, min);
                        max = Math.max(pos, max);
                        if (last >= 0 && !criteria.verify(pos, min, max, last, subjects.size(), size)) {
                            return matchesRec(from + 1, tested);
                        }
                        last = pos;
                        findPos.add(pos);
                        continue outer;
                    }
                }
                return false;
            }

            return true;
        }

        private static int size(Iterable<?> objects) {
            int size = 0;
            for (Iterator<?> it = objects.iterator(); it.hasNext(); it.next()) {
                size++;
            }
            return size;
        }
    }
}
