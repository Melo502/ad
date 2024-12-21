// webapp/js/adAnalysis.js

document.addEventListener('DOMContentLoaded', function() {
    // 获取每个广告的聚合数据
    var adAggregations = window.adAggregations || [];
    var overallAggregation = window.overallAggregation || { totalClicks: 0, totalIncome: 0 };

    // 解析点击量和收益数据
    var categories = [];
    var clickCounts = [];
    var totalIncomes = [];

    adAggregations.forEach(function(agg) {
        var category = agg.adTitle + ' (ID: ' + agg.adId + ')';
        categories.push(category);
        clickCounts.push(agg.clickCount);
        totalIncomes.push(agg.totalIncome);
    });

    // 初始化点击量ECharts实例
    var clickChart = echarts.init(document.getElementById('clickChart'));

    // 配置点击量图表选项
    var clickOption = {
        title: {
            text: '每个广告的点击量',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['点击量'],
            top: '10%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '10%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: categories,
            axisLabel: {
                rotate: 45,
                interval: 0
            }
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '点击量',
                type: 'bar',
                data: clickCounts,
                itemStyle: {
                    color: '#28a745' // 绿色
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示点击量图表
    clickChart.setOption(clickOption);

    // 初始化收益ECharts实例
    var incomeChart = echarts.init(document.getElementById('incomeChart'));

    // 配置收益图表选项
    var incomeOption = {
        title: {
            text: '每个广告的收益',
            left: 'center'
        },
        tooltip: {
            trigger: 'axis'
        },
        legend: {
            data: ['收益'],
            top: '10%'
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '10%',
            containLabel: true
        },
        xAxis: {
            type: 'category',
            data: categories,
            axisLabel: {
                rotate: 45,
                interval: 0
            }
        },
        yAxis: {
            type: 'value'
        },
        series: [
            {
                name: '收益',
                type: 'bar',
                data: totalIncomes,
                itemStyle: {
                    color: '#17a2b8' // 青色
                }
            }
        ]
    };

    // 使用刚指定的配置项和数据显示收益图表
    incomeChart.setOption(incomeOption);

    // 使图表在窗口大小改变时自适应
    window.addEventListener('resize', function(){
        clickChart.resize();
        incomeChart.resize();
    });
});
