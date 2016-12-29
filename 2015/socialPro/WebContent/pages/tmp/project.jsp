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
<body class="skin-blue sidebar-mini">
    <!-- Site wrapper -->
    <div class="wrapper">

      <header class="main-header">
        <!-- Logo -->
        <a href="http://localhost:8089/socialPro/pages/realPage/memberWelcom.html" class="logo">
          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>A</b>LT</span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>Admin</b>LTE</span>
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
                    <ul class="menu">
                      <li><!-- start message -->
                        <a href="#">
                          <div class="pull-left">
                            <img src="../../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                          </div>
                          <h4>
                            Support Team
                            <small><i class="fa fa-clock-o"></i> 5 mins</small>
                          </h4>
                          <p>Why not buy a new awesome theme?</p>
                        </a>
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
                  <li class="header">You have 10 notifications</li>
                  <li>
                    <!-- inner menu: contains the actual data -->
                    <ul class="menu">
                      <li>
                        <a href="#">
                          <i class="fa fa-users text-aqua"></i> 5 new members joined today
                        </a>
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
      <div class="content-wrapper" ng-module="app" ng-controller="ListCtrl">
        <!-- Content Header (Page header) -->
        <section class="content-header">
        	
        	<!-- select storage dropdown list start  -->
	        <div>
	        <div class="dropdown dropdown-scroll" >
			    <button class="btn btn-default dropdown-toggle" type="button" id="Team" data-toggle="dropdown" style="font-weight: bold; font-size: large;"> <span class="caret"></span>
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
			</div>
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
          		<div style="margin-top:0px;margin-bottom:20px;display: inline;font-size: 25px;font-weight: bold;">
	             	<span><a href="#" id="teamName"></a>
	             	</span>
	             	<span> / </span>
	             	<span><a href="#" class="projectName"></a></span>
	             </div>
             </div>
		  </div><!-- /.box -->
		  <!-- head end -->
			  
            <div class="box-body">
<!-- **********************************************Search Result Start -->

				<!-- Custom Tabs -->
              <div class="nav-tabs-custom">
                <ul class="nav nav-tabs">
                  <li class="active"><a href="#project" data-toggle="tab" class="projectName"></a></li>
                  <li><a href="#commit" data-toggle="tab">Commit</a></li>
                  <li><a href="#contributor" data-toggle="tab">Contributor</a></li>
                  <li><a href="#mergeRequest" data-toggle="tab">Merge Request</a></li>
                </ul>
                <div class="tab-content">
                  <div class="tab-pane active" id="project">
                  	<div class="box box-default collapsed-box">
                  		<div class="box-header with-border">
                  			<div style="margin-top:0px;margin-bottom:20px;display: inline;font-size: 25px;font-weight: bold;">
				             	<span style="float:left;">
				             		<!-- select storage dropdown list start  -->
							        <div>
							        <div class="dropdown dropdown-scroll" >
									    <button class="btn btn-default dropdown-toggle" type="button" id="folderN" data-toggle="dropdown" style="font-weight: bold; font-size: large;"> <span id="currentPoint"></span>
									    </button>
									    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" >
									        <li role="presentation">
									            <div class="input-group input-group-sm search-control">
									            	<span class="input-group-addon">
									                    <span class="glyphicon glyphicon-search"></span>
								                    </span>
									                <input type="text" class="form-control" placeholder="team name" ng-model="query"></input>
									            </div>
									        </li>
									        <!-- <li role="presentation"><a href="http://localhost:8089/socialPro/pages/realPage/memberWelcom.jsp" id="projectName"></a></li> -->
									        <li role="presentation" class="divider"></li>
									        <li role="presentation" ng-repeat='folder in folders | filter:query'>
									        	<a ng-click="selectParentFolder(folder)"> {{folder.name}} </a>
									        </li>
									    </ul>
									</div>
									</div>
<!-- ********************************************** select storage dropdown list end  -->
				             	</span>
				             	<span style="float:left;">&nbsp;&nbsp;/&nbsp;&nbsp;</span>
				             	<span style="float:left;">
				             		<div class="btn-group">
				                      <button type="button" class="btn btn-success">Action</button>
				                      <button type="button" class="btn btn-success dropdown-toggle" data-toggle="dropdown">
				                        <span class="caret"></span>
				                        <span class="sr-only">Toggle Dropdown</span>
				                      </button>
				                      <ul class="dropdown-menu" role="menu">
				                        <li><a href="createFolderForm.html">newFolder</a></li>
				                        <li><a href="createFileForm.html">newFile</a></li>
				                        <li><a href="#">upLoad</a></li>
				                        <li><a href="#">downLoad</a></li>
				                      </ul>
				                    </div>
				             	</span>
				            </div>
                  			<div class="box-tools pull-right">
                  				<button class="btn btn-box-tool" data-widget="collapse"><i class="fa fa-plus"></i></button>
               				</div><!-- /.box-tools -->
           				</div><!-- /.box-header -->
          				<div class="box-body">
          					<div class="input-group margin" style="width:20%">
			                    <input type="text" class="form-control">
			                    <span class="input-group-btn">
			                      <button class="btn btn-info btn-flat" type="button">search</button>
			                    </span>
			                 </div>
		                </div><!-- /.box-body -->
	                </div><!-- /.box -->
                   
                    
                    <b>&nbsp;</b>
                    <table id="projectList" class="table table-hover" >
					  <tr>
					  	<th></th>
					    <th>Name</th>
					    <th>commit description</th>
					    <th>commit date</th>
					    <th></th>
					  </tr>
					  <tr ng-click='goParentFolder()'>
					  	<td>..</td>
					    <td></td>
					    <td></td>
					    <td></td>
					    <td></td>
					  </tr>
					  <tr ng-repeat='row1 in rows1' ng-click='tbRowClick(row1)'>
					  	<td></td>
					    <td>{{row1.name}}</td>
					    <td>{{row1.description}}</td>
					    <td>{{row1.date}}</td>
					    <td>
					    	<a ng-click="removeRow(row1)"  class="btn btn-default btn-sm">
								<i class="fa fa-fw fa-trash"></i>
							</a>
					    </td>
					  </tr>
					</table>
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="commit">
                    <b>&nbsp;</b>
                    
	              <ul class="timeline">
	                <!-- timeline time label -->
	                <li class="time-label">
	                  <span class="bg-red">
	                    10 Feb. 2014
	                  </span>
	                </li>
	                <!-- /.timeline-label -->
	                <!-- timeline item -->
	                <li>
	                  <i class="fa fa-envelope bg-blue"></i>
	                  <div class="timeline-item">
	                    <span class="time"><i class="fa fa-clock-o"></i> 12:05</span>
	                    <h3 class="timeline-header"><a href="#">Support Team</a> sent you an email</h3>
	                    <div class="timeline-body">
	                      Etsy doostang zoodles disqus groupon greplin oooj voxy zoodles,
	                      weebly ning heekya handango imeem plugg dopplr jibjab, movity
	                      jajah plickers sifteo edmodo ifttt zimbra. Babblely odeo kaboodle
	                      quora plaxo ideeli hulu weebly balihoo...
	                    </div>
	                    <div class="timeline-footer">
	                      <a class="btn btn-primary btn-xs">Read more</a>
	                      <a class="btn btn-danger btn-xs">Delete</a>
	                    </div>
	                  </div>
	                </li>
	                <!-- END timeline item -->
	                <!-- timeline item -->
	                <li>
	                  <i class="fa fa-user bg-aqua"></i>
	                  <div class="timeline-item">
	                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
	                    <h3 class="timeline-header no-border"><a href="#">Sarah Young</a> accepted your friend request</h3>
	                  </div>
	                </li>
	                <!-- END timeline item -->
	                <!-- timeline item -->
	                <li>
	                  <i class="fa fa-comments bg-yellow"></i>
	                  <div class="timeline-item">
	                    <span class="time"><i class="fa fa-clock-o"></i> 27 mins ago</span>
	                    <h3 class="timeline-header"><a href="#">Jay White</a> commented on your post</h3>
	                    <div class="timeline-body">
	                      Take me to your leader!
	                      Switzerland is small and neutral!
	                      We are more like Germany, ambitious and misunderstood!
	                    </div>
	                    <div class="timeline-footer">
	                      <a class="btn btn-warning btn-flat btn-xs">View comment</a>
	                    </div>
	                  </div>
	                </li>
	                <!-- END timeline item -->
	                <!-- timeline time label -->
	                <li class="time-label">
	                  <span class="bg-green">
	                    3 Jan. 2014
	                  </span>
	                </li>
	                <!-- /.timeline-label -->
	                <!-- timeline item -->
	                <li>
	                  <i class="fa fa-camera bg-purple"></i>
	                  <div class="timeline-item">
	                    <span class="time"><i class="fa fa-clock-o"></i> 2 days ago</span>
	                    <h3 class="timeline-header"><a href="#">Mina Lee</a> uploaded new photos</h3>
	                    <div class="timeline-body">
	                      <img src="http://placehold.it/150x100" alt="..." class="margin" />
	                      <img src="http://placehold.it/150x100" alt="..." class="margin" />
	                      <img src="http://placehold.it/150x100" alt="..." class="margin" />
	                      <img src="http://placehold.it/150x100" alt="..." class="margin" />
	                    </div>
	                  </div>
	                </li>
	                <!-- END timeline item -->
	                <!-- timeline item -->
	                <li>
	                  <i class="fa fa-video-camera bg-maroon"></i>
	                  <div class="timeline-item">
	                    <span class="time"><i class="fa fa-clock-o"></i> 5 days ago</span>
	                    <h3 class="timeline-header"><a href="#">Mr. Doe</a> shared a video</h3>
	                    <div class="timeline-body">
	                      <div class="embed-responsive embed-responsive-16by9">
	                        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/tMWkeBIohBs" frameborder="0" allowfullscreen></iframe>
	                      </div>
	                    </div>
	                    <div class="timeline-footer">
	                      <a href="#" class="btn btn-xs bg-maroon">See comments</a>
	                    </div>
	                  </div>
	                </li>
	                <!-- END timeline item -->
	                <li>
	                  <i class="fa fa-clock-o bg-gray"></i>
	                </li>
	              </ul>
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="contributor">
                    <b>&nbsp;</b>
                    <table class="table table-hover">
                    <tr>
                      <th>name</th>
                      <th>email</th>
                      <th>entracne year</th>
                      <th>Tags</th>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                      <td>3</td>
                      <td><span class="label label-success">Approved</span><span class="label label-warning">Pending</span></td>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                      <td>4</td>
                      <td><span class="label label-warning">Pending</span><span class="label label-primary">Approved</span></td>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                      <td>11</td>
                      <td><span class="label label-primary">Approved</span><span class="label label-danger">Denied</span></td>
                    </tr>
                    <tr>
                      <td>TeamName/TaskName</td>
                      <td>Bacon ipsum dolor sit amet salami venison chicken flank fatback doner.</td>
                      <td>17</td>
                      <td><span class="label label-danger">Denied</span><span class="label label-warning">Pending</span><span class="label label-primary">Approved</span></td>
                    </tr>
                  </table>
                  </div><!-- /.tab-pane -->
                  <div class="tab-pane" id="mergeRequest">
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
                </div><!-- /.tab-content -->
              </div><!-- nav-tabs-custom -->
	

<!-- Search Result End -->
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

		//In sessionStorage there is only this Obj with (storageCode,projectCode,memberCode,folderCode)
		var projectInfo = JSON.parse(window.sessionStorage.getItem("projectInfo"));
		alert("hum... : "+projectInfo.projectName);
		
	    phonecatApp.controller('ListCtrl', function ($scope,$http) {
	    	
	    	//1. loadTeam List : memberCode is needed
	    	var obj = new Object();
	    	obj.memberCode=$.cookie("memberCode");
	    	$http.post('../../loadTeam',JSON.stringify(obj)).
	    	  then(function(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    	    alert(response.data);
	    		  $scope.items = response.data;
	    	  }, function(response) {
	    	    // called asynchronously if an error occurs
	    	    // or server returns response with an error status.
	    	  });
	    	
	    	
	    	//2. load folders : folderId, folderName
	    	  var parents = new Object();
	    	  parents.memberCode=projectInfo.projectCode;
	    	  parents.folderCode=projectInfo.folderCode;
	    	  alert("????? : \n"+JSON.stringify(projectInfo));
	    	  alert(JSON.stringify(parents));
	    	  $http.post('../../loadParentFolders',JSON.stringify(parents)).
	    	  then(function(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
	    		  if(response.data.folderRes=="fail"){
	    			  alert(response.data.failMsg);
	    			  $scope.folders=null;
		    	    }else{$scope.folders = response.data.list;} 
	    	    
	    	  }, function(response) {
	    	    // called asynchronously if an error occurs
	    	    // or server returns response with an error status.
	    	  });
		    	  
		        $scope.selectedItem;
		        $scope.selectTeam = function (item) {
		        	 
		            $scope.selectedItem = item.id;
		            alert($scope.selectedItem);
		            $("#Team").text(item.name);
		            project.storageName=item.Name;
		            project.storageId=item.id;
		        };
		        
		    //});
		    
	    	
	    	//3. loadProjectList : memberCode(default) or storageCode(if storage is clicked) is needed
		    var projectList = new Object();
	    	alert(projectInfo.projectCode+"\n"+projectInfo.projectName);
	    	if(projectInfo.folderCode==null){
	    		projectList.memberCode=projectInfo.projectCode;	
	    	}else{
	    		projectList.memberCode=projectInfo.folderCode;
	    	}
	    	$http.post('../../loadFolder',JSON.stringify(projectList)).
	    	  then(function(response) {
	    	    // this callback will be called asynchronously
	    	    // when the response is available
    		  alert(JSON.stringify(response.data.list));
		   		if(response.data.folderRes=="fail"){
		   			if(response.data.fileRes=="fail"){
		   				alert(response.data.failMsg);
			    	    $scope.rows1=null;	
		   			}
		   			else{
		   				$scope.rows1 = response.data.list;
		   			}
	    	    }else{$scope.rows1 = response.data.list;} 
	    	  }, function(response) {
	    	    // called asynchronously if an error occurs
	    	    // or server returns response with an error status.
	    	  });  
		    
	    	  
	        $scope.selectedItem;
	        $scope.selectTeam = function (item) {
	            
	            $scope.selectedItem = item.id;
	          	alert($scope.selectedItem);
	          	$("#Team").text(item.name);
	          	projectInfo.projectCode = item.id;
	          	projectInfo.projectName = item.name;

	    		//goto sevlet for specific Project's childs
	   			window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
	   			alert(JSON.stringify(projectInfo));
               	window.location.href='memberWelcom.jsp';
				alert(row1.id+"\n"+row1.name+"\n"+row1.description+"\n"+row1.share+"\n"+row1.date+"\n"+row1.tags);
	        };
	        
	    	$scope.removeRow = function(row1){	
		    	var id=row1.id;
		    	var index=-1;
		    	var itemsRow = eval( $scope.rows1 );
	    		for( var i = 0; i < itemsRow.length; i++ ) {
	    			if( itemsRow[i].id == id ) {
	    				index = i;
	    				break;
	    			}
	    		}
	    		if( index == -1 ) {
	    			alert( "Something gone wrong" );
	    		}
	    		else {alert(row1.id);$scope.rows1.splice( index, 1 );}		
	    	};
	    	
	    	/* When user clicked ParentFolder have to reload tablelow*/
	    	//set projectCode and folderCode
	    	$scope.selectParentFolder = function(folder){
	    		
	    		if(folder.folderCode==projectInfo.projectCode){
	    			projectInfo.folderCode=null;
	    			projectInfo.folderName=null;
	    		}else{
	    			projectInfo.folderCode=folder.folderCode;
	    			projectInfo.folderName=folder.folderName;
	    		}
	    		window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
	    		window.location.href='project.jsp';
	    		alert(JSON.stringify(projectInfo));
	    		
	    	};
	    	
	    	/* table row click function */
	    	//check is folder or file
	    	//set folderCode, folderName in projectInfo obj which is stored in sessionStorage 
			 $scope.tbRowClick=function(row1){
	    		
				var addItem = new Object();
				
				//json part-(foler start)
				if(row1.type=="folder"){
					projectInfo.folderCode=row1.id;
					projectInfo.folderName=row1.name;
					window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
					
					projectList.folderCode=projectInfo.folderCode;
			   		alert("THis  "+JSON.stringify(projectList));
			    	$http.post('../../loadFolder',JSON.stringify(projectList)).
			    	  then(function(response) {
			    	    // this callback will be called asynchronously
			    	    // when the response is available
			    	    alert(JSON.stringify(response.data.list));
				   		if(response.data.folderRes=="fail"){
				   			if(response.data.fileRes=="fail"){
				   				alert(response.data.failMsg);
					    	    $scope.rows1=null;	
				   			}
				   			else{
				   				$scope.rows1 = response.data.list;
				   				$("#currentPoint").text(projectInfo.folderName);
				   				addItem.id=projectInfo.folderCode;
				   				addItem.name=projectInfo.folderName;
				   				addItem.type="folder";
				   				$scope.folders.push(addItem);
				   			}
			    	    }else{$scope.rows1 = response.data.list;
			    	    	$("#currentPoint").text(projectInfo.folderName);
			   				addItem.id=projectInfo.folderCode;
			   				addItem.name=projectInfo.folderName;
			   				addItem.type="folder";
			   				$scope.folders.push(addItem);
			    	    } 
				   		
			    	  }, function(response) {
			    	    // called asynchronously if an error occurs
			    	    // or server returns response with an error status.
			    	  });
			    	//json part-(foler end)
				}
				
				else if(row1.type=="file"){
					projectInfo.fileName = row1.name;
					projectInfo.fileCode = row1.id;
					
					var fileInfo = new Object();
					fileInfo.fileCode=projectInfo.fileCode;
			   		alert("THis  file : \n"+JSON.stringify(fileInfo));
			   		
					//get FileContent
					$http.post('../../loadFileContent',JSON.stringify(fileInfo)).
			    	  then(function(response) {
			    	    // this callback will be called asynchronously
			    	    // when the response is available
			    	    //alert(JSON.stringify(response.data.list));
				   		if(response.data.answer=="fail"){

				   			alert(response.data.failMsg);
				   			
			    	    }else{
			    	    	alert("THis is OK\n\n"+response.data.fileContent);
			    	    	projectInfo.fileContent = response.data.fileContent;
			    	    	window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
			    	    	window.location.href = "http://localhost:8089/socialPro/pages/realPage/fileContent.jsp";
			    	    	
			    	    } 
				   		
			    	  }, function(response) {
			    	    // called asynchronously if an error occurs
			    	    // or server returns response with an error status.
			    	  });
				}
				
				
			};
	        
			$scope.goParentFolder=function(){
				var n = (eval( $scope.folders)).length;
				var obj = new Object();
				obj.projectCode = projectInfo.projectCode;
				obj.folderCode = projectInfo.folderCode;
				//json part
		    	$http.post('../../loadSiblingFolders',JSON.stringify(obj)).
		    	  then(function(response) {
		    	    // this callback will be called asynchronously
		    	    // when the response is available
		    	    alert(JSON.stringify(response.data.list));
		    	    if(response.data.folderRes.folderAnswer=="parentIsRoot"){
		    	    	alert(response.data.folderRes.folderFailMsg);
		    	    }else if(response.data.folderRes.folderAnswer=="fail"){
			   			if(response.data.fileRes.fileAnswer=="fail"){
			   				alert(response.data.foleRes.fileFailMsg);
				    	    $scope.rows1=null;	
			   			}
			   			else{
			   				$scope.rows1 = response.data.list;
			   				$scope.folders.splice(n-1, 1 );
			   			}
		    	    }else{
		    	    	$scope.rows1 = response.data.list;
		    	    	projectInfo.folderName=response.data.folderInfo.name;
		    	    	projectInfo.folderCode=response.data.folderInfo.id;
		    	    	window.sessionStorage.setItem("projectInfo", JSON.stringify(projectInfo));
		    	    	$scope.folders.splice(n-1, 1 );
		    	    } 
			   		
		    	  }, function(response) {
		    	    // called asynchronously if an error occurs
		    	    // or server returns response with an error status.
		    	  });
		    	 var parents = new Object();
		    	 parents.memberCode=projectInfo.projectCode;
		    	 parents.folderCode=projectInfo.folderCode;
		    	 if(parents.folderCode==null){
		    		 $("#currentPoint").text(parents.projectName);
		    	 }else{
		    		 $("#currentPoint").text(parents.folderName);
		    	 }
		    	 $http.post('../../loadParentFolders',JSON.stringify(parents)).
		    	  then(function(response) {
		    	    // this callback will be called asynchronously
		    	    // when the response is available
		    		  if(response.data.folderRes=="fail"){
		    			  alert(response.data.failMsg);
		    			  $scope.folders=null;
			    	    }else{
			    	    	$scope.folders = response.data.list;
						} 
		    	    
		    	  }, function(response) {
		    	    // called asynchronously if an error occurs
		    	    // or server returns response with an error status.
		    	  });
				
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
	
	
		$(window).load(function(){
	/* for navbar-custom-menu class-make it dynamic start */
			/* 1.insert user nickname */
			$(".userNickName").text($.cookie("nickName"));
			$("#appendEmail").html($.cookie("nickName")+"<small>"+$.cookie("email")+"</small>");
			
			/* 2.get chattingList */
			/* 3.get projectAlarmList */
			/* 4.get worningTaskList */
	/* for navbar-custom-menu class-make it dynamic end */
		
			var projectInfo=JSON.parse(window.sessionStorage.getItem("projectInfo"));
	/* for STORAGE button it dynamic start */
			//$("#Team").text($.cookie("nickName"));
			if(projectInfo.storageName!=null){
				$("#Team").text(projectInfo.storageName);	
			}else {
				$("#Team").text($.cookie("nickName"));
			}
	/* for STORAGE button it dynamic end */
		
	/* for current location */
		if(projectInfo.folderName==null){
			$("#currentPoint").text(projectInfo.projectName);	
		}else{
			$("#currentPoint").text(projectInfo.folderName);
		}
	
	
		
		
	/* listup individual project dynamic start */
	/* listup individual project dynamic end */
	
			/* $("#search_tag").bind("click",searchTag()); */
			$("#search_tag").bind("click",function (){searchTag();});
			$(".projectName").text(projectInfo.projectName);
	  		$("#teamName").text(projectInfo.storageName);
		});
    </script>
    
  </body>
</html>