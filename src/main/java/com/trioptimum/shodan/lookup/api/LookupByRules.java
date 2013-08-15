package com.trioptimum.shodan.lookup.api;

import com.trioptimum.shodan.common.internal.*;
import com.trioptimum.shodan.lookup.internal.BindingContext;
import com.trioptimum.shodan.lookup.service.LookupRule;
import com.trioptimum.shodan.lookup.service.StoringLookup;
import com.trioptimum.shodan.matcher.service.Matcher;

import java.lang.ref.ReferenceQueue;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.trioptimum.shodan.matcher.api.BasicMatchers.none;

public final class LookupByRules implements StoringLookup {

	private final Map<LookupRule, Set<CallablePoint>> destinationsOfRuleMap;

	private final Matcher<? super CallablePoint> filter;

	private final ReentrantReadWriteLock.ReadLock readLock;

	private final ReentrantReadWriteLock.WriteLock writeLock;
	
	private final ReferenceQueue<Object> queue;

	private final Map<CallablePoint,Long> codes = new HashMap<CallablePoint,Long>();
	
	private long nextNonce = 1;

	public LookupByRules(LookupRule rule) {
		this(rule, null);
	}

	public LookupByRules(LookupRule rule, Matcher<? super CallablePoint> filter) {
		this(Collections.singleton(rule), filter);
	}

	public LookupByRules(Collection<? extends LookupRule> rules) {
		this(rules, null);
	}

	public LookupByRules(Collection<? extends LookupRule> rules, Matcher<? super CallablePoint> filter) {
        if (rules == null) {
            throw new NullPointerException("rules cannot be null");
        }
        if (rules.isEmpty()) {
            throw new IllegalArgumentException("rules cannot be empty");
        }
        if (filter != null) {
            this.filter = filter;
        } else {
            this.filter = none();
        }
        this.destinationsOfRuleMap = initMap(rules);
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
        this.queue = new ReferenceQueue<Object>();
    }

	private Map<LookupRule, Set<CallablePoint>> initMap(Collection<? extends LookupRule> rules) {
		Map<LookupRule, Set<CallablePoint>> map = new LinkedHashMap<LookupRule, Set<CallablePoint>>(rules.size());
		for (LookupRule lr : rules) {
			Comparator<Method> cmp = lr.getMethodComparator();
			if (cmp != null) {
				map.put(lr, new TreeSet<CallablePoint>(new MethodAndTotalOrder(cmp)));
			} else {
				map.put(lr, new HashSet<CallablePoint>());
			}
		}

		return map;
	}

	public LookupByRules add(Object object) {
		return add(false, object);
	}
	
	public LookupByRules addWeakly(Object object) {
		return add(true, object);
	}

	private LookupByRules add(boolean weakly, Object object) {
		writeLock.lock();
		try {
			expungeStaleEntries();
			assignToRules(weakly, object);
		} finally {
			writeLock.unlock();
		}

		return this;
	}

	public LookupByRules addAll(Collection<?> objects) {
		return addAll(false, objects);
	}
	
	public LookupByRules addAllWeakly(Collection<?> objects) {
		return addAll(true, objects);
	}
	
	private LookupByRules addAll(boolean weakly, Collection<?> objects) {
		writeLock.lock();
		try {
			expungeStaleEntries();
			for (Object recipient : objects) {
				assignToRules(weakly, recipient);
			}
		} finally {
			writeLock.unlock();
		}
		
		return this;
	}

	private void assignToRules(boolean weakly, Object recipient) {
		for (Method method : recipient.getClass().getMethods()) {
			CallablePoint cp = weakly ? new WeakCallablePointUsingReflection(method, recipient, queue)
							   : new CallablePointUsingReflection(method, recipient);
			for (Map.Entry<LookupRule, Set<CallablePoint>> entry : destinationsOfRuleMap.entrySet()) {
				if (entry.getKey().getDestinationMatcher().matches(cp)) {
					Set<CallablePoint> cps = entry.getValue();
					if (!weakly) {
						cps.remove(cp); // allows weak ref to be overridden by strong ref
					}
					cps.add(cp);
				}
			}
		}
	}

	public LookupByRules remove(Object object) {
		writeLock.lock();
		try {
			expungeStaleEntries();
			removeFromRules(object);
		} finally {
			writeLock.unlock();
		}

		return this;
	}

	public LookupByRules removeAll(Collection<?> objects) {
		writeLock.lock();
		try {
			expungeStaleEntries();
			for (Object recipient : objects) {
				removeFromRules(recipient);
			}
		} finally {
			writeLock.unlock();
		}

		return  this;
	}

	private void removeFromRules(Object recipient) {
		for (Set<CallablePoint> cps : destinationsOfRuleMap.values()) {
			Iterator<CallablePoint> it = cps.iterator();
			while (it.hasNext()) {
				if (it.next().getInstance() == recipient) {
					it.remove();
				}
			}
		}
	}

	public Bindings apply(Key key) {
        List<LookupRule> rules = new ArrayList<LookupRule>();
		List<CallablePoint> bs = new ArrayList<CallablePoint>();
		readLock.lock();
		try {
			for (Map.Entry<LookupRule, Set<CallablePoint>> entry : destinationsOfRuleMap.entrySet()) {
				LookupRule rule = entry.getKey();
				if (!rule.getKeyMatcher().matches(key)) {
					continue;
				}
                for (CallablePoint cp : entry.getValue()) {
                    if (cp instanceof WeakCallablePointUsingReflection) {
						cp = ((WeakCallablePointUsingReflection) cp).getStrong();
						if (cp == null) {
							continue;
						}
					}
					Binding b = new Binding(key, cp);
					if (!filter.matches(cp) && rule.getContextMatcher().matches(new BindingContext(b, rules, bs))) {
                        bs.add(cp);
					}
				}
                rules.add(rule);
			}
		} finally {
			readLock.unlock();
		}

		return new Bindings(key, bs);
	}
	
	private void expungeStaleEntries() {
		WeakCallablePoint wcp;
		while ((wcp = (WeakCallablePoint) queue.poll()) != null) {
			codes.remove(wcp);
			for (Set<CallablePoint> cps : destinationsOfRuleMap.values()) {
				cps.remove(wcp);
			}
		}
	}
	
	private class MethodAndTotalOrder implements Comparator<CallablePoint> {
	    
		final Comparator<Method> methodComparator;
	    
	    MethodAndTotalOrder(Comparator<Method> methodComparator) {
			this.methodComparator = methodComparator;
		}
	    
	    Long getNonce(CallablePoint cp) {
	        Long nonce = codes.get(cp);
	        if (nonce == null) {
	            nonce = nextNonce++;
	            codes.put(cp, nonce);
	        }
	        return nonce;
	    }
	    
		public int compare(CallablePoint cp1, CallablePoint cp2) {
			if (cp1 == cp2) {
				return 0;
			}
			
			int m = methodComparator.compare(cp1.getMethod(), cp2.getMethod());
			if (m != 0) {
				return m;
			}
			
			int i1 = System.identityHashCode(cp1);
			int i2 = System.identityHashCode(cp2);
			if (i1 != i2) {
				return (i1 < i2) ? -1 : 1;
			}

            return getNonce(cp1).compareTo(getNonce(cp2));
		}
	}
}
