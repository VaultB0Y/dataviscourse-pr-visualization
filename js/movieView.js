//single movie view
//y axis: rating, record Num
//x axis: sex, age and state, occupation
//year


var movieView = {
    //store a specific movie's rating data
    movieRating : null,
    selectedData : null,
    updateMovie : function(movieID){
        this.movieRating = [];
        movieID = parseInt(movieID);
        var filePath = 'data/ratingByMovie/' + parseInt((movieID - 1)/100).toString() + '.csv';
        d3.csv(filePath, function(error, data){
            if(typeof movieInfo === 'undefined' || movieInfo.length == 0){
                alert("Invalid Movie Dataset!")
            }
            for(var i=0;i<data.length;i++){
                if(parseInt(data[i].movieID) == movieID){
                    var temp = data[i];
                    temp.rating = parseInt(temp.rating);
                    movieView.movieRating.push(temp);
                }
            }
            //console.log(movieView.movieRating);
            movieView.updateMovieData();
        });
    },

    updateMovieData : function() {
        for(var i=0;i<this.movieRating.length;i++){
            //var row = this.movieRating[i];
            var user = userInfo[userID2Idx[this.movieRating[i].userID]];
            //this.movieRating[i].sex = user.sex;
            if(user.sex == 'F'){
                this.movieRating[i].sex = 'Female';
            }
            else{
                this.movieRating[i].sex = 'Male';
            }
            this.movieRating[i].age = ageToRange[user.age];
            this.movieRating[i].occupation = user.occupation;
            this.movieRating[i].state = user.state;
            this.movieRating[i].stateID = state2ID[user.state];
            this.movieRating[i].recordNum = 1;
        }
        this.selectedData = this.movieRating;
        this.updateChart();
    },

    updateChart : function() {
        var chartType = document.getElementById('chartType1').value;
        if(chartType == "Bar Chart"){
            this.updateBarChart();
        }
        else if(chartType == "Box Plot"){
            this.updateBoxPlot();
        }
        else if(chartType == "Tree Map"){
            this.updateTreeMap();
        }
        else if(chartType == "Pie Chart"){
            this.updatePieChart();
        }
        else if(chartType == "Point Chart"){
            this.updateLineChart();
        }
        else if(chartType == "Geo Map"){
            this.updateGeoMap();
        }
    },
    updateBarChart : function() {
        var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        var sort = document.getElementById('sortSelect1').value;
        var sortType = 0;
        if(sort == 'Descending Order'){
            sortType = 'desc';
        }
        else{
            sortType = 'asc';
        }
        var sortValue = false;
        if (sort != 'Original Order'){
            sortValue = yAxis;
        }
        else{
            sortValue = xAxis;
        }
        var visualization = d3plus.viz()
            .container("#movieView")
            .data(this.selectedData)
            .type("bar")
            .id(xAxis)
            .x(xAxis)
            .y(yAxis)
            .aggs({"rating": "mean"})
            .order({
                sort : sortType,
                value : sortValue
            })
            //.order({sort:"asc", value:xAxis})
            .tooltip(["title", "year", "recordNum", "rating"])
            .draw()
    },

    updateBoxPlot : function(){
        var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        var visualization = d3plus.viz()
            .container("#movieView")
            .data(this.selectedData)
            .type("box")
            .id("userID")
            .x(xAxis)
            .y(yAxis)
            .tooltip("rating", "recordNum")
            //.aggs({"rating": "mean"})
            //.aggs({"recordNum": "sum"})
            .draw()
    },

    updateTreeMap : function(){
        var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        var visualization = d3plus.viz()
            .container("#movieView")
            .data(this.selectedData)
            .type("tree_map")
            .id(xAxis)
            .size("recordNum")
            .aggs({"rating": "mean"})
            .tooltip("rating", "recordNum")
            .labels({"align": "left", "valign": "top"})
            .draw()
    },

    updatePieChart : function(){
        var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        d3plus.viz()
            .container("#movieView")
            .data(this.selectedData)
            .type("pie")
            .id(xAxis)
            .size("recordNum")
            .aggs({"rating": "mean"})
            .tooltip("rating", "recordNum")
            .draw()
    },

    updateLineChart : function(){
        var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        var visualization = d3plus.viz()
            .container("#movieView")
            .data(this.selectedData)
            .type("line")
            .id(xAxis)
            .x(xAxis)
            .y(yAxis)
            .text("title")
            .aggs({"rating": "mean"})
            .tooltip(["recordNum", "rating"])
            .draw()
    },

    updateGeoMap : function() {
        //var xAxis = document.getElementById('xAxisSelect1').value;
        var yAxis = document.getElementById('yAxisSelect1').value;
        var visualization = d3plus.viz()
            .container("#movieView")
            .data(this.selectedData)
            .coords("data/us.json")
            .type("geo_map")
            .id("stateID")
            .text("state")
            .color({
                "heatmap": ["white", "purple"],
                "value": yAxis
            })
            .aggs({"rating": "mean"})
            .tooltip(["rating", "recordNum"])
            .draw()
    }
}
