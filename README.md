#RightOrWrongView
-------------------

这是一个判断对错的动画，场景可以用于试题的应用，来判断做题的正误啦:)


![正确演示](http://img.blog.csdn.net/20160721181943045)

![错误演示](http://img.blog.csdn.net/20160721181955643)

#引入到你的项目
-------------------
1.**copy** BallView.java 和 mipmap-xxhdpi里面的图片到项目中即可
2. 在values中的attrs.xml中添加
```
    <declare-styleable name="circle">
        <attr name="circleWidth" format="dimension"></attr>
        <attr name="circleHeight" format="dimension"></attr>
        <attr name="circleColor" format="color"/>
    </declare-styleable>
```
#用法
-------------------
1.在xml中调用
```
    <com.app.myapplication.RightOrWrongView
        android:id="@+id/rwview"
        android:layout_width="200dp"
        android:layout_height="200dp"
        hj:circleWidth = "200dp"
        hj:circleHeight = "200dp"
        android:visibility="gone"/>
```
2.在Activity中调用
```
        rwview = (RightOrWrongView) findViewById(R.id.rwview);
        tv = (TextView) findViewById(R.id.tv);
        rwview.setRightOrWrong(RightOrWrongView.RIGHT);
```
**注意**，可能你会要在动画结束后进行一些操作，那么请加上监听
```
        rwview.setCircleListener(new RightOrWrongView.CircleListener() {
            @Override
            public void AnimFinish() {
                tv.setText("动画执行完毕");
            }
        });
```
