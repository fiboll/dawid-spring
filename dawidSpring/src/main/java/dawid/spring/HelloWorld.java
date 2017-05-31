package dawid.spring;

public class HelloWorld {

    private String name;

	public void printHello() {
		System.out.println("Hello ! " + name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
}