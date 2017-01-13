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
    
    <!-- for codeMirror.. -->
    <link rel="stylesheet" href="../../lib/codemirror.css">
	<link rel="stylesheet" href="../../addon/hint/show-hint.css">
	<script src="../../lib/codemirror.js"></script>
	<script src="../../addon/hint/show-hint.js"></script>
	<script src="../../addon/hint/javascript-hint.js"></script>
	<script src="../../mode/javascript/javascript.js"></script>
    
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
      <div class="content-wrapper" ng-module="app" ng-controller="ListCtrl">
        <!-- Content Header (Page header) -->
        <section class="content-header">
        	
        	<!-- select storage dropdown list start  -->
	        <div>
	        <div class="dropdown dropdown-scroll" >
			    <button class="btn btn-default dropdown-toggle" type="button" id="Team" data-toggle="dropdown" style="font-weight: bold; font-size: large;"> <span class="caret"></span>
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
	          	<!-- select storage dropdown list start  -->
		        <div style="float:left;">
		        <div class="dropdown dropdown-scroll" style="float:left;">
				    <button class="btn btn-default dropdown-toggle" type="button" id="folderN" data-toggle="dropdown" style="font-weight: bold; font-size: large;"> <span class="caret"></span>
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
				        <li role="presentation"><a href="http://localhost:8089/socialPro/pages/realPage/memberWelcom.jsp" class="userNickName"></a></li>
				        <li role="presentation" class="divider"></li>
				        <li role="presentation" ng-repeat='folder in folders | filter:query'>
				        	<a ng-click="selectFolder(folder)"> {{folder.name}} </a>
				        </li>
				    </ul>
				</div>
				<span style="font-size: 25px;font-weight: bold;">&nbsp;&nbsp;/&nbsp;&nbsp;</span>
				<!-- font-weight: bold; font-size: large; -->
		        <span style="font-size: 25px;font-weight: bold;"><a id="fileName" href="#">projectName</a></span>
				</div>
			  <!-- select storage dropdown list end  -->
             </div>
		  </div><!-- /.box -->
		  <!-- head end -->
			  
            <div class="box-body">
<!-- -------------------Search Result Start-------------------------- -->
            	<!-- menuBtn start -->
            	<div class="btn-group" style="float: right;">
                <button type="button" class="btn btn-info">History</button>
                <button type="button" class="btn btn-info">Duty</button>
                <button id="editAble" type="button" class="btn btn-info">Edit</button>
                <button type="button" class="btn btn-info">Trash</button>
              </div></br></br></br>
            	<!-- menuBtn end -->
            	
<!-- realFile Content start -->
            	
				<article>
				<form><textarea id="code" name="code">
				</textarea></form>
				<!-- 
				<p>Press <strong>ctrl-space</strong> to activate autocompletion. Built
				on top of the <a href="../../doc/manual.html#addon_show-hint"><code>show-hint</code></a>
				and <a href="../../doc/manual.html#addon_javascript-hint"><code>javascript-hint</code></a>
				addons.</p> -->
				
				    <script>
				      var editor = CodeMirror.fromTextArea(document.getElementById("code"), {
				        lineNumbers: true,
				        extraKeys: {"Ctrl-Space": "autocomplete"},
				        readOnly: true,
				        mode: {name: "javascript", globalVars: true}
				      });
				    </script>
				  </article>
<!-- realFile Content end -->
            	</br>
				<div style="float:right;"><a id="saveFile" href="#" class="btn btn-block btn-primary" style="font-weight: bold;">save</a></p></div>
            	
<!-- -------------------Search Result End-------------------------- -->
            
            </div><!-- /.box-body -->
            <div class="box-footer">
              <!-- Footer -->
              <div class="box">
            <div class="box-header with-border">
              <h3 class="box-title">Commit</h3>
              <!-- <div class="box-tools pull-right">
                <button class="btn btn-box-tool" data-widget="collapse" data-toggle="tooltip" title="Collapse"><i class="fa fa-minus"></i></button>
                <button class="btn btn-box-tool" id="saveFile"><i class="fa fa-save"></i> Save</button>
              </div> -->
            </div>
            <div class="box-body">
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
			                   <input id="commitTitle" type="text" class="form-control" placeholder="commit title">
			                  	</br>
			                    <textarea id="commitContent" class="textarea" placeholder="Place some text here" style="width: 100%; height: 200px; font-size: 14px; line-height: 18px; border: 1px solid #dddddd; padding: 10px;"></textarea>
			                    <script>
			                    var oldVal = $("#commitContent").val();
			                    $("#commitContent").on("change keyup paste", function() {
			                        var currentVal = $(this).val();
			                        if(currentVal == oldVal) {
			                            return; //check to prevent multiple simultaneous triggers
			                        }

			                        oldVal = currentVal;
			                        //action to be performed on textarea changed
			                        //alert("changed!");
			                    });
			                    </script>
			                  </form>
			                </div>
		                  </div>
		                  <p class="text-right"><a href="#" id="cancel" class="btn btn-default" style="font-weight: bold;background-color: DeepPink;">Cancel</a>&nbsp;&nbsp;<a href="#" id="commitChangesBtn" class="btn btn-default" style="font-weight: bold;">Commit Changes</a></p>
		                </div>
		              </div>
		            </div>
		            </article>
		       </section>
		       </div></div></div>
            	<!-- comment form end -->
            </div><!-- /.box-body -->
            <div class="box-footer">
              Footer
            </div><!-- /.box-footer-->
          </div><!-- /.box -->
             
              	
              
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
    
    <!-- script for table search -->
    <script src="../../plugins/datatables/jquery.dataTables.min.js" type="text/javascript"></script>
    <script src="../../plugins/datatables/dataTables.bootstrap.min.js" type="text/javascript"></script>
    
    <script src="../../assets/angular.ng-modules.js"></script>
    
    <script> 

    /* when STORAGE button is clicked start */
	/* get storageList */
		/* teamListUp */
		var phonecatApp = angular.module('app', []);
		
    	//In sessionStorage there is only this Obj with (storageCode,projectCode,memberCode,folderCode)
    	var projectInfo = JSON.parse(window.sessionStorage.getItem("projectInfo"));
		//1. set StorageInfo
    	if(projectInfo==null){
    		//if storagecode is null default value is membeCode 
    		projectInfo.storageCode = $.cookie("memberCode");
    		projectInfo.storageName = $.cookie("nickName");
    	}
	    
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
    	  var project = new Object();
    	  project.memberCode=projectInfo.projectCode;
    	  project.folderCode=projectInfo.folderCode;
    	  alert("????? : \n"+JSON.stringify(projectInfo));
    	  alert(JSON.stringify(project));
    	  $http.post('../../loadParentFolders',JSON.stringify(project)).
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
	        
	    });
	    
	    
	    // jQuery
	    $('.dropdown-menu').find('input').click(function (e) {
	        e.stopPropagation();
	        
	    });
	    
	/* when STORAGE button is clicked end */
		function searchTag(){
			alert($("#tagValues").tagsinput('items'));
		}
	
		function saveFile(){
			//send folderCode, name
			alert(editor.getValue()+"\n" +$("#fileName").val());
			
			var fileInfo = new Object();
			fileInfo.memberCode = $.cookie("memberCode");
			fileInfo.ownerCode = projectInfo.storageCode;
			fileInfo.projectCode  = projectInfo.projectCode;
			fileInfo.folderCode  = projectInfo.folderCode;
			fileInfo.fileCode  = projectInfo.fileCode;
			fileInfo.fileContent  = editor.getValue();
            
            alert(JSON.stringify(fileInfo));
        	
            /* ajax start */
        	$.ajax({ 
        		type : 'POST',
       			data : JSON.stringify(fileInfo),
       			contentType : 'application/json;  charset=UTF-8',
       			url : '../../updateFileContent',
       			dataType:'JSON',
       			success : function(a){
       				alert("여기는왜//.....제이슨이 오긴옴");
       				if(a.answer=="success"){

	       				// back to welcomePage http://localhost:8089/socialPro/pages/realPage/memberWelcom.jsp
	       				 window.location.href = "http://localhost:8089/socialPro/pages/realPage/project.jsp";
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
			
		}
		

/* ------------- save File fucntion start --------------------- */
		function updateFile(){
			//send folderCode, name
			
			var fileInfo = new Object();
			fileInfo.memberCode = $.cookie("memberCode");
			fileInfo.ownerCode = projectInfo.storageCode;
			fileInfo.projectCode  = projectInfo.projectCode;
			fileInfo.folderCode  = projectInfo.folderCode;
			fileInfo.fileCode  = projectInfo.fileCode;
			//fileInfo.fileName  = $("#fileName").val();
			fileInfo.fileContent  = editor.getValue();

            
            alert(JSON.stringify(fileInfo));
        	
            /* ajax start */
        	$.ajax({ 
        		type : 'POST',
       			data : JSON.stringify(fileInfo),
       			contentType : 'application/json;  charset=UTF-8',
       			url : '../../updateFileContent',
       			dataType:'JSON',
       			success : function(a){
       				alert("여기는왜//.....제이슨이 오긴옴");
       				if(a.answer=="success"){
	
	       				// back to welcomePage http://localhost:8089/socialPro/pages/realPage/memberWelcom.jsp
	       				 window.location.href = "http://localhost:8089/socialPro/pages/realPage/project.jsp";
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
			
		}
/* ------------- save File fucntion end --------------------- */	
	
/* ------------- save Commit File fucntion start --------------------- */
		function updateFileCommit(){
			//send folderCode, name
			alert("commit content : "+$("#commitContent").text());
			
			var fileInfo = new Object();
			fileInfo.memberCode = $.cookie("memberCode");
			fileInfo.ownerCode = projectInfo.storageCode;
			fileInfo.projectCode  = projectInfo.projectCode;
			fileInfo.folderCode  = projectInfo.folderCode;
			fileInfo.fileCode  = projectInfo.fileCode;
			fileInfo.fileContent  = editor.getValue();
			fileInfo.commitTitle = $("#commitTitle").val();
			fileInfo.commitContent  = $("#commitContent").val();
            
            alert(JSON.stringify(fileInfo));
        	
            /* ajax start */
        	$.ajax({ 
        		type : 'POST',
       			data : JSON.stringify(fileInfo),
       			contentType : 'application/json;  charset=UTF-8',
       			url : '../../updateFileContentWithCommit',
       			dataType:'JSON',
       			success : function(a){
       				alert("여기는왜//.....제이슨이 오긴옴");
       				if(a.answer=="success"){
	
	       				// back to welcomePage http://localhost:8089/socialPro/pages/realPage/memberWelcom.jsp
	       				 window.location.href = "http://localhost:8089/socialPro/pages/realPage/project.jsp";
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
			
		}
/* ------------- save Commit File fucntion end --------------------- */	
	
	
/* >>>>>>>>>>>>>>>>>>>>>>>  Window Load Start <<<<<<<<<<<<<<<<<<<<<<<<<<<< */	
		$(window).load(function(){
	/* for navbar-custom-menu class-make it dynamic start */
			/* 1.insert user nickname */
			$(".userNickName").text($.cookie("nickName"));
			$("#appendEmail").html($.cookie("nickName")+"<small>"+$.cookie("email")+"</small>");
			
			/* 2.get chattingList */
			/* 3.get projectAlarmList */
			/* 4.get worningTaskList */
	/* for navbar-custom-menu class-make it dynamic end */
		
	/* for STORAGE button it dynamic start */
			if(projectInfo.storageName!=null){
				$("#Team").text(projectInfo.storageName);	
			}else {
				$("#Team").text($.cookie("nickName"));
			}
			if(projectInfo.folderName!=null){
				$("#folderN").text(projectInfo.folderName);
			}else{
				$("#folderN").text(projectInfo.projectName);
			}
	/* for STORAGE button it dynamic end */
		
		
		
	/* listup individual project dynamic start */
	/* listup individual project dynamic end */

			
	/* set FileName */
			$("#fileName").text(projectInfo.fileName);
	
			editor.setValue(projectInfo.fileContent);
			$("#commitChangesBtn").bind("click",function(){updateFileCommit();});
			$("#saveFile").bind("click",function(){updateFile();});
			$("#editAble").bind("click",function(){editor.setOption("readOnly",false);});
		});
/* >>>>>>>>>>>>>>>>>>>>>>>  Window Load End <<<<<<<<<<<<<<<<<<<<<<<<<<<< */
    </script>
    
    <script type="text/javascript">
      $(function () {
    	  $("#example1").DataTable();
      });
    </script>
    
  </body>
</html>
