//single user view
//y axis: rating
//x axis: movieID group by genre
//year

var userView = {
    //store a specific user's rating data
    oneUserRating : null,
    ratingByGenre : null,
    ratingByMovie : null,
    selectedData : null,
    updateUser : function(userID){
        userView.movieRating = [];
        userID = parseInt(userID);
        var filePath = 'data/ratingByUser/' + parseInt((userID - 1)/100).toString() + '.csv';
        d3.csv(filePath, function(error, data){
            userView.movieRating = [];
            if(typeof movieInfo === 'undefined' || movieInfo.length == 0){
                alert("Invalid Movie Dataset!")
            }
            for(var i=0;i<data.length;i++){
                if(parseInt(data[i].userID) == userID){
                    var temp = data[i];
                    temp.rating = parseInt(temp.rating);
                    userView.movieRating.push(temp);
                }
            }
            var xAxis = document.getElementById('xAxisSelect0').value;
            if(xAxis == 'movieID'){
                userView.updateByMovieID();
            }
            else{
                userView.updateByGenre();
            }
        });
    },
    updateByGenre : function(){
        var data = [];
        for(var i=0;i<userView.movieRating.length;i++){
            for(var j=0;j<genreType.length;j++){
                var property = "genre" + j.toString();
                var movie = movieInfo[movieID2Idx[userView.movieRating[i].movieID]];
                if(movie[property] == '1'){
                    //var row = userView.movieRating[i];    //shalow copy
                    var row = {};
                    row.userID = userView.movieRating[i].userID;
                    row.movieID = userView.movieRating[i].movieID;
                    row.rating = userView.movieRating[i].rating;
                    row.genre = genreType[j];
                    row.title = movie.title;
                    row.year = movie.year;
                    row.recordNum = 1;
                    row.recordID = data.length;
                    //row.avgRating = userView.movieRating[i].rating;
                    data.push(row);
                }
            }
        }
        userView.ratingByGenre = data;
        userView.selectedData = userView.ratingByGenre;
        userView.updateChart();
    },
    updateByMovieID : function(){
        userView.ratingByMovie = [];
        for(var i=0;i<userView.movieRating.length;i++){
            var row = userView.movieRating[i];
            var movie = movieInfo[movieID2Idx[row.movieID]];
            row.title = movie.title;
            row.year = movie.year;
            row.recordNum = 1;
            //row.avgRating = row.rating;
            userView.ratingByMovie.push(row);
        }
        userView.selectedData = userView.ratingByMovie;
        userView.updateChart();
    },

    updateChart : function() {
        var chartType = document.getElementById('chartType0').value;
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
    },
    updateBarChart : function() {
        var xAxis = document.getElementById('xAxisSelect0').value;
        var yAxis = document.getElementById('yAxisSelect0').value;
        var sort = document.getElementById('sortSelect0').value;
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
            .container("#userView")
            .data(this.selectedData)
            .type("bar")
            .id(xAxis)
            .x(xAxis)
            .y(yAxis)
            .aggs({"rating": "mean"})
            .tooltip(["title", "year", "recordNum", "rating"])
            .time("year")
            .order({
                sort : sortType,
                value : sortValue
            })
            .mouse({
                click : this.updateMovieViewByClick
            })
            .draw()
    },

    updateBoxPlot : function(){
        var xAxis = document.getElementById('xAxisSelect0').value;
        var yAxis = document.getElementById('yAxisSelect0').value;
        var visualization = d3plus.viz()
            .container("#userView")
            .data(this.selectedData)
            .type("box")
            .id("movieID")
            .x(xAxis)
            .y(yAxis)
            //.aggs({"rating": "mean"})
            //.aggs({"recordNum": "sum"})
            .time("year")
            .mouse({
                click : this.updateMovieViewByClick
            })
            .draw()
    },

    updateTreeMap : function(){
        var xAxis = document.getElementById('xAxisSelect0').value;
        var yAxis = document.getElementById('yAxisSelect0').value;
        var visualization = d3plus.viz()
            .container("#userView")
            .data(this.selectedData)
            .type("tree_map")
            .id(xAxis)
            .size("recordNum")
            .labels({"align": "left", "valign": "top"})
            .time("year")
            .mouse({
                click : this.updateMovieViewByClick
            })
            .draw()
    },

    updatePieChart : function(){
        var xAxis = document.getElementById('xAxisSelect0').value;
        var yAxis = document.getElementById('yAxisSelect0').value;
        d3plus.viz()
            .container("#userView")
            .data(this.selectedData)
            .type("pie")
            .id(xAxis)
            .size("recordNum")
            .time("year")
            .tooltip(["title", "year", "recordNum", "rating"])
            .mouse({
                click : this.updateMovieViewByClick
            })
            .draw()
    },

    updateLineChart : function(){
        var xAxis = document.getElementById('xAxisSelect0').value;
        var yAxis = document.getElementById('yAxisSelect0').value;
        var visualization = d3plus.viz()
            .container("#userView")
            .data(this.selectedData)
            .type("line")
            .id(xAxis)
            .x(xAxis)
            .y(yAxis)
            .text("title")
            .time("year")
            .aggs({"rating": "mean"})
            .tooltip(["title", "year", "recordNum", "rating"])
            .mouse({
                click : this.updateMovieViewByClick
            })
            .draw()
    },

    updateMovieViewByClick : function(d){
        if(typeof d.movieID == 'string'){
            var movie = document.getElementById("movieSelect1");
            movie.options[movieID2Idx[d.movieID]].selected = true;
            movieViewSelectMenu.changeMovie();
        }
    }
}