import java.util.Scanner;

public class CarSalesSystem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		Inventory inventory = new Inventory();
		while (true) {
			System.out.print("\n 1 ---> Add Stock");
			System.out.print("\n 2 ---> Update Existing Stock");
			System.out.print("\n 3 ---> Delete Stock");
			System.out.print("\n 4 ---> Sale Car");
			System.out.print("\n 5 ---> Show Inventory");
			System.out.print("\n 6 ---> Sort Inventory according to Brand Name");
			System.out.print("\n 0 ---> Exit the program.");
			System.out.print("\n Enter your option: ");
			selection = sc.nextInt();
			workOnSelection(selection, inventory, sc);
		}
	}

	public static void workOnSelection(int selection, Inventory inventory, Scanner sc) {
		switch (selection) {
		case 1:
			addStock(sc, inventory);
			break;
		case 2:
			updateStock(sc, inventory);
			break;
		case 3:
			deleteStock(sc, inventory);
			break;
		case 4:
			saleCar(sc, inventory);
			break;
		case 5:
			displayInventory(inventory);
			break;
		case 6:
			sortInventory(inventory);
			break;
		case 0:
			return;
		default:
			System.out.println("Invalid option");
			break;
		}
	}

	public static void addStock(Scanner sc, Inventory inventory) {
		Stock stock = new Stock();
		stock.itemId = inventory.size();
		stock.car = addCar(sc);
		System.out.print("\n Enter Quantity: ");
		stock.quantity = sc.nextDouble();
		inventory.insert(new Node(stock));
	}

	public static void deleteStock(Scanner sc, Inventory inventory) {
		System.out.print("\n Enter Stock ID: ");
		inventory.delete(sc.nextDouble());
	}

	public static void updateStock(Scanner sc, Inventory inventory) {
		System.out.print("\n Enter Stock ID: ");
		Node node = inventory.getNodeById(sc.nextDouble());
		System.out.print("\n Enter Quantity to Add: ");
		node.data.quantity += sc.nextDouble();
		inventory.update(node);
	}

	public static Car addCar(Scanner sc) {
		Car newCar = new Car();
		System.out.print("\n Enter Brand: ");
		newCar.brand = sc.next();
		System.out.print("\n Enter Model: ");
		newCar.model = sc.next();
		System.out.print("\n Enter Year: ");
		newCar.year = sc.nextInt();
		System.out.print("\n Enter Fuel Type(Petrol/CNG/Diesel): ");
		newCar.fuelType = sc.next();
		getCarRegistrationStatus(sc, newCar);
		getCarCondition(sc, newCar);
		System.out.print("\n Enter Price: ");
		newCar.price = sc.next();
		return newCar;
	}

	public static void getCarRegistrationStatus(Scanner sc, Car car) {
		System.out.print("\n Enter Registration Status(Registered/Unregistered): ");
		car.registrationStatus = sc.next();
		if (!car.registrationStatus.toLowerCase().equals("registered")
				&& car.registrationStatus.toLowerCase().equals("unregistered")) {
			System.out.print("\n Enter Correct Value");
			getCarRegistrationStatus(sc, car);
		}
	}

	public static void getCarCondition(Scanner sc, Car car) {
		System.out.print("\n Enter Condition(New/Used): ");
		car.condition = sc.next();
		if (!car.condition.toLowerCase().equals("new") && car.condition.toLowerCase().equals("used")) {
			System.out.print("\n Enter Correct Value");
			getCarCondition(sc, car);
		} else if (car.condition.toLowerCase().equals("used")) {
			System.out.print("\n Enter Registered Location: ");
			car.registeredLocation = sc.next();
			System.out.print("\n Enter KMS Driven: ");
			car.kmsDriven = sc.next();
		}
	}

	public static void displayInventory(Inventory inventory) {
		inventory.print();
	}

	public static void sortInventory(Inventory inventory) {
		inventory.sort();
	}

	public static void saleCar(Scanner sc, Inventory inventory) {
		System.out.print("\n Enter Car Id To Sale");
		Node node = inventory.getNodeById(sc.nextDouble());
		if (node.data.quantity > 0) {
			node.data.quantity -= 1;
		} else {
			System.out.print("\n Inventory is empty.");
		}
	}

}

class Car {
	String brand = "";
	String model = "";
	int year = 0;
	String fuelType;
	String registrationStatus = "";
	String condition = "";
	String registeredLocation = "-";
	String kmsDriven = "-";
	String price = "";
}

class Stock {
	int itemId = 0;
	double quantity = 0;
	Car car = new Car();

	public void print() {
		System.out.print("\n ID: " + this.itemId);
		System.out.print("\n Quantity: " + this.quantity);
		System.out.print("\n Brand: " + this.car.brand);
		System.out.print("\n Model: " + this.car.model);
		System.out.print("\n Price: " + this.car.price);
		System.out.print("\n Year: " + this.car.year);
		System.out.print("\n Fuel Type: " + this.car.fuelType);
		System.out.print("\n Registration Status: " + this.car.registrationStatus);
		System.out.print("\n Condition: " + this.car.condition);
		System.out.print("\n Registered Location: " + this.car.registeredLocation);
		System.out.println("\n KMS Driven: " + this.car.kmsDriven);
	}
}

class Node {
	Stock data;
	Node next;

	Node(Stock d) {
		data = d;
		next = null;
	}
}

class Inventory {
	static Inventory list = new Inventory();
	Node head;

	public void insert(Node new_node) {
		new_node.next = null;
		if (list.head == null) {
			list.head = new_node;
		} else {
			Node last = list.head;
			while (last.next != null) {
				last = last.next;
			}
			last.next = new_node;
		}
		System.out.print("\n Record Added.");
	}

	public void delete(double id) {
		Node currNode = list.head, prev = null;
		if (currNode != null && currNode.data.itemId == id) {
			list.head = currNode.next;
			System.out.print("\n Stock Removed");
			return;
		}
		while (currNode != null && currNode.data.itemId == id) {
			prev = currNode;
			currNode = currNode.next;
		}
		if (currNode != null) {
			prev.next = currNode.next;
			System.out.print("\n Stock Removed");
		}
		if (currNode == null) {
			System.out.print("\n not found");
		}
	}

	public void update(Node updateNode) {
		Node node = list.head;
		while (node != null) {
			if (node.data.itemId == updateNode.data.itemId) {
				node.data = updateNode.data;
			}
			node = node.next;
		}
		System.out.println("Updated Successfully");
	}

	public void print() {
		Node currNode = list.head;
		if (currNode == null) {
			System.out.print("\n Inventory is Empty");
		} else {
			while (currNode != null) {
				currNode.data.print();
				currNode = currNode.next;
			}
		}
	}

	public void sort() {
		Node current = list.head, index = null;
		Stock temp;
		if (current == null) {
			System.out.print("Nothing to sort");
			return;
		} else {
			while (current != null) {
				index = current.next;
				while (index != null) {
					if (current.data.car.brand.compareTo(index.data.car.brand) != -1) {
						temp = current.data;
						current.data = index.data;
						index.data = temp;
					}
					index = index.next;
				}
				current = current.next;
			}
			System.out.print("sorted Successfully");
		}
	}

	public int size() {
		int length = 0;
		Node current = list.head;
		while (current != null) {
			length += 1;
			current = current.next;
		}
		return length;
	}

	public Node getNodeById(double id) {
		Node currNode = list.head;
		if (currNode != null && currNode.data.itemId == id) {
			return currNode;
		}
		while (currNode != null && currNode.data.itemId == id) {
			currNode = currNode.next;
		}
		if (currNode != null) {
			return currNode;
		}
		if (currNode == null) {
			System.out.print("\n No existing found");
		}
		return null;
	}
}