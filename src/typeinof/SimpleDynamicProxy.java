package typeinof;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

interface Interface {
	void doSomething();
	void doSomethingElse(String arg);
}


class RealObject implements Interface {
	
	@Override
	public void doSomething() {
		// TODO Auto-generated method stub
		System.out.println("doSomething");
	}

	@Override
	public void doSomethingElse(String arg) {
		// TODO Auto-generated method stub
		System.out.println("doSomething Else " + arg);
	}
}

class DynamicProxyhandler implements InvocationHandler {
	private Object proxied;
	public DynamicProxyhandler(Object proxied) {
		// TODO Auto-generated constructor stub
		this.proxied = proxied;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		System.out.println("****proxy: " + proxy.getClass() + ", method: " + method + ", args: " + args);
		if (args != null) {
			for (Object arg : args) {
				System.out.println(" " + arg);
			}
		}
		return method.invoke(proxied, args);	
	}
}

public class SimpleDynamicProxy {
	public static void consumer(Interface iface) {
		iface.doSomething();
		iface.doSomethingElse("beeeee!");
	}
	
	public static void main(String[] args) {
		RealObject real = new RealObject();
		consumer(real);
		Interface proxy = (Interface) Proxy.newProxyInstance(Interface.class.getClassLoader(), 
				new Class[]{Interface.class}, new DynamicProxyhandler(real));
		consumer(proxy);
	}
}
