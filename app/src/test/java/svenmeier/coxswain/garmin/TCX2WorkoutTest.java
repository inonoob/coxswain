package svenmeier.coxswain.garmin;

import android.util.Pair;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import svenmeier.coxswain.gym.Snapshot;
import svenmeier.coxswain.gym.Workout;

import static org.junit.Assert.assertEquals;

/**
 * Test for {@link TCX2Workout}.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = svenmeier.coxswain.BuildConfig.class, sdk = 18)
public class TCX2WorkoutTest {

	private static final long Mon_Jun_15_2015 = 1434326400000l;

	@Test
	public void snapshots() throws IOException, ParseException {
		Reader reader = new InputStreamReader(getClass().getResourceAsStream("/snapshots.tcx"));

		Pair<Workout, List<Snapshot>> pair = new TCX2Workout(reader).workout();

		Workout workout = pair.first;

		assertEquals(Mon_Jun_15_2015 + (60 * 1000), (long)workout.start.get());
		assertEquals(3, (int)workout.duration.get());
		assertEquals(6, (int)workout.distance.get());
		assertEquals(2, (int)workout.strokes.get());
		assertEquals(3, (int)workout.energy.get());

		List<Snapshot> snapshots = pair.second;

		assertEquals(3, snapshots.size());

		Snapshot snapshot = snapshots.get(0);
		assertEquals(4_50, (int)snapshot.speed.get());
		assertEquals(80, (int)snapshot.pulse.get());
		assertEquals(25, (int)snapshot.strokeRate.get());
		assertEquals(2, (int)snapshot.distance.get());
		assertEquals(0, (int)snapshot.strokes.get()); // trackpoints do not have steps

		snapshot = snapshots.get(1);
		assertEquals(4_51, (int)snapshot.speed.get());
		assertEquals(81, (int)snapshot.pulse.get());
		assertEquals(26, (int)snapshot.strokeRate.get());
		assertEquals(4, (int)snapshot.distance.get());
		assertEquals(0, (int)snapshot.strokes.get()); // trackpoints do not have steps

		snapshot = snapshots.get(2);
		assertEquals(4_52, (int)snapshot.speed.get());
		assertEquals(82, (int)snapshot.pulse.get());
		assertEquals(27, (int)snapshot.strokeRate.get());
		assertEquals(6, (int)snapshot.distance.get());
		assertEquals(0, (int)snapshot.strokes.get()); // trackpoints do not have steps
	}
}