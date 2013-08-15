package com.trioptimum.shodan.common.internal

import org.testng.annotations.Test

import static com.trioptimum.shodan.matcher.api.BasicMatchers.is
import static com.trioptimum.shodan.matcher.api.IterableMatchers.*
import static com.trioptimum.shodan.matcher.api.MatchersList.item
import static com.trioptimum.shodan.matcher.api.MatchersList.these
import static org.testng.Assert.assertFalse
import static org.testng.Assert.assertTrue

/**
 * Created with IntelliJ IDEA.
 * User: trtl
 * Date: 1/26/13
 * Time: 10:04 PM
 * To change this template use File | Settings | File Templates.
 */
class ContainingTest {

    @Test void singleHas() {
        assertTrue  has(is(1)).matches([1])
        assertFalse has(is(1)).matches([2])
    }

    @Test void hasAnyOf() {
        assertTrue  hasAnyOf(item(is(1))).matches([1, 2])
        assertTrue  hasAnyOf(these(is(1), is(3))).matches([1, 2])
        assertTrue  hasAnyOf(these(is(3), is(1))).matches([1, 2])
        assertFalse hasAnyOf(item(is(3))).matches([1, 2])
        assertFalse hasAnyOf(these(is(3), is(4))).matches([1, 2])
    }

    @Test void hasFirstAnyOf() {
        assertTrue  hasFirstAnyOf(these(is(1), is(2))).matches([2, 1])
        assertFalse hasFirstAnyOf(these(is(1), is(1))).matches([2, 1])
    }

    @Test void hasLastAnyOf() {
        assertTrue  hasLastAnyOf(these(is(1), is(2))).matches([2, 1])
        assertFalse hasLastAnyOf(these(is(1), is(1))).matches([1, 2])
    }

    @Test void has() {
        assertTrue  has(these(is(1), is(2))).matches([2, 1, 2, 1])
        assertFalse has(these(is(1), is(2))).matches([1, 3, 2])
    }

    @Test void hasInAnyOrder() {
        assertTrue  hasInAnyOrder(these(is(1), is(2))).matches([2, 2, 3, 1])
        assertFalse hasInAnyOrder(these(is(2), is(2))).matches([1, 2])
    }

    @Test void hasTogetherInAnyOrder() {
        assertTrue  hasTogetherInAnyOrder(these(is(1), is(2))).matches([2, 3, 2, 1])
        assertFalse hasTogetherInAnyOrder(these(is(1), is(2))).matches([2, 3, 1])
    }

    @Test void hasInAscendingOrder() {
        assertTrue  hasInAscendingOrder(these(is(1), is(2))).matches([2, 1, 2])
        assertTrue  hasInAscendingOrder(these(is(1), is(3))).matches([1, 2, 3])
        assertFalse hasInAscendingOrder(these(is(2), is(1))).matches([1, 2])
    }

    @Test void descending() {
        assertTrue  hasInDescendingOrder(these(is(1), is(2))).matches([1, 2, 1])
        assertTrue  hasInDescendingOrder(these(is(3), is(2))).matches([2, 1, 3])
        assertTrue  hasInDescendingOrder(these(is(2), is(3))).matches([1, 3, 2])
        assertFalse hasInDescendingOrder(these(is(1), is(2))).matches([1, 2])
    }
    
    @Test void contains() {
        assertTrue  contains(these(is(1), is(2))).matches([1, 2])
        assertFalse contains(these(is(1), is(1))).matches([1, 2])
        assertFalse contains(these(is(2), is(2))).matches([1, 2])
        assertFalse contains(these(is(1), is(2))).matches([1, 2, 2])
        assertFalse contains(these(is(1), is(2))).matches([1, 1, 2])
    }

    @Test void containsInAnyOrder() {
        assertTrue  containsInAnyOrder(these(is(2), is(1))).matches([1, 2])
        assertFalse containsInAnyOrder(these(is(1), is(1))).matches([1, 2])
        assertFalse containsInAnyOrder(these(is(2), is(2))).matches([1, 2])
        assertFalse containsInAnyOrder(these(is(1), is(2))).matches([1, 2, 2])
        assertFalse containsInAnyOrder(these(is(1), is(2))).matches([1, 1, 2])
    }

    @Test void startWith() {
        assertTrue  startWith(these(is(1), is(2))).matches([1, 2, 2, 3])
        assertFalse startWith(these(is(1), is(1))).matches([1, 2, 1])
    }

    @Test void endWith() {
        assertTrue  endWith(these(is(1), is(2))).matches([1, 1, 2])
        assertFalse endWith(these(is(1), is(1))).matches([1, 1, 2])
    }

    @Test void startWithInAnyOrder() {
        assertTrue  startWithInAnyOrder(these(is(1), is(2))).matches([2, 1])
        assertFalse startWithInAnyOrder(these(is(1), is(1))).matches([1, 2, 1])
    }

    @Test void endWithInAnyOrder() {
        assertTrue  endWithInAnyOrder(these(is(1), is(2))).matches([2, 1])
        assertFalse endWithInAnyOrder(these(is(1), is(1))).matches([1, 1, 2])
        assertFalse endWithInAnyOrder(these(is(1), is(1))).matches([1, 3, 1])
    }
}
