import com.ecsoft.asteroids.controller.Controller;
import com.ecsoft.asteroids.model.NoHPLeftException;
import com.ecsoft.asteroids.model.Player;
import com.ecsoft.asteroids.model.SettingsManager;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Name: Asteroids
 * Description: MiaoTest
 *
 * @author: Victor Häggqvist
 * @since: 2/25/14
 * Package: PACKAGE_NAME
 */
public class MiaoTest {

    /**
     * Test NoHPLeftExeptions
     */
    @Test
    public void testPlayer(){
        Player player = new Player(1,2);

        boolean noHPleft=false;
        try {
            for (int i = 0; i < 10; i++) {
                player.setInvincible(false);
                player.takeDamage();
            }
        } catch (NoHPLeftException e) {
            noHPleft = true;
        }
        assertTrue("Test NoHPLeftExeption",noHPleft);
    }

    @Test
    public void testGameInitiation(){
        Controller ctrl = new Controller();
        ctrl.initiateGame(SettingsManager.getInstance().getDifficulty());
        assertTrue("If there is asteroids, we are good",ctrl.asteroids.size()>0);
    }
}
