package by.gsu.segg3r.rental.ui;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class JSelectablePanel extends JPanel implements MouseListener {

	private static final long serialVersionUID = 1L;

	private List<JSelectablePanel> panelList;
	private boolean selected;

	public JSelectablePanel() {
		super();

		addMouseListener(this);
	}

	public void setPanelList(List<JSelectablePanel> panelList) {
		this.panelList = panelList;
	}

	public boolean isSelected() {
		return selected;
	}

	private void select() {
		this.selected = true;
		setBorder(BorderFactory.createLineBorder(Color.red, 1));

		for (JSelectablePanel panel : panelList) {
			if (panel != this) {
				panel.selected = false;
				panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			}

		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		select();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
