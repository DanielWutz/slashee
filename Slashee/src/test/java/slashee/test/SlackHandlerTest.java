package slashee.test;
/*
 * This Java source file was generated by the Gradle 'init' task.
 */
import org.junit.Test;

import slashee.main.slack.SlackHandler;

import static org.junit.Assert.*;

public class SlackHandlerTest {
    @Test public void testSlackHandler() {
        assertNotNull(SlackHandler.getUserIds());
        assertFalse(SlackHandler.getUserIds().isEmpty());
        assertNotNull(SlackHandler.getIdToLabelMap());
        assertFalse(SlackHandler.getIdToLabelMap().isEmpty());
        assertNotNull(SlackHandler.getProfilePerUser());
        assertFalse(SlackHandler.getProfilePerUser().isEmpty());
    }
}
