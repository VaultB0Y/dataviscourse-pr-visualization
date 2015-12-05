
var movieViewSelectMenu = {
    changeMovie: function(){
        var movieID = parseInt(document.getElementById('movieSelect1').value);
        this.resetChart();
        movieView.updateMovie(movieID);
    },

    movieInit: function(){
        if(typeof movieInfo === 'undefined' || movieInfo.length == 0){
            alert("Invalid movie dataset!");
        }
        else{
            for(var i=1;i<movieInfo.length;i++){
                var para = document.createElement("option");
                para.innerHTML = movieInfo[i].movieID.toString();
                document.getElementById("movieSelect1").appendChild(para);
            }
        }
        this.changeMovie();
    },

    changeY: function(){
        this.resetChart();
        movieView.updateChart();
    },

    changeX : function(){
        var xAxis = document.getElementById('xAxisSelect1').value;
        this.resetChart();
        movieView.updateChart();
    },

    changeChart : function(){
        this.resetChart();
        movieView.updateChart();
    },

    changeOrder : function(){
        this.resetChart();
        movieView.updateChart();
    },

    resetChart :function(){
        var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        var chartType = document.getElementById('chartType1').value;

        if(yAxis == 'rating'){
            $("#chartType1 .boxPlot").show();
            $("#chartType1 .treeMap").hide();
            $("#chartType1 .pieChart").hide();
        }
        else if(yAxis == 'recordNum'){
            $("#chartType1 .boxPlot").hide();
            $("#chartType1 .treeMap").show();
            $("#chartType1 .pieChart").show();
        }

        if(chartType == 'Bar Chart'){
            $("#sortSelect1").show();
            $("#label_sortSelect1").show();
        }
        else{
            $("#sortSelect1").hide();
            $("#label_sortSelect1").hide();
        }
    }
}


