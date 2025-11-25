import java.util.ArrayList;
import java.util.Scanner;

public class StudentInformationSystem {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int selection = 0;
		LinkedList students = new LinkedList();
		while (true) {
			System.out.print("\n 1 ---> Add Student");
			System.out.print("\n 2 ---> Update Student Marks");
			System.out.print("\n 3 ---> Search Student");
			System.out.print("\n 4 ---> Print Student Marksheet");
			System.out.print("\n 5 ---> Print Students List");
			System.out.print("\n 6 ---> Sort Students List");
			System.out.print("\n 0 ---> Exit the program.");
			System.out.print("\n Enter your option: ");
			selection = sc.nextInt();
			workOnSelection(selection, students, sc);
		}
	}

	public static void workOnSelection(int selection, LinkedList students, Scanner sc) {
		switch (selection) {
		case 1:
			addStudent(sc, students);
			break;
		case 2:
			updateStudent(sc, students);
			break;
		case 3:
			searchList(sc, students);
			break;
		case 4:
			printMarksheet(sc, students);
			break;
		case 5:
			printList(students);
			break;
		case 6:
			sortList(students);
			break;
		case 0:
			return;
		default:
			System.out.println("Invalid option");
			break;
		}
	}

	public static void addStudent(Scanner sc, LinkedList list) {
		Student student = new Student();
		System.out.print("\n Enter First Name: ");
		student.firstName = sc.next();
		System.out.print("\n Enter Last Name: ");
		student.lastName = sc.next();
		System.out.print("\n Enter Roll Number: ");
		student.rollNum = sc.next();
		System.out.print("\n Enter Date of Birth: ");
		student.dob = sc.next();
                System.out.print("\n Enter Batch: ");
		student.batch = sc.next();
		System.out.print("\n Enter Semester: ");
		student.sems = sc.next();
		System.out.print("\n Enter Section: ");
		student.section = sc.next();
		System.out.print("\n Do you want to add Subject Marks (y/n)");
		if (sc.next().toLowerCase().charAt(0) == 'y') {
			addStudentMarks(sc, student);
		}
		list.insert(new _Node(student));
	}

	public static void addStudentMarks(Scanner sc, Student student) {
		student.subjects.forEach((subject) -> {
			subject = addMarks(sc, subject);
		});
	}

	public static Subject addMarks(Scanner sc, Subject subject) {
		if (subject.obtainedMarks > 0) {
			System.out.print("\n Enter Marks of " + subject.name + " out of " + subject.totalMarks + "("
					+ subject.obtainedMarks + "): ");
		} else {
			System.out.print("\n Enter Marks of " + subject.name + " out of " + subject.totalMarks + ": ");
		}
		subject.obtainedMarks = sc.nextDouble();
		if (subject.obtainedMarks > 50) {
			subject.obtainedMarks = 0;
			System.out.print("\n Value range is " + subject.totalMarks);
			subject = addMarks(sc, subject);
		}
		return subject;
	}

	public static void updateStudent(Scanner sc, LinkedList list) {
		System.out.print("\n Enter Roll Num of Student to edit: ");
		_Node node = list.get_NodeByRollNum(sc.next());
		if (node != null) {
			node.data.subjects.forEach((subject) -> {
				subject = addMarks(sc, subject);
			});
		}
	}

	public static void searchList(Scanner sc, LinkedList list) {
		System.out.print("\n Enter Key to Search: ");
		ArrayList<Student> arr = list.search(sc.next());
		if (arr != null) {
			arr.forEach((stud) ->{
				stud.print();
			});
		}
	}

	public static void printMarksheet(Scanner sc, LinkedList list) {
		System.out.print("\n Enter Roll Num of Student: ");
		_Node student = list.get_NodeByRollNum(sc.next());
		if (student != null) {
			student.data.print();
			if (student.data.subjects.size() > 0) {
				System.out.print("\n Name \t\t Total Marks \t Obtained Marks");
				double grandTotal = 0;
				double total = 0;
				for (Subject subject : student.data.subjects) {
					grandTotal += subject.totalMarks;
					total += subject.obtainedMarks;
					System.out.print("\n " + subject.name + " " + subject.totalMarks + " " + subject.obtainedMarks);
				}
				double percentage = (total / grandTotal) * 100;
				System.out.print("\n Total Marks: " + grandTotal);
				System.out.print("\n Obtained Marks: " + total);
				System.out.print("\n Percentage: " + percentage + "%");
				System.out.print("\n Grade: " + getGrade(percentage));
			} else {
				System.out.print("\n No Marks Found!");
			}
		}
	}
	
	public static String getGrade(double percentage) {
		if(percentage > 90) {
			return "A+";
		}else if(percentage > 80) {
			return "A";
		}else if(percentage > 70) {
			return "B";
		}else if(percentage > 60) {
			return "C";
		}else if(percentage > 50) {
			return "B";
		}else if(percentage <= 50) {
			return "F";
		}
		return null;
	}

	public static void printList(LinkedList list) {
		list.print(list);
	}

	public static void sortList(LinkedList list) {
		list.sort();
	}

}

class Subject {
	String name;
	double totalMarks;
	double obtainedMarks = 0;

	Subject(String name) {
		this.name = name;
		totalMarks = 50;
	}
}

class _Node {
	Student data;
	_Node next;

	_Node(Student d) {
		data = d;
		next = null;
	}
}

class Student {
	String firstName;
	String lastName;
        String sems;
	String rollNum;
	String dob;
	String batch;
	String section;
	ArrayList<Subject> subjects = new ArrayList<Subject>();

	Student() {
		subjects.add(new Subject("Data Structures & Algorithm"));
		subjects.add(new Subject("Complex Variables & Integral Transform"));
		subjects.add(new Subject("Circuit Analysis"));
		subjects.add(new Subject("Logic Design & Switching Circuit"));
		subjects.add(new Subject("Communication Skills"));
	}

	public void print() {
		System.out.print("\n\n Name: " + this.firstName + " " + this.lastName);
		System.out.print("\n rollNum: " + this.rollNum);
		System.out.print("\n Date of Birth: " + this.dob);
		System.out.print("\n Level: " + this.batch);
		System.out.println("\n Section: " + this.section);
	}
}

class LinkedList {
	_Node head;

	public void insert(_Node new_node) {
		new_node.next = null;
		if (this.head == null) {
			this.head = new_node;
		} else {
			_Node last = this.head;
			while (last.next != null) {
				last = last.next;
			}
			last.next = new_node;
		}
		System.out.print("\n Record Added.");
	}

	public void delete(String rollNum) {
		_Node curr_Node = this.head, prev = null;
		if (curr_Node != null && curr_Node.data.rollNum.equals(rollNum)) {
			this.head = curr_Node.next;
			System.out.println(rollNum + " found and deleted");
			return;
		}
		while (curr_Node != null && curr_Node.data.rollNum.equals(rollNum)) {
			prev = curr_Node;
			curr_Node = curr_Node.next;
		}
		if (curr_Node != null) {
			prev.next = curr_Node.next;
			System.out.println(rollNum + " found and deleted");
		}
		if (curr_Node == null) {
			System.out.println(rollNum + " not found");
		}
	}

	public void update(String rollNum, _Node updated_Node) {
		boolean found = false;
		_Node node = this.head;
		while (node != null) {
			if (node.data.rollNum.equals(rollNum)) {
				node.data = updated_Node.data;
				found = true;
			}
			node = node.next;
		}
		if (!found) {
			System.out.println("No person found with given first name");
		} else {
			System.out.println("Updated Successfully");
		}
	}

	public void print(LinkedList arr) {
		_Node curr_Node = arr.head;
		while (curr_Node != null) {
			curr_Node.data.print();
			curr_Node = curr_Node.next;
		}
		System.out.println();
	}

	public ArrayList<Student> search(String key) {
		_Node curr_Node = this.head, prev = null;
		ArrayList<Student> arr = new ArrayList<Student>();
		if (curr_Node != null && curr_Node == this.head && (curr_Node.data.firstName.contains(key)
				|| curr_Node.data.lastName.contains(key) || curr_Node.data.rollNum.contains(key))) {
			arr.add(curr_Node.data);
			return arr;
		}
		while (curr_Node != null && (curr_Node.data.firstName.contains(key) || curr_Node.data.lastName.contains(key)
				|| curr_Node.data.rollNum.contains(key))) {
			arr.add(curr_Node.data);
			prev = curr_Node;
			curr_Node = curr_Node.next;
		}
		if (!arr.isEmpty()) {
			return arr;
		} else {
			System.out.print("\n " + key + " not found");
			return null;
		}
	}

	public void sort() {
		_Node current = this.head, index = null;
		Student temp;
		if (current == null) {
			System.out.print("Nothing to sort");
			return;
		} else {
			while (current != null) {
				index = current.next;
				while (index != null) {
					if (current.data.firstName.compareTo(index.data.firstName) > 0) {
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

	public _Node get_NodeByRollNum(String rollNum) {
		_Node curr_Node = this.head;
		if (curr_Node != null && curr_Node.data.rollNum.equals(rollNum)) {
			return curr_Node;
		}
		while (curr_Node != null && curr_Node.data.rollNum.equals(rollNum)) {
			curr_Node = curr_Node.next;
		}
		if (curr_Node != null) {
			return curr_Node;
		}
		if (curr_Node == null) {
			System.out.print("\n No existing found");
		}
		return null;
	}

	public int size() {
		int length = 0;
		_Node current = this.head;
		while (current != null) {
			length += 1;
			current = current.next;
		}
		return length;
	}

}