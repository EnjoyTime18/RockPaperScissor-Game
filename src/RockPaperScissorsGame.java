import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RockPaperScissorsGame extends JFrame {
		// 재생할 오디오 파일을 지정. AudioLoading 클래스는 밑에 있음.
		AudioLoading win = new AudioLoading("C:/Shingu/ShinguProject/music/승리.wav");
		AudioLoading same = new AudioLoading("C:/Shingu/ShinguProject/music/비김.wav");
		AudioLoading defeat = new AudioLoading("C:/Shingu/ShinguProject/music/패배.wav");

		RockPaperScissorsGame() { //기본 세팅
			setTitle("가위바위보 게임");
			setSize(800, 500);
			setLocation(500, 150);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(UserPanel, "South"); //사용자가 선택할 패널를 아래쪽으로
			add(resultDisplay, "Center"); //결과 화면 패널를 중앙으로
			setVisible(true);
		}
		// img = ***로직 = 가위:0, 바위:1, 보:2 (**이미지 배열 생성)
		ImageIcon[] img = { new ImageIcon("img/가위.jpg"), new ImageIcon("img/바위.jpg"), new ImageIcon("img/보.jpg") };

		// 가위바위보 버튼만들기
		class UserSelectPanel extends JPanel {
			JButton[] btn = new JButton[3]; //사용자가 선택하는 3개의 버튼 생성

			public UserSelectPanel() { //사용자가 선택할 선택 패널 만들기
				setBackground(Color.blue); //사용자 선택할 배경색(블루)
				for (int i = 0; i < img.length; i++) { //for문을 이용해 이미지의 갯수(길이or크기)만큼 돌림 현재:이미지는 3개이기에 0~3개 돌려서 이미지 출력 
					btn[i] = new JButton(img[i]); //이미지에 버튼 생성
					this.add(btn[i]); //패널에 버튼을 add한다.
					//버튼을 클릭하였을때 선택한 버튼의 이미지가 나와야 하기에 addActionListener를 통해 EventHandler를 가져온다
					btn[i].addActionListener(new EventHandler());
				}
			}
		}

		UserSelectPanel UserPanel = new UserSelectPanel(); //사용자가 선택할 선택 패널 객체 생성

		// 가위바위보 결과(결과 화면 패널)
		class ResultDisplay extends JPanel { 
			JLabel user = new JLabel("User");
			JLabel com = new JLabel("Computer");
			JLabel result = new JLabel("Winner");

			public ResultDisplay() { //패널에 연결
				setBackground(Color.green); //결과 화면 배경색(그린)
				add(user);
				add(result);
				add(com);
			}

			public void output(Icon Userimg, Icon comlmg, String text) { //결과 화면 패널에 출력을 잡는 세팅
				user.setIcon(Userimg); //사용자가 선택한 이미지의 결과를 결과 화면 패널에 이미지 생성
				user.setHorizontalTextPosition(JLabel.LEFT); //결과를 표시할 User와 Computer 글자를 왼쪽으로 배치
				com.setIcon(comlmg); //컴퓨터가 랜덤으로 선택한 이미지의 결과를 결과 화면 패널에 이미지 생성
				result.setText(text); //결과 화면 패널에 결과값 글자 출력 (당신이 이겼습니다,비겼습니다,당신이 졌습니다)
				result.setForeground(Color.red); //결과 출력 글자 폰트 색깔(빨강)설정
				result.setFont(new Font(Font.DIALOG, Font.BOLD, 20)); //결과 출력 글자 폰트 설정과 크기 설정
			}
		}

	ResultDisplay resultDisplay = new ResultDisplay(); //결과 화면 패널 객체 생성

		class EventHandler implements ActionListener { //버튼 클릭 이벤트 핸들러(아래에 버튼을 클릭했을때의 이벤트 처리부분)
			@Override
			public void actionPerformed(ActionEvent ae) { //액션이벤트가 발생됬을떄 수행하는 동작
			//사용자가 선택한 버튼
			JButton btnUser = (JButton) ae.getSource(); //getSource가 Object 타입이기에 타입변환 해주기
			//컴퓨터의 랜덤선택 (***로직 = 0:가위, 1:바위, 2:보)
			int btnCom = (int) (Math.random() * 3); //컴퓨터 (AI 인공지능 랜덤) btnCom 생성
			//가위바위보 결과값을 저장할 문자열 선언
			String result = "";
			//사용자가 이기는 경우
			if (btnUser.getIcon() == img[0] && btnCom == 2 || btnUser.getIcon() == img[1] && btnCom == 0
					|| btnUser.getIcon() == img[2] && btnCom == 1) {
				result = "당신이 이겼습니다";
				win.play(); //이겼을때 사운드효과 호출
			} else if (btnUser.getIcon() == img[0] && btnCom == 0 || btnUser.getIcon() == img[1] && btnCom == 1
					|| btnUser.getIcon() == img[2] && btnCom == 2) { //사용자가 비기는 경우
				result = "비겼습니다";
				same.play(); //비겼을때 사운드효과 호출
			} else {
				result = "당신이 졌습니다"; //사용자가 패배할 경우
				defeat.play(); //패배했을때 사운드효과 호출
			}
			//결과값을 출력(버튼이 클릭되었을 때 위에 있는 그림이 바뀌고 결과 출력)
			resultDisplay.output(btnUser.getIcon(), img[btnCom], result); 
			/* btnUser.getIcon() = 사용자가 선택한 이미지 JLabel user에 추가해 글자와 함계 표시한다.
			   img[btnCom] = 컴퓨터가 랜덤으로 선택하는 이미지를 JLabel com에 추가해 글자와 함계 표시한다.
			   result = 만든 로직을 이용해 결과 표시 */
			
		}

	}

		// AudioLoading 클래스(오디오 재생)
		static class AudioLoading {
			Clip clip = null;
			File file;

			AudioLoading(String file) {
				this.file = new File(file);
		}

		void play() {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(this.file)); //오디오 스트림 객체
				// clip.loop(횟수) <-- 여러 번 재생 시
				clip.start(); //재생 시작
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
		}
	}

		public static void main(String[] args) {
			new RockPaperScissorsGame();
	}
}