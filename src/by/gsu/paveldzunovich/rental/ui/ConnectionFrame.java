package by.gsu.paveldzunovich.rental.ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.connection.DbConnection;
import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.factories.DaoFactory;
import by.gsu.paveldzunovich.rental.ifaces.IEmployeeDao;
import by.gsu.paveldzunovich.rental.model.Employee;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class ConnectionFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel contentPane;
	private JTextField urlTextField;
	private JTextField portTextField;
	private JTextField loginTextField;
	private JTextField passwordTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectionFrame frame = new ConnectionFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ConnectionFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 237);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panel.setBounds(10, 43, 443, 111);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("\u0410\u0434\u0440\u0435\u0441 : ");
		lblNewLabel_1.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel_1);

		urlTextField = new JTextField();
		urlTextField.setText("localhost");
		urlTextField.setBounds(66, 8, 205, 20);
		panel.add(urlTextField);
		urlTextField.setColumns(10);

		JLabel label = new JLabel("\u041F\u043E\u0440\u0442 :");
		label.setBounds(281, 11, 46, 14);
		panel.add(label);

		portTextField = new JTextField();
		portTextField.setText("1443");
		portTextField.setBounds(337, 8, 96, 20);
		panel.add(portTextField);
		portTextField.setColumns(10);

		JLabel label_1 = new JLabel(
				"\u0418\u043C\u044F \u043F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044F :");
		label_1.setBounds(10, 58, 127, 14);
		panel.add(label_1);

		JLabel label_2 = new JLabel("\u041F\u0430\u0440\u043E\u043B\u044C :");
		label_2.setBounds(10, 83, 127, 14);
		panel.add(label_2);

		loginTextField = new JTextField();
		loginTextField
				.setText("\u0421\u0435\u0440\u0433\u0435\u0435\u0432 \u0421\u0435\u0440\u0433\u0435\u0439 \u0421\u0435\u0440\u0433\u0435\u0435\u0432\u0438\u04472");
		loginTextField.setBounds(174, 56, 259, 20);
		panel.add(loginTextField);
		loginTextField.setColumns(10);

		passwordTextField = new JTextField();
		passwordTextField.setHorizontalAlignment(SwingConstants.LEFT);
		passwordTextField
				.setText("\u0421\u0435\u0440\u0433\u0435\u0435\u0432 \u0421\u0435\u0440\u0433\u0435\u0439 \u0421\u0435\u0440\u0433\u0435\u0435\u0432\u0438\u04472");
		passwordTextField.setColumns(10);
		passwordTextField.setBounds(174, 80, 259, 20);
		panel.add(passwordTextField);

		JLabel lblNewLabel = new JLabel(
				"\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u0435 \u043A \u0441\u0435\u0440\u0432\u0435\u0440\u0443 :");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 12, 258, 20);
		contentPane.add(lblNewLabel);

		JButton btnNewButton = new JButton(
				"\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0438\u0442\u044C\u0441\u044F");

		btnNewButton.setBounds(310, 165, 143, 23);
		contentPane.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				try {
					DbConnection.initialize(urlTextField.getText(),
							portTextField.getText());
					IEmployeeDao dao = DaoFactory.getEmployeeDao();

					Employee employee = dao.getEmployee(
							loginTextField.getText(),
							passwordTextField.getText());

					Application.employee = employee;
					dispose();

					new MainFrame().setVisible(true);
				} catch (DaoException e) {
					UiErrorHandler
							.handleError("Ошибка инициализации подключения. "
									+ e.getMessage());
				}
			}
		});
	}
}
