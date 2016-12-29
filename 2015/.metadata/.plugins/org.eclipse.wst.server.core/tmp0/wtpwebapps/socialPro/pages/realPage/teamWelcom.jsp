<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AdminLTE 2 | Dashboard</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.4 -->
    <link href="../../bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- Font Awesome Icons -->
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    <!-- Ionicons -->
    <link href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css" rel="stylesheet" type="text/css" />
    <!-- Theme style -->
    <link href="../../dist/css/AdminLTE.min.css" rel="stylesheet" type="text/css" />
    <!-- AdminLTE Skins. Choose a skin from the css/skins
         folder instead of downloading all of them to reduce the load. -->
    <link href="../../dist/css/skins/_all-skins.min.css" rel="stylesheet" type="text/css" />

    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- for tags input -->
    <link rel="stylesheet" href="../../dist/bootstrap-tagsinput/bootstrap-tagsinput.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rainbow/1.2.0/themes/github.css">
    <link rel="stylesheet" href="../../assets/app.css">
    
    <!-- jQuery 2.1.4 -->
    <script src="../../plugins/jQuery/jQuery-2.1.4.min.js" type="text/javascript"></script>
    <!-- Cookie -->
    <script src="../../jquery.cookie.js"></script>
    
    <style>
	.dropdown.dropdown-scroll .dropdown-menu {
    max-height: 300px;
    width: 300px;
    overflow: auto; 
	}
	
	.search-control {
	    padding: 5px 10px;
	}
	</style>
	
  </head>
  <body class="skin-blue sidebar-mini" ng-module="app" ng-controller="ListCtrl">
    <!-- Site wrapper -->
    <div class="wrapper">

      <header class="main-header">
        <!-- Logo -->
        <a href="http://localhost:8089/socialPro/pages/realPage/SPWelcome.html" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>S</b>P</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>SP</b>Ground</span>
        </a>
        <!-- Header Navbar: style can be found in header.less -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </a>
          
<!-- ++++++++++++++++   alarm bar start    ++++++++++++++++  --> 
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <!-- Messages: style can be found in dropdown.less-->
              
              <!-- dynamic ..-->
              <li class="dropdown messages-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-envelope-o"></i>
                  <span class="label label-success">4</span>
                </a>
                <ul class="dropdown-menu">
                  <li class="header">You have 4 messages</li>
                  <li>
                    <!-- inner menu: contains the actual data -->
                    <ul class="menu" ng-repeat='projectAlarm in projectAlarmList' >
                      <li ng-click='projectAlarmClick(projectAlarm)'><!-- start message -->
                        <div class="pull-left">
                          <img src="../../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                        </div>
                        <h4>
                          {{projectAlarm.date}}
                        </h4>
                        <p>{{projectAlarm.title}}</p>
                        <!-- <a href="#">
                          <div class="pull-left">
                            <img src="../../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                          </div>
                          <h4>
                            Support Team
                            <small><i class="fa fa-clock-o"></i> 5 mins</small>
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a> -->
                      </li><!-- end message -->
                    </ul>
                  </li>
                  <li class="footer"><a href="#">See All Messages</a></li>
                </ul>
              </li>
              <!-- dynamic ..-->
              
              <!-- Notifications: style can be found in dropdown.less -->
              <!-- dynamic ..-->
              <li class="dropdown notifications-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-bell-o"></i>
                  <span class="label label-warning">10</span>
                </a>
                <ul class="dropdown-menu">
                  <li class="header" id="joinCount">You have 10 notifications</li>
                  <li>
                    <!-- inner menu: contains the actual data -->
                    <ul class="menu"  ng-repeat='teamAlarm in teamAlarmList' >
                      <li ng-click='teamAlarmClick(teamAlarm)'>
                        <i class="fa fa-users text-aqua" ></i>{{teamAlarm.title}}
                        <!-- <a href="#alarmModal">
                          <i class="fa fa-users text-aqua"></i>{teamAlarm.title}
                        </a> -->
                      </li>
                    </ul>
                  </li>
                  <li class="footer"><a href="#">View all</a></li>
                </ul>
              </li>
              
            <!-- dynamic ..-->
              
              <!-- Tasks: style can be found in dropdown.less -->
              <!-- dynamic ..-->
              <li class="dropdown tasks-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-flag-o"></i>
                  <span class="label label-danger">9</span>
                </a>
                <ul class="dropdown-menu">
                  <li class="header">You have 9 tasks</li>
                  <li>
                    <!-- inner menu: contains the actual data -->
                    <ul class="menu">
                      <li><!-- Task item -->
                        <a href="#">
                          <h3>
                            Design some buttons
                            <small class="pull-right">20%</small>
                          </h3>
                          <div class="progress xs">
                            <div class="progress-bar progress-bar-aqua" style="width: 20%" role="progressbar" aria-valuenow="20" aria-valuemin="0" aria-valuemax="100">
                              <span class="sr-only">20% Complete</span>
                            </div>
                          </div>
                        </a>
                      </li><!-- end task item -->
                    </ul>
                  </li>
                  <li class="footer">
                    <a href="#">View all tasks</a>
                  </li>
                </ul>
              </li>
              <!-- dynamic ..-->
              
              <!-- dynamic ..-->
              <!-- User Account: style can be found in dropdown.less -->
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="../../dist/img/user2-160x160.jpg" class="user-image" alt="User Image" />
                  <span class="userNickName" class="hidden-xs"></span>
                </a>
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    <img src="../../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                    <p class="userNickName" id="appendEmail">
                      <small id="email"></small>
                    </p>
                  </li>
                  <!-- Menu Body -->
                  <li class="user-body">
                    <div class="col-xs-4 text-center">
                      <a href="#">Followers</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Sales</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Friends</a>
                    </div>
                  </li>
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="#" class="btn btn-default btn-flat">Profile</a>
                    </div>
                    <div class="pull-right">
                      <a href="#" class="btn btn-default btn-flat">Sign out</a>
                    </div>
                  </li>
                </ul>
              </li>
              <!-- dynamic ..-->
              <!-- Control Sidebar Toggle Button -->
              
              <li>
                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
              </li>
            </ul>
          </div>
          
		<div class="modal fade" role="dialog" id="alarmModal">
		  <div class="modal-dialog">
		    <div class="modal-content">
		      <div class="modal-header">
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
		        <h4 class="modal-title" id="alarmTitle">Modal title</h4>
		      </div>
		      <div class="modal-body">
		        <p id="alarmBody">One fine body&hellip;</p>
		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary" id="saveChange">Save changes</button>
		      </div>
		    </div><!-- /.modal-content -->
		  </div><!-- /.modal-dialog -->
		</div><!-- /.modal -->
     
<!-- ++++++++++++++++   alarm bar end    ++++++++++++++++  -->          
          
        </nav>
      </header>

      <!-- =============================================== -->

      <!-- Left side column. contains the sidebar -->
      <aside class="main-sidebar">
        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">
          <!-- Sidebar user panel -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="../../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
            </div>
            <div class="pull-left info">
              <p class="userNickName"></p>
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>
          <!-- sidebar menu: : style can be found in sidebar.less -->
          <ul class="sidebar-menu">
            <li class="header"></li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-dashboard"></i> 
                <span>Project</span> 
              </a>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-files-o"></i>
                <span>Task</span>
              </a>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-code"></i>
                <span>Code</span>
              </a>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-laptop"></i>
                <span>Board</span>
              </a>
            </li>
            <li class="treeview">
              <a href="#">
                <i class="fa fa-users"></i> 
                <span>Users</span>
              </a>
            </li>
          </ul>
        </section>
        <!-- /.sidebar -->
      </aside>

      <!-- =============================================== -->
      
      
      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper" >
        <!-- Content Header (Page header) -->
        <section class="content-header">
	        
	        <!-- select storage dropdown list start  -->
	        <div style="display: inline;">
	        <span class="dropdown dropdown-scroll" >
			    <button class="btn btn-default dropdown-toggle" type="button"  data-toggle="dropdown" style="font-weight: bold; font-size: large;"><i class="fa fa-home"></i>&nbsp;<span class="Team"></span>
			    </button>
			    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
			        <li role="presentation">
			            <div class="input-group input-group-sm search-control">
			            	<span class="input-group-addon">
			                    <span class="glyphicon glyphicon-search"></span>
		                    </span>
			                <input type="text" class="form-control" placeholder="team name" ng-model="query"></input>
			            </div>
			        </li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation"><a href="#">Manage Team</a></li>
			        <li role="presentation"><a href="createTeamForm.html">Create Team</a></li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation"><a href="http://localhost:8089/socialPro/pages/realPage/memberWelcom.jsp" class="userNickName"></a></li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation" ng-repeat='item in items | filter:query'>
			        	<a ng-click="selectTeam(item)"> {{item.name}} </a>
			        </li>
			    </ul>
			</span>
			
			<span> / </span>
	             	
<!-- /////////////////////////////////////////////////////  branchs start !!!!!!  -->
			<span class="dropdown dropdown-scroll">

	        <!-- <div class="dropdown dropdown-scroll" > -->
			    <button class="btn btn-default dropdown-toggle" type="button" id="teamBranch" data-toggle="dropdown" style="font-weight: bold; font-size: large;"> <span id="currentBranch"></span>
			    </button>
			    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" >
			        <li role="presentation">
			            <div class="input-group input-group-sm search-control">
			            	<span class="input-group-addon">
			                    <span class="glyphicon glyphicon-search"></span>
		                    </span>
			                <input type="text" class="form-control" placeholder="teamMember" ng-model="query"></input>
			            </div>
			        </li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation"><a ng-click="loadSharedProject()"  class="Team"></a></li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation"><a ng-click="loadMemberCopiedProject()"  class="userNickName"></a></li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation" ng-repeat='branch in branches | filter:query'>
			        	<a ng-click="selectBranch(branch)"> {{branch.name}} </a>
			        </li>
			    </ul>
			<!-- </div> -->

			</span>
            	<!-- <span><a href="#" class="projectName"></a></span> -->
	             	
<!-- /////////////////////////////////////////////////////  branchs end !!!!!!  -->
			
			</div>
		  <!-- select storage dropdown list end  -->
          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Home</a></li>
            <li><a href="#">Examples</a></li>
            <li class="active">Blank page</li>
          </ol>
        </section>
        <!-- Main content -->
        <section class="content">
          <!-- Default box -->
          <div class="box">
	         <!-- head start -->
	          <div class="box box-default collapsed-box">
	          	<div class="box-header with-border">
	          		<ul style="padding: 0px;list-style-type: none;">
	          			<li style="float:left;">
	          				<div><input id="tagValues" type="text" value="Amsterdam,Washington,Sydney,Beijing,Cairo" data-role="tagsinput" placeholder="Add tags"></div>
		              	</li>
		              	<li style="float:left;margin-left: 5px;">
		              		<div><button id="search_tag" class="btn btn-block btn-default">Search</button></div>
		              	</li>
	              	</ul>
	          		<div class="box-tools pull-right">
	          			<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
	       			</div><!-- /.box-tools -->
	       		</div><!-- /.box-header -->
	       		<div class="box-body">
		       			<div class="input-group input-group-lg" style="width: 90%;float:left;">
	                    <div class="input-group-btn">
	                      <button type="button" class="btn btn-warning dropdown-toggle" data-toggle="dropdown">Action <span class="fa fa-caret-down"></span></button>
	                      <ul class="dropdown-menu">
	                        <li><a href="#">Action</a></li>
	                        <li><a href="#">Another action</a></li>
	                        <li><a href="#">Something else here</a></li>
	                        <li class="divider"></li>
	                        <li><a href="#">Separated link</a></li>
	                      </ul>
	                    </div><!-- /btn-group -->
	                    <input type="text" class="form-control">
	                  </div><!-- /input-group -->
	                  <button class="btn btn-block btn-default btn-lg" style="width: 8%;float:left;margin-left:5px;text-align: center;">Search</button>
	   			</div><!-- /.box-body -->
			  </div><!-- /.box -->
			  <!-- head end -->
			  
            <div class="box-body">
<!-- ------------------------Search Result Start--------------------------- -->
            	
             <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                  <li class="active"><a href="#tab_1" data-toggle="tab">project list</a></li>
                  <li><a href="#tab_2" data-toggle="tab">merge request</a></li>
                  <li><a href="#inviteCooperator" data-toggle="tab">invite</a></li>
                </ul>
                <div class="tab-content">
                  <div class="tab-pane active" id="tab_1">
                  	<div class="col-md-2">
                  		<a ng-click="createTeamProject()"><button class="btn btn-block btn-success" ><i class="fa fa-fw fa-plus"></i>  new project</button></a>
                  	</div>
                  	<!-- <div class="pull-right">
                  		<button class="btn btn-block btn-success" ><i class="fa fa-fw fa-plus"></i>  new project</button>
                  	</div> -->
                  	</br></br>
                    <table id="projectList" class="table table-hover" >
                      <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Date</th>
                        <th>Share</th>
                        <th>Status</th>
                        <th></th>
                      </tr>
                      <tr ng-repeat='row1 in rows1' ng-click='tbRowClick(row1)'>
                        <td>{{row1.name}}</td>
                        <td>{{row1.description}}</td>
                        <td>{{row1.date}}</td>
                        <td>
                        	<a  href="#" class="btn btn-default btn-sm">
                    			share&nbsp;&nbsp;&nbsp;<span class="badge bg-purple" style="text-align: left;">{{row1.share}}</span>
                  			</a>
                  		</td>
                        <td><span class="label label-success">{{row1.status}}</span></td>
                        <td>
                        	<a ng-click="removeRow(row1)"  class="btn btn-default btn-sm">
                    			<i class="fa fa-fw fa-trash"></i>
                  			</a>
                        </td>
                      </tr>
                    </table>
                  </div><!-- /.tab-pane -->
                  
                  <div class="tab-pane" id="tab_2">
                    <b>&nbsp;</b>
                    <table class="table table-hover">
                    <tr>
                      <th>Title</th>
                      <th>Date</th>
                      <th>Tags</th>
                      <th>Comment</th>
                      <th>Stars</th>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>11-7-2014</td>                      
                      <td><span class="label label-success">Approved</span><span class="label label-warning">Pending</span></td>
                      <td>3</td>
                      <td>3</td>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>11-7-2014</td>
                      <td><span class="label label-warning">Pending</span><span class="label label-primary">Approved</span></td>
                      <td>4</td>
                      <td>4</td>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>11-7-2014</td>
                      <td><span class="label label-primary">Approved</span><span class="label label-danger">Denied</span></td>
                      <td>11</td>
                      <td>11</td>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>11-7-2014</td>
                      <td><span class="label label-danger">Denied</span><span class="label label-warning">Pending</span><span class="label label-primary">Approved</span></td>
                      <td>17</td>
                      <td>17</td>
                    </tr>
                  </table>
                  </div><!-- /.tab-pane -->
                  
                  <div class="tab-pane" id="inviteCooperator">
		                  	
		          <!-- Default box -->
		          <!-- <div class="box"> -->
		           <!--  <div class="box-header with-border" >
		              <h1 style="font-weight: bold;;" id="inviteM"></h1>
		              <p id="msg"></p>
		            </div> -->
		
		
					<form class="form-horizontal">
		                  <div class="box-body">
		                    <div class="form-group">
		                      <label for="inputEmail3" style="width: 17%;" class="col-sm-2 control-label">member</label>
		                      <div class="col-sm-10" style="width: 60%;">
			                        <div class="input-group input-group-sm">
					                    <input type="text" class="form-control" id="memberNick">
					                    <span class="input-group-btn">
					                      <button class="btn btn-info btn-flat" type="button" ng-click="addMem()" >Go!</button>
					                    </span>
					                 </div><!-- /input-group -->
		                      </div>
		                    </div>
		                   </div>
		             </form>
		             </br>
		             <div style="padding-left: 18%;padding-right: 24%;">
		             <table class="table" >
			             <tr ng-repeat='mem in memList'>
			               <td>{{mem.name}}</td>
			               <td></td>
			               <td></td>
			               <td style="text-align: right;">
			               		<a ng-click="delMem(mem)"  class="btn btn-default btn-sm">
			               			<i class="fa fa-fw fa-minus-square-o"></i>
			               		</a>
			               </td>
			             </tr>
			        </table>
		             </div>
		             						
		             
		            <div class="box-footer" style="padding-right: 10%;">
		            	<div style="float:right;"><a ng-click="sendInviteMsg()" href="#" class="btn btn-block btn-primary" style="font-weight: bold;">Invite</a></p></div>
		            </div><!-- /.box-footer-->
		          <!-- </div> --><!-- /.box -->

                  </div><!-- /.tab-pane -->
                </div><!-- /.tab-content -->
              </div><!-- nav-tabs-custom -->
            	
<!-- ------------------------Search Result End--------------------------- -->
            </div><!-- /.box-body -->
            <div class="box-footer">
              Footer
            </div><!-- /.box-footer-->
          </div><!-- /.box -->

        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
     	
      <footer class="main-footer">
        <div class="pull-right hidden-xs">
          <b>Version</b> 2.2.0
        </div>
        <strong>Copyright &copy; 2014-2015 <a href="http://almsaeedstudio.com">Almsaeed Studio</a>.</strong> All rights reserved.
      </footer>
      
      
      

      <!-- Control Sidebar -->
      <aside class="control-sidebar control-sidebar-dark">
        <!-- Create the tabs -->
        <ul class="nav nav-tabs nav-justified control-sidebar-tabs">
          <li><a href="#control-sidebar-home-tab" data-toggle="tab"><i class="fa fa-home"></i></a></li>

          <li><a href="#control-sidebar-settings-tab" data-toggle="tab"><i class="fa fa-gears"></i></a></li>
        </ul>
        <!-- Tab panes -->
        <div class="tab-content">
          <!-- Home tab content -->
          <div class="tab-pane" id="control-sidebar-home-tab">
            <h3 class="control-sidebar-heading">Recent Activity</h3>
            <ul class="control-sidebar-menu">
              <li>
                <a href="javascript::;">
                  <i class="menu-icon fa fa-birthday-cake bg-red"></i>
                  <div class="menu-info">
                    <h4 class="control-sidebar-subheading">Langdon's Birthday</h4>
                    <p>Will be 23 on April 24th</p>
                  </div>
                </a>
              </li>
              <li>
                <a href="javascript::;">
                  <i class="menu-icon fa fa-user bg-yellow"></i>
                  <div class="menu-info">
                    <h4 class="control-sidebar-subheading">Frodo Updated His Profile</h4>
                    <p>New phone +1(800)555-1234</p>
                  </div>
                </a>
              </li>
              <li>
                <a href="javascript::;">
                  <i class="menu-icon fa fa-envelope-o bg-light-blue"></i>
                  <div class="menu-info">
                    <h4 class="control-sidebar-subheading">Nora Joined Mailing List</h4>
                    <p>nora@example.com</p>
                  </div>
                </a>
              </li>
              <li>
                <a href="javascript::;">
                  <i class="menu-icon fa fa-file-code-o bg-green"></i>
                  <div class="menu-info">
                    <h4 class="control-sidebar-subheading">Cron Job 254 Executed</h4>
                    <p>Execution time 5 seconds</p>
                  </div>
                </a>
              </li>
            </ul><!-- /.control-sidebar-menu -->

            <h3 class="control-sidebar-heading">Tasks Progress</h3>
            <ul class="control-sidebar-menu">
              <li>
                <a href="javascript::;">
                  <h4 class="control-sidebar-subheading">
                    Custom Template Design
                    <span class="label label-danger pull-right">70%</span>
                  </h4>
                  <div class="progress progress-xxs">
                    <div class="progress-bar progress-bar-danger" style="width: 70%"></div>
                  </div>
                </a>
              </li>
              <li>
                <a href="javascript::;">
                  <h4 class="control-sidebar-subheading">
                    Update Resume
                    <span class="label label-success pull-right">95%</span>
                  </h4>
                  <div class="progress progress-xxs">
                    <div class="progress-bar progress-bar-success" style="width: 95%"></div>
                  </div>
                </a>
              </li>
              <li>
                <a href="javascript::;">
                  <h4 class="control-sidebar-subheading">
                    Laravel Integration
                    <span class="label label-warning pull-right">50%</span>
                  </h4>
                  <div class="progress progress-xxs">
                    <div class="progress-bar progress-bar-warning" style="width: 50%"></div>
                  </div>
                </a>
              </li>
              <li>
                <a href="javascript::;">
                  <h4 class="control-sidebar-subheading">
                    Back End Framework
                    <span class="label label-primary pull-right">68%</span>
                  </h4>
                  <div class="progress progress-xxs">
                    <div class="progress-bar progress-bar-primary" style="width: 68%"></div>
                  </div>
                </a>
              </li>
            </ul><!-- /.control-sidebar-menu -->

          </div><!-- /.tab-pane -->
          <!-- Stats tab content -->
          <div class="tab-pane" id="control-sidebar-stats-tab">Stats Tab Content</div><!-- /.tab-pane -->
          <!-- Settings tab content -->
          <div class="tab-pane" id="control-sidebar-settings-tab">
            <form method="post">
              <h3 class="control-sidebar-heading">General Settings</h3>
              <div class="form-group">
                <label class="control-sidebar-subheading">
                  Report panel usage
                  <input type="checkbox" class="pull-right" checked />
                </label>
                <p>
                  Some information about this general settings option
                </p>
              </div><!-- /.form-group -->

              <div class="form-group">
                <label class="control-sidebar-subheading">
                  Allow mail redirect
                  <input type="checkbox" class="pull-right" checked />
                </label>
                <p>
                  Other sets of options are available
                </p>
              </div><!-- /.form-group -->

              <div class="form-group">
                <label class="control-sidebar-subheading">
                  Expose author name in posts
                  <input type="checkbox" class="pull-right" checked />
                </label>
                <p>
                  Allow the user to show his name in blog posts
                </p>
              </div><!-- /.form-group -->

              <h3 class="control-sidebar-heading">Chat Settings</h3>

              <div class="form-group">
                <label class="control-sidebar-subheading">
                  Show me as online
                  <input type="checkbox" class="pull-right" checked />
                </label>
              </div><!-- /.form-group -->

              <div class="form-group">
                <label class="control-sidebar-subheading">
                  Turn off notifications
                  <input type="checkbox" class="pull-right" />
                </label>
              </div><!-- /.form-group -->

              <div class="form-group">
                <label class="control-sidebar-subheading">
                  Delete chat history
                  <a href="javascript::;" class="text-red pull-right"><i class="fa fa-trash-o"></i></a>
                </label>
              </div><!-- /.form-group -->
            </form>
          </div><!-- /.tab-pane -->
        </div>
      </aside><!-- /.control-sidebar -->
      <!-- Add the sidebar's background. This div must be placed
           immediately after the control sidebar -->
      <div class="control-sidebar-bg"></div>
    </div><!-- ./wrapper -->
	
   
    <!-- Bootstrap 3.3.2 JS -->
    <script src="../../bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
    <!-- SlimScroll -->
    <script src="../../plugins/slimScroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <!-- FastClick -->
    <script src="../../plugins/fastclick/fastclick.min.js" type="text/javascript"></script>
    <!-- AdminLTE App -->
    <script src="../../dist/js/app.min.js" type="text/javascript"></script>
    <!-- AdminLTE for demo purposes -->
    <script src="../../dist/js/demo.js" type="text/javascript"></script>
  
    
    <!-- for tags -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/typeahead.js/0.10.4/typeahead.bundle.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/angular.js/1.2.20/angular.min.js"></script>
    <script src="../../dist/bootstrap-tagsinput/bootstrap-tagsinput.min.js"></script>
    <script src="../../dist/bootstrap-tagsinput/bootstrap-tagsinput-angular.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rainbow/1.2.0/js/rainbow.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rainbow/1.2.0/js/language/generic.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rainbow/1.2.0/js/language/html.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/rainbow/1.2.0/js/language/javascript.js"></script>
    <script src="../../assets/app.js"></script>
    <script src="../../assets/app_bs3.js"></script>
    
    <!-- dropdown searchform&scollbar -->
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
    
    <!-- <script
	src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.17/angular.min.js"></script>-->
	<script src="../../assets/angular.ng-modules.js"></script> 
    
    <script> 

    /* when STORAGE button is clicked start */
	/* get storageList */
		/* teamListUp */
		var phonecatApp = angular.module('app', []);
		var memberList = new Array();
		//In sessionStorage there is only this Obj with (storageCode,projectCode,memberCode,folderCode)
    	var projectInfo = JSON.parse(window.sessionStorage.getItem("projectInfo"));
		//1. set StorageInfo
    	if(projectInfo.storageCode == null){
			projectInfo.storageCode =projectInfo.memberCode;
	   		projectInfo.storageName = projectInfo.nickName;
	   		projectInfo.projectCode=null;
	   		projectInfo.projectName=null;
	   		projectInfo.folderCodes= new Array();
	   		projectInfo.fileCode=null;
	   		projectInfo.fileName=null;
	   	}
		if(projectInfo.branchCode==null){
			projectInfo.branchCode = projectInfo.storageCode;
			projectInfo.branchName = projectInfo.storageName;
		}
		alert("projectInfo is init!!!!!  --->>>\n\n"+JSON.stringify(projectInfo));
		var tmpProInfo = new Object(); //for request
		
		function readyObjTmp() {
			/* make Object for request!!! */
	    	tmpProInfo.memberCode = projectInfo.memberCode;
			tmpProInfo.nickName = projectInfo.nickName;
			tmpProInfo.storageCode = projectInfo.storageCode;
			tmpProInfo.storageName = projectInfo.storageName;
			tmpProInfo.projectCode = projectInfo.projectCode;
			tmpProInfo.projectName = projectInfo.projectName;
			tmpProInfo.folderCodes = projectInfo.folderCodes;
			tmpProInfo.fileCode = projectInfo.fileCode;
			tmpProInfo.fileName = projectInfo.fileName;
			tmpProInfo.branchCode = projectInfo.branchCode;
			tmpProInfo.branchName = projectInfo.branchName;
		}
	    
	    phonecatApp.controller('ListCtrl', function ($scope,$http) { 
	    
	    	readyObjTmp();
	    	alert("tmpProINfo  : after init  \n\n"+JSON.stringify(tmpProInfo));
	    	
	    	//modal setting
	    	$scope.showModal = false;
	        $scope.toggleModal = function(){
	            $scope.showModal = !$scope.showModal;
	        };
	    	
	    	//1. loadTeam List : memberCode is needed
		    $http.post('../../loadTeam',JSON.stringify(tmpProInfo)).
		  	  then(function(response) {
		  	    alert(response.data);
		  		$scope.items = response.data;
		  	  }, function(response) {
		  		  
		  	  });  
	  	  
	  	
		    //2. loadProjectList : memberCode(default) or storageCode(if storage is clicked) is needed
		  	$http.post('../../loadProject',JSON.stringify(tmpProInfo)).
		  	  then(function(response) {
		  		 if(response.data.answer=="fail"){
			    	 alert(response.data.failMsg);
			    	 $scope.rows1=null;
			    }
			    else{$scope.rows1 = response.data.list;}
		  	  }, function(response) {
		  	   
		  	  });
		  	
		  //3. loadBranchList : memberCode(default) 
	    	$http.post('../../searchTeamMember',JSON.stringify(tmpProInfo)).
	    	  then(function(response) {
	    		  alert(JSON.stringify(response.data.list));
	    		  if(response.data.answer=="fail"){
		  			  alert("searchTeamMember   fail  :  "+JSON.stringify(response.data.failMsg));
		  			  $scope.rows1=null;
		  		  }
	    		  else{$scope.branches = response.data.list;} 
    		  
	    	  }, function(response) {
	    	    
	    	  });

//   -------------------------------------------------------  load is done   ------------------------------------------------------------	    	
	    	
		      $scope.selectedItem;
		      
		      //when table row is clicked the object in sessionStroage must be changed
		      //projectCode must be changed
		      $scope.selectTeam = function (item) {
		      	 
				  $scope.selectedItem = item.id;
		          alert($scope.selectedItem);
		          $("#Team").text(item.name);
		          projectInfo.memberCode = projectInfo.memberCode;
		          projectInfo.storageCode = item.id;
		          projectInfo.storageName = item.name;
		          projectInfo.branchCode = projectInfo.memberCode;
		          projectInfo.branchName = projectInfo.nickName;
		          window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
		          alert("selectTeam  +  loadProejct  :\n\n"+JSON.stringify(projectInfo));
		        
			      	if(item.id==projectInfo.memberCode){
		          		//goto sevlet for specific Project's childs
			   			alert("go to  memberWelcom.jsp  -->>> \n"+JSON.stringify(projectInfo));
			            window.location.href='memberWelcom.jsp';
		          	}
		          /* //reload project List : 
		          $http.post('../../loadProject',JSON.stringify(projectInfo)).
			    	  then(function(response) {
			    	    // this callback will be called asynchronously
			    	    // when the response is available
			    	    //alert(response.data);
			    	    //alert("ans : "+response.data.answer);
			    	    if(response.data.answer=="fail"){
			    	    	 alert(response.data.failMsg);
			    	    	 $scope.rows1=null;
			    	    }
			    	    else{$scope.rows1 = response.data.list;}
			    	  }, function(response) {
			    	    // called asynchronously if an error occurs
			    	    // or server returns response with an error status.
			    	  }); */
			    	  
			    	  else{
		          		//goto sevlet for specific Project's childs
			   			alert("go to  teamWelcom.jsp  -->>> \n"+JSON.stringify(projectInfo));
			            window.location.href='teamWelcom.jsp';
		          	}
		      };
		      
		      $scope.selectHome = function(){
		    	  
		    	  projectInfo.memberCode = projectInfo.memberCode;
		    	  projectInfo.storageCode = projectInfo.memberCode;
		          projectInfo.storageName = projectInfo.nickName;
		          window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
		          $("#Team").text(projectInfo.storageName);
		          projectList.memberCode=projectInfo.storageCode;
		          alert(JSON.stringify(projectInfo));
		    	  
		          alert("go to  memberWelcom.jsp  -->>> \n"+JSON.stringify(projectInfo));
		          window.location.href='memberWelcom.jsp';
		      };
		    
		      $scope.removeRow = function(){
		    	  
		    	  var id=row1.id;
		    	  var index=-1;
		    	  var itemsRow = eval($scope.rows1);
		    	  for(var i=0;i<itemsRow.length;i++){
		    		  if(itemsRow[i].id == id){
		    			  index = i;
		    			  break;
		    		  }
		    	  }
		    	  if(index == -1){
		    		  alert("Somthing gone wrong");
		    	  }
		    	  else{
		    		  //real send request. send project code and memberCode, storageCode!!!
		    		  alert(row1.id);
		    		  
		    		  
		    		  var delInfo = new Object();
		    		  delInfo.memberCode = projectInfo.memberCode;
		    		  delInfo.storageCode = projectInfo.storageCode;
		    		  delInfo.projectCode = id;
		    		  
		    		  alert(JSON.stringify(delInfo));
		    		  
		    		  $http.post('../../dropProject',JSON.stringify(delInfo)).
			    	  then(function(response) {
			    	    // this callback will be called asynchronously when the response is available alert(response.data); alert("ans : "+response.data.answer);
			    	    if(response.data.answer=="fail"){
			    	    	//fail..
			    	    	 alert(response.data.failMsg);
			    	    	 $scope.rows1=null;
			    	    }
			    	    else{
			    	    	//success
			    	    	alert(rows1.name+"is deleted !!");
			    	    	$scope.rows1.splice( index, 1 );
			    	    }
			    	  }, function(response) {
			    	    // called asynchronously if an error occurs
			    	    // or server returns response with an error status.
			    	  }); 
		    	  }
		      };
						  	
		  	
		  	/* table row click function */
		  	//projectCode,projectName must changed!!!
			 $scope.tbRowClick=function(row1){
	  		
		  		projectInfo.projectName = row1.name;
		  		projectInfo.projectCode = row1.id;
		  		projectInfo.folderCodes = new Array(row1.id);
	  		//goto sevlet for specific Project's childs
				window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
				alert("before goto teamProject.html  :  \n"+JSON.stringify(projectInfo));
	          	window.location.href='teamProject.html';
	  			/* if(row1.id==$.cookie("memberCode")){
	  				alert("before goto project.html  :  \n"+JSON.stringify(projectInfo));
		          	window.location.href='project.html';	
	  			}else{
	  				alert("before goto teamProject.html  :  \n"+JSON.stringify(projectInfo));
		          	window.location.href='teamProject.html';	
	  			} */
				
				//alert(row1.id+"\n"+row1.name+"\n"+row1.description+"\n"+row1.share+"\n"+row1.date+"\n"+row1.tags);
			};
			
			$scope.selectBranch=function(branch){
				
				var branchList = eval($scope.branches);
				
				projectInfo.branchName=branch.name;
				projectInfo.branchCode=branch.id;
				
				//init tmpProInfo
	    		readyObjTmp();
	    		alert("selectBranch   >>>>>   loadProject   : \n\n"+JSON.stringify(tmpProInfo));
				//json part
		    	$http.post('../../loadProject',JSON.stringify(tmpProInfo)).
		    	then(function(response) {
		    	    // this callback will be called asynchronously
		    	    // when the response is available
		    	    alert(JSON.stringify(response.data.list));
		    	    if(response.data.answer=="fail"){
		    	    	alert(response.data.failMsg);
		    	    	$scope.rows1=null;
			    	 }else{
			    		$scope.rows1 = response.data.list;
			    		$("#currentBranch").text(projectInfo.branchName);
			    	 }
		    	    
		    	  }, function(response) {
		    	    // called asynchronously if an error occurs
		    	    // or server returns response with an error status.
		    	  });				
			};
			
			$scope.loadMemberCopiedProject = function(){
				projectInfo.branchCode = projectInfo.memberCode;
				projectInfo.branchName = projectInfo.nickName;
				
				//init tmpProInfo
	    		readyObjTmp();
	    		alert("loadMemberCopiedProject   >>>>>   loadProject   : \n\n"+JSON.stringify(tmpProInfo));
				//json part
		    	$http.post('../../loadProject',JSON.stringify(tmpProInfo)).
		    	then(function(response) {
		    	    // this callback will be called asynchronously
		    	    // when the response is available
		    	    alert(JSON.stringify(response.data.list));
		    	    if(response.data.answer=="fail"){
		    	    	alert(response.data.failMsg);
		    	    	$scope.rows1=null;
			    	 }else{
			    		$scope.rows1 = response.data.list;
			    		$("#currentBranch").text(projectInfo.branchName);
			    	 }
		    	    
		    	  }, function(response) {
		    	    // called asynchronously if an error occurs
		    	    // or server returns response with an error status.
		    	  });				
			};
			
			$scope.loadSharedProject = function(){
				projectInfo.branchCode = projectInfo.storageCode;
				projectInfo.branchName = projectInfo.storageName;
				
				//init tmpProInfo
	    		readyObjTmp();
	    		alert("loadSharedProject   >>>>>   loadProject   : \n\n"+JSON.stringify(tmpProInfo));
				//json part
		    	$http.post('../../loadProject',JSON.stringify(tmpProInfo)).
		    	then(function(response) {
		    	    // this callback will be called asynchronously
		    	    // when the response is available
		    	    alert(JSON.stringify(response.data.list));
		    	    $("#currentBranch").text(projectInfo.branchName);
		    	    if(response.data.answer=="fail"){
		    	    	alert(response.data.failMsg);
		    	    	$scope.rows1=null;
			    	 }else{
			    		$scope.rows1 = response.data.list;
			    	 }
		    	    
		    	  }, function(response) {
		    	    // called asynchronously if an error occurs
		    	    // or server returns response with an error status.
		    	  });	
			};
			
			//only team manager can create project
			$scope.createTeamProject = function(){

	    		alert("createTeamProject   >>>>>   searchTeamManager   : \n\n"+JSON.stringify(tmpProInfo));
				//json part
		    	$http.post('../../searchTeamManager',JSON.stringify(tmpProInfo)).
		    	then(function(response) {
		    	    // this callback will be called asynchronously
		    	    // when the response is available
		    	    alert(JSON.stringify(response.data.list));
		    	    if(response.data.answer=="fail"){
		    	    	alert(response.data.failMsg);
			    	 }else{
			    		window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
						alert("before goto createProjectForMemberForm.html  :  \n"+JSON.stringify(projectInfo));
			          	window.location.href='createProjectForMemberForm.html';
			    	 }
		    	    
		    	  }, function(response) {
		    	    // called asynchronously if an error occurs
		    	    // or server returns response with an error status.
		    	  });			
			};
			
			
			//alarmModal
			$scope.teamAlarmClick = function(teamAlarm){
				$("#alarmTitle").text(teamAlarm.title);
				$("#alarmBody").text(teamAlarm.content);
				
				$('#alarmModal').modal('show');
			};
			

			//when member is clicked add mem to listgroup
			$scope.addMem = function(){
				var memNick=$("#memberNick").val();
				var mem = new Object();
				mem.name = memNick;
				
				memberList.push(mem);
				//$scope.memList=JSON.parse(memberList);
				$scope.memList = memberList;
				alert(JSON.stringify(eval($scope.memList)));
			};
			
			$scope.delMem = function(mem){
				
				var memName=mem.name;
		    	var index=-1;
		    	var memList = eval( $scope.memList );
		  		for( var i = 0; i < memList.length; i++ ) {
		  			if( memList[i].name == memName ) {
		  				index = i;
		  				break;
		  			}
		  		}
		  		if( index == -1 ) {
		  			alert("no member!!");
		  		}else {
		  			$scope.memList.splice( index, 1 );
		  		}	
					
			};
			
			$scope.sendInviteMsg = function(){
				alert((eval( $scope.memList )).length+"  sendInviteMsg\n"+JSON.stringify(eval($scope.memList)));
				var list = new Object();  
				//list.memList = JSON.stringify(eval($scope.memList));
				list.memList = eval($scope.memList);
				list.teamCode = JSON.parse(window.sessionStorage.getItem("projectInfo")).storageCode;
				alert(JSON.stringify(list));
				
				 /* ajax start */
	        	$.ajax({ 
	        		type : 'POST',
	       			data : JSON.stringify(list),
	       			contentType : 'application/json;  charset=UTF-8',
	       			url : '../../sendTeamCooperatorMsg',
	       			dataType:'JSON',
	       			success : function(a){
	       				alert("여기는왜//.....제이슨이 오긴옴");
	       				if(a.answer=="success"){
	       					
	           				window.location.href = "http://localhost:8089/socialPro/pages/realPage/teamWelcom.jsp";
	           				
		       				 
	       				}
	       				else if(a.answer=="fail"){
	       					/* input.disabled = false;
	       					var msg=$(".login-box-msg");
	       					msg.text(a.failMsg);
	       					msg.css("font-weight","bold");
	       					msg.css("color","Tomato"); */
	       					//msg.css("font-size","20px");
	       					alert(a.failMsg);
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
			};
			
		});
	    
	   
	 
	    // jQuery
	    $('.dropdown-menu').find('input').click(function (e) {
	        e.stopPropagation();
	        
	    });
	    
	/* when STORAGE button is clicked end */
		function searchTag(){
			alert($("#tagValues").tagsinput('items'));
		}
	
		function saveChange(){
			/* ajax start */
        	$.ajax({ 
        		type : 'POST',
       			data : JSON.stringify(obj),
       			contentType : 'application/json;  charset=UTF-8',
       			url : '../../saveChange',
       			dataType:'JSON',
       			success : function(a){
       				alert("여기는왜//.....제이슨이 오긴옴");
       				if(a.answer=="fail"){
       					$("#alarmBody").text(a.msg);
       				}
       				else {
       					$("#alarmBody").text(a.answer);
       				}
       				$("#saveChange").attr('disabled','disabled');
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
		}
		
		
		$(window).load(function(){
	/* for navbar-custom-menu class-make it dynamic start */
			/* 1.insert user nickname */
			$(".userNickName").text(projectInfo.nickName);
			$("#appendEmail").html(projectInfo.nickName+"<small>"+projectInfo.email+"</small>");
			
			/* 2.get chattingList */
			/* 3.get projectAlarmList */
			/* 4.get worningTaskList */
	/* for navbar-custom-menu class-make it dynamic end */
		
	/* for STORAGE button it dynamic start */
			var p=JSON.parse(window.sessionStorage.getItem("projectInfo"));
			if(p==null){ 
				$(".Team").text(projectInfo.nickName);
				$("#Team").text(projectInfo.nickName);
			}
			else {
				$(".Team").text(p.storageName);
				$("#Team").text(p.storageName);
			}
	/* for STORAGE button it dynamic end */
	/* set currentBranch */
			if(projectInfo.branchName==null){
				$("#currentBranch").text(projectInfo.nickName);	
			}else{
				$("#currentBranch").text(projectInfo.branchName);
			}
		
	/* listup individual project dynamic start */
	/* listup individual project dynamic end */
	
			/* $("#search_tag").bind("click",searchTag()); */
			$("#search_tag").bind("click",function (){searchTag();});
			
			$("#saveChange").bind("click",function(){saveChange();});
			
		});
    </script>
    
  </body>
</html>
