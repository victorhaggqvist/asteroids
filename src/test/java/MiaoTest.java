import com.ecsoft.asteroids.model.NoHPLeftException;
import com.ecsoft.asteroids.model.Player;
import com.ecsoft.asteroids.model.PlayerProjectile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Name: Asteroids
 * Description: MiaoTest
 *
 * @author: Victor Häggqvist
 * @since: 2/25/14
 * Package: PACKAGE_NAME
 */
public class MiaoTest {
    @Test
    public void wof(){
        assertEquals(1,1);
    }

    @Test
    public void testProjectile(){
        PlayerProjectile playerProjectile = new PlayerProjectile(0.4f,0.5f,3.0);

    }

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
        assertEquals("Test NoHPLeftExeption",true,noHPleft);
    }
}
