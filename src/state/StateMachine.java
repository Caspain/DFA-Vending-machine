package state;

public final class StateMachine {
	private static boolean midFindforN = true;
	private static String state = "", current_state = "";

	public static boolean isMidFindforN() {
		return midFindforN;
	}

	public static void setMidFindforN(boolean midFindforN) {
		StateMachine.midFindforN = midFindforN;
	}

	

	public StateMachine() {
		setCurrent_state("q0");

	}

	public static String getCurrent_state() {
		return current_state;
	}

	public static void setCurrent_state(String current_state) {
		StateMachine.current_state = current_state;
	}

	public static String getState() {
		return state;
	}

	public static void setState(String state) {
		StateMachine.state = state;
	}

	public static void determine(String state) {

		setState(state);
		//////////////////////////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q0")) {
			if (getState().equals("a") || getState().equals("b") || getState().equals("y")) {

				System.err.println("- > q1(R)");
				setCurrent_state("q1");
				return;
			}
		}
		///////////////////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q1")) {
			if (getState().equals("a") || getState().equals("b") || getState().equals("y")) {

				System.err.println("- > q1(loop)");
				setCurrent_state("q1");
				return;
			}
		}
		//////////////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q1")) {
			if (getState().equals("N") || getState().equals("S") || getState().equals("K") || getState().equals("F")) {
				setCurrent_state("q10");
				System.err.println("- > q10(R)");
				return;
			} 
		}
		////////////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q10")) {
			if (getState().equals("N") || getState().equals("S") || getState().equals("K") || getState().equals("F")) {
				System.err.println("- > q10(loop)");
				setCurrent_state("q10");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		/////////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q1")) {

			if (getState().equals("N")) {
				setCurrent_state("q10");
				System.err.println("- > q2(10)");
				setMidFindforN(true);// state that the first N at q1
				return;
			}
		}
		//////////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q0")) {

			if (getState().equals("N")) {
				setCurrent_state("q2");
				System.err.println("- > q2(R)");
				return;
			}
		}
		///////////////////////////////////////////////////////
		if (getCurrent_state().equals("q2")) {
			if (getState().equals("K")) {
				setCurrent_state("q3");
				System.err.println("- > q3(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		//////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q3")) {
			if (getState().equals("S")) {
				setCurrent_state("q4");
				System.err.println("- > q4(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		///////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q4")) {
			if (getState().equals("F")) {
				setCurrent_state("q5");
				System.err.println("- > q5(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		/////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q5")) {
			if (getState().equals("F")) {
				setCurrent_state("q6");
				System.err.println("- > q6(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		/////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q6")) {
			if (getState().equals("S")) {
				setCurrent_state("q7");
				System.err.println("- > q7(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		///////////////////////////////////////////////////////
		if (getCurrent_state().equals("q7")) {
			if (getState().equals("K")) {
				setCurrent_state("q8");
				System.err.println("- > q8(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}
		////////////////////////////////////////////////////////
		if(getCurrent_state().equals("q1")){
			if(!(getState().equals("N") || getState().equals("S") || getState().equals("K") || getState().equals("F") ||getState().equals("a") || getState().equals("b") || getState().equals("y")) ){
				setMidFindforN(false);
				reject_state();
			}
		}
		////////////////////////////////////////////////////////
		if (getCurrent_state().equals("q8")) {
			if (getState().equals("S")) {
				setCurrent_state("q9");
				System.err.println("- > q9(R)");
				return;
			} else {
				setMidFindforN(false);
				reject_state();
			}
		}

	}

	public static void start_state() {
		System.err.println("start state => q0");
	}

	public static void final_state() {
		System.err.println("final state => q11");
	}

	public static void reject_state() {
		System.err.println("reject state");
		return;
	}
}
