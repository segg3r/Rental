package by.gsu.segg3r.rental.ifaces;

import javax.swing.JComponent;

public interface IItemField<T> {

	T getValue();
	String getStringValue();
	JComponent getComponent();
	
}
