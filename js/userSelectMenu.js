
var userViewSelectMenu = {
    changeUser: function(){
        var userID = parseInt(document.getElementById('userSelect0').value);
        this.resetChart();
        userView.updateUser(userID);
    },

    userInit: function(){
        if(typeof userInfo === 'undefined' || userInfo.length == 0){
            alert("Invalid user dataset!");
        }
        else{
            //console.log(userInfo);
            //console.log(userInfo.length);
            for(var i=1;i<userInfo.length;i++){
                var para = document.createElement("option");
                para.innerHTML = userInfo[i].userID.toString();
                document.getElementById("userSelect0").appendChild(para);
            }
        }
        userViewSelectMenu.changeUser();
    },

    changeY: function(){
        var userID = parseInt(document.getElementById('userSelect0').value);
        this.resetChart();
        userView.updateChart();
    },

    changeX : function(){
        var xAxis = document.getElementById('xAxisSelect0').value;
        this.resetChart();
        if(xAxis == "genre"){
            userView.updateByGenre();
        }
        else if(xAxis == "movieID" || xAxis == "year"){
            userView.updateByMovieID();
        }
    },

    changeOrder : function(){
        this.resetChart();
        userView.updateChart();
    }
    ,
    resetChart :function(){
        var xAxis = document.getElementById('xAxisSelect0').value;
        var yAxis = document.getElementById('yAxisSelect0').value;
        var chartType = document.getElementById('chartType0').value;
        if(xAxis == "genre"){
            $("#chartType0 .boxPlot").show();
            $("#chartType0 .treeMap").show();
            $("#chartType0 .pieChart").show();
        }
        else{
            //var par = document.getElementById('chartType0');
            $("#chartType0 .boxPlot").hide();
            $("#chartType0 .treeMap").hide();
            $("#chartType0 .pieChart").hide();
        }
        if(yAxis == 'rating'){
            $("#chartType0 .boxPlot").show();
            $("#chartType0 .treeMap").hide();
            $("#chartType0 .pieChart").hide();
        }
        else if(yAxis == 'recordNum'){
            $("#chartType0 .boxPlot").hide();
            $("#chartType0 .TreeMap").show();
            $("#chartType0 .pieChart").show();
        }
        if(chartType == 'Bar Chart' && xAxis != "year"){
            $("#sortSelect0").show();
            $("#label_sortSelect0").show();
        }
        else{
            $("#sortSelect0").hide();
            $("#label_sortSelect0").hide();
        }
    }
}
//userViewSelectMenu.userSelectInit0();


