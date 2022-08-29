package bubble.test.ex06;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import lombok.Getter;
import lombok.Setter;

// class Player -> new 가능한 애들!! 게임에 존재할 수 있음. (추상메서드를 가질 수 없다.)
@Getter
@Setter
public class Player extends JLabel implements Moveable {

	// 위치 상태
	private int x;
	private int y;

	// 움직임 상태
	private boolean left;
	private boolean right;
	private boolean up;
	private boolean down;

	// 플레이어 속도 상태
	private final int SPEED = 4;
	private final int JUMPSPEED = 2; // up, down
	
	private ImageIcon playerR, playerL;

	public Player() {
		initObject();
		initSetting();
		initBackgroundPlayerService();
	}

	private void initObject() {
		playerR = new ImageIcon("image/playerR.png");
		playerL = new ImageIcon("image/playerL.png");
	}

	private void initSetting() {
		x = 80;
		y = 535;

		left = false;
		right = false;
		up = false;
		down = false;

		setIcon(playerR);
		setSize(50, 50);
		setLocation(x, y);
	}
	
	private void initBackgroundPlayerService() {
		new Thread(new BackgroundPlayerService(this)).start(); // BackgroundPlayerService 타입 자체가 Runnable 타입이라서 넝을 수 있다.
		// 이 파일 자체가 Player 파일이므로 this로 넘겨준다.
	}

	// 이벤트 핸들러
	@Override
	public void left() {
		System.out.println("left 쓰레드 생성");
		left = true;
		new Thread(() -> {
			while(left) { // left 버튼을 누르면 left가 true가 되니까 while문 계속 실행
				setIcon(playerL);
				x = x - SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void right() {
		System.out.println("right");
		right = true;
		new Thread(() -> {
			while(right) {
				setIcon(playerR);
				x = x + SPEED;
				setLocation(x, y);
				try {
					Thread.sleep(10); // 0.01초
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@Override
	public void up() {
		System.out.println("up");
		up = true;
		new Thread(()->{
			for(int i = 0 ; i<130/JUMPSPEED ; i++) { // 끝이 있으므로 for문을 돌린다. 안정적인 점프를 위해 넉넉히 잡는다.
				y = y - JUMPSPEED; // +가 아닌 -가 점프
				setLocation(x,y);
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			up = false; // 점프 후 하강
			down();
			
		}).start();
	}

	@Override
	public void down() {
		System.out.println("down");
		down = true;
		new Thread(() -> {
			for(int i = 0 ; i<130/JUMPSPEED ; i++) { // 끝이 있으므로 for문을 돌린다. 안정적인 점프를 위해 넉넉히 잡는다.
				y = y + JUMPSPEED; // +가 아닌 -가 점프
				setLocation(x,y);
				try {
					Thread.sleep(3);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			down = false;
		}).start();
	}
}
