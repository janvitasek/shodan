package test;

import com.trioptimum.shodan.facade.api.Shodan;
import com.trioptimum.shodan.facade.service.Delegation;

import javax.naming.Context;
import javax.naming.NamingException;

public class Test {

	public static void main(String[] args) {
        rulesTest();
    }

	private static void rulesTest() {
		System.out.println("now starting...");
        System.out.println("now calling...");
        Delegation delegation = new Shodan(new TestSettings()).add(new TestClass()).add(new TestClass());
        Context c = delegation.createDelegate(Context.class, null);
        try {
            c.destroySubcontext("value");
        } catch (NamingException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

	public static class TestClass {

		static int counter;

        public String getUser() {
            return "user";
        }

        public String metoda() {
			System.out.println("zavolano " + counter);
			return "called" + counter++;
		}

		@Deprecated
		public String metoda(String s) {
			System.out.println("zavolano " + s + " " + counter);
			return "called" + counter++;
		}

		public void metoda2() {
			System.out.println("another");
		}
		public void metoda3() {
			System.out.println("another");
		}
		public void metoda4() {
			System.out.println("another");
		}
		public void metoda5() {
			System.out.println("another");
		}
		public void metoda6() {
			System.out.println("another");
		}
		public void metoda7() {
			System.out.println("another");
		}
	}
}
