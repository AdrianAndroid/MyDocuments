@[TOC]
# JAVA知识点汇总
## JVM


### JVM工作流程
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210107103450300.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)
### 运行时数据区（Runtime Data Area）
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210107103517620.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)

| 区域         | 说明                                                         |
| ------------ | ------------------------------------------------------------ |
| 程序计数器   | 每条线程都需要有一个程序计数器，计数器记录的是正在执行的指令地址，如果正在执行的是Natvie方法，这个计数器值为空（Undefined） |
| java虚拟机栈 | Java方法执行的内存模型，每个方法执行的时候，都会创建一个栈帧用于保存局部变量表，操作数栈，动态链接，方法出口信息等。一个方法调用的过程就是一个栈帧从VM栈入栈到出栈的过程 |
| 本地方法栈   | 与VM栈发挥的作用非常相似，VM栈执行Java方法（字节码）服务，Native方法栈执行的恶事Native方法服务 |
| Java堆       | 此内存区域唯一的目的就是存放对象实例，几乎所有的对象都在这分配内存 |
| 方法区       | 方法区是各个内存所共享的内存空间，方法区中主要存放被JVM加载的类信息、常量、静态变量、即使编译后的代码等数据 |



### 方法指令
| 指令             | 说明                                                         |
| ---------------- | ------------------------------------------------------------ |
| invoke interface | 可以调用接口方法                                             |
| invoke virtual   | 指令用于调用对象的实例方法                                   |
| invoke static    | 用以调用类/静态方法                                          |
| invoke special   | 用于调用一些需要特殊处理的实例方法，包括实例初始化方法、似有方法和父类方法 |



### 类加载器
| 类加载器             | 说明                                                         |
| -------------------- | ------------------------------------------------------------ |
| BootstrapClassLoader | Bootstrap类加载器负责加载rt.jar中的JDK类文件，它是所有类加载器的父类加载器，如果你调用String.class.getClassLoader(), 会返回null，任何基于此的代码会抛出NullPointerException异常。Bootstrap加载器被称为初始类加载器 |
| ExtClassLoader       | 而Extension将加载类的请求先委托给它的父加载器，也就是Bootstrap,如果没有成功加载的话，再从jre/lib/ext目录下或者java.ext.dirs系统属性定义的目录下加载类。Extension加载器由sum.misc.Launcher$ExtClassLoader实现 |
| AppClassLoader       | 第三种默认的加载器就是System类加载器（又叫做Application类加载器）了。它负责从classpath环境变量中加载某些应用相关的类，classpath环境变量通常由-classpath或-cp命令选项来定义，或者是JAR中的Manifest的classpath属性。Application类加载器是Extension类加载器的子加载器 |



| 工作原理   | 说明                                                         |
| ---------- | ------------------------------------------------------------ |
| 委托机制   | 加载任务委托交给父类加载器，如果不行就向下传递委托任务，由其子类加载器加载，保证java核心库的安全性 |
| 可见性机制 | 子类加载器可以看到父类加载器的类，而反之则不行               |
| 单一性机制 | 父加载器加载过的类不能被子加载器加载第二次                   |


### 垃圾回收gc
#### 对象存活判断
* 引用计数
		每个对象有一个引用计数属性，新增一个引用时计数加1，引用释放时技术减1，计数为0时可以回收。此方法虽然简单，但无法解决对象相互循环引用的问题。
* 可达性分析
		从GC Roots开始向下搜索，搜索所走过的路径称为引用链。当一个对象到GC Roots没有任何引用链时，则证明此对象是不可用的。不科大对象。
		在java语言中，GC Roots包括：
	* 虚拟机栈中引用的对象
	* 方法区中类静态属性实体引用的对象
	* 方法区中常量引用的对象
	* 本地方法栈中JNI引用的对象
#### 垃圾收集算法
* 标记-清除算法
“标记-清除”(Mark-Sweep)算法，如它的名字一样。算法分为“标记”和“清除”两个阶段：首先标记出所有需要回收的对象，在标记完成后回收掉所有被标记的对象。
	之所以说它是最基础的算法，是因为后续的收集算法都是基于这种思路并对其缺点进行改进而得到的。
	它的主要缺点有两个：一个是效率问题，标记和清除过程的效率都不高；另外一个时空间问题，标记清除之后会产生大量不连续的内存碎片，空间碎片太多可能会导致当程序在以后的运行过程中需要分配较大对象时无法找到内存而不得不提前触发另一次垃圾收集动作。

* 复制算法
“复制”（Copying）的收集算法，它将可用内存按容量划分为大小相等的两块，每次只使用其中的一块。当这一块的内存用完了，就将还存活着的对象复制到另外一块儿上面，然后再已使用过的内存空间一次清理掉。
这样使得每次都是对其中的一块进行内存回收，内存分配时也就不用考虑内存碎片等复杂情况，只要移动堆顶指针，按顺序分配内存即可，实现简单，运行高效。只是这种算法的代价是将内存缩小为原来的一半，持续复制长生存期的对象则导致效率低。


* 标记整理算法
	复制收集算法在对象存活率较高时就要执行较多的复制操作，效率将会变低。更关键的是，如果不想浪费50%的空间，就需要额外的空间进行分配担保，以应对被使用的内存中所有对象都100%存活的极端情况，所以在老年代一般不能直接选用这种算法。
	根据老年代的特点，有人提出了另外一种“标记-整理”（Mark-Compact）算法，标记过程仍然与“标记-清除”算法一样，但后续步骤不是直接对可回收对象进行清理，而是所有存活的对象都向一端移动，然后直接清理掉端边界以外的内存。


* 分代收集算法
	GC分代的基本基本假设：绝大部份对象的生命周期非常短暂，存活时间短。
	“分代收集”（Generational Collection）算法，把Java堆分为新生代和老年代，这样就可以根据各个年代的特点采用最适当的收集算法，只需要付出少量存活对象的复制成本就可以完成收集。而老年代中因为对象存活率高、没有额外空间对它进行分配担保，就必须使用“标记-清理”或“标记-整理”算法来进行回收。


#### 垃圾收集器
* CMS收集器
CMS（Concurrent Mark Sweep）收集器是一种以获取最短回收停顿时间为目标的收集器。目前很大一部分的Java应用都集中在互联网站或B/S系统的服务端上，这类应用尤其重视服务的相应速度，希望系统停顿时间最短，以给用户带来更好的体验。

从名字（包含“Mark Sweep”）上就可以看出CMS收集器是基于“标记-清除”算法实现的，它的运作过程相对于前面几种收集器来说要更复杂一些，整个过程分为4个步骤，包括：
	* 初始标记（CMS initial mark）
	* 并发标记（CMS concurrent mark）
	* 重新标记（CMS remark）
	* 并发清除（CMS concurrent sweep）
其中初始标记、重新标记这两个步骤仍然需要“Stop The World”。初始标记仅仅只是标记一下GC Roots能直接关联到的对象，速度很快，并发标记阶段就是进行GC Roots Tracing的过程，而重新标记阶段则是为了修正并发标记期间，因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录，这个阶段的停顿时间一般会比初始标记阶段稍长一些，但远比并发标记的时间短。

由于整个过程中耗时最长的并发标记和并发清除过程中，收集器线程都可以与用户线程一起工作，所以总体上来说，CMS收集器的内存回收过程是与用户线程一起并发地执行。老年代收集器（新生代使用ParNew）


* G1收集器
与CMS收集器相比G1收集器有以下特点：
1、空间整合，G1收集器采用标记整理算法，不会产生内存空间碎片。分配大对象时不会因为无法找到连续空间而提前触发下一次GC。
2、可预测停顿，这是G1的另一大优势，降低停顿时间时G1和CMS的共同关注点，但G1除了追求低停顿外，还能建立可预测的停顿时间模型，能让使用者明确指定在一个长度为N毫秒的时间片段内，小号在垃圾收集上的时间不得超过N毫秒，者几乎已经是实时Java（RTSJ）的垃圾收集器的特征了。
使用G1收集器时，Java堆的内存布局与其他收集器有很大区别，它将整个Java堆划分为多个大小相等的独立区域（Region），虽然还保留有新生代和老年代的概念，但新生代和老年代不再是物理隔阂了，它们都是一部分（可以不连续）Region的集合。
G1的新生代收集器跟ParNew类似，当新生代占用达一定比例的时候，开始触发收集。和CMS类似，G1收集器收集老年代对象会有暂时停顿。



#### 内存模型与回收策略
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210107135541122.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)
Java堆（Java Heap）是JVM所管理的内存中最大的一块，堆又是垃圾收集器管理的主要区域，Java堆分为2个区域-年轻代与老年代，其中年轻代又分Eden区和Survivor区，其中Survivor区又分为From和To 2个区。

* Eden区
大多数情况下，对象会在新生代Eden区中进行分配，当Eden区没有足够看空间进行分配时，虚拟机会发起一次Minor GC， Minor GC相比Major GC更频繁，回收速度也更快。通过Minor GC之后，Eden会被清空，Eden区中绝大部分对象会被回收，而那些无需回收的存活对象，将会进到Survivor的From区（若From区不够，则直接进入Old区）。

* Survivor区
Survivor区相当于Eden区和Old区的一个缓冲，类似于我们交通中的黄灯。Survivor又分为2个区，一个是From区，一个是To区。每次执行Minor GC，会讲Eden区和From存活的对象放到Survivor的To区（如果To区不够，则直接进入Old区）。Survivor的存在意义就是减少被送到老年代的对象，进而减少Major GC的方式。Survivor的与筛选保证，只有经历16次Minor GC还能在新生代存活的对象，才会被送到老年代。

* Old区
老年代占据着2/3的堆内存空间，只有在Major GC的时候才会进行清理，每次GC都会触发“Stop-The-World”，STW的时间也越长，所以内存也不仅仅是越大就越好。由于复制算法在对象存活率较高的老年代会进行很多次的复制操作，效率很低，所以老年代这里采用的是“标记-整理”算法

## static
* static关键字修饰的方法或者变量不需要依赖于对象来进行访问，只要类被加载了，就可以通过类名区进行访问。
* 静态变量被所有的对象所共享，在内存中只有一个副本，它当且仅当在类初次加载时候会被初始化。
* 能通过this访问静态成员变量吗？所有的静态方法和静态变量都可以通过对象访问（只要访问权限足够）
* static 时不允许用来修饰局部变量。

## final
* 可以声明成员变量、方法、类以及本地变量
* final成员变量必须在声明的时候初始化或者在构造器中初始化，否则就会报编译错误
* final变量是只读的
* final申明的方法不可以被子类的方法重写
* final类通常功能是完整的，不能被继承的
* final变量可以安全的在多线程环境下进行共享，而不需要额外的同步开销
* final关键字提高了性能，JVM和Java应用都会缓存final变量，会对方法、变量以及类进行优化
* 方法的内部类方法方法中的局部变量，但必须用final修饰才能访问
## String、StringBuffer、StringBuilder
* String是final类，不能被继承。对于已经存在的String对象，修改它的值，就是新创建一个对象
* StringBuffer是一个类似于String的字符串缓冲区，使用append()方法修改StringBuilder，使用toString()方法转换为字符串，是线程安全的
* StringBuilder用来替代与StringBuffer，StringBuilder是非线程安全的，速度更快。
## 异常处理
* Exception、Error是Throwable类的子类
* Error类对象由Java虚拟机生成并抛出，不可捕捉
* 不管有没有异常，finally中的代码都会执行
* 当try、catch中有return时，finally中的代码依然会继续执行
**常见的Error**
OutOfMemeoryError
StackOverflowError
NoClassDeffoundError

**常见的Exception**

常见的非检查性异常
* ArithmeticException
* IllegalArgumentException
* NumberFormatException
* ArrayIndexOutOfBoundsException
* IndexOutOfBoundsException
* SecurityException
* ClassCastExcetion
* NullPointerException
* UnsupportedOperationException

常见的检查性异常
* IOException
* NoSuchFiledException
* CloneNotSupportedException
* NoSunchMethodException
* IllegalAcessException
* FileNotFoundException


## 内部类
* 非静态内部类没法在外部类的静态方法中实例化
* 非静态内部类的方法可以直接访问外部类的所有数据，包括私有的数据
* 在静态内部类中嗲用外部类成员，成员也要求用static修饰。
* 创建静态内部类的对象可以直接通过外部类调用静态内部类的构造器，创建非静态的内部类的对象必须先创建外部类的对象，通过外部类的对象调用内部类的构造器。

## 匿名内部类
* 匿名内部类不能定义任何静态成员、方法
* 匿名内部类中的方法不能时抽象的
* 匿名内部类必须实现接口或抽象父类的所有抽象方法
* 匿名内部类不能定义构造器
* 匿名内部类访问的外部类成员变量或成员方法必须用final修饰



## 多态
* 父类的引用可以指向子类的对象
* 创建子类对象时，调用的方法为子类重写的方法或者继承的方法
* 如果我们在子类中编写一个独有的方法，此时就不能通过父类的引用创建的子类对象来调用该方法。
## 抽象和接口
* 抽象类不能有对象（不能用new关键字来创建抽象类的对象）
* 抽象类中的抽象方法必须在子类中被重写
* 接口中的所有属性默认为：public static final ***;
* 接口中的所有方法默认为：public abstract ***;

## 集合框架
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210107145504712.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)



### HashMap
#### 结构图
* JDK1.7 HashMap结构图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210107150842466.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)

```
static class Entry<K,V> implements Map.Entry<K,V> {
		final K key,
		V value;
		Entry<K,V> next;
		int hash;
}
```

* JDK 1.8 HashMap结构图
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210107151753348.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)



#### HashMap的工作原理
HashMap基于hashing原理，我们通过put()和get()方法存储和获取对象。当我们将键值传递给put()方法时，它调用键对象的hashCode()方法来计算hashcode, 然后找到bucket位置来存储Entry对象。当两个对象的hashcode相同时，它的bucket位置相同，‘碰撞’会发生。因为HashMap使用链表存储对象，这个Entry会存储在链表中，当获取对象时，通过键对象的equals()方法找到正确的键值对，然后返回值对象。
* 如果HashMap的大小超过了负载因子（load factor）定义的容量，怎么办？
默认的负载因子大小为0.75，也就是说，当一个map填满了75%的bucket的时候，和其它集合类（如ArrayList等）一样，将会创建原来HashMap大小两倍的bucket数组，来重新调整map的大小，并将原来的对象放入新的bucket数组中。这个过程叫做rehashing，因为它调用hash方法找到新的bucket位置。

* 为什么String，Integer这样的wrapper类适合作为键？
因为String是不可变的，也是final的，而且已经重写了equals()和hashCode()方法了。其他的wrapper类页游这个特点。不可变性时必要的，因为为了要计算hashCode()，就要防止键值改变，如果键值在放入时和获取时返回不同的hashCode的话，那么就不能从HashMap中找到你想要的对象。不可变形还有其他的优点如线程安全。如果你可以仅仅通过某个field声明成final就能保证hashCode时不变的，那么请这么做吧。
因为获取对象的时候要用到equals()和hashCode()方法，那么键对象正确的重写这两个方法时非常重要的。如果两个不想等的对象返回不同的hashcode的话，那么碰撞的几率就会小些，这样就能提高HashMap的性能。


#### HashMap与HashTable对比
HashMap是非synchronized的，性能更好，HashMap可以接受为null的key-value，而HashTable是线程安全的，比HashMap要慢，不接受null的key-value。

**HashMap.java**
```
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>, Cloneable, Serializable {
	public V put(K key, V value){
		return putVal(hash(key), key, value, false, true);
	}
	...
	public V get(Object key) {
		Node<K,V> e;
		return (e == getNode(hash(key), key)) == null ? null : e.value;
	}
}
```

**HashTable.java**

```
public class Hashtable<K, V> extends Dictionary<K,V> implements<K,V>, Cloneable, java.io.Serializable {
	...
	public synchronized V put(K key, V value) {
		// Make sure the value is not null
		if(value == null){
			throw new NullPointerException();
		}
		...
		addEntry(hash, key, value, index);
		return null;
	}
	...
	public synchronized V get(Object key) {
		HashtableEntry<?,?> tab[] = table;
		int hash = key.hashCode();
		int index = (hash & 0x7FFFFFFF) % tab.length;
		for(HashtableEntry<?,?> e = tab[index]; e != null; e = e.next) {
			if((e.hash==hash) && e.key.equals(key)){
				return (V)e.value;
			}
		}
		return null;
	}
	
}
```


### ConcurrentHashMap
ConcurrentHashMap最外层不是一个大的数组，而是一个Segment的数组。每个Segment包含一个与HashMap数据结构差不多的链表数组。

#### Base1.7
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210108110927504.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)

在读写某个Key时，先取该Key的哈希值。并将哈希值的高N位对Segment个树取模从而得到该Key应该属于那个Segment，接着如何操作HashMap一样操作这个Segment。
Segment继承自ReentrantLock，可以很方便的对每一个Segment上锁。
对于读操作，获取Key所在的Segment时，需要保证可见性。具体实现上可以使用volatile关键字，也可使用锁。但使用锁开销太大，而使用volatile时每次都会让CPU内缓存无效，页游一定开销。ConcurrrentHashMap使用如下方法保证可见性，取得最新的Segment：

```
Segment<K,V> s = (Segment<K,V>)UNSAFE.getObjectVolatile(segments, u)
```
获取Segment中的HashEntry的HashEntry时也使用了类似方法：

```
HashEntry<K,V> s = (HashEntry<K,V>)UNSAFE.getObjectVolatile(tab, ((long)(((tab.length - 1) & h) << TSHIFT) + TBASE)
```
对于写操作，并不要求同时获取所有Segment的锁，因为那样相当于锁住了整个Map。它会先获取该Key-Value对所在的Segment的安全性。同时由于其它Segment的锁并未被获取，因此理论上可支持concurrentcyLevel（等于Segment的个数）个线程安全的并发读写。
获取锁时，并不直接使用lock来获取，因为该方法获取锁失败时会挂起。事实上，它使用了自旋锁，如果tryLock获取锁失败，说明锁被其它线程 占用，此时通过循环再次以tryLock的方式申请锁。如果在循环过程中该Key所对应的链表头被修改，则充值retry次数。如果retry此时超过一定值，则使用lock方法申请锁。
这里使用的自旋锁时因为自旋锁的效率比较高，但是它小号CPU资源比较多，因此在自旋次数超过阈值时切换为互斥锁。



#### Base1.8
1.7 已经解决了并发问题，并且能支持N个Segment这么多次数的并发，但依然存在HashMap在1.7版本中的问题：查询遍历链表效率太低。一次1.8做了一些数据结构上的调整。
![在这里插入图片描述](https://img-blog.csdnimg.cn/20210108112208179.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)
其中抛弃了原有的Segment分段锁，而采用了CAS+synchronized来保证并发安全性。

**ConcurrentHashMap.java**
```
final V putVal(K key, V value, boolean onlyIfAbset) {
	if(key == null || value == null) throw new NullPointerException();
	int hash = spread(key.hashCode());
	int binCount = 0;
	for(Node<K, V>[] tab = table ;; ){
		Node<K,V> f; int n, i, fh;
		if(tab == null || (n = tab.length) == 0) 
			tab.= initTable();
		else if((f = tabAt(tab, i = (n - 1) & has)) == null) {
			if(casTabAt(tab, i, null, new Node<K,V>(hash, key, value, null)) {
				break;
			}
		}
		else if((fh = f.has) == MOVED){
			tab = helpTransfer(tab, f);
		} else {
			V oldVal = null;
			synchronized(f) {
				if(tabAt(tab, i) == f) {
					if(fh >= 0) {
						binCount = 1;
						...
					}
					else if(f instanceof TreeBin) {
						...
					}
					else if(f instanceof ReservationNode) {
						throw new IllegalStateException("Recursive update");
					}
				}
			}
		}
	}
	addCount(1L, binCount);
	return null;
}
```


## ArrayList
ArrayList本质上是一个动态数组，第一次添加元素时，数组大小将变化为DEFAULT_CAPACITY 10, 不断添加元素后，会进行扩容。删除元素时，会按住嗷位置关系把数组元素整体（复制）移动一遍。
**ArrayList**

```
public class ArrayList<E> extends AbstractList<E> implements List<E>, RandomAccess, Clonable, Clonable, java.io.Serializable{
	...
	// 增加元素
	public boolean add(E e) {
		ensureCapacityInternal(size + 1); // Increments modCound!!
		elementData[size++] = e;
		return true;
	}
	// 删除元素
	public E remove(int index) {
		if(index >= size) {
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		}
		modCount++;
		E oldValue = (E)elementData[index];
	
		int numMoved = size - index - 1;
		if(numMoved > 0) 
			System.arraycopy(elementData, index+1, elementData, index, numMoved);
		elementData[--size] = null; // clear to let GC do its work
		return oldValue;
	}
	...

	// 查找元素
	public E get(int index) {
		if(index >= size)
			throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
		return (E) elementData[index];
	}
```

## LinkedList
LinkedList本质上是一个双向链表的存储结构

**LinkedList**
```
public class LinkedList<E> extends AbstractSequentialList<E> implements List<E>, Deque<E>, Cloneable, java.io.Serializable
{
	...
	private static class Node<E> {
		E item;
		Node<E> next;
		Node<E> prev;
		Node(Node<E> prev, E element, Node<E> next) {
			this.item = element;
			this.next = next;
			this.prev = prev;
		}

	// 增加元素
	void linkLast(E e){
		final Node<E> l = last;
		final Node<E> newNode = new Node<>(l, e, null);
		last = newNode;
		if(l == null) {
			first = newNode;
		} else {
			l.next = newNode;
		}
		size++;
		modCount++;
	}
	...
	// 删除元素
	E unlink(Node<E> x) {
		final E element = x.item;
		final Node<E> next = x.next;
		final Node<E> prev = x.prev;

		if(prev == null) {
			first = next;
		} else {
			prev.next = next;
			x.prev = null;
		}
		if(next == null){
			last = prev;
		} else {
			next.prev = prev;
			x.next = null;
		}
		x.item = null;
		size --;
		modeCount++;
		return element;
	}
}
...

	// 查找元素
	Node<E> node(int index) {
		// assert isElementIndex(index);
		if(index < (size >> 1)) {
			Node<E> x = first;
			for(int i = 0; i < index; i++) {
				x = x.next;
			}
			return x;
		} else {
			Node<E> x = last;
			for(int i = size - 1; i > index; i--)
				x=x.prev;
			return x;
		}
	...
}
```
对于元素查询来说，ArrayList优于LinkedList，因为LinkedList要移动指针。对于新增和删除操作，LinkedList比较占优势，因为ArrayList要移动数据。

## CopyOnWriteArrayList

CopyOnWriteArrayList时线程安全容器（相对于ArrayList），增加删除等操作通过加锁的相识保证数据一致性，通过复制新集合的方式决绝遍历迭代问题。

**CopyOnWriteArrayList.java**

```
public class CopyOnWriteArrayList<E> implements List<E>, RandomAccess, java.io.Serializable {
	final transient Object lock = new Object();
	...
	
	//增加元素
	public boolean add(E e){
		synchronized(lock){
			Object[] elements = getArray();
			int len = elements.length;
			Object[] newElements = Arrays.copyOf(elements, len+1);
			newElements[len] = e;
			setArray(newElements);
			retrun true;
		}
	}
	
	// 删除元素
	public E remove(int index) {
		synchronized(lock) {
			Object[] elements = getArray();
			int len elements.length;
			E oldValue = get(element, index);
			int numMoved = len - index - 1;
			if(numMoved == 0) {
				setArray(Arrays.copyOf(elements, len - 1));
			} else {
				Object[] newElements = new Object[len - 1];
				System.arraycopy(elements, 0, newElements, 0, index);
				System.arraycopy(elements, index + 1, newElements, index, numMoved);
				setArray(newElements);
			}
			return oldValues;
		}
	}
	...
	
	//查找元素
	private E get(Object[] a, int index) {
		return (E)a[index];
	}
}
```



## 反射

```
try{
	Class cls = Class.forName("com.jasonwu.Test");
	//获取构造方法
	Constructor[] publicConstructors = cls.getConstructors();
	//获取全部构造方法
	Constructor[] declaredConstructors = cls.getDeclaredConstructors();

	//获取公开方法
	Method[] methods = cls.getMethods();
	//获取全部方法
	Method[] delcaredMethods = cls.getDelaredMethods();
	//获取公开属性
	Field[] publicFields = cls.getFields();
	//获取全部属性
	Field[] declaredFields = cls.getDeclaredFields();
	
	Object clsObject = cls.newInstance();
	Method method = cls.getDeclaredMethod("getModuleFunctionality");
	Object object = method.invoke(null);
} catch (ClassNotFoundException e) {
	e.printStackTrace();
} catch (IllegalAccessException e) {
	e.printStackTrace();
} catch (InstantiationException e) {
	e.printStackTrace();
} catch (NoSuchMethodException e) {
	e.printStackTrace();
} catch (InvocationTargetException e) {
	e.printStackTrace();
}
```



## 单例

### 饿汉式

```
public class CustomManager {
	private Context mContext;
	private static final Object mLock = new Object;
	private static CustomMananger mInstance;
	
	public static CustomManager getInstance(Context context) {
		synchronized(mLock) {
			if(mInstance == null) {
				mInstance = new CustomManager(context);
			}
			return mInstance;
		}
	}
	
	private CustomManager(Context context){
		this.mContext= context.getApplicationContext();
	}
	
}
```

### 双重检查模式

```
public class CustomManager {
	private Context mContext;
	private volatile static CustomManager mInstance;
	public static CustomManager getInstance(Context context) {
		//避免非必要加锁
		if(mInstance == null) {
			synchronized(CustomMananger.class) {
				if(mInstance == null) {
					mInstance = new CustomManager(context);
				}
			}
		}
		return mInstance;
	}
	
	private CustomManager(Context context) {
		this.mContext = context.getApplicationContext();
	}
}
```



### 静态内部类模式

```
public class CustomManager {
	private CustomManager(){}
	
	private static class CustomManagerHolder {
		private static final CustomManager INSTANCE = new CustomManager();
	}
	
	public static CustomManager getInstance(){
		return CustomMnagerHolder.INSTANCE;
	}
}
```



静态内部类的原理是：

当SingleTo第一次被加载时，并不需要加载SingleTonHolder，只有当getInstance()方法第一次被调用时，才会去初始化INSTANCE，这种方法不仅能确保线程安全，也能保证单例的唯一性，同时也延迟了单例的实例化。getInstance方法并没有多去new对象，取的都是同一个INSTANCE对象。

虚拟机会保证一个类的```<clint>()```方法在多线程环境中被正确的加锁、同步，如果多个线程同时取初始化一个类，那么只会有一个线程去执行这个类的```<clinit>()```方法，其他线程都需要阻塞等待，直到活动线程执行```<clinit>()```方法完毕。

缺点在于无法传递参数



## 线程

线程时进程中独立执行的最小单位，也是CPU资源（时间片）分配的基本单位。同一个进程中的线程可以共享进程中的资源，如内存空间和文件句柄。

### 属性





| 属性     | 说明                                                         |
| :------- | ------------------------------------------------------------ |
| id       | 线程id用于标识不同的线程。编号可能被后续创建的线程使用。编号是只读属性，不能修改 |
| name     | 名字的默认值是Thread-(id)                                    |
| daemon   | 分为守护线程和用户线程，我们可以通过setDaemon(true)把线程设置为守护线程。守护线程通常用于执行不重要的的任务，比如监控其他线程的运行情况，GC线程就是一个守护线程。setDaemon()要在线程启动前设置，否者JVM会抛出非法线程状态异常，可被继承。 |
| priority | 线程调度器会根据这个值来决定哪个线程（不保证），优先级的取值范围1～10，默认值是5，可被继承。Thread中定义了下面三个优先级常量：<br />- 最低优先级：MIN_PRIORITY = 1<br />- 默认优先级:NORM_PRIORITY = 5<br />- 最高优先级:MAX_PRIORITY = 10 |



### 状态

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210111114924528.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)



| 状态          | 说明                                                         |
| ------------- | ------------------------------------------------------------ |
| New           | 新创建了一个线程对象，但还没有调用start()方法。              |
| Runnable      | Ready状态线程对象创建后，其他线程（比如main线程）调用了该对象的start()方法。该状态的线程位于可运行线程池中，等待被线程调度选中获取cpu的使用权。Running绪状态在获得CPU时间片后变为运行中状态(running)。 |
| Blocked       | 线程因为某种原因放弃了cpu使用权（等待锁），暂时停止运行      |
| Waiting       | 线程进入等待状态因为以下几个方法：<br />- Object#wait()<br />- Thread#join()<br />- LockSupport#part() |
| Timed Waiting | 有等待时间的等待状态                                         |
| Terminated    | 表示该线程已经执行完毕                                       |



## 状态控制

* wait() / notify() / notifyAll()

wait() , notify() , notifyAll() 是定义在Object类的实例方法，用于控制线程状态，是那个方法都必须在synchronized同步关键字锁限定的作用域中调用，否则会报错

Java.lang.IllegalMonitorStrateException

| 方法        | 说明                                                         |
| ----------- | ------------------------------------------------------------ |
| wait()      | 线程状态由 的使用权。Running变为waiting，并将当前线程放入等待队列中 |
| notify()    | notify()方法是将等待队列中一个等待线程从等待队列移动到同步队列中 |
| notifyAll() | 则是将所有等待对垒中的线程移动到同步队列中                   |

被移动的线程状态由Running变为blocked，notifyAll方法调用后，等待线程依旧不会从wait()返回，需要调用notify()或notifyAll()的线程释放掉锁后，等待线程才有机会从wait()返回。



* Join() / sleep() / yield()

在很多情况，主线程创建并启动子线程，如果子线程中需要进行大量的耗时计算，祝线程往往早于子线程结束。这时，如果主线程想等待子线程执行结束之后再结束，比如子线程处理一个元素，主线程要取得这个数据，就要用join()方法。

sleep(long)方法在睡眠时不释放对象锁，而join()方法在等待的过程中释放对象锁。

yield()方法会临时暂停当前正在执行的线程，来让有同样优先级的正在等待的线程有机会执行。如果没有正在等待的线程，或者所有正在等待线程的优先级都比较低，那么该线程会继续运行。执行了yield方法的线程什么时候会继续运行由线程调度器来决定。



## volatile

当把变量声明为volatile类型后，编译器与运行时都会注意到这个变量时共享的，因此不会讲该变量上的操作与其他内存操作一起重排序。volatile变量不会被缓存在寄存器或者对其他处理器不可见的地方，JVM保证了每次读变量都从内存中读，跳过CPU cache这一步，因此读取volatile类型变量时总会返回最新写入的值。

![在这里插入图片描述](https://img-blog.csdnimg.cn/20210112104351323.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)

当一个变量定义为volatile之后，将具备以下特性：

* 保证此变量对所有的线程的可见性，不能保证它具有原子性（可见性，是指线程之间的可见性，一个线程修改的状态对另一个线程是可见的）
* 禁止指令重排序优化
* volatile的读性能消耗与普通变量几乎相同，但是写操作稍慢，因为它需要在本地代码中插入许多内存屏障指令来保证处理器不发生乱序执行



AtomicInteger中主要实现了整型的原子操作，防止并发情况下出现异常结果，其内部主要JDK中的unsafe类操作内存中的数据来实现的。volatile修饰符保证了value在内存中其他线程可以看到其值得改变。CAS(Compare and Swap)操作保证了AtomicInteger可以安全的修改value的值。



## synchronized

当它来修饰一个方法或者一个代码块的时候，能够保证在同一时刻最多只有一个线程执行该段代码。

在Java中，每个对象都会有一个monitor对象，这个对象其实就是Java对象的锁，通常会被称为“内置锁”或“对象锁”。类的对象可以有多个，所以每个对象有其独立的对象锁，互不干扰。针对每个类页游一个锁，可以称为“类锁”，类锁实际上是通过对象锁实现的，即类的Class对象锁。每个类只有一个Class对象，所以每个类只有一个类锁。

Monitor时线程私有的数据结构，每一个线程都有一个可用moitor record列表，同时还有一个全局的可用列表。每一个被锁住的对象都会和一个monitor关联，同时monitor中有一个owner字段存放拥有该锁的线程的唯一标识，表示该锁被这个线程占用。Monitor时依赖于底层的操作系统的Mutext Lock（互斥锁）来实现的线程同步。

### 根据获取的锁分类

**获取对象锁**

* synchroinzed(this|object){}
* 修饰非静态方法

**获取类锁**

* synchronized(类.class){}
* 修饰静态方法

### 原理

同步代码块：

* monitorenter和monitorexit指令实现的

同步方法

* 方法修饰符上的ACC_SYNCHRONIZED实现



## Lock

```java
public interface Lock{
	void lock();
	void lockInterruptibly() throw InterruptedException;
	boolean tryLock();
	boolean tryLock(long time, TimeUnit unit) throws InterruptedException;
	void unlick();
	Condition newCondition();
}
```



| 方法                   | 说明                                                         |
| ---------------------- | ------------------------------------------------------------ |
| lock()                 | 用来获取锁，如果锁被其他线程获取，处于等待状态。如果采用lock，必须主动去释放锁，并且在发生异常时，不会自动释放锁。因此一般来说，如果Lock必须在try{}catch{}块中进行，并且将释放锁的操作放在finally块中进行，以保证锁一定被释放，防止死锁的发生。 |
| lockInterruptibly()    | 通过这个方法取获取锁时，如果线程正在等待获取锁，则这个线程能够响应中断，即中断线程的等待状态。 |
| tryLock()              | tryLock方法是有返回值的，它表示用来尝试获取锁，如果获取成功，则返回true，如果获取失败（即锁已被其它线程获取），则返回false，也就是说这个方法无论如何都会立即返回。在拿不到锁时不会一直在那儿等待。 |
| tryLock(long.TimeUnit) | 与tryLock类似，只不过时有等待时间，在等待时间内获取到锁返回true，超时返回false。 |



### 锁的分类



![在这里插入图片描述](https://img-blog.csdnimg.cn/20210112111859948.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L0FkcmlhbkFuZHJvaWQ=,size_16,color_FFFFFF,t_70)



### 悲观锁、乐观锁

悲观锁认为自己在使用数据的时候一定有别的线程来修改数据，因此在获取数据的时候会先加锁，确保数据不会被别的线程修改。Java中，synchronized关键字和Lock的实现类都是悲观锁。悲观锁适合写操作比较多的场景，先加锁可以保证写操作时数据正确。

而乐观锁认为自己在使用数据时不会有别的线程修改数据，所以不会添加锁，只是在更新数据的时候去判断之前有没有别的线程更新了这个数据。而这个数据没有被更新，当前线程将自己修改的数据成功写入。如果数据已经被其他线程更新，而根据不同的实现方式 执行不同的操作（例如报错或者自动重试）。乐观锁在Java中通过使用无锁编程来实现，最常采用的是CAS算法，Java原子类中的递增操作就通过CAS自旋实现。乐观锁适合读操作多的场景，不加锁的特点能够使其读操作的性能大幅提升。



### 自旋锁、适应性自旋锁

阻塞或唤醒一个Java线程需要操作系统切换CPU状态来完成，这种状态转换需要耗费处理时间。如果同步代码块中的内容过于简单，状态转换消费的时间有可能比用户代码执行的时间还要长。

在许多场景中，同步资源的锁定时间很短，为了这一小段时间去切换线程，线程挂起和恢复现场的话费可能会让系统得不偿失。如果物理机器有多个处理器，能够让两个或以上的线程同时并行执行，我们就可以让后面那个请求锁的线程不放弃CPU的执行时间，看看持有锁的线程是否很快就会释放锁。

而为了让当前线程“稍等一下”，我们需让当前线程进行自旋，如果在自旋完成后前面锁定同步资源的线程已经释放了锁，那么当前线程就可以不必阻塞而逝直接获取同步资源，从而避免切换线程的开销。这就是自旋锁。

自旋锁本身是有缺点的，它不能代替阻塞。自旋等待虽然避免了线程切换的开销，但它要占用处理器时间。如果锁被占用的时间很短，自旋等待的效果就会非常好。反之，如果锁被占用的时间很长，那么自旋的线程只会白浪费处理器资源。所以，自旋等待的时间必须要有一定的限度，如果自旋超过了限定次数（默认是10次，可以使用-XX:PreBlockSpin来更改)没有成功获得锁，就应当挂起线程。

自旋锁的实现原理同样也是CAS，AtomicInteger中调用unsafe进行自增操作的源码中的do-while循环就是一个自旋操作，如果修改数值失败则通过循环来执行自旋，直至修改成功。



### 死锁

当前线程拥有其他线程需要的资源，当前线程等待其他线程已拥有的资源，都不放弃自己拥有的资源。



## 引用类型

强引用〉软引用〉弱引用

| 引用类型                   | 说明                                                         |
| -------------------------- | ------------------------------------------------------------ |
| StrongReference(强引用)    | 当一个对象具有强引用，那么垃圾回收是绝对不会的回收和销毁它的，非静态内部类会在其整个生命周期中持有对它外部类的强引用 |
| WeakReference（弱引用）    | 在垃圾回收器运行的时候，如果对一个对象的所有引用都是弱引用的话，该对象会被回收 |
| SoftReference（软引用）    | 如果一个对象只具有软引用，弱内存空间足够，垃圾回收器就不会回收它；如果内存空间不足了，才会回收这些对象的内存 |
| PhantomReference（虚引用） | 一个只被虚引用持有的对象可能会在任何时候被 GC 回收。虚引用对对象的生存周期完全没有影响，也无法通过虚引用来获取对象实例，仅仅能在对象被回收时，得到一个系统通知（只能通过是否被加入到ReferenceQueue来判断是否被GC，这也是唯一判断对象时否被GC的途径）。 |



## 动态代理

示例：

```java
// 定义相关接口
public interface BaseInterface {
	void doSomething();
}

// 定义相关的实现类
public class BaseImpl implements BaseInterface {
  @Override
  public void doSomething(){
    System.out.println("doSomething");
  }
}

public static void main(String args[]){
  BaseImpol base = new BaseImpl();
  // Proxy 动态代理实现
  
  BaseInterface proxyInstance = (BaseInterface)Proxy.newProxyInstance(base.getClass().getClassLoader(), base.getClass().getInterfaces(), new InvocationHandler(){
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable{
      if(method.getName().equals("doSomething")) {
        method.invoke(base, args);
        System.out.println("do more");
      }
      return null;
    }
  });
  
  proxyInstance.doSomething();
}


```





```java
// Proxy.java
public class Proxy implements java.io.Serializable {
  // 代理类的缓存
  private static final WeakCache<ClassLoader, Class<?>[], Class<?>>  proxyClassCache = new WeakCache<>(new KeyFactory(), new ProxyClassFactory());
  ...
    
  //生成代理对象方法入口
    
    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interface, InvocationHandler h) throw IllegalArgumentException{
    Objects.requireNonNull(h);
    final Class<?>[] intfs = interfaces.clone();
    //找到并生成相关的代理类
    Class<?> cl = getProxyClass0(loader, intfs);
    // 调用代理类的构造方法生成代理类实例
    try {
      final Constructor<?> cons = cl.getConstructor(constructorParams);
      final InvocationHandler ih = h;
      if(!Modifier.isPublic(cl.getModifiers())){
        cons.setAccessible(true)l;
      }
      return cons.newInstance(new Object[]{h});
    } 
    ...
  }
	...
    
    
    
   // 定义和返回代理类的工厂类
  private static final class ProxyClassFactory implements BiFunction<ClassLoader, Class<?>[], Class<?>> {
    // 所有代理类的前缀
    private static final String proxyClassNamePrefix = "$Proxy";
    // 用于生成唯一代理类名称的下一个数字
    private static final AtomicLong nextUniqueNumber = new AtomicLong();

    @Override
    public Class<?> apply(ClassLoader loader, Class<?>[] interface){
      Map<Class<?>, Boolean> interfaceSet = new IdentityHashMap<>(interfaces.length);
      ...
      String proxyPkg = null; //用于定义代理类的包名
      int accessFlags = Mmodifier.PUBLIC | Modifier.FINAL;

      //确保所有的non-public 的代理接口在相同的包里
      for(Class<?> intf : interfaces) {
        int flags = intf.getModdifiers();
        if(!Modifier.isPublis(flags)){
          accessFlags = Modifier.FINAL;
          String name = intf.getName();
          int n = name.lastIndexOf('.');
          String pkg = ((n == -1) ? "" : name.substring(0, n+1));
          if(proxyPkg == null) {
            proxyPkg = pkg;
          } else if(!pkg.equals(proxyPkg)) {
            throw new illegalArgumentException("non-public interfaces from different packages");
          }
        }
      }
      if(proxyPkg == null) {
        // 如果没有non-publi的代理接口，使用默认的包名
        proxyPkg = "";
      }
      // Choose a name for the proxy class to generate.
      long num = nextuniqueNumber.getAndIncrement();
      String proxyName = proxyPkg + proxyClassNamePrefix + num;
      
      // Generate the specified proxy class.
      byte[] proxyClassFile = ProxyGenerator.generateProxyClass(
      	proxyname, interfaces, accessFlags
      );
      try {
        return defineClass0(loader, proxyName, proxyClassFile, 0, proxyClassFile.length);
      } catch(ClassFormatError e) {
        throw new IllegalArgumentsException(e.toString());
      }
    }
  }
  
  // 最终调用natvie方法生成代理类
  @FastNative
  private static native Class<?> generateProxy(String name, Class<?>[] interface, ClassLoader loader, Method[] methods, Class<?>[][] exceptions) 
}

```



```java
//ProxyGenerator.java
public static byte[] generateProxyClass(final String name, Class[] interfaces) {
  ProxyGenerator gen = new ProxyGenerator(name, interfaces);
  final byte[] classFile = gen.generateClassFile();
  if(saveGeneratedFiles) {
    java.security.AccessController.doPrivileged(
			new java.security.PrivilegedAction<Void>(){
        public void run(){
          try{
            FileOutputStream file = new FileoutputStream(dotToSlash(name) + ".class");
            file.write(classFile);
            file.close();
            retun null;
          } catch(IOException e){
            throw new InternalError(
              "I/O exception saving generated file:" + e
            );
          }
        }
      }
    );
  }
  return classFile;
}
  
```





元注解
Android知识点汇总
Activity
Fragment
Service
BroadcastReceiver
ContentProvider
数据存储
View
进程
Parcelable接口
IPC
Window/WindowManager
Bitmap
屏幕适配
Context
Sharedreferences
消息机制
线程异步
RecyclerView优化
Webview
Android扩展知识点
ART
APK包优化
Hook
Proguard
架构
Jetpack
NDK开发
计算机网络基础
类加载器
Android开源库源码分析
LeakCanary
EventBus
设计模式汇总
设计模式分类
面向对象的六大原则
工厂模式
单例模式
建造者模式
原型模式
适配器模式
观察者模式
代理模式
责任链模式
策略模式
备忘录模式
Gradle知识点汇总
依赖项配置
常见面试算法题汇总
排序
二叉树
链表
栈/队列
二分查找
哈希表
堆/优先队列
二叉搜索树
数组/双指针
贪心算法
字符串处理
动态规划
矩阵
二进制/位运算
其他
test
JVM
HELLO






# Android
[Android 高级开发工程师面试题以及答案整理](https://www.jianshu.com/p/21c7a804f3b4)
[2019大厂Android高级工程师面试题整理+进阶资料](https://www.jianshu.com/p/e6702d61eec9)
[面试一线互联网公司Android中高级开发工程师必问面试题集锦](https://cloud.tencent.com/developer/article/1444042)
[2019大厂Android高级工程师面试题整理+进阶资料](https://yq.aliyun.com/articles/687809)

# IOS