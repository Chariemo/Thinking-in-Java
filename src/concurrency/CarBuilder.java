package concurrency;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.SystemException;

class MyCar {
	private final int id;
	private boolean engine = false, 
			driveTrain = false,
			wheels = false;
	public MyCar(int id) {
		this.id = id;
	}
	public int getId() {
		return this.id;
	}
	public synchronized void addEngine() {
		this.engine = true;
	}
	
	public synchronized void addDriveTrain() {
		this.driveTrain = true;
	}
	
	public synchronized void addWheels() {
		this.wheels = true;
	}
	
	public String toString() {
		return "Car " + id + " [" + " engine: " + engine + " driveTrain: " 
			+ driveTrain + " wheels: " + wheels + " ]";
	}
}


class CarQueue extends LinkedBlockingQueue<MyCar> {
	
}

class ChassisBuilder implements Runnable {
	private CarQueue carQueue;
	private int counter = 0;
	public ChassisBuilder(CarQueue carQueue) {
		this.carQueue = carQueue;
	}
	
	public void run() {
		try {
			while(!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(50);
				MyCar car = new MyCar(counter++);
				System.out.println("ChassisBuilder created " + car);
				carQueue.put(car);
			}
		} catch(InterruptedException exception) {
			System.out.println("Interrupted " + this);
		}
		System.out.println("ChassisBuilder Off");
	}
}

class Assembler implements Runnable {
	private CarQueue chassisQueue, finishQueue;
	private MyCar car;
	private CyclicBarrier barrier = new CyclicBarrier(4);
	private RobotPool robotPool;
	public Assembler(CarQueue chassisQueue, CarQueue finishQueue, RobotPool robotPool) {
		this.chassisQueue = chassisQueue;
		this.finishQueue  = finishQueue;
		this.robotPool = robotPool;
	}
	
	public MyCar car() {
		return car;
	}
	
	public CyclicBarrier barrier() {
		return barrier;
	}
	
	public void run() {
		try {
			while(!Thread.interrupted()) {
				car = chassisQueue.take();
				
				robotPool.hire(EngineRobot.class, this);
				robotPool.hire(DriveTrainRobot.class, this);				
				robotPool.hire(WheelRobot.class, this);				
				barrier.await();
				
				finishQueue.put(car);
				}
		} catch(InterruptedException exception) {
			System.out.println("Interrupted " + this);
		} catch(BrokenBarrierException exception) {
			exception.printStackTrace();
		}
		System.out.println("Assembler Off");
	}
}

class Reporter implements Runnable {
	private CarQueue carQueue;
	public Reporter(CarQueue carQueue) {
		this.carQueue = carQueue;
	}
	
	public void run() {
		try {
			while(!Thread.interrupted()) {
				System.out.println(carQueue.take());
			}
		} catch(InterruptedException exception) {
			System.out.println(this + " Interrupted");
		}
		System.out.println("Reporter Off");
	}
}

abstract class Robot implements Runnable {
	private RobotPool robotPool;
	public Robot(RobotPool robotPool) {
		this.robotPool = robotPool;
	}
	protected Assembler assembler;
	public Robot assignAssembler(Assembler assembler) {
		this.assembler = assembler;
		return this;
	}
	
	private boolean engage = false;
	public synchronized void engage() {
		this.engage = true;
		notifyAll();
	}
	
	abstract protected void performService();
	
	public void run() {
		try {
			powerDown();
			while(!Thread.interrupted()) {
				TimeUnit.MILLISECONDS.sleep(100);
				performService();
				assembler.barrier().await();
				powerDown();
			}
		} catch(InterruptedException exception) {
			System.out.println(this + " Interrupted");
		} catch (BrokenBarrierException exception) {
			throw new RuntimeException(exception);
		}
		System.out.println(this + " Off");
	}
	
	private synchronized void powerDown() throws InterruptedException {
		engage = false;
		assembler = null;
		robotPool.release(this);
		while(engage == false) {
			wait();
		}
	}
	
	public String toString() {
		return getClass().getName();
	}
}

class EngineRobot extends Robot {
	public EngineRobot(RobotPool robotPool) {
		super(robotPool);
	}
	protected void performService() {
		System.out.println(this + " installing engine");
		assembler.car().addEngine();
	}
}

class DriveTrainRobot extends Robot {
	public DriveTrainRobot(RobotPool robotPool) {
		super(robotPool);
	}
	protected void performService() {
		System.out.println(this + " installing DriveTrain");
		assembler.car().addDriveTrain();
	}
}

class WheelRobot extends Robot {
	public WheelRobot(RobotPool robotPool) {
		super(robotPool);
	}
	protected void performService() {
		System.out.println(this + " installing Wheel");	
		assembler.car().addWheels();
	}
}

class RobotPool {
	private Set<Robot> pool = new HashSet<Robot>();
	public synchronized void add(Robot robot) {
	
		pool.add(robot);
		notifyAll();
		
	}
	
	public synchronized void hire(Class<? extends Robot> robotType, Assembler assembler) throws
		InterruptedException {
		for (Robot robot : pool) {
			if (robot.getClass().equals(robotType)) {	
				pool.remove(robot);
				robot.assignAssembler(assembler);
				robot.engage();
				return;
			}
		}	
		wait();	
		hire(robotType, assembler);
	}
	
	public synchronized void release(Robot robot) {
		add(robot);
	}
}
public final class CarBuilder {
	public static void main(String[] args) throws InterruptedException {
		CarQueue chassisQueue = new CarQueue(), finishedQueue = new CarQueue();
		ExecutorService executorService = Executors.newCachedThreadPool();
		RobotPool robotPool = new RobotPool();
		for (int i = 0; i < 2; i++) {
			executorService.execute(new EngineRobot(robotPool));
			executorService.execute(new DriveTrainRobot(robotPool));
			executorService.execute(new WheelRobot(robotPool));
			executorService.execute(new Assembler(chassisQueue, finishedQueue, robotPool));	
		}
		executorService.execute(new Reporter(finishedQueue));
		executorService.execute(new ChassisBuilder(chassisQueue));
		TimeUnit.SECONDS.sleep(5);
		executorService.shutdownNow();
	}
}
