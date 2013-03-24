import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Running extends JFrame {

	private JPanel contentPane;
	static JLabel lblFilesUploaded, lblBandwidth;

	/**
	 * Create the frame.
	 */
	public Running() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | UnsupportedLookAndFeelException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		setBounds(100, 100, 398, 169);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSports = new JLabel("Sports Photos Auto-Uploader");
		lblSports.setBounds(12, 12, 274, 15);
		contentPane.add(lblSports);
		
		JButton btnClose = new JButton("Close");
		btnClose.setBounds(271, 7, 117, 25);
		contentPane.add(btnClose);
		
		lblFilesUploaded = new JLabel("Files Uploaded : ");
		lblFilesUploaded.setBounds(12, 39, 245, 15);
		contentPane.add(lblFilesUploaded);
		
		lblBandwidth = new JLabel("Bandwidth : ");
		lblBandwidth.setBounds(12, 66, 245, 15);
		contentPane.add(lblBandwidth);
	}

}
