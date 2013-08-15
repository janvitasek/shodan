package com.trioptimum.shodan.matcher.internal;

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 1/12/13
 * Time: 3:19 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContainingCriteria {

    boolean verify(int pos, int min, int max, int lastPos, int subjectsCount, int objectsCount);
}
