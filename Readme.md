## RoboletricDemo

本项目用于演示如何使用Robolectric做单元测试，使用的Robolectric版本是3.0

Robolectric 3.0不能直接针对非Android Sdk的类做Shadow，必须使用Powermock或者mockito来处理这种情况，Powermock支持partial mock，也就是说mock某个类时，不需要为这个类的所有函数做mock处理，只需针对需要改变行为的函数进行mock就可以了，但是不能对Android sdk的类做mock，因为Robolectric框架已经将这些类都替换成了Shadow类

Robolectric 3.1 已支持针对非AndroidSdk的类做Shadow，但是不支持Powermock

总的来说如果为每个要hack的类都创建Shadow类，然后为每个要hack的函数都创建shadow函数还是非常麻烦的事情，相对来说，使用PowerMock需要添加的代码就少很多，所以建议使用Robolectric 3.0+power mock+mockito来做单元测试，就已经足够

本项目演示了如下几种使用场景:

1. 简单使用Robolectric测试框架
2. 使用Robolectric框架的shadow
3. 使用Robolectric测试代码包下的资源加载
4. 使用Robolectric测试assets资源加载  
5. 使用Robolectric+powermock+mockito进行单元测试，这种单元测试方式有非常强的实用性
6. 使用Robolectric测试多线程的情况
7. 使用Robolectric测试Volley

本项目的有测试的讲解都在博客上: http://www.cloudchou.com