# Disruptor高性能队列

Apache Storm、Camel、Log4j 2在内的很多知名项目都应用了Disruptor以获取高性能，注意，这里的队列指的是系统内部队列，而不是类似RabbitMQ和Kafka这种分布式队列。

## Java内置队列

| 队列                  | 有界性             | 锁   | 数据结构   |
| :-------------------- | :----------------- | :--- | :--------- |
| ArrayBlockingQueue    | bounded            | 加锁 | arraylist  |
| LinkedBlockingQueue   | optionally-bounded | 加锁 | linkedlist |
| ConcurrentLinkedQueue | unbounded          | 无锁 | linkedlist |
| LinkedTransferQueue   | unbounded          | 无锁 | linkedlist |
| PriorityBlockingQueue | unbounded          | 加锁 | heap       |
| DelayQueue            | unbounded          | 加锁 | heap       |

队列的底层一般分成三种：**数组、链表和堆**。其中，堆一般情况下是为了实现带有优先级特性的队列，暂且不考虑。我们就从数组和链表两种数据结构来看，基于数组线程安全的队列，比较典型的是ArrayBlockingQueue，它主要通过加锁的方式来保证线程安全；基于链表的线程安全队列分成LinkedBlockingQueue和ConcurrentLinkedQueue两大类，前者也通过锁的方式来实现线程安全，而后者以及上面表格中的LinkedTransferQueue都是通过原子变量compare and swap（以下简称“CAS”）这种不加锁的方式来实现的。

通过不加锁的方式实现的队列都是无界的（无法保证队列的长度在确定的范围内）；而加锁的方式，可以实现有界队列。在稳定性要求特别高的系统中，为了防止生产者速度过快，导致内存溢出，只能选择有界队列；同时，为了减少Java的垃圾回收对系统性能的影响，会尽量选择array/heap格式的数据结构。这样筛选下来，符合条件的队列就只有ArrayBlockingQueue。

#### 取模运算

a%b 

在程序中我们可以使用a&(b-1)做位运算，速度要比取模要快得多，HashMap中就是使用这个来定位数组元素的

