$(document).ready(function() {
  $(window).keydown(function (e) {
    if (e.keyCode == 116) {
      if (!confirm("Refresh will clear all the messages, do you really want to refresh?")) {
        e.preventDefault();
      }
    }
  });
  var socket = io.connect();
  var from = $.cookie('user');//从 cookie 中读取用户名，存于变量 from
  var to = 'all';//设置默认接收对象为"所有人"
  //发送用户上线信号
  socket.emit('online', {user: from});
  socket.on('online', function (data) {
    //显示系统消息
    if (data.user != from) {
      var sys = '<div style="color:#f00">System(' + now() + '):' + ' User ' + data.user + ' is online!</div>';
    } else {
      var sys = '<p class="descript">您可以基于华为云SDK通过编写代码的方式调用华为云API，实现对应用、资源和数据进行灵活的部署、更快速的操作、更精确的使用、更及时的监控。华为云的产品和服务陆续都会推出自己的SDK，您可以通过本页面获取相关的更新。</p>';
    }
    $("#contents").append(sys + "<br/>");
    //刷新用户在线列表
    flushUsers(data.users);
    //显示正在对谁说话
    showSayTo();
  });

  socket.on('say', function (data) {
    //对所有人说
    if (data.to == 'all') {
      $("#contents").append('<div>' + data.from + '(' + now() + ')say to all users: <br/>' + data.msg + '</div><br />');
    }
    //对你密语
    if (data.to == from) {
      $("#contents").append('<div style="color:#00f" >' + data.from + '(' + now() + ')say to you: <br/>' + data.msg + '</div><br />');
    }
  });

  socket.on('offline', function (data) {
    //显示系统消息
    var sys = '<div style="color:#f00">System(' + now() + '):' + ' User ' + data.user + ' is offline!</div>';
    $("#contents").append(sys + "<br/>");
    //刷新用户在线列表
    flushUsers(data.users);
    //如果正对某人聊天，该人却下线了
    if (data.user == to) {
      to = "all";
    }
    //显示正在对谁说话
    showSayTo();
  });

  //服务器关闭
  socket.on('disconnect', function() {
    var sys = '<div style="color:#f00">System: connection failed!</div>';
    $("#contents").append(sys + "<br/>");
    $("#list").empty();
  });

  //重新启动服务器
  socket.on('reconnect', function() {
    var sys = '<div style="color:#f00">System: reconnect!</div>';
    $("#contents").append(sys + "<br/>");
    socket.emit('online', {user: from});
  });

  //刷新用户在线列表
  function flushUsers(users) {
    //清空之前用户列表，添加 "所有人" 选项并默认为灰色选中效果
    $("#list").empty().append('<li title="Double click to chat" alt="all" class="sayingto" onselectstart="return false">All users</li>');
    //遍历生成用户在线列表
    for (var i in users) {
      $("#list").append('<li alt="' + users[i] + '" title="Double click to chat" onselectstart="return false">' + users[i] + '</li>');
    }
    //双击对某人聊天
    $("#list > li").dblclick(function() {
      //如果不是双击的自己的名字
      if ($(this).attr('alt') != from) {
        //设置被双击的用户为说话对象
        to = $(this).attr('alt');
        //清除之前的选中效果
        $("#list > li").removeClass('sayingto');
        //给被双击的用户添加选中效果
        $(this).addClass('sayingto');
        //刷新正在对谁说话
        showSayTo();
      }
    });
  }

  //显示正在对谁说话
  function showSayTo() {
    $("#from").html(from);
    $("#to").html(to == "all" ? "All users" : to);
  }

  //获取当前时间
  function now() {
    var date = new Date();
    var time = date.getFullYear() + '-' + (date.getMonth() + 1) + '-' + date.getDate() + ' ' + date.getHours() + ':' + (date.getMinutes() < 10 ? ('0' + date.getMinutes()) : date.getMinutes()) + ":" + (date.getSeconds() < 10 ? ('0' + date.getSeconds()) : date.getSeconds());
    return time;
  }

  //发话
  $("#say").click(function() {
    //获取要发送的信息
    var $msg = $("#input_content").html();
    if ($msg == "") return;
    //把发送的信息先添加到自己的浏览器 DOM 中
    if (to == "all") {
      $("#contents").append('<div>You(' + now() + ')are talking to all users: <br/>' + $msg + '</div><br />');
    } else {
      $("#contents").append('<div style="color:#00f" >You(' + now() + ')are talking to: ' + to + ' <br/>' + $msg + '</div><br />');
    }
    //发送发话信息
    socket.emit('say', {from: from, to: to, msg: $msg});
    //清空输入框并获得焦点
    $("#input_content").html("").focus();
  });
});
