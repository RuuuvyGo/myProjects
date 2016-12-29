<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AdminLTE 2 | Registration Page</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="../../dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- iCheck -->
    <link href="../../plugins/iCheck/square/blue.css" rel="stylesheet" type="text/css" />
	<!-- jQuery 2.1.4 -->
    <script src="../../plugins/jQuery/jQuery-2.1.4.min.js" type="text/javascript"></script>
	
	
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    
    <script>
    //var infoModal = $('#myModal');
    
    function makeModalMsg() {
    	$('input').iCheck({
            checkboxClass: 'icheckbox_square-blue',
            radioClass: 'iradio_square-blue',
            increaseArea: '20%' // optional
          });
    	
        $('#register').bind('click', function(e){
        	e.preventDefault();
        	var input = this;
        	input.disabled = true;
        	var str="";
        	
        	if ($("#nickName").val().trim() == "") { alert("no");str+="nickName,  ";}
        	if ($("#email").val().trim() == "") {str+="email,  ";}
	   		if ($("#pw").val().trim() == "") {str+="pw,  ";}
	   		if ($("#school").val().trim() == "") {str+="school,  ";}
	   		if ($("#entranceYear").val().trim() == "") {str+="entranceYear ";} 
        	
        	if(str!=""){
        		input.disabled = false;
				var msg=$("#msg");
				msg.text(str+"을 입력해주세요");
				msg.css("font-weight","bold");
				msg.css("font-size","15px");
				msg.css("color","Tomato");
				$('#myModal').modal('show');
        		return false;
        	}

        	var obj = new Object();
            obj.nickName = $("#nickName").val();//"BOBBY";
            obj.email  = $("#email").val();//"BoBBY_@gamil.com";
            obj.pw = $("#pw").val();//"pwpwBobby";
            obj.school = $("#school").val();//"pwpwBobby";
            obj.entranceYear = $("#entranceYear").val();//"pwpwBobby";
            
            //alert(JSON.stringify(obj));
        	
            /* ajax start */
        	$.ajax({ 
        		type : 'POST',
       			data : JSON.stringify(obj),
       			contentType : 'application/json;  charset=UTF-8',
       			url : '../../signOn',
       			dataType:'JSON',
       			success : function(a){
       				alert("여기는왜//.....제이슨이 오긴옴");
       				if(a.answer=="success"){
       					
       					$("#dynamicBtn").css("visibility","visible");
       					$("#closeBtn").css("visibility","hidden");
          				 
       					var msg=$("#msg");
       					msg.text(a.successMsg);
       					msg.css("font-weight","bold");
       					msg.css("font-size","20px");
       					msg.css("color","SlateBlue");
       					    
       				}
       				else if(a.answer=="fail"){

       					input.disabled = false;
       					var msg=$("#msg");
       					msg.text(a.failMsg);
       					msg.css("font-weight","bold");
       					msg.css("font-size","20px");
       					msg.css("color","Tomato");
       				}
       			},
       			error : function(e){
       				//alert("에에에엥??? error");
       			},
       			beforeSend : function(){
       				//alert("준비됐다..***.");
       			},
      			complete : function(){
      				//alert("이거되면 요청된거라는데...**..ㅠㅠ");
      			}
              
            });/* ajax end */
           
            $('#myModal').modal('show');
            
            return false;
        });
    }
    $(function() {
    	makeModalMsg();
      });
    </script>
</head>
 <body class="register-page">
    <div class="register-box">
      <div class="register-logo">
        <a href="http://localhost:8089/socialPro/pages/realPage/SPWelcome.html"><b>Admin</b>LTE</a>
      </div>

      <div class="register-box-body">
        <p class="login-box-msg">Register a new membership</p>
        <form action="../../index.html" method="post">
          <div class="form-group has-feedback">
            <input id="nickName" type="text" class="form-control" placeholder="nick name" required/>
            <span class="glyphicon glyphicon-user form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input id="email" type="email" class="form-control" placeholder="Email" required/>
            <span class="glyphicon glyphicon-envelope form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input id="pw" type="password" class="form-control" placeholder="Password" required/>
            <span class="glyphicon glyphicon-lock form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input type="password" class="form-control" placeholder="Retype password" required/>
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input id="school" type="univercity" class="form-control" placeholder="school" required/>
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
          </div>
          <div class="form-group has-feedback">
            <input id="entranceYear" type="entranceYear" class="form-control" placeholder="Entrance Year" required/>
            <span class="glyphicon glyphicon-log-in form-control-feedback"></span>
          </div>
          <div class="row">
            <div class="col-xs-8">
              <div class="checkbox icheck">
                <label>
                  <input type="checkbox"> I agree to the <a href="#">terms</a>
                </label>
              </div>
            </div><!-- /.col -->
            <div class="col-xs-4">
              <!-- <button id="register" type="button" class="btn btn-primary btn-block btn-flat">Register</button> -->
              <button id="register" type="button" class="btn btn-primary btn-block btn-flat" data-toggle="modal" data-target="#myModal">Register</button>
            </div><!-- /.col -->
            
            <!-- Modal start-->
			<div id="myModal" class="modal fade" role="dialog">
			  <div class="modal-dialog">
			
			    <!-- Modal content start-->
			    <div class="modal-content">
			      <div class="modal-header">
			        <button id="closeBtn" type="button" class="close" data-dismiss="modal">&times;</button>
			        <h4 class="modal-title">Message</h4>
			      </div>
			      <div class="modal-body">
			        <p id="msg">Some text in the modal.</p>
			      </div>
			      <div class="modal-footer">
			      	<a href="http://localhost:8089/socialPro/pages/realPage/SPWelcome.html"><button id="dynamicBtn" type="button" class="btn btn-default" style="visibility:hidden">OK</button></a>			        
			      </div>
			    </div>
			    <!-- Modal content end-->
			
			  </div>
			</div>
			<!-- Modal end-->
            
          </div>
        </form>

        <a href="login.html" class="text-center">I already have a membership</a>
      </div><!-- /.form-box -->
    </div><!-- /.register-box -->

    
    <!-- Bootstrap 3.3.2 JS -->
    <script src="../../bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- iCheck -->
    <script src="../../plugins/iCheck/icheck.min.js" type="text/javascript"></script>
    <script>
    $(function () {
        $('input').iCheck({
          checkboxClass: 'icheckbox_square-blue',
          radioClass: 'iradio_square-blue',
          increaseArea: '20%' // optional
        });
      });
    </script>
  </body>
</html>