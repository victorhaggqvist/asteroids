import org.junit.Test;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import com.ecsoft.asteroids.model.Player;

/**
 * Tests if player repositioning when out of the screen boundaries is accurate. 
 * 
 * @author Josef_R
 * @since: 2/25/14
 */
public class PlayerOutOfBoundsTest {

	Player player;
	private final int SCREEN_WIDTH = 1000;
    private final int SCREEN_HEIGHT = 600;
    
	
	@Test
	public void test() {
		player = new Player(0,0);
		testLeftBound();
		testRightBound();
		testTopBound();
		testBotBound();
	}
	
	/**
	 * Tests for when player exits through left boundary, with different
	 * conditions for the vertical (y) position as well.
	 */
	private void testLeftBound() {
		for(int i=-1; i<1; i++) {
			for(int j=-1; j<1; j++) {
				player.setPosition(i, SCREEN_HEIGHT*j);
				player.updatePos();
				if(i<0)
					assertEquals(player.getPosition().x, SCREEN_WIDTH, 0);
				else
					assertNotEquals(player.getPosition().x, SCREEN_WIDTH, 0);
			}
		}
	}
	
	/**
	 * Tests for when player exits through right boundary, with different
	 * conditions for the vertical (y) position as well.
	 */
	private void testRightBound() {
		for(int i=-1; i<1; i++) {
			for(int j=-1; j<1; j++) {
				player.setPosition(SCREEN_WIDTH+i, SCREEN_HEIGHT*j);
				player.updatePos();
				if(i>0)
					assertEquals(player.getPosition().x, 0, 0);
				else
					assertNotEquals(player.getPosition().x, 0, 0);
			}
		}
	}
	
	/**
	 * Tests for when player exits through bottom boundary, with different
	 * conditions for the horizontal (x) position as well.
	 */
	private void testBotBound() {
		for(int i=-1; i<1; i++) {
			for(int j=-1; j<1; j++) {
				player.setPosition(SCREEN_WIDTH*j, SCREEN_HEIGHT+i);
				player.updatePos();
				if(i>0)
					assertEquals(player.getPosition().y, 0, 0);
				else
					assertNotEquals(player.getPosition().y, 0, 0);
			}
		}
	}
	
	/**
	 * Tests for when player exits through top boundary, with different
	 * conditions for the horizontal (x) position as well.
	 */
	private void testTopBound() {
		for(int i=-1; i<1; i++) {
			for(int j=-1; j<1; j++) {
				player.setPosition(SCREEN_WIDTH*j, i);
				player.updatePos();
				if(i<0)
					assertEquals(player.getPosition().y, SCREEN_HEIGHT, 0);
				else
					assertNotEquals(player.getPosition().y, SCREEN_HEIGHT, 0);
			}
		}
	}

}
