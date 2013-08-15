package com.trioptimum.shodan.matcher.internal;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 1/12/13
 * Time: 5:41 PM
 * To change this template use File | Settings | File Templates.
 */
public class ContainingCriterias {

    public static ContainingCriteria allOf() {
        return new AnyOrderContainingCriteria();
    }

    public static ContainingCriteria allOfOnly() {
        return new AllOfOnlyContainingCriteria();
    }

    public static ContainingCriteria together() {
        return new TogetherContainingCriteria();
    }

    public static ContainingCriteria inAscendingOrder() {
        return new AscendingOrderContainingCriteria();
    }

    public static ContainingCriteria inDescendingOrder() {
        return new DescendingOrderContainingCriteria();
    }

    public static ContainingCriteria inTrendOrder(int trend) {
        return new TrendContainingCriteria(trend);
    }

    public static ContainingCriteria firstAllOf() {
        return new FirstContainingCriteria();
    }

    public static ContainingCriteria lastAllOf() {
        return new LastContainingCriteria();
    }

    private static class AllOfOnlyContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return (subjectsCount == objectsCount);
        }
    }

    private static class AnyOrderContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return true;
        }
    }

    private static class TogetherContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return (max - min) <= (subjectsCount - 1);
        }
    }

    private static class AscendingOrderContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return (pos > lastPos);
        }
    }

    private static class DescendingOrderContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return (pos < lastPos);
        }
    }

    private static class TrendContainingCriteria implements ContainingCriteria {

        private final int trend;

        public TrendContainingCriteria(int trend) {
            this.trend = trend;
        }

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return (lastPos + trend) == pos;
        }
    }

    private static class FirstContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return pos < subjectsCount;
        }
    }

    private static class LastContainingCriteria implements ContainingCriteria {

        public boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount) {
            return min >= (objectsCount - subjectsCount);
        }
    }
}
