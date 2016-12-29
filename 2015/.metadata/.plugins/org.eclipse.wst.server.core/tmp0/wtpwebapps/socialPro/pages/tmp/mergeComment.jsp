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
    
    <style>
	.dropdown.dropdown-scroll .dropdown-menu {
    max-height: 300px;
    width: 300px;
    overflow: auto; 
	}
	
	.search-control {
	    padding: 5px 10px;
	}
	
	/*font Awesome http://fontawesome.io*/
@import url(//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css);
/*Comment List styles*/
.comment-list .row {
  margin-bottom: 0px;
}
.comment-list .panel .panel-heading {
  padding: 4px 15px;
  position: absolute;
  border:none;
  /*Panel-heading border radius*/
  border-top-right-radius:0px;
  top: 1px;
}
.comment-list .panel .panel-heading.right {
  border-right-width: 0px;
  /*Panel-heading border radius*/
  border-top-left-radius:0px;
  right: 16px;
}
.comment-list .panel .panel-heading .panel-body {
  padding-top: 6px;
}
.comment-list figcaption {
  /*For wrapping text in thumbnail*/
  word-wrap: break-word;
}
/* Portrait tablets and medium desktops */
@media (min-width: 768px) {
  .comment-list .arrow:after, .comment-list .arrow:before {
    content: "";
    position: absolute;
    width: 0;
    height: 0;
    border-style: solid;
    border-color: transparent;
  }
  .comment-list .panel.arrow.left:after, .comment-list .panel.arrow.left:before {
    border-left: 0;
  }
  /*****Left Arrow*****/
  /*Outline effect style*/
  .comment-list .panel.arrow.left:before {
    left: 0px;
    top: 30px;
    /*Use boarder color of panel*/
    border-right-color: inherit;
    border-width: 16px;
  }
  /*Background color effect*/
  .comment-list .panel.arrow.left:after {
    left: 1px;
    top: 31px;
    /*Change for different outline color*/
    border-right-color: #FFFFFF;
    border-width: 15px;
  }
  /*****Right Arrow*****/
  /*Outline effect style*/
  .comment-list .panel.arrow.right:before {
    right: -16px;
    top: 30px;
    /*Use boarder color of panel*/
    border-left-color: inherit;
    border-width: 16px;
  }
  /*Background color effect*/
  .comment-list .panel.arrow.right:after {
    right: -14px;
    top: 31px;
    /*Change for different outline color*/
    border-left-color: #FFFFFF;
    border-width: 15px;
  }
}
.comment-list .comment-post {
  margin-top: 6px;
}
	</style>
  </head>
  <body class="skin-blue sidebar-mini">
    <!-- Site wrapper -->
    <div class="wrapper">

      <header class="main-header">
        <!-- Logo -->
        <a href="../../index2.html" class="logo">
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
              <!-- Notifications: style can be found in dropdown.less -->
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
              <!-- Tasks: style can be found in dropdown.less -->
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
              <!-- User Account: style can be found in dropdown.less -->
              <li class="dropdown user user-menu">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <img src="../../dist/img/user2-160x160.jpg" class="user-image" alt="User Image" />
                  <span class="hidden-xs">Alexander Pierce</span>
                </a>
                <ul class="dropdown-menu">
                  <!-- User image -->
                  <li class="user-header">
                    <img src="../../dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                    <p>
                      Alexander Pierce - Web Developer
                      <small>Member since Nov. 2012</small>
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
              <p>Alexander Pierce</p>
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
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
	        <div>
	        <div class="dropdown dropdown-scroll" ng-app="app">
			    <button class="btn btn-default dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" style="font-weight: bold; font-size: large;">STORAGE <span class="caret"></span>
			    </button>
			    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1" ng-controller="ListCtrl">
			        <li role="presentation">
			            <div class="input-group input-group-sm search-control">
			            	<span class="input-group-addon">
			                    <span class="glyphicon glyphicon-search"></span>
		                    </span>
			                <input type="text" class="form-control" placeholder="Query" ng-model="query"></input>
			            </div>
			        </li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation"><a href="#">Manage Team</a></li>
			        <li role="presentation"><a href="#">Create Team</a></li>
			        <li role="presentation" class="divider"></li>
			        <li role="presentation" ng-repeat='item in items | filter:query'> <a href="#"> {{item.name}} </a>
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
		             	<span><a href="#">memberNickName</a>
		             	</span>
		             	<span> / </span>
		             	<span><a href="#">projectName</a></span>
		             </div>
	             </div>
			  </div><!-- /.box -->
			  <!-- head end -->
			  
            <div class="box-body">
            <!-- Search Result Start -->
            	
            <!-- Custom Tabs -->
              <div class="nav-tabs-custom">
                
                <!-- Real Merge Comment Start -->
                	 <div class="container">
					  <div class="row">
					    <div class="col-md-8" style="width:100%">
					      <h2 class="page-header">Comments</h2>
					        <section class="comment-list">
					          <!-- First Comment -->
					          <article class="row">
					            <div class="col-md-2 col-sm-2 hidden-xs" style="height: 30%;width: 11%">
					              <figure class="thumbnail">
					                <img class="img-responsive" src="http://www.keita-gaming.com/assets/profile/default-avatar-c5d8ec086224cb6fc4e395f4ba3018c2.jpg" />
					                <figcaption class="text-center">username</figcaption>
					              </figure>
					            </div>
					            <div class="col-md-10 col-sm-10"  style="width: 88%">
					              <div class="panel panel-default arrow left">
					                <div class="panel-body">
					                  <header class="text-left">
					                    <div class="comment-user"><i class="fa fa-user"></i> That Guy</div>
					                    <time class="comment-date" datetime="16-12-2014 01:05"><i class="fa fa-clock-o"></i> Dec 16, 2014</time>&nbsp;&nbsp;<a href="#"><strong><i class="fa fa-fw fa-file-code-o"></i></strong></a>
					                  </header>
					                  <div class="comment-post">
					                    <p>
					                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
					                    </p>
					                  </div>
					                  <p class="text-right"><a href="#" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;<a href="#" class="btn btn-default btn-sm"><i class="fa  fa-trash-o"></i></a>&nbsp;&nbsp;<a href="#" class="btn btn-default btn-sm"><i class="fa fa-reply"></i> reply</a></p>
					                </div>
					              </div>
					            </div>
					            
					            <!-- timeline start -->
					            	<ul class="timeline" style="width: 88%;float: right;">
				                <li></li> <li></li>
				                <!-- timeline item -->
				                <li>
				                  <i class="fa bg-purple"></i>
				                  <div class="timeline-item">
				                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
				                    <h3 class="timeline-header no-border"><a href="#">fileName</a></h3>
				                  </div>
				                </li>
				                <!-- END timeline item -->
				                <!-- timeline item -->
				                <li>
				                  <i class="fa bg-purple"></i>
				                  <div class="timeline-item">
				                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
				                    <h3 class="timeline-header no-border"><a href="#">fileName</a></h3>
				                  </div>
				                </li>
				                <!-- END timeline item -->
				              </ul>
				                <!-- timeline end -->
				                
					          </article>
					          
					          <!-- Second Comment -->
					          <article class="row">
					            <div class="col-md-2 col-sm-2 hidden-xs" style="height: 30%;width: 11%">
					              <figure class="thumbnail">
					                <img class="img-responsive" src="http://www.keita-gaming.com/assets/profile/default-avatar-c5d8ec086224cb6fc4e395f4ba3018c2.jpg" />
					                <figcaption class="text-center">username</figcaption>
					              </figure>
					            </div>
					            <div class="col-md-10 col-sm-10" style="width: 88%">
					              <div class="panel panel-default arrow left">
					                <div class="panel-body">
					                  <header class="text-left">
					                    <div class="comment-user"><i class="fa fa-user"></i> That Guy</div>
					                    <time class="comment-date" datetime="16-12-2014 01:05"><i class="fa fa-clock-o"></i> Dec 16, 2014</time>&nbsp;&nbsp;<a href="#"><strong><i class="fa fa-fw fa-file-code-o"></i></strong></a>
					                  </header>
					                  <div class="comment-post">
					                    <p>
					                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
					                    </p>
					                  </div>
					                  <p class="text-right"><a href="#" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;<a href="#" class="btn btn-default btn-sm"><i class="fa  fa-trash-o"></i></a>&nbsp;&nbsp;<a href="#" class="btn btn-default btn-sm"><i class="fa fa-reply"></i> reply</a></p>
					                </div>
					              </div>
					            </div>
					            
					            	<!-- timeline start -->
					            	<ul class="timeline" style="width: 88%;float: right;">
				                <li></li> <li></li>
				                <!-- timeline item -->
				                <li>
				                  <i class="fa bg-purple"></i>
				                  <div class="timeline-item">
				                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
				                    <h3 class="timeline-header no-border"><a href="#">fileName</a></h3>
				                  </div>
				                </li>
				                <!-- END timeline item -->
				                <!-- timeline item -->
				                <li>
				                  <i class="fa bg-purple"></i>
				                  <div class="timeline-item">
				                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
				                    <h3 class="timeline-header no-border"><a href="#">fileName</a></h3>
				                  </div>
				                </li>
				                <!-- END timeline item -->
				              </ul>
				                <!-- timeline end -->
					            
					          </article>
					          
					          <!-- Third Comment -->
					          <article class="row">
					            <div class="col-md-2 col-sm-2 hidden-xs" style="height: 30%;width: 11%">
					              <figure class="thumbnail">
					                <img class="img-responsive" src="http://www.keita-gaming.com/assets/profile/default-avatar-c5d8ec086224cb6fc4e395f4ba3018c2.jpg" />
					                <figcaption class="text-center">username</figcaption>
					              </figure>
					            </div>
					            <div class="col-md-10 col-sm-10" style="width: 88%">
					              <div class="panel panel-default arrow left">
					                <div class="panel-body">
					                  <header class="text-left">
					                    <div class="comment-user"><i class="fa fa-user"></i> That Guy</div>
					                    <time class="comment-date" datetime="16-12-2014 01:05"><i class="fa fa-clock-o"></i> Dec 16, 2014</time>&nbsp;&nbsp;<a href="#"><strong><i class="fa fa-fw fa-file-code-o"></i></strong></a>
					                  </header>
					                  <div class="comment-post">
					                    <p>
					                      Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
					                    </p>
					                  </div>
					                  <p class="text-right"><a href="#" class="btn btn-default btn-sm"><i class="fa fa-pencil"></i></a>&nbsp;&nbsp;<a href="#" class="btn btn-default btn-sm"><i class="fa  fa-trash-o"></i></a>&nbsp;&nbsp;<a href="#" class="btn btn-default btn-sm"><i class="fa fa-reply"></i> reply</a></p>
					                </div>
					              </div>
					            </div>
					            
					            <!-- timeline start -->
					            	<ul class="timeline" style="width: 88%;float: right;">
				                <li></li> <li></li>
				                <!-- timeline item -->
				                <li>
				                  <i class="fa bg-purple"></i>
				                  <div class="timeline-item">
				                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
				                    <h3 class="timeline-header no-border"><a href="#">fileName</a></h3>
				                  </div>
				                </li>
				                <!-- END timeline item -->
				                <!-- timeline item -->
				                <li>
				                  <i class="fa bg-purple"></i>
				                  <div class="timeline-item">
				                    <span class="time"><i class="fa fa-clock-o"></i> 5 mins ago</span>
				                    <h3 class="timeline-header no-border"><a href="#">fileName</a></h3>
				                  </div>
				                </li>
				                <!-- END timeline item -->
				              </ul>
				                <!-- timeline end -->
					            
					          </article>
					          
					        </section>
					    </div>
					  </div>
					</div>
                	 
                <!-- Real Merge Comment End -->
               
              </div><!-- nav-tabs-custom -->	
            	
            	<!-- comment form start -->
            	 <div class="container">
				  <div class="row">
				    <div class="col-md-8" style="width:100%">
              <section class="comment-list">
		          <!-- First Comment -->
		          <article class="row">
		            <div class="col-md-2 col-sm-2 hidden-xs" style="height: 30%;width: 11%">
		              <figure class="thumbnail">
		                <img class="img-responsive" src="http://www.keita-gaming.com/assets/profile/default-avatar-c5d8ec086224cb6fc4e395f4ba3018c2.jpg" />
		                <figcaption class="text-center">username</figcaption>
		              </figure>
		            </div>
		            <div class="col-md-10 col-sm-10"  style="width: 88%">
		              <div class="panel panel-default arrow left">
		                <div class="panel-body">
		                  <div class="comment-post">
		                  	<div class="box-body pad">
			                  <form>
			                    <textarea class="textarea" placeholder="Place some text here" style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
			                  </form>
			                </div>
		                  </div>
		                  <p class="text-right"><a href="#" class="btn btn-default" style="font-weight: bold;background-color: DeepPink;">stop merge</a>&nbsp;&nbsp;<a href="#" class="btn btn-default" style="font-weight: bold;">comment</a></p>
		                </div>
		              </div>
		            </div>
		            </article>
		       </section>
		       </div></div></div>
            	<!-- comment form end -->
            	
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
	
    <!-- jQuery 2.1.4 -->
    <script src="../../plugins/jQuery/jQuery-2.1.4.min.js" type="text/javascript"></script>
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
    
    <!-- script for table search -->
    <script src="../../plugins/datatables/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="../../plugins/datatables/dataTables.bootstrap.min.js" type="text/javascript"></script>
    
    
    <script>
    var phonecatApp = angular.module('app', []);

    phonecatApp.controller('ListCtrl', function ($scope) {
        $scope.items = [{
            'name': 'Item 1',
            'id':'id1'
        }, {
            'name': 'Item 2'
        }, {
            'name': 'Account 3'
        }, {
            'name': 'Account 4'
        }, {
            'name': 'Item 5'
        }, {
            'name': 'Item 6'
        }, {
            'name': 'User 7'
        }, {
            'name': 'User 8'
        }, {
            'name': 'Item 9'
        }, {
            'name': 'Item 10'
        }, {
            'name': 'Item 11'
        }, {
            'name': 'Item 12'
        }, {
            'name': 'Item 13'
        }, {
            'name': 'Item 14'
        }, {
            'name': 'User 15'
        }, {
            'name': 'User 16'
        }, {
            'name': 'Person 17'
        }, {
            'name': 'Person 18'
        }, {
            'name': 'Person 19'
        }, {
            'name': 'Item 20'
        }, ];
    });

    // jQuery
    $('.dropdown-menu').find('input').click(function (e) {
        e.stopPropagation();
    });
    </script>
    
    <script type="text/javascript">
      $(function () {
    	  $("#example1").DataTable();
      });
    </script>
    
   
    
  </body>
</html>
