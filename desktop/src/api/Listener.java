package api;

public interface Listener {
	void sensorUpdated(int player, int sensor, float[] stuffAboutWhichICare);
}
