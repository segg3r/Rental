package by.gsu.paveldzunovich.rental.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import by.gsu.paveldzunovich.rental.Application;
import by.gsu.paveldzunovich.rental.exceptions.UiException;
import by.gsu.paveldzunovich.rental.ui.util.UiErrorHandler;

public class SelectDialog<T> extends JDialog {

	private static final long serialVersionUID = 1L;
	private ItemFrame<T> frame;
	private T item;

	public SelectDialog(final ItemFrame<T> frame) {
		super((Frame) null, true);
		this.frame = frame;
		frame.getInitializedButtonPanel().setFocusable(false);
		JPanel panel = (JPanel) frame.getContentPane();
		setContentPane(panel);

		panel.remove(frame.getOuter());

		panel.add(getButtonPanel(), BorderLayout.SOUTH);
		setSize(frame.getSize());
		setTitle(frame.getTitle());

		KeyboardFocusManager.getCurrentKeyboardFocusManager()
				.addKeyEventDispatcher(new KeyEventDispatcher() {
					@Override
					public boolean dispatchKeyEvent(KeyEvent ev) {
						if (SwingUtilities.getRoot(ev.getComponent()) == SelectDialog.this)
							if (SelectDialog.this.isVisible()) {
								int code = ev.getKeyCode();
								if (code == KeyEvent.VK_ENTER) {
									Application.PRESSED = !Application.PRESSED;
									if (Application.PRESSED) {
										try {
											item = frame.getItemHolder()
													.getSelectedItem();
											closeDialog();
										} catch (UiException e) {
											item = null;
											UiErrorHandler.handleError(e
													.getMessage());
										}
									}
								} else if (code == KeyEvent.VK_ESCAPE) {
									Application.PRESSED = !Application.PRESSED;
									if (Application.PRESSED) {
										item = null;
										closeDialog();
									}

								}
							}

						return false;
					}
				});
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
