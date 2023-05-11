package collection;

import java.util.HashSet;
import java.util.Objects;

public class HashSetTest {
	public static void main(String[] args) {
		HashSet set = new HashSet();
		Person p1 = new Person("zhangsan", 18);
		Person p2 = new Person("lisi", 20);
		set.add(p1);
		set.add(p2);
		System.out.println(set);
		p1.setAge(30);
		set.remove(p1);
		System.out.println(set);
	}

	static class Person {
		private String name;
		private int age;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}

		@Override
		public int hashCode() {
			return Objects.hash(age, name);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Person other = (Person) obj;
			return age == other.age && Objects.equals(name, other.name);
		}

	}
}
