<%--
  Created by IntelliJ IDEA.
  User: IuRac
  Date: 2020/11/19
  Time: 14:22
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="echarts/echarts.min.js"></script>
    <title>Title</title>

    <script type="text/javascript">
        $(function (){
            showEcharts();
        })

        function showEcharts(){
            $.ajax({
                url: "workbench/transaction/getEcharts.do",
                type: "get",
                dataType: "json",
                success: function (data) {
                    // 基于准备好的dom，初始化echarts实例
                    var myChart = echarts.init(document.getElementById('main'));
                    option = {
                        title: {
                            text: '交易阶段分布图',
                            subtext: '展现交易各个阶段数量',
                            left: 'center'
                        },
                        tooltip: {
                            trigger: 'item',
                            formatter: '{a} <br/>{b} : {c} ({d}%)'
                        },
                        series: [
                            {
                                name: '访问来源',
                                type: 'pie',
                                radius: '55%',
                                center: ['50%', '60%'],
                                data: data.dataList,
                                emphasis: {
                                    itemStyle: {
                                        shadowBlur: 10,
                                        shadowOffsetX: 0,
                                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                                    }
                                }
                            }
                        ]
                    };
                    myChart.setOption(option);
                }
            });


        }
    </script>
</head>
<body>
    <!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
    <div class="container">
        <div id="main" style="width: 80vh;height:90vh;"></div>
    </div>
</body>
</html>
