import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class Login extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	String path = null;

	/**
	 * Create the frame.
	 */
	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setBounds(100, 100, 269, 231);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Sports Photos Auto-Uploader");
		lblNewLabel.setBounds(5, 5, 440, 15);
		contentPane.add(lblNewLabel);
		
		JLabel lblEmail = new JLabel("Username");
		lblEmail.setBounds(12, 32, 90, 15);
		contentPane.add(lblEmail);
		
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setBounds(12, 73, 70, 15);
		contentPane.add(lblPassword);
		
		textField = new JTextField();
		textField.setBounds(100, 32, 116, 27);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(102, 71, 114, 30);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblPath = new JLabel("Path");
		lblPath.setBounds(22, 118, 70, 15);
		contentPane.add(lblPath);
		
		JButton btnSelect = new JButton("Select...");
		btnSelect.setBounds(99, 113, 117, 25);
		btnSelect.addActionListener(new ActionListener() {
			 
            public void actionPerformed(ActionEvent e){
                JFileChooser filechooser = new JFileChooser();
                filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
              //In response to a button click:
                int result = filechooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                	   path = filechooser.getCurrentDirectory().toString()
       	                    + File.separatorChar + filechooser.getSelectedFile().getName();
                	if (result == JFileChooser.CANCEL_OPTION) {
                	    // Disregard
                	    }
                }
            }
        });   
		contentPane.add(btnSelect);
		
		JButton btnStart = new JButton("Start");
		btnStart.setBounds(99, 167, 117, 25);
		btnStart.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Main.username = textField.getText();  
				Main.password = textField_1.getText(); 
				Main.path = path;
				dispose();
			}
		});
		contentPane.add(btnStart);
		
		
	}
}
