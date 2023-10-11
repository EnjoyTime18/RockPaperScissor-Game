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
		// ����� ����� ������ ����. AudioLoading Ŭ������ �ؿ� ����.
		AudioLoading win = new AudioLoading("C:/Shingu/ShinguProject/music/�¸�.wav");
		AudioLoading same = new AudioLoading("C:/Shingu/ShinguProject/music/���.wav");
		AudioLoading defeat = new AudioLoading("C:/Shingu/ShinguProject/music/�й�.wav");

		RockPaperScissorsGame() { //�⺻ ����
			setTitle("���������� ����");
			setSize(800, 500);
			setLocation(500, 150);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			add(UserPanel, "South"); //����ڰ� ������ �гθ� �Ʒ�������
			add(resultDisplay, "Center"); //��� ȭ�� �гθ� �߾�����
			setVisible(true);
		}
		// img = ***���� = ����:0, ����:1, ��:2 (**�̹��� �迭 ����)
		ImageIcon[] img = { new ImageIcon("img/����.jpg"), new ImageIcon("img/����.jpg"), new ImageIcon("img/��.jpg") };

		// ���������� ��ư�����
		class UserSelectPanel extends JPanel {
			JButton[] btn = new JButton[3]; //����ڰ� �����ϴ� 3���� ��ư ����

			public UserSelectPanel() { //����ڰ� ������ ���� �г� �����
				setBackground(Color.blue); //����� ������ ����(���)
				for (int i = 0; i < img.length; i++) { //for���� �̿��� �̹����� ����(����orũ��)��ŭ ���� ����:�̹����� 3���̱⿡ 0~3�� ������ �̹��� ��� 
					btn[i] = new JButton(img[i]); //�̹����� ��ư ����
					this.add(btn[i]); //�гο� ��ư�� add�Ѵ�.
					//��ư�� Ŭ���Ͽ����� ������ ��ư�� �̹����� ���;� �ϱ⿡ addActionListener�� ���� EventHandler�� �����´�
					btn[i].addActionListener(new EventHandler());
				}
			}
		}

		UserSelectPanel UserPanel = new UserSelectPanel(); //����ڰ� ������ ���� �г� ��ü ����

		// ���������� ���(��� ȭ�� �г�)
		class ResultDisplay extends JPanel { 
			JLabel user = new JLabel("User");
			JLabel com = new JLabel("Computer");
			JLabel result = new JLabel("Winner");

			public ResultDisplay() { //�гο� ����
				setBackground(Color.green); //��� ȭ�� ����(�׸�)
				add(user);
				add(result);
				add(com);
			}

			public void output(Icon Userimg, Icon comlmg, String text) { //��� ȭ�� �гο� ����� ��� ����
				user.setIcon(Userimg); //����ڰ� ������ �̹����� ����� ��� ȭ�� �гο� �̹��� ����
				user.setHorizontalTextPosition(JLabel.LEFT); //����� ǥ���� User�� Computer ���ڸ� �������� ��ġ
				com.setIcon(comlmg); //��ǻ�Ͱ� �������� ������ �̹����� ����� ��� ȭ�� �гο� �̹��� ����
				result.setText(text); //��� ȭ�� �гο� ����� ���� ��� (����� �̰���ϴ�,�����ϴ�,����� �����ϴ�)
				result.setForeground(Color.red); //��� ��� ���� ��Ʈ ����(����)����
				result.setFont(new Font(Font.DIALOG, Font.BOLD, 20)); //��� ��� ���� ��Ʈ ������ ũ�� ����
			}
		}

	ResultDisplay resultDisplay = new ResultDisplay(); //��� ȭ�� �г� ��ü ����

		class EventHandler implements ActionListener { //��ư Ŭ�� �̺�Ʈ �ڵ鷯(�Ʒ��� ��ư�� Ŭ���������� �̺�Ʈ ó���κ�)
			@Override
			public void actionPerformed(ActionEvent ae) { //�׼��̺�Ʈ�� �߻������� �����ϴ� ����
			//����ڰ� ������ ��ư
			JButton btnUser = (JButton) ae.getSource(); //getSource�� Object Ÿ���̱⿡ Ÿ�Ժ�ȯ ���ֱ�
			//��ǻ���� �������� (***���� = 0:����, 1:����, 2:��)
			int btnCom = (int) (Math.random() * 3); //��ǻ�� (AI �ΰ����� ����) btnCom ����
			//���������� ������� ������ ���ڿ� ����
			String result = "";
			//����ڰ� �̱�� ���
			if (btnUser.getIcon() == img[0] && btnCom == 2 || btnUser.getIcon() == img[1] && btnCom == 0
					|| btnUser.getIcon() == img[2] && btnCom == 1) {
				result = "����� �̰���ϴ�";
				win.play(); //�̰����� ����ȿ�� ȣ��
			} else if (btnUser.getIcon() == img[0] && btnCom == 0 || btnUser.getIcon() == img[1] && btnCom == 1
					|| btnUser.getIcon() == img[2] && btnCom == 2) { //����ڰ� ���� ���
				result = "�����ϴ�";
				same.play(); //������� ����ȿ�� ȣ��
			} else {
				result = "����� �����ϴ�"; //����ڰ� �й��� ���
				defeat.play(); //�й������� ����ȿ�� ȣ��
			}
			//������� ���(��ư�� Ŭ���Ǿ��� �� ���� �ִ� �׸��� �ٲ�� ��� ���)
			resultDisplay.output(btnUser.getIcon(), img[btnCom], result); 
			/* btnUser.getIcon() = ����ڰ� ������ �̹��� JLabel user�� �߰��� ���ڿ� �԰� ǥ���Ѵ�.
			   img[btnCom] = ��ǻ�Ͱ� �������� �����ϴ� �̹����� JLabel com�� �߰��� ���ڿ� �԰� ǥ���Ѵ�.
			   result = ���� ������ �̿��� ��� ǥ�� */
			
		}

	}

		// AudioLoading Ŭ����(����� ���)
		static class AudioLoading {
			Clip clip = null;
			File file;

			AudioLoading(String file) {
				this.file = new File(file);
		}

		void play() {
			try {
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(this.file)); //����� ��Ʈ�� ��ü
				// clip.loop(Ƚ��) <-- ���� �� ��� ��
				clip.start(); //��� ����
			} catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
				e.printStackTrace();
			}
		}
	}

		public static void main(String[] args) {
			new RockPaperScissorsGame();
	}
}