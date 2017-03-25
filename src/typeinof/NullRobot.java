package typeinof;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.List;

import net.mindview.util.Null;

class NullRobotProxyHandler implements InvocationHandler {
	private String nullName;
	private Robot proxied = new NRobot();
	public NullRobotProxyHandler(Class<? extends Robot> type) {
		// TODO Auto-generated constructor stub
		nullName = type.getSimpleName() + " NullRobot";
	}
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// TODO Auto-generated method stub
		return method.invoke(proxied, args);
	}
	private class NRobot implements Null, Robot {

		@Override
		public String name() {
			// TODO Auto-generated method stub
			return nullName;
		}

		@Override
		public String model() {
			// TODO Auto-generated method stub
			return nullName;
		}

		@Override
		public List<Operation> operations() {
			// TODO Auto-generated method stub
			return Collections.emptyList();
		}
	}
}
public class NullRobot {
	public static Robot newNullRobot(Class<? extends Robot> type) {
		return (Robot)Proxy.newProxyInstance(Robot.class.getClassLoader(), 
				new Class[] {Null.class, Robot.class}, new NullRobotProxyHandler(type));
	}
	public static void main(String[] args) {                                                                                            
		Robot[]	 bRobots = {new SnowRemoveRobot("SnowBee"), newNullRobot(SnowRemoveRobot.class)};
		for (Robot bRobot : bRobots) {
			Robot.Test.test(bRobot);
		}
	}
}
