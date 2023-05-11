数组存储的特点：
	数组一旦初始化，其长度就是确定不可变的
	数组中的多个元素在内存中是紧密排列的，有序可重复的
	数据一旦初始化完成，其元素类型就是确定的，不是此类型的元素，无法添加
	元素类型，既可以是基本数据类型，也可以存放引用数据类型
弊端：
	数组中存储特点单一性，对于无序不可重复的场景效果不好
	数组中的方法、属性较少
	元素的删除、插入性能较差
	
Java集合框架
	Collection:存储单个元素的数据
		-子接口：List(动态数组)：存储有序的、可重复的数据
			-ArrayList、LinkedList、Vector
		-子接口：Set：存储无序的、不可重复的数据
			-HashSet、LinkedHashSet、TreeSeet
	Map:存储成对的数据(key-value键值对)
		-HashMap、LinkedHashMap、TreeMap、Hashtable、Properties
		
Collection接口方法：
	Iterator iterator()		//迭代器
		iterator.next()
		iterator.hasNext()
	add(T value)	//向collection中添加元素，元素需要重写equals方法
	addAll(Collection other)
	
	size()
	isEmpty()
	contains(T value)	//是否包含某个元素
	containsAll(Collection other)	//是否包含这个集合中的所有元素
	clear()	for...=null
	
	boolean remove(T value)	//重写equse方法
	boolean removeAll(Collection other)	//移除交集
	boolean retainAll(Collection other)	//保留交集
	
	Object[] toArray()	//集合转数组Arrays.toString(array)	
	<T> T[]	toArray(T[] a)
	
	//数组转集合
	String[] arr=new String[]{"",""};
	Collection list= Arrays.asList(arr);
	
	重点：
		//向collection中添加元素，元素需要重写equals方法
	原因：
		因为collection中的方法，在使用时需要调用元素所在类的equals方法
例题：
	int[] arr1=new int[]{1,2,3}
	List list1=Arrays.asList(arr1)
	list1.size==1
	
List:接口方法
	
	remove(index)	//删除对象2,需要装箱Integer.valueOf(2)
	set(index,Object element)
	get(index)
	add(index,Object element)
	addAll(index,Collection c)
	
	List subList(int from,int end) //[from,end)
	int indexOf(obj)
	int lastIndexOf(obj)

	ArrayList:List的主要实现类，线程不安全
		Vector(1.0的List)：线程安全，效率低
	以上使用的是Object[]数组存储

	LinkedList:底层使用双向链表进行存储,在插入和删除数据时，效率较高;
	添加和查找数据时，效率较低。对集合中的数据进行频繁的删除和插入操作时，建议使用此类。

Set:接口存储无序的、不可重复的数据
	HashSet:
		|	底层使用的是HashMap,使用数组+单链表+红黑树的结构进行存储(遍历与添加顺序不同)
		|-LinkedHashSet:
			HashSet的子类,在HashSet的基础上增加了又添加了一组双向链表，用于记录添加元素的顺序，可以按照添加元素的顺序遍历.即可以做频繁的查询操作。
	TreeSet:
			底层使用红黑树存储，可以按照添加的元素的指定属性的大小顺序进行遍历
		添加元素时，会比较大小进行插入，所以需要同一类型(TreeSet需要使用泛型)，并且需要实现Comparable接口的compareTo方法或者Comparator的compare方法。
		添加的元素不需要重写hashCode和equals方法，比较大小和相等值判断compareTo或compare的返回值,返回值为0,则认为相同的元素，不会插入到树中。
	无序性：	
		不等于随机性，即第一次遍历的结果和多次遍历结果是一样的
		与添加的元素位置有关，不像ArrayList一样是依次紧密排列的,这里是根据元素的哈希值，计算其在数组中的存储位置，此位置是无序的。
	不可重复行：
		添加到Set中的元素是不能相同的，比较的标准，需要判断hashCode 的及equals得到的结果	
	添加到HashSet和LinkedHashSet中的要求：
		要求重写两个方法：equals()和hashCode()
		同时要求：equals和hashCode要保持一致性。
		
例题：HashSet添加两个对象，其中一个对象改变属性，remove之后，HashSet中数量？
	p1=Person("zhangsan",18);
	p2=Person("lisi",20);
	HashSet set=new HashSet();
	set.add(p1);
	set.add(p2);
	p1.age=28;
	
	set.remove(p1);
	结果：还是两个元素，remove比较的时候时按添加时形成的hash表比较的。hash表一样，才走equals比较。
	