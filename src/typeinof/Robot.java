package typeinof;

import java.util.Arrays;
import java.util.List;

import net.mindview.util.Null;

interface Operation {
	String description();
	void command();
}

interface Robot {
	String name();
	String model();
	List<Operation> operations();
	class Test {
		public static void test(Robot robot) {
			if (robot instanceof Null) {
				System.out.println("[NULL Robot]");
			}
			System.out.println("Robot name: " + robot.name());
			System.out.println("Robot model: " + robot.model());
			for (Operation operation : robot.operations()) {
				System.out.println(operation.description());
				operation.command();
			}
		}
	}
}

class SnowRemoveRobot implements Robot {
	private String name;
	public SnowRemoveRobot(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	@Override
	public String name() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public String model() {
		// TODO Auto-generated method stub
		return "Snowrot Series 11";
	}

	@Override
	public List<Operation> operations() {
		// TODO Auto-generated method stub
		return Arrays.asList(new Operation() {

			@Override
			public String description() {
				// TODO Auto-generated method stub
				return name + " can shovel snow";
			}

			@Override
			public void command() {
				// TODO Auto-generated method stub
				System.out.println(name + " shoveling snow");
			}
		}, new Operation() {

			@Override
			public String description() {
				// TODO Auto-generated method stub
				return name + " can chipping ice";
			}

			@Override
			public void command() {
				// TODO Auto-generated method stub
				System.out.println(name + " chipping ice");
			}
		}, new Operation() {

			@Override
			public String description() {
				// TODO Auto-generated method stub
				return name + " can clear the roof";
			}

			@Override
			public void command() {
				// TODO Auto-generated method stub
				System.out.println(name + " clearing roof");
			}
		});
	}
	
}
