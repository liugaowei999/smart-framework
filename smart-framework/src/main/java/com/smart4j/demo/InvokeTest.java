package com.smart4j.demo;

import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;

public class InvokeTest {

	class GrandFather {
		void thinking() {
			System.out.println("grandfather");
		}
	}

	class Father extends GrandFather {
		@Override
		void thinking() {
			System.out.println("father");
		}
	}

	class Son extends Father {
		@Override
		void thinking() {
			System.out.println(getClass());
			try {
				MethodType mt = MethodType.methodType(Void.class);

				/**
				 * Returns a lookup object with full capabilities to emulate all supported
				 * bytecode behaviors of the caller. These capabilities include private access
				 * to the caller. Factory methods on the lookup object can create direct method
				 * handles for any member that the caller has access to via bytecodes, including
				 * protected and private fields and methods. This lookup object is a capability
				 * which may be delegated to trusted agents. Do not store it in place where
				 * untrusted code can access it. This method is caller sensitive, which means
				 * that it may return different values to different callers.
				 * 
				 * For any given caller class C, the lookup object returned by this call has
				 * equivalent capabilities to any lookup object supplied by the JVM to the
				 * bootstrap method of an invokedynamic instruction executing in the same caller
				 * class C.
				 * 
				 * Returns: a lookup object for the caller of this method, with private access
				 */
				MethodHandle mh = lookup().findSpecial(GrandFather.class, "thinking", mt, getClass());
				mh.invoke(this);
				System.out.println(getClass());

			} catch (Exception e) {
				// TODO: handle exception
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("ddddd");
		(new InvokeTest().new Father()).thinking();
		(new InvokeTest().new Son()).thinking();
	}
}
