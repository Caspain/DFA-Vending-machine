package machine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.table.DefaultTableModel;

import files.TimeStamp;
import main.SetUp;
import state.StateMachine;

public final class Computation {
	private TimeStamp stamp;
	private SetUp setUpMachine = null;
	private Pattern pattern;
	private Matcher matcher;
	private String accepted;
	private String item, money;
	private boolean rejected = false;
	private int money_change, funds;
    private StateMachine machine;
    
	public StateMachine getMachine() {
		return machine;
	}

	public void setMachine(StateMachine machine) {
		this.machine = machine;
	
	}

	private ArrayList<Integer> temp_funds;

	public Pattern getPattern() {
		return pattern;
	}

	public TimeStamp getStamp() {
		return stamp;
	}

	public void setStamp(TimeStamp stamp) {
		this.stamp = stamp;
	}

	public void setPattern(Pattern pattern) {
		this.pattern = pattern;
	}

	public Matcher getMatcher() {
		return matcher;
	}

	public void setMatcher(Matcher matcher) {
		this.matcher = matcher;
	}

	public ArrayList<Integer> getTemp_funds() {
		return temp_funds;
	}

	public void setTemp_funds(ArrayList<Integer> temp_funds) {
		this.temp_funds = temp_funds;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getRESTOCK() {
		return RESTOCK;
	}

	public int getFunds() {
		return funds;
	}

	public void setFunds(int funds) {
		this.funds = funds;
	}

	public void addFunds(int add) {
		setFunds((getFunds() + add));
	}

	public void subFunds(int add) {
		setFunds((getFunds() - add));
	}

	public int getMoney_change() {
		return money_change;
	}

	public void setMoney_change(int money_change) {
		this.money_change = money_change;
	}

	private ArrayList<String> accepted_items;// contains all the current
												// accepted validated vending
												// items

	public ArrayList<String> getAccepted_items() {
		return accepted_items;
	}

	public void setAccepted_items(ArrayList<String> accepted_items) {
		this.accepted_items = accepted_items;
	}

	public boolean isRejected() {
		return rejected;
	}

	public void setRejected(boolean rejected) {
		this.rejected = rejected;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getMoney() {
		return money;
	}

	public void setMoney(String money) {
		this.money = money;
	}

	public String getAccepted() {
		return accepted;
	}

	public void setAccepted(String accepted) {
		this.accepted = accepted;
	}

	private String expression = "^(a|b|y)+([NKSF]){1,80}$";
	private final String RESTOCK = "^(NKSFFSKS)$";

	public SetUp getSetUpMachine() {
		return setUpMachine;
	}

	public void setSetUpMachine(SetUp setUpMachine) {
		this.setUpMachine = setUpMachine;
	}

	public Computation(int stock) {
		setSetUpMachine(new SetUp(stock));
		setAccepted_items(new ArrayList<>());
		setTemp_funds(new ArrayList<>());
		setStamp(new TimeStamp());
		setMachine(new StateMachine());
	}

	/*
	 * given the accepted string expression both the items and money are
	 * extracted and stored
	 */

	public void Invalidate() {

		String[] result_money = getAccepted().split("(N|K|F|S)+");
		String[] result_item = getAccepted().split("(a|b|y)+");
		setMoney(result_money[result_money.length - 1]);
		setItem(result_item[result_item.length - 1]);

	}

	public boolean analyze() {

		int sum = sumate();
		if (!isStockEmpty()) {

			return false;
		}
		if (sum > 0) {
			for (char ch : getItem().toCharArray()) {
				if (ch == 'N') {
					if (sum >= getSetUpMachine().getVending_item().get("N")) {

						if (ItemStockCalculation("N")) {
							// enough is in stock
							sum = subtract(getSetUpMachine().getVending_item().get("N"), sum);
							getAccepted_items().add("N");
							getTemp_funds().add(getSetUpMachine().getVending_item().get("N"));
							setRejected(false);
						} else {
							System.out.println("Current Stock: " + getSetUpMachine().getNapkin_stock());
							setRejected(true);
							break;
						}

					} else {
						setRejected(true);
						break;
					}
				} else if (ch == 'F') {
					if (sum >= getSetUpMachine().getVending_item().get("F")) {
						if (ItemStockCalculation("F")) {
							sum = subtract(getSetUpMachine().getVending_item().get("F"), sum);
							getAccepted_items().add("F");
							getTemp_funds().add(getSetUpMachine().getVending_item().get("F"));
							setRejected(false);
						} else {
							System.out.println("Current Stock: " + getSetUpMachine().getFork_stock());
							setRejected(true);
							break;
						}

					} else {
						setRejected(true);
						break;
					}
				} else if (ch == 'S') {
					if (sum >= getSetUpMachine().getVending_item().get("S")) {
						if (ItemStockCalculation("S")) {
							sum = subtract(getSetUpMachine().getVending_item().get("S"), sum);
							getAccepted_items().add("S");
							getTemp_funds().add(getSetUpMachine().getVending_item().get("S"));
							setRejected(false);
						} else {
							System.out.println("Current Stock: " + getSetUpMachine().getSpoon_stock());
							setRejected(true);
							break;
						}

					} else {
						setRejected(true);
						break;
					}
				} else if (ch == 'K') {
					if (sum >= getSetUpMachine().getVending_item().get("K")) {
						if (ItemStockCalculation("K")) {
							sum = subtract(getSetUpMachine().getVending_item().get("K"), sum);
							getAccepted_items().add("K");
							getTemp_funds().add(getSetUpMachine().getVending_item().get("K"));
							setRejected(false);
						} else {
							System.out.println("Current Stock: " + getSetUpMachine().getKnife_stock());
							setRejected(true);
							break;
						}

					} else {
						setRejected(true);
						break;
					}
				}
			}
		} else {
			setRejected(true);
			System.out.println("You have no money!");
		}
		setMoney_change(sum);
		return isRejected();
	}

	private int sumate() {
		int total = 0;
		for (char ch : getMoney().toCharArray()) {
			if (ch == 'a') {
				total += 5;
			} else if (ch == 'b') {
				total += 10;
			} else if (ch == 'y') {
				total += 20;
			}
		}
		return total;
	}

	private int subtract(int amount, int sum) {
		return sum -= amount;
	}

	private void decider(boolean rejected) {
		if(getAccepted_items().size()>=1 && rejected){
			System.err.println("Only some items could be processed.");
			getAccepted_items().forEach((item)->{
				System.err.println("---> " + item);
			});
			System.err.println("StockSize: " + getSetUpMachine().getStock_size());
			addToFundsBalance();// add to funds gained..
			if (getMoney_change() > 0) {
				System.out.println("Remaining change: " + getMoney_change());
			}
			reset();// clear all settings
		}
		// boolean result = analyze();
		else if (!isStockEmpty()) {
			System.err.println("Stack is empty.");
			return;
		} else if (!rejected) {

			System.err.println("StockSize: " + getSetUpMachine().getStock_size());
			System.out.println("\nLanguage accepted by machine");
			addToFundsBalance();// add to funds gained..
			if (getMoney_change() > 0) {
				System.out.println("Remaining change: " + getMoney_change());
			}
			reset();// clear all settings

		} else if(getAccepted_items().size() <=0 && rejected ){
			System.out.println("Language was not accepted by machine");
			System.out.println("Refund: " + getMoney_change());
			reset();// clear all settings
		}
		System.err.println("\n-----------------------------\n");
	}

	private void reset() {
		getAccepted_items().clear();
		getTemp_funds().clear();
		setMoney_change(0);
		setRejected(true);
		setItem(null);
		setMoney(null);
	
	}
	private void itemStockReset(){
		getSetUpMachine().setFork_stock(20);
		getSetUpMachine().setKnife_stock(20);
		getSetUpMachine().setSpoon_stock(20);
		getSetUpMachine().setNapkin_stock(20);
		getSetUpMachine().setStock_size(80);
	}

	private boolean isStockEmpty() {
		return getSetUpMachine().getStock_size() > 0;
	}

	/*
	 * this function accepts a given vending item , and checks the stock size ,
	 * if the stock is empty return false ,else subtract one from current size
	 * and return true,also subtract one from total stock
	 */
	private boolean ItemStockCalculation(String item) {
		boolean state = false;
		if (item.equals("F")) {
			state = getSetUpMachine().getFork_stock() > 0 ? true : false;
			if (state) {
				int mainStock = getSetUpMachine().getStock_size();
				getSetUpMachine().setStock_size(mainStock - 1);

				int stock = getSetUpMachine().getFork_stock();

				getSetUpMachine().setFork_stock(stock - 1);

			}
		} else if (item.equals("N")) {
			state = getSetUpMachine().getNapkin_stock() > 0 ? true : false;
			if (state) {
				int stock = getSetUpMachine().getNapkin_stock();
				int mainStock = getSetUpMachine().getStock_size();
				getSetUpMachine().setStock_size(mainStock - 1);
				getSetUpMachine().setNapkin_stock(stock - 1);
			}
		} else if (item.equals("S")) {
			state = getSetUpMachine().getSpoon_stock() > 0 ? true : false;
			if (state) {
				int stock = getSetUpMachine().getSpoon_stock();
				int mainStock = getSetUpMachine().getStock_size();
				getSetUpMachine().setStock_size(mainStock - 1);
				getSetUpMachine().setSpoon_stock(stock - 1);
			}
		} else if (item.equals("K")) {
			state = getSetUpMachine().getKnife_stock() > 0 ? true : false;
			if (state) {
				int stock = getSetUpMachine().getKnife_stock();
				int mainStock = getSetUpMachine().getStock_size();
				getSetUpMachine().setStock_size(mainStock - 1);
				getSetUpMachine().setKnife_stock(stock - 1);
			}
		}
		return state;
	}

	// ORDER [k - s - f - n]
	/*
	 * accepts array of stock items,refill each stock accordingly.
	 */
	private void refillStock(ArrayList<Integer> stock) {
		getSetUpMachine().setFork_stock(getSetUpMachine().getFork_stock() + stock.get(2));
		getSetUpMachine().setNapkin_stock(getSetUpMachine().getNapkin_stock() + stock.get(3));
		getSetUpMachine().setSpoon_stock(getSetUpMachine().getSpoon_stock() + stock.get(1));
		getSetUpMachine().setKnife_stock(getSetUpMachine().getKnife_stock() + stock.get(0));
		System.out.format("\n[ %d: K - %d: S - %d: F - %d: N ]\n", getSetUpMachine().getKnife_stock(),
				getSetUpMachine().getSpoon_stock(), getSetUpMachine().getFork_stock(),
				getSetUpMachine().getNapkin_stock());
	}

	/*
	 * this function calculates the refill size of the stock. first, get current
	 * stock size of each item then subtract 20 from it ,yielding the remaining
	 * size
	 * 
	 */
	private String calculateRefillSize() { // ORDER [k - s - f - n]
		ArrayList<Integer> items_stock = new ArrayList<>();
		int k, s, f, n;
		k = getSetUpMachine().getKnife_stock() > 0 ? 20 - getSetUpMachine().getKnife_stock() : 0;
		s = getSetUpMachine().getSpoon_stock() > 0 ? 20 - getSetUpMachine().getSpoon_stock() : 0;
		f = getSetUpMachine().getFork_stock() > 0 ? 20 - getSetUpMachine().getFork_stock() : 0;
		n = getSetUpMachine().getNapkin_stock() > 0 ? 20 - getSetUpMachine().getNapkin_stock() : 0;
		items_stock.add(k);
		items_stock.add(s);
		items_stock.add(f);
		items_stock.add(n);

		System.out.format("\n[ %d: K  %d: S  %d: F  %d: N ]\n", k, s, f, n);
		return String.format("\n[ %d: K  %d: S  %d: F  %d: N ]\n", k, s, f, n);

	}

	/*
	 * this function creates a expression for recognizing the stock refill
	 * input,then sets the accepted input marking it as accepted
	 */
	private void create_stock_signature(ArrayList<Integer> s, String input) {

		String filter = String.format("^([K]{0,%d}[S]{0,%d}[F]{0,%d}[N]{0,%d})$", s.get(0), s.get(1), s.get(2),
				s.get(3));
		System.err.println("Result: " + filter);
		pattern = Pattern.compile(filter);
		matcher = pattern.matcher(input);
		if (matcher.matches()) {

			setAccepted(matcher.group(matcher.groupCount() - 1));
			System.out.println("Expression for refill accepted: " + getAccepted());
			setRejected(false);
		} else {
			setRejected(true);
			System.out.println("Expression rejected");
		}

	}

	/*
	 * given the accepted input string,this function loops and increment the
	 * occurrences of all the items in the vending machine
	 */
	// ORDER [k - s - f - n]
	private ArrayList<Integer> getAllStockValues() {
		ArrayList<Integer> stock = new ArrayList<>();
		int k, s, f, n;
		k = s = f = n = 0;
		for (char ch : getAccepted().toCharArray()) {
			if (ch == 'F') {
				f += 1;
			} else if (ch == 'N') {
				n += 1;
			} else if (ch == 'S') {
				s += 1;
			} else if (ch == 'K') {
				k += 1;
			}
		}
		stock.add(k);
		stock.add(s);
		stock.add(f);
		stock.add(n);
		return stock;
	}

	/*
	 * takes in all item occurrences and calculate the total for each item times
	 * the amount stored
	 */
	private int calculateInputStock_Items() {
		int total = 0;
		ArrayList<Integer> stock = getAllStockValues();
		if (!stock.isEmpty()) {
			// get for all K
			total += (stock.get(0) * getSetUpMachine().getVending_item().get("K"));
			// get for all S
			total += (stock.get(1) * getSetUpMachine().getVending_item().get("S"));
			// get for all F
			total += (stock.get(2) * getSetUpMachine().getVending_item().get("F"));
			// get for all N
			total += (stock.get(3) * getSetUpMachine().getVending_item().get("N"));
		}
		return total;
	}

	/*
	 * refill stock based on input and matched it against the available funds,
	 * returning true if
	 */
	private boolean DeriveStockWith_FundIsAvailable() {
		if (getFunds() > 0) {
			if (calculateInputStock_Items() > getFunds()) {
				System.err.println("Insufficient Funds, Cannot restock");
				return false;
			} else {
				return true;
				// available funds can accommodate input stock requirement
			}
		} else {
			System.err.println("Insufficient Funds");
		}
		return false;
	}

	/*
	 * this function just all the refill associated functions in sequence
	 */
	public void refill_compute() throws IOException {
		pattern = Pattern.compile(getRESTOCK());
		matcher = pattern.matcher(getSetUpMachine().getInput());

		if (matcher.matches()) {
			System.out.println("expression in language");

			if (getFunds() <= 0) {
				System.err.println("Insufficient Funds, cannot restock!");

			} else {
				String data = calculateRefillSize();
				getStamp().writer(data);
				reset();
				setFunds(0);
			}
		} else {
			System.out.println("expression not in language");
		}

	}

	public void compute() throws IOException {

		boolean rejected = true;
		pattern = Pattern.compile(expression);
		matcher = pattern.matcher(getSetUpMachine().getInput());

		if (matcher.matches()) {
			System.out.println("expression in language");

			setAccepted(matcher.group(0));
			Invalidate();// validate input

			rejected = analyze();
		} else {
			System.out.println("expression not in language");
		}
		// call decider
		decider(rejected);
	}

	public void execute() throws IOException {
		boolean restock = true, normal = true, rejected = true;
		normal = validateStringForNormal();
		restock = validateStringForRestock();
		if (normal) {
			System.out.println("\nexpression in language\n");
			
			
			Invalidate();// validate input
			rejected = analyze();
			decider(rejected);
		} else if (restock) {
			System.out.println("\nexpression in language\n");
			if (getFunds() <= 0) {
				System.err.println("Insufficient Funds, cannot restock!");
			}
			String data = calculateRefillSize();
			getStamp().writer(data);
			reset();
			System.out.println("Funds: " + getFunds());
			setFunds(0);
			itemStockReset();
		} else {
			System.out.println("expression not in language");
		}
	}

	private void addToFundsBalance() {
		if (!getTemp_funds().isEmpty()) {
			getTemp_funds().forEach(new Consumer<Integer>() {

				@Override
				public void accept(Integer item) {
					addFunds(item);

				}
			});
			System.out.println("Funds : " + getFunds());
		}
	}

	private boolean validateStringForRestock() {
		pattern = Pattern.compile(getRESTOCK());
		matcher = pattern.matcher(getSetUpMachine().getInput());
		
		return matcher.matches() == true ? true : false;
	}

	private boolean validateStringForNormal() {

		pattern = Pattern.compile(getExpression());
		matcher = pattern.matcher(getSetUpMachine().getInput());
		if(matcher.matches()){
			setAccepted(matcher.group(0));
		}
		return matcher.matches() == true ? true : false;
	}
	public void enumerate(){
		StateMachine.start_state();
		char[] ans =getSetUpMachine().getInput().toCharArray(); //don't trim
		for (int i = 0; i < ans.length; i++) {
			StateMachine.determine(Character.toString(ans[i]));
			if(!StateMachine.isMidFindforN())break; //some incorrect symbol was read
		}
		if(StateMachine.isMidFindforN()){//input was accepted by machine
			StateMachine.final_state();
		}
		StateMachine.setMidFindforN(true);//reset
		StateMachine.setCurrent_state("q0");
	}
	public String getVendingNumber(){
		return String.format("\nTotal:%d <|> S:%d <|> N:%d <|> K:%d <|> F:%d\n", getSetUpMachine().getStock_size(),getSetUpMachine().getSpoon_stock(),getSetUpMachine().getNapkin_stock(),getSetUpMachine().getKnife_stock(),getSetUpMachine().getFork_stock());
	}
}
