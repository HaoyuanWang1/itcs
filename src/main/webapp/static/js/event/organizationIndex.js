define(function(require, exports) {
	var $ = require('jquery');
	require('flottime');
	require('flotpie');
	
	var $eventResuleTable = $("#eventResuleTable");
	var $canvasT = $("#placeholderT");
	//客户
	var optionsT = {
			series:{
				pie:{
					show:true,
					label:{
						 show: true,
				            radius:0.5,
				            formatter: function (label, series) {   
				            	return label+'<div style="padding:5px; text-align:center;color:black;font-size:8pt;">'+series.data[0][1]+'</div>';
				        },
				        background:{
							opacity:0.1
						}
					}
				},
				groupEvents:tenantGroupEvents,
				tenantId:{
					tenantId:tenantId
				}
			},
			grid: {
		        hoverable: true,
		        clickable: true
		    }
	};

	var datasetT="";
	if(tenantData.length > 0){
		datasetT = eval("["+tenantData.substring(1)+"]");
	}
	
	$.plot($canvasT, datasetT, optionsT);
	
	$canvasT.bind("plotclick", function (event, pos, item) {
		if (item) {
			var seriaIndex=item.seriesIndex;
			var eventIds = item.series.groupEvents[seriaIndex];
			$.post(webContext + "/event/panel/unSloveEvents/"+eventIds, 'html')
				.done(function (html) {
					$eventResuleTable.modal('show');
					$eventResuleTable.html(html);
			});
		}
	});
    
   //客户经理
    var datasetTM = [{ data: spUserData, color: "#a5a7e4" }];
	var $canvasTM = $("#placeholderTM");
	var optionsTM = {
	    series: {
	        bars: {
	            show: true
	        },
	        groupEvents:userGroupEvents,
	    },
	    bars: {
	        align: "center",
	        barWidth: 0.5,
	        lineWidth: 1,
	        color:'#ccc',
            fillColor:'#4f81bd' //'#a5a7e4'柱子颜色
	    },
	    xaxis: {
	    	ticks: spUser
	    },
	    yaxis: {
	    },
	    legend: {
	        noColumns: 0,
	        labelBoxBorderColor: "#000",
	        position: "ne"
	    },
	    grid: {
	        hoverable: true,
	        clickable: true,
	        borderWidth: 1,
	        backgroundColor: { colors: ["#ffffff", "#EDF5FF"] }
	    }
	};
	if(spUserData.length <= 3){
		optionsTM.xaxis.max = 3;
	}
    $.plot($canvasTM, datasetTM, optionsTM);
    
    $canvasTM.bind("plotclick", function (event, pos, item) {
		if (item) {
			var seriaIndex=item.dataIndex;
            var eventIds = item.series.groupEvents[seriaIndex];
			$.post(webContext + "/event/panel/unSloveEvents/"+eventIds, 'html')
				.done(function (html) {
					$eventResuleTable.modal('show');
					$eventResuleTable.html(html);
			});
		}
	});
	
    F.close= function(){
    	$eventResuleTable.modal('hide');
    	$eventResuleTable.removeClass('in').css('display','none');
    };
    
	var $taskSignerPage = $('#taskSignerPage');
	$taskSignerPage._load(webContext + '/event/panel/signerPage');
});