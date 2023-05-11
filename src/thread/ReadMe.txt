10-1:相关概念的理解
程序(program):硬盘中存放的指令集合,一段静态的代码
进程(process):正在内存中执行的程序,是操作系统调度和分配cpu资源的基本单位
线程(thread):进程的进一步细化,是进程中的一条执行路径,cpu调度和执行的最小单位
.class=>ClassLoader=>运行时内存
线程共享区：方法区,堆
线程隔离区：虚拟机栈,本地方法栈,程序计数器
线程的调度：
	分时调度：所有线程轮流使用cpu的使用权,并且平均分配cpu时间
	抢占式调度：优先级高的线程以较大的概率优先使用cpu,优先级相同随机选择,java使用的是抢占式调度
多线程的优点：
	提高应用程序的响应，对图形化界面更有意义
	提高cpu的利用率
	改善程序结构，是代码封装利于理解和修改
多核的性能效率是单核的倍数吗？
	理论上是，实际上不是，性能瓶颈在内存，寄存器
并行和并发：
	并行：多个事件在同一时刻发生(多核cpu)
	并发：多个事件在同一个时间段内发生，但同一个时间点只有一个事件;
		原因是，单个cpu在快速轮换交替执行，使得宏观上具有多个进程同时执行的效果.

10-2:多线程创建方式一
	继承Thread,重写run方法;
	例题：创建一个线程，遍历100以内的偶数
	class NewThead extends Thread{
		run(){
			Thread.currentThread().getName()
		}
	}
	
	Thread thread=new NewThead();
	thread.start();
	//线程的创建和启动是在main线程中运行的
	//不能二次运行start()，非法状态异常
	
10-3:多线程创建方式二
	1、创建一个实现Runnable 接口的类
	2、实现接口中的run方法
	3、创建实现类的对象
	4、将此对象作为参数传递到Thread类的构造器中
	5、Thread类的实例调用start(),启动新线程调用任务run方法
	
两种方式的比较：
	共同点：启动线程，都需要使用Thread类中定义的start();
		创建的线程对象，都是Thread类或其子类的实例。
	不同点：一个类的继承，一个接口的实现
		Runnable的好处:实现接口的方式方便使用共享数据
例子：
	Runnable r=new Runnable(){
		run(){
			System.out.print('aaa');
		}
	};
    new	Thread(r){
    	run(){
    		System.out.print('bbb');
    	}
    };
	
	//结果为bbb,因为把Thread里的run方法复写了，所以不会调用tagget.run了

10-4:Thead类的常用方法
	4个构造方法:
	public Thread();
	public Thread(String name);
	public Thread(Runnable target);
	public Thread(Runnable target,String name);
	
	start();//启动线程，调用线程的run方法
	run();//将线程要执行的操作，声明在run方法中
	currentThead();//获取当前代码运行的线程
	getName();//获取线程名
	setName();//设置线程名
	
	static sleep(long millis);//休眠指定时间
	static yield();//主动释放cpu执行权
	join();		//线程a中,线程b调用join,意味着线程a阻塞,线程b优先执行,线程b执行完毕,a结束阻塞,继续运行
	isAlive();	//是否存活
	
	过时方法:
	stop();
	suspend();
	resume();
	
	线程的优先级:[1,10]
	getPriority();
	setPriority(int num);
	Max=10
	Min=1
	Norm=5
	
线程的生命周期(1.5之前)：
	新建			就绪			运行						死亡
	new		start()	=>	获取cpu执行权		=>									
					<=	失去cpu执行权	1.run执行结束
					yield()			2.出现了未处理的e
									3.stop()			
				|<=阻塞(临时) <=|
			sleep()时间到;  	sleep()						
			join对应线程结束; 	join()	
				获得同步锁	; 	失去同步锁
	wait到时,notify,notifyAll; wait()
				
	Thread enum State{
		new,runnable,blocked,waiting,timed_waiting,terminated
	}

10-5:线程安全问题及解决
	多个线程访问同一资源，若只有读操作，那么就是安全的，如果有写操作，就容易出现安全问题。

案例：卖票问题？
	Ticket t=Ticket(ticket=100){
		this.ticket=ticket;
		run(){
			while(true){
				if(ticket>0){
					System.out.print(Thread.currentThread.getName()+",票号:"+ticket);
					ticket--;
				}else{
					break;
				}
			}
		}
	};
	Thread t1;t1.start(t);
	Thread t2;t2.start(t);
	Thread t3;t3.start(t);

解决方法：
1.同步代码块
	synchronized(同步监视器(锁)){
		...//需要操作(写入)共享数据的代码
	}
	>被synchronized包裹后，就使得一个线程在操作这些代码时，其他线程必须等待
	>同步监视器，俗称锁，哪个线程获取了锁，哪个线程就能执行需要被同步的代码
	>同步监视器，可以使用任何类的对象，但必须多个线程共同使用一个锁。

2.同步方法
	非静态的同步方法，使用的监视器是this
	静态的同步方法，使用的监视器是obj.class

单例模式中的懒汉式的线程安全问题？
	1.私有化构造器
	2.私有内部静态对象 volatile
	3.静态方法返回对象
	public static Bank getInstance(){
		if(instance==null){
			synchronized(Bank.class){
				if(instance==null){
					instance=new Bank();
				}
			}
		}
		return instance;
	}
	
死锁：
	死锁是由于两个或以上的线程互相持有对方需要的资源，导致这些线程处于等待状态，无法执行
死锁条件：
	互斥			//资源加锁后，只能被持有锁的线程访问，不能被其他线程访问
	占用并等待		//占有一个加锁资源后，并请求另外的加锁资源
	不可抢夺		//一个线程在释放资源之前，其他的线程无法剥夺占用
	循环等待		//多个线程持锁访问链形成环
死锁原因：
	1.竞争不可抢占性资源
	2.请求和释放资源的顺序不当
解决办法：
	加锁顺序（线程按照统一约定的顺序持有锁）
	加锁时限（线程尝试获取锁的时候加上一定的时限，超过时限则放弃对该锁的请求，并释放自己占有的锁）

接口Lock（jdk1.5）
	ReentrantLock(可重入锁)
	
	static final ReentrantLock lock=new ReentrantLock();
	try{
		lock.lock();
	}finally{
		lock.unlock();
	}

10-7:线程的通信
	wait:线程一旦进入此方法，就进入等待状态同时，释放同步监视器的调用
	notify(随机唤醒)：一旦调用此方法，会唤醒优先级最高的线程，相同则随机
	notifyAll：唤醒所有被wait的线程
	
	重点：
		此三个方法,必须带synchronized代码块或方法中使用。
		此三个方法的调用者,必须是同步监视器(锁)
		此三个方法定义在object中
		Lock需配合Condition实现线程间的通信
		
案例：两个线程交替打印1-100的数

	sleep与wait的区别？
	相同点：一旦执行，当前线程都会进入阻塞状态
	不同：
		定义的位置不同：wait定义在Object类中
					sleep定义在Thread中，静态的
		使用场景不同：	wait只能使用在同步代码块或者方法中
					sleep可以在任意场景使用
		使用在同步中：	wait会释放掉同步监视器
					sleep不会释放同步监视器
		结束阻塞的方法：wait到达指定时间自动结束阻塞，或者被notify唤醒
					sleep到达时间自动结束阻塞

案例：生产者&消费者
