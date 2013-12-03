package by.gsu.segg3r.rental.ui.filter;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import by.gsu.segg3r.rental.exceptions.DaoException;
import by.gsu.segg3r.rental.ifaces.IItemDao;
import by.gsu.segg3r.rental.ifaces.IItemUiStrings;
import by.gsu.segg3r.rental.ui.ItemFrame;
import by.gsu.segg3r.rental.ui.ItemTable;

public class FilterItemFrame<T> extends ItemFrame<T> {

	private static final long serialVersionUID = 1L;
	private FilterItemTable<T> filterItemTable;
	private JTextField filterTextField;

	public FilterItemFrame(IItemDao<T> itemDao, IItemUiStrings<T> uiStrings) {
		super(itemDao, uiStrings);
	}

	public JComponent getMainPanel() {
		JComponent component = super.getMainPanel();

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(component, BorderLayout.CENTER);

		JPanel filterPanel = new JPanel();
		filterPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

		filterPanel.add(new JLabel("Фильтр: "));

		filterTextField = new JTextField("", 20);
		filterPanel.add(filterTextField);
		filterTextField.getDocument().addDocumentListener(getFilterListener());

		panel.add(filterPanel, BorderLayout.NORTH);
		return panel;
	}

	public ItemTable<T> createItemTable() throws DaoException {
		this.filterItemTable = new FilterItemTable<T>(this, getItemDao(),
				getUiStrings());
		return this.filterItemTable;
	}

	private DocumentListener getFilterListener() {
		return new DocumentListener() {

			@Override
			public void changedUpdate(DocumentEvent ev) {
				filter();
			}

			@Override
			public void insertUpdate(DocumentEvent ev) {
				filter();
			}

			@Override
			public void removeUpdate(DocumentEvent ev) {
				filter();
			}

			private void filter() {
				filterItemTable.filter(filterTextField.getText());
			}

		};
	}

}
