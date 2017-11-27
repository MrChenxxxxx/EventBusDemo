# EventBusDemo

1、创建一个EventMsg类，用来传递事件的，可以无参数，也可以带参
2、注册EventBus  ：EventBus.getDefault().regist(Object);
3、发送事件 post（EventMsg）
4、接收事件
5、注销EventBus

一
PostThread：如果使用事件处理函数指定了线程模型为PostThread，那么该事件在哪个线程发布出来的，事件处理函数就会在这个线程中运行，也就是说发布事件和接收事件在同一个线程。在线程模型为PostThread的事件处理函数中尽量避免执行耗时操作，因为它会阻塞事件的传递，甚至有可能会引起ANR。
MainThread：如果使用事件处理函数指定了线程模型为MainThread，那么不论事件是在哪个线程中发布出来的，该事件处理函数都会在UI线程中执行。该方法可以用来更新UI，但是不能处理耗时操作。
BackgroundThread：如果使用事件处理函数指定了线程模型为BackgroundThread，那么如果事件是在UI线程中发布出来的，那么该事件处理函数就会在新的线程中运行，如果事件本来就是子线程中发布出来的，那么该事件处理函数直接在发布事件的线程中执行。在此事件处理函数中禁止进行UI更新操作。
Async：如果使用事件处理函数指定了线程模型为Async，那么无论事件在哪个线程发布，该事件处理函数都会在新建的子线程中执行。同样，此事件处理函数中禁止进行UI更新操作。


二
EventBus.getDefault().cancelEventDelivery(event) ;//取消事件传递

三、stick 粘性事件，用法暂未调查
EventBus.getDefault().postSticky(new MessageEvent("111","222"));//使用EventBus.postSticky()方法发送置顶事件

@Subscribe(sticky = true, threadMode = ThreadMode.MAIN)//注意此处需要增加sticky = true，表明处理置顶事件
public void handleEvent(MessageEvent event) {
    // UI updates must run on MainThread
    textView.setText(event.message);
}

四、设置优先级
@Subscribe(priority = 1)//设置优先级为1，优先级只对同个线程有效
public void onEvent(MessageEvent event) {
    //do  something
}