package com.hive.harvest.eventing;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.eventing.tooling.EventLauncher;
import com.hive.harvest.eventing.tooling.LaunchableEvent;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.root.HQLTreeRootExpression;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/10.
 */
public class TestLaunchableEvent {
  private static class MyLaunchableEvent extends LaunchableEvent<HQLTreeRootExpression> {
    private boolean wasLaunched = false;

    public MyLaunchableEvent() {
      super(HQLTreeRootExpression.class);
    }

    @Override
    public boolean canLaunch(HQLExpression expression) {
      return true;
    }

    @Override
    public void launch(HQLExpression expression) {
      this.wasLaunched = true;
    }

    public boolean wasLaunched() {
      return this.wasLaunched;
    }
  }

  @Test
  public void testLaunchableEvent() {
    MyLaunchableEvent launchableEvent = new MyLaunchableEvent();
    EventLauncher<MyLaunchableEvent> eventLauncher = new EventLauncher<MyLaunchableEvent>()
      .add(launchableEvent);

    HQLCommand command = new HQLCommand("use mydb");
    HQLTreeRootExpression root = command.tree();

    assert (eventLauncher.canLaunch(root));
    eventLauncher.launch(root);

    assert (launchableEvent.wasLaunched());
  }
}
