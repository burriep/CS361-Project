package tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ TimerTest.class, SensorTest.class, ChannelTest.class, PrinterTest.class, RacerRunTest.class,
		RunTest.class, IndEventControllerTest.class, ParIndEventControllerTest.class, GrpEventControllerTest.class,
		ParGrpEventControllerTest.class, ChronoTimerTest.class })

public class Tests {
}
