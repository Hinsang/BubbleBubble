package bubble.test.ex07;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class BubbleFrame extends JFrame{
	
	private JLabel backgroundMap;
	private Player player;	// Player 객체
	
	public BubbleFrame() {
		initObject();
		initSetting();
		initListener();
		setVisible(true);
	}
	
	private void initObject() {
		backgroundMap = new JLabel(new ImageIcon("image/backgroundMap.png"));
		setContentPane(backgroundMap);
		player = new Player();
		add(player);
	}
	
	private void initSetting() {
		setSize(1000, 640);
		setLayout(null); // absolute 레이아웃 (자유롭게 그림을 그릴 수 있다.)
		setLocationRelativeTo(null); // JFrame 가운데 배치하기
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // x버튼으로 창을 끌 때 JVM 같이 종료하기
	}
	
	private void initListener() {
		addKeyListener(new KeyAdapter() {
			
			// 키보드 클릭 이벤트 핸들러
			@Override
			public void keyPressed(KeyEvent e) {
				//System.out.println(e.getKeyCode());
				
				switch(e.getKeyCode()) { // e.getKeyCode()메서드로 받았으면
				case KeyEvent.VK_LEFT:	// 각각 콘솔의 상하좌우 키값이 다 다르므로 KeyEvent.값으로 가독성을 높인다. (각 값은 VK_LEFT에서 컨트롤 클릭하면 나온다. 16진수를 10으로 변환한 값)
					if(!player.isLeft() && !player.isLeftWallCrash()) {						
						player.left();
					}
					break;
				case KeyEvent.VK_RIGHT: // 버튼을 눌렀을때
					if(!player.isRight() && !player.isRightWallCrash()) { // 가고있는 상태가 아닐때만(여러번 중첩 방지 on/off 기능)
						player.right(); // 오른쪽 이동
					}
					break;
				case KeyEvent.VK_UP:
					if(!player.isUp() && !player.isDown()) { // 위를 누르거나 아래를 누르지 않을때 up버튼 한번만 눌리게					
						player.up();
					}
					break;
				}
			}
			
			// 키보드 해제 이벤트 핸들러
			@Override
			public void keyReleased(KeyEvent e) {
				switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					player.setLeft(false);
					break;
				case KeyEvent.VK_RIGHT:
					player.setRight(false);
					break;
				}
			}
			
		});
	}
	
	public static void main(String[] args) {
		new BubbleFrame();
	}
}
