package by.gsu.segg3r.rental.exceptions;

public class DaoException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public DaoException() {
		super();
	}

	public DaoException(String arg0) {
		super(arg0);
	}

	public DaoException(Throwable arg0) {
		super(arg0);
	}

	public DaoException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public DaoException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}