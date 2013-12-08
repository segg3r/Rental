package by.gsu.paveldzunovich.rental.ui.impl;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import by.gsu.paveldzunovich.rental.exceptions.DaoException;
import by.gsu.paveldzunovich.rental.ifaces.IItemDao;
import by.gsu.paveldzunovich.rental.ifaces.IItemHolder;
import by.gsu.paveldzunovich.rental.ifaces.IUiStrings;
import by.gsu.paveldzunovich.rental.model.Client;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemFrame;
import by.gsu.paveldzunovich.rental.ui.filter.FilterItemList;

public class ClientItemFrame extends FilterItemFrame<Client> {
	
	private static final long serialVersionUID = 1L;

	public ClientItemFrame(IItemDao<Client> itemDao,
			IUiStrings<Client> uiStrings) {
		super(itemDao, uiStrings);
	}

	public void initializeFrame() {
		super.initializeFrame();
		setSize(800, 600);
	}
	
	public IItemHolder<Client> createItemHolder() throws DaoException {
		return super.createItemHolder(new FilterItemList<Client>(getItemDao()));
	}
	
	public JComponent getButtonPanel() {
		JComponent mainButtonPanel = super.getButtonPanel();
		
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		JPanel additionalButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JButton addRentalButton = new JButton("Оформить прокат");
		JButton showRentalsButton = new JButton("Показать прокаты");
		additionalButtonPanel.add(addRentalButton);
		additionalButtonPanel.add(showRentalsButton);
		
		buttonPanel.add(additionalButtonPanel, BorderLayout.CENTER);
		buttonPanel.add(mainButtonPanel, BorderLayout.EAST);
		
		return buttonPanel;
	}

}
