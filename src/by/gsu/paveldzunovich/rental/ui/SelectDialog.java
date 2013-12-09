package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class SelectDialog<T> extends JDialog {

	private static final long serialVersionUID = 1L;
	private ItemFrame<T> frame;
	private T item;

	public SelectDialog(ItemFrame<T> frame) {
		super((Frame) null, true);
		this.frame = frame;
		JPanel panel = (JPanel) frame.getContentPane();
		setContentPane(panel);

		panel.add(getButtonPanel(), BorderLayout.SOUTH);
		setSize(frame.getSize());
	}

	private Component getButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
		JButton chooseButton = new JButton("Выбрать");
		chooseButton.setActionCommand("OK");

		chooseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ev) {
				try {
					item = frame.getItemHolder().getSelectedItem();
					closeDialog();
				} catch (UiException e) {
					item = null;
					UiErrorHandler.handleError(e.getMessage());
				}
			}
		});

		panel.add(chooseButton);

		JButton cancelButton = new JButton("Назад");
		cancelButton.setActionCommand("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				item = null;
				closeDialog();
			}
		});
		panel.add(cancelButton);
		return panel;
	}
	
	public T getItem() {
		setVisible(true);
		return item;
	}
	
	private void closeDialog() {
		setVisible(false);
		dispose();
	}

}
