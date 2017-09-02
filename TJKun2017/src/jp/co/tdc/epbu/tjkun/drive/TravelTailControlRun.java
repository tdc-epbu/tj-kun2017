package jp.co.tdc.epbu.tjkun.drive;

import jp.co.tdc.epbu.tjkun.device.EV3;
import jp.co.tdc.epbu.tjkun.measure.Calibrater;

public class TravelTailControlRun implements Travel {


	EV3 ev3 = EV3.getInstance();
	public int tail;
	private float THRESHOLD;



	public TravelTailControlRun(Calibrater calibrater, int tail) {

		this.THRESHOLD = (calibrater.blackBaseline() + 2* calibrater.whiteBaseline()) / 3.0F;
		this.tail = tail;

	}

	public void travel(WheelSpeed speed) {
		float forward = speed.getWheelSpeedScaleLeft();
		float turn = jaggyTravel();
		ev3.controlBalance(forward, turn, tail);
	}

	/**
	 * ジグザグ走行制御
	 */
	public float jaggyTravel() {
		if (ev3.getBrightness() > THRESHOLD) {
			return -20.0F; // 右旋回命令
		} else {
			return 20.0F; // 左旋回命令
		}
	}

}
