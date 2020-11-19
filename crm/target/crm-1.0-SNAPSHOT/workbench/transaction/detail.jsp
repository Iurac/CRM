<%@ page import="java.util.Map" %>
<%@ page import="com.iurac.crm.settings.domain.DicValue" %>
<%@ page import="java.util.List" %>
<%@ page import="jdk.internal.org.objectweb.asm.tree.analysis.Value" %>
<%@ page import="com.iurac.crm.workbench.domain.Tran" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";

List<DicValue> stageList = (List<DicValue>) application.getAttribute("stageList");
Map<String ,String> stage2Possibility = (Map<String, String>) application.getAttribute("stage2Possibility");
int point = 0;

	for (int i = 0; i < stageList.size(); i++) {
		DicValue stage = stageList.get(i);
		String possibility = stage2Possibility.get(stage.getValue());
		if("0".equals(possibility)){
			point = i;
			break;
		}
	}

%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />

<style type="text/css">
.mystage{
	font-size: 20px;
	vertical-align: middle;
	cursor: pointer;
}
.closingDate{
	font-size : 15px;
	cursor: pointer;
	vertical-align: middle;
}
</style>
	
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>

<script type="text/javascript">

	//默认情况下取消和保存按钮是隐藏的
	var cancelAndSaveBtnDefault = true;
	
	$(function(){
		$("#remark").focus(function(){
			if(cancelAndSaveBtnDefault){
				//设置remarkDiv的高度为130px
				$("#remarkDiv").css("height","130px");
				//显示
				$("#cancelAndSaveBtn").show("2000");
				cancelAndSaveBtnDefault = false;
			}
		});
		
		$("#cancelBtn").click(function(){
			//显示
			$("#cancelAndSaveBtn").hide();
			//设置remarkDiv的高度为130px
			$("#remarkDiv").css("height","90px");
			cancelAndSaveBtnDefault = true;
		});
		
		$(".remarkDiv").mouseover(function(){
			$(this).children("div").children("div").show();
		});
		
		$(".remarkDiv").mouseout(function(){
			$(this).children("div").children("div").hide();
		});
		
		$(".myHref").mouseover(function(){
			$(this).children("span").css("color","red");
		});
		
		$(".myHref").mouseout(function(){
			$(this).children("span").css("color","#E6E6E6");
		});
		
		
		//阶段提示框
		$(".mystage").popover({
            trigger:'manual',
            placement : 'bottom',
            html: 'true',
            animation: false
        }).on("mouseenter", function () {
                    var _this = this;
                    $(this).popover("show");
                    $(this).siblings(".popover").on("mouseleave", function () {
                        $(_this).popover('hide');
                    });
                }).on("mouseleave", function () {
                    var _this = this;
                    setTimeout(function () {
                        if (!$(".popover:hover").length) {
                            $(_this).popover("hide")
                        }
                    }, 100);
                });
		showTranHistoryList();
	});
	
	function showTranHistoryList(){
		$.ajax({
			url: "workbench/transaction/getTranHistoryListByTid.do",
			data: {
				"id":"${tran.id}"
			},
			type: "get",
			dataType: "json",
			success: function (data) {
				var html = '';
				$.each(data, function (i, n) {
					html += '<tr>';
					html += '<td>'+n.stage+'</td>';
					html += '<td>'+n.money+'</td>';
					html += '<td>'+n.possibility+'</td>';
					html += '<td>'+n.expectedDate+'</td>';
					html += '<td>'+n.createTime+'</td>';
					html += '<td>'+n.createBy+'</td>';
					html += '</tr>';
				});
				$("#tranHistoryBody").html(html);
			}
		})
	}

	function changeStage(stage,i){
		$.ajax({
			url: "workbench/transaction/changeStage.do",
			data: {
				"id":"${tran.id}",
				"stage":stage,
				"money":"${tran.money}",
				"expectedDate":"${tran.expectedDate}"
			},
			type: "post",
			dataType: "json",
			success: function (data) {
				if(data.success){
					$("#stage").html(data.tran.stage);
					$("#possibility").html(data.tran.possibility);
					$("#editBy").html(data.tran.editBy+"  ");
					$("#editTime").html(data.tran.editTime);

					showTranHistoryList();

					changeStageIcon($("#stage").html(),i);
				}else {
					alert("修改阶段失败");
				}
			}
		})
	}

	function changeStageIcon(stage,index){
		var currentStage = stage;
		var currentPossibility = $("#possibility").html();
		var index = index;
		var point = "<%=point%>";

		if(currentPossibility=="0"){
			for (var i = 0; i < point; i++) {
				$("#"+i).removeClass();
				$("#"+i).addClass("glyphicon glyphicon-record mystage");
				$("#"+i).css("color","#000000");
			}
			for (var i = point; i < <%=stageList.size()%>; i++) {
				if(i == index){
					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-remove mystage");
					$("#"+i).css("color","#FF0000");
				}else {

					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-remove mystage");
					$("#"+i).css("color","#000000");
				}
			}
		}else {
			for (var i = 0; i < point; i++) {
				if(i == index){
					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-map-marker mystage");
					$("#"+i).css("color","#90F790");
				}else if(i < index){
					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-record mystage");
					$("#"+i).css("color","#90F790");
				}else {
					$("#"+i).removeClass();
					$("#"+i).addClass("glyphicon glyphicon-record mystage");
					$("#"+i).css("color","#000000");
				}
			}
			for (var i = point; i < <%=stageList.size()%>; i++) {
				$("#"+i).removeClass();
				$("#"+i).addClass("glyphicon glyphicon-remove mystage");
				$("#"+i).css("color","#000000");
			}
		}
	}
	
</script>

</head>
<body>
	
	<!-- 返回按钮 -->
	<div style="position: relative; top: 35px; left: 10px;">
		<a href="javascript:void(0);" onclick="window.history.back();"><span class="glyphicon glyphicon-arrow-left" style="font-size: 20px; color: #DDDDDD"></span></a>
	</div>
	
	<!-- 大标题 -->
	<div style="position: relative; left: 40px; top: -30px;">
		<div class="page-header">
			<h3>${tran.customerId}-${tran.name}<small>${tran.money}</small></h3>
		</div>
		<div style="position: relative; height: 50px; width: 250px;  top: -72px; left: 700px;">
			<button type="button" class="btn btn-default" onclick="window.location.href='edit.jsp';"><span class="glyphicon glyphicon-edit"></span> 编辑</button>
			<button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>

	<!-- 阶段状态 -->
	<div style="position: relative; left: 40px; top: -50px;">
		阶段&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<%
			Tran tran = (Tran) request.getAttribute("tran");
			String currentPossibility = tran.getPossibility();
			String currentStage = tran.getStage();

			if("0".equals(currentPossibility)){
				for (int i = 0; i < stageList.size(); i++) {
					DicValue stageDv = stageList.get(i);
					String stage = stageDv.getValue();
					String possibility = stage2Possibility.get(stage);
					if("0".equals(possibility)){
						if(currentStage.equals(stage)){
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-remove mystage" style="color: red;"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
						}else {
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-remove mystage"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
						}
					}else {
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-record mystage"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
					}
				}
			}else {
				int index = 0;
				for (int i = 0; i < stageList.size(); i++) {
					DicValue stageDv = stageList.get(i);
					String stage = stageDv.getValue();
					if(currentStage.equals(stage)){
						index = i;
					}
				}
				for (int i = 0; i < stageList.size(); i++) {
					DicValue stageDv = stageList.get(i);
					String stage = stageDv.getValue();
					String possibility = stage2Possibility.get(stage);
					System.out.println(i+"---"+index);
					if("0".equals(possibility)){
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-remove mystage"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
					}else {
						if(i == index){
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-map-marker mystage" style="color: #90F790;"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
						}else if(i < index){
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-ok-circle mystage" style="color: #90F790;"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
						}else {
		%>
		<span id="<%=i%>" onclick="changeStage('<%=stageList.get(i).getValue()%>','<%=i%>')"
			  class="glyphicon glyphicon-record mystage"
			  data-toggle="popover" data-placement="bottom" data-content="<%=stage%>"></span>
		-----------
		<%
						}
					}
				}
			}
		%>
		<span class="closingDate">${tran.expectedDate}</span>
	</div>
	
	<!-- 详细信息 -->
	<div style="position: relative; top: 0px;">
		<div style="position: relative; left: 40px; height: 30px;">
			<div style="width: 300px; color: gray;">所有者</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.owner}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">金额</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${tran.money}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 10px;">
			<div style="width: 300px; color: gray;">名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.customerId}-${tran.name}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">预计成交日期</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${tran.expectedDate}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 20px;">
			<div style="width: 300px; color: gray;">客户名称</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.customerId}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">阶段</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;" id="stage"><b>${tran.stage}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 30px;">
			<div style="width: 300px; color: gray;">类型</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.type}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">可能性</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;" id="possibility"><b>${tran.possibility}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 40px;">
			<div style="width: 300px; color: gray;">来源</div>
			<div style="width: 300px;position: relative; left: 200px; top: -20px;"><b>${tran.source}</b></div>
			<div style="width: 300px;position: relative; left: 450px; top: -40px; color: gray;">市场活动源</div>
			<div style="width: 300px;position: relative; left: 650px; top: -60px;"><b>${tran.activityId}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px;"></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -60px; left: 450px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 50px;">
			<div style="width: 300px; color: gray;">联系人名称</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${tran.contactsId}</b></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 60px;">
			<div style="width: 300px; color: gray;">创建者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${tran.createBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;">${tran.createTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 70px;">
			<div style="width: 300px; color: gray;">修改者</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b id="editBy">${tran.editBy}&nbsp;&nbsp;</b><small style="font-size: 10px; color: gray;" id="editTime">${tran.editTime}</small></div>
			<div style="height: 1px; width: 550px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 80px;">
			<div style="width: 300px; color: gray;">描述</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					${tran.description}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 90px;">
			<div style="width: 300px; color: gray;">联系纪要</div>
			<div style="width: 630px;position: relative; left: 200px; top: -20px;">
				<b>
					&nbsp;${tran.contactSummary}
				</b>
			</div>
			<div style="height: 1px; width: 850px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
		<div style="position: relative; left: 40px; height: 30px; top: 100px;">
			<div style="width: 300px; color: gray;">下次联系时间</div>
			<div style="width: 500px;position: relative; left: 200px; top: -20px;"><b>${tran.nextContactTime}</b></div>
			<div style="height: 1px; width: 400px; background: #D5D5D5; position: relative; top: -20px;"></div>
		</div>
	</div>
	
	<!-- 备注 -->
	<div style="position: relative; top: 100px; left: 40px;">
		<div class="page-header">
			<h4>备注</h4>
		</div>
		
		<!-- 备注1 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>哎呦！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>动力节点-交易01</b> <small style="color: gray;"> 2017-01-22 10:10:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		<!-- 备注2 -->
		<div class="remarkDiv" style="height: 60px;">
			<img title="zhangsan" src="image/user-thumbnail.png" style="width: 30px; height:30px;">
			<div style="position: relative; top: -40px; left: 40px;" >
				<h5>呵呵！</h5>
				<font color="gray">交易</font> <font color="gray">-</font> <b>动力节点-交易01</b> <small style="color: gray;"> 2017-01-22 10:20:10 由zhangsan</small>
				<div style="position: relative; left: 500px; top: -30px; height: 30px; width: 100px; display: none;">
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-edit" style="font-size: 20px; color: #E6E6E6;"></span></a>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="myHref" href="javascript:void(0);"><span class="glyphicon glyphicon-remove" style="font-size: 20px; color: #E6E6E6;"></span></a>
				</div>
			</div>
		</div>
		
		<div id="remarkDiv" style="background-color: #E6E6E6; width: 870px; height: 90px;">
			<form role="form" style="position: relative;top: 10px; left: 10px;">
				<textarea id="remark" class="form-control" style="width: 850px; resize : none;" rows="2"  placeholder="添加备注..."></textarea>
				<p id="cancelAndSaveBtn" style="position: relative;left: 737px; top: 10px; display: none;">
					<button id="cancelBtn" type="button" class="btn btn-default">取消</button>
					<button type="button" class="btn btn-primary">保存</button>
				</p>
			</form>
		</div>
	</div>
	
	<!-- 阶段历史 -->
	<div>
		<div style="position: relative; top: 100px; left: 40px;">
			<div class="page-header">
				<h4>阶段历史</h4>
			</div>
			<div style="position: relative;top: 0px;">
				<table id="activityTable" class="table table-hover" style="width: 900px;">
					<thead>
						<tr style="color: #B3B3B3;">
							<td>阶段</td>
							<td>金额</td>
							<td>可能性</td>
							<td>预计成交日期</td>
							<td>创建时间</td>
							<td>创建人</td>
						</tr>
					</thead>
					<tbody id="tranHistoryBody">
					</tbody>
				</table>
			</div>
			
		</div>
	</div>
	
	<div style="height: 200px;"></div>
	
</body>
</html>