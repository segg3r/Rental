package by.gsu.segg3r.rental.ifaces;

import java.awt.Component;

import javax.swing.JPanel;

public interface IItemWindow {

	void initializeFrame();
	JPanel initializeContentPane();
	Component getMainPanel();
	Component getButtonPanel();
	Component getUpperPanel();
	
}
