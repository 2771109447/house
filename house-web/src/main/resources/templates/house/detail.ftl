<!DOCTYPE html>

<html lang="en-US">
<@common.header/>

<body class="page-sub-page page-property-detail" id="page-top">
<div class="wrapper">
    <@common.nav/>

    <div id="page-content">
        <div class="container">
            <ol class="breadcrumb">
                <li><a href="#">Home</a></li>
                <li class="active">房产详情</li>
            </ol>
        </div>

        <div class="container">
            <div class="row">
                <div class="col-md-9 col-sm-9">
                    <section id="property-detail">
                        <header class="property-title">
                            <h1>${house.name}</h1>
                            <figure>${house.address}</figure>

                        <#if loginUser??>
                            <span class="actions">
                                <a href="#" class="bookmark" data-bookmark-state="empty"
 
                                ><span class="title-add">Add to bookmark</span><span class="title-added">Added</span></a>
                            </span>
                       </#if>
                        </header>
                        <section id="property-gallery">
                            <div class="owl-carousel property-carousel">
                                <#list house.imageList as image> 
                                   <div class="property-slide">
                                      <a href="${image}" class="image-popup">
                                         <div class="overlay"><h3>Front View</h3></div>
                                         <img alt="" src="${image}">
                                     </a>
                                   </div>
                                </#list>
                            </div>
                        </section>
                        <div class="row">
                            <div class="col-md-4 col-sm-12">
                                <section id="quick-summary" class="clearfix">
                                    <header><h2>总体</h2></header>
                                    <dl>
                                        <dt>地址</dt>
                                            <dd>${house.address}</dd>
                                        <dt>价格</dt>
                                            <dd><span class="tag price">${house.price}万</span></dd>
                                        <dt>类型:</dt>
                                            <dd>
                                             <#if (house.type)?? && (house.type)==1> 销售</#if>
                                             <#if (house.type)?? && (house.type)==2> 出租 </#if>
                                            </dd>
                                        <dt>面积:</dt>
                                            <dd>${house.area} m<sup>2</sup></dd>
                                        <dt>卧室:</dt>
                                            <dd>${house.beds}</dd>
                                        <dt>卫生间:</dt>
                                            <dd>${house.baths}</dd>
                                        <dt>评分:</dt>
                                            <dd><div class="rating rating-overall" data-score="${house.rating}"></div></dd>
                                    </dl>
                                </section>
                            </div>
                            <div class="col-md-8 col-sm-12">
                                <section id="description">
                                    <header><h2>房屋描述</h2></header>
                                    ${house.remarks}
                                </section><!-- /#description -->
                                <section id="property-features">
                                    <header><h2>房屋特点</h2></header>
                                    <ul class="list-unstyled property-features-list">
                                    <#list house.featureList as feature> 
                                        <li>${feature}</li>
                                    </#list>
                                    </ul>
                                </section>
                                <section id="floor-plans">
                                    <div class="floor-plans">
                                        <header><h2>户型图</h2></header>
                                         <#list house.floorPlanList as floorPlan> 
                                            <a href="${floorPlan}" class="image-popup"><img alt="" src="${floorPlan}" ></a>
                                         </#list>
                                    </div>
                                </section><!-- /#floor-plans -->
                               <!--   <section id="property-map">
                                    <header><h2>Map</h2></header>
                                    <div class="property-detail-map-wrapper">
                                        <div class="property-detail-map" id="property-detail-map"></div>
                                    </div>
                                </section> -->
                                <section id="property-rating">
                                    <header><h2>评价</h2></header>
                                    <div class="clearfix">
                                        <aside>
                                            <header>您的评价</header>
                                            <div class="rating rating-user">
                                                <div class="inner"></div>
                                            </div>
                                        </aside>
                                        <figure>
                                            <header>总体评价</header>
                                            <div class="rating rating-overall" data-score="${house.rating}"></div>
                                        </figure>
                                    </div>
                                    <div class="rating-form">
                                    </div>
                                </section>

                            </div>
                           
                            <div class="col-md-12 col-sm-12">
                                <#if (agent)?? >
                                   <section id="contact-agent">
                                    <header><h2>联系经纪人</h2></header>
                                    <div class="row">
                                        <section class="agent-form">
                                            <div class="col-md-7 col-sm-12">
                                                <aside class="agent-info clearfix">
                                                    <figure><a href="/agency/agentDetail?id=${(agent.id)!}"><img alt="" src="${(agent.avatar)!}"></a></figure>
                                                    <div class="agent-contact-info">
                                                        <h3>${(agent.userName)!}</h3>
                                                        <p>
                                                            ${(agent.aboutme)!}
                                                        </p>
                                                        <dl>
                                                            <dt>手机:</dt>
                                                            <dd>${(agent.phone)!}</dd>
                                                            <dt>Email:</dt>
                                                            <dd><a href="mailto:#">${(agent.email)!}</a></dd>
                                                            <dt>&nbsp;&nbsp;&nbsp;</dt>
                                                            <dd>&nbsp;&nbsp;&nbsp;</dd>
                                                        </dl>
                                                        <hr>
                                                    </div>
                                                </aside><!-- /.agent-info -->
                                            </div><!-- /.col-md-7 -->
                                            <div class="col-md-5 col-sm-12">
                                                <div class="agent-form">
                                                    <form role="form" id="form-contact-agent" method="post" action="/house/leaveMsg" class="clearfix">
                                                        <div class="form-group">
                                                            <label for="form-contact-agent-name">你的名字<em>*</em></label>
                                                            <input type="hidden" name="agentId" value="${(agent.id)!}">
                                                            <input type="hidden" name="houseId" value="${house.id}">
                                                            <input type="text" class="form-control" id="name" name="userName" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="form-contact-agent-email">你的邮箱<em>*</em></label>
                                                            <input type="email" class="form-control" id="form-contact-agent-email" name="email" required>
                                                        </div>
                                                        <div class="form-group">
                                                            <label for="form-contact-agent-message">你的留言<em>*</em></label>
                                                            <textarea class="form-control" id="form-contact-agent-message" rows="2" name="msg" required></textarea>
                                                        </div>
                                                        <div class="form-group">
                                                            <button type="submit" class="btn pull-right btn-default" id="form-contact-agent-submit">发送</button>
                                                        </div>
                                                        <div id="form-contact-agent-status"></div>
                                                    </form>
                                                </div>
                                            </div>
                                        </section>
                                    </div>
                                   </section>
                                </#if>
                               
                                <hr class="thick">
                                <section id="comments">
                                    <div class="agent-form">
                                                    <form role="form" id="form-contact-agent" method="post" action="/comment/leaveComment" class="clearfix">
                                                        <input type="hidden" name="houseId" value="${house.id}">
                                                        <div class="form-group">
                                                            <label for="form-contact-agent-message">评论</label>
                                                            <textarea class="form-control" id="form-contact-agent-message" rows="2" name="content" required></textarea>
                                                        </div><!-- /.form-group -->
                                                        <div class="form-group">
                                                            <button type="submit" class="btn pull-right btn-default" id="form-contact-agent-submit">评论</button>
                                                        </div><!-- /.form-group -->
                                                        <div id="form-contact-agent-status"></div>
                                                    </form><!-- /#form-contact -->
                                                </div>
                                    <header><h2 class="no-border">Comments</h2></header>
                                    <ul class="comments">
                                      <#--<#list commentList as comment> -->
                                        <#--<li class="comment" style="width: 830px;">-->
                                            <#--<figure>-->
                                                <#--<div class="image">-->
                                                    <#--<img alt="" src="${comment.avatar}">-->
                                                <#--</div>-->
                                            <#--</figure>-->
                                            <#--<div class="comment-wrapper">-->
                                                <#--<div class="name pull-left">${comment.userName}</div>-->
                                                <#--<span class="date pull-right"><span class="fa fa-calendar"></span>${(comment.createTime)?datetime}</span>-->
                                                <#--<p>${comment.content}-->
                                                <#--</p>-->
                                                <#--<hr>-->
                                            <#--</div>-->
                                        <#--</li>-->
                                      <#--</#list>-->
                                    </ul>
                                </section>
                            </div>
                        </div>
                    </section>
                </div>

                <div class="col-md-3 col-sm-3">
                    <section id="sidebar">
                        <@common.search />
                        <@common.hot />
                       
                    </section>
                </div><!-- /.col-md-3 -->
                <!-- end Sidebar -->
            </div><!-- /.row -->
        </div><!-- /.container -->
    </div>
    <@common.footer/>
</div>

<@common.js/>

<script type="text/javascript" src="/static//js/ie.js"></script>
 <script  type="text/javascript" >

    $(window).load(function(){
        initializeOwl(false);
    });


     $(document).ready(function() {
          var errorMsg   = "${errorMsg!""}";
          var successMsg = "${successMsg!""}";
          if(errorMsg){ 
              errormsg("error",errorMsg);
          }
          if(successMsg) {
              successmsg("success",successMsg);
          }

           var ratingUser = $('.rating-user');
            if (ratingUser.length > 0) {
                $('.rating-user .inner').raty({
                    path: '/static/assets/img',
                    starOff : 'big-star-off.png',
                    starOn  : 'big-star-on.png',
                    width: 150,
                    //target : '#hint',
                    targetType : 'number',
                    targetFormat : 'Rating: {score}',
                    click: function(score, evt) {
                        showRatingForm();
                        $.ajax({
                               url: "/house/rating?id=${house.id}&rating="+score,
                               type: 'GET',
                               cache:false,
                               timeout:60000
                              })
                              .done(function(ret) {
                                   
                              })
                    }
                });
            }
        })

    var bookmarkButton = $(".bookmark");

    
    bookmarkButton.on("click", function() {
        if (bookmarkButton.data('bookmark-state') == 'empty') {
            commonAjax('/house/bookmark?id=${house.id}');
        } else if (bookmarkButton.data('bookmark-state') == 'added') {
            commonAjax('/house/unbookmark?id=${house.id}');
        }
    });
    
   

        
 </script>

</body>
</html>