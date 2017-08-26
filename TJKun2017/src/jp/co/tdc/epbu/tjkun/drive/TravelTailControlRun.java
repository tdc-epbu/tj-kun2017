package jp.co.tdc.epbu.tjkun.drive;

import jp.co.tdc.epbu.tjkun.device.BalancerControl;
import jp.co.tdc.epbu.tjkun.device.DeviceFactory;
import jp.co.tdc.epbu.tjkun.device.LightSensor;
import jp.co.tdc.epbu.tjkun.measure.Calibrater;

public class TravelTailControlRun implements Travel {


    private LightSensor lightSensor;
    private BalancerControl balancerControl;

	public int tail;
	private float THRESHOLD;
	private WheelSpeed speed;

	public TravelTailControlRun(WheelSpeed speed, int tail) {
		this.speed = speed;

	      DeviceFactory df = DeviceFactory.getInstance();

	        lightSensor = df.getLightSensor();
	        balancerControl = df.getBalancerControl();

		Calibrater calibrater = Calibrater.getInstance();
		this.THRESHOLD = (calibrater.blackBaseline() + calibrater.whiteBaseline()) / 2.0F;
		this.tail = tail;

	}

	public void travel() {
		float forward = speed.getWheelSpeedScaleLeft();
		float turn = jaggyTravel();
		balancerControl.controlBalance(forward, turn, tail);
	}

	/**
	 * ジグザグ走行制御
	 */
	public float jaggyTravel() {
		if (lightSensor.getBrightness() > THRESHOLD) {
			return 20.0F; // 右旋回命令
		} else {
			return -20.0F; // 左旋回命令
		}
	}

}
