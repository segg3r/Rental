package by.gsu.paveldzunovich.rental.ui.reportframe;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.sf.dynamicreports.report.exception.DRException;
import by.gsu.paveldzunovich.rental.impl.itemfields.SelectionItemField;
import by.gsu.paveldzunovich.rental.model.Rental;
import by.gsu.paveldzunovich.rental.reports.PayReport;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

import com.toedter.calendar.JDateChooser;

public class PayReportOptionFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public PayReportOptionFrame() {
		setTitle("Отчет по оплатам");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 454, 190);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(5, 5));
		setContentPane(contentPane);

		JPanel selectionPanel = new JPanel(new GridLayout(0, 2, 0, 0));
		JLabel titleLabel = new JLabel("Опции");
		titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
		contentPane.add(titleLabel, BorderLayout.NORTH);
		selectionPanel.add(new JLabel("Начиная с:"));

		final JDateChooser dateChooser = new JDateChooser(new Date());
		selectionPanel.add(dateChooser);

		selectionPanel.add(new JLabel("Прокат:"));
		final SelectionItemField<Rental> rentalSelector = new SelectionItemField<Rental>(
				"Прокат:", new Rental());
		selectionPanel.add(rentalSelector.getComponent());

		selectionPanel.add(new JLabel("Предмет проката:"));
		final JCheckBox rentalItemNeed = new JCheckBox();
		rentalItemNeed.setHorizontalAlignment(SwingConstants.CENTER);
		rentalItemNeed.setSelected(true);
		selectionPanel.add(rentalItemNeed);

		selectionPanel.add(new JLabel("График:"));
		final JCheckBox graphNeed = new JCheckBox();
		graphNeed.setHorizontalAlignment(SwingConstants.CENTER);
		selectionPanel.add(graphNeed);

		contentPane.add(selectionPanel, BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton showButton = new JButton("Показать");

		buttonPanel.add(showButton);
		contentPane.add(buttonPanel, BorderLayout.SOUTH);

		showButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					new PayReport(rentalItemNeed.isSelected(), graphNeed
							.isSelected(), rentalSelector.getValue(),
							dateChooser.getDate()).getReport().show(false);
				} catch (DRException | NullPointerException e) {
					UiErrorHandler.handleError("Ошибка построения отчета. "
							+ e.getMessage());
				}
			}
		});

	}
}
