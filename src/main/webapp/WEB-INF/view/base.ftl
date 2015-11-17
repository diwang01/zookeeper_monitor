<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
  <meta name="apple-mobile-web-app-capable" content="yes">
  <title>Zookeeper监控后台</title>
  <meta name="description" content="The Cloudatlas Admin Console">
  <meta name="author" content="Sohu">
  <link href="${base}/resources/stylesheets/bootstrap.min.css" rel="stylesheet">
  <link href="${base}/resources/stylesheets/bootstrap-responsive.min.css" rel="stylesheet">
  <link href="${base}/resources/stylesheets/font-awesome.css" rel="stylesheet">
  <link href="${base}/resources/stylesheets/base-admin.css" rel="stylesheet">
  <link href="${base}/resources/stylesheets/select2.css" rel="stylesheet">
  <link href="${base}/resources/stylesheets/default.css" rel="stylesheet">

  <script src="${base}/resources/javascripts/jquery.min.js"></script>
  <script src="${base}/resources/javascripts/bootstrap.min.js"></script>
  <script src="${base}/resources/javascripts/select2.min.js"></script>
</head>
<body>
    <div class="navbar navbar-inverse navbar-fixed-top">
        <div class="navbar-inner">
            <div class="container">
                <a class="brand" href="/">
                    Zookeeper监控后台
                </a>
                <div class="nav-collapse">
                    <ul class="nav pull-right">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                <i class="icon-user"></i>
                                ${userName}
                                <b class="caret"></b>
                            </a>

                            <ul class="dropdown-menu">
                                <li><a href="/profile">我的档案</a></li>
                                <li class="divider"></li>
                                <li><a href="/logout">登出</a></li>
                            </ul>
                        </li>
                    </ul>
                </div><!--/.nav-collapse -->
            </div> <!-- /container -->
        </div> <!-- /navbar-inner -->
    </div>


     <div class="subnavbar">

        <div class="subnavbar-inner">

            <div class="container">

                <ul class="mainnav">
					<li <#if menu=="conf"> class="active" </#if>>
                        <a href="${base}/conf">
                            <i class="icon-home"></i>
                            <span>集群配置</span>
                        </a>
                    </li>

					<li <#if menu=="monitor"> class="active" </#if>>
                        <a href="${base}/monitor">
                            <i class="icon-home"></i>
                            <span>集群监控</span>
                        </a>
                    </li>
                    
                    <li <#if menu=="hostPerformance"> class="active" </#if>>
                        <a href="${base}/hostPerformance">
                            <i class="icon-home"></i>
                            <span>集群机器监控</span>
                        </a>
                    </li>
                    
                    <li <#if menu=="explorer"> class="active" </#if>>
                        <a href="${base}/explorer">
                            <i class="icon-home"></i>
                            <span>集群节点信息</span>
                        </a>
                    </li>

					<li <#if menu=="readMe"> class="active" </#if>>
                        <a href="${base}/readMe">
                            <i class="icon-home"></i>
                            <span>配置集群监控说明</span>
                        </a>
                    </li>
                </ul>

            </div> <!-- /container -->

        </div> <!-- /subnavbar-inner -->

    </div>




    <div class="main" style="min-height:400px;min-width:1024px">
		<#include "${url}" />
    </div>


    <div class="footer">
        <div class="footer-inner">
            <div class="container">
                <div class="row">
                    <div class="span12">
                        © 2014 <a href="http://www.letv.com">Letv Inc.</a>
                    </div> <!-- /span12 -->
                </div> <!-- /row -->
            </div> <!-- /container -->
        </div> <!-- /footer-inner -->
    </div>

</body>
</html>