package by.gsu.paveldzunovich.rental.ui.reportframe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.sf.dynamicreports.report.exception.DRException;

import by.gsu.paveldzunovich.rental.reports.RentalReport;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

import com.toedter.calendar.JDateChooser;
import javax.swing.SwingConstants;

public class RentalReportOptionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public RentalReportOptionFrame() {
		setTitle("\u041E\u0442\u0447\u0435\u0442 \u043F\u043E \u043F\u0440\u043E\u043A\u0430\u0442\u0430\u043C");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);

		JPanel selectionPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		selectionPanel.add(new JLabel("Начиная с:"));

		final JDateChooser dateChooser = new JDateChooser(new Date());
		selectionPanel.add(dateChooser);

		selectionPanel.add(new JLabel("Клиент:"));
		final JCheckBox clientNeeded = new JCheckBox();
		clientNeeded.setHorizontalAlignment(SwingConstants.CENTER);
		clientNeeded.setSelected(true);
		selectionPanel.add(clientNeeded);

		selectionPanel.add(new JLabel("Работник:"));
		final JCheckBox employeeNeeded = new JCheckBox();
		employeeNeeded.setHorizontalAlignment(SwingConstants.CENTER);
		employeeNeeded.setSelected(true);
		selectionPanel.add(employeeNeeded);

		selectionPanel.add(new JLabel("Предмет проката:"));
		final JCheckBox rentalItemNeeded = new JCheckBox();
		rentalItemNeeded.setHorizontalAlignment(SwingConstants.CENTER);
		rentalItemNeeded.setSelected(true);
		selectionPanel.add(rentalItemNeeded);

		selectionPanel.add(new JLabel("График:"));
		final JCheckBox graphNeeded = new JCheckBox();
		graphNeeded.setHorizontalAlignment(SwingConstants.CENTER);
		graphNeeded.setSelected(false);
		selectionPanel.add(graphNeeded);

		contentPane.add(selectionPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton showButton = new JButton("Показать");

		buttonPanel.add(showButton);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		showButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new RentalReport(dateChooser.getDate(), clientNeeded
							.isSelected(), employeeNeeded.isSelected(),
							rentalItemNeeded.isSelected(), graphNeeded.isSelected())
							.getReport().show(false);
				} catch (DRException | NullPointerException e) {
					UiErrorHandler.handleError("Ошибка построения отчета. " + e.getMessage());
				}
			}
		});

	}

}
