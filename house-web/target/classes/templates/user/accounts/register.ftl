<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-create-account page-account" id="page-top">
<div class="wrapper">
    <@common.nav/>

    <div id="page-content">
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li class="active">注册</li>
            </ol>
        </div>

        <div class="container">
            <header><h1>注册账号</h1></header>
            <div class="row">
                <div class="col-md-4 col-sm-6 col-md-offset-4 col-sm-offset-3">
                    <h3>Account Type</h3>
                    <form role="form" id="form-create-account" method="post" enctype="multipart/form-data" action="/user/Register">
                        <div class="radio" id="create-account-user">
                            <label>
                                <input type="radio" value="1" id="account-type-user"  name="type" required>普通用户
                            </label>
                        </div>
                        <div class="radio" id="agent-switch" data-agent-state="">
                            <label>
                                <input type="radio" value="2" id="account-type-agent" name="type" required>经纪人
                            </label>
                        </div>

                        <hr>
                        <div class="form-group">
                            <label for="form-create-account-full-name">全名:</label>
                            <input type="text" class="form-control" id="form-create-account-full-name" name="userName" required>
                        </div>
                        <div class="form-group">
                            <label for="form-create-account-email">Email:</label>
                            <input type="text" class="form-control" id="form-create-account-email"  name="email" required>
                        </div>
                        <div class="form-group">
                            <label for="form-create-account-email">手机号:</label>
                            <input type="text" class="form-control" id="form-create-account-email"  name="phone" >
                        </div>
                        <div class="form-group">
                            <label for="form-create-account-password">密码:</label>
                            <input type="password" class="form-control" value="123456" id="form-create-account-password" name="passwd" required>
                        </div>
                        <div class="form-group">
                            <label for="form-create-account-confirm-password">确认密码:</label>
                            <input type="password" class="form-control" value="123456"id="form-create-account-confirm-password" name="confirmPasswd" required>
                        </div>
                         <div class="form-group">
                            <label for="form-create-account-email">自我介绍:</label>
                            <textarea class="form-control" name="aboutme"></textarea> 
                        </div>

                        <div class="form-group">
                            <label for="form-create-account-email">用户头像:</label>
                            <input id="file-upload" type="file" class="file" multiple="true" data-show-upload="false" data-show-caption="false" data-show-remove="false" accept="image/*" data-browse-class="btn btn-default" data-browse-label="Browse Images" name="avatarFile" required>
                            <figure class="note"><strong>Hint:</strong> You can upload all images at once!</figure>
                        </div>
                              
                        
                        <div class="form-group clearfix">
                            <button type="submit" class="btn pull-right btn-default" id="account-submit">注册账号</button>
                        </div>
                        
                        

                    </form>
                    <hr>
                    <div class="center">
                        <figure class="note"> 我已阅读并同意<a href="terms-conditions.html">用户协议</a></figure>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <@common.footer/>
</div>

<@common.js/>
<script type="text/javascript" src="/static/assets/js/ie.js"></script>
 <script  type="text/javascript" >
    $(document).ready(function() {
          var errorMsg   = "${errorMsg!""}";
          var successMsg = "${successMsg!""}";
          if(errorMsg){ 
              errormsg("error",errorMsg);
          }
          if(successMsg) {
              successmsg("success",successMsg);
          }
        })
        
 </script>
</body>
</html>