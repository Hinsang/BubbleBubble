package bubble.test.ex08;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

// 메인스레드 바쁨 ~ 키보드 이벤트를 처리하기 바쁨
// 백그라운드에서 계속 관찰
public class BackgroundPlayerService implements Runnable{
 
	private BufferedImage image;
	private Player player;
	
	public BackgroundPlayerService(Player player) {
		this.player = player;
		try {
			image = ImageIO.read(new File("image/backgroundMapService.png"));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@Override
	public void run() {
		while(true) {
			// 색상 확인
			Color leftColor = new Color(image.getRGB(player.getX() - 10, player.getY() + 25));
			Color rightColor = new Color(image.getRGB(player.getX() + 50 + 15, player.getY() + 25));
			int bottomColor = image.getRGB(player.getX() + 10, player.getY() + 50 + 5) // +10 정도 여유줘서 잘 떨어지게함
					+ image.getRGB(player.getX()+50, player.getY() + 50 + 5); // 가장 오른쪽 아래도 확인
					// -2가 나온다는 뜻은 바닥에 색깔이 없이 흰색
			
			// 바닥 충돌 확인
			if(bottomColor != -2) { // bottomColor가 -1이 아니면 무조건 바닥이 아니라는 의미니까 착지한다.
				//System.out.println("바텀 컬러 : "+bottomColor);
				//System.out.println("바닥에 충돌함");
				player.setDown(false);
			}
			
			// 외벽 충돌 확인
			if(leftColor.getRed() == 255 && leftColor.getGreen() == 0 && leftColor.getBlue() == 0) {
				//System.out.println("왼쪽 벽에 충돌함");
				player.setLeftWallCrash(true); // 왼쪽 충돌했을때
				player.setLeft(false); // 왼쪽 못가게
			} else if(rightColor.getRed() == 255 && rightColor.getGreen() == 0 && rightColor.getBlue() == 0) {
				//System.out.println("오른쪽 벽에 충돌함");
				player.setRightWallCrash(true);
				player.setRight(false);
			} else { // 왼쪽벽이나 오른쪽벽에 충돌을 안했을때
				player.setLeftWallCrash(false);
				player.setRightWallCrash(false);
			}
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
